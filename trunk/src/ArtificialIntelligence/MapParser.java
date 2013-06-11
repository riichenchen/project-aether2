/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ArtificialIntelligence;

import GameSource.PhysicsSpace.*;

/**
 *
 * @author Angus
 */
public class MapParser {
    
    public static final int DEFAULT_HEIGHT = 64;
    public static final int DEFAULT_WIDTH = 64;
    protected int x, y;
    protected int boundary_height, boundary_width;
    
    public MapParser(int _x, int _y, int _boundary_height, int _boundary_width) {
        x = _x;
        y = _y;
        boundary_height = _boundary_height;
        boundary_width = _boundary_width;
    }
    
    public MapParser(int _x, int _y) {
        x = _x;
        y = _y;
        boundary_height = DEFAULT_HEIGHT;
        boundary_width = DEFAULT_WIDTH;
    }
    
    
}
