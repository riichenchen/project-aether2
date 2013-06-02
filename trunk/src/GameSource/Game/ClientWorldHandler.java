package GameSource.Game;
import Controls.AbstractControl;
import Database.PlayerData;
import GameSource.Assets.AssetManager;
import GameSource.Assets.Portals.Portal;
import GameSource.Net.Client.AetherClientNetSender;
import GameSource.game.GameMap;
import Networking.Messages.PlayerJoinMessage;
import Networking.Messages.SaveMessage;
import Sound.SoundManager;
import Spatial.Spatial;
import Testing.SteveyKeyListener;

public class ClientWorldHandler {
    private GameMap myGameMap;
    private AetherClientNetSender netSender;
    private ClientMain theclient;
    private AetherGamePanel thegame;
    private Spatial boundSpat = null;
    private int boundAccountId = -1;
    private AetherCameraControl camControl;
    
    public ClientWorldHandler(ClientMain theclient,AetherGamePanel thegame) {
        this.theclient = theclient;
        this.thegame = thegame;
        thegame.setHandler(this);
        AbstractControl.setWorld(this);
        SoundManager.addChannel("Effects", false);
        SoundManager.addChannel("BackgroundMusic", true);
        SoundManager.getChannel("Effects").setNumberTracks(6);
        this.camControl = new AetherCameraControl();
    }
    
    public void setGameMap(String mapid){
        this.myGameMap = AssetManager.getMap(mapid);
        thegame.setMap(this.myGameMap);
        camControl.bindToCamera(myGameMap.getCamera());
        SoundManager.getChannel("BackgroundMusic").addTrack(myGameMap.getBGMusic());
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
        spat.addControl(new SteveyKeyListener());
        this.boundSpat = spat;
        camControl.bindToSpatial(spat);
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
        camControl.update();
    }
    
    public void disconnect(){
        Spatial playerSpat = boundSpat;
        PlayerData saveData = new PlayerData(boundAccountId,-1,playerSpat.getLocation(),myGameMap.getName());
        netSender.sendMessage(new SaveMessage(saveData));
    }
    
    public void removeSpatial(String mapid,int spatId){
        AssetManager.getMap(mapid).removeSpatial(spatId);
    }
    
    public void enterPortal(){
        Portal curPort = (Portal)boundSpat.getProperty("currentPortal");
        if (curPort != null)
            enterPortal(curPort);
    }
    
    public void enterPortal(Portal port){
        //Clear old map's previous messages
        myGameMap.clearMessages();
        AbstractControl hold = (AbstractControl)boundSpat.getControl(SteveyKeyListener.class);
        boundSpat.removeControl(hold);
        myGameMap.removeSpatial(boundSpat);
        boundSpat.setLocation(port.getNewPos());
        myGameMap = AssetManager.getMap(port.getToMap());
        myGameMap.addSpatial(boundSpat);
        thegame.setMap(myGameMap);
        //Clear new Map's previous messages
        myGameMap.clearMessages();
        camControl.bindToCamera(myGameMap.getCamera());
        SoundManager.getChannel("BackgroundMusic").stopAll();
        SoundManager.getChannel("BackgroundMusic").addTrack(myGameMap.getBGMusic());
        boundSpat.addControl(hold);
    }
}