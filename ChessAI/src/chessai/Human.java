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
public class Human extends Player {
    Passer passer;
    boolean colour;
    Human(Passer p, boolean colour){
	this.colour = colour;
	passer=p;
    }

    @Override
    int[] requestPiece() {
	passer.mouseClicked=false;
	while(!passer.mouseClicked){
	}
	int x = (int)((passer.mouseX-54)/73);
	int y = (int)((passer.mouseY-60)/74);
	int[] coords = new int[2];
	while(!validPiece(x,y)){
	    x = (int)(passer.mouseX/8);
	    y = (int)(passer.mouseY/8);
	}
	coords[0]=x;
	coords[1]=y;
	return coords;
    }

    boolean validPiece(int x,int y){
	
	return true;
    }
    
    @Override
    Board requestMove(int[] piece) {
	return null;
    }
    
    boolean validMove(int x,int y){
	return true;
    }
}
