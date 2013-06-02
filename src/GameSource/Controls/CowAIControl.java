/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Controls;

import ArtificialIntelligence.AIControl;
import GameSource.game.GameMap;
import Testing.CowAICalculation;

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
        System.out.println("Updating cow "+boundTo.getId());
    }
    
}
