package amara.applications.ingame.network;

import com.jme3.network.serializing.Serializer;
import amara.applications.ingame.network.messages.*;
import amara.applications.ingame.network.messages.objects.commands.*;
import amara.applications.ingame.network.messages.objects.commands.casting.*;

public class MessagesSerializer_Ingame {

    public static void registerClasses() {
        Serializer.registerClasses(
            Message_Ping.class,
            Message_Pong.class,
            Message_SendChatMessage.class,
            Message_ChatMessage.class,
            Message_Command.class,
                AutoAttackCommand.class,
                BuyItemCommand.class,
                Command.class,
                DanceCommand.class,
                LearnSpellCommand.class,
                SellItemCommand.class,
                    ItemIndex.class,
                ShowReactionCommand.class,
                StopCommand.class,
                StopWalkInDirectionCommand.class,
                SwapItemsCommand.class,
                UpgradeSpellCommand.class,
                WalkInDirectionCommand.class,
                WalkToTargetCommand.class,
                    CastLinearSkillshotSpellCommand.class,
                    CastPositionalSkillshotSpellCommand.class,
                    CastSelfcastSpellCommand.class,
                    CastSingleTargetSpellCommand.class,
                    SpellIndex.class,
            Message_ClientInitialized.class,
            Message_ClientReady.class,
            Message_EntityChanges.class,
            Message_GameCrashed.class,
            Message_GameInfo.class,
            Message_GameStarted.class,
            Message_GameOver.class,
            Message_InitialEntityWorldSent.class,
            Message_JoinGame.class,
            Message_LeaveGame.class
        );
    }
}
