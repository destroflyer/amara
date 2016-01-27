/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.shared.maps;

/**
 *
 * @author Carl
 */
public class MapSpell{

    public MapSpell(String entityTemplate){
        this.entityTemplate = entityTemplate;
    }
    private String entityTemplate;

    public String getEntityTemplate(){
        return entityTemplate;
    }
}
