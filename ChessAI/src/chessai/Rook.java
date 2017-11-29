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
        if (!(this.x == newPos[1] || this.y == newPos[0])){
            return false;
        }
        if(this.x==newPos[1]){
            int changeInY;
            if(this.y>newPos[0]){
                changeInY = -1;
            }
            else{
                changeInY = 1;
            }
            for (int i=this.y+changeInY;i!=newPos[0];i+=changeInY){
                if (bs[i][this.x].hasPiece){
                    return false;
                }
            }
        }
        else{
            int changeInX;
            if(this.x>newPos[1]){
                changeInX = -1;
            }
            else{
                changeInX = 1;
            }
            for (int i=this.x+changeInX;i!=newPos[1];i+=changeInX){
                if (bs[this.y][i].hasPiece){
                    return false;
                }
            }
        }
        if(bs[newPos[0]][newPos[1]].hasPiece && bs[newPos[0]][newPos[1]].piece.colour == this.colour){
            return false;
        }
        return true;
    }
    
}
