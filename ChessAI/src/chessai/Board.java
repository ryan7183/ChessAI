package chessai;

import java.io.File;

/**
 *
 * @author Ryan and Parm
 * 
 */
public class Board {
    BoardSquare[][] board;
    Passer p;
    Player player1;
    Player player2;
    //Set up a regular board
    Board(Passer p,Player player1, Player player2){
	this.player1 = player1;
	this.player2 =player2;
	board = new BoardSquare[8][8];
	this.p =p;
	//Make board
	for(int y=0;y<8;y++){
	    for(int x=0;x<8;x++){
		board[y][x] = new BoardSquare(x,y);
	    }
	}
	//Set up pieces
	//Black first row
	board[0][0].setPiece(new Rook(false,0,0,"R"));
	board[0][7].setPiece(new Rook(false,7,0,"R"));
	board[0][1].setPiece(new Knight(false,1,0,"N"));
	board[0][6].setPiece(new Knight(false,6,0,"N"));
	board[0][2].setPiece(new Bishop(false,2,0,"B"));
	board[0][5].setPiece(new Bishop(false,5,0,"B"));
	board[0][3].setPiece(new Queen(false,3,0,"Q"));
	board[0][4].setPiece(new King(false,4,0,"K"));
	//Black pawns
	for(int x=0;x<8;x++){
	    board[1][x].setPiece(new Pawn(false,x,1,"P"));
	
	}
	//White first row
	board[7][0].setPiece(new Rook(true,0,7,"r"));
	board[7][7].setPiece(new Rook(true,7,7,"r"));
	board[7][1].setPiece(new Knight(true,1,7,"n"));
	board[7][6].setPiece(new Knight(true,6,7,"n"));
	board[7][2].setPiece(new Bishop(true,2,7,"b"));
	board[7][5].setPiece(new Bishop(true,05,7,"b"));
	board[7][3].setPiece(new Queen(true,3,7,"q"));
	board[7][4].setPiece(new King(true,4,7,"k"));
	//White pawns
	for(int x=0;x<8;x++){
	    board[6][x].setPiece(new Pawn(true,x,6,"p"));
	
	}
	p.setBoard(board);
	//printBoard();
	start();
    }
   
    //Set up a board from a file
    Board(File f,Passer p,Player player1, Player player2){
	start();
    }
    
    //Set up a board from an array of board squares
    Board(BoardSquare[][] board, Passer p,Player player1, Player player2){
	this.board = board;
	start();
    }
    
    void start(){
	boolean cont=true;
	boolean valid = false;
	int[] pieceSelected;
	int[] pieceMove;
	while(cont){
	    //Ask player1
	    valid = false;
	    //Wait till valid piece is selected
	    pieceSelected = player1.requestPiece(this);
	    //Wait till valid move is selected
	    player1.requestMove(pieceSelected);
	    p.update = true;//Tell the render there is a change to update
	    //Update board
	    //Ask player2
	    //Update board
	}
    }
    
    void printBoard(){
        System.out.print(" ");
        for (int i=0; i<board[0].length; i++){
            System.out.print(" "+(char)(i+65)+" ");
        }
        System.out.println();
	for(int x=0;x<board[0].length;x++){
            System.out.print(x);
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
    
    void movePiece (int[] ogPos, int[] newPos){
        board[newPos[1]][newPos[0]].setPiece(board[ogPos[1]][ogPos[0]].piece);
        board[ogPos[1]][ogPos[0]].removePiece();
        printBoard();
    }
}
