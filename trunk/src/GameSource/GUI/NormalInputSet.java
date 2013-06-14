package GameSource.GUI;
import GameSource.Globals;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/* NormalInputSet.java			@Chen~
 * An extension of the AbstractInputSet that is the default
 * action-result procedure to follow.
 */

public class NormalInputSet extends AbstractInputSet{
//        public static boolean passShift=true;
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
        //Check: Is a mouse click existent? If not, no hours for volunteers.
        boolean passMouse=MyGUI.mouseFree() && (AMouseInput.clicked(AMouseInput.LEFT)||AMouseInput.held(AMouseInput.LEFT));

        //Checks whether or not to free the mouse from the object it is bound to (and therefore dropping the item.
        //This occurs when there is an object bound to the mouse and the mouse is clicked outside the GUI
        boolean a=AMouseInput.clicked(AMouseInput.LEFT);
//         boolean b=AMouseInput.released(AMouseInput.LEFT)&&(AMouseInput.held(AMouseInput.LEFT));
        if (a||AMouseInput.doubleclick(AMouseInput.LEFT)) {
            if (MyGUI.mouseFree()==false){
                MyGUI.freeMouse();
                passMouse=false;
            }
        }

        String [] cwindows = MyGUI.get_windows().toArray(new String [0]);
        for (String wname: cwindows){
        //Loop through all windows and check to see if their shortcut key has been pressed
            if (keyMap.get(wname)!=null && InputManager.keys[keyMap.get(wname)]){
                MyGUI.keyCall(wname);
                InputManager.clearKey(keyMap.get(wname));
            }
        }
        if (InputManager.keys[KeyEvent.VK_ESCAPE]){
            MyGUI.closeWindow();
            InputManager.clearKey(KeyEvent.VK_ESCAPE);
        }
        
        if (MyGUI.getWindow("hud").visible() && MyGUI.getWindow("hud").collidepoint(AMouseInput.mx,AMouseInput.my)){
        //Check if the hud is receiving the mouse click
            if (AMouseInput.clicked(AMouseInput.LEFT)){
                MyGUI.mouseClickCall("hud");
                passMouse=false;
            }
        }
        else{
        //If not, loop through all the visible windows on their screen in the appropriate
        //priority and update then if contact with the mouse is made.
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
        
        //These methods pass input data (mouseClicked, keyPressed) to the Game if the
        //GUI does not use them.
		if (passMouse){         //Pass a mouseClick to the Game mouse if GUI did not use it
            MyGUI.unfocus();
            Globals.theMouse.click(AMouseInput.mx, AMouseInput.my);
		}
        for (int i=0; i<KeyEvent.KEY_LAST; i++){
            if (i!=KeyEvent.VK_SHIFT && InputManager.down(i))   //Shift is handled in MyGUI
        		Input.InputManager.keyDown(i);
        }
    }
}