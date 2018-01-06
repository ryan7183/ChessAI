package chessai;

import java.util.ArrayList;

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
	System.out.println(pieceChosen[0]+","+pieceChosen[1]+" to "+move[0]+","+move[1]);
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
	for(int x=0;x<bs.length;x++){
	    for(int y=0;y<bs.length;y++){
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
	Piece[] p=getPieces(bs,colour);//Get the pieces for the current team
	int[][] moves;
	int[] piecePos=new int[2];
	int[] tempPos = new int[2];
	double best=Double.NEGATIVE_INFINITY;
	double value=Double.NEGATIVE_INFINITY;
	BoardSquare[][] newBoard;
	//Find each piece
	for(int x=0;x<bs.length;x++){
            for(int y=0;y<bs[0].length;y++){
                if(bs[x][y].hasPiece && bs[x][y].piece.colour==colour){
                    moves = bs[x][y].piece.generateMoves(bs,randList);//Generate each move the piece can make
                    tempPos[0] = x;
                    tempPos[1] = y;
		    System.out.println("Looking at "+bs[x][y].piece.textRepresentation+" num moves:"+moves.length);
		    if(isKingInCheck(colour,bs)&&!pieceCanPreventCheck(tempPos,moves,bs,colour)){
			System.out.println("Skipped Piece:"+ x+","+y);
		    }else{
		    		    
		    //Explore each move the piece can make
                    for(int j=0;j<moves.length;j++){
			    int[] mCC=new int[2];
			    mCC[0] = x;
			    mCC[1] = y;
			    int[] mCCMove = moves[j].clone();
			    if(!moveCanPreventCheck(mCC,moves[j],bs,colour)){
				
			    }else{
			    newBoard = copyBoard(bs);//Make a copy of the board to make the next move
			    newBoard = requestMove(tempPos,moves[j],newBoard);
			    //if the game is not in a end state explore further
			    if(!isCheckMate(colour,newBoard)){
				value = minValue(newBoard,0,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);//Determine if a previously explored path or the new path is better
				//System.out.println(value);
			    }
			    //If the new value is better than the previous best make the new move the best found
			    if(value>=best){
				best =value;
				move = moves[j];
				piecePos[0]=x;
				piecePos[1]=y;
				pieceChosen = piecePos;
			    }
			    }
			
                    }
		    }
                }
            }
	}
	//System.out.print(colour+""+bs[piecePos[0]][piecePos[1]].piece.textRepresentation+""+bs[piecePos[0]][piecePos[1]].piece.colour);
	return best;
    }
    
    /**The max portion of the alpha beta algorithm. Determines the path with the max score 
     * @param bs The board state from the previous move
     * @param depth the number of moves ahead the algorithm has looked so far
     * @param a the alpha score
     * @param b the beta score
     * @return the score 
     */
    double maxValue(BoardSquare[][] bs,int depth,double a, double b){
	double v;
	Piece[] p=getPieces(bs,colour);
	int[][] moves;
	BoardSquare[][] newBoard;
	int[] piecePos=new int[2];
	int[] tempPos = new int[2];
	//If the algorithm is at its max depth or reached an end state returnt he current value of the board
	if(isKingInCheck(colour,bs)){
	    return Double.NEGATIVE_INFINITY;
	}
	if(depth >= Max_Depth||isCheckMate(!colour,bs)||isKingInCheck(!colour,bs)){
	    //System.out.println(isCheckMate(!colour,bs)+" "+isKingInCheck(!colour,bs));
	    return boardEvaluation(bs);
	}
	v=Double.NEGATIVE_INFINITY;
	//Explore all moves of each piece
	for(int x=0;x<bs.length;x++){
            for(int y=0;y<bs[0].length;y++){
                if(bs[x][y].hasPiece && bs[x][y].piece.colour == colour){
                    moves = bs[x][y].piece.generateMoves(bs,randList);
                    tempPos[0] = x;
                    tempPos[1] = y;
		    if(isKingInCheck(colour,bs)&&!pieceCanPreventCheck(tempPos,moves,bs,colour)){}else{
                    for(int j=0;j<moves.length;j++){
		
			    newBoard = copyBoard(bs);
			    newBoard = requestMove(tempPos,moves[j],newBoard);
			    if(!isCheckMate(colour,newBoard)){
				v = max(v,minValue(newBoard,depth+1,a,b));
			    }
			    //if score is greater than the value of beta don't look any further on this path
			    if(v>=b){
				return v;
			    }
			    a = min(a,v);
			
                    }
                }
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
    double minValue(BoardSquare[][] bs,int depth,double a, double b){
	double v;
	Piece[] p=getPieces(bs,!colour);
	int[][] moves;
	BoardSquare[][] newBoard;
	int[] piecePos=new int[2];
	int[] tempPos = new int[2];
	if(isKingInCheck(!colour,bs)){
	    return Double.POSITIVE_INFINITY;
	}
	if(depth >= Max_Depth||isCheckMate(colour,bs)||isKingInCheck(colour,bs)){
	    //System.out.println(isCheckMate(colour,bs)+" "+isKingInCheck(colour,bs));
	    return boardEvaluation(bs);
	}
	v=Double.POSITIVE_INFINITY;
	//Look at each move of all pieces
	for(int x=0;x<bs.length;x++){
            for(int y=0; y<bs[0].length; y++){
                if(bs[x][y].hasPiece && bs[x][y].piece.colour){
                    moves = bs[x][y].piece.generateMoves(bs,randList);
                    tempPos[0] = x;
                    tempPos[1] = y;
		    if(isKingInCheck(colour,bs)&&!pieceCanPreventCheck(tempPos,moves,bs,colour)){}else{
                    for(int j=0;j<moves.length;j++){
			
			
                        newBoard = copyBoard(bs);
                        newBoard = requestMove(tempPos,moves[j],newBoard);
                        if(!isCheckMate(!colour,newBoard)){
                            v = min(v,maxValue(newBoard,depth+1,a,b));
                        }
			//If score is less than alpha don't look at path any further
                        if(v<=a){
                            return v;
                        }
                        b = min(b,v);
		    }
		    }
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
	//Run each factor towards that contributes to the score as sepertate threads
	ScoreFromNumPieces one = new ScoreFromNumPieces(bs,colour);//Determines the score for the number of pieces each team has
	ScoreFromPawnDistanceToEnd two = new ScoreFromPawnDistanceToEnd(bs,colour);//Determines the score for how far each pawn has moved
	ScoreFromPieceValue three = new ScoreFromPieceValue(bs,colour);//Determines the score from the value of pieces each team still has in play
	ControlMiddle four = new ControlMiddle(bs,colour,getKingLocation(colour,bs));
	one.start();
	two.start();
	three.start();
	four.start();
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
            if(possibleMove){
                bs = requestMove(pos,m,bs);
            }
            int[] kingPos = getKingLocation(c,bs);
	    preventCheck = !isKingInCheck(colour,bs);
	    if(preventCheck && possibleMove){
		break;
	    }
            else{
                preventCheck = false;
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
	/**
	 * @param bs the board state
	 * @param colour the colour of the current player
	 */
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
		score = score + ((whitePieceCount+(16-blackPieceCount))*10);//White pieces currently in play plus pieces black lost
		if(whitePieceCount>blackPieceCount){
		    score = score+200;
		}else{
		    
		}
	    }else{
		score = score+ ((blackPieceCount+(16-whitePieceCount))*10);//Black pieces currently in play plus pieces white lost
		if(blackPieceCount>whitePieceCount){
		    score = score+200;
		}else{
		    
		}
	    }
	}
    }
    
    //Class that evaluates the score from the distance each pawn has moved from their starting position 
    class ScoreFromPawnDistanceToEnd extends Thread{
	double score;
	BoardSquare[][] bs;
	Boolean colour;
	/**
	 * @param bs the board state
	 * @param colour the colour of the current player
	 */
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
			    score = score+((8-bs[x][y].piece.y)*10);
			    if(bs[x][y].piece.y==0){
				score += 50;
			    }
			}
		    }
		}
	    }else{
		for(int x=0;x<bs.length;x++){
		    for(int y=0;y<bs[0].length;y++){
			if(bs[x][y].hasPiece&&!bs[x][y].piece.colour==colour&&bs[x][y].piece.textRepresentation.equals("P")){
			    score = score+((bs[x][y].piece.y)*10);
			    if(bs[x][y].piece.y==7){
				score += 50;
			    }
			}
		    }
		}
	    }
	}
    }
    
    //Class that evaulates the score from the value of each piece still in play
    //Reference http://chessprogramming.wikispaces.com/Point+Value for values
    class ScoreFromPieceValue extends Thread{
	double score;
	BoardSquare[][] bs;
	Boolean colour;
	/**
	 * @param bs the board state
	 * @param colour the colour of the current player
	 */
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
				    score = score+525;
				    break;
				case "N":
				    //Knight
				    score = score +350;
				    break;
				case "B":
				    //Bishop
				    score = score +350;
				    break;
				case "K":
				    //King
				    score = score +10000;
				    break;
				case "P":
				    score = score +100;
				    //Pawn
				    break;
				case "Q":
				    score +=1000;
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
    
    class ControlMiddle extends Thread{
	double score;
	BoardSquare[][] bs;
	Boolean colour;
	int[] kingPos;
	ControlMiddle(BoardSquare[][] bs,Boolean colour,int[] kingPos){
	    score = 0 ; 
	    this.bs = bs;
	    this.colour = colour;
	    this.kingPos = kingPos;
	    
	}
	
	@Override
	public void run(){
	    for(int x=2;x<6;x++){
		for(int y=2;y<6;y++){
		    if(bs[x][y].hasPiece && bs[x][y].piece.colour==colour){
			score+=10;
		    }
		}
	    }
	    score+=kingsGaurds();
	    //score += pawnsInFile();
	}
	
	public double pawnsInFile(){
	    int fileScore=0;
	    int fileCount=0;
	    for(int x=0;x<bs.length;x++){
		for(int y=0;y<bs[0].length;y++){
		    if(bs[x][y].hasPiece&&bs[x][y].piece.colour==colour&&(bs[x][y].piece.textRepresentation.equals("p")||bs[x][y].piece.textRepresentation.equals("P"))){
			fileCount++;
		    }
		}
		fileScore += (-1)*(fileCount*2);
	    }
	    
	    return fileScore;
	}
	
	public double kingsGaurds(){
	    int gaurdScore=0;
	    //Left
	   if(kingPos[0]>0&&bs[kingPos[0]-1][kingPos[1]].hasPiece&&bs[kingPos[0]-1][kingPos[1]].piece.colour==colour){
	       gaurdScore += 100;
	       if(bs[kingPos[0]-1][kingPos[1]].piece.textRepresentation.equals("p")||bs[kingPos[0]-1][kingPos[1]].piece.textRepresentation.equals("P")){
		   gaurdScore+=20;
	       }else{gaurdScore-=20;}
	   }
	   //UpperLeft
	   if(kingPos[0]>0&&kingPos[1]>0&&bs[kingPos[0]-1][kingPos[1]-1].hasPiece&&bs[kingPos[0]-1][kingPos[1]-1].piece.colour==colour){
	       gaurdScore += 100;
	       if(bs[kingPos[0]-1][kingPos[1]-1].piece.textRepresentation.equals("p")||bs[kingPos[0]-1][kingPos[1]-1].piece.textRepresentation.equals("P")){
		   gaurdScore+=20;
	       }else{gaurdScore-=20;}
	   }
	   //Upper
	   if(kingPos[1]>0&&bs[kingPos[0]][kingPos[1]-1].hasPiece&&bs[kingPos[0]][kingPos[1]-1].piece.colour==colour){
	       gaurdScore += 100;
	       if(bs[kingPos[0]][kingPos[1]-1].piece.textRepresentation.equals("p")||bs[kingPos[0]][kingPos[1]-1].piece.textRepresentation.equals("P")){
		   gaurdScore+=20;
	       }else{gaurdScore-=20;}
	   }
	   //upper Right
	   if(kingPos[0]<7&&kingPos[1]>0&&bs[kingPos[0]+1][kingPos[1]-1].hasPiece&&bs[kingPos[0]+1][kingPos[1]-1].piece.colour==colour){
	       gaurdScore += 100;
	       if(bs[kingPos[0]+1][kingPos[1]-1].piece.textRepresentation.equals("p")||bs[kingPos[0]+1][kingPos[1]-1].piece.textRepresentation.equals("P")){
		   gaurdScore+=20;
	       }else{gaurdScore-=20;}
	   }
	   //Right
	   if(kingPos[0]<7&&bs[kingPos[0]+1][kingPos[1]].hasPiece&&bs[kingPos[0]+1][kingPos[1]].piece.colour==colour){
	       gaurdScore += 100;
	       if(bs[kingPos[0]+1][kingPos[1]].piece.textRepresentation.equals("p")||bs[kingPos[0]+1][kingPos[1]].piece.textRepresentation.equals("P")){
		   gaurdScore+=20;
	       }else{gaurdScore-=20;}
	   }
	   //LowerRight
	   if(kingPos[0]<7&&kingPos[1]<7&&bs[kingPos[0]+1][kingPos[1]+1].hasPiece&&bs[kingPos[0]+1][kingPos[1]+1].piece.colour==colour){
	       gaurdScore += 100;
	       if(bs[kingPos[0]+1][kingPos[1]+1].piece.textRepresentation.equals("p")||bs[kingPos[0]+1][kingPos[1]+1].piece.textRepresentation.equals("P")){
		   gaurdScore+=20;
	       }else{gaurdScore-=20;}
	   }
	   //Lower
	   if(kingPos[1]<7&&bs[kingPos[0]][kingPos[1]+1].hasPiece&&bs[kingPos[0]][kingPos[1]+1].piece.colour==colour){
	       gaurdScore += 50;
	       if(bs[kingPos[0]][kingPos[1]+1].piece.textRepresentation.equals("p")||bs[kingPos[0]][kingPos[1]+1].piece.textRepresentation.equals("P")){
		   gaurdScore+=20;
	       }else{gaurdScore-=20;}
	   }
	   //LowerLeft
	   if(kingPos[0]>0&&kingPos[1]<7&&bs[kingPos[0]-1][kingPos[1]+1].hasPiece&&bs[kingPos[0]-1][kingPos[1]+1].piece.colour==colour){
	       gaurdScore += 100;
	       if(bs[kingPos[0]-1][kingPos[1]+1].piece.textRepresentation.equals("p")||bs[kingPos[0]-1][kingPos[1]+1].piece.textRepresentation.equals("P")){
		   gaurdScore+=20;
	       }else{gaurdScore-=20;}
	   }
	   return gaurdScore;
	}
    }
    
}

