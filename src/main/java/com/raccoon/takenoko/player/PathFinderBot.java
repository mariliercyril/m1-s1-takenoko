package com.raccoon.takenoko.player;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.game.objective.Objective;
import com.raccoon.takenoko.game.objective.ObjectivePool;
import com.raccoon.takenoko.game.objective.ObjectiveType;
import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.tool.Constants;
import com.raccoon.takenoko.tool.ForbiddenActionException;
import com.raccoon.takenoko.tool.graphs.Edge;
import com.raccoon.takenoko.tool.graphs.Graph;
import com.raccoon.takenoko.tool.graphs.Path;

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

    public PathFinderBot() {
        super();
        this.objectivesToValidate = new ArrayList<>();
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
        // TODO : add the logic here, for now just a random Panda mover

        Map<Point, Boolean> visitedPositions = new HashMap<>();



        Collections.shuffle(available);
        return available.get(0);
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

    List<Point> shortestPath(Board board, Point start, Point goal) {

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
        return null;
    }

    Map<Tile, Map<Tile, List<Tile>>> paths(Board board, List<Tile> starts) {

        Map<Tile, Map<Tile, List<Tile>>> result = new HashMap<>();

        for (Tile start : starts) {
            Deque<Tile> pointsToVisit = new ArrayDeque<>();
            Map<Tile, Tile> trace = new HashMap<>();

            trace.put(start, null);

            pointsToVisit.addLast(start);

            while(! pointsToVisit.isEmpty()) {
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


    private List<Point> computePath(Map<Point, Point> trace, Point arrival) {
        List<Point> result = new ArrayList<>();

        Point current = arrival;

        while (trace.get(current) != null) {
            result.add(current);
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

        //graph.clean();

        return graph;
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
