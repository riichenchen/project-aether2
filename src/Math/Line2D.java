/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Math;

/**
 *
 * @author Shiyang
 */
public class Line2D {
    private float A;
    private float B;
    private float C;
    private boolean undefinedSlope = false;
    private float xIntercept = 0; // used if undefined slope occurs
    private float slope;
    public Line2D(float x1,float y1,float x2,float y2) {
        this.A = -(y2-y1);
        this.B = x2-x1;
        if (this.B == 0){
            this.undefinedSlope = true;
            this.xIntercept = x1;
            return;
        }
        this.slope = -A/B;
        float b = y2-slope*x2;
        this.C = b*B;
    }
    
    public Line2D(Point2D a,Point2D b){
        this(a.x,a.y,b.x,b.y);
    }
    
    public Point2D intersect(Line2D line){
        if (undefinedSlope){
            return new Point2D(xIntercept,-line.A/line.B*xIntercept+line.C/line.B);
        } else if (line.undefinedSlope){
            return new Point2D(line.xIntercept,-A/B*line.xIntercept+C/B);
        }
        float delta = A*line.B-B*line.A;
        if (delta == 0){
            return null;
        }
        float nx = (line.B*C-line.C*B)/delta;
        float ny = (line.C*A-line.A*C)/delta;
        
        return new Point2D(nx,ny);
    }
    
    public float distanceTo(Point2D point){
        if (!undefinedSlope){
            return Math.abs(A*point.x+B*point.y-C)/(float)Math.sqrt(Math.pow(A,2)+Math.pow(B,2));
        } 
        return Math.abs(point.x-xIntercept);
    }
    
    public boolean isUndefined(){
        return undefinedSlope;
    }
    public float getSlope(){
        return slope;
    }
}
