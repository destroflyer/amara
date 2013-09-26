/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.physics;

/**
 *
 * @author Philipp
 */
public class IntersectionFilterComponent
{
    private long collisionGroups;
    private long collidesWithGroups;
    
    public static final long COLLISION_GROUP_NONE = 0x0;
    public static final long COLLISION_GROUP_MAP = 0x1;
    public static final long COLLISION_GROUP_2 = 0x2;
    public static final long COLLISION_GROUP_3 = 0x4;
    public static final long COLLISION_GROUP_4 = 0x8;
    public static final long COLLISION_GROUP_5 = 0x10;
    public static final long COLLISION_GROUP_6 = 0x20;
    public static final long COLLISION_GROUP_ALL = 0xffffffffffffffffL;
    
    public static boolean groupsCollide(long a, long b)
    {
        return (a & b) != 0;
    }

    public IntersectionFilterComponent(long collisionGroups, long collidesWithGroups)
    {
        this.collisionGroups = collisionGroups;
        this.collidesWithGroups = collidesWithGroups;
    }

    public long getCollisionGroups()
    {
        return collisionGroups;
    }

    public long getCollidesWithGroups()
    {
        return collidesWithGroups;
    }
    
}
