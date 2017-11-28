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
    double mouseX;
    double mouseY;
    Passer(){
	update =false;
	board = null;
	mouseX=0;
	mouseY = 0;
    }
    
    public void setBoard(BoardSquare[][] b){
	board = b;
    }
}
