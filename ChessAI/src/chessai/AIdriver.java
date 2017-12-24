/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessai;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ryan and Parm
 */
public class AIdriver extends Player{
    Passer passer;
    int[] move;
    int[] pieceChosen;
    final int Max_Depth=2; 
    ArrayList<Piece> randList= new ArrayList();
    AIdriver(Passer p, boolean colour){
	this.colour = colour;
	this.isHuman = false;
	passer=p;
	move = new int[2];
	pieceChosen = new int[2];
    }
    
    //GEts piece to move and the position to move it
    @Override
    int[] requestPiece(BoardSquare[][] bs) {
	double value=alphaBeta(bs,0,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
	System.out.println(pieceChosen[0]+","+pieceChosen[1]+" to "+move[0]+","+move[1]);
	return pieceChosen;
    }
    
    boolean getColour(){
	return colour;
    }

    /**
     * @param bs the board
     * @param c which colour to check for white is true
     */
    Piece[] getPieces(BoardSquare[][] bs,boolean c){
	Piece[] p;
	int numPieces=0;
	//Count the number of pieces still in play for the given player
	for(int x=0;x<bs.length;x++){
	    for(int y=0;y<bs.length;y++){
		if(bs[x][y].hasPiece&&bs[x][y].piece.colour==c){
		    numPieces++;
		}
	    }
	}
	p=new Piece[numPieces];
	int count =0;
	//Get each piece
	for(int x=0;x<bs.length;x++){
	    for(int y=0;y<bs.length;y++){
		if(bs[x][y].hasPiece&&bs[x][y].piece.colour==c){
		    p[count]=bs[x][y].piece;
		    count++;
		}
	    }
	}
	return p;
    }

    @Override
    boolean validPiece() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //Returns the new positon of the piece being moved
    @Override
    int[] requestMove(int[] piece, BoardSquare[][] bs) {
	return move;
    }
    /**
     * @param bs the board state
     * @param depth the depth of the current node
     * @param a the alpha score
     * @param b the beta score
     * @param maximize if maximizing score or not
     */
    
    double alphaBeta(BoardSquare[][] bs,int depth,double a, double b){
	//printBoard(bs);
	Piece[] p=getPieces(bs,colour);
	int[][] moves;
	int[] piecePos=new int[2];
	int[] tempPos = new int[2];
	double best=Double.NEGATIVE_INFINITY;
	double value=Double.NEGATIVE_INFINITY;
	BoardSquare[][] newBoard;
	for(int i=0;i<p.length;i++){
	    moves = p[i].generateMoves(bs,randList);
	    for(int j=0;j<moves.length;j++){
		newBoard = copyBoard(bs);
		/*tempPos[0] = p[i].x;
		tempPos[1] =p[i].y;
		newBoard = requestMove(tempPos,moves[j],newBoard);*/
		value =  minValue(newBoard,0,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
		if(value>best){
		    best =value;
		    move = moves[j];
		    piecePos[0]=p[i].x;
		    piecePos[1]=p[i].y;
		    pieceChosen = piecePos;
		}
	    }
	}
	
	return best;
    }
    
    double maxValue(BoardSquare[][] bs,int depth,double a, double b){
	//printBoard(bs);
	double v;
	Piece[] p=getPieces(bs,colour);
	int[][] moves;
	BoardSquare[][] newBoard;
	int[] piecePos=new int[2];
	int[] tempPos = new int[2];
	if(depth >= Max_Depth||isCheckMate(!colour,bs)){
	    return boardEvaluation(bs);
	}
	v=Double.NEGATIVE_INFINITY;
	for(int i=0;i<p.length;i++){
	    moves = p[i].generateMoves(bs,randList);
	    for(int j=0;j<moves.length;j++){
		newBoard = copyBoard(bs);
		/*tempPos[0] = p[i].x;
		tempPos[1] =p[i].y;
		newBoard = requestMove(tempPos,moves[j],newBoard);*/
		v = max(v,minValue(newBoard,depth+1,a,b));
		if(v<b){
		    return v;
		}
		a = min(a,v);
	    }
	}
	return v;
    }
    
    double minValue(BoardSquare[][] bs,int depth,double a, double b){
	//printBoard(bs);
	double v;
	Piece[] p=getPieces(bs,!colour);
	int[][] moves;
	BoardSquare[][] newBoard;
	int[] piecePos=new int[2];
	int[] tempPos = new int[2];
	if(depth >= Max_Depth||isCheckMate(colour,bs)){
	    return boardEvaluation(bs);
	}
	v=Double.POSITIVE_INFINITY;
	for(int i=0;i<p.length;i++){
	    moves = p[i].generateMoves(bs,randList);
	    for(int j=0;j<moves.length;j++){
		newBoard = copyBoard(bs);
		/*tempPos[0] = p[i].x;
		tempPos[1] =p[i].y;
		newBoard = requestMove(tempPos,moves[j],newBoard);*/
		v = min(v,maxValue(newBoard,depth+1,a,b));
		if(v<a){
		    return v;
		}
		b = min(b,v);
	    }
	}
	return v;
    }
    
    double max(double a, double b){
	if(a>b){
	    return a;
	}
	return b;
    }
    
    double min(double a, double b){
	if(a<b){
	    return a;
	}
	return b;
    }
    
    double boardEvaluation(BoardSquare[][] bs){
	double score=0;//Greater value beter for this player
	ScoreFromNumPieces one = new ScoreFromNumPieces(bs,colour);
	ScoreFromPawnDistanceToEnd two = new ScoreFromPawnDistanceToEnd(bs,colour);
	ScoreFromPieceValue three = new ScoreFromPieceValue(bs,colour);
	one.start();
	two.start();
	three.start();
	try {
	    one.join();
	    two.join();
	    three.join();
	    score = one.score+two.score+three.score;
	} catch (InterruptedException ex) {
	    
	}
	//score = scoreFromNumPieces(bs)+scoreFromPawnDistanceToEnd(bs);
	return score;
    }
    
    BoardSquare[][] copyBoard(BoardSquare[][] board){
	BoardSquare[][] bs = new BoardSquare[board.length][board[0].length];
	for(int x=0;x<bs.length;x++){
	    for(int y=0;y<bs[0].length;y++){
		bs[x][y] = new BoardSquare(board[x][y]);
	    }
	}
	return bs;
    }
    
    BoardSquare[][] requestMove(int[] piece, int[] move, BoardSquare[][] board){
	int x,y;
	board[move[0]][move[1]].piece = board[piece[0]][piece[1]].piece;
	board[move[0]][move[1]].piece.x = move[0];
	board[move[0]][move[1]].piece.y = move[1];
        board[move[0]][move[1]].piece.hasMoved = true;
	board[piece[0]][piece[1]].hasPiece=false;
	board[move[0]][move[1]].hasPiece=true;
	return board;
    }
    
    boolean isCheckMate(Boolean c, BoardSquare[][] board){
	boolean checkMate = true;
	int[] kingPos = getKingLocation(c,board);
	int[][] moves;
	BoardSquare[][] bs=board.clone();
	//Check if king can move
	//Check each piece
	for(int x=0;x<bs.length;x++){
	    for(int y=0;y<bs[0].length;y++){
		if(bs[x][y].hasPiece && bs[x][y].piece.colour==c){
		    moves = bs[x][y].piece.generateMoves(board,randList);
                    if(pieceCanPreventCheck(kingPos,moves,board,c)){
                        checkMate = false;
                    }
		}
	    }
	}
	return checkMate;
	
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
    
    boolean pieceCanPreventCheck(int[] pos,int[][] moves, BoardSquare[][] board, boolean c){
	
	BoardSquare[][] bs = new BoardSquare[board.length][board[0].length];
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
	    bs = requestMove(pos,m,bs);
            int[] kingPos = getKingLocation(c,bs);
	    preventCheck = !isKingInCheck(colour,bs);
	    if(preventCheck){
		break;
	    }
	}
	return preventCheck;
    }
    
    boolean isKingInCheck(Boolean c,BoardSquare[][] board){
        int[] loc = getKingLocation(c,board);
        for(int x=0; x<board.length; x++){
            for(int y=0; y<board[0].length; y++){
                if(board[x][y].hasPiece && board[x][y].piece.colour != c){
                    if(board[x][y].piece.isValidMove(loc, board,randList)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    void printBoard(BoardSquare[][] bs){
	for(int y=0;y<bs.length;y++){
	    for(int x=0;x<bs[0].length;x++){
		if(bs[x][y].hasPiece){
		    System.out.print("["+bs[x][y].piece.textRepresentation+"]");
		}else{
		    System.out.print("[ ]");
		}
	    }
	    System.out.println();
	}
	System.out.println();
    }
    
    //Threads for evaluating board
    class ScoreFromNumPieces extends Thread{
	double score;
	BoardSquare[][] bs;
	Boolean colour;
	ScoreFromNumPieces(BoardSquare[][] bs,Boolean colour){
	    this.bs = bs;
	    this.score =0;
	    this.colour = colour;
	}
	@Override
	public void run(){
	   int blackPieceCount=0;
	   int whitePieceCount=0;
	    //Count the number of pieces each player has
	    for(BoardSquare[] bArray:bs){
		for(BoardSquare s:bArray){
		    if(s.hasPiece){
			if(s.piece.colour){
			    //Search for white
			    whitePieceCount++;
			}else{
			    //search for black
			    blackPieceCount++;
			}
		    }
		}
	    }
	    if(colour/*white*/){
		score = score + whitePieceCount+(16-blackPieceCount);//White pieces currently in play plus pieces black lost
	    }else{
		score = score+ blackPieceCount+(16-whitePieceCount);//Black pieces currently in play plus pieces white lost
	    }
	}
    }
    
    class ScoreFromPawnDistanceToEnd extends Thread{
	double score;
	BoardSquare[][] bs;
	Boolean colour;
	
	ScoreFromPawnDistanceToEnd(BoardSquare[][] bs,Boolean colour){
	    this.score = 0;
	    this.bs = bs;
	    this.colour = colour;
	}
	@Override
	public void run(){
	    if(colour/*white*/){
		for(int x=0;x<bs.length;x++){
		    for(int y=0;y<bs[0].length;y++){
			if(bs[x][y].hasPiece&&bs[x][y].piece.colour==colour&&bs[x][y].piece.textRepresentation.equals("p")){
			    score = score+(8-bs[x][y].piece.y);
			}
		    }
		}
	    }else{
		for(int x=0;x<bs.length;x++){
		    for(int y=0;y<bs[0].length;y++){
			if(bs[x][y].hasPiece&&!bs[x][y].piece.colour==colour&&bs[x][y].piece.textRepresentation.equals("P")){
			    score = score+(bs[x][y].piece.y);
			}
		    }
		}
	    }
	}
    }
    
    class ScoreFromPieceValue extends Thread{
	double score;
	BoardSquare[][] bs;
	Boolean colour;
	
	ScoreFromPieceValue(BoardSquare[][] bs,Boolean colour){
	    this.score = 0;
	    this.bs = bs;
	    this.colour = colour;
	}
	@Override
	public void run(){
	    if(/*white*/colour){
		for(int x=0;x<bs.length;x++){
		    for(int y=0;y<bs[0].length;y++){
			if(bs[x][y].hasPiece&&bs[x][y].piece.colour==colour){
			    switch(bs[x][y].piece.textRepresentation){
				case "r":
				    //Rook
				    score = score+5;
				    break;
				case "n":
				    //Knight
				    score = score +3;
				    break;
				case "b":
				    //Bishop
				    score = score +3;
				    break;
				case "k":
				    //King
				    score = score +100;
				    break;
				case "p":
				    score = score +1;
				    //Pawn
				    break;
				default:
				    break;
			    }
			}
		    }
		}
	    }else{
		for(int x=0;x<bs.length;x++){
		    for(int y=0;y<bs[0].length;y++){
			if(bs[x][y].hasPiece&&bs[x][y].piece.colour==colour){
			    switch(bs[x][y].piece.textRepresentation){
				case "R":
				    //Rook
				    score = score+5;
				    break;
				case "N":
				    //Knight
				    score = score +3;
				    break;
				case "B":
				    //Bishop
				    score = score +3;
				    break;
				case "K":
				    //King
				    score = score +100;
				    break;
				case "P":
				    score = score +1;
				    //Pawn
				    break;
				default:
				    break;
			    }
			}
		    }
		}
	    }
	}
    }
    /*double scoreFromNumPieces(BoardSquare[][] bs){
	double score=0;
	int blackPieceCount=0;
	int whitePieceCount=0;
	//Count the number of pieces each player has
	for(BoardSquare[] bArray:bs){
	    for(BoardSquare s:bArray){
		if(s.hasPiece){
		    if(s.piece.colour){
			//Search for white
			whitePieceCount++;
		    }else{
			//search for black
			blackPieceCount++;
		    }
		}
	    }
	}
	if(colour/*white){
	    score = score + whitePieceCount+(16-blackPieceCount);//White pieces currently in play plus pieces black lost
	}else{
	    score = score+ blackPieceCount+(16-whitePieceCount);//Black pieces currently in play plus pieces white lost
	}
	return score;
    }
    
    double scoreFromPawnDistanceToEnd(BoardSquare[][] bs){
	double score=0;
	if(colour/*white){
	    for(int x=0;x<bs.length;x++){
		for(int y=0;y<bs[0].length;y++){
		    if(bs[x][y].hasPiece&&bs[x][y].piece.colour&&bs[x][y].piece.textRepresentation.equals("p")){
			score = score+(8-bs[x][y].piece.y);
		    }
		}
	    }
	}else{
	    for(int x=0;x<bs.length;x++){
		for(int y=0;y<bs[0].length;y++){
		    if(bs[x][y].hasPiece&&!bs[x][y].piece.colour&&bs[x][y].piece.textRepresentation.equals("P")){
			score = score+(bs[x][y].piece.y);
		    }
		}
	    }
	}
	return score;
    }*/
}

