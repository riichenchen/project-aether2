package GameSource.GUI;/**/

import java.awt.event.MouseEvent;

public class AMouseInput{
	public static final int MOUSEBUTTONUP= 0; //unpressed
	public static final int MOUSEBUTTONDOWN = 1;	//pressed
	public static final int [] NULL = {0,0,0};
	
	private int mx, my;
	private int [] buttons;
	
	public AMouseInput(){
		mx=-1; my=-1;
		buttons= new int [3];
	}
	public void update(MouseEvent e){
		mx=e.getX(); my= e.getY();
	}
	public void press(MouseEvent e){
		buttons[e.getButton()-1]=MOUSEBUTTONDOWN;
	}
	public void release(MouseEvent e){
		buttons[e.getButton()-1]=MOUSEBUTTONUP;
	}
	public int get_x(){
		return mx;
	}
	public int get_y(){
		return my;
	}
	public int [] get_buttons(){
		return buttons;
	}
}