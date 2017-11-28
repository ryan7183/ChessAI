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
public class AIdriver extends Player{
    Passer passer;
    boolean colour;
    AIdriver(Passer p, boolean colour){
	this.colour = colour;
	passer=p;
    }
    

    @Override
    int[] requestPiece() {
	return null;
    }

    @Override
    Board requestMove(int[] piece) {
	return null;
    }
}
