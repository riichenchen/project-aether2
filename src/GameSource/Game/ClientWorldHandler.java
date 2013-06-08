package GameSource.Game;
import ArtificialIntelligence.AIHandler;
import Controls.AbstractControl;
import Database.PlayerData;
import Database.SaveItemData;
import GameSource.Assets.AssetManager;
import GameSource.Assets.Portals.Portal;
import GameSource.Globals;
import GameSource.Net.Client.AetherClientNetSender;
import GameSource.User.CharacterHandler;
import GameSource.User.InventoryHandler;
import GameSource.User.ItemFactory;
import GameSource.game.GameMap;
import Networking.Messages.PlayerJoinMessage;
import Networking.Messages.SaveMessage;
import Sound.SoundManager;
import Spatial.AetherMouse;
import Spatial.Spatial;
import Testing.PlayerSpatial;
import Testing.SteveyKeyListener;

public class ClientWorldHandler {
    private GameMap myGameMap;
    private AetherClientNetSender netSender;
    private ClientMain theclient;
    private AetherGamePanel thegame;
    private Spatial boundSpat = null;
    private int boundAccountId = -1;
    private AetherCameraControl camControl;
    private AIHandler aiHandle;
    private AetherMouse theMouse;
    
    public ClientWorldHandler(ClientMain theclient,AetherGamePanel thegame) {
        this.theclient = theclient;
        this.thegame = thegame;
        thegame.setHandler(this);
        AbstractControl.setWorld(this);
        SoundManager.addChannel("Effects", false);
        SoundManager.addChannel("BackgroundMusic", true);
        SoundManager.addChannel("UI",false);
        SoundManager.getChannel("UI").setNumberTracks(10);
        SoundManager.getChannel("Effects").setNumberTracks(6);
        this.camControl = new AetherCameraControl();
        this.aiHandle = new AIHandler();
        aiHandle.start();
        this.theMouse = Globals.theMouse;
    }
    
    public AetherMouse getMouse(){
        return theMouse;
    }
    public void setGameMap(String mapid){
        this.myGameMap = AssetManager.getMap(mapid);
        thegame.setMap(this.myGameMap);
        camControl.bindToCamera(myGameMap.getCamera());
        SoundManager.getChannel("BackgroundMusic").addTrack(myGameMap.getBGMusic());
    }
    
    public Spatial addPlayerSpatial(PlayerJoinMessage mymsg){
        PlayerData pData = mymsg.getpData();
        GamePoint loc = pData.getLocation();
        String mapId = pData.getMapId();
        setGameMap(mapId);
        PlayerSpatial newSpat = myGameMap.addPlayer(loc);
        for (SaveItemData dat: pData.getItems()){
            InventoryHandler.addItem(ItemFactory.getItem(dat.getItemKey()),dat.getQuantity());
        }
        String[] keys = new String[]{"hp","maxhp","mp","maxmp","money","level","exp","attack","defense"};
        CharacterHandler.addAllStats(keys,pData.getEntity_data());
        CharacterHandler.addAllSkills(pData.getSkillData());
        CharacterHandler.setName(pData.getCharName());
        CharacterHandler.bindPlayer(newSpat);
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
        aiHandle.update();
        myGameMap.update();
        camControl.update();
    }
    
    public void disconnect(){
        Spatial playerSpat = boundSpat;
        PlayerData saveData = new PlayerData(boundAccountId,-1,playerSpat.getLocation(),myGameMap.getName());
        netSender.sendMessage(new SaveMessage(saveData));
    }
    
    public void enterPortal(){
        Portal curPort = CharacterHandler.getCurrentPortal();
        if (curPort != null)
            enterPortal(curPort);
    }
    
    private void enterPortal(Portal port){
        //Clear old map's previous messages
        AbstractControl hold = (AbstractControl)boundSpat.getControl(SteveyKeyListener.class);
        boundSpat.removeControl(hold);
        myGameMap.removeSpatial(boundSpat);
        boundSpat.setLocation(port.getNewPos());
        myGameMap = AssetManager.getMap(port.getToMap());
        myGameMap.addSpatial(boundSpat);
        thegame.setMap(myGameMap);
        //Clear new Map's previous messages
//        myGameMap.clearMessages();
        camControl.bindToCamera(myGameMap.getCamera());
        SoundManager.getChannel("BackgroundMusic").stopAll();
        SoundManager.getChannel("BackgroundMusic").addTrack(myGameMap.getBGMusic());
        boundSpat.addControl(hold);
    }
    public void clickMap(int x,int y){
        myGameMap.getSpace().grabSpatialsAround(theMouse);
//        myGameMap.getSpace().grabSpatialsAround();
    }
}