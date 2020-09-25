package amara.applications.master.client.network.backends;

import amara.applications.master.client.appstates.MapsAppState;
import amara.applications.master.network.messages.Message_AvailableMaps;
import amara.libraries.network.MessageBackend;
import amara.libraries.network.MessageResponse;
import com.jme3.network.Message;

public class ReceiveAvailableMapsBackend implements MessageBackend {

    public ReceiveAvailableMapsBackend(MapsAppState mapsAppState) {
        this.mapsAppState = mapsAppState;
    }
    private MapsAppState mapsAppState;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if (receivedMessage instanceof Message_AvailableMaps) {
            Message_AvailableMaps message = (Message_AvailableMaps) receivedMessage;
            mapsAppState.setMapNames(message.getMapNames());
        }
    }
}
