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
    public static int requiredExp(int level){
        return (int)(10*(level+Math.pow(1.175,level)));
    }
}
