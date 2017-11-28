/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessai;

/**
 *
 * @author RyanS
 */
public class Passer {
    Boolean update;
    BoardSquare[][] board;
    Passer(){
	update =false;
	board = null;
    }
    
    public void setBoard(BoardSquare[][] b){
	board = b;
    }
}
