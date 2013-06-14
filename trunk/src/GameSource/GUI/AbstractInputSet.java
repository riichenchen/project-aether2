package GameSource.GUI;
import java.util.Map;
/* AbstractInputSet.java			@Chen~
 * Input sets process received Mouse and keyboard input information
 * and respond as necessary. They each contain a main update loop that
 * checks to map each input to its appropriate result
 */
public abstract class AbstractInputSet{
	protected String name;
	protected Map<String,Integer> keyMap;
		//keyMap is a HashMap that maps a window name to its shortcut
		//key code for opening/closing.
	protected boolean mouseUsed;

	public int hashCode(){
		return name.hashCode();
	}
	public void useMouse(){
		mouseUsed=true;
	}
	
	public abstract void update();
}