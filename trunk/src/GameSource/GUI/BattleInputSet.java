package GameSource.GUI;/**/

public class BattleInputSet extends AbstractInputSet{
	//Inventory: u, Chat: o, Shop: disabled
	public BattleInputSet(){
		name="normal";
	}
	public boolean invent(boolean [] keys){
		return keys[85];
	}
	public boolean chat(boolean [] keys){
		return keys[79];
	}
	public boolean shop(boolean [] keys){
		return false;
	}
	public void update(){}
}