import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import java.io.FileInputStream;
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

public class GameOver extends Application{
	String gameoverloc="C:\\Users\\Piyush Aggarwal\\eclipse-workspace\\SnakeVsBlockGame\\src\\gameover.png";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		primaryStage.setTitle("Game Over Page");
		primaryStage.setResizable(false);
		
		DropShadow ds=new DropShadow();
		
		Button mainBut = new Button("Go to Main Menu");
		mainBut.setPrefSize(150,30);
		mainBut.setLayoutX(300);
		mainBut.setLayoutY(90);
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
		ExitButton.setLayoutY(140);
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
		
		Image textimage=new Image(new FileInputStream(gameoverloc));
		ImageView textimageView = new ImageView(textimage);
		textimageView.setX(50);
		textimageView.setY(50);
		textimageView.setFitHeight(300);
		textimageView.setFitWidth(175);
		textimageView.setPreserveRatio(true);
		
//		StackPane root=new StackPane();
		Pane root=new Pane();
		root.setStyle("-fx-background-color: #000000;");
        root.getChildren().addAll(mainBut,ExitButton,textimageView);
//        scene.get
        Scene scene = new Scene(root, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

	}

}
