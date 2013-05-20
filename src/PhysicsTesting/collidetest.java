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
        
        
//        Spatial c = new Spatial(0f,0f,0f, 40f,20f,20f, 0f,0f,0) {
//            @Override
//            public void collideEffect(Spatial s) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//        };
//        Spatial d = new Spatial(10f,10f,10f, 20f,20f,20f, 0f,0f,0) {
//            @Override
//            public void collideEffect(Spatial s) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//        };
//        
//        Spatial e = new Spatial(0f,0f,0f, 20f,40f,20f, 0f,0f,0) {
//            @Override
//            public void collideEffect(Spatial s) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//        };
//        Spatial f = new Spatial(10f,10f,10f, 20f,20f,20f, 0f,0f,0) {
//            @Override
//            public void collideEffect(Spatial s) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//        };
        Spatial a = new Spatial(0f,0f,0f, 20f,20f,40f, 0, 0f,0f,0) {
            @Override
            public void collideEffect(Spatial s) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        Spatial b = new Spatial(0f,0f,0f, 5f,5f,5f, 0, 0f,0f,0) {
            @Override
            public void collideEffect(Spatial s) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        
        if (a.collide(b)){ // || b.collide(a)){
            System.out.println("gord");
        }
//        if (c.collide(d) || d.collide(c)){
//            System.out.println("tord");
//        }
//        if (e.collide(f) || f.collide(e)){
//            System.out.println("vord");
//        }
    }
}
