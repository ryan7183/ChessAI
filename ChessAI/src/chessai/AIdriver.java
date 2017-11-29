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
    int depth;
    AIdriver(Passer p, boolean colour){
	this.colour = colour;
	passer=p;
	depth=4;
    }
    

    @Override
    int[] requestPiece() {
	return null;
    }


    @Override
    boolean validPiece() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    BoardSquare[][] requestMove(int[] piece, BoardSquare[][] bs) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    int[] aplhaBeta(){
	return null;
    }
    
    double boardEvaluation(){
	return 0;
    }
    
    
}
