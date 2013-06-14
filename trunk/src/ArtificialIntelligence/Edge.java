/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ArtificialIntelligence;

/**
 *
 * @author Angus
 */

 //Edge for A* Algorithm in Pathfinding.java
 
 public class Edge {
        protected int to, cost; //to is the other Node that the Edge is connected to, cost is the cost to traverse the edge
        public Edge(int _to, int _cost) {
                to = _to;
                cost = _cost;
        }
}
