/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Assets;

import GameSource.Assets.TerrainBlocks.Blocks.DirtBlock.Dirt_Block;
import GameSource.Globals;
import GameSource.game.GameMap;
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
    private static HashMap<String,GameMap> allmaps;
    
    public static void init(){
        blockimages = new HashMap<>();
        blockimages.put(null, null);
        loadBlocks();
        allmaps = new HashMap<>();
        loadMaps();
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
    public static void loadMaps(){
        try {
            BufferedReader fin = new BufferedReader(new FileReader(DIRECTORY+"mapdata.txt"));
            String nextline;
            String[] tempdat;
            while ((nextline = fin.readLine())!=null){
                tempdat = nextline.split(";");
                GameMap mymap = new GameMap(tempdat[0],0.1,1600,1200,Globals.__CAMX__,Globals.__CAMY__,true);
                BufferedReader fin_map = new BufferedReader(new FileReader(DIRECTORY+"maps/"+tempdat[1]));
                while ((nextline = fin_map.readLine()) != null){
                    tempdat = nextline.split(" ");
                    if (tempdat[0].equals("dirtblock")){
                        mymap.addSpatial(new Dirt_Block(Integer.parseInt(tempdat[1]),Integer.parseInt(tempdat[2]),Integer.parseInt(tempdat[3])));
                    }
                }
                allmaps.put(mymap.getName(), mymap);
            }
        } catch (IOException e){
            System.out.println("Failed to load Maps.");
            e.printStackTrace();
            System.exit(0);
        }
    }
    public static GameMap getMap(String identifier){
        if (!allmaps.containsKey(identifier)){
            System.out.println("SEVERE: Unable to find map with key "+identifier+"!");
            System.exit(0);
        }
        return allmaps.get(identifier);
    }
//    public static void main(String[] args){
//        AssetManager.init();
//        System.out.println(AssetManager.getBlockImage("dirtblock"));
//    }
}
