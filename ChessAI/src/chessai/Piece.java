package chessai;

import java.util.ArrayList;

/**
 *This class is an abstract class which is used by all the chess pieces
 * @author Ryan and Parm
 */
public abstract class Piece {
    boolean colour;//false black, true white
    int x,y;//Coordinates
    int prevX, prevY;//Previous coordinates
    public String textRepresentation;//The text representation of the piece
    boolean hasMoved;//If the piece has been moved
    boolean prevHasMoved;//If the piece had been prevously moved
    boolean pawnPromotion;//Is true if pawn promoation is allowd
    boolean castleQueenSide;//Is true if castling on the queen side is allowed
    boolean castleKingSide;//Is true if castling on the king side is allowed
    boolean enpassantLeft ;//Is true if en passant on the left side is allowed
    boolean enpassantRight;//Is true if en passant on the right side is allowed
    Piece(boolean c,int x, int y, String n){
	hasMoved=false;
        prevHasMoved=false;
	colour = c;
	this.x=x;
	this.y=y;
        this.prevX=this.x;
        this.prevY=this.y;
        this.textRepresentation = n;
    }
    
    public abstract Boolean isValidMove(int[] newPos, BoardSquare[][] bs, ArrayList<Piece> moveList);
    public abstract int[][] generateMoves(BoardSquare[][] bs, ArrayList<Piece> moveList);
}
