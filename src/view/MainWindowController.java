package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.ResourceBundle;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainWindowController implements Initializable{
	
	@FXML
	PipeDisplayer pipeDisplayer;
	
	@FXML 
	BorderPane window;
	
	@FXML
	private TextField numMoves = new TextField();
	
	@FXML
	private TextField numTime = new TextField();
	
	@FXML
	private TextField idPort = new TextField();
	
	@FXML
	private TextField ipAdd = new TextField();

	

	
	File _boardFile=null;
	ArrayList<String> _game;
	int moves = 0;
	int secondsPlay = 0;
	Timeline timer;
	String serverIP ;
	Integer portNumber ;
	Socket mySocket = null;


	
	public void initTime() {
		if (timer != null)
		{
			timer.stop();
			secondsPlay = 0;
		}
		numTime.setText(Integer.toString(secondsPlay));
		timer = new Timeline(new KeyFrame(Duration.millis(1000), e-> {
			secondsPlay++;
			numTime.setText(Integer.toString(secondsPlay));
		}));
		timer.setCycleCount(Animation.INDEFINITE);
		timer.play();
	}
	
	public String getIP() {
		return this.serverIP;
	}
	
	public int getPort() {
		return this.portNumber;
	}
	
	public void setIP(String ip) {
		this.serverIP = ip;
	}
	
	public void setPort() {
		this.portNumber = Integer.parseInt(idPort.getText());


	}

	public void finished() {
		if(idPort.getText().equals("")) {
			this.portNumber = 6400;
		}
		else
			this.portNumber = Integer.parseInt(idPort.getText());
		
		if(ipAdd.getText().equals("")) {
			this.serverIP = "127.0.0.1";
		}
		else
			this.serverIP = ipAdd.getText();
				
		try {
				
			mySocket = new Socket(this.getIP(), this.getPort());
		    PrintWriter out = new PrintWriter(mySocket.getOutputStream());

		    for (int i = 0; i < this._game.size(); i++) {
		    	out.println(this._game.get(i).toString());
		    }
		    out.println("done");
		    out.flush();
			BufferedReader in = new BufferedReader(new java.io.InputStreamReader(mySocket.getInputStream()));

			String line=null;
			
			while (!(line = in.readLine()).equals("done")) { 
				int i = Integer.parseInt(line.split(",")[0]); //rows
		        int j = Integer.parseInt(line.split(",")[1]); // cols
		        int times = Integer.parseInt(line.split(",")[2]); //rotations
				if (times != 0) { 
					Alert alert=new Alert(AlertType.INFORMATION);
					alert.setTitle("You Lose!!!");
					alert.setHeaderText("Lose Lose Loseee :O");
					alert.showAndWait();
					return;
			    }
				
		    }
			
			timer.pause();
			Alert alert=new Alert(AlertType.INFORMATION);
			alert.setTitle("You Win!!!");
			alert.setHeaderText("Win Win Winnn (:");
			alert.showAndWait();
			
		    in.close();
		    out.close();  
		    mySocket.close();

		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Alert alert=new Alert(AlertType.WARNING);
			alert.setTitle("Server Error");
			alert.setHeaderText("Wrong Port number or IP address");
			alert.showAndWait();

		}
		
		//return true;
	}
	
	public void solve() throws InterruptedException{
		timer.pause();
		if(idPort.getText().equals("")) {
			this.portNumber = 6400;
		}
		else
			this.portNumber = Integer.parseInt(idPort.getText());
		
		if(ipAdd.getText().equals("")) {
			this.serverIP = "127.0.0.1";
		}
		else
			this.serverIP = ipAdd.getText();
		
		try {
				
			mySocket = new Socket(this.getIP(), this.getPort());
		    PrintWriter out = new PrintWriter(mySocket.getOutputStream());

		    for (int i = 0; i < this._game.size(); i++) {
		    	out.println(this._game.get(i).toString());
		    }
		    out.println("done");
		    out.flush();
			BufferedReader in = new BufferedReader(new java.io.InputStreamReader(mySocket.getInputStream()));

			Timeline sortLoop = new Timeline(); 
			double curDelay = 0.2;
			String line=null;
			
			while (!(line = in.readLine()).equals("done")) { 
				int i = Integer.parseInt(line.split(",")[0]); //rows
		        int j = Integer.parseInt(line.split(",")[1]); // cols
		        int times = Integer.parseInt(line.split(",")[2]); //rotations
		        int rot = 0;
		        while( rot < times) {
					KeyFrame kf = new KeyFrame(Duration.seconds(curDelay), actionEvent -> {
						this._game = rotate(this._game,i,j);
						pipeDisplayer.setPipeDisplayer(this._game);		
					});
					rot++;
					curDelay += 0.2;
		            sortLoop.getKeyFrames().add(kf);
				}
		    }

		    
		    sortLoop.play();
		    //in.reset();
		    in.close();
		    out.close();  
		    mySocket.close();

		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Alert alert=new Alert(AlertType.WARNING);
			alert.setTitle("Server Error");
			alert.setHeaderText("Wrong Port number or IP address");
			alert.showAndWait();
		}

		
	}

	
	public ArrayList<String> rotate(ArrayList<String> myLevel,int row, int col){
		StringBuilder temp = new StringBuilder(myLevel.get(row));
		ArrayList<String> a = new ArrayList<String>(myLevel);
		
		switch(temp.charAt(col)) {
		case 'L':
			temp.setCharAt(col, 'F');
			break;
		case 'J':
			temp.setCharAt(col, 'L');
			break;
		case '7':
			temp.setCharAt(col, 'J');
			break;
		case 'F':
			temp.setCharAt(col, '7');
			break;
		case '-':
			temp.setCharAt(col, '|');
			break;
		case '|':
			temp.setCharAt(col, '-');
			break;
		}
		
		a.set(row, temp.toString());
		return a;
	}
	
	
	
	public MainWindowController() {
		super();
		numMoves.setText("0");
		this.serverIP = "127.0.0.1";
		this.portNumber = 0;
	}
	
	public void spongeBob() {
		Theme t = new Theme();
		pipeDisplayer.stopMusic();
		t.setTheme("/spongeBoB/sadspongeBob.png", "/spongeBoB/spongepinnapple.png",
				"/spongeBoB/right-up.png", "/spongeBoB/right-down.png", 
				"/spongeBoB/left-up.png", "/spongeBoB/left-down.png",
				"/spongeBoB/horizontal.png", "/spongeBoB/vertical.png",
				"/media/spongebob.wav", "/spongeBoB/sadspongeBob.png", "/spongeBoB/background.png");	
		pipeDisplayer.setTheme(t);
		pipeDisplayer.playMusic();
		
	}
	
	public void dora() {
		Theme t = new Theme();
		pipeDisplayer.stopMusic();
		t.setTheme("/Dora/dora.png", "/Dora/buts.png",
				"/spongeBoB/right-up.png", "/spongeBoB/right-down.png", 
				"/spongeBoB/left-up.png", "/spongeBoB/left-down.png",
				"/spongeBoB/horizontal.png", "/spongeBoB/vertical.png",
				"/media/dora.wav", "/spongeBoB/sadspongeBob.png", "/Dora/backgroundDora.png");	
		pipeDisplayer.setTheme(t);
		pipeDisplayer.playMusic();
		
	}

	
	
	
	public void openFile() throws IOException {
		File chosen;
		FileChooser fc=new FileChooser();
		fc.setTitle("PipeGame Levels File");	
		fc.setInitialDirectory(new File("./resources"));
		if(_game  == null) {
			chosen = new File("./resources/Levels/level1.txt");
		}
		else {
			chosen = fc.showOpenDialog(null);
		}
		if(chosen != null) {
			ArrayList<String> pipeGameLev = new ArrayList<String>();
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader (chosen));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    String         line = null;
		    StringBuilder  stringBuilder = new StringBuilder();
		    String         ls = System.getProperty("line.separator");
	
		        try {
					while((line = reader.readLine()) != null) {
						pipeGameLev.add(line);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		        try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
		        _game=pipeGameLev;
		        pipeDisplayer.setPipeDisplayer(new ArrayList<>(_game));
		        numMoves.setText("0");
		        this.initTime();

		}
	}


	
	@Override
	public void initialize(URL location, ResourceBundle Resources) {
		if(_game == null) {
			pipeDisplayer.playMusic();
			this._game = new ArrayList<String>(pipeDisplayer.pipeGame);
	        numMoves.setText("0");
	        this.initTime();

		}
		

		pipeDisplayer.setPipeDisplayer(_game);
		pipeDisplayer.addEventHandler(MouseEvent.MOUSE_CLICKED, 
			(MouseEvent e)->{
				if(_game != null) {
					double w = pipeDisplayer.getWidth() / _game.get(0).length();
					double h = pipeDisplayer.getHeight() / _game.size();
					int x = (int) (e.getX() / w);
					int y = (int) (e.getY() / h);
					boolean flag = false;
					flag = pipeDisplayer.changeByclick(x,y);
					if(flag == true) {
						moves++;
						this._game.clear();
						this._game.addAll(pipeDisplayer.pipeGame);
						numMoves.setText(Integer.toString(moves));
						//finished();
						
					}
					
				}
			}
		);
	}
	
}
