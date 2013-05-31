/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Assets;

import Animation.AnimTrack;
import Animation.SpriteSet;
import GameSource.Assets.TerrainBlocks.Blocks.DirtBlock.Dirt_Block;
import GameSource.Assets.TerrainBlocks.Blocks.otherblock.Other_Block;
import GameSource.Globals;
import GameSource.game.GameMap;
import Spatial.Spatial;
import Testing.MyTestCharacter;
import Testing.Portal;
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
    private static HashMap<String,SpriteSet> allAnimSets;
    
    public static void init(){
        blockimages = new HashMap<>();
        blockimages.put(null, null);
        loadBlocks();
        //if (Globals.Assetdebug)
        System.out.println("Loaded Blocks!");
        allmaps = new HashMap<>();
        loadMaps();
        System.out.println("Loaded Maps!");
        allAnimSets = new HashMap<>();
        loadAnimations();
        System.out.println("Loaded Animations!");
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
            fin.close();
        } catch (IOException e){
            System.out.println("Failed to load blocks.");
            e.printStackTrace();
            System.exit(0);
        }
    }
    public synchronized static Image getBlockImage(String imgName){
        return blockimages.get(imgName);
    }
    private static void loadMaps(){
        try {
            BufferedReader fin = new BufferedReader(new FileReader(DIRECTORY+"mapdata.txt"));
            String nextline;
            String[] tempdat;
            while ((nextline = fin.readLine())!=null){
                tempdat = nextline.split(";");
                GameMap mymap = new GameMap(tempdat[0],0.1,1600,4000,Globals.__CAMX__,Globals.__CAMY__,true);
                BufferedReader fin_map = new BufferedReader(new FileReader(DIRECTORY+"maps/"+tempdat[1]));
                while ((nextline = fin_map.readLine()) != null){
                    tempdat = nextline.split(" ");
                    if (tempdat[0].equals("dirtblock")){
                        mymap.addBackgroundSpatial(new Dirt_Block(Integer.parseInt(tempdat[1]),Integer.parseInt(tempdat[2]),Integer.parseInt(tempdat[3])));
                    }
                }
                allmaps.put(mymap.getName(), mymap);
                fin_map.close();
            }
            fin.close();
        } catch (IOException e){
            System.out.println("Failed to load Maps.");
            e.printStackTrace();
            System.exit(0);
        }
    }
    public synchronized static GameMap getMap(String identifier){
        if (!allmaps.containsKey(identifier)){
            System.out.println("SEVERE: Unable to find map with key "+identifier+"!");
            System.exit(0);
        }
        return allmaps.get(identifier);
    }
    public synchronized static GameMap[] getAllMaps(){
        return allmaps.values().toArray(new GameMap[0]);
    }
    public static void loadAnimations(){
        try {
            BufferedReader fin = new BufferedReader(new FileReader(DIRECTORY+"Animations.txt"));
            String nextline;
            String[] tempdat;
            while ((nextline = fin.readLine())!=null){
                String curSpriteName = nextline;
                SpriteSet currentSet = new SpriteSet();
                nextline = fin.readLine();
                while (!nextline.equals("/"+curSpriteName)){
                    BufferedReader animTrack = new BufferedReader(new FileReader(DIRECTORY+"Animations/"+curSpriteName+"/"+nextline+"/info.txt"));
                    int numImages = Integer.parseInt(animTrack.readLine());
                    tempdat = animTrack.readLine().split(",");
                    int[] offsets = new int[]{Integer.parseInt(tempdat[0]),Integer.parseInt(tempdat[1])};
                    //Create the animation track and init with the above info
                    AnimTrack myTrack = new AnimTrack(numImages,offsets[0],offsets[1]);
                    tempdat = animTrack.readLine().split(" ");
                    for (int i = 0; i < numImages;i++){
                        myTrack.addImage((new ImageIcon(DIRECTORY+"Animations/"+curSpriteName+"/"+nextline+"/"+i+".png")).getImage());
                    }
                    for (String s: tempdat){
                        String[] frame = s.split(",");
                        myTrack.addFrame(Integer.parseInt(frame[0]), Integer.parseInt(frame[1]));
                    }
                    currentSet.addTrack(nextline, myTrack);
                    animTrack.close();
                    nextline = fin.readLine();
                }
                allAnimSets.put(curSpriteName,currentSet);
            }
            fin.close();
        } catch (IOException e){
            System.out.println("Failed to load Animations.");
            e.printStackTrace();
            System.exit(0);
        }
    }
    public static SpriteSet getSpriteSet(String key){
        return allAnimSets.get(key);
    }
    public static int SpatialToType(Spatial spat){
        if (spat instanceof Other_Block){
            return 0;
        } else if (spat instanceof MyTestCharacter){
            return 1;
        } else if (spat instanceof Dirt_Block){
            return 2;
        } else if (spat instanceof Portal){
            return 3;
        }
        System.out.println("SEVERE: Unable to recognise SpatialID!");
        System.exit(0);
        return -1;
    }

//    public static void main(String[] args){
//        AssetManager.init();
////        System.out.println(AssetManager.getBlockImage("dirtblock"));
//    }
}
