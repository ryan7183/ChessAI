/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessai;

/**
 *
 * @author Ryan and Parm
 */
public class Knight extends Piece{
    public Knight(boolean c, int x, int y, String n) {
	super(c, x, y, n);
    }

    @Override
    public Boolean isValidMove(int[] newPos, BoardSquare[][] bs) {
        int changeInX = Math.abs(this.x-newPos[0]);
        int changeInY = Math.abs(this.y-newPos[1]);
        if(!((changeInX==2 && changeInY==1)||(changeInX==1 && changeInY==2))){
            return false;
        }
        if(bs[newPos[0]][newPos[1]].hasPiece && bs[newPos[0]][newPos[1]].piece.colour == this.colour){
            return false;
        }
        return true;
    }

    @Override
    public int[][] ganerateMoves(BoardSquare[][] bs) {
	int[][] moves=new int[4][];//4 is the max number of moves a knight can take
	int[] possibleMove=new int[2];
	int validCount =0;
	int[][] returnMove;
	for(int x=0;x<bs.length;x++){
	    for(int y=0;y<bs[0].length;y++){
		possibleMove[0]=x;
		possibleMove[1]=y;
		if(isValidMove(possibleMove,bs)){
		    moves[validCount]=possibleMove;
		    validCount++;
		}
	    }
	}
	returnMove =new int[validCount][];//Create an array of the correct size to store the moves
	System.arraycopy(moves, 0,returnMove , 0, validCount);//Copy the valid moves to an array of the correct size
	return moves;
    }
    
}
