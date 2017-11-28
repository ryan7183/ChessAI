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
public class Human extends Player {
    Passer passer;
    Human(Passer p){
	passer=p;
    }

    @Override
    int[] requestPiece() {
	passer.playerSelectSet(true);
	
	return null;
    }

    @Override
    int[] requestMove() {
	return null;
    }
}
