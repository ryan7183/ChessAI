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
    boolean boardUpdate;
    BoardSquare[][] board;
    double mouseX;
    double mouseY;
    boolean mouseClicked;
    boolean playerSelect;
    boolean playerDone;
    boolean request ;
    boolean mouseUpdate;
    Passer(){
	playerSelect = false;
	playerDone = false;
	boardUpdate =true;
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
