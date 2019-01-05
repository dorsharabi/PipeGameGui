package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;

//import com.sun.javafx.tk.Toolkit;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
//import sun.text.resources.cldr.ext.FormatData_en_CH;
import javafx.util.Duration;

public class PipeDisplayer extends Canvas{


	ArrayList<String> pipeGame;
	private StringProperty s;
	private	StringProperty g;
	private StringProperty L;
	private StringProperty f;
	private StringProperty j;
	private StringProperty seven;
	private StringProperty hor;
	private StringProperty ver;
	private StringProperty music;
	private StringProperty finished;
	private Thread musicThread;
	private MediaPlayer player;
	private StringProperty back;

	private int counter;
	@FXML
	PipeDisplayer pipeDisplayer;

	
	void stopMusic() {
		if(this.musicThread!=null) {
			player.stop();
			this.musicThread.stop();
			
		}	
		
	}
	
	void playMusic() {
		
		 File file = new File(music.get());
		 //System.out.println(getClass().getResource(music.get()).toExternalForm());
		 player =new MediaPlayer(new Media(getClass().getResource(music.get()).toExternalForm()));		 
		 player.setOnEndOfMedia(musicThread=new Thread(new Runnable() {
		       public void run() {
		         player.seek(Duration.ZERO);
		       }
		   }));
		  player.play();
	
}
	
	public String getHor() {
		return hor.get();
	}

	public void setHor(String hor) {
		this.hor.set(hor);
	}

	public String getVer() {
		return ver.get();
	}

	public void setVer(String ver) {
		this.ver.set(ver);
	}

	public String getS() {
		return s.get();
	}

	public void setS(String s) {
		this.s.set(s);
	}

	public String getG() {
		return g.get();
	}

	public void setG(String g) {
		this.g.set(g);
	}

	public String getL() {
		return L.get();
	}

	public void setL(String l) {
		this.L.set(l);
	}

	public String getF() {
		return f.get();
	}

	public void setF(String f) {
		this.f.set(f);
	}

	public String getJ() {
		return j.get();
	}

	public void setJ(String j) {
		this.j.set(j);
	}

	public String getSeven() {
		return seven.get();
	}

	public void setSeven(String seven) {
		this.seven.set(seven);
	}

	public String getFinished() {
		return finished.get();
	}

	public void setBack(String back) {
		this.back.set(back);
	}

	public String getBack() {
		return back.get();
	}
	
	public PipeDisplayer() {
		pipeGame = new ArrayList<String>();
		pipeGame.add("s----L");
		pipeGame.add("L----L");
		pipeGame.add("L----L");
		pipeGame.add("L----g");
		this.s=new SimpleStringProperty();
		this.f=new SimpleStringProperty();
		this.g=new SimpleStringProperty();
		this.j=new SimpleStringProperty();
		this.L=new SimpleStringProperty();
		this.seven=new SimpleStringProperty();
		this.ver=new SimpleStringProperty();
		this.hor=new SimpleStringProperty();
		this.music=new SimpleStringProperty();
		this.finished=new SimpleStringProperty();
		this.music.set(new Theme().get_music());
		this.counter = 0;
		this.back=new SimpleStringProperty();

	}
	
	public void setFinished(StringProperty finished) {
		this.finished = finished;
	}
	
	public void setPipeDisplayer(ArrayList<String> game) {
		if(game != null) {
			this.pipeGame = new ArrayList<>(game);
			redraw();
		}
		
	}
	
	
	public void setTheme(Theme t) {
		s.set(t.get_s());
		f.set(t.get_f());
		g.set(t.get_g());
		j.set(t.get_j());
		L.set(t.get_L());
		finished.set(t.get_finished());
		seven.set(t.get_seven());
		ver.set(t.get_ver());
		hor.set(t.get_hor());
		music.set(t.get_music());
		back.set(t.get_back());
		
		redraw();
	}	

	
	public Boolean changeByclick(int x, int y) {
		StringBuilder temp = new StringBuilder(pipeGame.get(y));
		ArrayList<String> a = new ArrayList<String>(pipeGame);
	    boolean flag = false;
		switch (temp.charAt(x)) {
		case 'L':
			temp.setCharAt(x, 'F');
			flag = true;
			break;
		case 'F':
			temp.setCharAt(x, '7');
			flag = true;
			break;
		case '7':
			temp.setCharAt(x, 'J');
			flag = true;
			break;
		case 'J':
			temp.setCharAt(x, 'L');
			flag = true;
			break;
		case '-':
			temp.setCharAt(x, '|');
			flag = true;
			break;
		case '|':
			temp.setCharAt(x, '-');
			flag = true;
			break;
		default:
			break;
		}
		
		a.set(y, temp.toString());
		this.setPipeDisplayer(a);
		return flag;
	}
	
	public void redraw() {
		if(pipeGame!=null) {
			double W=this.getWidth();
			double H=this.getHeight();
			double w=W/pipeGame.get(0).length();
			double h=H/pipeGame.size();
			
			GraphicsContext gc=this.getGraphicsContext2D();
			Image startImage=null;
			
				//startImage = new Image(new FileInputStream(s.get()));
				try {
					startImage = new Image(getClass().getResource(s.get()).openStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			Image horizontal=null;
				try {
					horizontal = new Image(getClass().getResource(hor.get()).openStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			
			Image leftDown=null;
				try {
					leftDown = new Image(getClass().getResource(seven.get()).openStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			
			Image leftUp=null;
				try {
					leftUp = new Image(getClass().getResource(j.get()).openStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			
			Image rightDown=null;
				try {
					rightDown = new Image(getClass().getResource(f.get()).openStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			
			Image rightUp=null;
				try {
					rightUp = new Image(getClass().getResource(L.get()).openStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			
			Image vertical=null;
				try {
					vertical = new Image(getClass().getResource(ver.get()).openStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			
			Image goal=null;
				try {
					goal = new Image(getClass().getResource(g.get()).openStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			
			Image backImage=null;
				try {
					backImage = new Image(getClass().getResource(back.get()).openStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			//TODO set finished photo
			
			
			gc.setFill(Color.WHITE);
			gc.drawImage(backImage, 0, 0, W, H);
			
			for(int i=0;i<pipeGame.size();i++) {
				for(int j=0;j<pipeGame.get(0).length();j++) {
					switch (pipeGame.get(i).charAt(j)) {
					case 's':
						gc.drawImage(startImage,j*w,i*h,w,h);//840.1350,j*w,i*h
						break;
					
					case 'g':
						gc.drawImage(goal,j*w,i*h,w,h);
						break;
					case '-':
						gc.drawImage(horizontal,j*w,i*h,w,h);
						break;	
					case '|':
						gc.drawImage(vertical,j*w,i*h,w,h);
						break;
					case 'L':
						gc.drawImage(rightUp,j*w,i*h,w,h);
						break;
					case '7':
						gc.drawImage(leftDown,j*w,i*h,w,h);
						break;
					case 'F':
						gc.drawImage(rightDown,j*w,i*h,w,h);
						break;
					case 'J':
						gc.drawImage(leftUp,j*w,i*h,w,h);
						break;
					default:
						break;
					} {
					
					
					}
					
				
				}
				
			}
		}
		
	}
	
	@Override
	public boolean isResizable() {
		return true;
	}

	@Override
	public double minHeight(double width) {
		return 90;
	}

	@Override
	public double maxHeight(double width) {
		return 1200;
	}

	@Override
	public double prefHeight(double width) {
		return minHeight(width);
	}

	@Override
	public double minWidth(double height) {
		return 0;
	}

	@Override
	public double maxWidth(double height) {
		return 1200;
	}

	@Override
	public void resize(double width, double height) {
		super.setWidth(width - 15);
		super.setHeight(height - 15);
		redraw();
	}
	

}
