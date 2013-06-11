/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Assets;

import GameSource.Game.GamePoint;
import GameSource.UI.DamageNumber;
import GameSource.game.GameMap;
import java.util.LinkedList;

/**
 * The DamageFactory class takes in a map, damage, int representing image type
 * as well as a location and spawns the number on the gamemap
 * @author Shiyang
 */
public class DamageFactory {
    public static final int RED = 0,PURPLE = 1,LENGTH = 30,HEIGHT = 39;
    
    public static void addDamage(GameMap map,int damage,int type,GamePoint location){
        LinkedList<Integer> digits = getDigits(damage);
        int length = LENGTH*digits.size();
        //increment to the right everytime another digit is added
        for (int i = 0; i < length; i+= LENGTH){
            map.addBackgroundSpatial(new DamageNumber(location.getX()-length/2+i,location.getZ()-100,type,digits.pollLast()));
        }
    }
    
    /*This method takes in a number and returns a linked list containing its digits*/
    private static LinkedList<Integer> getDigits(int x){
        LinkedList<Integer> digits = new LinkedList<>();
        if (x == 0)
            digits.add(0);
        double n = 10;
        while (x/n >= 0.1){ // keep taking out powers of 10 and tracking the remainder
            digits.add((int)((x%n-x%(n/10))/n*10));
            n*=10;
        }
        return digits;
    }
}
