/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Math;

/**
 *
 * @author Shiyang
 * Point2D is a container for a 2D point
 * to be used with line2D.
 * Provides a method that calculates the distance squared to another point
 */
public class Point2D {
    public float x,y;
    
    public Point2D(float x,float y) {
        this.x = x;
        this.y = y;
    }
    //pythagorean distance formula
    public double distSquared(Point2D p){
        return Math.pow(x-p.x,2)+Math.pow(y-p.y, 2);
    }
    
}
