/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.User;

import Database.SaveSkillData;
import GameSource.Assets.MobData.AbstractMob;
import GameSource.Assets.Portals.Portal;
import GameSource.Game.ClientWorldHandler;
import GameSource.User.Inventory.InventoryItem;
import Sound.SoundManager;
import Testing.PlayerSpatial;
import Testing.SteveyKeyListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Shiyang
 * The characterhandler class is a static class that handles any logic that has
 * to do with the player character. All items colliding with it, all portals colliding
 * with it, it's controls, as well as its name and stats.
 */
public class CharacterHandler {
    private static HashMap<String,Integer> stats;
    private static LinkedList<InventoryItem> collideItems;
    private static Portal currentPortal = null;
    private static String charName;
    private static HashMap<String, Integer> skillLevels;
    private static PlayerSpatial player;
    private static SteveyKeyListener userControl = null;
    private static ClientWorldHandler world;
    
    public static void init(){
        stats = new HashMap<>();
        collideItems = new LinkedList<>();
        skillLevels = new HashMap<>();
        //insert some keys by default so null doesn't appear
        stats.put("hp",0);
        stats.put("mp",0);
        stats.put("maxhp",0);
        stats.put("maxmp",0);
        stats.put("attack",0);
        stats.put("defense",0);
        stats.put("statPoints",0);
        stats.put("skillPoints",0);
        stats.put("exp",0);
        stats.put("money", 0);
        stats.put("level",0);
        stats.put("job",0);
    }
    
    public static void bindWorld(ClientWorldHandler nworld){
        world = nworld;
    }
    
    public static ClientWorldHandler getWorld(){
        return world;
    }
    //Add this spatial to the player
    public static void bindPlayer(PlayerSpatial spat){
        player = spat;
    }
    public static PlayerSpatial getPlayer(){
        return player;
    }
    //set a skill's level or add to it if it exists
    public static void addSkillLevel(String skillName,int level){
        if (skillLevels.get(skillName)!=null)
            skillLevels.put(skillName,skillLevels.get(skillName)+level);
        else
            skillLevels.put(skillName, level);
    }
    
    public static int getSkillLevel(String key){
        return skillLevels.get(key);
    }
    
    //The add stat method takes a few stats the cannot be negative into consideration
    //if it's possible for these stats to decrease and also handles leveling
    public static void addStat(String stat,int val){
        if (stat.equals("hp")){
            stats.put("hp", Math.max(0,Math.min(stats.get("maxhp"), stats.get("hp")+val)));
        } else if (stat.equals("mp")) {
            stats.put("mp", Math.max(0,Math.min(stats.get("maxmp"), stats.get("mp")+val)));
        } else if (stat.equals("exp")){
            stats.put("exp",stats.get("exp")+val);
            int required = LevelManager.requiredExp(stats.get("level"));
            while (stats.get("exp") > required){
                stats.put("exp", stats.get("exp")-required);
                stats.put("level",stats.get("level")+1);
            }
        } else {
            stats.put(stat, stats.get(stat)+val);
        }
    }
    
    //some standard add set and clear methods
    public static int getStat(String stat){
        return stats.get(stat);
    }
    
    public static void addCollideItem(InventoryItem i){
        collideItems.add(i);
    }
    
    public static void clearCollideItems(){
        collideItems.clear();
    }
    //If there's an item that's colliding with the character, add the first
    //item parsed into the inventory
    public static void pickUpItem(){
        if (collideItems.peek() != null){
            SoundManager.getChannel("UI").addTrack("PickUpSound");
            InventoryItem myItem = collideItems.poll();
            InventoryHandler.addItem(myItem);
            myItem.getMap().removeSpatial(myItem); // remove this item from the map
        }
    }
    public static void setCurrentPortal(Portal port){
        currentPortal = port;
    }
    public static Portal getCurrentPortal(){
        return currentPortal;
    }
    
    //This method is used only by the clientworldhandler when loading in player
    //statistics.
    public static void addAllStats(String[] keys,int[] newStats){
        for (int i = 0; i < newStats.length; i++){
            addStat(keys[i],newStats[i]);
        }
    }
    //Set and get name methods
    public static void setName(String name){
        charName = name;
    }
    public static String getName(){
        return charName;
    }
    
    //the disableusercontrols method temperarly renders the player immobile
    public static void disableUserControls(){
        userControl = (SteveyKeyListener)player.getControl(SteveyKeyListener.class);
        player.removeControl(userControl);
    }
    //return the control to the player
    public static void enableUserControls(){
        player.addControl(userControl);
    }
    
    //Exports all skill data into a neat and tidy array to be transferred back
    //to the server
    public static SaveSkillData[] getSaveSkillData(){
        LinkedList<SaveSkillData> export = new LinkedList<>();
        for (String skill: skillLevels.keySet().toArray(new String[0])){
            export.add(new SaveSkillData(skill,skillLevels.get(skill)));
        }
        return export.toArray(new SaveSkillData[0]);
    }
    //Adds all skills provided by an array into the player's skill data
    public static void addAllSkills(SaveSkillData[] Import){
        for (SaveSkillData dat: Import){
            addSkillLevel(dat.getSkillName(),dat.getLevel());
        }
    }
//    TODO:
//    public static int calculateHurtDamage(AbstractMob mob){
//        
//    }
//    
    
    //Simple damage calculation. A range is made using a random double between 0
    // and 1 and then truncating this range to 0.8 to 1.2
    // This percentage is then multiplied by a modifier percentage as well as
    // the player's attack
    public static int calculateDamage(double modifier,AbstractMob mob){
        return (int)(((new Random()).nextDouble()/1.0*0.4+0.8)*getStat("attack")*modifier);
    }
    
}
