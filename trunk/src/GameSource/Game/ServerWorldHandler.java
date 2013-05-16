package GameSource.Game;

import Database.DatabaseHandler;
import GameSource.game.GameMap;
import Networking.Messages.Message;
import Networking.Messages.PlayerJoinMessage;
import Networking.Server.ClientManager;
import PhysicsSpace.PhysicsSpace;
import PhysicsSync.PhysicSyncMessage;
import PhysicsSync.PhysicsSyncManager;
import Spatial.Spatial;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ServerWorldHandler {
    private DatabaseHandler db;
    private GameMap myGameMap;
    private ClientManager clientManager;
    private PhysicsSpace myPhysicsSpace;
    private PhysicsSyncManager psync;
//    private ConcurrentLinkedQueue<WorldMessage> allMessages;
    public ServerWorldHandler(GameMap map, PhysicsSpace space,DatabaseHandler db) {
        this.myGameMap = map;
        this.myPhysicsSpace = space;
        this.db = db;     
        this.psync = new PhysicsSyncManager(this);
//        this.allMessages = new ConcurrentLinkedQueue<>();
    }
    
    public void bindManager(ClientManager clientManager){
        this.clientManager = clientManager;
    }
    
    public Spatial getSpatial(int id,String mapid){
        return myGameMap.getSpatial(id);
    }
    
    public void addPSyncMessage(PhysicSyncMessage msg){
        psync.addMessage(msg);
    }
    
    public void sendMessage(Message m){
        if (clientManager == null){
            System.out.println("SEVERE: NO CLIENTMANAGER!");
            System.exit(0);
        }
        clientManager.broadcast(m);
    }
    
    public void sendToOne(int clientId,Message msg){
        clientManager.sendToOne(clientId,msg);
    }
    
    public void update() {
        if (clientManager == null){
            System.out.println("ERROR! NO MANAGER!");
            System.exit(0);
        }
        psync.elapseTime();
        psync.update();
        //resolveMessages();
    }
//    public void sendSpatData(){
//        for ()
//    }

    public static void addMonster() {

    }

    public static void dropItem() {

    }
    
    public PlayerJoinMessage getPlayerJoinMessage(int accountid,int clientid){
        ResultSet r = db.makeQuerry("Select * from characters where accountid = "+accountid);
        try{
            r.next();
            //Note: There should only be one character at this point.
            //Anymore, and itl only take the first character!
            //Future: Add in multiple character support
            
            int characterType = r.getInt("characterType");
            //register our character into the world, the world returns a
            //spatial id for the client to register
            GamePoint loc = new GamePoint(r.getFloat("x"),r.getFloat("y"),r.getFloat("z"));
            String mapType = r.getString("mapid");
            
            int registeredId = myGameMap.addPlayer(characterType,loc);
            

            return new PlayerJoinMessage(characterType,loc,mapType,registeredId,clientid);
        } catch (SQLException e){
            System.out.println("Error fetching player data!");
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }
    public HashMap<Integer,Spatial> getNonPermanents(){
        return myGameMap.getNonPermanents();
    }
    
    public int RequestLogin(String username, String password){
        //System.out.println("okay to querry");
        ResultSet r = db.makeQuerry("Select * from accounts where username = '"+username+"' and password = '"+password+"'");
        try {
            if (r.next()){
                return r.getInt("idaccounts");
            } else {
                return -1;
            }
        } catch (SQLException e){
            System.out.println("ERROR LOGGING IN! "+e.getMessage());
            return -1;
        }
    }
}