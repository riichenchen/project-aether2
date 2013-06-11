/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ArtificialIntelligence;

import Spatial.Spatial;

/**
 *
 * @author Angus
 */
public class MapParser {
    
    public static final int DEFAULT_LENGTH = 64;
    public static final int DEFAULT_WIDTH = 64;
    protected int x, y;
    protected int boundary_length, boundary_width;
    protected DummySpatial dummySpat;
    protected char [][] charMap;
    
    public MapParser(int _x, int _y, int _boundary_length, int _boundary_width) {
        x = _x;
        y = _y;
        boundary_length = _boundary_length;
        boundary_width = _boundary_width;
        init();
    }
    
    public MapParser(int _x, int _y) {
        x = _x;
        y = _y;
        boundary_length = DEFAULT_LENGTH;
        boundary_width = DEFAULT_WIDTH;
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
        charMap = new char[boundary_length][boundary_width];
        
    }
    
    
}
