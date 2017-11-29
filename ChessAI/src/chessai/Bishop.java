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
public class Bishop extends Piece{
    public Bishop(boolean c, int x, int y, String n) {
	super(c, x, y, n);
    }

    @Override
    public Boolean isValidMove(int[] newPos, BoardSquare[][] bs) {
        if(this.x == newPos[1] || this.y == newPos[0]){
            return false;
        }
        if(Math.abs(this.x-newPos[1])!=Math.abs(this.y-newPos[0])){
            return false;
        }
        int addOne = 1;
        int subOne = -1;
        int nextX;
        int nextY;
        if (this.x>newPos[1]){
            nextX=subOne;
        }
        else{
            nextX=addOne;
        }
        if (this.y>newPos[0]){
            nextY=subOne;
        }
        else{
            nextY=addOne;
        }
        int newY = this.y+nextY;
        for(int i=x+nextX; i!= newPos[1]; i+=nextX){
            if (bs[newY][i].hasPiece){
                return false;
            }
            newY+=nextY;
        }
        if(bs[newPos[0]][newPos[1]].hasPiece && bs[newPos[0]][newPos[1]].piece.colour == this.colour){
            return false;
        }
        return true;
    }
    
}
