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
import javafx.scene.layout.StackPane;
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
	Canvas canvas = new Canvas( 1920,1080 );
	GraphicsContext gc = canvas.getGraphicsContext2D();
	
	primaryStage.setTitle("Best Chess Game The World.. Maybe, Possibly Not, I Don't Know!");
	primaryStage.setScene(scene);
	primaryStage.show();
	
	
	Passer p = new Passer();//Create a way to communicate with board
	
	Task<Void> task = new Task<Void>(){
            @Override
            protected Void call() throws Exception{
                Board b = new Board(p);
                return null;
                
            }
        };
	
	
	AnimationTimer aTimer = new AnimationTimer(){

	    @Override
	    public void handle(long now) {
		if(p.update){
		    renderBoard(p.board);
		}
	    }
	    public void renderBoard(BoardSquare[][] b){
		
	    }
	};
    
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	launch(args);
    }
    
}
