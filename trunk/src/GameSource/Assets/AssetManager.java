/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Assets;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.ImageIcon;

/**
 *
 * @author Shiyang
 */
public class AssetManager {
    private static HashMap<String,Image> blockimages;
    private static final String DIRECTORY = "src/GameSource/Assets/";
    public static void init(){
        blockimages = new HashMap<>();
        blockimages.put(null, null);
        loadBlocks();
        //load in images here
    }
    private static void loadBlocks(){
        try {
            BufferedReader fin = new BufferedReader(new FileReader(DIRECTORY+"blockdata.txt"));
            String nextline;
            String[] tempdat;
            while ((nextline = fin.readLine())!=null){
                tempdat = nextline.split(";");
                /*Conventions: name of folder = name of block.
                 * Note: that the image will be inside of such a folder
                 */
                blockimages.put(tempdat[0].trim(), new ImageIcon(DIRECTORY+"TerrainBlocks/Blocks/"+tempdat[0]+"/"+tempdat[1]).getImage());
            }
        } catch (IOException e){
            System.out.println("Failed to load blocks.");
            e.printStackTrace();
            System.exit(0);
        }
    }
    public static Image getBlockImage(String imgName){
        return blockimages.get(imgName);
    }
//    public static void main(String[] args){
//        AssetManager.init();
//        System.out.println(AssetManager.getBlockImage("dirtblock"));
//    }
}
