package GameSource.Game;

import GameSource.Game.GamePoint;
import GameSource.game.GameMap;
import Networking.Server.ServerNetListener;
import PhysicsSpace.PhysicsSpace;

public class ServerWorldHandler {
        private GameMap myGameMap;
        private GamePoint myGamePoint;
        private PhysicsSpace myPhysicsSpace;
        private ServerNetListener myServerNetListener;
        
        private int numMonsters;
        private double mobDensity;
        
        public ServerWorldHandler(GameMap map, GamePoint point, PhysicsSpace space, ServerNetListener netlistener) {
                myGameMap = map;
                myGamePoint = point;
                myPhysicsSpace = space;
                myServerNetListener = netlistener;
                numMonsters = 0;
                mobDensity = map.getMobDensity();
                update();       
        }
        
        public static void update() {
                
        }
        
        public static void addMonster() {
                
        }
        
        public static void dropItem() {
                
        }
}