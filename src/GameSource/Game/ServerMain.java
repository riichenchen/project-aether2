package GameSource.Game;

import Database.DatabaseHandler;
import GameSource.Globals;
import GameSource.Net.Server.AetherNetListener;
import GameSource.Net.Server.AetherServer;
import GameSource.game.GameMap;
import PhysicsSpace.PhysicsSpace;

public class ServerMain {

    private static DatabaseHandler myDB;
    private static GameMap myGameMap;
    private static PhysicsSpace myPhysicsSpace;
    private static AetherNetListener myNetListener;
    private static AetherServer theServer;
    private static ServerWorldHandler world;
    
    public static void main(String [] args) {
        myDB = new DatabaseHandler();
        myGameMap = new GameMap("theMap",0.1,1600,1200,Globals.__CAMX__,Globals.__CAMY__);
        
        //world = new ServerWorldHandler();
        theServer = new AetherServer(world);
        
    }
}