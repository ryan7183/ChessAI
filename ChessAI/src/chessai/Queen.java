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
public class Queen extends Piece{
    public Queen(boolean c, int x, int y, String n) {
	super(c, x, y, n);
	
    }

    @Override
    public Boolean isValidMove(int[] newPos, BoardSquare[][] bs) {
        if (this.x == newPos[0] || this.y == newPos[1]){
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
        }
        else{
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
            for(int i=x+nextX; i!= newPos[0]; i+=nextX){
                if (bs[i][newY].hasPiece){
                    return false;
                }
                newY+=nextY;
            }
            if(bs[newPos[0]][newPos[1]].hasPiece && bs[newPos[0]][newPos[1]].piece.colour == this.colour){
                return false;
            }   
        }
        return true;
    }
    
}
