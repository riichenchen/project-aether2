package ArtificialIntelligence;

import java.awt.Rectangle;

public class CharGameMap {
	
	char [][] charMap;
	
	public CharGameMap(Rectangle [] rects, int max_height, int max_width) {
		charMap = new char[max_height][max_width];
		for (int i = 0; i < max_height; i++)
			for (int j = 0; j < max_width; j++)
					charMap[i][j] = 0;
		for (int i = 0; i < rects.length; i++) {
				int curHeight = (int)rects[i].getHeight();
				int curWidth = (int)rects[i].getWidth();
				int curX = (int)rects[i].getX();
				int curY = (int)rects[i].getY();
				for (int j = 0; j < curHeight; j++)
					for (int k = 0; k < curWidth; k++)
						charMap[curX + j][curY + k] = 1;
		}
	}
	
	public char [][] getCharMap() {
		return charMap;
	}
	
	/*public static void main(String [] args) {
		Rectangle [] myrects = new Rectangle[1];
		myrects[0] = new Rectangle(2, 3, 5, 4);
		CharGameMap mycgm = new CharGameMap(myrects, 15, 15);
		char [][] tmp = mycgm.getCharMap();
		int x = tmp.length;
		int y = tmp[0].length;
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < x; j++)
				System.out.printf("%d ", (int)tmp[i][j]);
			System.out.printf("\n");
		}
	}*/
}