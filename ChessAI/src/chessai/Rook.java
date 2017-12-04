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
public class Rook extends Piece {
    public Rook(boolean c, int x, int y, String n) {
	super(c, x, y, n);
    }

    @Override
    public Boolean isValidMove(int[] newPos, BoardSquare[][] bs) {
        if (!(this.x == newPos[0] || this.y == newPos[1])){
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
            for (int i=this.x+changeInX;i!=newPos[0];i+=changeInX){
                if (bs[i][this.y].hasPiece){
                    return false;
                }
            }
        }
        if(bs[newPos[0]][newPos[1]].hasPiece && bs[newPos[0]][newPos[1]].piece.colour == this.colour){
            return false;
        }
        return true;
    }

    @Override
    public int[][] generateMoves(BoardSquare[][] bs) {
	int[][] moves=new int[16][];//4 is the max number of moves a knight can take
	int[] possibleMove=new int[2];
	int validCount =0;
	int[][] returnMove;
	for(int x=0;x<bs.length;x++){
	    for(int y=0;y<bs[0].length;y++){
		possibleMove[0]=x;
		possibleMove[1]=y;
		if(isValidMove(possibleMove,bs)){
		    moves[validCount]=possibleMove.clone();
		    validCount++;
		}
	    }
	}
	returnMove =new int[validCount][];//Create an array of the correct size to store the moves
	System.arraycopy(moves, 0,returnMove , 0, validCount);//Copy the valid moves to an array of the correct size
	return moves;
    }
    
}
