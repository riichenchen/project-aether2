package GameSource.Game;
import ArtificialIntelligence.AIHandler;
import Controls.AbstractControl;
import Database.PlayerData;
import Database.SaveItemData;
import GameSource.Assets.AssetManager;
import GameSource.Assets.Portals.Portal;
import GameSource.GUI.MyGUI;
import GameSource.Globals;
import GameSource.Net.Client.AetherClientNetSender;
import GameSource.Quest.QuestManager;
import GameSource.User.CharacterHandler;
import GameSource.User.EquipHandler;
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

/* @author Shiyang Han
 * Client World Handler class. All main operations in the game are handled by
 * this class. This class is also the synchronizer between all other components
 * in the game. It contains a handle to the ai, the current gamemap and the 
 * network as well as the sound and asset managers. The update method of this
 * class is called per action performed.
 */

public class ClientWorldHandler {
    private GameMap myGameMap;
    private AetherClientNetSender netSender;
    private ClientMain theclient;
    private AetherGamePanel thegame;
    private Spatial boundSpat = null; //handle to the player spatial
    private int boundAccountId = -1; // account id for saving
    private AetherCameraControl camControl; // camControl for each aethercam
    private AIHandler aiHandle; 
    private AetherMouse theMouse;
    
    public ClientWorldHandler(ClientMain theclient,AetherGamePanel thegame) {
        this.theclient = theclient;
        this.thegame = thegame; // Bind to the game panel
        thegame.setHandler(this);
        AbstractControl.setWorld(this);//bind all controls to the world handler
        SoundManager.addChannel("Effects", false); // initialize the sound handler with some default channels
        SoundManager.addChannel("BackgroundMusic", true);
        SoundManager.addChannel("UI",false);
        SoundManager.getChannel("UI").setNumberTracks(10);
        SoundManager.getChannel("Effects").setNumberTracks(6);
        this.camControl = new AetherCameraControl();
        this.aiHandle = new AIHandler();
        aiHandle.start(); // begin the ai handle thread
        this.theMouse = Globals.theMouse; // bind the mouse
    }
    
    public AetherMouse getMouse(){
        return theMouse;
    }
    //called each time a new map is entered
    public void setGameMap(String mapid){
        String oldMusic = null;
        if (myGameMap != null){
            oldMusic = myGameMap.getBGMusic();
        }
        this.myGameMap = AssetManager.getMap(mapid);
        thegame.setMap(this.myGameMap);
        camControl.bindToCamera(myGameMap.getCamera());
        //BackgroundMusic only has 1 track slot for bgm, so it gets overridden each time
        if (oldMusic == null || !myGameMap.getBGMusic().equals(oldMusic)){ // only swap if the music actually changes
            SoundManager.getChannel("BackgroundMusic").addTrack(myGameMap.getBGMusic());
        }
    }
    
    //Called when the network handler receives player data
    //should only be called once!
    public Spatial addPlayerSpatial(PlayerJoinMessage mymsg){
        PlayerData pData = mymsg.getpData();//get the player data, map location, etc.
        GamePoint loc = pData.getLocation();
        String mapId = pData.getMapId();
        setGameMap(mapId);
        PlayerSpatial newSpat = myGameMap.addPlayer(loc);//load in the player
        EquipHandler.equipAll(pData.getEquipData()); //load in all equipment
        for (SaveItemData dat: pData.getItems()){ // load in all items
            InventoryHandler.addItem(ItemFactory.getItem(dat.getItemKey()),dat.getQuantity());
        } //load in stats
        String[] keys = new String[]{"maxhp","hp","maxmp","mp","money","level","exp","attack","defense","statPoints","skillPoints","job"};
        CharacterHandler.addAllStats(keys,pData.getEntity_data());
        CharacterHandler.addAllSkills(pData.getSkillData()); //load in skills
        CharacterHandler.setName(pData.getCharName()); //set the name
        CharacterHandler.bindPlayer(newSpat);//add a keycontrol
        QuestManager.importQuestData(pData.getQuestData());
        MyGUI.updateStatWindow(); // update the gui
        return newSpat;
    }
    //Bind a input object to the spatial passed in
    //Assumes that any time a spatial is passed in, it's the player spatial
    public void bindPlayerToClient(Spatial spat){
        spat.addControl(new SteveyKeyListener());
        this.boundSpat = spat;
        camControl.bindToSpatial(spat);
    }
    
    public void bindAccountToClient(int accountId){
        this.boundAccountId = accountId;
    }
    
    //bind a handle of the netSender for communication to the server
    public void bindSender(AetherClientNetSender netSender){
        this.netSender = netSender;
    }
    
    //Initializes the game itself, called by the network handler to begin
    //loading process when a valid login is received
    public void startGame(){
        theclient.startGame();
    }
    //Login method; called when a successful login occurs
    public void login(){
        theclient.login();
    }
    //sets a login response should unsuccessful login occur
    public void setResponse(String s){
        theclient.setResponse(s);
    }
    
    //The main update loop of the game. Called by the game panel
    public void update(){
        aiHandle.update();
        myGameMap.update();
        camControl.update();
    }
    
    //called when a player disconnects. Current data are all sent to the server
    //where it's archived into the database.
    public void disconnect(){ 
        Spatial playerSpat = boundSpat;
        PlayerData saveData = new PlayerData(boundAccountId,-1,playerSpat.getLocation(),myGameMap.getName());
        netSender.sendMessage(new SaveMessage(saveData));
    }
    //Called whenever a portal is entered.
    //Makes sure that the player is currently on a portal before attempting
    //to follow through with a warp
    public void enterPortal(){
        Portal curPort = CharacterHandler.getCurrentPortal();
        if (curPort != null)
            enterPortal(curPort);
    }
    //Overloaded enter portal. This class actually does the entering.
    //Takes in a portal and swaps map, camera control as well as background
    //music.
    //Note: the previous map "freezes" when the player exits and "resumes"
    //when the player enters again. It's meant to be that way :3
    private void enterPortal(Portal port){
        //Clear old map's previous messages
        AbstractControl hold = (AbstractControl)boundSpat.getControl(SteveyKeyListener.class);
        boundSpat.removeControl(hold); // hold the current input object (lest a player spam enter)
        myGameMap.removeSpatial(boundSpat);
        boundSpat.setLocation(port.getNewPos());
        myGameMap = AssetManager.getMap(port.getToMap());
        myGameMap.addSpatial(boundSpat);
        thegame.setMap(myGameMap);
        camControl.bindToCamera(myGameMap.getCamera());
        SoundManager.getChannel("BackgroundMusic").stopAll();
        SoundManager.getChannel("BackgroundMusic").addTrack(myGameMap.getBGMusic());
        boundSpat.addControl(hold);
    }
    //Not exactly used atm. At sound point in time was used to implement a mouse
    //click into the actual game.
    public void clickMap(int x,int y){
        myGameMap.getSpace().grabSpatialsAround(theMouse);
//        myGameMap.getSpace().grabSpatialsAround();
    }
}