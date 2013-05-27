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
		if (myGUI.mouseButtons[0]==AMouseInput.MOUSEBUTTONUP)
			mouseUsed=false;
		for (String name: myGUI.get_windows()){
			if (keyMap.get(name)!=null && AGUI.keys[keyMap.get(name)]){
				myGUI.keyCall(name);
			}

			if (myGUI.getWindow(name).visible() && myGUI.mouseButtons[0]==AMouseInput.MOUSEBUTTONDOWN){
				if (mouseUsed==false && myGUI.getWindow(name).collidepoint(AGUI.mx,AGUI.my)){
					System.out.println("hi");
					myGUI.mouseCall(name);
					mouseUsed=true;
				}
			}
		}
		if ((myGUI.mouseButtons[0]==AMouseInput.MOUSEBUTTONDOWN) && mouseUsed==false){
			myGUI.unfocus();
		}
	}
}