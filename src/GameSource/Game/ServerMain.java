package GameSource.Game;

import Database.DatabaseHandler;
import GameSource.Assets.AssetManager;
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
        AssetManager.init();
        myDB = new DatabaseHandler();
        myGameMap = AssetManager.getMap("testMap");
        //myNetListener = new AetherNetListener();
        world = new ServerWorldHandler(myGameMap,null,myDB);
        theServer = new AetherServer(world);
        theServer.start();
        while (true){
            world.update();
        }
    }
}