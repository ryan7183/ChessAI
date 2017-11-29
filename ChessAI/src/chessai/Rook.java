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
        else {
            if (this.x > newPos[1]){
                for (int i=this.x;i!=newPos[1];i--){
                    if (bs[i][this.y].hasPiece){
                        return false;
                    }
                }
            }
            else if (this.x < newPos[1]) {
                for (int i=this.x;i!=newPos[1];i++){
                    if (bs[i][this.y].hasPiece){
                        return false;
                    }
                }
            }
            else if (this.y > newPos[0]) {
                for (int i=this.y;i!=newPos[0];i--){
                    if (bs[this.x][i].hasPiece){
                        return false;
                    }
                }
            }
            else if (this.y < newPos[0]) {
                for (int i=this.y;i!=newPos[0];i++){
                    if (bs[this.x][i].hasPiece){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
}
