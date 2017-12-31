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
		    piece = new Bishop(p.colour,p.x,p.y,p.textRepresentation);
                    if(p.hasMoved){
                        piece.hasMoved = true;
                    }
                    if(p.pawnPromotion){
                        p.pawnPromotion = true;
                    }
                    if(p.prevHasMoved){
                        p.prevHasMoved = true;
                    }
                    piece.prevX = p.prevX;
                    piece.prevY = p.prevY;
		    break;
		case "K":
		    p=bs.piece;
		    piece = new King(p.colour,p.x,p.y,p.textRepresentation);
                    if(p.castleKingSide){
                        piece.castleKingSide = true;
                    }
                    if(p.castleQueenSide){
                        piece.castleQueenSide = true;
                    }
                    if(p.hasMoved){
                        piece.hasMoved = true;
                    }
                    if(p.prevHasMoved){
                        p.prevHasMoved = true;
                    }
                    piece.prevX = p.prevX;
                    piece.prevY = p.prevY;
		    break;
		case "N":
		    p=bs.piece;
		    piece = new Knight(p.colour,p.x,p.y,p.textRepresentation);
                    if(p.hasMoved){
                        piece.hasMoved = true;
                    }
                    if(p.prevHasMoved){
                        p.prevHasMoved = true;
                    }
                    piece.prevX = p.prevX;
                    piece.prevY = p.prevY;
		    break;
		case "P":
		    p=bs.piece;
		    piece = new Pawn(p.colour,p.x,p.y,p.textRepresentation);
                    if(p.enpassantLeft){
                        piece.enpassantLeft = true;
                    }
                    if(p.enpassantRight){
                        piece.enpassantRight = true;
                    }
                    if(p.hasMoved){
                        piece.hasMoved = true;
                    }
                    if(p.pawnPromotion){
                        p.pawnPromotion = true;
                    }
                    if(p.prevHasMoved){
                        p.prevHasMoved = true;
                    }
                    piece.prevX = p.prevX;
                    piece.prevY = p.prevY;
		    break;
		case "Q":
		    p=bs.piece;
		    piece = new Queen(p.colour,p.x,p.y,p.textRepresentation);
                    if(p.hasMoved){
                        piece.hasMoved = true;
                    }
                    if(p.prevHasMoved){
                        p.prevHasMoved = true;
                    }
                    piece.prevX = p.prevX;
                    piece.prevY = p.prevY;
		    break;
		case "R":
		    p=bs.piece;
		    piece = new Rook(p.colour,p.x,p.y,p.textRepresentation);
                    if(p.hasMoved){
                        piece.hasMoved = true;
                    }
                    if(p.prevHasMoved){
                        p.prevHasMoved = true;
                    }
                    piece.prevX = p.prevX;
                    piece.prevY = p.prevY;
		    break;
		case "b":
		    p=bs.piece;
		    piece = new Bishop(p.colour,p.x,p.y,p.textRepresentation);
                    if(p.hasMoved){
                        piece.hasMoved = true;
                    }
                    if(p.pawnPromotion){
                        p.pawnPromotion = true;
                    }
                    if(p.prevHasMoved){
                        p.prevHasMoved = true;
                    }
                    piece.prevX = p.prevX;
                    piece.prevY = p.prevY;
		    break;
		case "k":
		    p=bs.piece;
		    piece = new King(p.colour,p.x,p.y,p.textRepresentation);
                    if(p.castleKingSide){
                        piece.castleKingSide = true;
                    }
                    if(p.castleQueenSide){
                        piece.castleQueenSide = true;
                    }
                    if(p.hasMoved){
                        piece.hasMoved = true;
                    }
                    if(p.prevHasMoved){
                        p.prevHasMoved = true;
                    }
                    piece.prevX = p.prevX;
                    piece.prevY = p.prevY;
		    break;
		case "n":
		    p=bs.piece;
		    piece = new Knight(p.colour,p.x,p.y,p.textRepresentation);
                    if(p.hasMoved){
                        piece.hasMoved = true;
                    }
                    if(p.prevHasMoved){
                        p.prevHasMoved = true;
                    }
                    piece.prevX = p.prevX;
                    piece.prevY = p.prevY;
		    break;
		case "p":
		    p=bs.piece;
		    piece = new Pawn(p.colour,p.x,p.y,p.textRepresentation);
                    if(p.enpassantLeft){
                        piece.enpassantLeft = true;
                    }
                    if(p.enpassantRight){
                        piece.enpassantRight = true;
                    }
                    if(p.hasMoved){
                        piece.hasMoved = true;
                    }
                    if(p.pawnPromotion){
                        p.pawnPromotion = true;
                    }
                    if(p.prevHasMoved){
                        p.prevHasMoved = true;
                    }
                    piece.prevX = p.prevX;
                    piece.prevY = p.prevY;
		    break;
		case "q":
		    p=bs.piece;
		    piece = new Queen(p.colour,p.x,p.y,p.textRepresentation);
                    if(p.hasMoved){
                        piece.hasMoved = true;
                    }
                    if(p.prevHasMoved){
                        p.prevHasMoved = true;
                    }
                    piece.prevX = p.prevX;
                    piece.prevY = p.prevY;
		    break;
		case "r":
		    p=bs.piece;
		    piece = new Rook(p.colour,p.x,p.y,p.textRepresentation);
                    if(p.hasMoved){
                        piece.hasMoved = true;
                    }
                    if(p.prevHasMoved){
                        p.prevHasMoved = true;
                    }
                    piece.prevX = p.prevX;
                    piece.prevY = p.prevY;
		    break;
		default:
		    break;
	    }
	}else{
	    bs.piece=null;
	}
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
    
    /**
     * Sets a piece at the board square
     * @param p The piece that is going to be set on the board square
     */
    void setPiece(Piece p){
	piece = p;
	hasPiece = true;
    }
}
