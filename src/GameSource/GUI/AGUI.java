package GameSource.GUI;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedList;

//Check for locking bug with subComponents

public class AGUI{
	//Main screen that manages all AComponents; drawing, adding, updating,
	//and removing them as necessary.
	public static final int INVENTORY=0;
	public static final int SHOP=1;
	public static final int CHAT=2;
	
	private int width, height;
	private AbstractInputSet inputSet;
	private Map<String,AbstractInputSet> inputSets;

	private Map<String,AComponent> windows;
	//private ArrayList<String> windowNames;
        private LinkedList<String> windowNames;

	private AComponent focusedScreen;



	private InputManager keyboard;
	private AMouseInput mouse;
	
	public static boolean [] keys;			//Input fields; keyboard and mouse
	public static int mx, my;				//Public for easier updating and
	public static int [] mouseButtons;		//access by the AComponents
	
	private AWindow invent;
	private AWindow	shop;
	private ATextField chat;
	private ALabel info;
	private ATextArea write;
        
        public void bringToFront(String name){
            windowNames.remove(name);
            windowNames.push(name);
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
	
	public void openWindow(String name){
		windows.get(name).setVisible(true);
                bringToFront(name);
		shiftFocus(name);
	}
	public void closeWindow(String name){
                windows.get(name).setFocused(false);
		windows.get(name).setVisible(false);
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
			if (cWindow.locked()==false)
				cWindow.lock();
			cWindow.call();				//DO I WANT TO DO THIS?

		}
	}
	
	
	public AGUI(InputManager input, AMouseInput mInput, int wid, int hgt){
		width=wid; height = hgt;
		bindTo(input);
		bindTo(mInput);
                
		keys=keyboard.get_keys();
		mx=mouse.get_x(); my= mouse.get_y();
		mouseButtons=mouse.get_buttons();
		
               // windowNames=new ArrayList<String>();
		windows = new HashMap<String,AComponent>();
                windowNames=new LinkedList<String>();
		
		//Replace with reading data file
		invent=new AWindow("invent");
		invent.setSize(300,200);
		invent.setBG(0,255,0);
		invent.setLocation(50,50);
		
		shop = new AWindow("shop");
		shop.setSize(500,500);
		shop.setBG(255,0,0);
		shop.setLocation(150,50);
		AButton nest = new AButton("nest",AMessage.OPEN_WINDOW,"invent");
		nest.setSize(20,20);
		nest.setBG(0,0,255);
		nest.setLocation(200,200);
		shop.add(nest);
		
		chat = new ATextField();
		chat.setSize(750,30);
		chat.setLocation(25,550);
		chat.setVisible(true);
		chat.setName("chat");
		info= new ALabel("STUFF GOES HERE");
		info.setName("info");
		info.setLocation(500,10);
		info.setFG(0,0,0);
		info.setVisible(true);
		write=new ATextArea();
		write.setLocation(450,300);
		write.setName("textbox");
		
		windowNames.add("invent");
		windowNames.add("shop");
		windowNames.add("chat");
		windowNames.add("info");
		windowNames.add("textbox");
//		popup= new HashMap<String,AComponent>();
		windows.put(invent.getName(),invent);
		windows.put(shop.getName(),shop);
		windows.put(chat.getName(),chat);
		windows.put(info.getName(),info);
		windows.put(write.getName(),write);						
//		perm= new HashMap<String,AComponent>();						*
//		perm[0]=info;												*
				//													*
		//***********************************************************
		
		inputSets= new HashMap<> ();
		inputSets.put("battle",new BattleInputSet(this));
		inputSets.put("normal",new NormalInputSet(this));
		inputSet=inputSets.get("normal");
                AProcessor.bindTo(this);
	}
    public void bindTo(InputManager bound){
        this.keyboard = bound;
    }
    public void bindTo(AMouseInput bound){
        this.mouse = bound;
    }
    public LinkedList<String> get_windows(){
    	return windowNames;
    }
    public AComponent getWindow(String name){
    	return windows.get(name);
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
		
		for (String name: windowNames){
			AComponent c=windows.get(name);
			if (c.focus())
				c.update();
		}

		inputSet.update();
		
	}
	public void draw(Graphics g){
	//	g.clearRect(0,0,width,height);
                for (int i=windowNames.size()-1; i>=0; i--){
                    windows.get(windowNames.get(i)).draw(g);
                }
	//	for (String k: windowNames){
	//		windows.get(k).draw(g);
	//	}
	}
}