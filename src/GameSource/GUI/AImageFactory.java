package GameSource.GUI;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.ImageIcon;

/*AImageFactory.java            @Chen~
 * This class loads all images into a HashMap and allows other classes to
 * retrieve them by key.
 */

public class AImageFactory {
    private static HashMap<String,Image> allImages;
    private static final String DIRECTORY = "src/GameSource/Assets/";
	
    public static void init(){
        allImages = new HashMap<>();
        loadImages();
        System.out.println("Loaded GUI Images !");
    }
	
    private static void loadImages(){
        try {
            BufferedReader fin = new BufferedReader(new FileReader(DIRECTORY+"GUIImageData.txt"));
            String nextline;
            String[] tempdat;
            while ((nextline = fin.readLine())!= null){
                tempdat = nextline.split(" ");
                allImages.put(tempdat[0], (new ImageIcon(DIRECTORY+"GUI/"+tempdat[1])).getImage());
            }
            fin.close();
        } catch (IOException e){
            System.out.println("Error loading images!");
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static Image getImage(String key){
        return allImages.get(key);
    }
}
