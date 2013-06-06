/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Assets;

import Animation.AnimTrack;
import Animation.SpriteSet;
import GameSource.Assets.TerrainBlocks.Blocks.DirtBlock.Dirt_Block;
import GameSource.Assets.TerrainBlocks.Blocks.GrassBlock.Grass_Block;
import GameSource.Assets.TerrainBlocks.Blocks.otherblock.Other_Block;
import GameSource.Globals;
import GameSource.game.GameMap;
import Spatial.Spatial;
import Testing.MyTestCharacter;
import GameSource.Assets.Portals.Portal;
import GameSource.Assets.Portals.PortalData;
import GameSource.Assets.Spawners.AbstractMobSpawner;
import GameSource.Spawners.CowSpawner;
import GameSource.User.Inventory.ItemData;
import Testing.Stevey;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private static HashMap<String,PortalData> allPortalData;
    private static String[] mapProperties = new String[]{"Blocks","Portals","Spawners","BGSound"};
    private static HashMap<String,Image> allImages;
    private static HashMap<String,ItemData> allItemData;
    
    public static void init(){
        allImages = new HashMap<>();
        loadImages();
        System.out.println("Loaded Images !");
        
        allAnimSets = new HashMap<>();
        loadAnimations();
        System.out.println("Loaded Animations!");
        
        allPortalData = new HashMap<>();
        loadPortals();
        System.out.println("Loaded Portals!");
        
        blockimages = new HashMap<>();
        loadBlocks();
        //if (Globals.Assetdebug)
        System.out.println("Loaded Blocks!");
        
        allmaps = new HashMap<>();
        loadMaps();
        System.out.println("Loaded Maps!");
        
        allItemData = new HashMap<>();
        loadItemData();
        System.out.println("Loaded item data!");
        
    }
    private static void loadImages(){
        try {
            BufferedReader fin = new BufferedReader(new FileReader(DIRECTORY+"ImageData.txt"));
            String nextline;
            String[] tempdat;
            while ((nextline = fin.readLine())!= null){
                tempdat = nextline.split(" ");
                allImages.put(tempdat[0], (new ImageIcon(DIRECTORY+"Images/"+tempdat[1])).getImage());
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
    
    public static Image getBlockImage(String imgName){
        return blockimages.get(imgName);
    }
    
    private static void loadPortals(){
        try {
            BufferedReader fin = new BufferedReader(new FileReader(DIRECTORY+"portaldata.txt"));
            String nextline;
            String[] tempdat;
            while ((nextline = fin.readLine())!=null){
                tempdat = nextline.split(";");
                BufferedReader portal_fin = new BufferedReader(new FileReader(DIRECTORY+"portals/allPortals/"+tempdat[1]));
                String[] portalData = portal_fin.readLine().split(",");
                allPortalData.put(tempdat[0], new PortalData(portalData[0],Float.parseFloat(portalData[1]),Float.parseFloat(portalData[2]),Float.parseFloat(portalData[3])));
            }
            fin.close();
        } catch (IOException e){
            System.out.println("Error loading portals!");
            e.printStackTrace();
            System.exit(0);
        }
    }
    
    public static Portal getPortal(String key,float x,float y,float z){
        PortalData dat = allPortalData.get(key);
        return new Portal(x,y,z,dat.getToMap(),dat.getTx(),dat.getTy(),dat.getTz());
    }
    
    private static void loadMaps(){
        try {
            BufferedReader fin = new BufferedReader(new FileReader(DIRECTORY+"mapdata.txt"));
            String nextline;
            String[] tempdat;
            while ((nextline = fin.readLine())!=null){
                tempdat = nextline.split(";");
                BufferedReader fin_map = new BufferedReader(new FileReader(DIRECTORY+"maps/"+tempdat[1]));
                String[] mapinfo = fin_map.readLine().split(",");
                
                int x = Integer.parseInt(mapinfo[0]);
                int y = Integer.parseInt(mapinfo[1]);
                int mobLim = Integer.parseInt(mapinfo[2]);
                GameMap mymap = new GameMap(tempdat[0],mobLim,x,y,Globals.__CAMX__,Globals.__CAMY__,true);
                for (String s: mapProperties){
                    while (!nextline.equals(s)){
                        nextline = fin_map.readLine();
                    }
                    if (s.equals("Blocks")){
                        nextline = fin_map.readLine();
                        while (!nextline.equals("/"+s)){    
                            tempdat = nextline.split(" ");
                            if (tempdat[0].equals("dirtblock")){
                                mymap.addBackgroundSpatial(new Dirt_Block(Integer.parseInt(tempdat[1]),Integer.parseInt(tempdat[2]),Integer.parseInt(tempdat[3])));
                            } else if (tempdat[0].equals("grassblock")){
                                mymap.addBackgroundSpatial(new Grass_Block(Integer.parseInt(tempdat[1]),Integer.parseInt(tempdat[2]),Integer.parseInt(tempdat[3])));
                            }
                            nextline = fin_map.readLine();
                        }
                    } else if (s.equals("Portals")){
                        nextline = fin_map.readLine();
                        while (!nextline.equals("/"+s)){    
                            tempdat = nextline.split(" ");
                            mymap.addPermanentSpatial(AssetManager.getPortal(tempdat[0], Float.parseFloat(tempdat[1]), Float.parseFloat(tempdat[2]), Float.parseFloat(tempdat[3])));
                            nextline = fin_map.readLine();
                        }
                    } else if (s.equals("Spawners")){
                        nextline = fin_map.readLine();
                        while (!nextline.equals("/"+s)){    
                            tempdat = nextline.split(" ");
                            AbstractMobSpawner spawner = null;
                            if (tempdat[0].equals("CowSpawner")){
                                spawner = new CowSpawner(Float.parseFloat(tempdat[1]), Float.parseFloat(tempdat[2]), Float.parseFloat(tempdat[3]),Integer.parseInt(tempdat[4])/10);
                            }//else if....
                            if (spawner == null){
                                System.out.println("SEVERE: UNABLE TO FIND MOBID "+tempdat[0]);
                                System.exit(0);
                            }
                            mymap.addBackgroundSpatial(spawner);
                            nextline = fin_map.readLine();
                        }
                    } else if (s.equals("BGSound")){
                        nextline = fin_map.readLine();
                        while (!nextline.equals("/"+s)){    
                            mymap.setBGMusic(nextline);
                            nextline = fin_map.readLine();
                        }
                    }
                }
                mymap.addBackgroundSpatial(Globals.theMouse);
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
    public static GameMap getMap(String identifier){
        if (!allmaps.containsKey(identifier)){
            System.out.println("SEVERE: Unable to find map with key "+identifier+"!");
            System.exit(0);
        }
        return allmaps.get(identifier);
    }
    
    public static GameMap[] getAllMaps(){
        return allmaps.values().toArray(new GameMap[0]);
    }
    
    private static void loadAnimations(){
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
        } else if (spat instanceof Stevey){
            return 4;
        }
        System.out.println("SEVERE: Unable to recognise SpatialID!");
        System.exit(0);
        return -1;
    }
    private static void loadItemData(){
        try {
            BufferedReader fin = new BufferedReader(new FileReader(DIRECTORY+"ItemData.txt"));
            String nextline;
            String[] tempdat;
            while ((nextline = fin.readLine())!= null){
                tempdat = nextline.split(" ");
                BufferedReader itemFin = new BufferedReader(new FileReader(DIRECTORY+"Items/"+tempdat[1]));
                String name = itemFin.readLine();
                String itemType = itemFin.readLine();
                String equipType = null;
                ItemData itemdata = new ItemData(name,itemType);
                if (itemType.equals("equip")){
                    equipType = itemFin.readLine();
                    itemdata.equipItemType = equipType;
                }
                String imageKey = itemFin.readLine();
                itemdata.setImage(imageKey);
                String itemline;
                String[] itemStat;
                while ((itemline = itemFin.readLine())!= null){
                    itemStat = itemline.split(" ");
                    itemdata.addStat(itemStat[0], Integer.parseInt(itemStat[1]));
                }
                allItemData.put(tempdat[0], itemdata);
                itemFin.close();
            }
            fin.close();
        } catch (IOException e) {
            System.out.println("Unable to load in ItemData!");
            e.printStackTrace();
            System.exit(0);
        }
    }
    public static ItemData getItemData(String key){
        return allItemData.get(key);
    }
//    public static NPC getNpc(){
//        
//    }
//    public static void main(String[] args){
//        AssetManager.init();
////        System.out.println(AssetManager.getBlockImage("dirtblock"));
//    }
}
