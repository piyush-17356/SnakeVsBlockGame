import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

import javafx.scene.text.Text;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javax.swing.text.Element;
//import javax.swing.text.html.ImageView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.Button;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LeaderBoard extends Application{
	String scoreloc="C:\\Users\\Piyush Aggarwal\\eclipse-workspace\\SnakeVsBlockGame\\src\\scores.txt";
	String limageloc="C:\\\\Users\\\\Piyush Aggarwal\\\\eclipse-workspace\\\\SnakeVsBlockGame\\\\src\\\\l.png";
	public String givescores() throws IOException {
		DataInputStream dataIn = null;
		try {
			dataIn = new DataInputStream(new FileInputStream(scoreloc));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String scores=null;
		while(dataIn.available()>0) {	
			scores=dataIn.readUTF();
		}
		System.out.println(scores);
		return scores;
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		String scores=givescores();
		primaryStage.setTitle("LeaderBoard Page");
		primaryStage.setResizable(false);
		
		DropShadow ds=new DropShadow();
		
		Button mainBut = new Button("Go to Main Menu");
		mainBut.setPrefSize(150,30);
		mainBut.setLayoutX(300);
		mainBut.setLayoutY(70);
		mainBut.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {				
					@Override
					public void handle(MouseEvent arg0) {
						// TODO Auto-generated method stub
						Practise obj=new Practise();
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
		ExitButton.setLayoutX(300);
		ExitButton.setLayoutY(120);
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
		
		Image textimage=new Image(new FileInputStream(limageloc));
		ImageView textimageView = new ImageView(textimage);
		textimageView.setX(50);
		textimageView.setY(50);
		textimageView.setFitHeight(300);
		textimageView.setFitWidth(135);
		textimageView.setPreserveRatio(true);
		
		ArrayList<Integer> score=new ArrayList<Integer>();
		String[] score1= scores.split(" ");
		for(int i=0;i<score1.length;i++) {
			score.add(Integer.parseInt(score1[i]));
		}
		score.sort(null);
		System.out.println(score);
		Text[][] table = new Text[11][2];
		table[0][0]=new Text("Sl. No");
		table[0][1]=new Text("Score");
		for(int i=1;i<score.size()+1;i++) {
			if(i>10)
				break;
			table[i][0]=new Text(Integer.toString(i));
			table[i][1]=new Text(Integer.toString(score.get(score.size()-i)));
			table[i][0].setFill(Color.WHITE);
			table[i][0].setTranslateX(50);
			table[i][0].setTranslateY(250+30*i);
			table[i][1].setFill(Color.WHITE);
			table[i][1].setTranslateX(150);
			table[i][1].setTranslateY(250+30*i);
			table[i][0].setScaleX(1.5);
			table[i][0].setScaleY(1.5);
			table[i][1].setScaleX(1.5);
			table[i][1].setScaleY(1.5);
		}
		
		table[0][0].setFill(Color.WHITE);
		table[0][0].setTranslateX(50);
		table[0][0].setTranslateY(250);
		table[0][1].setFill(Color.WHITE);
		table[0][1].setTranslateX(150);
		table[0][1].setTranslateY(250);
		table[0][0].setScaleX(2);
		table[0][0].setScaleY(2);
		table[0][1].setScaleX(2);
		table[0][1].setScaleY(2);
//		StackPane root=new StackPane();
		Pane root=new Pane();
		root.setStyle("-fx-background-color: #000000;");
        root.getChildren().addAll(mainBut,ExitButton,textimageView);
        for(int i=0;i<table.length;i++) {
			for(int j=0;j<table[0].length;j++)
				root.getChildren().addAll(table[i][j]);
				if(i==11) {
					break;
				}
		}
//        scene.get
        Scene scene = new Scene(root, 500, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

	}

}
