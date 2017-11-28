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

    abstract int[] requestPiece(Board b);
    abstract Board requestMove(int[] piece);
}
