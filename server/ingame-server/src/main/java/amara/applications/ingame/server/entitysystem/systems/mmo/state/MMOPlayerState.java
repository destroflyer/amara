package amara.applications.ingame.server.entitysystem.systems.mmo.state;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MMOPlayerState {
    private MMOVectorState2f position;
    private MMOVectorState2f direction;
    private MMOItemState[] inventory;
    private MMOItemState[] bag;
    private Float gold;
}
