/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PhysicsTesting;

import java.awt.*;

/**
 *
 * @author Robert
 */
public class polycollidetest {
    private int[] xpoints;
    private int[] ypoints;
    public Polygon a;
    public Rectangle b;
    
    public polycollidetest(){
        xpoints= new int[]{100,140,140,100};
        ypoints= new int[]{100,100,140,140};
        a = new Polygon(xpoints,ypoints,4);
        b = new Rectangle(150,150,100,100);
    }
    
    public static void main(String[] args){
        polycollidetest der = new polycollidetest();
        if (der.a.intersects(150,150,100,100)){
            System.out.println("derp");
        }
    }
}
