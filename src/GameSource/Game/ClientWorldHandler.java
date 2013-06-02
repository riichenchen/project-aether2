package GameSource.Game;
import Controls.AbstractControl;
import Database.PlayerData;
import GameSource.Assets.AssetManager;
import GameSource.Input.PlayerKeyListener;
import GameSource.Net.Client.AetherClientNetSender;
import GameSource.game.GameMap;
import Networking.Messages.PlayerJoinMessage;
import Networking.Messages.SaveMessage;
import PhysicsSpace.PhysicsSpace;
import Spatial.Spatial;

public class ClientWorldHandler {
    private GameMap myGameMap;
    private AetherClientNetSender netSender;
    private ClientMain theclient;
    private AetherGamePanel thegame;
    private int boundSpatId = -1;
    private int boundAccountId = -1;
    
    public ClientWorldHandler(ClientMain theclient,AetherGamePanel thegame) {
        this.theclient = theclient;
        this.thegame = thegame;
        thegame.setHandler(this);
        AbstractControl.setWorld(this);
    }
    
    public Spatial getSpatial(int id,String mapid){
        return myGameMap.getSpatial(id);
    }
    
    public void setGameMap(String mapid){
        this.myGameMap = AssetManager.getMap(mapid);
        thegame.setMap(this.myGameMap);
    }
    public Spatial addPlayerSpatial(PlayerJoinMessage mymsg){
        //System.out.println(mymsg.getSpatId());
        PlayerData pData = mymsg.getpData();
        int charType = pData.getCharacterType();
        GamePoint loc = pData.getLocation();
        //Future: Add support for different maps!
        String mapId = pData.getMapId();
        setGameMap(mapId);
        Spatial newSpat = myGameMap.addPlayer(charType,loc);
        return newSpat;
    }
    
    public void bindPlayerToClient(Spatial spat){
        spat.addControl(new PlayerKeyListener());
        this.boundSpatId = spat.getId();
    }
    
    public void bindAccountToClient(int accountId){
        this.boundAccountId = accountId;
    }
    
    public void bindSender(AetherClientNetSender netSender){
        this.netSender = netSender;
    }
    
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
    }
    
    public void disconnect(){
        Spatial playerSpat = myGameMap.getSpatial(boundSpatId);
        PlayerData saveData = new PlayerData(boundAccountId,-1,playerSpat.getLocation(),myGameMap.getName());
        netSender.sendMessage(new SaveMessage(saveData));
    }
    
    public void removeSpatial(String mapid,int spatId){
        AssetManager.getMap(mapid).removeSpatial(spatId);
    }
}