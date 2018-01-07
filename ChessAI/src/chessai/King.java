package chessai;

import java.util.ArrayList;

/**
 *This class creates a new king piece and also generates possible moves for the
 * king as well as checking if the move selected is valid for the king.
 * @author Ryan and Parm
 */
public class King extends Piece{
    
    public King(boolean c, int x, int y, String n) {
	super(c, x, y, n);
        this.castleKingSide = false;
        this.castleQueenSide = false;
    }
    
    public King(King k){
	super(k.colour,k.x,k.y,k.textRepresentation);
	this.castleKingSide=k.castleKingSide;
	this.castleQueenSide=k.castleQueenSide;
    }
    
    /**
     * Checks if the move that was chosen for the king in valid or not. Also
     * checks to see if castling is possible for the king. If castling is 
     * possible, either castleKingSide or castleQueenSide will be set to true
     * depending on which side castling is possible.
     * @param newPos The position that the king will move to
     * @param bs The chess board.
     * @param moveList A list of moves that have been made.
     * @return 
     */
    @Override
    public Boolean isValidMove(int[] newPos, BoardSquare[][] bs, ArrayList<Piece> moveList) {
        int changeInX = Math.abs(this.x-newPos[0]);
        int changeInY = Math.abs(this.y-newPos[1]);
        //Returns false if the new x position is out of bounds
        if(newPos[0] > 7 || newPos[0] < 0){
            return false;
        }
        //Returns false if the new y position is out of bounds
        if(newPos[1] > 7 || newPos[1] < 0){
            return false;
        }
        //Returns false if the change in the y position is greater than 1
        if(changeInY>1){
            return false;
        }
        //Returns false if the change in the x position is greater than 2
        if(changeInX>2){
            return false;
        }
        else{
            //Checks to see if castling is possible for the king
            if(changeInX == 2 && !this.hasMoved){
                //Checks to see if castling is possible on the queen side
                if(newPos[0] < this.x){
                    if(bs[0][this.y].hasPiece && (bs[0][this.y].piece.textRepresentation.equals("R") || bs[0][this.y].piece.textRepresentation.equals("r")) && !bs[0][this.y].piece.hasMoved){
                        for(int i=1; i<this.x;i++){
                            //Returns false if there is a piece between the rook and king
                            if(bs[i][this.y].hasPiece){
                                return false;
                            }
                            int[] loc = new int [2];
                            loc[0] = i;
                            loc[1] = this.y;
                            //For loop checks if the king will be attacked. Will return false if it is
                            for(int x=0; x<8; x++){
                                for(int y=0; y<8; y++){
                                    if(bs[x][y].hasPiece && bs[x][y].piece.colour != this.colour){
                                        if(bs[x][y].piece.isValidMove(loc, bs, moveList)){
                                            return false;
                                        }
                                    }
                                }
                            }
                        }
                        this.castleQueenSide = true;
                    }
                    else{
                        return false;
                    }
                }
                //Checks to see if castling is possible on the king side
                else if(newPos[0] > this.x){
                    if(bs[7][this.y].hasPiece && (bs[7][this.y].piece.textRepresentation.equals("R") || bs[7][this.y].piece.textRepresentation.equals("r")) && !bs[7][this.y].piece.hasMoved){
                        //Checks if there is a piece between the rook and the king
                        for(int i=6; i<this.x;i--){
                            if(bs[i][this.y].hasPiece){
                                return false;
                            }
                            int[] loc = new int [2];
                            loc[0] = i;
                            loc[1] = this.y;
                            //Checks if the king can be attacked
                            for(int x=0; x<8; x++){
                                for(int y=0; y<8; y++){
                                    if(bs[x][y].hasPiece && bs[x][y].piece.colour != this.colour){
                                        if(bs[x][y].piece.isValidMove(loc, bs, moveList)){
                                            return false;
                                        }
                                    }
                                }
                            }
                        }
                        this.castleKingSide = true;
                    }
                    else{
                        return false;
                    }
                }
            }
            //Returns false if change in the x position is 2.
            else if (changeInX == 2){
                return false;
            }
        }
        //Returns false if the new position has a piece with the same colour
        if(bs[newPos[0]][newPos[1]].hasPiece && bs[newPos[0]][newPos[1]].piece.colour == this.colour){
            return false;
        }
	
	/*if(bs[newPos[0]][newPos[1]].hasPiece&&(bs[newPos[0]][newPos[1]].piece.textRepresentation.equals("K")||bs[newPos[0]][newPos[1]].piece.textRepresentation.equals("k"))){
	   return false; 
	}*/
        return true;
    }

    /**
     * Generates all the possible moves for the king at its current position. 
     * Stores all the possible moves in a 2d array
     * @param bs The chess board with all the pieces
     * @param moveList A list of moves that have been made
     * @return A 2d array with all the possible moves for the king 
     */
    @Override
    public int[][] generateMoves(BoardSquare[][] bs, ArrayList<Piece> moveList) {
	int[][] moves=new int[16][];
	int[] possibleMove=new int[2];
	int validCount =0;
	int[][] returnMove;
	for(int y=this.x-1;y<this.x+2;y++){
	    for(int x=this.y-1;x<this.y+2;x++){
		possibleMove[0]=x;
		possibleMove[1]=y;
		if(isValidMove(possibleMove,bs, moveList)){
		    moves[validCount]=possibleMove.clone();
		    validCount++;
		}
	    }
	}
	returnMove =new int[validCount][];//Create an array of the correct size to store the moves
	System.arraycopy(moves, 0,returnMove , 0, validCount);//Copy the valid moves to an array of the correct size
	return returnMove;
    }
    
     
}
