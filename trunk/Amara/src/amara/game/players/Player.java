/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.players;

/**
 *
 * @author Carl
 */
public class Player{

    public Player(int id, String login){
        this.id = id;
        this.login = login;
    }
    private int id;
    private String login;

    public int getID(){
        return id;
    }

    public String getLogin(){
        return login;
    }
}
