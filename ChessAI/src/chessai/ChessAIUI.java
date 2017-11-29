/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessai;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author RyanS
 */
public class ChessAIUI extends Application {
    
    @Override
    public void start(Stage primaryStage) {
	
	
	StackPane root = new StackPane();
	Scene scene = new Scene(root, 1920, 1080);
	Canvas canvas = new Canvas( 700,715 );
	root.getChildren().add( canvas );
	GraphicsContext gc = canvas.getGraphicsContext2D();
	
	primaryStage.setTitle("Best Chess Game In The World... Maybe, Possibly Not, I Don't Know!");
	primaryStage.setScene(scene);
	
	
	
	Passer p = new Passer();//Create a way to communicate with board
	Human p1=new Human(p,true);
	Human p2 = new Human(p,false);
	
	Task<Void> task = new Task<Void>(){
            @Override
            protected Void call() throws Exception{
                Board b = new Board(p,p1,p2);
                return null;
                
            }
        };
	Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
	
	//Event handlers
	EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
         @Override 
         public void handle(MouseEvent e) { 
	     //If a player is selecting a piece update coordinates
	     if(!p.mouseClicked){
		p.mouseX=e.getX();
		p.mouseY=e.getY();
		p.mouseClicked =true;
	     }
             
         } 
	}; 
	canvas.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
	
	AnimationTimer aTimer = new AnimationTimer(){

	    @Override
	    public void handle(long now) {
		
		if(p.boardUpdate){
		    renderBoard(p.board);
		    p.boardUpdate = false;
		}
	    }
	    public void renderBoard(BoardSquare[][] b){
		Image i = new Image("img/board.png");
		gc.drawImage(i,0,0,canvas.getWidth(),canvas.getHeight());
		//Draw pieces
		for(int x=0;x<b.length;x++){
		    for(int y=0;y<b[0].length;y++){
			if(b[x][y].hasPiece){
			    drawPiece(b[x][y]);
			}
		    }
		}
	    }
	    public void drawPiece(BoardSquare bs){
		String piece = bs.piece.textRepresentation;
		Image i;
		int pieceWidth = 73;
		int pieceHeight = 74;
		int x = 54+ (pieceWidth*bs.x);
		int y = 60+(pieceHeight*bs.y);
		
		switch(piece){
		    case "B":
			i = new Image("img/BlackPieces/BlackBishop.png");
			gc.drawImage(i,x,y,pieceWidth,pieceHeight);
			break;
		    case "b":
			i = new Image("img/WhitePieces/WhiteBishop.png");
			gc.drawImage(i,x,y,pieceWidth,pieceHeight);
			break;
		    case "K":
			i = new Image("img/BlackPieces/BlackKing.png");
			gc.drawImage(i,x,y,pieceWidth,pieceHeight);
			break;
		    case "k":
			i = new Image("img/WhitePieces/WhiteKing.png");
			gc.drawImage(i,x,y,pieceWidth,pieceHeight);
			break;
		    case "N":
			i = new Image("img/BlackPieces/BlackKnight.png");
			gc.drawImage(i,x,y,pieceWidth,pieceHeight);
			break;
		    case "n":
			i = new Image("img/WhitePieces/WhiteKnight.png");
			gc.drawImage(i,x,y,pieceWidth,pieceHeight);
			break;
		    case "P":
			i = new Image("img/BlackPieces/BlackPawn.png");
			gc.drawImage(i,x,y,pieceWidth,pieceHeight);
			break;
		    case "p":
			i = new Image("img/WhitePieces/WhitePawn.png");
			gc.drawImage(i,x,y,pieceWidth,pieceHeight);
			break;
		    case "Q":
			i = new Image("img/BlackPieces/BlackQueen.png");
			gc.drawImage(i,x,y,pieceWidth,pieceHeight);
			break;
		    case "q":
			i = new Image("img/WhitePieces/WhiteQueen.png");
			gc.drawImage(i,x,y,pieceWidth,pieceHeight);
			break;
		    case "R":
			i = new Image("img/BlackPieces/BlackRook.png");
			gc.drawImage(i,x,y,pieceWidth,pieceHeight);
			break;
		    case "r":
			i = new Image("img/WhitePieces/WhiteRook.png");
			gc.drawImage(i,x,y,pieceWidth,pieceHeight);
			break;
		    default:
			break;
		}
	    }
	};
	aTimer.start();
	primaryStage.show();
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	launch(args);
    }
    
}
