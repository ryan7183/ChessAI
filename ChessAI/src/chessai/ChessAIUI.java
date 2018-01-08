package chessai;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * This class takes care of the GUI for the program. 
 * @author Ryan and Parm
 */
public class ChessAIUI extends Application {
    @Override
    public void start(Stage primaryStage) {
	//Get Images
	Image boardImg = new Image("img/board.png");
	Image blackBishopImg =new Image("img/BlackPieces/BlackBishop.png");
	Image whiteBishopImg = new Image("img/WhitePieces/WhiteBishop.png");
	Image blackKingImg = new Image("img/BlackPieces/BlackKing.png");
	Image whiteKingImg = new Image("img/WhitePieces/WhiteKing.png"); 
	Image blackKnightImg = new Image("img/BlackPieces/BlackKnight.png");
	Image whiteKnightImg = new Image("img/WhitePieces/WhiteKnight.png");
	Image blackPawnImg=new Image("img/BlackPieces/BlackPawn.png");
	Image whitePawnImg =new Image("img/WhitePieces/WhitePawn.png");
	Image blackQueenImg = new Image("img/BlackPieces/BlackQueen.png");
	Image whiteQueenImg = new Image("img/WhitePieces/WhiteQueen.png");
	Image blackRookImg = new Image("img/BlackPieces/BlackRook.png");;
	Image whiteRookImg = new Image("img/WhitePieces/WhiteRook.png");
	
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
	//Save Board Button
	Button saveBoard = new Button();
	saveBoard.setText("Save Board");
	saveBoard.setLayoutX(0);
	saveBoard.setLayoutY(0);
	saveBoard.setOnAction((ActionEvent event) -> {
            //Save the board
	    BoardSquare[][] board  = p.board;
	    printBoardToFile(board,primaryStage);
        });
	root.getChildren().add(saveBoard);
	
	//Load Board Button
	Button loadBoard = new Button();
	loadBoard.setText("Load Board");
	loadBoard.setLayoutX(0);
	loadBoard.setLayoutY(30);
	loadBoard.setOnAction((ActionEvent event) -> {
            //Load the board
	    //File f = new File("C:/Users/Ryan/OneDrive/School/COSC 3P71/Project/ChessAI/ChessAI/ChessAI/ChessBoard.txt");
	    FileChooser fileChooser = new FileChooser();
	    fileChooser.setTitle("Open chess state");
	    File f =fileChooser.showOpenDialog(primaryStage);
	    BoardSquare[][] b;
	    try {
		b = buildBoardFile(f);
		p.setNewBoard(b);
		
	    } catch (FileNotFoundException ex) {
	    }
        });
	
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
	
        Text playerTurn = new Text();
        playerTurn.setText("It is your turn to move a piece");
        playerTurn.setLayoutX(5);
        playerTurn.setLayoutY(100);
        playerTurn.setVisible(false);
        Text aiTurn = new Text();
        aiTurn.setText("The AI is thinking");
        aiTurn.setLayoutX(5);
        aiTurn.setLayoutY(100);
        aiTurn.setVisible(false);
        root.getChildren().addAll(playerTurn,aiTurn);
        
	primaryStage.setTitle("Best Chess Game In The World... Maybe, Possibly Not, I Don't Know!");
	primaryStage.setScene(scene);
	
        
	Task<Void> task = new Task<Void>(){
            @Override
            protected Void call() throws Exception{
		//File f =new File("C:/Users/Ryan/OneDrive/School/COSC 3P71/Project/ChessAI/ChessAI/ChessAI/ChessBoard.txt");
		Board b = new Board(p,p1,p2);
                return null;
                
            }
        };
	Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
	
	
	root.getChildren().add(loadBoard);
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
		//Will update the board if boardUpdate is true
		if(p.boardUpdate){
		    renderBoard(p.board);
		    p.boardUpdate = false;
		}
                //Lets the user know that the move selected was invalid
                if(p.isInvalid){
		    p.isInvalid=false;
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Invalid Move!");
                    alert.setHeaderText(null);
                    alert.setContentText("The move that you have chosen is invalid. Please choose a valid move.");
                    alert.show();
                    
                }
                //Lets the user know that there is a stalemate and closes the game when the user closes the alert box
                if(p.stalemate){
		    p.stalemate=false;
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Stalemate!");
                    alert.setHeaderText(null);
                    alert.setContentText("There is a stalemate, the game is a draw.");
                    alert.setOnHidden(e -> Platform.exit());
                    alert.show();
                    
                }
                //Lets the user know that they won by checkmate. Will also close the game after user closes the alert box
                if(p.blackCheckmate){
		    p.blackCheckmate = false;
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Checkmate!");
                    alert.setHeaderText(null);
                    alert.setContentText("Checkmate! White wins the game!");
                    alert.setOnHidden(e -> Platform.exit());
                    alert.show();
                    
                }
                //Lets the user know that the AI won by checkmate. Will also close the game after user closes alert box.
                if(p.whiteCheckmate){
		    p.whiteCheckmate = false;
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Checkmate!");
                    alert.setHeaderText(null);
                    alert.setContentText("Checkmate! Black wins the game!");
                    alert.setOnHidden(e -> Platform.exit());
                    alert.show();
                    
                }
                //Lets player know its their turn
                if(p.isPlayerTurn){
                    playerTurn.setVisible(true);
                }
                else{
                    playerTurn.setVisible(false);
                }
                //Lets the player know it the AI's turn
                if(p.isAITurn){
                    aiTurn.setVisible(true);
                }
                else{
                    aiTurn.setVisible(false);
                }
	    }
            
            /**
             *Renders the board  
             * @param b The chess board
             */
	    public void renderBoard(BoardSquare[][] b){
		gc.drawImage(boardImg,0,0,canvas.getWidth(),canvas.getHeight());
		//Draw pieces
		for(int x=0;x<b.length;x++){
		    for(int y=0;y<b[0].length;y++){
			if(b[x][y].hasPiece){
			    drawPiece(b[x][y]);
			}
		    }
		}
	    }
            /**
             * Will add images of the pieces based on their text representation
             * value
             * @param bs The Chess board
             */
	    public void drawPiece(BoardSquare bs){
		String piece = bs.piece.textRepresentation;
		int pieceWidth = 73;
		int pieceHeight = 74;
		int x = 54+ (pieceWidth*bs.x);
		int y = 60+(pieceHeight*bs.y);
		switch(piece){
		    case "B":
			gc.drawImage(blackBishopImg,x,y,pieceWidth,pieceHeight);
			break;
		    case "b":
			gc.drawImage(whiteBishopImg,x,y,pieceWidth,pieceHeight);
			break;
		    case "K":
			gc.drawImage(blackKingImg,x,y,pieceWidth,pieceHeight);
			break;
		    case "k":
			gc.drawImage(whiteKingImg,x,y,pieceWidth,pieceHeight);
			break;
		    case "N":
			gc.drawImage(blackKnightImg,x,y,pieceWidth,pieceHeight);
			break;
		    case "n":
			gc.drawImage(whiteKnightImg,x,y,pieceWidth,pieceHeight);
			break;
		    case "P":
			gc.drawImage(blackPawnImg,x,y,pieceWidth,pieceHeight);
			break;
		    case "p":
			gc.drawImage(whitePawnImg,x,y,pieceWidth,pieceHeight);
			break;
		    case "Q":
			gc.drawImage(blackQueenImg,x,y,pieceWidth,pieceHeight);
			break;
		    case "q":
			gc.drawImage(whiteQueenImg,x,y,pieceWidth,pieceHeight);
			break;
		    case "R":
			gc.drawImage(blackRookImg,x,y,pieceWidth,pieceHeight);
			break;
		    case "r":
			gc.drawImage(whiteRookImg,x,y,pieceWidth,pieceHeight);
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
     * Builds the board based on the board configuration in the text file 
     * provided.
     * @param f The text file of the board configuration
     * @return The chess board 
     * @throws FileNotFoundException 
     */
    BoardSquare[][] buildBoardFile(File f) throws FileNotFoundException{
	BoardSquare[][] board;
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
			if(y!=1){
			    p.hasMoved=true;
			}
			break;
		    case 'p':
			p= new Pawn(true,x,y,"p");
			if(y!=6){
			    p.hasMoved=true;
			}
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
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	launch(args);
    }

    /**
     * Makes a text file of the board configuration.
     * @param board The chess board 
     * @param primaryStage The stage of the GUI
     */
    private void printBoardToFile(BoardSquare[][] board,Stage primaryStage) {
	File f = new File("ChessBoard.txt");
	FileChooser fileChooser = new FileChooser();
	fileChooser.setTitle("Save chess state");
	 FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);
	f =fileChooser.showSaveDialog(primaryStage);
	try {
	    PrintWriter out = new PrintWriter(f);
	    for(int x=0;x<board.length;x++){
		for(int y=0;y<board[0].length;y++){
		    if(board[y][x].hasPiece){
			out.print(board[y][x].piece.textRepresentation);
		    }else{
			out.print("-");
		    }
		}
		out.println();
	    }
	    out.flush();
	out.close();
	} catch (FileNotFoundException ex) {
	    Logger.getLogger(ChessAIUI.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	
    }
}
