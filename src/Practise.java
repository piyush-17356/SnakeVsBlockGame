import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

//import java.awt.Label;
import javafx.scene.control.Label;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javax.swing.text.Element;
//import javax.swing.text.html.ImageView;
//import javax.swing.text.Position;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.Button;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Practise extends Application{
	String continueloc="C:\\\\Users\\\\Piyush Aggarwal\\\\eclipse-workspace\\\\SnakeVsBlockGame\\\\src\\\\continue.txt";
	String snakeimageloc="C:\\\\Users\\\\Piyush Aggarwal\\\\eclipse-workspace\\\\SnakeVsBlockGame\\\\src\\\\snake2.jpg";
	String textimageloc="C:\\Users\\Piyush Aggarwal\\eclipse-workspace\\SnakeVsBlockGame\\src\\text.jpg";
	String magnetimageloc="C:\\\\Users\\\\Piyush Aggarwal\\\\eclipse-workspace\\\\SnakeVsBlockGame\\\\src\\\\magnet.png";
	String bombimageloc="C:\\\\Users\\\\Piyush Aggarwal\\\\eclipse-workspace\\\\SnakeVsBlockGame\\\\src\\\\bomb.jpg";
	String sampleimageloc="C:\\\\Users\\\\Piyush Aggarwal\\\\eclipse-workspace\\\\SnakeVsBlockGame\\\\src\\\\sample.jpg";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
		
	}
	public String give() throws IOException {
		DataInputStream dataIn = null;
		try {
			dataIn = new DataInputStream(new FileInputStream(continueloc));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String res=null;
		while(dataIn.available()>0) {	
			res=dataIn.readUTF();
		}
		System.out.println(res);
		return res;
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		// TODO Auto-generated method stub
		
		primaryStage.setTitle("Snake Vs Block Game");
//		primaryStage.setResizable(false);
		
		DropShadow ds=new DropShadow();
		
		Button Startbutton = new Button("Start Game");
		Startbutton.setPrefSize(150,30);
		Startbutton.setTranslateX(-120);
		Startbutton.setTranslateY(50);
		Startbutton.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {				
					@Override
					public void handle(MouseEvent arg0) {
						// TODO Auto-generated method stub
						SnakevsBlock obj=new SnakevsBlock();
						try {
							obj.start(primaryStage);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
			);
		
		Button Continuebutton=new Button("Continue Playing Game");
		Continuebutton.setPrefSize(150,30);
		Continuebutton.setTranslateX(-120);
		Continuebutton.setTranslateY(100);
//		Continuebutton.setPadding(new Insets(5,5,5,5));
		Continuebutton.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {				
					@Override
					public void handle(MouseEvent arg0) {
						// TODO Auto-generated method stub
						String res = null;
						try {
							res = give();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if(res!=null) {
							String[] result=res.split(" ");
							int a1=Integer.parseInt(result[0]);
							int a2=Integer.parseInt(result[1]);
							SnakevsBlock obj=new SnakevsBlock(a1,a2);
							try {
								obj.start(primaryStage);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			);
		Button LeaderBoardbutton = new Button("LeaderBoard Page");
		LeaderBoardbutton.setPrefSize(150,30);
		LeaderBoardbutton.setTranslateX(-120);
		LeaderBoardbutton.setTranslateY(150);
		LeaderBoardbutton.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {				
					@Override
					public void handle(MouseEvent arg0) {
						// TODO Auto-generated method stub
						LeaderBoard obj=new LeaderBoard();
						try {
							obj.start(primaryStage);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
			);
		
		Button ExitButton = new Button("Exit Game");
		ExitButton.setPrefSize(150,30);
		ExitButton.setTranslateX(-120);
		ExitButton.setTranslateY(200);
		ExitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {				
					@Override
					public void handle(MouseEvent arg0) {
						// TODO Auto-generated method stub
						//Stage stage = (Stage) ExitButton.getScene().getWindow();
						//stage.close();
						System.exit(0);
					}
					
				}
			);
		
		Image textimage=new Image(new FileInputStream(textimageloc));
		ImageView textimageView = new ImageView(textimage);
		textimageView.setTranslateX(-130);
		textimageView.setTranslateY(-130);
		textimageView.setFitHeight(300);
		textimageView.setFitWidth(135);
		textimageView.setPreserveRatio(true);
		
		Image snakeimage=new Image(new FileInputStream(snakeimageloc));
		ImageView snakeimageView = new ImageView(snakeimage);
		snakeimageView.setTranslateX(100);
		snakeimageView.setTranslateY(-110);
		snakeimageView.setFitHeight(300);
		snakeimageView.setFitWidth(200);
//		snakeimageView.setPreserveRatio(true);
        
		Image magnetimage=new Image(new FileInputStream(magnetimageloc));
		ImageView magnetimageView = new ImageView(magnetimage);
		magnetimageView.setTranslateX(70);
		magnetimageView.setTranslateY(90);
//		magnetimageView.setx
		magnetimageView.setFitHeight(50);
		magnetimageView.setFitWidth(50);
		magnetimageView.setPreserveRatio(true);
		
		Image bombimage=new Image(new FileInputStream(bombimageloc));
		ImageView bombimageView = new ImageView(bombimage);
		bombimageView.setTranslateX(140);
		bombimageView.setTranslateY(85);
		bombimageView.setFitHeight(70);
		bombimageView.setFitWidth(70);
		bombimageView.setPreserveRatio(true);
		
		Image sampleimage=new Image(new FileInputStream(sampleimageloc));
		ImageView sampleimageView = new ImageView(sampleimage);
		sampleimageView.setTranslateX(100);
		sampleimageView.setTranslateY(180);
		sampleimageView.setFitHeight(100);
		sampleimageView.setFitWidth(180);
		sampleimageView.setPreserveRatio(true);
		
//		StackPane root=new StackPane();
		StackPane root=new StackPane();
		root.setStyle("-fx-background-color: #000000;");
        root.getChildren().addAll(Startbutton,Continuebutton,LeaderBoardbutton,ExitButton,textimageView,snakeimageView,magnetimageView,bombimageView,sampleimageView);
//        root.getChildren().addAll(Startbutton,Continuebutton,LeaderBoardbutton);
//        scene.get
        Scene scene = new Scene(root, 500, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

	}

}
