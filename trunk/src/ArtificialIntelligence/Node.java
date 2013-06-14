/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ArtificialIntelligence;

/**
 *
 * @author Angus
 */
 
 //Node used for A* path finding
 
public class Node {
            protected int location, cost; //Location is the x, y coordinate packed as x * Pathfinding.Y_MAX + y and cost is the edge cost + manhattan distance heuristic
            public Node(int _location, int _cost) {
                    location = _location;
                    cost = _cost;
            }
            
            public int getX() {
                return location / (Pathfinding.Y_MAX); //return x component
            }
            
            public int getY() {
                return location % (Pathfinding.Y_MAX); //return y component
            }
}