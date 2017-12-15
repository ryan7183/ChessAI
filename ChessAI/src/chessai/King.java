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
public class King extends Piece{
    
    public King(boolean c, int x, int y, String n) {
	super(c, x, y, n);
        this.castleKingSide = false;
        this.castleQueenSide = false;
    }

    @Override
    public Boolean isValidMove(int[] newPos, BoardSquare[][] bs) {
        int changeInX = Math.abs(this.x-newPos[0]);
        int changeInY = Math.abs(this.y-newPos[1]);
        if(changeInY>1){
            return false;
        }
        if(changeInX>2){
            return false;
        }
        else{
            if(changeInX == 2 && !this.hasMoved){
                if(newPos[0] < this.x){
                    if(bs[0][this.y].hasPiece && (bs[0][this.y].piece.textRepresentation.equals("R") || bs[0][this.y].piece.textRepresentation.equals("r")) && !bs[0][this.y].piece.hasMoved){
                        for(int i=1; i<this.x;i++){
                            if(bs[i][this.y].hasPiece){
                                return false;
                            }
                            int[] loc = new int [2];
                            loc[0] = i;
                            loc[1] = this.y;
                            for(int x=0; x<8; x++){
                                for(int y=0; y<8; y++){
                                    if(bs[x][y].hasPiece && bs[x][y].piece.colour != this.colour){
                                        if(bs[x][y].piece.isValidMove(loc, bs)){
                                            return false;
                                        }
                                    }
                                }
                            }
                        }
                        this.castleQueenSide = true;
                    }
                    else{
                        return false;
                    }
                }
                else if(newPos[0] > this.x){
                    if(bs[7][this.y].hasPiece && (bs[7][this.y].piece.textRepresentation.equals("R") || bs[7][this.y].piece.textRepresentation.equals("r")) && !bs[7][this.y].piece.hasMoved){
                        for(int i=6; i<this.x;i--){
                            if(bs[i][this.y].hasPiece){
                                return false;
                            }
                            int[] loc = new int [2];
                            loc[0] = i;
                            loc[1] = this.y;
                            for(int x=0; x<8; x++){
                                for(int y=0; y<8; y++){
                                    if(bs[x][y].hasPiece && bs[x][y].piece.colour != this.colour){
                                        if(bs[x][y].piece.isValidMove(loc, bs)){
                                            return false;
                                        }
                                    }
                                }
                            }
                        }
                        this.castleKingSide = true;
                    }
                    else{
                        return false;
                    }
                }
            }
        }
        if(bs[newPos[0]][newPos[1]].hasPiece && bs[newPos[0]][newPos[1]].piece.colour == this.colour){
            return false;
        }
        return true;
    }

    @Override
    public int[][] generateMoves(BoardSquare[][] bs) {
	int[][] moves=new int[10][];//4 is the max number of moves a knight can take
	int[] possibleMove=new int[2];
	int validCount =0;
	int[][] returnMove;
	for(int x=0;x<bs.length;x++){
	    for(int y=0;y<bs[0].length;y++){
		possibleMove[0]=x;
		possibleMove[1]=y;
		if(isValidMove(possibleMove,bs)){
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
