package chessai;

import java.util.logging.Level;
import java.util.logging.Logger;

/**The logic for a human player making a move
 *
 * @author Ryan and Parm
 */
public class Human extends Player {
    volatile Passer passer;//A class used to pass values between threads
    Human(Passer p, boolean c){
	this.colour = c;
	passer=p;
        this.isHuman = true;
    }
    
    /** Gets the piece to be moved from the player
     * @param bs the board state
     * @return the location of the chosen piece
     */
    @Override
    int[] requestPiece(BoardSquare[][] bs) {
	passer.mouseClicked=false;
	//Waits till the mouse is clicked
	while(!passer.mouseClicked){
	    //Aborts the move when a new board is loaded
	    if(passer.newBoardAvailable){
		return null;
	    }
	    try {
		Thread.sleep(1);
	    } catch (InterruptedException ex) {
		Logger.getLogger(Human.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	//Aborts the move when a new board is loaded
	if(passer.newBoardAvailable){
	    return null;
	}
	//Get the board location of mouse click
	int x = (int)((passer.mouseX-54)/73);
	int y = (int)((passer.mouseY-60)/74);
	int[] coords = new int[2];
	coords[0]=x;
	coords[1]=y;
	return coords;
    }

    //Get the colourt of this player
    boolean getColour(){
	return colour;
    }
    
    /**Gets the location the piece will be moved to
     * @param piece the piece to be moved
     * @param bs the board state
     * @return the position the piece will be moved to 
     */
    @Override
    int[] requestMove(int[] piece,BoardSquare[][] bs) {
	passer.mouseClicked=false;
	//Wait till the mouse is clicked
	while(!passer.mouseClicked){
	    //Abort move if new board is loaded
	    if(passer.newBoardAvailable){
		return null;
	    }
	    try {
		if(passer.cancelSelection){
		    return piece;
		}else{
		    Thread.sleep(1);
		}
	    } catch (InterruptedException ex) {
		Logger.getLogger(Human.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	//Abort move if new board is loaded
	if(passer.newBoardAvailable){
	    return null;
	}
	//Detrermines board location of mouse click
	int x = (int)((passer.mouseX-54)/73);
	int y = (int)((passer.mouseY-60)/74);
	int[] coords = new int[2];
	coords[0]=x;
	coords[1]=y;
	return coords;
    }

    @Override
    boolean validPiece() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
