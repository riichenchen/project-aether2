package GameSource.GUI;

public class KeyCharMap{
	public static boolean isTypeable(int keyCode, int textType){
		if (44<=keyCode && keyCode<=93 && keyCode!=58)
			return true;
		if (keyCode==32)
			return true;
		if (keyCode==192)
			return true;
		if (keyCode==222)
			return true;
		if (keyCode==10 && textType==1)	//replace 1 with GUI.TEXT_BOX
			return true;
		return false;
	}
	public static int getChar(int key, boolean shift){
		if (key==32 || key==10)
			return key;
		if (shift==false){
			if ((44<=key && key<=93)){
				if (65<=key && key<=90)
					return key+32;
				return key;
			}
			if (key==192)		// ` character
				return 96;		
			if (key==222)		// ' character
				return 39;
		}
		if (shift){
			if (65<=key && key<=90)
				return key;
			int [] mapNums={60,95,62,63,41,33,64,35,36,37,94,38,42,40};
			if (44<=key &&key<=57)
				return mapNums[key-44];
			if (91<=key &&key<=93)
				return key+33;
			if (key==59)
				return 58;
			if (key==61)
				return 43;
			if (key==192)
				return 126;
			if (key==222)
				return 34;
			
		}
		
		return -1;
	}
	
}