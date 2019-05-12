/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.core.encoding;

/**
 *
 * @author Carl
 */
public abstract class Encoder{
    
    public abstract String encode(String text);
    
    public abstract String decode(String text);
}
