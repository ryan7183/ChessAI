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
    boolean boardUpdate;
    BoardSquare[][] board;
    double mouseX;
    double mouseY;
    boolean mouseClicked;
    boolean playerSelect;
    boolean playerDone;
    boolean request;
    boolean mouseUpdate;
    boolean drawSelection;
    boolean cancelSelection;
    boolean isInvalid;
    boolean whiteCheckmate;
    boolean blackCheckmate;
    boolean stalemate;
    int[] selectionPosition;
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
	selectionPosition = new int[2];
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
}
