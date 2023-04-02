# Description
A hobby Chess Application, where the goal is to set up a primitive chess engine based on a [minimax](https://en.wikipedia.org/wiki/Minimax) algorithm (with [alpha-beta pruning](https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning) while practising OO design patterns and principles.

Screenshot the app:

 <img src="https://user-images.githubusercontent.com/78796219/229346830-1dcce46e-c68b-4199-9d35-6dcb050e1de1.png">

On the right side of the board, there are the piece- and side-level evaluation aspects, some of which are penalties. On the left side, an evaluation bar is visible, describing what the engine (here with the black side) thinks of the position (unlike [regular/popular evaluation methods](https://en.wikipedia.org/wiki/Chess_piece_relative_value), a pawn is worth roughly 100 points instead of 1)

# Engine
The engine uses many evaluation aspects which try to grasp the (relative) strengths of different pieces or sides (e.g. how well-positioned the individual pieces are or how broken up the pawn structure is). The given position is then evaluated with a linear combination of these evaluation aspects, similar to [this idea](https://en.wikipedia.org/wiki/Evaluation_function). The weights are set empirically for now, it would be interesting to create a framework for finding the best weights with, for example, a genetic algorithm.

## Currently implemented evaluation aspects for different entities
### All Pieces
- Relative Value of the given piece - based on the [general consensus of piece values](https://en.wikipedia.org/wiki/Chess_piece_relative_value)
- Position Heuristic of the given piece - described [here](https://www.chessprogramming.org/Simplified_Evaluation_Function)
- Undefended Piece - whether this piece is protected by other pieces
### King 
- Castling Right - whether the King still can castle
- King Tropism - how far the enemy pieces are from the king 
- Pawn Shield - how protected the king is with pawns in front 
### Knight
- Knight Mobility - how many squares the knight has to move
- Pawns In Game - how many pawns there are alive in the game (<u><i>The higher the better</i></u>)
### Rook
- Enemy 7th Rank positioning - gives the rook bonus if it is positioned on the 7th rank, controlling the enemy backline movement 
- Pawns on the same file - how many pawns there are on the same file as the rook 
- Pawns in Game  - how many pawns there are alive in the game (<u><i>The lower the better</i></u>)
### Individual Sides
- Bishop Pair - whether the side has the bishop pair
- Color weakness - how unbalanced the pieces are on colors (e.g. only having the light-colored bishop with all pawns on light tiles is a disadvantage) 
- Connected Rooks - whether the rooks are protecting each other
- Pawn islands - how broken up the pawn structure is
- Pawn mobility - how many pawn moves can a side make
- Pins - how many pinned pieces the side has (<u><i>The lower the better</i></u>)




