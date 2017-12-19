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
    final int Max_Depth=3; 
    AIdriver(Passer p, boolean colour){
	this.colour = colour;
	passer=p;
	depth=4;
	move = new int[2];
	pieceChosen = new int[2];
    }
    
    //GEts piece to move and the position to move it
    @Override
    int[] requestPiece(BoardSquare[][] bs) {
	return null;
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
	//Count the number of pieces still in playfor this player
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
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	//return move;
    }
    /**
     * @param bs the board state
     * @param depth the depth of the current node
     * @param a the alpha score
     * @param b the beta score
     * @param maximize if maximizing score or not
     */
    double alphaBeta(BoardSquare[][] bs,int depth,double a, double b, boolean maximize){
	Piece[] pieces;
	int[][] possibleMoves;
	int[] piecePos=new int[2];
	BoardSquare[][] newBoard;
	Piece p;
	double value;
	if(depth >= Max_Depth){
	    return scoreFromNumPieces(bs);
	}
	if(maximize){
	    value = Double.NEGATIVE_INFINITY;
	    //check child nodes
	    pieces=getPieces(bs,false);
	    for(int i=0;i<pieces.length;i++){
		p=pieces[i];
		possibleMoves = p.generateMoves(bs);
		for(int j=0;j<possibleMoves.length;j++){
		    newBoard = copyBoard(bs);
		    piecePos[0]=p.x;
		    piecePos[1]=p.y;
		    newBoard = requestMove(piecePos,possibleMoves[j],newBoard);//Get board for the next child node
		    value = max(value,alphaBeta(newBoard,depth+1,a,b,false));
		    a=max(a,value);
		    if(b<=a){
			return value;
		    }
		}
	    }
	}else{
	    value = Double.POSITIVE_INFINITY;
	    pieces=getPieces(bs,true);
	    for(int i=0;i<pieces.length;i++){
		p=pieces[i];
		possibleMoves = p.generateMoves(bs);
		for(int j=0;j<possibleMoves.length;j++){
		    newBoard = copyBoard(bs);
		    piecePos[0]=p.x;
		    piecePos[1]=p.y;
		    newBoard = requestMove(piecePos,possibleMoves[j],newBoard);//Get board for the next child node
		    value = min(value,alphaBeta(newBoard,depth+1,a,b,false));
		    a=min(a,value);
		    if(b<=a){
			return value;
		    }
		}
	    }
	    
	}
	return value;
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
	score = scoreFromNumPieces(bs);
	return score;
    }
    
    double scoreFromNumPieces(BoardSquare[][] bs){
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
	if(colour/*white*/){
	    score = score + whitePieceCount+(16-blackPieceCount);//White pieces currently in play plus pieces black lost
	}else{
	    score = score+ blackPieceCount+(16-whitePieceCount);//Black pieces currently in play plus pieces white lost
	}
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
}
