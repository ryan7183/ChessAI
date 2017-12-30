/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessai;

import javafx.stage.Stage;

/**
 *
 * @author RyanS
 */
public class Passer {
    volatile boolean boardUpdate;
    volatile BoardSquare[][] board;
    volatile double mouseX;
    volatile double mouseY;
    volatile boolean mouseClicked;
    volatile boolean playerSelect;
    volatile boolean playerDone;
    volatile boolean request;
    volatile boolean mouseUpdate;
    volatile boolean drawSelection;
    volatile boolean cancelSelection;
    volatile boolean isInvalid;
    volatile boolean whiteCheckmate;
    volatile boolean blackCheckmate;
    volatile boolean stalemate;
    volatile boolean promotion;
    volatile boolean newBoardAvailable;
    volatile BoardSquare[][] newBoard;
    volatile String newPiece;
    volatile int[] selectionPosition;
    Passer(){
	cancelSelection = false;
	playerSelect = false;
	playerDone = false;
	boardUpdate =true;
	board = null;
	mouseX=0;
	mouseY = 0;
	drawSelection=false;
        isInvalid = false;
        whiteCheckmate = false;
        blackCheckmate = false;
        stalemate = false;
        promotion = false;
        newPiece = "";
	selectionPosition = new int[2];
	newBoard = null;
	newBoardAvailable=false;
    }
    public void setSelectionPosition(int[] p){
	selectionPosition[0] =54+( p[0]*73);
	selectionPosition[1] =60+(p[1]*74);
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
    
    public void setNewBoard(BoardSquare[][] bs){
	this.newBoard=bs;
	board=bs;
	boardUpdate=true;
	newBoardAvailable = true;
    }
}
