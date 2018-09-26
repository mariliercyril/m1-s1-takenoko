# Takenoko releases


# 1.0.0 : First iteration
First draft of the game : minimal representation 
### What's been done
* Basic game engine handling tile placing
* A player that places tiles at random places
* Score increases by default when the player plays
* The game ends when a player is at 9 points
* Basic objective : returns that an objective is completed when 2 tiles are aligned

# SEP24 : Colors
Tiles can now have colors, and the player and the objectives are updated accordingly
### What's been done
* Added colors to tiles
* New objective : 3 tiles with the same color aligned
* Creation of a tile deck in which the player takes a tile
* Tile deck creation implied the player's process of choosing a tile among 3 tiles and putting the two others back under the deck

# SEP25 : Bamboos
We can now grow bamboos on tiles ! All the tiles are considered irrigated by default in this iteration.
As it was a small iteration, we also did a bit of refactoring.
### What's been done
* A bamboo grows on a tile when placed on the board
* The bamboo can grow (not used in this iteration)
* Make all tiles irrigated (and take the flag into account to grow a bamboo)
* [refact] Score is now computed based on objective completion
* [refact] Players now have an unique ID (int increasing in the order in which they were created)

# SEP26 : Gardener
We now have a gardener that can move on the board ! It grows a bamboo on the tiles next to it if the tile has the same color as the one he is on.
### What's been done
* The gardener can move in a straight line
* Where the gardener moves, a bamboo grows, as well as on the compatible neighbour tiles (color + irrigation).
* [refact] Players now can choose an indefinite amount of actions (some of which have a cost) and play them in indefinite order.