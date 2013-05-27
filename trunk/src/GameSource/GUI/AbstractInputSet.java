package GameSource.GUI;
import java.util.Map;
public abstract class AbstractInputSet{
	protected String name;
	protected AGUI myGUI;
	protected Map<String,Integer> keyMap;
	protected boolean mouseUsed;

	/*Erm... yea... abstract implies that it's not specifically
	 *designed for any purpose and should work for like just about
	 *anything... This implies that it has to have an invent,chat, and
	 *shop keymapping... T.T.T.T.T.T.T.T*/
	 /*
	public abstract boolean invent(boolean [] keys);
	public abstract boolean chat(boolean [] keys);
	public abstract boolean shop(boolean [] keys);
	*/
	public int hashCode(){
		return name.hashCode();
	}
	public void useMouse(){
		mouseUsed=true;
	}
	
	public abstract void update();
}