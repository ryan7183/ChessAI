/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessai;

/**
 *
 * @author Ryan
 */
public class King extends Piece{
    
    public String textRepresentation;
    
    public King(boolean c, int x, int y) {
	super(c, x, y);
	textRepresentation = "K";
    }
    
}
