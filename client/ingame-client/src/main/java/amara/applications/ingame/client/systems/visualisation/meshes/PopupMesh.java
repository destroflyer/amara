/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation.meshes;

/**
 *
 * @author Carl
 */
public class PopupMesh extends RectangleMesh{
    
    public PopupMesh(int rows){
        super(-125, 0, 0, 250, 50 + ((rows - 1) * (50f / 3)));
    }
}
