/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.User;

/**
 *
 * @author Shiyang
 */
public class LevelManager {
    private static int requiredExp(int level){
        return (int)(10*(level+Math.pow(1.175,level)));
    }
    public static void addExp(int exp){
        do {
            int currentExp = CharacterHandler.getStat("exp");
            int currentLevel = CharacterHandler.getStat("level");
            int required = requiredExp(currentLevel);
            if (currentExp+exp > required){
                int dif = currentExp+exp-required;
                CharacterHandler.addStat("level",1);
                CharacterHandler.addStat("exp",dif-currentExp);
                System.out.println("level up! to level "+CharacterHandler.getStat("level"));
            } else {
                break; // continuously level (incase overleveling occurs, which is really unlikely
            }
        } while (true);
    }
}
