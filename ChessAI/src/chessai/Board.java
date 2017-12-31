package chessai;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ryan and Parm
 * 
 */
public class Board {
    BoardSquare[][] board;//[y][x]
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
		board[x][y] = new BoardSquare(x,y);
	    }
	}
	//Set up pieces
	//Black first row
	board[0][0].setPiece(new Rook(false,0,0,"R"));
	board[7][0].setPiece(new Rook(false,7,0,"R"));
	board[1][0].setPiece(new Knight(false,1,0,"N"));
	board[6][0].setPiece(new Knight(false,6,0,"N"));
	board[2][0].setPiece(new Bishop(false,2,0,"B"));
	board[5][0].setPiece(new Bishop(false,5,0,"B"));
	board[3][0].setPiece(new Queen(false,3,0,"Q"));
	board[4][0].setPiece(new King(false,4,0,"K"));
	//Black pawns
	for(int x=0;x<8;x++){
	    board[x][1].setPiece(new Pawn(false,x,1,"P"));
	
	}
	//White first row
	board[0][7].setPiece(new Rook(true,0,7,"r"));
	board[7][7].setPiece(new Rook(true,7,7,"r"));
	board[1][7].setPiece(new Knight(true,1,7,"n"));
	board[6][7].setPiece(new Knight(true,6,7,"n"));
	board[2][7].setPiece(new Bishop(true,2,7,"b"));
	board[5][7].setPiece(new Bishop(true,05,7,"b"));
	board[3][7].setPiece(new Queen(true,3,7,"q"));
	board[4][7].setPiece(new King(true,4,7,"k"));
	//White pawns
	for(int x=0;x<8;x++){
	    board[x][6].setPiece(new Pawn(true,x,6,"p"));
	
	}
	p.setBoard(board);
	start();
	}
   
    //Set up a board from a file
    Board(File f,Passer p,Player player1, Player player2){
	this.player1 = player1;
	this.player2 =player2;
	this.p = p;
	try {
	    board = buildBoardFile(f);
	    p.setBoard(board);
	} catch (FileNotFoundException ex) {
	    Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
	}
	start();
    }
    
    //Set up a board from an array of board squares
    Board(BoardSquare[][] board, Passer p,Player player1, Player player2){
	this.board = board;
	start();
    }
    BoardSquare[][] buildBoardFile(File f) throws FileNotFoundException{
	
	FileReader fr = new FileReader(f);
	Scanner in = new Scanner(fr);
	String row;
	char square;
	board = new BoardSquare[8][8];
	Piece p=null;
	for(int y=0;y<8;y++){
	    row = in.next();
	    for(int x=0;x<8;x++){
		square = row.charAt(x);
		board[x][y] = new BoardSquare(x,y);
		switch(square){
		    case '-':
			p =null;
			break;
		    case 'B':
			p= new Bishop(false,x,y,"B");
			break;
		    case 'b':
			p= new Bishop(true,x,y,"b");
			break;
		    case 'K':
			p= new King(false,x,y,"K");
			break;
		    case 'k':
			p= new King(true,x,y,"k");
			break;
		    case 'N':
			p= new Knight(false,x,y,"N");
			break;
		    case 'n':
			p= new Knight(true,x,y,"n");
			break;
		    case 'P':
			p= new Pawn(false,x,y,"P");
			break;
		    case 'p':
			p= new Pawn(true,x,y,"p");
			break;
		    case 'Q':
			p= new Queen(false,x,y,"Q");
			break;
		    case 'q':
			p= new Queen(true,x,y,"q");
			break;
		    case 'R':
			p= new Rook(false,x,y,"R");
			break;
		    case 'r':
			p= new Rook(true,x,y,"r");
			break;
		    default:
			p=null;
			break;
		}
		if(p!=null){
		    board[x][y].setPiece(p);
		}
	    }
	}
	return board;
     }
    void start(){
	boolean cont=true;
	boolean valid = false;
	int[] pieceSelected;
	int[] pieceMove;
        ArrayList<Piece> moveList = new ArrayList<Piece>();
	boolean isCheckMate=false;
        boolean stalemate=false;
	while(cont){
	while(cont){
	    //Player1 move loop
	    while(true){
		//Ask player1
		valid = false;
		//Wait till valid piece is selected
		while(true){
		    pieceSelected = player1.requestPiece(board);//Get piece to be moved
		    if(p.newBoardAvailable){
			break;
		    }
		    valid = validPiece(player1,pieceSelected);
		    if(valid){
			p.setSelectionPosition(pieceSelected);
			p.drawSelection=true;
			break;
		    }
		}
		//Update board
		p.boardUpdate = true;//Tell the render there is a change to update
		//Wait till valid move is selected
		while(true){
		    pieceMove =  player1.requestMove(pieceSelected,board);//Get postion piece will be moved to
		    if(p.newBoardAvailable){
			break;
		    }
		    if(p.cancelSelection){
			break;
		    }
                    board[pieceSelected[0]][pieceSelected[1]].piece.x = pieceSelected[0];
                    board[pieceSelected[0]][pieceSelected[1]].piece.y = pieceSelected[1];
                    outerloop:
		    if(board[pieceSelected[0]][pieceSelected[1]].piece.isValidMove(pieceMove, board, moveList)){
                        ArrayList<Piece> temp = new ArrayList<Piece>();
			if(!isKingInCheck(board[pieceSelected[0]][pieceSelected[1]].piece.colour,this.board, moveList)){
                            //board=requestMove(pieceSelected,pieceMove,board);//Move piece
                            BoardSquare[][] bs = new BoardSquare[board.length][board[0].length];
                            for(int x=0;x<bs.length;x++){
                                for(int y=0;y<bs[0].length;y++){
                                    bs[x][y] = new BoardSquare(board[x][y]);
                                }
                            }
                            bs = requestMove(pieceSelected,pieceMove,bs,temp);
                            if(!isKingInCheck(true, bs, moveList)){
                                board = requestMove(pieceSelected,pieceMove,board,moveList);
                            }
                            else{
                                break outerloop;
                            }
                        }
                        else{
                            BoardSquare[][] bs = new BoardSquare[board.length][board[0].length];
                            for(int x=0;x<bs.length;x++){
                                for(int y=0;y<bs[0].length;y++){
                                    bs[x][y] = new BoardSquare(board[x][y]);
                                }
                            }
                            bs = requestMove(pieceSelected,pieceMove,bs,temp);
                            if(!isKingInCheck(true, bs, moveList)){
                                board = requestMove(pieceSelected,pieceMove,board,moveList);
                            }
                            else{
                                break outerloop;
                            }
                        }
                        if(board[pieceMove[0]][pieceMove[1]].piece.textRepresentation.equals("p") && board[pieceMove[0]][pieceMove[1]].piece.pawnPromotion){
                            p.promotion = true;
                            String piece = p.newPiece;
                            switch(piece){
                                case "Bishop":
                                    board[pieceMove[0]][pieceMove[1]].piece = new Bishop(true,pieceMove[0],pieceMove[1],"b");
                                    break;
                                case "Knight":
                                    board[pieceMove[0]][pieceMove[1]].piece = new Knight(true,pieceMove[0],pieceMove[1],"n");
                                    break;
                                case "Queens":
                                    board[pieceMove[0]][pieceMove[1]].piece = new Queen(true,pieceMove[0],pieceMove[1],"q");
                                    break;
                                case "Rook":
                                    board[pieceMove[0]][pieceMove[1]].piece = new Rook(true,pieceMove[0],pieceMove[1],"r");
                                    break;
                                default: piece.equals("");
                                    board[pieceMove[0]][pieceMove[1]].piece = new Queen(true,pieceMove[0],pieceMove[1],"q");
                                    break;
                                        
                            }
                        }
                        if(board[pieceMove[0]][pieceMove[1]].piece.enpassantLeft){
                            board[pieceMove[0]][pieceMove[1]+1].removePiece();
                            board = requestMove(pieceSelected,pieceMove,board,moveList);
                            board[pieceMove[0]][pieceMove[1]].piece.enpassantLeft = false;
                        }
                        else if(board[pieceMove[0]][pieceMove[1]].piece.enpassantRight){
                            board[pieceMove[0]][pieceMove[1]+1].removePiece();
                            board = requestMove(pieceSelected,pieceMove,board,moveList);
                            board[pieceMove[0]][pieceMove[1]].piece.enpassantRight = false;
                        }
                        if(board[pieceMove[0]][pieceMove[1]].piece.castleKingSide){
                            int[] rookPos = new int[2];
                            int[] rookMove = pieceMove.clone();
                            rookPos[0] = 7;
                            rookPos[1] = pieceMove[1];
                            rookMove[0]-=1;
                            board = requestMove(rookPos,rookMove,board,moveList);
                            board[pieceMove[0]][pieceMove[1]].piece.castleKingSide = false;
                        }
                        else if(board[pieceMove[0]][pieceMove[1]].piece.castleQueenSide) {
                            int[] rookPos = new int[2];
                            int[] rookMove = pieceMove.clone();
                            rookPos[0] = 0;
                            rookPos[1] = pieceMove[1];
                            rookMove[0]+=1;
                            board= requestMove(rookPos,rookMove,board,moveList);
                            board[pieceMove[0]][pieceMove[1]].piece.castleQueenSide = false;
                        }
			break;
		    }
                    if(this.player1.isHuman){
                        p.isInvalid = true;
                    }
		    System.out.println("Can't move a piece like that");
		}
		if(p.newBoardAvailable){
			break;
		    }
		if(p.cancelSelection){
		    p.cancelSelection=false;
		    p.drawSelection=false;
		    p.boardUpdate=true;
		}else{
		    break;
		}
	    }//End of Player1 turn
	    if(p.newBoardAvailable){
		p.board=p.newBoard;
		board=p.board;
		p.newBoardAvailable=false;
		p.boardUpdate=true;
		break;
	    }
	    p.drawSelection=false;
	    
	    //Update board
	    p.boardUpdate = true;//Tell the render there is a change to update
	    
	    //Check for check mate
            if(isKingInCheck(false,board,moveList)){
                isCheckMate = isCheckMate(false,board, moveList);
                if(isCheckMate){
                    p.blackCheckmate = true;
                    System.out.println("CheckMate! White Wins.");
                }
            }
            else{
                stalemate = isStalemate(false,board, moveList);
                if(stalemate){
                    p.stalemate = true;
                    System.out.println("Stalemate. The game is a draw.");
                }
            }
	     //Player2 move loop
	    while(true){
		//Ask player2
		valid = false;
		//Wait till valid piece is selected
		while(true){
		    pieceSelected = player2.requestPiece(board);
		    if(p.newBoardAvailable){
			break;
		    }
		    valid = validPiece(player2,pieceSelected);

		    if(valid){
			p.setSelectionPosition(pieceSelected);
			p.drawSelection=true;
			break;
		    }
		}
		//Update board
		p.boardUpdate = true;//Tell the render there is a change to update
		//Wait till valid move is selected
		while(true){
		    pieceMove =  player2.requestMove(pieceSelected,board);//Get position piece will be moved to
		    if(p.newBoardAvailable){
			break;
		    }
		    if(p.cancelSelection){
			break;
		    }
                    outerloop:
		    if(board[pieceSelected[0]][pieceSelected[1]].piece.isValidMove(pieceMove, board, moveList)){
                        ArrayList<Piece> temp = new ArrayList<Piece>();
                        if(!isKingInCheck(board[pieceSelected[0]][pieceSelected[1]].piece.colour,this.board, moveList)){
                            BoardSquare[][] bs = new BoardSquare[board.length][board[0].length];
                            for(int x=0;x<bs.length;x++){
                                for(int y=0;y<bs[0].length;y++){
                                    bs[x][y] = new BoardSquare(board[x][y]);
                                }
                            }
                            bs = requestMove(pieceSelected,pieceMove,bs,temp);
                            if(!isKingInCheck(false, bs, moveList)){
                                board = requestMove(pieceSelected,pieceMove,board,moveList);
                            }
                            else{
                                break outerloop;
                            }
                        }
                        else{
                            BoardSquare[][] bs = new BoardSquare[board.length][board[0].length];
                            for(int x=0;x<bs.length;x++){
                                for(int y=0;y<bs[0].length;y++){
                                    bs[x][y] = new BoardSquare(board[x][y]);
                                }
                            }
                            bs = requestMove(pieceSelected,pieceMove,bs,temp);
                            if(!isKingInCheck(false, bs, moveList)){
                                board = requestMove(pieceSelected,pieceMove,board,moveList);
                            }
                            else{
                                break outerloop;
                            }
                        }
                        if(board[pieceMove[0]][pieceMove[1]].piece.textRepresentation.equals("P") && board[pieceMove[0]][pieceMove[1]].piece.pawnPromotion){
                            p.promotion = true;
                            String piece = p.newPiece;
                            switch(piece){
                                case "Bishop":
                                    board[pieceMove[0]][pieceMove[1]].piece = new Bishop(false,pieceMove[0],pieceMove[1],"B");
                                    break;
                                case "Knight":
                                    board[pieceMove[0]][pieceMove[1]].piece = new Knight(false,pieceMove[0],pieceMove[1],"N");
                                    break;
                                case "Queens":
                                    board[pieceMove[0]][pieceMove[1]].piece = new Queen(false,pieceMove[0],pieceMove[1],"Q");
                                    break;
                                case "Rook":
                                    board[pieceMove[0]][pieceMove[1]].piece = new Rook(false,pieceMove[0],pieceMove[1],"R");
                                    break;
                                default: piece.equals("");
                                    board[pieceMove[0]][pieceMove[1]].piece = new Queen(false,pieceMove[0],pieceMove[1],"Q");
                                    break;
                                        
                            }
                            //board[pieceMove[0]][pieceMove[1]].piece = new Queen(false,pieceMove[0],pieceMove[1],"Q");
                        }
                        if(board[pieceMove[0]][pieceMove[1]].piece.enpassantLeft){
                            board[pieceMove[0]][pieceMove[1]-1].removePiece();
                            board = requestMove(pieceSelected,pieceMove,board,moveList);
                            board[pieceMove[0]][pieceMove[1]].piece.enpassantLeft = false;
                        }
                        else if(board[pieceMove[0]][pieceMove[1]].piece.enpassantRight){
                            board[pieceMove[0]][pieceMove[1]-1].removePiece();
                            board = requestMove(pieceSelected,pieceMove,board,moveList);
                            board[pieceMove[0]][pieceMove[1]].piece.enpassantRight = false;
                        }
                        if(board[pieceMove[0]][pieceMove[1]].piece.castleKingSide){
                            int[] rookPos = new int[2];
                            int[] rookMove = pieceMove.clone();
                            rookPos[0] = 7;
                            rookPos[1] = pieceMove[1];
                            rookMove[0]-=1;
                            board = requestMove(rookPos,rookMove,board,moveList);
                            board[pieceMove[0]][pieceMove[1]].piece.castleKingSide = false;
                        }
                        else if(board[pieceMove[0]][pieceMove[1]].piece.castleQueenSide) {
                            int[] rookPos = new int[2];
                            int[] rookMove = pieceMove.clone();
                            rookPos[0] = 0;
                            rookPos[1] = pieceMove[1];
                            rookMove[0]+=1;
                            board = requestMove(rookPos,rookMove,board, moveList);
                            board[pieceMove[0]][pieceMove[1]].piece.castleQueenSide = false;
                        }
			break;
		    }
                    if(this.player2.isHuman){
                        p.isInvalid = true;
                    }
		    System.out.println("Can't move a piece like that");
		}
		if(p.newBoardAvailable){
			break;
		    }
		if(p.cancelSelection){
		    p.cancelSelection=false;
		    p.drawSelection=false;
		    p.boardUpdate=true;
		}else{
		    break;
		}
	    }//End of player 2 turn
	    p.drawSelection=false;
	    if(p.newBoardAvailable){
		p.board=p.newBoard;
		board=p.board;
		p.newBoardAvailable=false;
		p.boardUpdate=true;
		break;
	    }
	    //Update board
	    p.boardUpdate = true;//Tell the render there is a change to update
	    
	    //Check for check mate
            if(isKingInCheck(true,board,moveList)){
                isCheckMate = isCheckMate(true,board,moveList);
                if(isCheckMate){
                    p.whiteCheckmate = true;
                    System.out.println("CheckMate! Black Wins.");
                } 
            }
            else{
                stalemate = isStalemate(true,board,moveList);
                if(stalemate){
                    p.stalemate = true;
                    System.out.println("Stalemate. The game is a draw.");
                }
            }
	}
	}
    }
    
    
    
    BoardSquare[][] requestMove(int[] piece, int[] move, BoardSquare[][] board, ArrayList<Piece> moveList){
	int x,y;
	board[move[0]][move[1]].piece = board[piece[0]][piece[1]].piece;
	board[move[0]][move[1]].piece.x = move[0];
	board[move[0]][move[1]].piece.y = move[1];
        board[move[0]][move[1]].piece.prevX = piece[0];
        board[move[0]][move[1]].piece.prevY = piece[1];
        board[move[0]][move[1]].piece.prevHasMoved = board[piece[0]][piece[1]].piece.hasMoved;
        board[move[0]][move[1]].piece.hasMoved = true;
	board[piece[0]][piece[1]].hasPiece=false;
	board[move[0]][move[1]].hasPiece=true;
        moveList.add(board[move[0]][move[1]].piece);
	return board;
    }
    
    boolean validPiece(Player player, int[] c){
	boolean valid = true;
	
	if(!board[c[0]][c[1]].hasPiece || player.colour!=board[c[0]][c[1]].piece.colour){
	    valid = false;
	}
	return valid;
    }
    
    int[] getKingLocation (Boolean c, BoardSquare[][] bs){
        int[] location = new int[2];
        for(int x=0;x<bs.length;x++){
            for(int y=0; y<bs[0].length; y++){
                if(c){
                    if(bs[x][y].hasPiece && bs[x][y].piece.textRepresentation.equals("k")){
                        location[0] = x;
                        location[1] = y; 
                    }
                }
                else {
                    if(bs[x][y].hasPiece && bs[x][y].piece.textRepresentation.equals("K")){
                        location[0] = x;
                        location[1] = y; 
                    }
                }
            }
        }
        return location; 
    }
    
    boolean isKingInCheck(Boolean c,BoardSquare[][] board, ArrayList<Piece> moveList){
        int[] loc = getKingLocation(c,board);
        for(int x=0; x<board.length; x++){
            for(int y=0; y<board[0].length; y++){
                if(board[x][y].hasPiece && board[x][y].piece.colour != c){
                    if(board[x][y].piece.isValidMove(loc, board, moveList)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    boolean isCheckMate(Boolean c, BoardSquare[][] board, ArrayList<Piece> moveList){
	boolean checkMate = true;
	int[] kingPos = getKingLocation(c,board);
	int[][] moves;
        int[] pos = new int[2];
	BoardSquare[][] bs=board.clone();
	//Check if king can move
	//Check each piece
	for(int x=0;x<bs.length;x++){
	    for(int y=0;y<bs[0].length;y++){
		if(bs[x][y].hasPiece && bs[x][y].piece.colour==c){
		    moves = bs[x][y].piece.generateMoves(board, moveList);
                    pos[0]=x;
                    pos[1]=y;
                    if(pieceCanPreventCheck(kingPos,moves,board,c)){
                        checkMate = false;
                    }
		}
	    }
	}
	return checkMate;
	
    }
    
    boolean isStalemate(Boolean c, BoardSquare[][] board, ArrayList<Piece> moveList){
        boolean staleMate = true;
	int[] kingPos = getKingLocation(c,board);
	int[][] moves;
        int[] pos = new int[2];
	BoardSquare[][] bs=board.clone();
	//Check if king can move
	//Check each piece
	for(int x=0;x<bs.length;x++){
	    for(int y=0;y<bs[0].length;y++){
		if(bs[x][y].hasPiece && bs[x][y].piece.colour==c){
		    moves = bs[x][y].piece.generateMoves(board, moveList);
                    pos[0]=x;
                    pos[1]=y;
                    if(canMove(kingPos,pos,moves,board,c)){
                        staleMate = false;
                    }
		}
	    }
	}
	return staleMate;
    }
    boolean pieceCanPreventCheck(int[] pos,int[][] moves, BoardSquare[][] board, boolean c){
	
	BoardSquare[][] bs = new BoardSquare[board.length][board[0].length];
        ArrayList<Piece> temp = new ArrayList<Piece>();
	//Make copy of array to work on
	for(int x=0;x<bs.length;x++){
	    for(int y=0;y<bs[0].length;y++){
		bs[x][y] = new BoardSquare(board[x][y]);
	    }
	}
        bs[pos[0]][pos[1]].piece.x = pos[0];
        bs[pos[0]][pos[1]].piece.y = pos[1];
	boolean preventCheck=false;
	boolean colour = c;
	
	for(int[] m:moves){
            boolean possibleMove = false;
            possibleMove = bs[pos[0]][pos[1]].piece.isValidMove(m, bs, temp);
	    bs = requestMove(pos,m,bs,temp);
            int[] kingPos = getKingLocation(c,bs);
	    preventCheck = !isKingInCheck(colour,bs, temp);
	    if(preventCheck && possibleMove){
		break;
	    }
            else{
                preventCheck = false;
            }
	}
	return preventCheck;
    }
    
    public boolean canMove(int[] pos,int[] piecePos,int[][] moves, BoardSquare[][] board, boolean c){
        BoardSquare[][] bs = new BoardSquare[board.length][board[0].length];
        ArrayList<Piece> temp = new ArrayList<Piece>();
	//Make copy of array to work on
	for(int x=0;x<bs.length;x++){
	    for(int y=0;y<bs[0].length;y++){
		bs[x][y] = new BoardSquare(board[x][y]);
	    }
	}
        bs[pos[0]][pos[1]].piece.x = pos[0];
        bs[pos[0]][pos[1]].piece.y = pos[1]; 
        boolean preventCheck=false;
	boolean colour = c;
        
        for(int[] m:moves){
            boolean possibleMove = false;
            possibleMove = bs[piecePos[0]][piecePos[1]].piece.isValidMove(m, bs, temp);
            if(possibleMove){
                bs = requestMove(pos,m,bs,temp);
                preventCheck = !isKingInCheck(colour,bs,temp);
                if(preventCheck){
                    break;
                }
            }
        }
        return preventCheck;
    }
    
}
