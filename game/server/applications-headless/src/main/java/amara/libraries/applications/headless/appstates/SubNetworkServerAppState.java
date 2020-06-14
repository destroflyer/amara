/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.headless.appstates;

import amara.libraries.network.SubNetworkServer;

/**
 *
 * @author Carl
 */
public class SubNetworkServerAppState extends BaseHeadlessAppState {

    public SubNetworkServerAppState(SubNetworkServer subNetworkServer) {
        this.subNetworkServer = subNetworkServer;
    }
    private SubNetworkServer subNetworkServer;

    public SubNetworkServer getSubNetworkServer() {
        return subNetworkServer;
    }
}
