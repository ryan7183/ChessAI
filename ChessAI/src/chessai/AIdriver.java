package chessai;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Ryan and Parm
 * 
 * AIdriver runs the logic for the AI actions. AI uses an alpha beta algorithm 
 * to determine the optimal move to make.
 */
public class AIdriver extends Player{
    volatile Passer passer;//A class used to pass values between threads
    int[] move;//The position the ai will move the selected piece to 
    int[] pieceChosen;//Th epiece the ai is moving
    final int Max_Depth=2;//The number of moves the ai looks ahead for
    final double Max_Time = 60;//Max time the ai has to find a move, in seconds
    ArrayList<Piece> randList= new ArrayList();
    AIdriver(Passer p, boolean colour){
	this.colour = colour;//The colour the ai is controlling
	this.isHuman = false;//DEtermines if it is a person or the computer
	passer=p;//The passer
	move = new int[2];
	pieceChosen = new int[2];
    }
    
    /**Gets piece to move and the position to move it by activating the alpha beta algorithm
     * @param bs the state of the current board
     * @return the piece that has been chosen to be moved
     */
    @Override
    int[] requestPiece(BoardSquare[][] bs) {
	double value=alphaBeta(bs,0,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
	System.out.println(pieceChosen[0]+","+pieceChosen[1]+" to "+move[0]+","+move[1]+" final board score "+value);
	if(passer.newBoardAvailable){
	    return null;
	}
	return pieceChosen;
    }
    
    boolean getColour(){
	return colour;
    }

    /**Gets a list of pieces that are currently in play for the given team
     * @param bs the board
     * @param c which colour to check for white is true
     * @return the array of a teams pieces
     */
    Piece[] getPieces(BoardSquare[][] bs,boolean c){
	Piece[] p;//The list of pieces
	int numPieces=0;
	//Count the number of pieces still in play for the given player
	for(int x=0;x<bs.length;x++){
	    for(int y=0;y<bs.length;y++){
		if(bs[x][y].hasPiece&&bs[x][y].piece.colour==c){
		    numPieces++;
		}
	    }
	}
	p=new Piece[numPieces];//Make the array of pieces
	int count =0;
	//Get each piece
	for(int y=0;y<bs.length;y++){
	    for(int x=0;x<bs.length;x++){
		if(bs[x][y].hasPiece&&bs[x][y].piece.colour==c){
                    bs[x][y].piece.x=x;
                    bs[x][y].piece.y=y;
		    p[count]=bs[x][y].piece;
		    count++;
		}
	    }
	}
	return p;
    }
    
    /**Gets a list of pieces that are currently in play for both teams
     * @param bs the board
     * @param c which colour to check for white is true
     * @return the array of a teams pieces
     */
    Piece[] getPieces(BoardSquare[][] bs){
	Piece[] p;//The list of pieces
	int numPieces=0;
	//Count the number of pieces still in play for the given player
	for(int x=0;x<bs.length;x++){
	    for(int y=0;y<bs.length;y++){
		if(bs[x][y].hasPiece){
		    numPieces++;
		}
	    }
	}
	p=new Piece[numPieces];//Make the array of pieces
	int count =0;
	//Get each piece
	for(int x=0;x<bs.length;x++){
	    for(int y=0;y<bs.length;y++){
		if(bs[x][y].hasPiece){
                    bs[x][y].piece.x=x;
                    bs[x][y].piece.y=y;
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

    /**Returns the new positon of the piece being moved
     * @param piece the position of the piece being moved
     * @param bs the current state of the board
     * @return the position the piece is being moved to
     */
    @Override
    int[] requestMove(int[] piece, BoardSquare[][] bs) {
	if(passer.newBoardAvailable){
	    return null;
	}
	return move;
    }
    
    /**Runs the alpha beta algorithm. Determines the best move to play by looking 
     * a number of moves ahead and playing the move that puts it on a path to the
     * best outcome 
     * @param bs the board state
     * @param depth the depth of the current node
     * @param a the alpha score
     * @param b the beta score
     * @param maximize if maximizing score or not
     * @return the score for the move selected
     */
    double alphaBeta(BoardSquare[][] bs,int depth,double a, double b){
	long startTime = System.nanoTime();      

	Piece[] p=getPieces(bs,colour);//Get the pieces for the current team
	int[][] moves;
	int[] piecePos=new int[2];
	int[] tempPos = new int[2];
	double best=Double.NEGATIVE_INFINITY;
	double value=Double.NEGATIVE_INFINITY;
	BoardSquare[][] newBoard;
	//Find each piece
	
	
            for(Piece piece:p){
                    moves = piece.generateMoves(bs,randList);//Generate each move the piece can make
                    tempPos[0] = piece.x;
                    tempPos[1] = piece.y;
		    System.out.println("Looking at "+piece.textRepresentation+" num moves:"+moves.length);
		    if(isKingInCheck(colour,bs)&&!pieceCanPreventCheck(tempPos,moves,bs,colour)){
		    }else{
		    		    
		    //Explore each move the piece can make
                    for(int j=0;j<moves.length;j++){
			    int[] mCC=new int[2];
			    mCC[0] = piece.x;
			    mCC[1] = piece.y;
			    int[] mCCMove = moves[j].clone();
			    if(!moveCanPreventCheck(mCC,moves[j],bs,colour)){
				
			    }else{
			    newBoard = copyBoard(bs);//Make a copy of the board to make the next move
			    newBoard = requestMove(tempPos,moves[j],newBoard);
			    //if the game is not in a end state explore further
			    if(!isCheckMate(colour,newBoard)){
				value = minValue(newBoard,0,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,startTime);//Determine if a previously explored path or the new path is better
			    }
			    //If the new value is better than the previous best make the new move the best found
			    if(value>=best){
				best =value;
				move = moves[j];
				piecePos[0]=piece.x;
				piecePos[1]=piece.y;
				pieceChosen = piecePos;
			    }
			    }
			
                    }
		    }
		    if(((System.nanoTime()-startTime)/1000000000.0)>Max_Time){
			break;
		    }
                
            }
	
	long estimatedTime = System.nanoTime() - startTime;
	System.out.println("Turn took "+(double)estimatedTime/1000000000.0+" sceonds.");
	return best;
    }
    
    /**The max portion of the alpha beta algorithm. Determines the path with the max score 
     * @param bs The board state from the previous move
     * @param depth the number of moves ahead the algorithm has looked so far
     * @param a the alpha score
     * @param b the beta score
     * @return the score 
     */
    double maxValue(BoardSquare[][] bs,int depth,double a, double b,Long startTime){
	double v;
	double timeElapsed;
	Piece[] p=getPieces(bs,colour);
	int[][] moves;
	BoardSquare[][] newBoard;
	int[] piecePos=new int[2];
	int[] tempPos = new int[2];
	//If the algorithm is at its max depth or reached an end state returnt he current value of the board
	if(isKingInCheck(colour,bs)){
	    return boardEvaluation(bs);
	}
	timeElapsed = (System.nanoTime()-startTime)/1000000000.0;
	if(depth >= Max_Depth||timeElapsed>=Max_Time||isCheckMate(!colour,bs)||isKingInCheck(!colour,bs)){
	    return boardEvaluation(bs);
	}
	v=Double.NEGATIVE_INFINITY;
	//Explore all moves of each piece
            for(Piece piece:p){
           
                    moves = piece.generateMoves(bs,randList);
                    tempPos[0] = piece.x;
                    tempPos[1] = piece.y;
		    if(isKingInCheck(colour,bs)&&!pieceCanPreventCheck(tempPos,moves,bs,colour)){}else{
                    for(int j=0;j<moves.length;j++){
		
			    newBoard = copyBoard(bs);
			    newBoard = requestMove(tempPos,moves[j],newBoard);
			    if(!isCheckMate(colour,newBoard)){
				v = max(v,minValue(newBoard,depth+1,a,b,startTime));
			    }
			    //if score is greater than the value of beta don't look any further on this path
			    if(v>=b){
				return v;
			    }
			    a = min(a,v);
			
                    }
                }
		
            }
	
	return v;
    }
    /**The min portion of the alpha beta algorithm. Determines the path with a 
     * minimum score, therefor the best path for the opponent
     * @param bs the board sate from the last move
     * @param depth the current number of moves ahead the algorithm has looked
     * @param a the alpha score
     * @param b the beta score
     * @return the nodes score
     */
    double minValue(BoardSquare[][] bs,int depth,double a, double b,Long startTime){
	double v;
	double timeElapsed;
	Piece[] p=getPieces(bs,!colour);
	int[][] moves;
	BoardSquare[][] newBoard;
	int[] piecePos=new int[2];
	int[] tempPos = new int[2];
	if(isKingInCheck(!colour,bs)){
	    return boardEvaluation(bs);//Double.POSITIVE_INFINITY;
	}
	timeElapsed = (System.nanoTime()-startTime)/1000000000.0;
	if(depth >= Max_Depth||timeElapsed>=Max_Time||isCheckMate(colour,bs)||isKingInCheck(colour,bs)){
	    //Return value if at max depth, max time, checkmate, or check
	    return boardEvaluation(bs);
	}
	v=Double.POSITIVE_INFINITY;
	//Look at each move of all pieces
            for(Piece piece:p){
                    moves = piece.generateMoves(bs,randList);
                    tempPos[0] = piece.x;
                    tempPos[1] = piece.y;
		    if(isKingInCheck(colour,bs)&&!pieceCanPreventCheck(tempPos,moves,bs,colour)){}else{
                    for(int j=0;j<moves.length;j++){
			
			
                        newBoard = copyBoard(bs);
                        newBoard = requestMove(tempPos,moves[j],newBoard);
                        if(!isCheckMate(!colour,newBoard)){
                            v = min(v,maxValue(newBoard,depth+1,a,b,startTime));
                        }
			//If score is less than alpha don't look at path any further
                        if(v<=a){
                            return v;
                        }
                        b = min(b,v);
		    }
		    }
              
            }
	
	return v;
    }
    
    /**Return the maximum value of the two given
     * @param a one value to check
     * @param b the second value to check
     * @return the maximum value
     */
    double max(double a, double b){
	if(a>b){
	    return a;
	}
	return b;
    }
    
    /**Return the minimum value of the two given
     * @param a one value to check
     * @param b the second value to check
     * @return the minimum value
     */
    double min(double a, double b){
	if(a<b){
	    return a;
	}
	return b;
    }
    
    /**Evaluates the fitness of the board given for the current player
     * @param bs the board state
     * @return the boards score
     */
    double boardEvaluation(BoardSquare[][] bs){
	double score=0;//Greater value beter for this player
	int[] kingLocation = getKingLocation(colour,bs);
	int[] opponentKingLoc = getKingLocation(!colour,bs);
	//Run each factor that contributes to the score as sepertate threads
	//Threads that don't require a piece list
	ControlMiddle four = new ControlMiddle(bs,colour);
	//Start threads that don't need a move list
	four.start();
	//The threads that require a list of pieces
	Piece[] pieces = getPieces(bs);
	ScoreFromNumPieces one = new ScoreFromNumPieces(bs,colour,pieces,kingLocation);//Determines the score for the number of pieces each team has
	ScoreFromPawnDistanceToEnd two = new ScoreFromPawnDistanceToEnd(bs,colour,pieces,opponentKingLoc);//Determines the score for how far each pawn has moved
	ScoreFromPieceValue three = new ScoreFromPieceValue(bs,colour,pieces);//Determines the score from the value of pieces each team still has in play
	//Start threads that need a move lists
	one.start();
	two.start();
	three.start();
	
	try {
	    //Wait for each thread to finish its evaluation
	    one.join();
	    two.join();
	    three.join();
	    four.join();
	    score = one.score+two.score+three.score+four.score;//Add the scores
	} catch (InterruptedException ex) {
	    
	}
	return score;
    }
    
    /**Creates a deep copy of a board state
     * @param board the board to copy
     * @return the copy of a board state
     */
    BoardSquare[][] copyBoard(BoardSquare[][] board){
	BoardSquare[][] bs = new BoardSquare[board.length][board[0].length];
	for(int x=0;x<bs.length;x++){
	    for(int y=0;y<bs[0].length;y++){
		bs[x][y] = new BoardSquare(board[x][y]);
	    }
	}
	return bs;
    }
    
    /**Makes a move on the board
     * @param piece the piece to be moved
     * @param move where the piece is being moved to
     * @param board the board being played on
     * @return the board with the move done
     */
    BoardSquare[][] requestMove(int[] piece, int[] move, BoardSquare[][] board){
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
        randList.add(board[move[0]][move[1]].piece);
	return board;
    }
    
    /**Checks if the board is in check mate
     * @param c the colour of the team being check
     * @param board the board being checked
     * @return true if in check mate, false if not
     */
    boolean isCheckMate(Boolean c, BoardSquare[][] board){
	boolean checkMate = true;
	int[] kingPos = getKingLocation(c,board);
	int[][] moves;
        int[] pos = new int[2];
	BoardSquare[][] bs=copyBoard(board);
	//Check if king can move
	//Check each piece
	for(int x=0;x<bs.length;x++){
	    for(int y=0;y<bs[0].length;y++){
		if(bs[x][y].hasPiece && bs[x][y].piece.colour==c){
		    moves = bs[x][y].piece.generateMoves(board, randList);
                    pos[0]=x;
                    pos[1]=y;
                    if(pieceCanPreventCheck(pos,moves,board,c)){
                        checkMate = false;
                    }
		}
	    }
	}
	
	return checkMate;
    }
    
    /**Finds the location of the king for the given team
     * @param c the colour of the king being found
     * @param bs the board 
     * @return the location of the king
     */
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
    
    /**Checks if a piece can prevent check
     * @param pos the position of the piece being checked
     * @param moves the moves the piece can make
     * @param board the board state
     * @param c the colour of the team being checked
     * @return true if the piece can prevent check, false if not
     */
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
	    bs =copyBoard(board);
            boolean possibleMove = false;
            possibleMove = bs[pos[0]][pos[1]].piece.isValidMove(m, bs, temp);
            bs = requestMove(pos,m,bs);
            int[] kingPos = getKingLocation(c,bs);
	    preventCheck = !isKingInCheck(colour,bs);
	    if(preventCheck && possibleMove){
		break;
	    }
            else{
                preventCheck = false;
            }
            for(int x=0;x<bs.length;x++){
                for(int y=0;y<bs[0].length;y++){
                    bs[x][y] = new BoardSquare(board[x][y]);
                }
            }
	}
	return preventCheck;
    }
    
    boolean moveCanPreventCheck(int[] pos,int[] move,BoardSquare[][] board, boolean c){
	BoardSquare[][] bs = copyBoard(board);
	bs = requestMove(pos,move,bs);
	if(isKingInCheck(c,bs)){
	    return false;
	}
	return true;
    }
    
    /**Checks if the king is in check
     * @param c The colour of the team being checked
     * @param board the board state
     * @return true if king is in check, false if not
     */
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
    
    /**Prints the board state to console
     */
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
    /**Class that evaluates the score from the number of pieces each team has*/
    class ScoreFromNumPieces extends Thread{
	double score;
	BoardSquare[][] bs;
	Boolean colour;
	Piece[] pieces; 
	int blackPieceCount;
	int whitePieceCount;
	int[] kingPos;
	/**
	 * @param bs the board state
	 * @param colour the colour of the current player
	 */
	ScoreFromNumPieces(BoardSquare[][] bs,Boolean colour,Piece[] pieces,int[] kingPos){
	    this.bs = bs;
	    this.score =0;
	    this.colour = colour;
	    this.pieces = pieces;
	    this.kingPos = kingPos;
	}
	@Override
	public void run(){
	    long startTime = System.nanoTime(); 
	    blackPieceCount=0;
	    whitePieceCount=0;
	    //Count the number of pieces each player has
	    for(Piece p:pieces){
		if(p.colour){
		    whitePieceCount++;
		}else{
		    blackPieceCount++;
		}
	    }
	    if(colour/*white*/){
		score = score + ((whitePieceCount+(16-blackPieceCount))*10);//White pieces currently in play plus pieces black lost
		if(whitePieceCount>blackPieceCount){
		    score = score+100;
		}else{
		    
		}
	    }else{
		score = score+ ((blackPieceCount+(16-whitePieceCount))*10);//Black pieces currently in play plus pieces white lost
		if(blackPieceCount>whitePieceCount){
		    score = score+100;
		}else{
		    
		}
	    }
	    long estimatedTime = System.nanoTime() - startTime;
	//System.out.println("Thread one took "+(double)estimatedTime/1000000000.0+" sceonds."); 
	}
	public double kingsGaurds(){
	    double gaurdScore=0;
	    //Left
	   if(kingPos[0]>0&&bs[kingPos[0]-1][kingPos[1]].hasPiece&&bs[kingPos[0]-1][kingPos[1]].piece.colour==colour){
	       gaurdScore += 80;
	       if(bs[kingPos[0]-1][kingPos[1]].piece.textRepresentation.equals("p")||bs[kingPos[0]-1][kingPos[1]].piece.textRepresentation.equals("P")){
		   gaurdScore+=40;
	       }else{gaurdScore-=20;}
	   }
	   //UpperLeft
	   if(kingPos[0]>0&&kingPos[1]>0&&bs[kingPos[0]-1][kingPos[1]-1].hasPiece&&bs[kingPos[0]-1][kingPos[1]-1].piece.colour==colour){
	       gaurdScore += 80;
	       if(bs[kingPos[0]-1][kingPos[1]-1].piece.textRepresentation.equals("p")||bs[kingPos[0]-1][kingPos[1]-1].piece.textRepresentation.equals("P")){
		   gaurdScore+=40;
	       }else{gaurdScore-=20;}
	   }
	   //Upper
	   if(kingPos[1]>0&&bs[kingPos[0]][kingPos[1]-1].hasPiece&&bs[kingPos[0]][kingPos[1]-1].piece.colour==colour){
	       gaurdScore += 80;
	       if(bs[kingPos[0]][kingPos[1]-1].piece.textRepresentation.equals("p")||bs[kingPos[0]][kingPos[1]-1].piece.textRepresentation.equals("P")){
		   gaurdScore+=40;
	       }else{gaurdScore-=20;}
	   }
	   //upper Right
	   if(kingPos[0]<7&&kingPos[1]>0&&bs[kingPos[0]+1][kingPos[1]-1].hasPiece&&bs[kingPos[0]+1][kingPos[1]-1].piece.colour==colour){
	       gaurdScore += 80;
	       if(bs[kingPos[0]+1][kingPos[1]-1].piece.textRepresentation.equals("p")||bs[kingPos[0]+1][kingPos[1]-1].piece.textRepresentation.equals("P")){
		   gaurdScore+=40;
	       }else{gaurdScore-=20;}
	   }
	   //Right
	   if(kingPos[0]<7&&bs[kingPos[0]+1][kingPos[1]].hasPiece&&bs[kingPos[0]+1][kingPos[1]].piece.colour==colour){
	       gaurdScore += 80;
	       if(bs[kingPos[0]+1][kingPos[1]].piece.textRepresentation.equals("p")||bs[kingPos[0]+1][kingPos[1]].piece.textRepresentation.equals("P")){
		   gaurdScore+=40;
	       }else{gaurdScore-=20;}
	   }
	   //LowerRight
	   if(kingPos[0]<7&&kingPos[1]<7&&bs[kingPos[0]+1][kingPos[1]+1].hasPiece&&bs[kingPos[0]+1][kingPos[1]+1].piece.colour==colour){
	       gaurdScore += 80;
	       if(bs[kingPos[0]+1][kingPos[1]+1].piece.textRepresentation.equals("p")||bs[kingPos[0]+1][kingPos[1]+1].piece.textRepresentation.equals("P")){
		   gaurdScore+=40;
	       }else{gaurdScore-=20;}
	   }
	   //Lower
	   if(kingPos[1]<7&&bs[kingPos[0]][kingPos[1]+1].hasPiece&&bs[kingPos[0]][kingPos[1]+1].piece.colour==colour){
	       gaurdScore += 80;
	       if(bs[kingPos[0]][kingPos[1]+1].piece.textRepresentation.equals("p")||bs[kingPos[0]][kingPos[1]+1].piece.textRepresentation.equals("P")){
		   gaurdScore+=40;
	       }else{gaurdScore-=20;}
	   }
	   //LowerLeft
	   if(kingPos[0]>0&&kingPos[1]<7&&bs[kingPos[0]-1][kingPos[1]+1].hasPiece&&bs[kingPos[0]-1][kingPos[1]+1].piece.colour==colour){
	       gaurdScore += 80;
	       if(bs[kingPos[0]-1][kingPos[1]+1].piece.textRepresentation.equals("p")||bs[kingPos[0]-1][kingPos[1]+1].piece.textRepresentation.equals("P")){
		   gaurdScore+=40;
	       }else{gaurdScore-=20;}
	   }
	   //Once half the pieces are gone reduce the value of gaurding the king
	   if((blackPieceCount+whitePieceCount)<16){
	       gaurdScore = gaurdScore/2.0;
	   }
	   return gaurdScore;
	}
	
    }
    
    //Class that evaluates the score from the distance each pawn has moved from their starting position 
    class ScoreFromPawnDistanceToEnd extends Thread{
	double score;
	BoardSquare[][] bs;
	Boolean colour;
	Piece[] pieces;
	int[] kingPos;
	/**
	 * @param bs the board state
	 * @param colour the colour of the current player
	 */
	ScoreFromPawnDistanceToEnd(BoardSquare[][] bs,Boolean colour, Piece[] pieces,int[] oppKing){
	    this.score = 0;
	    this.bs = bs;
	    this.colour = colour;
	    this.pieces = pieces;
	    this.kingPos = oppKing;
	}
	@Override
	public void run(){
	    long startTime = System.nanoTime(); 
	    if(colour/*white*/){
		for(Piece p : pieces){
		    if(p.colour==colour&&p.textRepresentation.equals("p")){
			score = score+((7-p.y)*15);
			    if(p.y==0){
				score += 50;
			    }
		    }
		}
	    }else{
		for(Piece p : pieces){
		    if(p.colour==colour&&p.textRepresentation.equals("P")){
			score = score+((p.y)*15);
			    if(p.y==7){
				score += 50;
			    }
		    }
		}
	    }
	    score+=toCloseToOpponentKing();
	    long estimatedTime = System.nanoTime() - startTime;
	//System.out.println("Thread two took "+(double)estimatedTime/1000000000.0+" sceonds."); 
	}
	//Negative score from pieces being to close to opponents king
	public double toCloseToOpponentKing(){
	    double gaurdScore=0;
	    //Left
	   if(kingPos[0]>0&&bs[kingPos[0]-1][kingPos[1]].hasPiece&&bs[kingPos[0]-1][kingPos[1]].piece.colour==!colour){
	       gaurdScore -= 80;
	   }
	   //UpperLeft
	   if(kingPos[0]>0&&kingPos[1]>0&&bs[kingPos[0]-1][kingPos[1]-1].hasPiece&&bs[kingPos[0]-1][kingPos[1]-1].piece.colour==!colour){
	       gaurdScore -= 80;
	   }
	   //Upper
	   if(kingPos[1]>0&&bs[kingPos[0]][kingPos[1]-1].hasPiece&&bs[kingPos[0]][kingPos[1]-1].piece.colour==!colour){
	       gaurdScore -= 80;
	   }
	   //upper Right
	   if(kingPos[0]<7&&kingPos[1]>0&&bs[kingPos[0]+1][kingPos[1]-1].hasPiece&&bs[kingPos[0]+1][kingPos[1]-1].piece.colour==!colour){
	       gaurdScore -= 80;
	   }
	   //Right
	   if(kingPos[0]<7&&bs[kingPos[0]+1][kingPos[1]].hasPiece&&bs[kingPos[0]+1][kingPos[1]].piece.colour==!colour){
	       gaurdScore -= 80;
	   }
	   //LowerRight
	   if(kingPos[0]<7&&kingPos[1]<7&&bs[kingPos[0]+1][kingPos[1]+1].hasPiece&&bs[kingPos[0]+1][kingPos[1]+1].piece.colour==!colour){
	       gaurdScore -= 80;
	   }
	   //Lower
	   if(kingPos[1]<7&&bs[kingPos[0]][kingPos[1]+1].hasPiece&&bs[kingPos[0]][kingPos[1]+1].piece.colour==!colour){
	       gaurdScore -= 80;
	   }
	   //LowerLeft
	   if(kingPos[0]>0&&kingPos[1]<7&&bs[kingPos[0]-1][kingPos[1]+1].hasPiece&&bs[kingPos[0]-1][kingPos[1]+1].piece.colour==!colour){
	       gaurdScore -= 80;
	   }
	   return gaurdScore;
	}
	
	
    }
    
    //Class that evaulates the score from the value of each piece still in play
    //Reference http://chessprogramming.wikispaces.com/Point+Value for values
    class ScoreFromPieceValue extends Thread{
	double score;
	BoardSquare[][] bs;
	Boolean colour;
	Piece[] pieces;
	Piece blackKing;
	Piece whiteKing;
	/**
	 * @param bs the board state
	 * @param colour the colour of the current player
	 */
	ScoreFromPieceValue(BoardSquare[][] bs,Boolean colour,Piece[] pieces){
	    this.score = 0;
	    this.bs = bs;
	    this.colour = colour;
	    this.pieces = pieces;
	}
	@Override
	public void run(){
	    long startTime = System.nanoTime(); 
	    ArrayList<Piece> moveList = new ArrayList();
		for(Piece p:pieces){
		    
			if(p.colour==colour){
			    switch(p.textRepresentation){
				case "r":
				    //Rook
				    score = score+525;
				    break;
				case "n":
				    //Knight
				    score = score +350;
				    break;
				case "b":
				    //Bishop
				    score = score +350;
				    break;
				case "k":
				    //King
				    score = score +10000;
				    break;
				case "p":
				    score = score +100;
				    //Pawn
				    break;
				case "q":
				    score +=1000;
				default:
				    break;
			    }
			    //Count total number of moves
			    score += p.generateMoves(bs,moveList).length;
			}else{
			    switch(p.textRepresentation){
				case "R":
				    //Rook
				    score = score-525;
				    break;
				case "N":
				    //Knight
				    score = score -350;
				    break;
				case "B":
				    //Bishop
				    score = score -350;
				    break;
				case "K":
				    //King
				    score = score -10000;
				    break;
				case "P":
				    score = score -100;
				    //Pawn
				    break;
				case "Q":
				    score -=1000;
				    break;
				default:
				    break;
			    }
			    //Count oppenents total number of moves
			    score -= p.generateMoves(bs,moveList).length;
			}
		    
		}
		scoreFromNumKingMoves();
		long estimatedTime = System.nanoTime() - startTime;
	//System.out.println("Thread three took "+(double)estimatedTime/1000000000.0+" sceonds."); 
	}
	public void scoreFromNumKingMoves(){
	    ArrayList<Piece> moveList=new ArrayList();
	    for(Piece p:pieces){
		if(p.textRepresentation.equals("k")){
		    whiteKing = new King((King)p);
		}else if(p.textRepresentation.equals("K")){
		    blackKing = new King((King)p);;
		}
	    }
	    if(colour/*white*/){
		score+=(whiteKing.generateMoves(bs,moveList).length-blackKing.generateMoves(bs,moveList).length)*5;
	    }else{//black
		score+=(blackKing.generateMoves(bs,moveList).length-whiteKing.generateMoves(bs,moveList).length)*5;
	    }
	}
    }
    
    /**Determines the score from controlling the middle of the board, pieces surrounding the king, and how many pawns of a colour are in any column
     */
    class ControlMiddle extends Thread{
	double score;
	BoardSquare[][] bs;
	Boolean colour;
	ControlMiddle(BoardSquare[][] bs,Boolean colour){
	    score = 0 ; 
	    this.bs = bs;
	    this.colour = colour;
	    
	}
	
	@Override
	public void run(){
	    long startTime = System.nanoTime(); 
	    for(int x=2;x<6;x++){
		for(int y=2;y<6;y++){
		    if(bs[x][y].hasPiece && bs[x][y].piece.colour==colour){
			score+=20;
		    }
		}
	    }
	    score += pawnsInFile();
	    long estimatedTime = System.nanoTime() - startTime;
	//System.out.println("Thread four took "+(double)estimatedTime/1000000000.0+" sceonds.");   
	}
	public double pawnsInFile(){
	    int fileScore=0;
	    int fileCount=0;
	    for(int x=0;x<bs.length;x++){
		for(int y=0;y<bs[0].length;y++){
		    if(bs[x][y].hasPiece/*&&bs[x][y].piece.colour==colour*/&&(bs[x][y].piece.textRepresentation.equals("p")||bs[x][y].piece.textRepresentation.equals("P"))){
			fileCount++;
		    }
		}
		fileScore += (-1)*(fileCount*3);
	    }
	    
	    return fileScore;
	}
	
    }
    
}

