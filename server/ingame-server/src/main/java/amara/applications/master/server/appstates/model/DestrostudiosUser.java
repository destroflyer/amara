package amara.applications.master.server.appstates.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class DestrostudiosUser {
    private int id;
    private String login;
    private int[] ownedAppIds;
}
