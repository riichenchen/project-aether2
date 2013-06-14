package GameSource.GUI;

import Sound.SoundManager;
import java.awt.event.MouseEvent;

/*AMouseInput.java          @Chen~
 * This class records input states from the mouse. To record button stats, it 
 * contains 3 boolean arrays: if a button is clicked, if it is held, and if it 
 * is released for all three buttons on the mouse. The mouse location is stored
 * in mx, my fields. It also contains a timer to check for doubleclicks.
 */
public class AMouseInput{
	public static final int NO= 0; //unpressed
	public static final int YES = 1;	//pressed
	public static final int DOUBLE=2;
	public static final int LEFT=0;
	public static final int CENTER=1;
	public static final int RIGHT=2;
    public static final int DOUBLECLICK=200;        //Time between doubleclicks
        
	public static int mx, my;
	public static int [] buttonsClicked;       
	public static int [] buttonsHeld;
	public static int [] buttonsReleased;
	public static int [] doubleclick;               //Whether a doubleclick occurred

	private static int clickTimer;
	
	public static void init(){
		mx=-1; my=-1;
		buttonsClicked= new int [3];
		buttonsHeld= new int [3];
		buttonsReleased=new int[3];
		doubleclick=new int[3];
		clickTimer=0;
	}
	
	public static void update(MouseEvent e){        //Update location
		mx=e.getX(); my= e.getY();
	}

	public static void press(MouseEvent e){
        //Invoked when MouseListener.mousePressed() is called. It manages click (just pressed)
        //vs. hold as follows:
        //      -If the button was previously clicked, the key is now being held and not clicked
        //      -If the key was previously neither clicked nor held, the key is now being clicked
        //      -If the key was previously held but not clicked, the key is still being held.
		if (buttonsClicked[e.getButton()-1]==YES){
			buttonsHeld[e.getButton()-1]=YES;
			buttonsClicked[e.getButton()-1]=NO;
		}
		else if (buttonsHeld[e.getButton()-1]==NO){
			buttonsClicked[e.getButton()-1]=YES;
			if (clickTimer<DOUBLECLICK){            //Check the time since the last click
				doubleclick[e.getButton()-1]=YES;
			}
			clickTimer=0;
		}    

	}
	public static void release(MouseEvent e){       //Invoked at MouseListener.mouseReleased()
		buttonsHeld[e.getButton()-1]=NO;
//           buttonsClicked[e.getButton()-1]=NO;
                if (buttonsClicked[e.getButton()-1]==YES){
                    SoundManager.getChannel("UI").addTrack("mouseclick");
                }
		buttonsReleased[e.getButton()-1]=YES;
	}
	public static void reset(){             //Clear all button states
		for (int i=0; i<3; i++){
			buttonsClicked[i]=NO;
			buttonsReleased[i]=NO;
			doubleclick[i]=NO;
		}
	}
    
	public static void tick(){              //Update the timer
		clickTimer+=10;
	}
    
    //Methods for quickly checking button states~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static boolean clicked(int b){
		return buttonsClicked[b]==YES;
	}
	public static boolean held(int b){
		return buttonsHeld[b]==YES;
	}
	public static boolean released(int b){
		return buttonsReleased[b]==YES;
	}
	public static boolean doubleclick(int b){
		return doubleclick[b]==YES;
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
