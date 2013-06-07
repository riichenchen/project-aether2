package GameSource.GUI;
import java.awt.event.KeyEvent;
import java.util.HashMap;


public class NormalInputSet extends AbstractInputSet{
	//Inventory: F9, Shop: F10, Chat: F11
	
	public NormalInputSet(MyGUI g){
		name="normal";
		myGUI=g;
		keyMap=new HashMap<String, Integer>();
                keyMap.put("chat",KeyEvent.VK_F8);
		keyMap.put("npcchat",KeyEvent.VK_F9);
		keyMap.put("shop",KeyEvent.VK_F10);
		keyMap.put("invent",KeyEvent.VK_F11);
		keyMap.put("write",KeyEvent.VK_F12);
		mouseUsed=false;
	}

	public void update(){
		if (AGUI.mouseButtons[0]==AMouseInput.MOUSEBUTTONUP)
			mouseUsed=false;
                else
                    myGUI.freeMouse();
                String [] cwindows = myGUI.get_windows().toArray(new String [0]);
                if (AGUI.keys[keyMap.get("npcchat")]){
                    myGUI.showNPC("\"We faeries don't talk about them much, but \\nthe Underclouds are a place hidden deep \\nwithin the clouds you can normally see up \\nhere. Strange creatures and dark faeries live \\nthere, but they've always kept to themselves. \\n\n" +
"There's even a rumor that there's some kind of city down there, but nobody who's gone to look has ever returned. I think there might be a path through there that lets you get to Faerie City, but I don't know. But it's probably your only choice.\" ");
                }
		for (String wname: cwindows){
			if (keyMap.get(wname)!=null && AGUI.keys[keyMap.get(wname)]){
				myGUI.keyCall(wname);
			}

			if (myGUI.getWindow(wname).visible() && AGUI.mouseButtons[0]==AMouseInput.MOUSEBUTTONDOWN){
				if (mouseUsed==false && myGUI.getWindow(wname).collidepoint(AGUI.mx,AGUI.my)){
	//				System.out.println("hi");
					myGUI.mouseCall(wname);
					mouseUsed=true;
				}
			}
		}
		if ((AGUI.mouseButtons[0]==AMouseInput.MOUSEBUTTONDOWN) && mouseUsed==false){
			myGUI.unfocus();
		}
                if (AGUI.keys[KeyEvent.VK_ESCAPE]){
                    myGUI.closeWindow();
                }
	}
}