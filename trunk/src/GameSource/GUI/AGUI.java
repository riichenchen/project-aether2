package GameSource.GUI;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

//CURRENT TASK: Order of display/render; use priority queue? LinkedList?
//KEY ISSUES TO RESOLVE: Line 67

public class AGUI{
	//Main screen that manages all AComponents; drawing, adding, updating,
	//and removing them as necessary.
	public static final int INVENTORY=0;
	public static final int SHOP=1;
	public static final int CHAT=2;
	
	private int width, height;
	private AbstractInputSet inputSet;
	private Map<String,AbstractInputSet> inputSets;
//	private Map<String,AComponent> popup;
	private Map<String,AComponent> windows;
	private ArrayList<String> windowNames;
//	private Map<String,AComponent> perm;
	private AComponent focusedScreen;



	private InputManager keyboard;
	private AMouseInput mouse;
//	private AComponent [] popup;
//	private AComponent [] perm;
//	private ArrayList <AComponent> popup;	//AComponents that are temporarily opened
//	private ArrayList <AComponent> perm;	//AComponents that are always displayed (HUD)
	
	public static boolean [] keys;			//Input fields; keyboard and mouse
	public static int mx, my;				//Public for easier updating and
	public static int [] mouseButtons;		//access by the AComponents
	
	private AWindow invent;
	private AWindow	shop;
	private ATextField chat;
	private ALabel info;
	private ATextArea write;
	
	public void shiftFocus(String name){
		if (focusedScreen==null)
			focusedScreen=windows.get(name);
		else{
			focusedScreen.setFocused(false);
			focusedScreen=windows.get(name);
			focusedScreen.setFocused(true);
		}
	}
	
	public void openWindow(String name){
		windows.get(name).setVisible(true);
		shiftFocus(name);
	}
	public void closeWindow(String name){
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
			cWindow.call();				//DO I WANT TO DO THIS?
		}
	}
	
	public void mouseCall(String name){
		System.out.println(name+" called by mouse");
		AComponent cWindow=windows.get(name);
		if (cWindow.visible()){
			if (cWindow.focus()==false){
				shiftFocus(name);
			}
			if (cWindow.locked()==false)
				cWindow.lock();
			else{
				cWindow.call();				//DO I WANT TO DO THIS?
			}
		}
	}
	
	
	public AGUI(InputManager input, AMouseInput mInput, int wid, int hgt){
		width=wid; height = hgt;
		bindTo(input);
		bindTo(mInput);
		keys=keyboard.get_keys();
		mx=mouse.get_x(); my= mouse.get_y();
		mouseButtons=mouse.get_buttons();
		
		
		//Replace with reading data file
		invent=new AWindow("invent");
		invent.setSize(300,200);
		invent.setBG(0,255,0);
		invent.setLocation(50,50);
		
		shop = new AWindow("shop");
		shop.setSize(500,500);
		shop.setBG(255,0,0);
		shop.setLocation(150,50);
		AWindow nest = new AWindow("nest");
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
		
		windowNames=new ArrayList<String>();
		windows = new HashMap<String,AComponent>();
		
		
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
		
		/*Hashmap of Window Frames mapped to String
		 *Note: use inputSet.update()
		 *where inside it goes through each key and what not
		 *in the inputset and does thing accordingly
		 *When you make the input set, give it a binding to the
		 *gui so that it can use GUI.attachFrame(), GUI.detachFrame(), etc.*/
		 /*
		if (inputSet.invent(keys)){
			System.out.println("INVENT");
			System.out.println(popup[INVENTORY].visible()+","+popup[SHOP].visible()+","+popup[INVENTORY].visible());
			if (popup[INVENTORY].focus()==false){
				popup[INVENTORY].setFocused(true);
				popup[INVENTORY].setVisible(true);
				popup[SHOP].setFocused(false);
				popup[CHAT].setFocused(false);
			}
			else{
				popup[INVENTORY].setVisible(false);
				popup[INVENTORY].setFocused(false);
			}
		}
		if (inputSet.shop(keys)){
			System.out.println("SHOP");
			System.out.println(popup[INVENTORY].visible()+","+popup[SHOP].visible()+","+popup[INVENTORY].visible());
			if (popup[SHOP].focus()==false){
				popup[SHOP].setVisible(true);
				popup[SHOP].setFocused(true);
				popup[INVENTORY].setFocused(false);
				popup[CHAT].setFocused(false);
			}
			else{
				popup[SHOP].setVisible(false);
				popup[SHOP].setFocused(false);
			}
				
		}
		if (inputSet.chat(keys)){
			System.out.println("CHAT");
			System.out.println(popup[INVENTORY].visible()+","+popup[SHOP].visible()+","+popup[INVENTORY].visible());
			if (popup[CHAT].focus()==false){
				popup[CHAT].setFocused(true);
				popup[SHOP].setFocused(false);
				popup[INVENTORY].setFocused(false);
			}
			else
				popup[CHAT].setFocused(false);
		}
		*/
	}
	public void draw(Graphics g){
	//	g.clearRect(0,0,width,height);
		for (String k: windowNames){
			windows.get(k).draw(g);
		}
	}
}