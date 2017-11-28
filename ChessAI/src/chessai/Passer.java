/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessai;

/**
 *
 * @author RyanS
 */
public class Passer {
    Boolean update;
    BoardSquare[][] board;
    double mouseX;
    double mouseY;
    boolean playerSelect;
    boolean playerDone;
    Passer(){
	playerSelect = false;
	playerDone = false;
	update =false;
	board = null;
	mouseX=0;
	mouseY = 0;
    }
    
    public void setBoard(BoardSquare[][] b){
	board = b;
    }
    
    public void playerSelectSet(Boolean b){
	playerSelect = b;
    }
    
    public void playerDoneSet(Boolean b){
	playerDone = b;
    }
}
