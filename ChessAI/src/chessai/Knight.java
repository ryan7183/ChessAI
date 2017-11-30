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
        int changeInX = Math.abs(this.x-newPos[1]);
        int changeInY = Math.abs(this.y-newPos[0]);
        if(!((changeInX==2 && changeInY==1)||(changeInX==1 && changeInY==2))){
            return false;
        }
        if(bs[newPos[0]][newPos[1]].hasPiece && bs[newPos[0]][newPos[1]].piece.colour == this.colour){
            return false;
        }
        return true;
    }
    
}
