/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Controls;

import ArtificialIntelligence.AIControl;
import GameSource.game.GameMap;
import Testing.CowAICalculation;
import java.util.Random;

/**
 *
 * @author Shiyang
 */
public class CowAIControl extends AIControl{

    public CowAIControl(GameMap map) {
        super(new CowAICalculation());
    }

    @Override
    public void update(Object returnValue) {
//        System.out.println("Updating cow "+boundTo.getId());
        Random meep = new Random();
        int xdist = meep.nextInt(3)-1;
        int ydist = meep.nextInt(3)-1;
        boundTo.move(xdist*5, 0, ydist*5);
    }
    
}
