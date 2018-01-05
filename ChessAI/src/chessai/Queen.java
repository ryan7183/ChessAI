package chessai;

import java.util.ArrayList;

/**
 * This class creates a new queen and generates all the possible moves for the 
 * queen at its current location as well as check if the move selected is a 
 * valid move for the queen.
 * @author Ryan and Parm
 */
public class Queen extends Piece{
    public Queen(boolean c, int x, int y, String n) {
	super(c, x, y, n);
	
    }

    public Queen(Queen q){
	super(q.colour,q.x,q.y,	q.textRepresentation);
    }
    
    /**
     * Checks to see if the move selected for the queen is a valid move or not
     * @param newPos Array of the new position for the queen
     * @param bs The chess board with all the pieces
     * @param moveList List of all the moves that have been made
     * @return True if the move is valid or false if the move is not valid
     */
    @Override
    public Boolean isValidMove(int[] newPos, BoardSquare[][] bs, ArrayList<Piece> moveList) {
        //Returns false if the new position equals the current position
        if(this.x==newPos[0] && this.y==newPos[1]){
            return false;
        }
        if (this.x == newPos[0] || this.y == newPos[1]){
            if(this.x==newPos[0]){
                int changeInY;
                if(this.y>newPos[1]){
                    changeInY = -1;
                }
                else{
                    changeInY = 1;
                }
                //Returns false if there is a piece between the current position and the new position
                for (int i=this.y+changeInY;i!=newPos[1];i+=changeInY){
                    if (bs[this.x][i].hasPiece){
                        return false;
                    }
                }
            }
            else{
                int changeInX;
                if(this.x>newPos[0]){
                    changeInX = -1;
                }
                else{
                    changeInX = 1;
                }
                //Returns false if there is a piece between the current position and the new position
                for (int i=this.x+changeInX;i!=newPos[0];i+=changeInX){
                    if (bs[i][this.y].hasPiece){
                        return false;
                    }
                }
            }
            //Returns false if there is a piece at the new position that is the same colour as the queen
            if(bs[newPos[0]][newPos[1]].hasPiece && bs[newPos[0]][newPos[1]].piece.colour == this.colour){
                return false;
            }
        }
        else{
            //returns false if the change in x does not equal the change in y
            if(Math.abs(this.x-newPos[0])!=Math.abs(this.y-newPos[1])){
                return false;
            }
            int addOne = 1;
            int subOne = -1;
            int nextX;
            int nextY;
            if (this.x>newPos[0]){
                nextX=subOne;
            }
            else{
                nextX=addOne;
            }
            if (this.y>newPos[1]){
                nextY=subOne;
            }
            else{
                nextY=addOne;
            }
            int newY = this.y+nextY;
            //Returns false if there is a piece between the current position and the new position
            for(int i=x+nextX; i!= newPos[0]; i+=nextX){
                if (bs[i][newY].hasPiece){
                    return false;
                }
                newY+=nextY;
            }
            ////Returns false if there is a piece at the new position that is the same colour as the queen
            if(bs[newPos[0]][newPos[1]].hasPiece && bs[newPos[0]][newPos[1]].piece.colour == this.colour){
                return false;
            }   
        }
	/*if(bs[newPos[0]][newPos[1]].hasPiece&&(bs[newPos[0]][newPos[1]].piece.textRepresentation.equals("K")||bs[newPos[0]][newPos[1]].piece.textRepresentation.equals("k"))){
	   return false; 
	}*/
        return true;
    }

    /**
     * Generates all the possible moves for the queen at its current location.
     * All the possible moves are added to a 2d array.
     * @param bs The chess board with all the pieces
     * @param moveList List of all the moves that have been made
     * @return A 2d array of all the possible moves for the queen at its 
     * current location
     */
    @Override
    public int[][] generateMoves(BoardSquare[][] bs, ArrayList<Piece> moveList) {
	int[][] moves=new int[48][];
	int[] possibleMove=new int[2];
	int validCount =0;
	int[][] returnMove;
	for(int x=0;x<bs.length;x++){
	    for(int y=0;y<bs[0].length;y++){
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
