/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ArtificialIntelligence;

import PhysicsSpace.PhysicsSpace;
import Spatial.Spatial;
import java.util.Arrays;

/**
 *
 * @author Angus
 */
public class MapParser {
   
    protected char [][] charMap;
    protected Spatial [][] mySpats;
    protected int xOffset, yOffset;
    
    public MapParser(Spatial[][] _mySpats) {
        mySpats = _mySpats;
        int lx, ly, rx, ry;
        lx = ly = 1 << 30;
        rx = ry = - (1 << 30);
        for (int i = 0; i < mySpats[1].length; i++) {
            
            int cx = (int)mySpats[1][i].getX();
            int cy = (int)mySpats[1][i].getZ();
            
            if (cx < lx)
                lx = cx;
            if (cy < ly)
                ly = cy;
            
            if (cx > rx)
                rx = cx;
            if (cy > ry)
                ry = cy;
        }
        
        xOffset = lx;
        yOffset = ly;
        
        charMap = new char [rx - xOffset][ry - yOffset];
        
        for (char [] c: charMap)
            Arrays.fill(c, (char)0);
        
        for (int i = 0; i < mySpats[1].length; i++) {
            int cx = (int)mySpats[1][i].getX();
            int cy = (int)mySpats[1][i].getZ();
            charMap[cx - xOffset][cy - yOffset] = 1;
        }
    }
    
    public int getxOffset() {
        return xOffset;
    }
    
    public int getyOffset() {
        return yOffset;
    }
    
    public char[][] getCharMap() {
        return charMap;
    }
    
    
}
