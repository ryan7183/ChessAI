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
public class Rook extends Piece {
    public String textRepresentation;
    public Rook(boolean c, int x, int y) {
	super(c, x, y);
	textRepresentation = "R";
    }
    
}
