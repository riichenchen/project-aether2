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
public class DummySpatial extends Spatial {
    public DummySpatial(int x, int y, int length, int width) {
        super(x, 0, y, length, 0, width, 0, 0, 0);
    }
    
    @Override
    public void collideEffect(Spatial s) {}
    
}
