# TER specifications
Loic Germerie--Guizouarn, Theo Qui

## First Bot -- <i>BamBot</i>

### What it does
- Only tries to complete panda objectives
- Can only move the panda
- Computes the shortest path that allows him to complete as many objectives as possible
- Following that path
- Repeating as long as he has objectives left to complete

### How the game engine will be changed
- The tiles are all placed randomly at the beggining of the game
- All the tiles are irrigated
- Every tile has a bamboo shoot of size 1 that doesn't grow
- Each turn, the player picks an objective and discards every objective that isn't completable
- The game ends when there is no more completable objective

### Why these changes are necessary
- They simplify the game while keeping it complex enough to make the AI interesting
- Not needing to decide where to places tiles or where to irrigate allows us to focus on the objective completion

### Purpose of this bot
- Being able to complete its objectives in as few turns as possible 

## Second Bot

### What it does
- Two players against each other
- They know each others' objective cards
- Tries to complete its panda objectives but also to keep the opponent from completing theirs
- Move the gardener to grow the bamboos they need

### How the game engine will be changed
- The tiles are all placed randomly at the beginning of the game
- All the tiles are irrigated
- Bamboos do <b>not</b> grow automatically
- Each turn, the players pick an objective card
- The game ends when there is no objective card left in the deck
