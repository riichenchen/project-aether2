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
 * The AbstractCameraControl class provides from a skeleton that all camera
 * controls can build off of. The camera control can bind to spatial and then
 * have the bound camera follow the spatial
 */
public abstract class AbstractCameraControl {
    protected Spatial boundSpat;
    protected AetherCam camera;
    
    public AbstractCameraControl() {
    }
    //Standard bind and unbind methods
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
    //Each cam control should have it's own way to update
    public abstract void Update();
    
}
