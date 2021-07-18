import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;
import javafx.util.Duration;
import java.util.Timer;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

import javafx.event.ActionEvent;
public class SnakevsBlock extends Application implements Serializable {
	int score=0;
	TextFlow statictext=new TextFlow();
	TextFlow textFlow = new TextFlow();	
    Font font = new Font("Tahoma", 48);
//	Pane root=new Pane();
    StackPane root=new StackPane();
    gameoverpage gover;
    serializescore sersc=new serializescore();
    serializecontinue sercon=new serializecontinue();
	Snake snk=new Snake();
	Text scoretext=new Text();

    Button menubut = new Button("Menu");
    ArrayList<brick> bricks= new ArrayList<brick>();
    ArrayList<Text> bricktext=new ArrayList<Text>();
    ArrayList<ImageView> foods= new ArrayList<ImageView>();
    ArrayList<Integer> foodval=new ArrayList<Integer>();
    ArrayList<Text> foodtext=new ArrayList<Text>();
//    ArrayList<Line> lines= new ArrayList<Line>();
    ArrayList<ImageView> magnets= new ArrayList<ImageView>();
    ArrayList<ImageView> shields= new ArrayList<ImageView>();
    ArrayList<ImageView> bombs= new ArrayList<ImageView>();
    
    int brickbefore;
    int brickafter;
    int brickbef2;
    int brickaf2;
    boolean notleft=false;
    boolean notright=false;
    boolean magnetactive=false;
    boolean shieldactive=false;
    public void showscore(int score) {
    	scoretext.setText("Score : "+score);
        scoretext.setFont(new Font("Tahoma", 36));
        scoretext.setFill(Color.rgb(70,60,70));
        scoretext.setTranslateX(-125);
        scoretext.setTranslateY(-275);
        root.getChildren().remove(scoretext);
//        statictext.getChildren().add(scoretext);
        root.getChildren().add(scoretext);
        
        
    }
    SnakevsBlock(int sc,int l){
    	score=sc;
    	showscore(sc);
    	if(snk.length>l) {
    		snk.deccircle(snk.length-l);
    	}
    	else if(snk.length<l)
    		snk.addcircle(l-snk.length);
    }
    
    public SnakevsBlock() {
		// TODO Auto-generated constructor stub
	}

	
	public Parent createContent(Stage primaryStage) {
		gover=new gameoverpage(primaryStage);
        root.setPrefSize(500, 600);
        showscore(score);
        wall Wall=new wall(3);
        Timeline timeline = new Timeline();
        
        
		menubut.setPrefSize(50,30);
		menubut.setLayoutX(275);
		menubut.setLayoutY(15);
		
		Button crossButton = new Button("X");
		crossButton.setPrefSize(50,30);
		crossButton.setLayoutX(350);
		crossButton.setLayoutY(15);
		crossButton.setVisible(false);
		
		Button mainBut = new Button("Go to Main Menu");
		mainBut.setPrefSize(150,30);
		mainBut.setLayoutX(175);
		mainBut.setLayoutY(50);
		mainBut.setVisible(false);
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
						try {
							sercon.store();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			);
		Button RestartButton = new Button("Restart Game");
		RestartButton.setPrefSize(150,30);
		RestartButton.setLayoutX(350);
		RestartButton.setLayoutY(50);
		RestartButton.setVisible(false);
		RestartButton.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {				
					@Override
					public void handle(MouseEvent arg0) {
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
		

//        brick[] bricks;
        timeline.getKeyFrames().addAll(new KeyFrame(Duration.seconds(2), event -> {
//        root.getChildren().adds(snk);
        	makerandombricks(10);

        }),new KeyFrame(Duration.seconds(1.5), event -> {
//          root.getChildren().adds(snk);
          	try {
				makerandomfood(3);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
          	
          }), new KeyFrame(Duration.seconds(1.5), event -> {
//            root.getChildren().adds(snk);
        	  Random random=new Random();
        	  int x=(int) (Math.random()*(4));
        	  if(x==1)
            	makerandommagnet(1);
            }), new KeyFrame(Duration.seconds(1.5), event -> {

          	  int x=(int) (Math.random()*(4));
          	  if(x==1)
              	makerandomshield(1);
              }), new KeyFrame(Duration.seconds(1.5), event -> {            
            	  int x=(int) (Math.random()*(4));
            	  if(x==1)
                	makerandombomb(1);
                }));
        KeyFrame k=new KeyFrame(Duration.seconds(0.5),new bricksCollisionHandler());
        Timeline t2=new Timeline(k);
        t2.setCycleCount(Animation.INDEFINITE);
        KeyFrame kfood=new KeyFrame(Duration.seconds(0.5),new foodsCollisionHandler());
        Timeline tfood=new Timeline(kfood);
        tfood.setCycleCount(Animation.INDEFINITE);
        KeyFrame kmagnet=new KeyFrame(Duration.seconds(0.5),new magnetCollisionHandler());
        Timeline tmagnet=new Timeline(kmagnet);
        tmagnet.setCycleCount(Animation.INDEFINITE);
        timeline.setCycleCount(Animation.INDEFINITE);
        KeyFrame kshield=new KeyFrame(Duration.seconds(0.5),new shieldCollisionHandler());
        Timeline tshield=new Timeline(kshield);
        tshield.setCycleCount(Animation.INDEFINITE);
        KeyFrame kbomb=new KeyFrame(Duration.seconds(0.5),new bombCollisionHandler());
        Timeline tbomb=new Timeline(kbomb);
        tbomb.setCycleCount(Animation.INDEFINITE);
//        KeyFrame kwall=new KeyFrame(Duration.seconds(0.5),new wallsCollisionHandler());
//        Timeline twall=new Timeline(kwall);
//        twall.setCycleCount(Animation.INDEFINITE);
        
        timeline.play();
//        twall.play();
        t2.play();
        tfood.play();
        tmagnet.play();
        tshield.play();
        tbomb.play();
        
        menubut.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {				
					@Override
					public void handle(MouseEvent arg0) {
						crossButton.setVisible(true);
						mainBut.setVisible(true);
						RestartButton.setVisible(true);
						timeline.pause();
					}
					
				}
			);
		crossButton.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {				
					@Override
					public void handle(MouseEvent arg0) {
						crossButton.setVisible(false);
						mainBut.setVisible(false);
						RestartButton.setVisible(false);
						timeline.play();
					}
					
				}
			);
		

        return new Group(root,mainBut,RestartButton,menubut,crossButton);
//        return root;
    }
	class gameoverpage {
		Stage primaryStage;
		public gameoverpage(Stage primaryStage) {
			this.primaryStage=primaryStage;
		}
		public void gotogameoverpage() {
			GameOver obj=new GameOver();
			try {
				obj.start(primaryStage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	class serializecontinue {
		DataOutputStream dataOut;
		DataInputStream dataIn;
		serializecontinue()  {
			try {
				dataOut = new DataOutputStream(new FileOutputStream("C:\\Users\\Piyush Aggarwal\\eclipse-workspace\\SnakeVsBlockGame\\src\\continue.txt"));
				dataIn = new DataInputStream(new FileInputStream("C:\\Users\\Piyush Aggarwal\\eclipse-workspace\\SnakeVsBlockGame\\src\\continue.txt"));
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void store() throws IOException{
			System.out.println(score+" "+snk.length);
			dataOut.writeUTF(score+" "+snk.length);
		}
		
		
	}
	class serializescore {
		DataOutputStream dataOut;
		DataInputStream dataIn;
		serializescore()  {
			try {
				dataOut = new DataOutputStream(new FileOutputStream("C:\\Users\\Piyush Aggarwal\\eclipse-workspace\\SnakeVsBlockGame\\src\\scores.txt",true));
				dataIn = new DataInputStream(new FileInputStream("C:\\Users\\Piyush Aggarwal\\eclipse-workspace\\SnakeVsBlockGame\\src\\scores.txt"));
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void store(int sc) throws IOException{
			String prev=null;
			while(dataIn.available()>0) {	
				prev=dataIn.readUTF();
			}
			System.out.println(prev);
			if(prev==null) {
				dataOut.writeUTF(sc+"");
			}
			else {
				String naya=prev+" "+sc;
				dataOut.writeUTF(naya);
			}
			System.out.println(dataIn.readUTF());
		}
		
		
	}
//	class nowork extends TimerTask{
//
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			System.out.println("okokokok");
//		}
//
//		
//	}
//	
//	class del{
//		Timer time;
//		public del(int seconds) {
//	        time = new Timer();
//	        time.schedule(new nowork(), seconds*1000);
//	    }
//
//	}
//	

	class Snake extends Circle {
		Circle range;
		ArrayList<Circle> circles=new ArrayList<Circle>();
		int length;
		Text text;
		public void set(int x) {
//			System.out.println(length);
			for(int i=0;i<length;i++) {
//				System.out.println(i);
				circles.get(i).setFill(Color.GREEN);
//				circles.get(i).setCenterX(300);
//				circles.get(i).setCenterY(600+(i*20));
				circles.get(i).setRadius(10);
				circles.get(i).setStroke(Color.BLACK);
				circles.get(i).setTranslateX(x);
				circles.get(i).setTranslateY(60+(i*20));
				circles.get(i).setVisible(true);
			}
			range.setRadius(175);
			range.setTranslateX(x);
			range.setTranslateY(60);
			range.setFill(Color.GREEN);
			range.setVisible(false);
		}
		public Snake() {
			range=new Circle();
			length=4;
			text=new Text();
			text.setText(Integer.toString(length));
			text.setFill(Color.BLACK);
			text.setTranslateX(10);
			text.setTranslateY(40);
			circles.add(new Circle());
			circles.add(new Circle());
			circles.add(new Circle());
			circles.add(new Circle());
			root.getChildren().add(text);
//			super(300, 750, 5);
			set(10);
			for(int i=0;i<circles.size();i++) {
				root.getChildren().add(circles.get(i));
			}
			root.getChildren().add(range);
//			root.getChildren().addAll(this.cir, this.cir1);
		}
		void addcircle(int m) {
			int q=(int) circles.get(0).getTranslateX();
			length+=m;
			for(int i=0;i<m;i++) {
				circles.add(new Circle());
			}
			
			set(q);
			text.setText(Integer.toString(length));
			for(int i=length-1;i>=length-m;i--) {
				root.getChildren().add(circles.get(i));
			}
			
		}
		void deccircle(int m) {	
			if(length>m) {
				int q=(int) circles.get(0).getTranslateX();
				
				for(int i=length-1;i>=length-m;i--) {
					root.getChildren().remove(circles.get(i));
				}
				length-=m;
				text.setText(Integer.toString(length));

				set(q);
				
			}
			else {
				System.out.println("GAME OVER");
				try {
					sersc.store(score);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Score Stored");
				snk.addcircle(100);
				snk.length=100;
				Stage stage = (Stage) menubut.getScene().getWindow();
				stage.close();
				gover.gotogameoverpage();
			}
		}
		
		void moveLeft() {
			if(circles.get(0).getTranslateX()<-235) {
				return;
			}
			if(notleft)
				return;
//			System.out.println(circles.get(0).getTranslateX());
			for(int i=0;i<circles.size();i++) {
				circles.get(i).setTranslateX(circles.get(i).getTranslateX() - 5);
				circles.get(i).setCenterX(circles.get(i).getCenterX()-5);
			}
			text.setTranslateX(text.getTranslateX()-5);
            range.setTranslateX(range.getTranslateX()-5);
        }
		void moveRight() {
			if(circles.get(0).getTranslateX()>235) {
				return;
			}
			if(notright)
				return;
			for(int i=0;i<circles.size();i++) {
				circles.get(i).setTranslateX(circles.get(i).getTranslateX() + 5);
				circles.get(i).setCenterX(circles.get(i).getCenterX()+5);
			}

            range.setTranslateX(range.getTranslateX()+5);
			text.setTranslateX(text.getTranslateX()+5);
        }
		
		
	}
	class brick extends Rectangle{ 
		int val;
		Rectangle brk;
		public brick(int v) {
			brk=new Rectangle();
//			brk.setFill(Color.AQUAMARINE);
			Random rand=new Random();
			brk.setFill(Color.rgb(rand.nextInt(255),rand.nextInt(255) , rand.nextInt(255)));
			brk.setArcHeight(20);
			brk.setArcWidth(20);
			brk.setWidth(50);
			brk.setHeight(40);
			brk.setStroke(Color.BLACK);
			brk.setTranslateY(-320);
			val=(int)(Math.random()*(40))+1;
		}
		
	}

	
	public void makerandombricks(int n) {
//		root.getChildren().add(snk);
//		brick[] bricks=new brick[n];
		int i=0;
		int[] used=new int[10];
		brickbef2=brickbefore;
		brickbefore=bricks.size();
		ParallelTransition p=new ParallelTransition();
		while (i<n) {
			
			Random rand=new Random();
			int x=rand.nextInt(2);
			int rand1=rand.nextInt(10)-5;
//			int rand1=0;
			
//			bricks[i]=new brick((int)(Math.random()*(20))+1);
//			int rand1=(int)(Math.random()*(10));
			if (used[rand1+5]==1) {
				break;
			}

			bricks.add(new brick((int)(Math.random()*(20))+1));
//			System.out.println(bricks);
//			bricks[i].brk.setX(25+((rand1)*50));
			bricks.get(bricks.size()-1).brk.setTranslateX(((rand1)*50)+25);
//			System.out.println(bricks);
//			System.out.println("x+"+bricks.get(bricks.size()-1).brk.getTranslateX());
			used[rand1+5]=1;
			TranslateTransition t1=new TranslateTransition();
			t1.setDuration(Duration.seconds(7));
			t1.setToY(900);
			t1.setNode(bricks.get(bricks.size()-1).brk);

			TranslateTransition t2=new TranslateTransition();
			bricktext.add(new Text());
//			valtext[i]= new Text();
			bricktext.get(bricktext.size()-1).setText(Integer.toString(bricks.get(bricks.size()-1).val));
			
			bricktext.get(bricktext.size()-1).setFill(Color.BLACK);
			bricktext.get(bricktext.size()-1).setTranslateX(((rand1)*50)+25);
			bricktext.get(bricktext.size()-1).setTranslateY(-320);
//			textFlow.getChildren().add(valtext[i]);
			root.getChildren().addAll(bricks.get(bricks.size()-1).brk,bricktext.get(bricktext.size()-1));
			
			t2.setDuration(Duration.seconds(7));
			t2.setToY(900);		
			t2.setNode(bricktext.get(bricktext.size()-1));
			i++;
			
			p.getChildren().addAll(t1,t2);
		}
		brickaf2=brickafter;
		brickafter=bricks.size();
		p.play();
	}
	
	class bricksCollisionHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			// TODO Auto-generated method stub
//		System.out.println("snake "+snk.circles.get(0).getCenterX());
//		System.out.println(bricks.size());
			
			for(int i1=0;i1<bricks.size();i1++) {
				if(bricks.get(i1)!=null) {
					if((snk.circles.get(0)).getBoundsInParent().intersects(bricks.get(i1).brk.getBoundsInParent())) {
						if(shieldactive==false) {
							snk.deccircle(bricks.get(i1).val);
							bricks.get(i1).brk.setVisible(false);
							bricktext.get(i1).setVisible(false);
							score+=bricks.get(i1).val;
							showscore(score);
							System.out.println("block se takraya......."+bricks.get(i1).val);
						}
						else if(shieldactive==true) {
							bricks.get(i1).brk.setVisible(false);
							bricktext.get(i1).setVisible(false);
							score+=bricks.get(i1).val;
							showscore(score);
							System.out.println("block se takraya......."+bricks.get(i1).val);

						}
					}
	    		}
			
			}
		}
		
	}
	
	class wall{
		Line[] line;
		int n;
		
		public wall(int n){
			line=new Line[n];
			this.n=n;
		}
	}
//
//	public void makerandomwalls(int n) {
//		int i=0;
//		int[] used=new int[10];
//		ParallelTransition p=new ParallelTransition();
//		while (i<n) {
//			lines.add(new Line());
//			Random random=new Random();
////			int rand1=random.nextInt(600 / 50) * 50;
////			int rand1=(int)(Math.random()*(600));
//			int rand1=random.nextInt(11)-5;
//			int rand2=(random.nextInt(100 / 50) *50 )+100;
//			lines.get(lines.size()-1).setStrokeWidth(2);
//			lines.get(lines.size()-1).setStartX(rand1*50);
//			lines.get(lines.size()-1).setStartY(0);
//			lines.get(lines.size()-1).setEndX(rand1*50);
//			lines.get(lines.size()-1).setEndY(-1*rand2);
//			lines.get(lines.size()-1).setTranslateX(rand1*50);
//			lines.get(lines.size()-1).setTranslateY(-320);
//			TranslateTransition t1=new TranslateTransition();
//			t1.setDuration(Duration.seconds(7));
//			t1.setToY(900);
//			t1.setNode(lines.get(lines.size()-1));
//			p.getChildren().add(t1);
//			root.getChildren().addAll(lines.get(lines.size()-1));
//			i++;
//		}
//		p.play();
//
//	}
//	class setwallnoleft extends TimerTask{
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
////			System.out.println("magnet time up");
//			notleft=false;
//		}		
//	}
//	
//	class wallnoleft{
//		Timer timer;
//		public wallnoleft(int seconds) {
//	        timer = new Timer();
//	        timer.schedule(new setwallnoleft(), seconds*1000);
//	    }
//	}
//	class setwallnoright extends TimerTask{
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
////			System.out.println("magnet time up");
//			notright=false;
//		}		
//	}
//	
//	class wallnoright{
//		Timer timer;
//		public wallnoright(int seconds) {
//	        timer = new Timer();
//	        timer.schedule(new setwallnoleft(), seconds*1000);
//	    }
//	}
//	class wallsCollisionHandler implements EventHandler<ActionEvent>{
//
//		@Override
//		public void handle(ActionEvent arg0) {
//			// TODO Auto-generated method stub
//			for(int i1=0;i1<lines.size();i1++) {
//	//			System.out.println("block "+bricks.get(i1).brk.getTranslateX());
//				if(lines.get(i1)!=null) {
//					if((snk.circles.get(0)).getBoundsInParent().intersects(lines.get(i1).getBoundsInParent())) {
//						System.out.println("wall se takraya.......");
//						if(snk.circles.get(0).getTranslateX()>=lines.get(i1).getStartX()) {
//							System.out.println("left nhi jana");
////							notleft=true;
////							new wallnoleft(1);
//						}							
//						if(snk.circles.get(0).getTranslateX()<=lines.get(i1).getStartX()) {
//							System.out.println("right nhi jana");
////							notright=true;							
////							new wallnoright(1);
//						}
//					}
//					else {
//						notleft=false;
//						notright=false;
//					}
//						
//					
//	    		}
//			
//			}
//		}
//		
//	}
	
	class tokens {
		int xLoc;
		int yLoc;
		
		public void setLoc(int x,int y) {
			xLoc=x;
			yLoc=y;
		}
		public int getX() {
			return xLoc;
		}
		public int getY() {
			return yLoc;
		}
		
	}
	class food extends tokens {
		ImageView[] apple;
		int value;
		int n;
		public food(int n){
			apple = new ImageView[n];
			this.n=n;
		}
	}
	public void makerandomfood(int n) throws FileNotFoundException {
		int i=0;
		int[] used=new int[10];
		
		ParallelTransition p=new ParallelTransition();
		while (i<n) {
			Random rand=new Random();
			int rand1=rand.nextInt(10)-5;
//			int rand1=(int)(Math.random()*(10));
			if (used[rand1+5]==1) {
				break;
			}
//			apple[i]=new ImageView(new Image(new FileInputStream("C:\\\\Users\\\\Piyush Aggarwal\\\\eclipse-workspace\\\\SnakeVsBlockGame\\\\src\\\\apple.png")));
			foods.add(new ImageView(new Image(new FileInputStream("C:\\\\Users\\\\Piyush Aggarwal\\\\eclipse-workspace\\\\SnakeVsBlockGame\\\\src\\\\apple.png"))));
			foods.get(foods.size()-1).setFitHeight(25);
			foods.get(foods.size()-1).setFitWidth(25);
			foods.get(foods.size()-1).setTranslateY(-325);
			
			
			
			foods.get(foods.size()-1).setTranslateX(25+((rand1)*50));
			used[rand1+5]=1;
			TranslateTransition t1=new TranslateTransition();
			t1.setDuration(Duration.seconds(7));
			t1.setToY(900); 
			t1.setNode(foods.get(foods.size()-1));
			foodtext.add(new Text());
//			valtext[i]= new Text();
//			if(foods.size()>0)
			foodval.add((int) (Math.random()*(10)+1));
			foodtext.get(foodtext.size()-1).setText(Integer.toString(foodval.get(foodval.size()-1)));
			foodtext.get(foodtext.size()-1).setFill(Color.BLACK);
			foodtext.get(foodtext.size()-1).setTranslateX(((rand1)*50)+25);
			foodtext.get(foodtext.size()-1).setTranslateY(-320);
			TranslateTransition t2=new TranslateTransition();
			t2.setDuration(Duration.seconds(7));
			t2.setToY(900);
			t2.setNode(foodtext.get(foodtext.size()-1));
			p.getChildren().addAll(t1,t2);
			
			root.getChildren().addAll(foods.get(foods.size()-1),foodtext.get(foodtext.size()-1));
			i++;
		}
		p.play();
	}

	class foodsCollisionHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			// TODO Auto-generated method stub
//		System.out.println("snake "+snk.circles.get(0).getCenterX());
//		System.out.println(bricks.size());	
		for(int i1=0;i1<foods.size();i1++) {
//			System.out.println("block "+bricks.get(i1).brk.getTranslateX());
			if(foods.get(i1)!=null) {
				if(magnetactive==true) {
					if(snk.range.getBoundsInParent().intersects(foods.get(i1).getBoundsInParent())) {
						snk.addcircle(foodval.get(i1));
						foods.get(i1).setVisible(false);
						foodtext.get(i1).setVisible(false);
					}
				}
				else {
					if((snk.circles.get(0)).getBoundsInParent().intersects(foods.get(i1).getBoundsInParent())) {
	//					snk.deccircle(bricks.get(i1).val);
	//					System.out.println("khane se takraya......."+foodval.get(i1));
						snk.addcircle(foodval.get(i1));
						foods.get(i1).setVisible(false);
						foodtext.get(i1).setVisible(false);
					}
    			}
			}
			
			}
		}
		
	}
	
	class magnet extends tokens{
		ImageView[] Magnet;
		int n;
		public magnet(int n){
			Magnet=new ImageView[n];
			this.n=n;
		}
		
		
	}
	public void makerandommagnet(int n) {
		int i=0;
		int[] used=new int[10];
		ParallelTransition p=new ParallelTransition();
		while (i<n) {
			try {
			magnets.add(new ImageView(new Image(new FileInputStream("C:\\Users\\Piyush Aggarwal\\eclipse-workspace\\SnakeVsBlockGame\\src\\magnet_preview.jpg"))));
			magnets.get(magnets.size()-1).setFitHeight(23);
			magnets.get(magnets.size()-1).setFitWidth(23);
			magnets.get(magnets.size()-1).setTranslateY(-325);
			}catch(FileNotFoundException fnf) {
				System.out.println("kjaskca");
			}
			Random rand=new Random();
			int rand1=rand.nextInt(10)-5;
			if (used[rand1+5]==1) {
				break;
			}
			magnets.get(magnets.size()-1).setTranslateX(25+((rand1)*50));
			used[rand1+5]=1;
			TranslateTransition t1=new TranslateTransition();
			t1.setDuration(Duration.seconds(7));
			t1.setToY(900);
			t1.setNode(magnets.get(magnets.size()-1));
			p.getChildren().add(t1);
			root.getChildren().addAll(magnets.get(magnets.size()-1));
			i++;
		}
		p.play();
	}
	
	class setmagnet extends TimerTask{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("magnet time up");
			magnetactive=false;
		}
		
	}
	
	class magnetwork{
		Timer timer;
		public magnetwork(int seconds) {
	        timer = new Timer();
	        timer.schedule(new setmagnet(), seconds*1000);
	    }

	}

	class magnetCollisionHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			// TODO Auto-generated method stub
//		System.out.println("snake "+snk.circles.get(0).getCenterX());
//		System.out.println(bricks.size());	
		for(int i1=0;i1<magnets.size();i1++) {
//			System.out.println("block "+bricks.get(i1).brk.getTranslateX());
			if(magnets.get(i1)!=null) {
				if((snk.circles.get(0)).getBoundsInParent().intersects(magnets.get(i1).getBoundsInParent())) {
//					snk.deccircle(bricks.get(i1).val);
//					System.out.println("magnet se takraya.......");
					magnets.get(i1).setVisible(false);
					magnetactive=true;
					new magnetwork(5);
				}
    		}
			
			}
		}
		
	}

	class shield extends tokens{
		ImageView[] Shield;
		int n;
		public shield(int n){
			Shield=new ImageView[n];
			this.n=n;
		}
		
	}
	public void makerandomshield(int n) {
		int i=0;
		int[] used=new int[10];
		ParallelTransition p=new ParallelTransition();
		while (i<n) {
			try {
			shields.add(new ImageView(new Image(new FileInputStream("C:\\Users\\Piyush Aggarwal\\eclipse-workspace\\SnakeVsBlockGame\\src\\shield.jpg"))));
			shields.get(shields.size()-1).setFitHeight(23);
			shields.get(shields.size()-1).setFitWidth(23);
			shields.get(shields.size()-1).setTranslateY(-325);
			}catch(FileNotFoundException fnf) {
				System.out.println("kjaskca");
			}
			Random rand=new Random();
			int rand1=rand.nextInt(10)-5;
			if (used[rand1+5]==1) {
				break;
			}
			shields.get(shields.size()-1).setTranslateX(25+((rand1)*50));
			used[rand1+5]=1;
			TranslateTransition t1=new TranslateTransition();
			t1.setDuration(Duration.seconds(7));
			t1.setToY(900);
			t1.setNode(shields.get(shields.size()-1));
			p.getChildren().add(t1);
			root.getChildren().addAll(shields.get(shields.size()-1));
			i++;
		}
		p.play();
	}
	
	class setshield extends TimerTask{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("shield time up");
			shieldactive=false;
		}
		
	}
	
	class shieldwork{
		Timer timer;
		public shieldwork(int seconds) {
	        timer = new Timer();
	        timer.schedule(new setshield(), seconds*1000);
	    }

	}
	
	class shieldCollisionHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			for(int i1=0;i1<shields.size();i1++) {
	//			System.out.println("block "+bricks.get(i1).brk.getTranslateX());
				if(shields.get(i1)!=null) {
					if((snk.circles.get(0)).getBoundsInParent().intersects(shields.get(i1).getBoundsInParent())) {
	//					System.out.println("shield se takraya.......");
						shields.get(i1).setVisible(false);
						shieldactive=true;
						new shieldwork(5);
						System.out.println("shield start");
						}
	    			}
				
				}
			}
		
	}

	class bomb extends tokens{
		ImageView[] Bomb;
		int n;
		public bomb(int n){
			Bomb=new ImageView[n];
			this.n=n;
		}		
		
	}
	public void makerandombomb(int n) {
		int i=0;
		int[] used=new int[10];
		ParallelTransition p=new ParallelTransition();
		while (i<n) {
			try {
			bombs.add(new ImageView(new Image(new FileInputStream("C:\\Users\\Piyush Aggarwal\\eclipse-workspace\\SnakeVsBlockGame\\src\\bomb_new.png"))));
			bombs.get(bombs.size()-1).setFitHeight(23);
			bombs.get(bombs.size()-1).setFitWidth(23);
			bombs.get(bombs.size()-1).setTranslateY(-325);
			}catch(FileNotFoundException fnf) {
				System.out.println("kjaskca");
			}
			int rand1=(int)(Math.random()*(10));
			if (used[rand1]==1) {
				break;
			}
			bombs.get(bombs.size()-1).setTranslateX(25+((rand1)*50));
			used[rand1]=1;
			TranslateTransition t1=new TranslateTransition();
			t1.setDuration(Duration.seconds(7));
			t1.setToY(900);
			t1.setNode(bombs.get(bombs.size()-1));
			p.getChildren().add(t1);
			root.getChildren().addAll(bombs.get(bombs.size()-1));
			i++;
		}
		p.play();
	}
	class bombCollisionHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			int x=bricks.size();
			for(int i1=0;i1<bombs.size();i1++) {
				
	//			System.out.println("block "+bricks.get(i1).brk.getTranslateX());
				if(bombs.get(i1)!=null) {
					if((snk.circles.get(0)).getBoundsInParent().intersects(bombs.get(i1).getBoundsInParent())) {
	//					snk.deccircle(bricks.get(i1).val);
//						System.out.println("bomb se takraya.......");
						bombs.get(i1).setVisible(false);
						System.out.println("bricks itne h "+bricks.size());
						int y=bricks.size();
//						System.out.println(x+" "+y);
						System.out.println(brickbef2+ " "+ brickaf2);
						for(int j=brickaf2-1;j>=brickbef2;j--) {
							bricks.get(j).brk.setVisible(false);
							
							bricktext.get(j).setVisible(false);;
							score+=bricks.get(j).val;
							showscore(score);
							bricks.remove(j);
							bricktext.remove(j);
							brickaf2=bricks.size();
//							score+=Integer.parseInt( bricktext.get(j).getText());
//							
						}
					}
	    		}
			
			}
		}
		
	}


	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		   
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Scene scene=new Scene(createContent(primaryStage));
		primaryStage.setTitle("Snake vs Block Game");
		primaryStage.setResizable(false);
		
		
		scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case A:
                    snk.moveLeft();
                    break;
                case D:
                    snk.moveRight();
                    break;
                case LEFT:
                	snk.moveLeft();
                	break;
                case RIGHT:
                	snk.moveRight();
                	break;
            }
        });
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
}