package GameSource.Game;

import Database.DatabaseHandler;
import GameSource.Net.Server.AetherNetListener;
import GameSource.game.GameMap;
import Networking.Server.ServerNetListener;
import PhysicsSpace.PhysicsSpace;

public class ServerWorldHandler {
    private DatabaseHandler db;
    
    private GameMap myGameMap;
    
    private PhysicsSpace myPhysicsSpace;
    private AetherNetListener myServerNetListener;

    private int numMonsters;
    private double mobDensity;

    public ServerWorldHandler(GameMap map, PhysicsSpace space, AetherNetListener netlistener,DatabaseHandler db) {
        myGameMap = map;
        myPhysicsSpace = space;
        myServerNetListener = netlistener;
        numMonsters = 0;
        mobDensity = map.getMobDensity();
        this.db = db;
        
        //update();       
    }
    
    public static void update() {

    }

    public static void addMonster() {

    }

    public static void dropItem() {

    }
}