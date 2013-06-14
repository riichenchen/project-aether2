//OLD NOT USED IN CURRENT BUILD

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Angus
 */
 
 //* Refer to: http://mathworld.wolfram.com/Line-PlaneIntersection.html
 
 //This class determines if a line and a plane intersects and can return the point of intersection if they do intersect at a point
 
package ArtificialIntelligence;

public class LinePlaneIntersection {
   public class Point { //Express a point as (x, y, z) on the co-ordinate system
       double x, y, z;
       public Point(double _x, double _y, double _z) {
           x = _x;
           y = _y;
           z = _z;
       }
   }
   
   public class Line { //Express a line using 2 points
       Point x1, x2;
       public Line(Point _x1, Point _x2) {
           x1 = _x1;
           x2 = _x2;
       }
   }
   
   public class Plane { //Express a plane using 3 points
       Point x1, x2, x3;
       public Plane(Point _x1, Point _x2, Point _x3) {
           x1 = _x1;
           x2 = _x2;
           x3 = _x3;
       }
   }
   
   Line l;
   Plane p;
  
   double x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4, x5, y5, z5; //Components
   double tNum, tDen;
   
   public LinePlaneIntersection(Line _l, Plane _p) {
       l = _l;
       p = _p;
       
       getComponents();
       
	   //Math formula*
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
   
   private void getComponents() { //Get all components given the point of a line and plane
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
   
   //Recursively compute determinant of matrix
   private double solveMat(double [][] m) {
       int R = m.length;
       int C = m[0].length;
       
       if (R == 2 && C == 2) // If it's a 2 by 2 matrix, just solve it with down products minus up products
           return m[0][0] * m[1][1] - m[1][0] * m[0][1];
       
	   //Return value
       double ret = 0;
       
       for (int i = 0; i < C; i++) {
           double [][] newMat = new double[R - 1][C - 1]; //Make a new submatrix for each column
           int curR, curC; 	//Current row and column of submatrix
           curR = curC = 0; 
		   //Iterate through the matrix, ignore elements of the same row or column
           for (int j = 0; j < R; j++) { 
               if (i == j)
                   continue;
               for (int k = 0; k < C; k++) {
                   if (i == k)
                       continue;
                   newMat[curR][curC] = m[j][k]; //Add the elements to the submatrix
                   curC++;
               }
               curR++;
           }
		   //Determinant is evaluated by adding the alternating sums
           if ((i & 1) == 1) //Odd
               ret += (m[0][i] * -solveMat(newMat));
           else //Even
               ret += (m[0][i] * solveMat(newMat));
       }
       
       return ret;
   }
   
   public boolean intersectsAtLine() {
       return (tNum == 0 && tDen == 0); // '0' divided by '0' = indeterminate (in most cases), infinte points of intersection
   }
   
   public boolean doesNotIntersect() {
       return (tNum != 0 && tDen == 0); // Not '0' divided by '0' = undefined (in most cases), no point of intersection
   }
   
   public boolean intersectsAtPoint() {
       return (!intersectsAtLine() && !doesNotIntersect()); //If it does not intersect at a line and does intersect, it must be at a point
   }
   
   public Point getPointOfIntersection() {
       return new Point(x4 + (x5 - x4) * (tNum / tDen), y4 + (y5 - y4) * (tNum / tDen), z4 + (z5 - z4) * (tNum / tDen)); //Some math transformations* 
   }
}
