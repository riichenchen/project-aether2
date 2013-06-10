/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Angus
 */
 
 //Refer to: http://mathworld.wolfram.com/Line-PlaneIntersection.html
 
package ArtificialIntelligence;

public class LinePlaneIntersection {
   public class Point {
       double x, y, z;
       public Point(double _x, double _y, double _z) {
           x = _x;
           y = _y;
           z = _z;
       }
   }
   
   public class Line {
       Point x1, x2;
       public Line(Point _x1, Point _x2) {
           x1 = _x1;
           x2 = _x2;
       }
   }
   
   public class Plane {
       Point x1, x2, x3;
       public Plane(Point _x1, Point _x2, Point _x3) {
           x1 = _x1;
           x2 = _x2;
           x3 = _x3;
       }
   }
   
   Line l;
   Plane p;
  
   double x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4, x5, y5, z5;
   double tNum, tDen;
   
   public LinePlaneIntersection(Line _l, Plane _p) {
       l = _l;
       p = _p;
       
       getComponents();
       
       double matNum[][] = 
                 {{1, 1, 1, 1},
                 {x1, x2, x3, x4},
                 {y1, y2, y3, y4},
                 {z1, z2, z3, z4}};
       
       double matDen[][] = 
               {{1, 1, 1, 0},
               {x1, x2, x3, x5 - x4},
               {y1, y2, y3, y5 - y4},
               {z1, z2, z3, z5 - z4}};
       
       tNum = -solveMat(matNum);
       tDen = solveMat(matDen);
   }
   
   private void getComponents() {
       x1 = p.x1.x;
       y1 = p.x1.y;
       z1 = p.x1.z;
       
       x2 = p.x2.x;
       y2 = p.x2.y;
       z2 = p.x2.z;
       
       x3 = p.x3.x;
       y3 = p.x3.y;
       z3 = p.x3.z;
       
       x4 = l.x1.x;
       y4 = l.x1.y;
       z4 = l.x1.z;
       
       x5 = l.x2.x;
       y5 = l.x2.y;
       z5 = l.x2.z;
   }
   
   private double solveMat(double [][] m) {
       int R = m.length;
       int C = m[0].length;
       
       if (R == 2 && C == 2)
           return m[0][0] * m[1][1] - m[1][0] * m[0][1];
       
       double ret = 0;
       
       for (int i = 0; i < C; i++) {
           double [][] newMat = new double[R - 1][C - 1];
           int curR, curC;
           curR = curC = 0;
           for (int j = 0; j < R; j++) {
               if (i == j)
                   continue;
               for (int k = 0; k < C; k++) {
                   if (i == k)
                       continue;
                   newMat[curR][curC] = m[j][k];
                   curC++;
               }
               curR++;
           }
           if ((i & 1) == 1)
               ret += (m[0][i] * -solveMat(newMat));
           else 
               ret += (m[0][i] * solveMat(newMat));
       }
       
       return ret;
   }
   
   public boolean intersectsAtLine() {
       return (tNum == 0 && tDen == 0);
   }
   
   public boolean doesNotIntersect() {
       return (tNum != 0 && tDen == 0);
   }
   
   public boolean intersectsAtPoint() {
       return (!intersectsAtLine() && !doesNotIntersect());
   }
   
   public Point getPointOfIntersection() {
       return new Point(x4 + (x5 - x4) * (tNum / tDen), y4 + (y5 - y4) * (tNum / tDen), z4 + (z5 - z4) * (tNum / tDen));
   }
}
