/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Assets;

import Animation.AnimTrack;
import Animation.SpriteSet;
import GameSource.Assets.Portals.Portal;
import GameSource.Assets.Portals.PortalData;
import GameSource.Assets.Shop.ShopData;
import GameSource.Assets.Spawners.AbstractMobSpawner;
import GameSource.Assets.TerrainBlocks.Blocks.DirtBlock.Dirt_Block;
import GameSource.Assets.TerrainBlocks.Blocks.GrassBlock.Grass_Block;
import GameSource.Globals;
import GameSource.Script.FrameCase;
import GameSource.Script.FrameData;
import GameSource.Script.ScriptData;
import GameSource.Script.ScriptFrame;
import GameSource.Skills.ActiveSkillData;
import GameSource.Skills.SkillData;
import GameSource.Spawners.CowSpawner;
import GameSource.User.Inventory.ItemData;
import GameSource.game.GameMap;
import Spatial.NPC;
import Spatial.Spatial;
import Testing.MyTestCharacter;
import Testing.PlayerSpatial;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.ImageIcon;

/**
 * The AssetManager handles the grabbing of all resources ever used in
 * the game itself. The static class needs to be initialized before
 * it can be used.
 * @author Shiyang
 */
public class AssetManager {
    private static HashMap<String,Image> blockimages; // map block images
    private static final String DIRECTORY = "src/GameSource/Assets/";//directory of assets
    private static HashMap<String,GameMap> allmaps; // maps
    private static HashMap<String,SpriteSet> allAnimSets; //all spritesets
    private static HashMap<String,PortalData> allPortalData; //all portal dat
    private static String[] mapProperties = new String[]{"Blocks","Portals","Spawners","BGSound","Npc"}; // tags that load map uses
    private static HashMap<String,Image> allImages;
    private static HashMap<String,ItemData> allItemData;
    private static HashMap<String, SkillData> allSkillData;
    private static HashMap<String, ScriptData> allScriptData;
    private static HashMap<String, ShopData> allShopData;
    
    /*The init method loads up everything. Literally*/
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
        System.out.println("Loaded Blocks!");
        
        allmaps = new HashMap<>();
        loadMaps();
        System.out.println("Loaded Maps!");
        
        allItemData = new HashMap<>();
        loadItemData();
        System.out.println("Loaded item data!");
        
        allSkillData = new HashMap<>();
        loadSkillData();
        System.out.println("Loaded skill data!");
        
        allScriptData = new HashMap<>();
        loadScriptData();
        System.out.println("Loaded script data!");
        
        allShopData = new HashMap<>();
        loadShopData();
        System.out.println("Loaded shop data!");
    }
    /*This method loads all information and data regarding shops*/
    private static void loadShopData(){
        try {
            BufferedReader fin = new BufferedReader(new FileReader(DIRECTORY+"shopData.txt"));
            String nextline;
            String[] tempdat;
            while ((nextline = fin.readLine())!= null){ // read each line, storing info as we go into a shopdata object
                tempdat = nextline.split(" ");
                BufferedReader shopFin = new BufferedReader(new FileReader(DIRECTORY+"Shop/"+tempdat[1]));
                String shopNextLine = shopFin.readLine();
                String[] shopdat;
                ShopData dat = new ShopData(shopNextLine); //Line 1: npc name
                while ((shopNextLine = shopFin.readLine())!= null){
                    shopdat = shopNextLine.split(" ");
                    dat.addItem(shopdat[0], Integer.parseInt(shopdat[1]));
                }
                allShopData.put(tempdat[0],dat); //map the given key to this object
            }
        } catch (IOException e) {
            System.out.println("Failed to load shopData!");
            e.printStackTrace();
            System.exit(0);
        }
        
    }
    /*This method loads all information and data regarding npc scripts, including
     the actual interpretation of the script itself into data*/
    private static void loadScriptData(){
        try {
            BufferedReader fin = new BufferedReader(new FileReader(DIRECTORY+"scriptData.txt"));
            String nextline;
            String[] tempdata;
            while ((nextline = fin.readLine())!= null){
                tempdata = nextline.split(" ");
                BufferedReader scriptFin = new BufferedReader(new FileReader(DIRECTORY+"script/"+tempdata[1]));
                int nFrames = Integer.parseInt(scriptFin.readLine());
                //Load in all frames
                String scriptFinNext;
                ScriptData newDat = new ScriptData();
                for (int i = 0; i < nFrames; i++){
                    while (!(scriptFinNext = scriptFin.readLine()).equals("Frame")); // skip to next Frame tag
                    String[] allButtons = scriptFin.readLine().split(" ");
                    ScriptFrame newFrame = new ScriptFrame(allButtons);//create a frame with the provided buttons array
                    while (!(scriptFinNext = scriptFin.readLine()).equals("Text")); // skip to next Text tag
                    
                    String text = "";
                    while (!(scriptFinNext = scriptFin.readLine()).equals("/Text")){
                        text+= scriptFinNext+"\n";
                    }
                    text = text.substring(0,text.length()-1); // trim last \n
                    newFrame.setText(text);
                    
                    while (!(scriptFinNext = scriptFin.readLine()).equals("Outcome")); // skip to next Outcome tag
                    
                    while (!(scriptFinNext = scriptFin.readLine()).equals("/Outcome")){
                        String[] temp = scriptFinNext.split(" ");
                        FrameData outcomeDat = new FrameData(temp[0],temp[1].split(","));
                        newFrame.addOutcome(outcomeDat);
                    }
                    while (!(scriptFinNext = scriptFin.readLine()).equals("/Frame"));// skip to endFrame tag
                    newDat.addFrame(newFrame);
                }
                
                //Load in all outcomes
                int nOutcomes = Integer.parseInt(scriptFin.readLine());
                for (int i = 0; i < nOutcomes; i++){
                    while (!(scriptFinNext = scriptFin.readLine()).equals("Case"));// skip to case tag
                    while (!(scriptFinNext = scriptFin.readLine()).equals("Require"));// skip to require tag
                    FrameCase newCase = new FrameCase(); // create a new case object
                    while (!(scriptFinNext = scriptFin.readLine()).equals("/Require")){
                        String[] temp = scriptFinNext.split(" ");
                        newCase.addData(temp[0], temp[1].split(",")); // add in some requirements
                    }
                    newCase.setToFrame(Integer.parseInt(scriptFin.readLine())); // what frame does this case lead to?
                    newDat.addCase(newCase);
                    while (!(scriptFinNext = scriptFin.readLine()).equals("/Case"));// skip to end case tag
                }
                newDat.setFinalCase(Integer.parseInt(scriptFin.readLine())); // if nothing matches go to this case
                allScriptData.put(tempdata[0], newDat);
            }
        } catch (IOException e){
            System.out.println("Error loading scripts!");
            e.printStackTrace();
            System.exit(0);
        }
    }
    /*This method loads all information and data regarding skills*/
    private static void loadSkillData(){
        try {
            BufferedReader fin = new BufferedReader(new FileReader(DIRECTORY+"skillData.txt"));
            String nextline;
            String[] tempdata;
            while ((nextline = fin.readLine())!= null){
                tempdata = nextline.split(" ");
                BufferedReader skillFin = new BufferedReader(new FileReader(DIRECTORY+"SkillData/"+tempdata[1]));
                String skillName = skillFin.readLine();
                String skillDescrip = skillFin.readLine();
                String skillType = skillFin.readLine();
                if (skillType.equals("active")){
                    String type = skillFin.readLine();
                    ActiveSkillData dat = new ActiveSkillData(skillName,type);
                    dat.setDescription(skillDescrip);
                    int n = Integer.parseInt(skillFin.readLine());
                    for (int i = 1; i <= n; i++){
                        //Set Range
                        dat.setRange(i, Integer.parseInt(skillFin.readLine()));
                    }
                    for (int i = 1; i <= n; i++){
                        //Set mpCost
                        dat.setMpCost(i, Integer.parseInt(skillFin.readLine()));
                    }
                    for (int i = 1; i <= n; i++){
                        //set damage modifier
                        dat.setDamagePercentile(i, Integer.parseInt(skillFin.readLine())/(double)100);
                    }
                    for (int i = 1; i <= n; i++){
                        //set cast time
                        dat.setCastTime(i, Integer.parseInt(skillFin.readLine())/10);
                    }
                    allSkillData.put(tempdata[0],dat);
                }
                
            }
        } catch (IOException e) {
            System.out.println("Failed to load skills!");
            e.printStackTrace();
            System.exit(0);
        }
    }
    /*This method loads all images and maps them to a provided key*/
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
    /*This method loads in all block image information.*/
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
    /*This method loads all information and data regarding portals.*/
    private static void loadPortals(){
        try {
            BufferedReader fin = new BufferedReader(new FileReader(DIRECTORY+"portaldata.txt"));
            String nextline;
            String[] tempdat;
            while ((nextline = fin.readLine())!=null){
                tempdat = nextline.split(";");
                BufferedReader portal_fin = new BufferedReader(new FileReader(DIRECTORY+"portals/allPortals/"+tempdat[1]));
                String[] portalData = portal_fin.readLine().split(",");
                //each portal has a destination and location on the destination map
                allPortalData.put(tempdat[0], new PortalData(portalData[0],Float.parseFloat(portalData[1]),Float.parseFloat(portalData[2]),Float.parseFloat(portalData[3])));
            }
            fin.close();
        } catch (IOException e){
            System.out.println("Error loading portals!");
            e.printStackTrace();
            System.exit(0);
        }
    }
    /*Perhaps the longest method, load maps loads in all information regarding 
     * a map and stores it into an actual GameMap object.*/
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
                    //Load all blocks
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
                        //Load all portals
                    } else if (s.equals("Portals")){
                        nextline = fin_map.readLine();
                        while (!nextline.equals("/"+s)){    
                            tempdat = nextline.split(" ");
                            mymap.addPermanentSpatial(AssetManager.getPortal(tempdat[0], Float.parseFloat(tempdat[1]), Float.parseFloat(tempdat[2]), Float.parseFloat(tempdat[3])));
                            nextline = fin_map.readLine();
                        }
                        //Load all mob spawners
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
                        //Load in the background sound key
                    } else if (s.equals("BGSound")){
                        nextline = fin_map.readLine();
                        while (!nextline.equals("/"+s)){    
                            mymap.setBGMusic(nextline);
                            nextline = fin_map.readLine();
                        }
                    } else if (s.equals("Npc")){
                        nextline = fin_map.readLine();
                        while (!nextline.equals("/"+s)){
                            String[] npcTemp = nextline.split(" ");
                            NPC newNpc = new NPC(Integer.parseInt(npcTemp[0]),Integer.parseInt(npcTemp[2]),Integer.parseInt(npcTemp[2]),npcTemp[3]);
                            mymap.addPermanentSpatial(newNpc);
                        }
                    }
                }
                //Register the global mouse with each map
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
    //All spatials that are communicated over the net must be registered. Spatial to type converts 
    //an object to an integer representation of it.
    public static int SpatialToType(Spatial spat){
        if (spat instanceof MyTestCharacter){
            return 1;
        } else if (spat instanceof Dirt_Block){
            return 2;
        } else if (spat instanceof Portal){
            return 3;
        } else if (spat instanceof PlayerSpatial){
            return 4;
        }
        System.out.println("SEVERE: Unable to recognise SpatialID!");
        System.exit(0);
        return -1;
    }
    //All itemdata is loaded in through this method, which reads from itemdata and loads
    //in all specified items to a given key
    private static void loadItemData(){
        try {
            BufferedReader fin = new BufferedReader(new FileReader(DIRECTORY+"ItemData.txt"));
            String nextline;
            String[] tempdat;
            while ((nextline = fin.readLine())!= null){
                tempdat = nextline.split(" ");
                BufferedReader itemFin = new BufferedReader(new FileReader(DIRECTORY+"Items/"+tempdat[1]));
                String name = itemFin.readLine();
                String description = itemFin.readLine();
                int sellPrice = Integer.parseInt(itemFin.readLine());
                String itemType = itemFin.readLine();
                String equipType = null;
                ItemData itemdata = new ItemData(name,itemType);
                itemdata.setItemDescription(description);
                itemdata.setSellPrice(sellPrice);
                if (itemType.equals("equip")){ // if it's an equip item, we must add an equipment type
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
    
    /*The "get information section"*/
    public static ItemData getItemData(String key){
        return allItemData.get(key);
    }
    public static SpriteSet getSpriteSet(String key){
        return allAnimSets.get(key);
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
    public static Portal getPortal(String key,float x,float y,float z){
        PortalData dat = allPortalData.get(key);
        return new Portal(x,y,z,dat.getToMap(),dat.getTx(),dat.getTy(),dat.getTz());
    }
    
    public static Image getBlockImage(String imgName){
        return blockimages.get(imgName);
    }
    public static Image getImage(String key){
        return allImages.get(key);
    }
    public static ScriptData getScriptData(String key){
        return allScriptData.get(key);
    }
    public static SkillData getSkillData(String key){
        return allSkillData.get(key);
    }
    public static ShopData getShopData(String key){
        return allShopData.get(key);
    }
//    public static NPC getNpc(){
//        
//    }
//    public static void main(String[] args){
//        AssetManager.init();
////        System.out.println(AssetManager.getBlockImage("dirtblock"));
//    }
}
