/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ArtificialIntelligence;

/**
 *
 * @author Angus
 */
public class Node {
            protected int location, cost;
            public Node(int _location, int _cost) {
                    location = _location;
                    cost = _cost;
            }
            
            public int getX() {
                return location / (Pathfinding.Y_MAX);
            }
            
            public int getY() {
                return location % (Pathfinding.Y_MAX);
            }
}