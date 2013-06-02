/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Renderer.CameraControls;

import Renderer.AetherCam;
import Spatial.Spatial;

/**
 *
 * @author Shiyang
 */
public abstract class AbstractCameraControl {
    protected Spatial boundSpat;
    protected AetherCam camera;
    
    public AbstractCameraControl() {
    }
    
    public void bindToSpatial(Spatial spat){
        this.boundSpat = spat;
    }
    public void unbindFromSpatial(){
        this.boundSpat = null;
    }
    
    public void bindToCamera(AetherCam camera){
        this.camera = camera;
    }
    public void unbindFromCamera(){
        this.camera = null;
    }
    public void update(){
        if (camera != null && boundSpat != null){
            Update();
        }
    }
    public abstract void Update();
    
}
