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
public class Pawn extends Piece {

    public Pawn(boolean c, int x, int y, String n) {
	super(c, x, y, n);
        this.pawnPromotion = false;
    }

    @Override
    public Boolean isValidMove(int[] newPos, BoardSquare[][] bs) {
        int changeInY;
        if(this.colour){
            changeInY = -1;
        }
        else{
            changeInY = 1;
        }
        if((this.x-1 >=0 && (bs[this.y+changeInY][this.x-1].hasPiece)) || (this.x+1 <=7 && (bs[this.y+changeInY][this.x+1].hasPiece))){
            if((newPos[1]==(this.y+changeInY) && newPos[0]==(this.x-1)) || (newPos[1]==(this.y+changeInY) && newPos[0]==(this.x+1))){
                if(bs[newPos[0]][newPos[1]].piece.colour == this.colour){
                    return false;
                }
                else{
                    return true;
                }
            }
        }
        else{
            if(this.x!=newPos[0]){
                return false;
            }
        }
            if(this.hasMoved){
                if(Math.abs(newPos[1]-this.y) != 1){
                    return false;
                }
                if(this.y+(-changeInY)==newPos[1]){
                    return false;
                }
                if(bs[newPos[0]][newPos[1]].hasPiece){
                    return false;
                }
            }
            else{
                if(!(Math.abs(newPos[1]-this.y) == 1 || Math.abs(newPos[1]-this.y) == 2)){
                    return false;
                }
                if(bs[newPos[0]][newPos[1]].hasPiece){
                    return false;
                }
            }
            if (newPos[1] == 0 || newPos[1] ==7){
                this.pawnPromotion = true;
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
