/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessai;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Ryan and Parm
 */
public class Human extends Player {
    Passer passer;
    Human(Passer p, boolean c){
	this.colour = c;
	passer=p;
    }

    @Override
    int[] requestPiece() {
	passer.mouseClicked=false;
	while(!passer.mouseClicked){
	    try {
		Thread.sleep(1);
	    } catch (InterruptedException ex) {
		Logger.getLogger(Human.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	int x = (int)((passer.mouseX-54)/73);
	int y = (int)((passer.mouseY-60)/74);
	int[] coords = new int[2];
	coords[0]=x;
	coords[1]=y;
	return coords;
    }

    boolean getColour(){
	return colour;
    }
    
    @Override
    BoardSquare[][] requestMove(int[] piece,BoardSquare[][] bs) {
	passer.mouseClicked = false;
	while(!passer.mouseClicked){
	    try {
		Thread.sleep(1);
	    } catch (InterruptedException ex) {
		Logger.getLogger(Human.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	int x = (int)((passer.mouseX-54)/73);
	int y = (int)((passer.mouseY-60)/74);
        int[] newPos = new int[2];
        newPos[0] = y;
        newPos[1] = x;
        while(!bs[piece[1]][piece[0]].piece.isValidMove(newPos, bs)){
            requestMove(piece,bs);
        }
	bs[y][x].piece = bs[piece[1]][piece[0]].piece;
	bs[piece[1]][piece[0]].hasPiece=false;
	bs[y][x].hasPiece=true;
	return bs;
    }
    
    boolean validMove(int x,int y){
	return true;
    }

    @Override
    boolean validPiece() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
