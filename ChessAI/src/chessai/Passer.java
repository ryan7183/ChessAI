package chessai;

import javafx.stage.Stage;

/**
 *
 * @author RyanS
 * A class used to pass values between threads
 */
public class Passer {
    volatile boolean boardUpdate;//True if board visuals need to be updated
    volatile BoardSquare[][] board;//The current board state
    volatile double mouseX;//Mouse click x value
    volatile double mouseY;//Mouse click y value
    volatile boolean mouseClicked;//True if the mouse has been clicked
    volatile boolean playerSelect;//True if the player has selected a piece
    volatile boolean drawSelection;//True if the selection of a piece should be displayed
    volatile boolean cancelSelection;//True if the undo select button has been pressed
    volatile boolean whiteCheckmate;//True if white is in check mate
    volatile boolean blackCheckmate;//True if black is in check mate
    volatile boolean stalemate;//True if board is in stalemate
    volatile boolean promotion;
    volatile boolean newBoardAvailable;//True if a new board has been loaded
    volatile boolean isInvalid;
    volatile BoardSquare[][] newBoard;//The new board
    volatile String newPiece;
    volatile int[] selectionPosition;//The posiition of the selection box
    Passer(){
	cancelSelection = false;
	playerSelect = false;
	boardUpdate =true;
	board = null;
	mouseX=0;
	mouseY = 0;
	drawSelection=false;
        whiteCheckmate = false;
        blackCheckmate = false;
        stalemate = false;
        promotion = false;
	isInvalid = false;
        newPiece = "";
	selectionPosition = new int[2];
	newBoard = null;
	newBoardAvailable=false;
    }
    
    /**Sets the position for the selection box
     * @param p the position
     */
    public void setSelectionPosition(int[] p){
	selectionPosition[0] =54+( p[0]*73);
	selectionPosition[1] =60+(p[1]*74);
    }
    
    /**Sets the board state
     * @param b the new board state
     */
    public void setBoard(BoardSquare[][] b){
	board = b;
    }
    
    /**Sets if the player has selected a piece
     */
    public void playerSelectSet(Boolean b){
	playerSelect = b;
    }
    
    //Sets the new board
    public void setNewBoard(BoardSquare[][] bs){
	this.newBoard=bs;
	board=bs;
	boardUpdate=true;
	newBoardAvailable = true;
    }
}
