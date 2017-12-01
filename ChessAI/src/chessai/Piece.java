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
public abstract class Piece {
    boolean colour;//false black, true white
    int x,y;//Coordinates
    public String textRepresentation;
    boolean hasMoved;
    boolean pawnPromotion;
    Piece(boolean c,int x, int y, String n){
	hasMoved=false;
	colour = c;
	this.x=x;
	this.y=y;
        this.textRepresentation = n;
    }
    
    public abstract Boolean isValidMove(int[] newPos, BoardSquare[][] bs);
}
