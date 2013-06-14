//OLD NOT USED ANYMORE

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ArtificialIntelligence;

import GameSource.Assets.Portals.Portal;
import GameSource.game.GameMap;
import PhysicsSpace.PhysicsSpace;
import Spatial.Spatial;
import Testing.PlayerSpatial;

/**
 *
 * @author Angus
 */
public class GrabSpatial {
    
    public static final int DEFAULT_LENGTH = 1024;
    public static final int DEFAULT_WIDTH = 1024;
    protected int x, y;
    protected int boundary_length, boundary_width;
    protected DummySpatial dummySpat;
    protected Spatial [][] inRange;
    protected PhysicsSpace ps;
    protected GameMap gm;
    
    public GrabSpatial(GameMap map, int _x, int _y, int _boundary_length, int _boundary_width) {
        x = _x;
        y = _y;
        boundary_length = _boundary_length;
        boundary_width = _boundary_width;
        gm = map;
        ps = map.getPhysicsSpace();
        init();
    }
    
    public GrabSpatial(GameMap map, int _x, int _y) {
        x = _x;
        y = _y;
        boundary_length = DEFAULT_LENGTH;
        boundary_width = DEFAULT_WIDTH;
        gm = map;
        ps = map.getPhysicsSpace();
        init();
    }
    
    public void updateLocation(int _x, int _y) {
        x = _x;
        y = _y;
        init();
    }
    
    public void updateBoundary(int _boundary_length, int _boundary_width) {
        boundary_length = _boundary_length;
        boundary_width = _boundary_width;
        init();
    }
    
    private void init() {
        dummySpat = new DummySpatial(x, y, boundary_length, boundary_width);
        gm.addSpatial(dummySpat);
        if (gm == null)
            System.out.println("Passing in null map");
        inRange = ps.grabSpatialsAround(dummySpat);
        
        int xlen = inRange[0].length;
        int ylen = inRange[1].length;
        gm.removeSpatial(dummySpat);
        //System.out.printf("Num player / mob spats: %d\nNum enviro spats: %d\n", xlen, ylen);
    }
    
    public Spatial[][] getSpatials() {
        return inRange;
    }
    
}
