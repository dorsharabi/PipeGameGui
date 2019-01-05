
package view;

import java.io.FileInputStream;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.image.Image;

public class Theme {

	private String _s;
	private	String _g;
	private String _L;
	private String _f;
	private String _j;
	private String _seven;
	private String _finished;
	private String _hor;
	private String _ver;
	private String _music;
	private String _back;
	//@FXML
	//PipeDisplayer pipeDisplayer;

	
	public Theme() {
		this._s = new String("/spongeBoB/sadspongeBob.png");
		this._g = new String("/spongeBoB/spongepinnapple.png");
		this._L = new String("/spongeBoB/right-up.png");
		this._f = new String("/spongeBoB/right-down.png");
		this._j = new String("/spongeBoB/left-up.png");
		this._seven = new String("/spongeBoB/left-down.png");
		this._hor = new String("/spongeBoB/horizontal.png");
		this._ver = new String("/spongeBoB/vertical.png");
		this._finished = new String("/spongeBoB/sadspongeBob.png");
		this._music = new String("/media/spongebob.wav");
		this._back = new String("/spongeBoB/background.png");
	}
	
	public void setTheme(String s,String g,String L, String f, String j ,String seven,String hor,String ver,String music, String finished,String back) {
		
		_s=s;
		_g=g;
		_L=L;
		_f=f;
		_j=j;
		_seven=seven;
		_hor=hor;
		_ver=ver;
		_music=music;
		_finished=finished;
		_back=back;
		
	}
	

	public String get_s() {
		return _s;
	}

	public String get_g() {
		return _g;
	}

	public String get_L() {
		return _L;
	}

	public String get_f() {
		return _f;
	}

	public String get_j() {
		return _j;
	}

	public String get_seven() {
		return _seven;
	}

	public String get_finished() {
		return _finished;
	}

	public String get_hor() {
		return _hor;
	}

	public String get_ver() {
		return _ver;
	}

	public String get_music() {
		return _music;
	}

	public String get_back() {
		return _back;
	}
}

