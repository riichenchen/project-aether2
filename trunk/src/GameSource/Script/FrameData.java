/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Script;

/**
 * @author Shiyang
 * the frame data class is used as a container by the scriptframe and
 * framecase classes to store information.
 */
public class FrameData {
    public String dataType; //what type of information is this
    public String[] values; //the values that correspond to this type of information
    public FrameData(String dataType,String[] values){
        this.dataType = dataType;
        this.values = values;
    }
}
