
package chessai;

/**
 *
 * @author Ryan and Parm
 * Is a single position on the chess board
 * Store location on the board, and which piece if any the position holds.
 */
public class BoardSquare {
    int x,y;
    Piece piece;
    boolean hasPiece;
    
    BoardSquare(int x,int y){
	piece = null;
	hasPiece=false;
	this.x=x;
	this.y=y;
    }
    BoardSquare(Piece p, int x, int y){
	piece = p;
	hasPiece = true;
	this.x=x;
	this.y=y;
    }
    
    //Converts number location to a corresponding letter
    char convertNumToLetterLocation(int n){
	char l;
	n=n+65;
	l = (char)n;
	return l;
    }
    
    //Removes the piece fromt the square and returns it.
    void removePiece(){
	piece= null;
	hasPiece=false;
    }
    
    void setPiece(Piece p){
	piece = p;
	hasPiece = true;
    }
}
