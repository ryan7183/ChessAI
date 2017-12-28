
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
    
    BoardSquare(BoardSquare bs){
	Piece p;
	//Determine which type of piece is being copied

	if(bs.hasPiece){
	    	String str = bs.piece.textRepresentation;
	    switch(str){
		case "B":
		    p=bs.piece;
		    System.out.println(p.textRepresentation);
		    piece = new Bishop(p.colour,p.x,p.y,p.textRepresentation);
		    break;
		case "K":
		    p=bs.piece;
		    System.out.println(p.textRepresentation);
		    piece = new King(p.colour,p.x,p.y,p.textRepresentation);
		    piece.castleKingSide=p.castleKingSide;
		    piece.castleQueenSide=p.castleQueenSide;
		    break;
		case "N":
		    p=bs.piece;
		    System.out.println(p.textRepresentation);
		    piece = new Knight(p.colour,p.x,p.y,p.textRepresentation);
		    break;
		case "P":
		    p=bs.piece;
		    System.out.println(p.textRepresentation);
		    piece = new Pawn(p.colour,p.x,p.y,p.textRepresentation);
		    piece.pawnPromotion=p.pawnPromotion;
		    piece.enpassantLeft=p.enpassantLeft;
		    piece.enpassantRight=p.enpassantRight;
		    break;
		case "Q":
		    p=bs.piece;
		    System.out.println(p.textRepresentation);
		    piece = new Queen(p.colour,p.x,p.y,p.textRepresentation);
		    break;
		case "R":
		    p=bs.piece;
		    System.out.println(p.textRepresentation);
		    piece = new Rook(p.colour,p.x,p.y,p.textRepresentation);
		    break;
		case "b":
		    p=bs.piece;
		    System.out.println(p.textRepresentation);
		    piece = new Bishop(p.colour,p.x,p.y,p.textRepresentation);
		    break;
		case "k":
		    p=bs.piece;
		    System.out.println(p.textRepresentation);
		    piece = new King(p.colour,p.x,p.y,p.textRepresentation);
		    piece.castleKingSide=p.castleKingSide;
		    piece.castleQueenSide=p.castleQueenSide;
		    break;
		case "n":
		    p=bs.piece;
		    System.out.println(p.textRepresentation);
		    piece = new Knight(p.colour,p.x,p.y,p.textRepresentation);
		    break;
		case "p":
		    p=bs.piece;
		    System.out.println(p.textRepresentation);
		    piece = new Pawn(p.colour,p.x,p.y,p.textRepresentation);
		    piece.pawnPromotion=p.pawnPromotion;
		    piece.enpassantLeft=p.enpassantLeft;
		    piece.enpassantRight=p.enpassantRight;
		    break;
		case "q":
		    p=bs.piece;
		    System.out.println(p.textRepresentation);
		    piece = new Queen(p.colour,p.x,p.y,p.textRepresentation);
		    break;
		case "r":
		    p=bs.piece;
		    System.out.println(p.textRepresentation);
		    piece = new Rook(p.colour,p.x,p.y,p.textRepresentation);
		    break;
		default:
		    break;
	    }
	}else{
	    bs.piece=null;
	}
	//this.piece = bs.piece;
	this.hasPiece = bs.hasPiece;
	this.x=bs.x;
	this.y=bs.y;
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
