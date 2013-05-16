package GameSource.Game;
import Controls.AbstractControl;
import GameSource.Assets.AssetManager;
import GameSource.Input.PlayerKeyListener;
import GameSource.Net.Client.AetherClientNetSender;
import GameSource.game.GameMap;
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
    public void setGameMap(String mapid){
        this.myGameMap = AssetManager.getMap(mapid);
        thegame.setMap(this.myGameMap);
    }
    public Spatial addPlayerSpatial(PlayerJoinMessage mymsg){
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
    }
    public void bindSender(AetherClientNetSender netSender){
        this.netSender = netSender;
    }
    public void addAllSpatials(MapSpatData[] spatData){
        for (MapSpatData s: spatData){
            myGameMap.addPlayer(0, s.getLocation(),s.getId());
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
    public void update(){
        myGameMap.update();
        //Work on this!
        psync.elapseTime();
        psync.update();
    }
    public void addSpatAction(Spatial spat, int action){
        netSender.sendMessage(new SpatActionMessage(spat,action));
    }
}