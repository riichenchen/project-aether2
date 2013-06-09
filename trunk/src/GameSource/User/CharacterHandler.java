/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.User;

import Database.SaveSkillData;
import GameSource.Assets.MobData.AbstractMob;
import GameSource.Assets.Portals.Portal;
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
 */
public class CharacterHandler {
    private static HashMap<String,Integer> stats;
    private static InventoryHandler invenHandle;
    private static EquipHandler equipHandle;
    private static LinkedList<InventoryItem> collideItems;
    private static Portal currentPortal = null;
    private static String charName;
    private static HashMap<String, Integer> skillLevels;
    private static PlayerSpatial player;
    private static SteveyKeyListener userControl = null;
    
    public static void init(){
        //Note these are all placeholders to put a key in
        stats = new HashMap<>();
        collideItems = new LinkedList<>();
        skillLevels = new HashMap<>();
        
        stats.put("hp",0);
        stats.put("mp",0);
        stats.put("maxhp",0);
        stats.put("maxmp",0);
        stats.put("attack",0);
        stats.put("defense",0);
        stats.put("exp",0);
        stats.put("money", 0);
        stats.put("level",0);
    }
    public static void bindPlayer(PlayerSpatial spat){
        player = spat;
    }
    public static PlayerSpatial getPlayer(){
        return player;
    }
    public static void addSkillLevel(String skillName,int level){
        skillLevels.put(skillName, level);
    }
    
    public static int getSkillLevel(String key){
        return skillLevels.get(key);
    }
    
    public static void addStat(String stat,int val){
        if (stat.equals("hp")){
            stats.put("hp", Math.min(stats.get("maxhp"), stats.get("hp")+val));
        } else if (stat.equals("mp")) {
            stats.put("mp", Math.min(stats.get("maxmp"), stats.get("mp")+val));
        } else if (stat.equals("exp")){
            LevelManager.addExp(val);
        } else {
            stats.put(stat, stats.get(stat)+val);
        }
    }
    
    public static int getStat(String stat){
        return stats.get(stat);
    }
    
    public static void addCollideItem(InventoryItem i){
        collideItems.add(i);
    }
    
    public static void clearCollideItems(){
        collideItems.clear();
    }
    public static void pickUpItem(){
        if (collideItems.peek() != null){
            SoundManager.getChannel("UI").addTrack("PickUpSound");
            InventoryItem myItem = collideItems.poll();
            InventoryHandler.addItem(myItem);
            myItem.getMap().removeSpatial(myItem);
        }
    }
    public static void setCurrentPortal(Portal port){
        currentPortal = port;
    }
    public static Portal getCurrentPortal(){
        return currentPortal;
    }
    
    public static void addAllStats(String[] keys,int[] newStats){
        for (int i = 0; i < newStats.length; i++){
            addStat(keys[i],newStats[i]);
        }
    }
    public static void setName(String name){
        charName = name;
    }
    public static String getName(){
        return charName;
    }
    public static void disableUserControls(){
        userControl = (SteveyKeyListener)player.getControl(SteveyKeyListener.class);
        player.removeControl(userControl);
    }
    public static void enableUserControls(){
        player.addControl(userControl);
    }
    public static SaveSkillData[] getSaveSkillData(){
        LinkedList<SaveSkillData> export = new LinkedList<>();
        for (String skill: skillLevels.keySet().toArray(new String[0])){
            export.add(new SaveSkillData(skill,skillLevels.get(skill)));
        }
        return export.toArray(new SaveSkillData[0]);
    }
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
    public static int calculateDamage(double modifier,AbstractMob mob){
        return (int)(((new Random()).nextDouble()/1.0*0.4+0.8)*getStat("attack")*modifier);
    }
    
}
