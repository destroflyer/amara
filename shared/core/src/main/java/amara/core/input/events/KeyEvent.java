package amara.core.input.events;

import amara.core.input.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KeyEvent extends Event {
    private int keyCode;
    private boolean pressed;
    private boolean modifierControl;
    private boolean modifierShift;
}
