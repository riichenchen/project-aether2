package GameSource.game;

import ArtificialIntelligence.Pathfinding;
import GameSource.Assets.MobData.AbstractMob;
import GameSource.Assets.Spawners.AbstractMobSpawner;
import GameSource.Game.GamePoint;
import GameSource.Globals;
import GameSource.User.CharacterHandler;
import PhysicsSpace.PhysicsSpace;
import Renderer.AetherCam;
import Renderer.RenderSpatial;
import Renderer.Renderer;
import Spatial.Spatial;
import Testing.PlayerSpatial;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JPanel;
/* GameMap
 * @author Shiyang Han
 * 
 * The main map class that handles all spatials - effects, casts, mobs, etc.
 * Literally handles almost everything in-game logic related.
 */
public class GameMap {
    protected int mobCounter = 0;
    protected int mobLimit; // the mob limit of this map
    protected int dimx,dimy; //dimensions
    protected HashMap<Integer,Spatial> spats; //separate permanent and non permanents for ease of clearing the map of animate objects
    protected HashMap<Integer,Spatial> nonPermaSpats;
    //Only client maps can render. This saves space and allows
    //the server to use this class as well if required
    //(In this case, not exactly required)
    protected Renderer renderer = null;
    protected AetherCam camera = null;
    protected boolean canRender = false;
    protected String mapName; //very handy to store a key (for easy assetmanager reference)
    protected PhysicsSpace space;
    protected Rectangle[] obstacles;
    protected char[][] charMap;
    private String bgMusic; // key for bgm

    //Constructor: initializes and sets everything
    public GameMap(String mapName, int mobLimit,int dimx,int dimy,int camlen,int camwid,boolean canRender) {
        this.dimx = dimx; 
        this.dimy = dimy;
        this.mobLimit = mobLimit;
        spats = new HashMap<>();
        this.mapName = mapName;
        this.nonPermaSpats = new HashMap<>();
        this.space = new PhysicsSpace(9.81f,dimx,dimy); //note: gravity isn't exactly used at the point
        //Clean this up later maybe
        if (canRender){
            this.canRender = true;
            camera = new AetherCam(this,camlen,camwid);
            renderer = new Renderer(this,camera); // create a renderer for this map
        }
        obstacles = new Rectangle[1];
        obstacles[0] = new Rectangle(0, 0, 2, 2);
    }

    //returns a handle to the camera for the game to control if needed
    public AetherCam getCamera(){
        return camera;
    }
    //Verifies the existance of a renderer before allowing
    //a call to it
    private boolean verifyRender(){
        if (renderer == null && canRender){
            if (Globals.RENDER_DEBUG)
                System.out.println("Warning: No renderer set to map. Unable to attach spatial to render space.");
            return false;
        }
        return true;
    }

    //standard addspat method, spatials are added into the physics space as well
    public void addSpatial(Spatial spat){
        clearMessages();//to preserve synchronization, messages are cleared from all spaces
        spat.bindToMap(this); //bind spatial to this map
        spats.put(spat.getId(), spat); //add the spatial to all containers
        nonPermaSpats.put(spat.getId(), spat);
        space.addPlayerSpatial(spat);

        if (!verifyRender()) // verify that spatial is renderable
            return;
        if (spat instanceof RenderSpatial){
            renderer.addSpatial((RenderSpatial)spat);
        }
    }
    
    //Handles attaching of spatials to the background
    //Spatials added here are also added to the physicsspace
    public void addPermanentSpatial(Spatial spat){
        clearMessages();
        spat.bindToMap(this);
        spats.put(spat.getId(), spat);
        space.addEnviroSpatial(spat);

        if (!verifyRender())
            return;
        if (spat instanceof RenderSpatial){
            renderer.addSpatial((RenderSpatial)spat);
        }
    }

    //Special add method; adds spatials to then render map only
    //So no operations are wasted on a spatial that'l never have
    //a collision
    public void addBackgroundSpatial(Spatial spat){
        clearMessages();
        spats.put(spat.getId(), spat);
        spat.bindToMap(this);

        if (!verifyRender())
            return;
        if (spat instanceof RenderSpatial){
            renderer.addSpatial((RenderSpatial)spat);
        }
    }
    //Render method. Calls upon the renderer's render method
    public void render(Graphics g,JPanel pane){
        if (!verifyRender())
            return;
        renderer.render(g, pane);
    }

    //Only hides the spatial from the renderer, spatial still behaves
    //and moves normally. (Not sure if will be used)
    public void hideSpatial(Spatial spat){
        if (!verifyRender())
            return;
        if (spat instanceof RenderSpatial){
            renderer.removeSpatial((RenderSpatial)spat);
        } else {
            System.out.println("Warning: Trying to hide non-renderable Spatial!");
        }
    }
    
    //Reveals a hidden spatial.
    public void revealSpatial(Spatial spat){
        if (!verifyRender())
            return;
        if (spat instanceof RenderSpatial){
            renderer.addSpatial((RenderSpatial)spat);
        } else {
            System.out.println("Warning: Trying to reveal non-renderable Spaial!");
        }
    }

    //Removes a spatial from the gamemap and space.
    //Permanent spats are an exception
    public void removeSpatial(Spatial spat){
        clearMessages();
        spats.remove(spat.getId());
        nonPermaSpats.remove(spat.getId());
        space.removeSpatial(spat);
        //Remove from render space if the spatial is in the render space
        if (!verifyRender())
            return;
        if (spat instanceof RenderSpatial){
            renderer.removeSpatial((RenderSpatial)spat);
        }
        spat.unbindFromMap();
    }
    
    //Special remove spatial method.
    //Assumes spatial to be removed isn't bound to physics space
    public void removeBackgroundSpatial(Spatial spat){
        clearMessages();
        spats.remove(spat.getId());
        nonPermaSpats.remove(spat.getId());
        //Remove from render space if the spatial is in the render space
        if (!verifyRender())
            return;
        if (spat instanceof RenderSpatial){
            renderer.removeSpatial((RenderSpatial)spat);
        }
        spat.unbindFromMap();
    }
    
    //Clear messages clears all current messages going on
    //used to synchronize (and prevent concurrent exceptions)
    public void clearMessages(){
        space.clearMessages();
        renderer.clearMessages();
    }

    //Standard get methods
    public int getDimX(){
        return dimx;
    }
    public int getDimY(){
        return dimy;
    }

    //Main update method. Updates all controls, collisions, and the renderer
    public void update(){
        Spatial[] spatList = spats.values().toArray(new Spatial[0]);
        for (Spatial s: spatList){
            s.update();
        }
        CharacterHandler.clearCollideItems();
        space.update();
        if (!verifyRender())
            return;
        renderer.update();
    }

    public String getName(){
        return mapName;
    }
    //Returns a spatial with the id passed in
    public Spatial getSpatial(int id){
        return spats.get(id);
    }

    //Spatial passed in is assumed to be the player. So a "sola" character
    //is added
    public PlayerSpatial addPlayer(GamePoint loc){
        PlayerSpatial newChar = null;
        newChar = new PlayerSpatial(loc.getX(),loc.getY(),loc.getZ());
        addSpatial(newChar);
        return newChar;
    }
    
    //Returns all non-permanent spatials in this map
    public HashMap<Integer,Spatial> getNonPermanents(){
        return nonPermaSpats;
    }
    //Standard get space method
    public PhysicsSpace getSpace(){
        return space;
    }
    //Used by the AssetManager to set a musicKey
    public void setBGMusic(String musicKey){
        this.bgMusic = musicKey;
    }
    //Standard get BGMusic method
    public String getBGMusic(){
        return bgMusic;
    }
    //This method checks for whether a mob can be spawned before generating and
    //adding a mob to the map using the provided spawner
    //Note: As abstract mob initializes an AI control and ai controls are 
    //handled by the ai thread, it's crucial that no new instances are made
    //and then thrown away. (Otherwise the handler may result in "ghost" controls
    public void spawnMob(AbstractMobSpawner spawner){
        if (mobCounter < mobLimit){
            this.addSpatial(spawner.getMob());
            mobCounter++;
        }
    }
    
    //Removes a given mob
    public void removeMob(AbstractMob spat){
        removeSpatial(spat);
        mobCounter--;
    }
    
    //gets mob count
    public int getMobCount(){
        return mobCounter;
    }

    //Methods for AI
    public char [][] getCharMap() {
        if (charMap == null) {
            charMap = new char[dimx / Pathfinding.K][dimy / Pathfinding.K];
		for (int i = 0; i < dimx / Pathfinding.K; i++)
			for (int j = 0; j < dimy / Pathfinding.K; j++)
					charMap[i][j] = 0;
		for (int i = 0; i < obstacles.length; i++) {
				int curHeight = (int)obstacles[i].getHeight() / Pathfinding.K;
				int curWidth = (int)obstacles[i].getWidth() / Pathfinding.K;
				int curX = (int)obstacles[i].getX() / Pathfinding.K;
				int curY = (int)obstacles[i].getY() / Pathfinding.K;
				for (int j = 0; j < curHeight; j++)
					for (int k = 0; k < curWidth; k++)
						charMap[curX + j][curY + k] = 1;
		}
        }
        return charMap;
    }

    public ArrayList getSpatials() {
        ArrayList mySpats = new ArrayList<Spatial>();
        for (Map.Entry<Integer, Spatial> entry: spats.entrySet()) {
            Spatial curSpat = entry.getValue();
            mySpats.add(curSpat);
        }
        return mySpats;
    }

    public PhysicsSpace getPhysicsSpace() {
        return space;
    }
    
    public Rectangle [] getObstacles() {
        return obstacles;
    }
    
}

