/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.User;

/**
 *
 * @author Shiyang
 * The level manager provides a static method used to calculate exp required
 * to level the player up based upon a mathematical function.
 */
public class LevelManager {
    public static int requiredExp(int level){
        //I sorta played around with this and liked the overall effect :33
        return (int)(10*(level+Math.pow(1.175,level)));
    }
}
