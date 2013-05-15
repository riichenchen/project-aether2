/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PhysicsTesting;

import Spatial.Spatial;

/**
 *
 * @author Robert
 */
public class collidetest {
    public static void main(String[] args){
        Spatial a = new Spatial(0f,10f,0f, 10f,20f,10f, 0f,0f,0) {

            @Override
            public void collideEffect(Spatial s) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        Spatial b = new Spatial(0f,0f,0f, 10f,20f,10f, 0f,0f,0) {

            @Override
            public void collideEffect(Spatial s) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        if (a.collide(b)){
            System.out.println("gord");
        }
    }
}
