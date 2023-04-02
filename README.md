## Description
A Hobby Chess Application, where the goal was to setup a primitive chess engine based on a [minimax](https://en.wikipedia.org/wiki/Minimax) algorithm (with [alpha-beta pruning](https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning) while practising OO design patterns and principles.


## Engine
The engine uses many [evaluation aspects](/src/ai/evaluationaspects) which try to grasp the (relative) strenghts of different pieces or sides (e.g. how well positioned the individual pieces are or how broken up the pawn structure is). The given position is then evaluated with a linear combination of these evaluation aspects, similar to [this idea](https://en.wikipedia.org/wiki/Evaluation_function). The weights are set empirically for now, it would be interesting to create a framework for finding the best weights with, for example, a genetic algorithm.

### Currently implemented evaluation aspects for different entities
(<img src="https://user-images.githubusercontent.com/78796219/229349845-f2965819-1296-4c55-81bb-51b29d8ad4ea.png"  width="20" height="20"> = the higher the better, <img src="https://user-images.githubusercontent.com/78796219/229348117-f77c56ce-4d57-4fe0-b1ce-d8fc19edaf05.png"  width="20" height="20"> = the lower the better )
#### All Pieces
+ Relative Value of the given piece - based on the [general concensus of piece values](https://en.wikipedia.org/wiki/Chess_piece_relative_value) <img src="https://user-images.githubusercontent.com/78796219/229349845-f2965819-1296-4c55-81bb-51b29d8ad4ea.png"  width="20" height="20">
+ Position Heuristic of the given piece - edscribed [here](https://www.chessprogramming.org/Simplified_Evaluation_Function) <img src="https://user-images.githubusercontent.com/78796219/229349845-f2965819-1296-4c55-81bb-51b29d8ad4ea.png"  width="20" height="20">
+ Undefended Piece - whether this piece is protected by other pieces  <img src="https://user-images.githubusercontent.com/78796219/229349845-f2965819-1296-4c55-81bb-51b29d8ad4ea.png"  width="20" height="20">
#### King 
> Castling Right - whether the King still has the ability to castle <img src="https://user-images.githubusercontent.com/78796219/229349845-f2965819-1296-4c55-81bb-51b29d8ad4ea.png"  width="20" height="20">
- King Tropism - how far the enemy pieces are to the king <img src="https://user-images.githubusercontent.com/78796219/229349845-f2965819-1296-4c55-81bb-51b29d8ad4ea.png"  width="20" height="20">
- Pawn Shield - how protected the king is with pawns in front <img src="https://user-images.githubusercontent.com/78796219/229349845-f2965819-1296-4c55-81bb-51b29d8ad4ea.png"  width="20" height="20">
#### Knight
- Knight Mobility - how many squares the knight has to move <img src="https://user-images.githubusercontent.com/78796219/229349845-f2965819-1296-4c55-81bb-51b29d8ad4ea.png"  width="20" height="20">
- Pawns In Game - how many pawns there are alive in the game <img src="https://user-images.githubusercontent.com/78796219/229349845-f2965819-1296-4c55-81bb-51b29d8ad4ea.png"  width="20" height="20">
#### Rook
- Enemy 7th Rank positioning - gives the rook bonus if it is positioned on the 7th rank, controlling the enemy backline movement <img src="https://user-images.githubusercontent.com/78796219/229349845-f2965819-1296-4c55-81bb-51b29d8ad4ea.png"  width="20" height="20">
- Pawns on the same file - how many pawns there are on the same file as the rook <img src="https://user-images.githubusercontent.com/78796219/229348117-f77c56ce-4d57-4fe0-b1ce-d8fc19edaf05.png"  width="20" height="20">
- Pawns in Game  - how many pawns there are alive in the game <img src="https://user-images.githubusercontent.com/78796219/229348117-f77c56ce-4d57-4fe0-b1ce-d8fc19edaf05.png"  width="20" height="20">
![image](https://user-images.githubusercontent.com/78796219/229346830-1dcce46e-c68b-4199-9d35-6dcb050e1de1.png)
