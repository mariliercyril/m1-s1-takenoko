package com.raccoon.takenoko.player;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.game.Panda;
import com.raccoon.takenoko.game.objective.Objective;
import com.raccoon.takenoko.game.objective.ObjectivePool;
import com.raccoon.takenoko.game.objective.ObjectiveType;
import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.tool.Constants;
import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.tool.graphs.Edge;
import com.raccoon.takenoko.tool.graphs.Graph;
import com.raccoon.takenoko.tool.graphs.Path;
import com.raccoon.takenoko.tool.graphs.UnexistingEdgeException;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class PathFinderBot extends Player {

    /*
    *****************************************************
    The idea here is to have a graph representation of the board,
    with an adjacency matrix of the bamboo tiles for example
    to know in how many turns we can get from one bamboo tile to another.
    This would allow to search for a shorter path in the graph
    to a tile of the given colour, or for a path to a tile which is as well
    close to another tile with the desired colour.

    We could also think of paths with the weights of the arcs being
    the number of bamboo tiles for example…

    The graph should be computed in the first turn and kept in memory,
    as the tiles on which bamboos will grow will remain the same.

    To determine where to move the panda, it will simply be finding
    a path with the correct properties.

    If bamboos don't grow, we'll need to be able to delete a summit
    when no bamboos remain on the tile.

     *****************************************************
     */

    private List<Objective> objectivesToValidate;

    private Graph bambooTilesGraph;

    private Path currentPathInGraph;

    private Deque<Point> currentSteps;

    private Deque<Color> coloursNeeded;

    public PathFinderBot() {
        super();
        this.objectivesToValidate = new ArrayList<>();
        this.currentSteps = new ArrayDeque<>();
        this.currentPathInGraph = new Path();
        this.coloursNeeded = new ArrayDeque<>();
    }

    @Override
    protected Action[] planActions(Game game) {

        objectivesToValidate = new ArrayList<>();   // Re-initialisation of the objectives to validate list

        Action[] actions;
        /*
        The whole thing could be done using the size of the objectivesToValidate list instead
         */
        int actionNumber = 1;   // We at least have one action : moving the panda

        for (Objective objective : this.getObjectives()) {
            if (objective.isCompleted()) {
                actionNumber++;
                objectivesToValidate.add(objective);
            }
        }

        // If we have less than the maximum number of objective or if we'll validate one in this round
        if (this.getObjectives().size() < Constants.MAX_AMOUNT_OF_OBJECTIVES || actionNumber > 1) {
            actions = new Action[actionNumber + 1];   // We will have all the actions we already need, plus one to draw an objective
            actions[actionNumber - 1] = Action.DRAW_OBJECTIVE;  // The action just before the last will be to draw an objective
            actions[actionNumber] = Action.MOVE_PANDA;          // The last action will be to move the panda
        }
        else {
            actions = new Action[actionNumber];     // We just prepare the actions we need
            actions[actionNumber - 1] = Action.MOVE_PANDA;
        }


        for (int i = 0; i < actionNumber - 1; i++) {    // For the number of actions we needed to validate abjectives
            actions[i] = Action.VALID_OBJECTIVE;
        }

        return actions;
    }

    @Override
    protected ObjectiveType whatTypeToDraw(ObjectivePool pool) {
        return ObjectiveType.PANDA;
    }

    @Override
    protected Point whereToMovePanda(Game game, List<Point> available) {

        Board board = game.getBoard();
        Panda panda = game.getPanda();
        Tile pandaTile = board.get(panda.getPosition());

        if (coloursNeeded.isEmpty()) {  // If we don't have new colours to eat

            List<Objective> sortedObjectives = this.getObjectives().stream().sorted(Comparator.comparing(Objective::getScore).reversed()).collect(Collectors.toList());

            for (Objective objective : sortedObjectives) {
                coloursNeeded.addAll(bamboosToEatToComplete(objective));
            }

            if (coloursNeeded.isEmpty()) {
                coloursNeeded.add(board.getAllTiles().stream().filter(t -> t.getBambooSize() > 0).collect(Collectors.toList()).get(0).getColor());
            }
        }

        if (currentPathInGraph.isEmpty() && currentSteps.isEmpty()) { // If the path we are following is empty

            Path bestPath = new Path();
            bestPath.addStep(board.get(new Point(0,0)));

            bambooTilesGraph = buildBambooGraph(game);      // We compute the graph

            if (bambooTilesGraph.getNodes().size() > 1) {

                List<Path> pathsToRightColour = new ArrayList<>();

                Color targetColour = coloursNeeded.poll();  // MAKE SURE WE HAVE SOMETHING IN THERE

                Map[] dijkstra = new Map[0];

                dijkstra = bambooTilesGraph.shortestPath(pandaTile);

                Map<Tile, Integer> distances = dijkstra[1];
                Map<Tile, Tile> trace = dijkstra[0];

                int minDistanceYet = 3000;

                for (Tile node : bambooTilesGraph.getNodes()) {
                    // If we found a tile of the right colour which is not the one we are on
                    if (node == pandaTile) {
                        continue;
                    }
                    if ((!node.equals(pandaTile)) && node.getColor().equals(targetColour)) {

                        if (distances.get(node) == minDistanceYet) {  // If we found a path just as long as the others
                            pathsToRightColour.add(new Path(trace, node));  // we simply add it to the list
                        }

                        if (distances.get(node) < minDistanceYet) { // If we found a shorter path than the one we have already found
                            minDistanceYet = distances.get(node);   // we update the minimum distance
                            pathsToRightColour.clear();             // we remove the longer paths we have
                            pathsToRightColour.add(new Path(trace, node));  // we add the one we found
                            if (minDistanceYet <= 1) {              // if the path's length is 1, I.E. the shortest possible path
                                break;                              // we stop searching
                            }
                        }

                    }
                }


                int bestPathScore = 0;

                for (Path path : pathsToRightColour) {
                    int score = 0;
                    for (int value : path.getBambooYield().values()) {
                        score += value;
                    }

                    if (score > bestPathScore) {
                        bestPath = path;
                        bestPathScore = score;
                    }
                }
            }


            currentPathInGraph = bestPath;



        }

        if (currentSteps.isEmpty()) {   // If we don't have steps we remembered to follow
            /* If we are there, we already computed a path in the graph.
            As we don't have an intermediate step to execute, we know we are on one node of this graph.
            Therefor we can just check in the graph the weight of the edge between us and the target to know
            if we need to compute the shortest way to get to our next tile.
            */

            Tile nextTarget = currentPathInGraph.nextStep();
            // This should work, we just compute the shortest way to go to our goal, which might be in one step
            try {
                currentSteps = shortestPath(board, panda.getPosition(), nextTarget.getPosition());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return currentSteps.poll();
    }

    @Override
    protected Objective chooseObjectiveToValidate() {
        Objective objective = this.objectivesToValidate.get(0);
        this.objectivesToValidate.remove(0);
        return objective;
    }

    /*
    *****************************************
    *           Internal methods            *
    ****************************************/

    Deque<Point> shortestPath(Board board, Point start, Point goal) {

        Deque<Point> pointsToVisit = new ArrayDeque<>();

        Map<Point, Point> trace = new HashMap<>();

        trace.put(start, null);

        pointsToVisit.addLast(start);

        while(! pointsToVisit.isEmpty()) {
            Point current = pointsToVisit.poll();
            for (Point accessible : board.getAccessiblePositions(current)) {
                if (!trace.containsKey(accessible)) {
                    if (accessible.equals(goal)) {
                        trace.put(accessible, current);
                        return computePath(trace, accessible);
                    }
                    else {
                        trace.put(accessible, current);
                        pointsToVisit.addLast(accessible);
                    }
                }
            }
        }
        // If we didn't return anything we are in the case of a non connex component
        // shouldn't happen in a board
        return new ArrayDeque<>();
    }

    Map<Tile, Map<Tile, List<Tile>>> paths(Board board, List<Tile> starts) {
        /*
        Returns the list of steps to get from each tile to another
         */

        Map<Tile, Map<Tile, List<Tile>>> result = new HashMap<>();

        for (Tile start : starts) {
            Deque<Tile> pointsToVisit = new ArrayDeque<>();
            Map<Tile, Tile> trace = new HashMap<>();

            trace.put(start, null);

            pointsToVisit.addLast(start);

            while (!pointsToVisit.isEmpty()) {
                Tile current = pointsToVisit.poll();
                for (Tile accessible : board.getAccessiblePositions(current)) {
                    if (!trace.containsKey(accessible)) {   // If we didn't visit this vertex already
                        trace.put(accessible, current);     // we say we just did, remembering where we came from
                        pointsToVisit.addLast(accessible);  // and we remember we have to check where we can go from there
                    }
                }
            }

            result.put(start, new HashMap<>());

            for (Tile point : trace.keySet()) {
                result.get(start).put(point, computePath(trace, point));
            }


        }


        return result;
    }


    private Deque<Point> computePath(Map<Point, Point> trace, Point arrival) {
        Deque<Point> result = new ArrayDeque<>();

        Point current = arrival;

        while (trace.get(current) != null) {
            result.addFirst(current);
            current = trace.get(current);
        }

        return result;
    }

    private List<Tile> computePath(Map<Tile, Tile> trace, Tile arrival) {
        List<Tile> result = new ArrayList<>();

        Tile current = arrival;

        while (trace.get(current) != null) {
            result.add(current);
            current = trace.get(current);
        }

        return result;
    }

    protected Graph buildBambooGraph(Game game) {

        Board board = game.getBoard();

        List<Tile> bambooTiles = board.getAllTiles().stream()
                .filter(t -> t.getBambooSize() > 0)
                .collect(Collectors.toList());    // getting all the tiles with bamboo

        if (!bambooTiles.contains(board.get(game.getPanda().getPosition()))) {
            bambooTiles.add(board.get(game.getPanda().getPosition()));
        }

        Map<Tile, Map<Tile, List<Tile>>> paths = paths(board, bambooTiles);

        Graph graph = new Graph();

        for (int i = 0; i < bambooTiles.size(); i++) {
            for (int j = i+1; j < bambooTiles.size(); j++) {
                graph.addEdge(bambooTiles.get(i), bambooTiles.get(j), paths.get(bambooTiles.get(i)).get(bambooTiles.get(j)).size());
            }
        }

        graph.clean();

        return graph;
    }

    private List<Color> bamboosToEatToComplete(Objective objective) {
        /* COULDN'T THIS BE PART OF THE OBJECTIVE INSTEAD ? */
        /* Computes the difference between our stomach and the goal needed to complete the objective
         * Returns a list of colors : one for each bamboo we need to eat */

        Map<Color, Integer> goalStomach = objective.getPatternForCompleting();

        // We initialize a "diff" stomach, that will contain the difference between our stomach and the goal
        Map<Color, Integer> stomachsDiff = new EnumMap<>(Color.class);
        for (Color color : Color.values()) {
            stomachsDiff.put(color, 0);
        }

        goalStomach.forEach((key, value) -> {
            int difference;
            if ((difference = value - this.getStomach().get(key)) > 0) {
                // If we need to eat more bamboo
                stomachsDiff.put(key, difference); // We remember how many
            }
        });

        List<Color> bamboosToEat = new ArrayList<>();

        // For each color, we add as many copy as their value in the list of bamboos to eat
        stomachsDiff.forEach((key, value) ->
                bamboosToEat.addAll(Collections.nCopies(value, key))
        );

        return bamboosToEat;
    }

    protected Path mostUsefulPath(Path a, Path b, Map<Color, Integer> goal) {
        Map<Color, Integer> needed = new EnumMap<>(Color.class);
        for (Color c : Color.values()) {
            needed.put(c, Math.max(goal.get(c) - getStomach().get(c), 0)); //   0 if we don't need any more bamboo of color c
        }

        boolean aCanWork = true;
        boolean bCanWork = true;

        for (Color c : Color.values()) {    // we check if both path can fill the goal
            aCanWork = aCanWork && needed.get(c) >= a.getBambooYield().get(c);
            bCanWork = bCanWork && needed.get(c) >= b.getBambooYield().get(c);
        }

        if (aCanWork && !bCanWork) {    //  if only one of them works, return the one that works
            return a;
        } else if (bCanWork && !aCanWork) {
            return b;
        } else {                        // if both or none work, return the shortest one
            if (a.length() < b.length()) {
                return a;
            } else {
                return b;
            }
        }
    }

    /*
    *********************************************************************************
    * Methods unused by this bot, we are working on a reduced version of the game   *
    *********************************************************************************
     */

    @Override
    public boolean keepIrrigation() {
        return false;
    }

    @Override
    protected boolean putDownIrrigation(Game game) {
        return false;
    }

    @Override
    protected Point whereToPutDownTile(Game game, Tile t) {
        return null;
    }

    @Override
    protected Tile chooseTile(Game game) {
        return null;
    }

    @Override
    protected Point whereToMoveGardener(Game game, List<Point> available) {
        return null;
    }

    @Override
    public void tileImprovement(Game game, List<Tile> improvableTiles) {

    }
}
