package chessai;

import java.util.ArrayList;

/**
 *This class will create a bishop piece and will also generate possible moves
 * for the bishop as well as check if the move selected is valid.
 * @author Ryan and Parm
 */
public class Bishop extends Piece{
    public Bishop(boolean c, int x, int y, String n) {
	super(c, x, y, n);
    }

    public Bishop(Bishop b){
	super(b.colour,b.x,b.y,b.textRepresentation);
    }
    
    /**
     * Checks to see if the move chosen for the bishop is a valid move or not
     * @param newPos Where the bishop would be moved if the move is valid 
     * @param bs The chess board
     * @param moveList The list of moves that have been completed
     * @return Returns true if the move is valid. Returns false if the move is 
     * not valid.
     */
    @Override
    public Boolean isValidMove(int[] newPos, BoardSquare[][] bs, ArrayList<Piece> moveList) {
        //Returns false if the new position is the same as the current possition
        if(this.x == newPos[0] || this.y == newPos[1]){
            return false;
        }
        //Returns false if the change in the x position does not match the change in the y position
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
        //Returns false if there is a piece in between the current postion and the new position 
        for(int i=x+nextX; i!= newPos[0]; i+=nextX){
            if (bs[i][newY].hasPiece){
                return false;
            }
            newY+=nextY;
        }
        //Returns false if a piece of the same colour is at the new position
        if(bs[newPos[0]][newPos[1]].hasPiece && bs[newPos[0]][newPos[1]].piece.colour == this.colour){
            return false;
        }
        return true;
    }

    /**
     * Generates all the possible moves for the piece and stores them in an array
     * @param bs The chess board
     * @param moveList list of all the moves made
     * @return A 2d array that stores all the possible moves of the piece
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
