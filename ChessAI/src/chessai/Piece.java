/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessai;

import java.util.ArrayList;

/**
 *
 * @author Ryan and Parm
 */
public abstract class Piece {
    boolean colour;//false black, true white
    int x,y;//Coordinates
    int prevX, prevY;//Previous coordinates
    public String textRepresentation;
    boolean hasMoved;
    boolean prevHasMoved;//If the piece had been prevously moved
    boolean pawnPromotion;
    boolean castleQueenSide;
    boolean castleKingSide;
    boolean enpassantLeft ;
    boolean enpassantRight;
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
