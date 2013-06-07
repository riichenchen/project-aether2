package GameSource.game;

import GameSource.Assets.MobData.AbstractMob;
import GameSource.Assets.Spawners.AbstractMobSpawner;
import GameSource.Assets.TerrainBlocks.Blocks.otherblock.Other_Block;
import GameSource.Game.GamePoint;
import GameSource.Globals;
import GameSource.User.CharacterHandler;
import PhysicsSpace.PhysicsSpace;
import Renderer.AetherCam;
import Renderer.RenderSpatial;
import Renderer.Renderer;
import Spatial.Spatial;
import Testing.MyTestCharacter;
import Testing.PlayerSpatial;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JPanel;

public class GameMap {
        protected int mobCounter = 0;
        protected int mobLimit;
        protected int dimx,dimy;        
        protected HashMap<Integer,Spatial> spats;
        protected HashMap<Integer,Spatial> nonPermaSpats;
        //Only client maps can render. This saves space and allows
        //the server to use this class as well
        protected Renderer renderer = null;
        protected AetherCam camera = null;
        protected boolean canRender = false;
        protected String mapName;
        protected PhysicsSpace space;
        private String bgMusic;
        
        public static final int Char_TESTBLOCK = 0,Char_TESTANIM = 1,Char_STEVEY = 4;
                
        public GameMap(String mapName, int mobLimit,int dimx,int dimy,int camlen,int camwid,boolean canRender) {
            this.dimx = dimx;
            this.dimy = dimy;
            this.mobLimit = mobLimit;
            spats = new HashMap<>();
            this.mapName = mapName;
            this.nonPermaSpats = new HashMap<>();
            this.space = new PhysicsSpace(9.81f,dimx,dimy);
            //Clean this up later maybe
            if (canRender){
                this.canRender = true;
                camera = new AetherCam(this,camlen,camwid);
                renderer = new Renderer(this,camera);
            }
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

        //Handles attaching and removing of spatials
        public void addSpatial(Spatial spat){
            clearMessages();
            spat.bindToMap(this);
            spats.put(spat.getId(), spat);
            nonPermaSpats.put(spat.getId(), spat);
            space.addPlayerSpatial(spat);
            
            if (!verifyRender())
                return;
            if (spat instanceof RenderSpatial){
                renderer.addSpatial((RenderSpatial)spat);
            }
        }
        
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
        
        public void revealSpatial(Spatial spat){
            if (!verifyRender())
                return;
            if (spat instanceof RenderSpatial){
                renderer.addSpatial((RenderSpatial)spat);
            } else {
                System.out.println("Warning: Trying to reveal non-renderable Spaial!");
            }
        }
        
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
        public void clearMessages(){
            space.clearMessages();
            renderer.clearMessages();
        }
        

        public int getDimX(){
            return dimx;
        }
        public int getDimY(){
            return dimy;
        }
        
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
        public Spatial getSpatial(int id){
            return spats.get(id);
        }
        
        public Spatial addPlayer(int characterType,GamePoint loc){
            Spatial newChar = null;
            if (characterType == Char_TESTBLOCK){
                newChar = new Other_Block(loc.getX(),loc.getY(),loc.getZ());
            } else if (characterType == Char_TESTANIM){
                newChar = new MyTestCharacter(loc.getX(),loc.getY(),loc.getZ());
            } else if (characterType == Char_STEVEY){
                newChar = new PlayerSpatial(loc.getX(),loc.getY(),loc.getZ());
            }
            if (newChar == null){
                System.out.println("SEVERE: UNABLE TO LOAD CHARACTER!");
                System.exit(0);
            }
            addSpatial(newChar);
            return newChar;
        }
        
        public HashMap<Integer,Spatial> getNonPermanents(){
            return nonPermaSpats;
        }
        public PhysicsSpace getSpace(){
            return space;
        }
        public void setBGMusic(String musicKey){
            this.bgMusic = musicKey;
        }
        public String getBGMusic(){
            return bgMusic;
        }
        public void spawnMob(AbstractMobSpawner spawner){
            if (mobCounter < mobLimit){
                this.addSpatial(spawner.getMob());
                mobCounter++;
            }
        }
        public void removeMob(AbstractMob spat){
            removeSpatial(spat);
            mobCounter--;
        }
}
