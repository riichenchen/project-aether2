package GameSource.Game;

import Database.DatabaseHandler;
import GameSource.Assets.AssetManager;
import GameSource.Net.Server.AetherNetListener;
import GameSource.Net.Server.AetherServer;
import GameSource.game.GameMap;
import PhysicsSpace.PhysicsSpace;

public class ServerMain {

    private static DatabaseHandler myDB;
    private static AetherServer theServer;
    private static ServerWorldHandler world;
    
    public static void main(String [] args) {
        AssetManager.init();
        myDB = new DatabaseHandler();
        world = new ServerWorldHandler(myDB);
        theServer = new AetherServer(world);
        theServer.start();
    }
}