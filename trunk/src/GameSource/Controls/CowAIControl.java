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
    protected ArrayList myPath;
    protected Pathfinding pf;
    protected FindClosestEnemy fce;
    protected int curX, curY;
    protected Spatial target;
    protected boolean isLoaded;
    
    public CowAIControl(GameMap map) {
        super(new CowAICalculation());
        curMap = map;
        if (map == null)
            System.out.println("Who needs a map?");
        isLoaded = false;
    }

    @Override
    public void update(Object returnValue) {
        if (boundTo != null && !isLoaded)
            init();
        
        if (myPath == null)
            return;
        
        if (myPath.isEmpty()) {
            Random rnd = new Random();
            int xdist = rnd.nextInt(5) - 1;
            int ydist = rnd.nextInt(5) - 1;
            boundTo.move(xdist, 0, ydist);
        } else {
            int dx = ((Node)(myPath.get(0))).getX() - curX;
            int dy = ((Node)(myPath.get(0))).getY() - curY;
         
            boundTo.move(dx, 0, dy);
       }
    }
    
    public void init() {
        curX = (int)boundTo.getX();
        curY = (int)boundTo.getY();
        
        if (curMap == null) {
            System.out.println("Are you serious!");
        }

        fce = new FindClosestEnemy(curX, curY, curMap.getSpatials());
        target = fce.getTarget();

        pf = new Pathfinding(curMap.getCharMap(), curX, curY, (int)target.getX(), (int)target.getY());
        myPath = pf.getPath();
        
        isLoaded = true;
    }
    
}
