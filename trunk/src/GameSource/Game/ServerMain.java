package GameSource.Game;

import Database.DatabaseHandler;
import GameSource.Assets.AssetManager;
import GameSource.Net.Server.AetherNetListener;
import GameSource.Net.Server.AetherServer;
import GameSource.game.GameMap;
import PhysicsSpace.PhysicsSpace;

/*
 * @author Shiyang Han
 * The class run when starting the server.
 * Starts an aetherserver as well as a database handler
 */
public class ServerMain {

    private static DatabaseHandler myDB;
    private static AetherServer theServer;
    private static ServerWorldHandler world;
    
    public static void main(String [] args) {
        //AssetManager.init(); //incase we need to send any game content involving assets
        myDB = new DatabaseHandler();
        world = new ServerWorldHandler(myDB); // start the game
        theServer = new AetherServer(world);
        theServer.start();
    }
}