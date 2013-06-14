package GameSource.GUI;
import GameSource.Globals;
import java.awt.event.KeyEvent;
import java.util.HashMap;


public class NormalInputSet extends AbstractInputSet{
        public static boolean passShift=true;
	public NormalInputSet(){
		name="normal";
		keyMap=new HashMap<String, Integer>();
		keyMap.put("invent",KeyEvent.VK_I);
                keyMap.put("npcchat",KeyEvent.VK_F9);
                keyMap.put("equipment",KeyEvent.VK_E);
                keyMap.put("user_stats",KeyEvent.VK_S);
                keyMap.put("skills",KeyEvent.VK_K);
                keyMap.put("shop",KeyEvent.VK_Y);
		mouseUsed=false;
	}

	public void update(){
            passShift=InputManager.down(KeyEvent.VK_SHIFT);
            boolean passMouse=MyGUI.mouseFree() && (AMouseInput.clicked(AMouseInput.LEFT)||AMouseInput.held(AMouseInput.LEFT));
            boolean a=AMouseInput.clicked(AMouseInput.LEFT);
            boolean b=AMouseInput.released(AMouseInput.LEFT)&&(AMouseInput.held(AMouseInput.LEFT));
            if (a||b||AMouseInput.doubleclick(AMouseInput.LEFT)) {
                if (MyGUI.mouseFree()==false){
                    MyGUI.freeMouse();
                    passMouse=false;
                }
            }
		
            String [] cwindows = MyGUI.get_windows().toArray(new String [0]);
                
                for (String wname: cwindows){
			if (keyMap.get(wname)!=null && InputManager.keys[keyMap.get(wname)]){
				MyGUI.keyCall(wname);
                                InputManager.clearKey(keyMap.get(wname));
			}
                }
                if (MyGUI.getWindow("hud").visible() && MyGUI.getWindow("hud").collidepoint(AMouseInput.mx,AMouseInput.my)){
                        if (AMouseInput.clicked(AMouseInput.LEFT)){
                                MyGUI.mouseClickCall("hud");
                                passMouse=false;
                        }
                }
                else{
                    for (String wname: MyGUI.get_visibleWindows()){
                             if (MyGUI.getWindow(wname).visible() && MyGUI.getWindow(wname).collidepoint(AMouseInput.mx,AMouseInput.my)){
                                     if (AMouseInput.clicked(AMouseInput.LEFT)){
                                             MyGUI.mouseClickCall(wname);
                                             passMouse=false;
                                             break;
                                     }
                             }
                     }
                }
		if (passMouse){
                    MyGUI.unfocus();
                    Globals.theMouse.click(AMouseInput.mx, AMouseInput.my);
		}
                if (InputManager.keys[KeyEvent.VK_ESCAPE]){
                    MyGUI.closeWindow();
                }
        for (int i=0; i<KeyEvent.KEY_LAST; i++){
                if (i!=KeyEvent.VK_SHIFT && InputManager.down(i))
        		Input.InputManager.keyDown(i);
        }
    }
}