package GameSource.GUI;
import java.awt.event.KeyEvent;
import java.util.HashMap;


public class NormalInputSet extends AbstractInputSet{
	//Inventory: F9, Shop: F10, Chat: F11
	
	public NormalInputSet(AGUI g){
		name="normal";
		myGUI=g;
		keyMap=new HashMap<String, Integer>();
		keyMap.put("invent",KeyEvent.VK_F9);
		keyMap.put("shop",KeyEvent.VK_F10);
		keyMap.put("chat",KeyEvent.VK_F11);
		keyMap.put("textbox",KeyEvent.VK_F12);
		mouseUsed=false;
	}

	public void update(){
		if (AGUI.mouseButtons[0]==AMouseInput.MOUSEBUTTONUP)
			mouseUsed=false;
                String [] cwindows = myGUI.get_windows().toArray(new String [0]);
		for (String wname: cwindows){
			if (keyMap.get(wname)!=null && AGUI.keys[keyMap.get(wname)]){
				myGUI.keyCall(wname);
			}

			if (myGUI.getWindow(wname).visible() && AGUI.mouseButtons[0]==AMouseInput.MOUSEBUTTONDOWN){
				if (mouseUsed==false && myGUI.getWindow(wname).collidepoint(AGUI.mx,AGUI.my)){
					System.out.println("hi");
					myGUI.mouseCall(wname);
					mouseUsed=true;
				}
			}
		}
		if ((AGUI.mouseButtons[0]==AMouseInput.MOUSEBUTTONDOWN) && mouseUsed==false){
			myGUI.unfocus();
		}
	}
}