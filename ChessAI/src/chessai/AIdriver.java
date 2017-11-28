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
public class AIdriver extends Player{
    Passer passer;
    AIdriver(Passer p){
	passer=p;
    }
    

    @Override
    int[] requestPiece(Board b) {
	return null;
    }

    @Override
    Board requestMove(int[] piece) {
	return null;
    }
}
