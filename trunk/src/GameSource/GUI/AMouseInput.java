package GameSource.GUI;/**/

import java.awt.event.MouseEvent;

public class AMouseInput{
	public static final int MOUSEBUTTONUP= 0; //unpressed
	public static final int MOUSEBUTTONDOWN = 1;	//pressed
	public static final int [] NULL = {0,0,0};
        public static final int DOUBLECLICK=400;
        
	private int mx, my;
	private int [] buttons;
	
        private int pressTimer, releaseTimer;
        
	public AMouseInput(){
		mx=-1; my=-1;
		buttons= new int [3];
                pressTimer=0;
                releaseTimer=0;
	}
	public void update(MouseEvent e){
		mx=e.getX(); my= e.getY();
	}
	public void press(MouseEvent e){
            if (pressTimer<DOUBLECLICK && buttons[e.getButton()-1]==MOUSEBUTTONUP){
                AGUI.doubleclick=true;
            }
            buttons[e.getButton()-1]=MOUSEBUTTONDOWN;
            pressTimer=0;
            
            
	}
	public void release(MouseEvent e){
            buttons[e.getButton()-1]=MOUSEBUTTONUP;
            releaseTimer=0;
            AGUI.doubleclick=false;
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
        public void tick(){
            pressTimer+=10;
            releaseTimer+=10;
        }
}