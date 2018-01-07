package chessai;

import java.util.ArrayList;

/**
 * This class creates a rook and will generate all the possible moves for the 
 * rook at its current position as well as check if the move selected it a 
 * valid move or not.
 * @author Ryan and Parm
 */
public class Rook extends Piece {
    public Rook(boolean c, int x, int y, String n) {
	super(c, x, y, n);
    }

    public Rook(Rook r){
	super(r.colour,r.x,r.y,r.textRepresentation);
    }
    
    /**
     * Checks if the move selected is a valid move or not
     * @param newPos Array of the new position for the rook
     * @param bs The Chess board with all the pieces
     * @param moveList List of all the moves that have been made
     * @return True if the move is a valid move or false if the move is not a 
     * valid move
     */
    @Override
    public Boolean isValidMove(int[] newPos, BoardSquare[][] bs, ArrayList<Piece> moveList) {
        //Returns false if either the new x position is not the same as the current x position or the new y posistion is not the same as the current y position
        if (!(this.x == newPos[0] || this.y == newPos[1])){
            return false;
        }
        //Returns false if the new position is the same as the current position
        if(this.x==newPos[0] && this.y==newPos[1]){
            return false;
        }
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
        //Returns false if there is a piece at the new position that has the same colour as the rook
        if(bs[newPos[0]][newPos[1]].hasPiece && bs[newPos[0]][newPos[1]].piece.colour == this.colour){
            return false;
        }
        return true;
    }

    /**
     * Generates all the possible moves for the rook at its current position. 
     * All possible moves are added into a 2d array.
     * @param bs The chess board with all the moves
     * @param moveList List of all the moves that have been made
     * @return A 2d array of all the possible moves for the rook
     */
    @Override
    public int[][] generateMoves(BoardSquare[][] bs, ArrayList<Piece> moveList) {
	int[][] moves=new int[16][];
	int[] possibleMove=new int[2];
	int validCount =0;
	int[][] returnMove;
	for(int y=0;y<bs.length;y++){
	    for(int x=0;x<bs[0].length;x++){
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
