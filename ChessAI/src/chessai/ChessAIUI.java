/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessai;

import java.io.File;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author RyanS
 */
public class ChessAIUI extends Application {
    
    @Override
    public void start(Stage primaryStage) {
	
	Passer p = new Passer();//Create a way to communicate with board
	Human p1=new Human(p,true);
	AIdriver p2 = new AIdriver(p,false);
	
	Pane root = new Pane();
	Scene scene = new Scene(root, 1920, 1080);
	//Add canvas
	Canvas canvas = new Canvas( 700,715 );
	canvas.setLayoutX((scene.getWidth()/2)-(canvas.getWidth()/2));
	canvas.setLayoutY(50);
	root.getChildren().add( canvas );
	GraphicsContext gc = canvas.getGraphicsContext2D();
	
	//Add ui buttons
	
	//Undo selection button
	Button undoSelect = new Button();
        undoSelect.setText("Undo Selection");
        undoSelect.setLayoutX(canvas.getLayoutX());
        undoSelect.setLayoutY(765);
        undoSelect.setOnAction((ActionEvent event) -> {
            //Undo Selection
	    p.cancelSelection=true;
        });
        root.getChildren().add(undoSelect);
	
        //Lets user choose piece for promotion
        Button promotionSelect = new Button();
        promotionSelect.setText("Slect New Piece");
        ChoiceBox<String> pieces = new ChoiceBox<String>();
        pieces.getItems().addAll("Bishop","Knight","Queen","Rook");
        pieces.setValue("Queen");
        Text promotionHelp = new Text();
        promotionHelp.setText("For promotion, choose the piece you want the pawn to become before moving the pawn. If this is not done, the pawn will turn into a queen.");
        promotionHelp.setLayoutX(canvas.getLayoutX());
        promotionHelp.setLayoutY(825);
        pieces.setLayoutX(canvas.getLayoutX()+150);
        pieces.setLayoutY(765);
        promotionSelect.setLayoutX(canvas.getLayoutX()+250);
        promotionSelect.setLayoutY(765);
        promotionSelect.setOnAction((ActionEvent event) -> {
            p.newPiece = pieces.getValue();
            p.promotion = false;
        });
        root.getChildren().addAll(promotionSelect, pieces,promotionHelp);
	
	primaryStage.setTitle("Best Chess Game In The World... Maybe, Possibly Not, I Don't Know!");
	primaryStage.setScene(scene);
	
        
	Task<Void> task = new Task<Void>(){
            @Override
            protected Void call() throws Exception{
		//File f =new File("C:/Users/RyanS/OneDrive//School/COSC 3P71/Project/ChessAIGit/ChessAI/ChessAI/src/ChessTestFiles/Test1.txt");
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
                if(p.isInvalid){
		    p.isInvalid=false;
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Invalid Move!");
                    alert.setHeaderText(null);
                    alert.setContentText("The move that you have chosen is invalid. Please choose a valid move.");
                    alert.show();
                    
                }
                if(p.stalemate){
		    p.stalemate=false;
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Stalemate!");
                    alert.setHeaderText(null);
                    alert.setContentText("There is a stalemate, the game is a draw.");
                    alert.show();
                    
                }
                if(p.blackCheckmate){
		    p.blackCheckmate = false;
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Checkmate!");
                    alert.setHeaderText(null);
                    alert.setContentText("Checkmate! White wins the game!");
                    alert.show();
                    
                }
                if(p.whiteCheckmate){
		    p.whiteCheckmate = false;
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Checkmate!");
                    alert.setHeaderText(null);
                    alert.setContentText("Checkmate! Black wins the game!");
                    alert.show();
                    
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
		if(p.drawSelection){
		    gc.setStroke(Color.BLUE);
		    gc.setLineWidth(10);
		    gc.strokeRect(p.selectionPosition[0], p.selectionPosition[1], pieceWidth, pieceHeight);
		    gc.setStroke(Color.BLACK);
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
