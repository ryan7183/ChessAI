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
    int[] move;
    int[] pieceChosen;
    AIdriver(Passer p, boolean colour){
	this.colour = colour;
	passer=p;
	depth=4;
	move = new int[2];
	pieceChosen = new int[2];
    }
    

    @Override
    int[] requestPiece(BoardSquare[][] bs) {
	return null;
    }

    Piece[] getPieces(BoardSquare[][] bs){
	Piece[] p;
	int numPieces=0;
	//Count the number of pieces still in playfor this player
	for(int x=0;x<bs.length;x++){
	    for(int y=0;y<bs.length;y++){
		if(bs[x][y].hasPiece&&bs[x][y].piece.colour==colour){
		    numPieces++;
		}
	    }
	}
	p=new Piece[numPieces];
	int count =0;
	//Get each piece
	for(int x=0;x<bs.length;x++){
	    for(int y=0;y<bs.length;y++){
		if(bs[x][y].hasPiece&&bs[x][y].piece.colour==colour){
		    p[count]=bs[x][y].piece;
		    count++;
		}
	    }
	}
	return null;
    }

    @Override
    boolean validPiece() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    int[] requestMove(int[] piece, BoardSquare[][] bs) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    int[] aplhaBeta(BoardSquare[][] bs){
	return null;
    }
    
    double boardEvaluation(){
	return 0;
    }
    
    
}
