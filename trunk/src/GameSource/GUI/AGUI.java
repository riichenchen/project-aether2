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
	protected int width, height;
	protected AbstractInputSet inputSet;
	protected Map<String,AbstractInputSet> inputSets;

	protected Map<String,AComponent> windows;
        protected ArrayList<String> windowNames;
        protected LinkedList<String> visibleWindows;

	protected AComponent focusedScreen;

	protected InputManager keyboard;
	protected AMouseInput mouse;
        protected InventoryItem mouseItem;              //Static?
	
        public static boolean [] keys;			//Input fields; keyboard and mouse
        public static int mx, my;			//Public for easier updating and
        public static int [] mouseButtons;		//access by the AComponents
        public static boolean doubleclick;

        
        public void bringToFront(String name){
            visibleWindows.remove(name);
            visibleWindows.push(name);
        }
	
	public void shiftFocus(String name){
		if (focusedScreen==null)
			focusedScreen=windows.get(name);
		else{
			focusedScreen.setFocused(false);
			focusedScreen=windows.get(name);
			focusedScreen.setFocused(true);
		}
                bringToFront(name);
	}
	public void addNewWindow(AComponent a){
            windows.put(a.getName(),a);
            windowNames.add(a.getName());
        }
	public void openWindow(String name){
		windows.get(name).setVisible(true);
                bringToFront(name);
		shiftFocus(name);
	}
	public void closeWindow(String name){
                windows.get(name).setFocused(false);
		windows.get(name).setVisible(false);
                visibleWindows.remove(name);
                if (focusedScreen.equals(windows.get(name)))
                    unfocus();
	}
        public void closeWindow(){
                if (focusedScreen!=null && (focusedScreen instanceof AHUD == false)){
                    closeWindow(focusedScreen.getName());
                }
	}
	
	public void keyCall(String name){
	//The function is called when the AComponent name has been triggered either by
	//keyboard, and opens or closes the windows as needed.
		AComponent cWindow=windows.get(name);
		if (cWindow.visible()==false){
			openWindow(name);
		}
		else if (cWindow.focus()==false){
			shiftFocus(name);
		}
		else{
			cWindow.call();				
		}
	}
	
	public void mouseCall(String name){
		AComponent cWindow=windows.get(name);
		if (cWindow.visible()){
			if (cWindow.focus()==false){
				shiftFocus(name);
			}
                        cWindow.call();	
			if (mouseItem==null && cWindow.moveable()&&cWindow.locked()==false)
				cWindow.lock();
						//DO I WANT TO DO THIS?

		}
	}
	public void bindToMouse(InventoryItem i){
            mouseItem=i;
        }
        public void freeMouse(){
            if (mouseItem!=null && hitGUI(mx,my)==false)
                System.out.println(mouseItem.getKey()+" dropped at ("+mx+","+my+")");
            mouseItem=null;
        }
	public AGUI(InputManager input, AMouseInput mInput, int wid, int hgt){
		width=wid; height = hgt;
		bindTo(input);
		bindTo(mInput);
              
		keys=keyboard.get_keys();
		mx=mouse.get_x(); my= mouse.get_y();
		mouseButtons=mouse.get_buttons();
		
		windows = new HashMap<String,AComponent>();
                windowNames= new ArrayList<String>();
                visibleWindows=new LinkedList<String>();
	
		inputSets= new HashMap<> ();
		

	}
    public void bindTo(InputManager bound){
        this.keyboard = bound;
    }
    public void bindTo(AMouseInput bound){
        this.mouse = bound;
    }
    public ArrayList<String> get_windows(){
    	return windowNames;
    }
    public AComponent getWindow(String name){
    	return windows.get(name);
    }
    
    public boolean hitGUI(int mx, int my){
	for (String wname: visibleWindows){
            AComponent c=windows.get(wname);
            if (c.visible() && c.collidepoint(mx,my))
		return true;
	}
	return false;
    }
    
    public void shiftSet(String newSet){
    	inputSet=inputSets.get(newSet);
    }
    public void unfocus(){
        if (focusedScreen!=null){
            focusedScreen.setFocused(false);
            focusedScreen=null;
        }
    }
	
	public void update(){						
		keys = keyboard.get_keys();
		mx=mouse.get_x(); my= mouse.get_y();
		mouseButtons=mouse.get_buttons();
                
                if (mouseItem!=null && mouseButtons[0]==AMouseInput.MOUSEBUTTONUP){
                    freeMouse();
                }
		
		for (String name: visibleWindows){
                    AComponent c=windows.get(name);
                    if (c.focus())          //Needed?
			c.update();
		}
		inputSet.update();
		
	}
	public void draw(Graphics g){
                for (int i=visibleWindows.size()-1; i>=0; i--){
                    windows.get(visibleWindows.get(i)).draw(g);
                }
                if (mouseItem!=null){
                    Image img=mouseItem.getImage();
                    g.drawImage(img,mx-img.getWidth(null)/2,my-img.getHeight(null)/2,null);
                }
	}
        public void tick(){
            mouse.tick();
        }
}