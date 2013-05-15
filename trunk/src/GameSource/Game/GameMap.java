package GameSource.game;

import GameSource.Globals;
import Renderer.AetherCam;
import Renderer.RenderSpatial;
import Renderer.Renderer;
import Spatial.Spatial;
import java.awt.Graphics;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GameMap {
        
        protected ImageIcon mapImage;//To be used.
        protected double mobDensity; //????
        protected int dimx = 1600,dimy = 1200;        
        protected HashMap<Integer,Spatial> spats;
        //Only client maps can render. This saves space and allows
        //the server to use this class as well
        protected Renderer renderer = null;
        protected AetherCam camera = null;
        protected boolean canRender = false;
        protected String mapName;
        
        public GameMap(String mapName, double _mobDensity,int dimx,int dimy,int camlen,int camwid,boolean canRender) {
            //mapImage = new ImageIcon(mapName);
            mobDensity = _mobDensity;
            this.dimx = dimx;
            this.dimy = dimy;
            spats = new HashMap<>();
            this.mapName = mapName;
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
            spats.put(spat.getId(), spat);
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
            spats.remove(spat.getId());
            //Remove from render space if the spatial is in the render space
            if (!verifyRender())
                return;
            if (spat instanceof RenderSpatial){
                renderer.removeSpatial((RenderSpatial)spat);
            }
        }
        
        
        public double getMobDensity() {
            return mobDensity;
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
            if (!verifyRender())
                return;
            renderer.update();
        }
        public String getName(){
            return mapName;
        }
}
