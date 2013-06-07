package GameSource.GUI;
import java.awt.event.KeyEvent;
import java.util.HashMap;


public class NormalInputSet extends AbstractInputSet{
	//Inventory: F9, Shop: F10, Chat: F11
	public void init(){
            name="normal";
            keyMap=new HashMap<String, Integer>();
            keyMap.put("invent",KeyEvent.VK_I);
            mouseUsed=false;
        }
	public NormalInputSet(){
		name="normal";
		keyMap=new HashMap<String, Integer>();
		keyMap.put("invent",KeyEvent.VK_I);
		mouseUsed=false;
	}

	public void update(){
            if (AMouseInput.released(AMouseInput.LEFT)) {
                MyGUI.freeMouse();
            }
		
            String [] cwindows = MyGUI.get_windows().toArray(new String [0]);
                /*
                if (AGUI.keys[keyMap.get("npcchat")]){
                    myGUI.showNPC("\"We faeries don't talk about them much, but \\nthe Underclouds are a place hidden deep \\nwithin the clouds you can normally see up \\nhere. Strange creatures and dark faeries live \\nthere, but they've always kept to themselves. \\n\n" +
"There's even a rumor that there's some kind of city down there, but nobody who's gone to look has ever returned. I think there might be a path through there that lets you get to Faerie City, but I don't know. But it's probably your only choice.\" ");
                }
                * */
		for (String wname: cwindows){
			if (keyMap.get(wname)!=null && AGUI.keys[keyMap.get(wname)]){
				MyGUI.keyCall(wname);
			}

			if (MyGUI.getWindow(wname).visible() && MyGUI.getWindow(wname).collidepoint(AMouseInput.mx,AMouseInput.my)){
				if (AMouseInput.clicked(AMouseInput.LEFT)){
	//				System.out.println("hi");
					MyGUI.mouseClickCall(wname);
					AMouseInput.buttonsClicked[AMouseInput.LEFT]=AMouseInput.NO;
                                }
                                if (AMouseInput.held(AMouseInput.LEFT)){
	//				System.out.println("hi");
					MyGUI.mousePressCall(wname);
					AMouseInput.buttonsHeld[AMouseInput.LEFT]=AMouseInput.NO;
                                }
			}
		}
		if (AMouseInput.clicked(AMouseInput.LEFT)||AMouseInput.held(AMouseInput.LEFT)){
                    MyGUI.unfocus();
		}
                if (AGUI.keys[KeyEvent.VK_ESCAPE]){
                    MyGUI.closeWindow();
                }
	}
}