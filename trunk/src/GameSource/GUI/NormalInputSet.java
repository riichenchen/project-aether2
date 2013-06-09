package GameSource.GUI;
import java.awt.event.KeyEvent;
import java.util.HashMap;


public class NormalInputSet extends AbstractInputSet{
	//Inventory: F9, Shop: F10, Chat: F11
	public void init(){
            name="normal";
            keyMap=new HashMap<String, Integer>();
            keyMap.put("invent",KeyEvent.VK_I);
            keyMap.put("npcchat",KeyEvent.VK_F9);
            mouseUsed=false;
        }
	public NormalInputSet(){
		name="normal";
		keyMap=new HashMap<String, Integer>();
		keyMap.put("invent",KeyEvent.VK_I);
                keyMap.put("npcchat",KeyEvent.VK_F9);
		mouseUsed=false;
	}

	public void update(){
            boolean passMouse=MyGUI.mouseFree() && (AMouseInput.clicked(AMouseInput.LEFT)||AMouseInput.held(AMouseInput.LEFT));
            
            boolean a=(AMouseInput.held(AMouseInput.LEFT)==false)&&AMouseInput.clicked(AMouseInput.LEFT);
            boolean b=AMouseInput.released(AMouseInput.LEFT)&&(AMouseInput.clicked(AMouseInput.LEFT)==false);
            if (a||b||AMouseInput.doubleclick(AMouseInput.LEFT)) {
                MyGUI.freeMouse();
                passMouse=false;
            }
		
            String [] cwindows = MyGUI.get_windows().toArray(new String [0]);
                
                if (InputManager.keys[keyMap.get("npcchat")]){
                    MyGUI.showNPC("\"We faeries don't talk about them much, but the Underclouds are a place hidden deep within the clouds you can normally see up here. Strange creatures and dark faeries live there, but they've always kept to themselves. \n" +
"There's even a rumor that there's some kind of city down there, but nobody who's gone to look has ever returned. I think there might be a path through there that lets you get to Faerie City, but I don't know. But it's probably your only choice.\" ");
                }
                
            
		for (String wname: cwindows){
			if (keyMap.get(wname)!=null && InputManager.keys[keyMap.get(wname)]){
				MyGUI.keyCall(wname);
                                InputManager.clearKey(keyMap.get(wname));
			}
                }
               for (String wname: cwindows){
			if (MyGUI.getWindow(wname).visible() && MyGUI.getWindow(wname).collidepoint(AMouseInput.mx,AMouseInput.my)){
                            /*
				if (AMouseInput.clicked(AMouseInput.LEFT)){
	//				System.out.println("hi");
					MyGUI.mouseClickCall(wname);
					AMouseInput.buttonsClicked[AMouseInput.LEFT]=AMouseInput.NO;
                                }
                                * */
                                if (AMouseInput.held(AMouseInput.LEFT)){
	//				System.out.println("hi");
					MyGUI.mousePressCall(wname);
                                        passMouse=false;
					break;
                                }
                                if (AMouseInput.clicked(AMouseInput.LEFT)){
                                        MyGUI.mouseClickCall(wname);
					AMouseInput.buttonsClicked[AMouseInput.LEFT]=AMouseInput.NO;
                                }
			}
		}
		if (passMouse){
                    MyGUI.unfocus();
                    System.out.println("Mouse call that I have nothing to do with");
		}
                if (InputManager.keys[KeyEvent.VK_ESCAPE]){
                    MyGUI.closeWindow();
                }
	}
}