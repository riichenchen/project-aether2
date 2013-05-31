package GameSource.Game;
import Controls.AbstractControl;
import GameSource.Assets.AssetManager;
import GameSource.Input.PlayerKeyListener;
import GameSource.Net.Client.AetherClientNetSender;
import GameSource.game.GameMap;
import Networking.Messages.DisconnectMessage;
import Networking.Messages.PlayerJoinMessage;
import PhysicsSpace.PhysicsSpace;
import PhysicsSync.PhysicSyncMessage;
import PhysicsSync.PhysicsSyncManager;
import PhysicsSync.SpatActionMessage;
import Spatial.MapSpatData;
import Spatial.Spatial;

public class ClientWorldHandler {
    private GameMap myGameMap;
    private AetherClientNetSender netSender;
    private PhysicsSpace myPhysicsSpace;
    private PhysicsSyncManager psync;
    private ClientMain theclient;
    private AetherGamePanel thegame;
    private int boundSpatId = -1;
    public ClientWorldHandler(ClientMain theclient,AetherGamePanel thegame) {
        this.theclient = theclient;
        this.thegame = thegame;
        this.psync = new PhysicsSyncManager(this);
        thegame.setHandler(this);
        AbstractControl.setWorld(this);
    }
    public Spatial getSpatial(int id,String mapid){
        return myGameMap.getSpatial(id);
    }
    
    public void addPSyncMessage(PhysicSyncMessage msg){
        psync.addMessage(msg);
    }
    public synchronized void setGameMap(String mapid){
        this.myGameMap = AssetManager.getMap(mapid);
        thegame.setMap(this.myGameMap);
    }
    public synchronized Spatial addPlayerSpatial(PlayerJoinMessage mymsg){
        //System.out.println(mymsg.getSpatId());
        int charType = mymsg.getCharacterType();
        GamePoint loc = mymsg.getLocation();
        //Future: Add support for different maps!
        String mapId = mymsg.getMapId();
        
        int entityid = mymsg.getSpatId();
        Spatial newSpat = myGameMap.addPlayer(charType,loc,entityid);
        return newSpat;
    }
    public void bindPlayerToClient(Spatial spat){
        spat.addControl(new PlayerKeyListener());
        this.boundSpatId = spat.getId();
    }
    public void bindSender(AetherClientNetSender netSender){
        this.netSender = netSender;
    }
    public synchronized void addAllSpatials(MapSpatData[] spatData){
        for (MapSpatData s: spatData){
            myGameMap.addPlayer(s.getType(), s.getLocation(),s.getId());
        }
    }
//    public void setGameWaiting(boolean b){
//        theclient.setWaiting(b);
//    }
    public void startGame(){
        theclient.startGame();
    }
    public void login(){
        theclient.login();
    }
    public void setResponse(String s){
        theclient.setResponse(s);
    }
    public synchronized void update(){
        myGameMap.update();
        //Work on this!
        psync.elapseTime();
        psync.update();
    }
    public synchronized void addSpatAction(Spatial spat, int action){
        netSender.sendMessage(new SpatActionMessage(spat,action));
    }
    
    public synchronized void disconnect(){
        netSender.sendMessage(new DisconnectMessage(myGameMap.getSpatial(boundSpatId)));
    }
    
    public synchronized void removeSpatial(String mapid,int spatId){
        AssetManager.getMap(mapid).removeSpatial(spatId);
    }
}