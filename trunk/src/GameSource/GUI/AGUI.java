package GameSource.GUI;

import GameSource.User.Inventory.InventoryItem;
import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedList;

//Check for locking bug with subComponents

public class AGUI{
	//Main screen that manages all AComponents; drawing, adding, updating,
	//and removing them as necessary.
	protected static int width, height;
	protected static AbstractInputSet inputSet;
	protected static Map<String,AbstractInputSet> inputSets;

	protected static Map<String,AComponent> windows;
        protected static ArrayList<String> windowNames;
        protected static LinkedList<String> visibleWindows;

	protected static AComponent focusedScreen;
        
        protected static InventoryItem mouseItem;              //Static?
	private static ArrayList<AContainer.AScrollButton> scrollbars;
//        public static boolean [] keys;			//Input fields; keyboard and mouse
//        public static int mx, my;			//Public for easier updating and
//        public static int [] mouseButtons;		//access by the AComponents
//        public static boolean doubleclick;

        
        public static void bringToFront(String name){
            visibleWindows.remove(name);
            visibleWindows.push(name);
        }
	
	public static void shiftFocus(String name){
		if (focusedScreen==null)
			focusedScreen=windows.get(name);
		else{
			focusedScreen.setFocused(false);
			focusedScreen=windows.get(name);
			focusedScreen.setFocused(true);
		}
                bringToFront(name);
	}
	public static void addNewWindow(AComponent a){
            windows.put(a.getName(),a);
            windowNames.add(a.getName());
        }
	public static void openWindow(String name){
		windows.get(name).setVisible(true);
                bringToFront(name);
		shiftFocus(name);
	}
	public static void closeWindow(String name){
                windows.get(name).setFocused(false);
		windows.get(name).setVisible(false);
                visibleWindows.remove(name);
                if (focusedScreen!= null && focusedScreen.equals(windows.get(name)))
                    unfocus();
	}
        public static void closeWindow(){
                if (focusedScreen!=null && (focusedScreen instanceof AHUD == false)){
                    closeWindow(focusedScreen.getName());
                }
	}
	
	public static void keyCall(String name){
	//The function is called when the AComponent name has been triggered either by
	//keyboard, and opens or closes the windows as needed.
		AComponent cWindow=windows.get(name);
		if (cWindow.visible()==false){
			openWindow(name);
		}
		if (cWindow.focus()==false){
			shiftFocus(name);
		}
		else{
			closeWindow(name);				
		}
	}
	public static void mousePressCall(String name){
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
	public static void mouseClickCall(String name){
		AComponent cWindow=windows.get(name);
		if (cWindow.visible()){
			if (cWindow.focus()==false){
				shiftFocus(name);
			}
                        cWindow.call();	
		}
	}
	public static void bindToMouse(InventoryItem i){
            mouseItem=i;
        }
        public static void freeMouse(){
            if (mouseItem!=null && hitGUI(AMouseInput.mx,AMouseInput.my)==false)
                System.out.println(mouseItem.getKey()+" dropped at ("+AMouseInput.mx+","+AMouseInput.my+")");
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
    
    public static ArrayList<String> get_windows(){
    	return windowNames;
    }
    public static AComponent getWindow(String name){
    	return windows.get(name);
    }
    
    public static boolean hitGUI(int mx, int my){
	for (String wname: visibleWindows){
            AComponent c=windows.get(wname);
            if (c.visible() && c.collidepoint(mx,my))
		return true;
	}
	return false;
    }
    
    public static void shiftSet(String newSet){
    	inputSet=inputSets.get(newSet);
    }
    public static void unfocus(){
        if (focusedScreen!=null){
            focusedScreen.setFocused(false);
            focusedScreen=null;
        }
    }
	
	public static void update(){						
                AMouseInput.tick();
		
		for (String name: visibleWindows){
                    AComponent c=windows.get(name);
                    if (c.focus())          //Needed?
			c.update();
		}
                for (AContainer.AScrollButton s: scrollbars){
                    if (s.parent.focus()){
                        s.update();
                    }
                }
		inputSet.update();
		AMouseInput.reset();
                
                
//                InputManager.clearTyped();
	}
	public static void draw(Graphics g){
                for (int i=visibleWindows.size()-1; i>=0; i--){
                    windows.get(visibleWindows.get(i)).draw(g);
                }
                if (mouseItem!=null){
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