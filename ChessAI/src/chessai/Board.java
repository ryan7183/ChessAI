package chessai;

import java.io.File;

/**
 *
 * @author Ryan and Parm
 * 
 */
public class Board {
    BoardSquare[][] board;
    //Set up a regular board
    Board(){
	board = new BoardSquare[8][8];
	//Make board
	for(int y=0;y<8;y++){
	    for(int x=0;x<8;x++){
		board[y][x] = new BoardSquare(x,y);
	    }
	}
	//Set up pieces
	//Black first row
	board[0][0].setPiece(new Rook(false,0,0));
	board[0][7].setPiece(new Rook(false,7,0));
	board[0][1].setPiece(new Knight(false,1,0));
	board[0][6].setPiece(new Knight(false,6,0));
	board[0][2].setPiece(new Bishop(false,2,0));
	board[0][5].setPiece(new Bishop(false,5,0));
	board[0][3].setPiece(new Queen(false,3,0));
	board[0][4].setPiece(new King(false,4,0));
	//Black pawns
	for(int x=0;x<8;x++){
	    board[1][x].setPiece(new Pawn(false,x,1));
	
	}
	//White first row
	board[7][0].setPiece(new Rook(true,0,7));
	board[7][7].setPiece(new Rook(true,7,7));
	board[7][1].setPiece(new Knight(true,1,7));
	board[7][6].setPiece(new Knight(true,6,7));
	board[7][2].setPiece(new Bishop(true,2,7));
	board[7][5].setPiece(new Bishop(true,05,7));
	board[7][3].setPiece(new Queen(true,3,7));
	board[7][4].setPiece(new King(true,4,7));
	//White pawns
	for(int x=0;x<8;x++){
	    board[6][x].setPiece(new Pawn(false,x,6));
	
	}
	printBoard();
    }
   
    //Set up a board from a file
    Board(File f){
	
    }
    
    //Set up a board from an array of board squares
    Board(BoardSquare[][] board){
	this.board = board;
    }
    
    void printBoard(){
	for(int x=0;x<board[0].length;x++){
	    for(int y=0;y<board.length;y++){
		if(board[x][y].hasPiece){
		    System.out.print("["+board[x][y].piece.textRepresentation+"]");
		}else{
		System.out.print("[ ]");
		}
		
	    }
	    System.out.println();
	}
    }
}
