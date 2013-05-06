import java.io.*;

public class GameMap {
	
	private ImageIcon mapImage;
	private double mobDensity; 
		
	public GameMap(String mapName, double _mobDensity) {
		mapImage = new ImageIcon(mapName);
		mobDensity = _mobDensity;
	}
	
	public double getMobDensity() {
		return mobDensity;
	}
}