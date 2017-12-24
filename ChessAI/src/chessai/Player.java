/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessai;

/**
 *
 * @author Ryan and Parm
 */
public abstract class Player {
    boolean colour;
    boolean isHuman;
    abstract int[] requestPiece(BoardSquare[][] bs);
    abstract int[] requestMove(int[] piece, BoardSquare[][] bs) ;
    abstract boolean validPiece();
}
