package chessai;

/**
 *
 * @author Ryan and Parm
 */
public abstract class Player {
    boolean colour;//Colour of the player, true is white, false is black
    boolean isHuman;//True if it is a human player , false if not
    abstract int[] requestPiece(BoardSquare[][] bs);
    abstract int[] requestMove(int[] piece, BoardSquare[][] bs) ;
    abstract boolean validPiece();
}
