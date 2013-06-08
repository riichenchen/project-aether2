package GameSource.GUI;/**/

import java.awt.event.MouseEvent;

public class AMouseInput{
	public static final int NO= 0; //unpressed
	public static final int YES = 1;	//pressed
        public static final int DOUBLE=2;
        public static final int LEFT=0;
        public static final int CENTER=1;
        public static final int RIGHT=2;
	public static final int [] NULL = {0,0,0};
        public static final int DOUBLECLICK=400;
        
	public static int mx, my;
	public static int [] buttonsClicked;       //public?
        public static int [] buttonsHeld;
        public static int [] buttonsReleased;
        public static int [] doubleclick;
	
        private static int clickTimer;
        
        public static void init(){
            mx=-1; my=-1;
            buttonsClicked= new int [3];
            buttonsHeld= new int [3];
            buttonsReleased=new int[3];
            doubleclick=new int[3];
            clickTimer=0;
        }
	public AMouseInput(){
		mx=-1; my=-1;
		buttonsClicked= new int [3];
                buttonsHeld= new int [3];
                buttonsReleased=new int[3];
                doubleclick=new int[3];
                clickTimer=0;
	}
	public static void update(MouseEvent e){
		mx=e.getX(); my= e.getY();
	}
        public static void click(MouseEvent e){
            buttonsClicked[e.getButton()-1]=YES;
            if (clickTimer<DOUBLECLICK){
                doubleclick[e.getButton()-1]=YES;
                System.out.println("Doubleclick!");
            }
            clickTimer=0;
        }
	public static void press(MouseEvent e){
            buttonsHeld[e.getButton()-1]=YES;
//            if (clickTimer<DOUBLECLICK){
//                doubleclick[e.getButton()-1]=YES;
//                System.out.println("Doubleclick!");
//            }
	}
        public static void release(MouseEvent e){
            buttonsHeld[e.getButton()-1]=NO;
            buttonsReleased[e.getButton()-1]=YES;
        }
	public static void reset(){
            for (int i=0; i<3; i++){
                buttonsClicked[i]=NO;
//                buttonsHeld[i]=NO;
                buttonsReleased[i]=NO;
                doubleclick[i]=NO;
            }
	}
	
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
        public static void tick(){
            clickTimer+=10;
        }
}