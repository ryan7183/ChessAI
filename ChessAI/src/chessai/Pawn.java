package chessai;

import java.util.ArrayList;

/**
 *This class creates a new class and will generate all the possible moves for
 * the pawn at its current position as well as check if the move selected is
 * a valid move for the pawn.
 * @author Ryan and Parm
 */
public class Pawn extends Piece {

    public Pawn(boolean c, int x, int y, String n) {
	super(c, x, y, n);
        this.pawnPromotion = false;
        this.enpassantLeft = false;
        this.enpassantRight = false;
    }

    public Pawn(Pawn p){
	super(p.colour,p.x,p.y,p.textRepresentation);
	this.pawnPromotion=p.pawnPromotion;
	this.enpassantLeft=p.enpassantLeft;
	this.enpassantRight=p.enpassantRight;
    }
    
    /**
     * Checks to see if the move selected is a valid move for the pawn/
     * @param newPos An Array of the new x and y positions
     * @param bs The chess board with all the pieces
     * @param moveList List of all the moves that have been made
     * @return True if the move is valid for the pawn or false if the move is 
     * not valid.
     */
    @Override
    public Boolean isValidMove(int[] newPos, BoardSquare[][] bs, ArrayList<Piece> moveList) {
        int changeInY;
        //Return false if the new x position is out of bounds
        //System.out.println(100);
        if(newPos[0]<0||newPos[0]>7){
            return false;
        }
        //System.out.println(200);
        if(this.colour){
            changeInY = -1;
        }
        else{
            changeInY = 1;
        }
        //System.out.println(300);
        //Returns false if the move selected moves the white pawn backwards
        if(this.colour && newPos[1]>this.y){
            return false;
        }
        //System.out.println(400);
        //Returns false if the move selected moves the black pawn backwards
        if(!this.colour && newPos[1]<this.y){
            return false;
        }
        
        if((((this.x-1 >=0 && (this.y+changeInY <=7 && this.y+changeInY >=0)) && (bs[this.x-1][this.y+changeInY].hasPiece)) && 
                (newPos[1]==(this.y+changeInY) && newPos[0]==(this.x-1))) || (((this.x+1 <=7 && (this.y+changeInY <=7 && this.y+changeInY >=0))&& (bs[this.x+1][this.y+changeInY].hasPiece))
                && (newPos[1]==(this.y+changeInY) && newPos[0]==(this.x+1)))){
                //Returns false if the piece that the pawn can attack is the same colour as the pawn
                if(bs[newPos[0]][newPos[1]].hasPiece && bs[newPos[0]][newPos[1]].piece.colour == this.colour){
                    return false;
                }
                //Returns true if the piece at the new position is not the same colour as the pawn
                else{
                    return true;
                }
        }
        //Checks to see if en passant is possible for the left side of the pawn
        else if(((this.x-1>=0 && this.y+changeInY <= 7 && (bs[this.x-1][this.y].hasPiece && bs[this.x-1][this.y].piece.colour!=this.colour)) 
                && (newPos[1]==(this.y+changeInY) && newPos[0]==this.x-1))){
            //System.out.println(600);
            if(bs[newPos[0]][newPos[1]].hasPiece && bs[this.x-1][this.y+changeInY].piece.colour == this.colour){
                return false;
            }
            else{
                //Return false if there is a piece with the same colour as the pawn at the new position
                if(bs[newPos[0]][newPos[1]].hasPiece && bs[newPos[0]][newPos[1]].piece.colour == this.colour){
                    return false;
                }
                else if(!bs[newPos[0]][newPos[1]].hasPiece){
                    //System.out.println(700);
                    if(moveList.size()>2){
                        int pos = moveList.size()-1;
                        //Finds the last move made by the opponent 
                        while(moveList.get(pos).colour==this.colour){
                            pos--;
                        }
                        //Returns true if en passant on the left side is possible. Also sets enpassantLeft to be true.
                        if((moveList.get(pos).textRepresentation.equals("P") || moveList.get(pos).textRepresentation.equals("p"))
                                &&(this.y==moveList.get(pos).y && Math.abs(moveList.get(pos).prevY-moveList.get(pos).y)==2
                                && this.x-1==moveList.get(pos).x)){
                            //System.out.println(800);
                            this.enpassantLeft = true;
                            return true;
                        }
                        else{
                            //System.out.println(900);
                            return false;
                        }
                    }
                }
            }
        }
        //Checks to see if en passant on the right side is possible
        else if(((this.x+1<=7 && this.y+changeInY >=0 && (bs[this.x+1][this.y].hasPiece) && bs[this.x+1][this.y].piece.colour!=this.colour) 
                && (newPos[1]==(this.y+changeInY) && newPos[0]==this.x+1))){
            //System.out.println(1000);
            if(bs[newPos[0]][newPos[1]].hasPiece && bs[this.x+1][this.y+changeInY].piece.colour == this.colour){
                return false;
            }
            else{
                //Return false if there is a piece with the same colour as the pawn at the new position
                //System.out.println(2000);
                if(bs[newPos[0]][newPos[1]].hasPiece && bs[newPos[0]][newPos[1]].piece.colour == this.colour){
                    return false;
                }
                else if(!bs[newPos[0]][newPos[1]].hasPiece){
                    //System.out.println(3000);
                    if(moveList.size()>2){
                        int pos = moveList.size()-1;
                        //Finds the last move made by the opponent 
                        while(moveList.get(pos).colour==this.colour){
                            pos--;
                        }
                        //Returns true if en passant on the right side is possible. Also sets enpassantRight to be true.
                        if((moveList.get(pos).textRepresentation.equals("P") || moveList.get(pos).textRepresentation.equals("p"))
                                &&(this.y==moveList.get(pos).y && Math.abs(moveList.get(pos).prevY-moveList.get(pos).y)==2
                                && this.x+1==moveList.get(pos).x)){
                            //System.out.println(4000);
                            this.enpassantRight = true;
                            return true;
                        }
                        else{
                            return false;
                        }
                    }
                }
            }    
        }
        else{
            //returns false if the current x position is not the same as the new x position
            if(this.x!=newPos[0]){
                //System.out.println(5000);
                return false;
            }
        }
        //System.out.println(6000);
        //Only allows the pawn to move one space if the pawn has been moved from its initial position
        if(this.hasMoved){
            //Returns false if the change in y does not equal 1
            //System.out.println(7000);
            if(Math.abs(newPos[1]-this.y) != 1){
                return false;
            }
            //System.out.println(8000);
            //Returns false if the new y position does not equal the current y plus the negative of the change in y
            if(this.y+(-changeInY)==newPos[1]){
                return false;
            }
            //System.out.println(9000);
            //Returns false if the new position has a piece located on it
            if(bs[newPos[0]][newPos[1]].hasPiece){
                return false;
            }
        }
        //Allows the piece to move 1 or 2 spaces if it hasn't moved from its inital position
        else{
            //System.out.println(10000);
            //Returns false if the chanfe in y doesn't equal 1 or if the change in y doesn't equal 2
            if(!(Math.abs(newPos[1]-this.y) == 1 || Math.abs(newPos[1]-this.y) == 2)){
                return false;
            }
            //System.out.println(20000);
            //Returns false if the new position has a piece
            if(bs[newPos[0]][newPos[1]].hasPiece){
                return false;
            }
            //System.out.println(30000);
            if(Math.abs(newPos[1]-this.y) == 2){
                if(this.colour){
                    if(bs[newPos[0]][newPos[1]+1].hasPiece){
                        return false;
                    }
                }
                else{
                    //System.out.println(40000);
                    if(bs[newPos[0]][newPos[1]-1].hasPiece){
                        return false;
                    }
                }
            }
        }
        //Sets pawnPromotion to true if the white pawn has made it to the other end of the board
        //System.out.println(50000);
        if(newPos[1]==0 && this.colour){
            this.pawnPromotion = true;
        }
        //System.out.println(60000);
        //Sets pawnPromotion to true if the black pawn has made it to the other end of the board
        if (newPos[1] ==7 && !this.colour){
            //System.out.println(70000);
            this.pawnPromotion = true;
        }
        //System.out.println(80000);
	/*if(bs[newPos[0]][newPos[1]].hasPiece&&(bs[newPos[0]][newPos[1]].piece.textRepresentation.equals("K")||bs[newPos[0]][newPos[1]].piece.textRepresentation.equals("k"))){
	   return false; 
	}*/
        return true;
    }

    /**
     * Generates all the possible moves for the pawn at its current position. 
     * All the possible moves for the pawn are stored in a 2d array
     * @param bs The chess board with all the chess pieces
     * @param moveList List of all the moves that have been made
     * @return A 2d array with all the possible moves for the pawn
     */
    @Override
    public int[][] generateMoves(BoardSquare[][] bs, ArrayList<Piece> moveList) {
	int[][] moves=new int[16][];
	int[] possibleMove=new int[2];
	int validCount = 0;
	int[][] returnMove;
	for(int x=this.x-1;x<this.x+2;x++){
	    for(int y=0;y<bs[0].length;y++){
		possibleMove[0]=x;
		possibleMove[1]=y;
		if(isValidMove(possibleMove,bs, moveList)){
		    moves[validCount]=possibleMove.clone();
		    validCount++;
		}
	    }
	}
	returnMove =new int[validCount][];//Create an array of the correct size to store the moves
	System.arraycopy(moves, 0,returnMove , 0, validCount);//Copy the valid moves to an array of the correct size
	return returnMove;
    }
    
    
}
