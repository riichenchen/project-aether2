package GameSource.Controls;

import ArtificialIntelligence.Pathfinding;
import ArtificialIntelligence.FindClosestEnemy;
import ArtificialIntelligence.AIControl;
import ArtificialIntelligence.GrabSpatial;
import ArtificialIntelligence.MapParser;
import ArtificialIntelligence.Node;
import GameSource.Assets.MobData.AbstractMob;
import GameSource.game.GameMap;
import Spatial.Spatial;
import Testing.CowAICalculation;
import java.util.ArrayList;
import java.util.Random;

public class CowAIControl extends AIControl {

    protected GameMap curMap;
    protected ArrayList myPath;
    protected Pathfinding pf;
    protected FindClosestEnemy fce;
    protected int curX, curY;
    protected Spatial target;
    protected MapParser mp;
    protected boolean isLoaded;
    protected GrabSpatial gs;
    protected Spatial [][] inRange;
    
    public CowAIControl(GameMap map) {
        super(new CowAICalculation());
        curMap = map;
        if (map == null)
            System.out.println("Who needs a map?");
        isLoaded = false;
    }

    @Override
    public void update(Object returnValue) {
//        if (boundTo != null && !isLoaded)
//            init();
//        
//        if (myPath == null || myPath.isEmpty()) {
//            Random rnd = new Random();
//            int xdist = rnd.nextInt(5) - 1;
//            int ydist = rnd.nextInt(5) - 1;
//            boundTo.move(xdist, 0, ydist);
//        } else {
//            int dx = ((Node)(myPath.get(0))).getX() - curX;
//            int dy = ((Node)(myPath.get(0))).getY() - curY;
//         
//            boundTo.move(dx, 0, dy);
//      }
    }
    
    public void init() {
        curX = (int)boundTo.getX();
        curY = (int)boundTo.getZ();
        
        gs = new GrabSpatial(curMap, curX, curY);
        
        inRange = gs.getSpatials();
        
        mp = new MapParser(inRange);
        
        if (mp.getCharMap() != null) {
            fce = new FindClosestEnemy(curX, curY, inRange);
            target = fce.getTarget();

            pf = new Pathfinding(mp.getCharMap(), curX - mp.getxOffset(), curY - mp.getyOffset(), (int)target.getX() - mp.getxOffset(), (int)target.getY() - mp.getyOffset());
            myPath = pf.getPath();

            isLoaded = true;
        }
    }
    
    public int countSpatials() {
        ArrayList curSpatials = curMap.getSpatials();
        int size = curSpatials.size();
        int numMob = 0;
        for (int i = 0; i < size; i++) {
            if (curSpatials.get(i) instanceof AbstractMob) {
                numMob++;
            }
        }
        return numMob;
    }
    
}
