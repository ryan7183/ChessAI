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
public class Piece {
    boolean colour;//false black, true white
    int x,y;//Coordinates
    public String textRepresentation;
    Piece(boolean c,int x, int y, String n){
	colour = c;
	this.x=x;
	this.y=y;
        this.textRepresentation = n;
    }
}
