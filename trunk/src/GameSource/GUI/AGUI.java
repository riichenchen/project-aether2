package GameSource.GUI;

import GameSource.User.Inventory.InventoryItem;
import Sound.SoundManager;
import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedList;

/*AGUI.java         @Chen~
 * This class is a general GUI class that defines the fields and general functions
 * that any GUI would need to have.
 */

public class AGUI{
	//Main screen that manages all AComponents; drawing, adding, updating,
	//and removing them as necessary.
	protected static int width, height;                     //Size
    
	protected static AbstractInputSet inputSet;             //Controls
	protected static Map<String,AbstractInputSet> inputSets;

	protected static Map<String,AComponent> windows;        //Windows
    protected static ArrayList<String> windowNames;         //Names of all available windows
    protected static LinkedList<String> visibleWindows;     //Ordered list of all visible windows
	protected static AComponent focusedScreen;
    protected static ArrayList<AContainer.AScrollButton> scrollbars;   
    
    protected static InventoryItem mouseItem;              

    protected static Image cursorup;
    protected static Image cursordown;

        
    public static void bringToFront(String name){
        //Pushes a window to the front of the list to give it drawing and access priority
        visibleWindows.remove(name);
        visibleWindows.push(name);
    }

	public static void shiftFocus(String name){
        //Unfocuses the current screen and focuses the other.
		if (focusedScreen!=null)
            focusedScreen.setFocused(false);
		focusedScreen=windows.get(name);
		focusedScreen.setFocused(true);
        bringToFront(name);
	}

	public static void openWindow(String name){
        //To open a window, make it visible, focus it, and bring it to the very funct
		windows.get(name).setVisible(true);
        bringToFront(name);
		shiftFocus(name);
	}
	public static void closeWindow(String name){
        //To close a window, unlock it, unfocus it, and set it invisible
        windows.get(name).unlock();
        windows.get(name).setFocused(false);
		windows.get(name).setVisible(false);
        visibleWindows.remove(name);
        if (focusedScreen!= null && focusedScreen.equals(windows.get(name)))
            unfocus();
	}
    public static void closeWindow(){
        //Closes the current focused window, as long it's not the HUD.
        if (focusedScreen!=null && (focusedScreen instanceof AHUD == false)){
            closeWindow(focusedScreen.getName());
        }
	}
	
	public static void keyCall(String name){
	//The function is called when the AComponent name has been triggered by
	//keyboard, and opens or closes the windows as needed.
		AComponent cWindow=windows.get(name);
		if (cWindow.visible()==true){
            closeWindow(name);
		}
        else if (cWindow.visible()==false){
            openWindow(name);
		}
        else if (cWindow.focus()==false){
            shiftFocus(name);			
		}
	}
	public static void mouseClickCall(String name){
        //The function is called when the AComponent name has been clicked on. It
        //locks the AComponent if necessary, then looks through its subComponents
        //to see if the mouse lands on any subComponent that can use the mouse call
        //to trigger other events.
        AComponent cWindow=windows.get(name);
        if (cWindow.visible()){
			if (cWindow.focus()==false){
				shiftFocus(name);
			}
			if (mouseItem==null && cWindow.moveable() && cWindow.locked()==false){
                if (cWindow.moveBarCollide()){
                    cWindow.lock();
                }
            }
            cWindow.call();
        }
    }

	public static void bindToMouse(InventoryItem i){        //Attach an item to the mouse
            mouseItem=i;
    }
    public static void freeMouse(){                     //Remove the item from the mouse
        if (mouseItem!=null && hitGUI(AMouseInput.mx,AMouseInput.my)==false){
            SoundManager.getChannel("UI").addTrack("dragend");
            System.out.println(mouseItem.getKey()+" dropped at ("+AMouseInput.mx+","+AMouseInput.my+")");
        }
        mouseItem=null;
    }
    public static boolean mouseFree(){
        return mouseItem==null;
    }
        
	public static void init(int wid, int hgt){
        AMouseInput.init();
        InputManager.init();
		width=wid; height = hgt;
		
		windows = new HashMap<String,AComponent>();
        windowNames= new ArrayList<String>();
        visibleWindows=new LinkedList<String>();
	
		inputSets= new HashMap<> ();
        scrollbars=new ArrayList<>();
	}
    
    
    //Accessor Methods
    public static ArrayList<String> get_windows(){
    	return windowNames;
    }
    public static LinkedList<String> get_visibleWindows(){
        return visibleWindows;
    }
    public static AComponent getWindow(String name){
    	return windows.get(name);
    }
    
    public static boolean hitGUI(int mx, int my){
        //This function checks if a mouseclick lands on any visible component of
        //the GUI.
        for (String wname: visibleWindows){
            AComponent c=windows.get(wname);
            if (c.visible() && c.collidepoint(mx,my))
                return true;
        }
        return false;
    }
    
    public static void shiftSet(String newSet){     //Shifts inputSets. Currently not used.
    	inputSet=inputSets.get(newSet);
    }
    public static void unfocus(){
        //Unfocuses all screens of the GUI.
        if (focusedScreen!=null){
            focusedScreen.setFocused(false);
            focusedScreen=null;
        }
    }
	
	public static void update(){		
        //Update loop that loops through all the windows and updates them if they
        //are in focus, then updates from the keyboard and mouse.
		for (String name: visibleWindows){              //Check windows
            AComponent c=windows.get(name);
            if (c.focus())          
			c.update();
		}
        for (AContainer.AScrollButton s: scrollbars){   //Check scrollbars
            if (s.parent.focus()){
                s.update();
            }
        }
        inputSet.update();                              
        AMouseInput.reset();
        AMouseInput.tick();
	}
	public static void draw(Graphics g){        //Draws all the windows and cursor
        for (int i=visibleWindows.size()-1; i>=0; i--){         //Draw backwarkds: the highest
            windows.get(visibleWindows.get(i)).draw(g);         //priority should be drawn on top.
        }
        if (mouseItem!=null){                   //Draw the mouse's attached symbol if it exists,
            Image img=mouseItem.getImage();
            g.drawImage(img,AMouseInput.mx-img.getWidth(null)/2,AMouseInput.my-img.getHeight(null)/2,null);
        }
	}
    public static void tick(){
        AMouseInput.tick();
    }
    public static void add(AContainer.AScrollButton b){
        scrollbars.add(b);
    }
        
}