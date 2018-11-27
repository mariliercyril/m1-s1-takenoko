# TER specifications
Loïc Germerie Guizouarn, Theo Qui

## First Bot -- <i>BamBot</i>

### What it does
- Only tries to complete panda objectives
- Can only move the panda
- Computes the shortest path that allows him to complete as many objectives as possible
- Follows that path
- The bot always has the max number of objectives in its hand

### How the game engine will be changed
- The tiles are all placed randomly at the beginning of the game
- A given number of bamboo chunks is added on a fixed number of tiles of each colour 
- We make sure building the board that all the objectives are completable
- Each turn, the players pick as many objectives as needed to complete their hand
- The game ends when a given score is reached

### Why these changes are necessary
- They simplify the game while keeping it complex enough to make the AI interesting
- Not needing to decide where to places tiles or where to irrigate allows us to focus on the objective completion
- The game is reduced to a path finding problem

### Purpose of this bot
- Implementation of an efficient path-finding algorithm

## Second Bot

### What it does
- Two players against each other
- They know each others' objective cards
- Tries to complete its panda and gardener objectives but also to keep the opponent from completing theirs
- Move the gardener to grow the bamboos they need
- Move the panda to eat the bamboo

### How the game engine will be changed
- The tiles are all placed at the beginning of the game
- All the tiles are irrigated
- Bamboos do <b>not</b> grow automatically
- Each turn, the players pick as many objectives as needed to complete their hand
- The game ends when the first player reaches the maximum score

### Purpose of this bot
- Implementation of a min-max algorithm
