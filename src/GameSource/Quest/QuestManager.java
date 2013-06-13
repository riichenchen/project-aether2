/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Quest;

import GameSource.Assets.AssetManager;
import GameSource.Assets.MobData.AbstractMob;
import GameSource.Mob.BillysCow;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author Shiyang
 * The Quest Manager is a static class that handles all quest parsing and quest
 * handling through mobs. It holds a hashmap of all quests as well as a 
 * HashMap of an array of requirements that require a similar mob. This allows
 * for O(1) add time to all quests that count a mob when it's killed.
 */
public class QuestManager {
    private static HashMap<String,LinkedList<QuestRequirement>> allQuestRequirementData; //used for an O(1) "status" update
    private static HashMap<String, QuestData> allQuests;
    
    public static void init(){
        allQuestRequirementData = new HashMap<>();
        allQuests = new HashMap<>();
    }
    //Returns true if the quest is complete
    public static boolean checkQuest(String id){
        return allQuests.get(id).isComplete();
    }
    //Returns the quest's status
    public static int getQuestStatus(String id){
        return allQuests.get(id).getStatus();
    }
    
    public static void setQuestStatus(String id,int newStatus){
        allQuests.get(id).setStatus(newStatus);
    }
    //Takes the mobs id and adds 1 to all requirements that need that mob's id
    public static void addMobKill(AbstractMob monster){
        if (allQuestRequirementData.containsKey(monster.getName())){
            QuestRequirement[] questReq = allQuestRequirementData.get(monster.getName()).toArray(new QuestRequirement[0]);
            for (QuestRequirement req: questReq){
                if (getQuestStatus(req.getQuestId()) == 1 && !req.isCompleted()){
                    req.addNumber();
                }
            }
        }
    }
    
    //Should only be called when initially loading all quest data
    public static void addQuestData(QuestData data){
        allQuests.put(data.getQuestId(), data);
        
        if (data.isComplete()){
            return;
        }
        //only add to the requirement pile if this quest is not complete
        for (QuestRequirement req: data.getRequirements()){ // map int requirement data this is how mobs will be assigning to the stuff
            String mob = req.getMobId();
            if (!allQuestRequirementData.containsKey(mob)){
                allQuestRequirementData.put(mob, new LinkedList<QuestRequirement>());
            }
            allQuestRequirementData.get(mob).add(req);
        }
    }
    
    //compiles all current quest data to an array to be exported
    public static QuestData[] exportQuestData(){
        return allQuests.values().toArray(new QuestData[0]);
    }
    //takes and array of quest data and adds it into the questmanager database
    public static void importQuestData(QuestData[] data){
        for (QuestData q: data){
            addQuestData(q);
        }
    }
    // main method used to test the questManager before networking
    // a sample quest is made and manually added to until completion is detected
    public static void main(String[] args){
        AssetManager.init();
        QuestManager.init();
        QuestData meep = new QuestData("questA",0);
        QuestData merp = new QuestData("questB",0);
        meep.addRequirement(new QuestRequirement("billyscow","questA",1,1));
        merp.addRequirement(new QuestRequirement("billyscow","questA",0,2));
        QuestManager.addQuestData(meep);
        QuestManager.addQuestData(merp);
        QuestManager.addMobKill(new BillysCow(0,0,0));
        System.out.println(QuestManager.checkQuest("questA"));
        System.out.println(QuestManager.checkQuest("questB"));
        QuestManager.addMobKill(new BillysCow(0,0,0));
        System.out.println(QuestManager.checkQuest("questA"));
        System.out.println(QuestManager.checkQuest("questB"));
    }
}
