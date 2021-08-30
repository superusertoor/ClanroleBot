package events;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import objects.ClanEmbed;

public class BotPingEvent extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getMessage().getContentRaw().equalsIgnoreCase("<@!864982899354107959>")) {
            new ClanEmbed().help().send(event.getTextChannel());
        }
    }
}