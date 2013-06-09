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
 *
 * @author Shiyang
 */
public class QuestManager {
    private static HashMap<String,LinkedList<QuestRequirement>> allQuestRequirementData; //used for an O(1) "status" update
    private static HashMap<String, QuestData> allQuests;
    
    public static void init(){
        allQuestRequirementData = new HashMap<>();
        allQuests = new HashMap<>();
    }
    
    public static boolean checkQuest(String id){
        return allQuests.get(id).isComplete();
    }
    
    public static void addMobKill(AbstractMob monster){
        if (allQuestRequirementData.containsKey(monster.getName())){
            QuestRequirement[] questReq = allQuestRequirementData.get(monster.getName()).toArray(new QuestRequirement[0]);
            for (QuestRequirement req: questReq){
                if (!req.isCompleted()){
                    req.addNumber();
                }
            }
        }
    }
    
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
    
    // main method used to test the questManager before networking
    // a sample quest is made and manually added to until completion is detected
    public static void main(String[] args){
        AssetManager.init();
        QuestManager.init();
        QuestData meep = new QuestData("questA");
        QuestData merp = new QuestData("questB");
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
