/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Controls;

import ArtificialIntelligence.Pathfinding;
import ArtificialIntelligence.FindClosestEnemy;
import ArtificialIntelligence.AIControl;
import ArtificialIntelligence.Node;
import GameSource.game.GameMap;
import Spatial.Spatial;
import Testing.CowAICalculation;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Shiyang
 */
public class CowAIControl extends AIControl {

    protected GameMap curMap;
    
    public CowAIControl(GameMap map) {
        super(new CowAICalculation());
        curMap = map;
    }

    @Override
    public void update(Object returnValue) {
        
//        int curX = (int)boundTo.getX();
//        int curY = (int)boundTo.getY();
//        
//        FindClosestEnemy fce = new FindClosestEnemy(curX, curY, curMap.getSpatials());
//        Spatial target = fce.getTarget();
//        
//        Pathfinding pf = new Pathfinding(curMap.getCharMap(), curX, curY, (int)target.getX(), (int)target.getY());
//        ArrayList myPath = pf.getPath();
//        
//        if (myPath.isEmpty()) {
//            Random rnd = new Random();
//            int xdist = rnd.nextInt(5)-1;
//            int ydist = rnd.nextInt(5)-1;
//            boundTo.move(xdist * 5, 0, ydist * 5);
//        } else {
//            int dx = ((Node)(myPath.get(0))).getX() - curX;
//            int dy = ((Node)(myPath.get(0))).getY() - curY;
//
//            boundTo.move(dx * 5, 0, dy * 5);
//        }
    }
    
}
