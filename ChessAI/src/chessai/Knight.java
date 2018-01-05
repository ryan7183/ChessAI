package chessai;

import java.util.ArrayList;

/**
 * This class creates a new knight and will generate all possible moves for 
 * the knight as well as check is the move selected is a valid move for the 
 * knight or not.
 * @author Ryan and Parm
 */
public class Knight extends Piece{
    public Knight(boolean c, int x, int y, String n) {
	super(c, x, y, n);
    }
    
    public Knight(Knight k){
	super(k.colour,k.x,k.y,k.textRepresentation);
	
    }

    /**
     * Checks to see if the move selected for the knight is valid or not.
     * @param newPos Array of the new x and y position
     * @param bs The chess board with all the pieces
     * @param moveList A list of moves that have been made
     * @return True if the selected move is valid or false if the move is not
     * valid.
     */
    @Override
    public Boolean isValidMove(int[] newPos, BoardSquare[][] bs, ArrayList<Piece> moveList) {
        int changeInX = Math.abs(this.x-newPos[0]);
        int changeInY = Math.abs(this.y-newPos[1]);
        //Returns false if the new x position is out of bounds
        if(newPos[0]<0||newPos[0]>7){
            return false;
        }
        //Returns false if the new y position is out of bounds
        if(newPos[1]<0||newPos[1]>7){
            return false;
        }
        /**
         * Returns false if the change in x does not equal 2 and if change in y
         * does not equal 1 or if change in x does not equal 1 and if change in
         * y does not equal 2.
         */
        if(!((changeInX==2 && changeInY==1)||(changeInX==1 && changeInY==2))){
            return false;
        }
        //Returns false if there is a piece with the same colour as the knight on the new position selected
        if(bs[newPos[0]][newPos[1]].hasPiece && bs[newPos[0]][newPos[1]].piece.colour == this.colour){
            return false;
        }
	/*if(bs[newPos[0]][newPos[1]].hasPiece&&(bs[newPos[0]][newPos[1]].piece.textRepresentation.equals("K")||bs[newPos[0]][newPos[1]].piece.textRepresentation.equals("k"))){
	   return false; 
	}*/
        return true;
    }

    /**
     * Generates all the possible moves for the knight at its current position.
     * All the possible moves for the knight are stored in a 2d array
     * @param bs The chess board with all the chess pieces
     * @param moveList List of all the moves that have been made
     * @return A 2d array with all the possible moves for the knight
     */
    @Override
    public int[][] generateMoves(BoardSquare[][] bs, ArrayList<Piece> moveList) {
	int[][] moves=new int[16][];
	int[] possibleMove=new int[2];
	int validCount =0;
	int[][] returnMove;
	for(int x=this.x-2;x<this.x+3;x++){
	    for(int y=this.y-2;y<this.y+3;y++){
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
