package bot;

import commands.RoleCommands;
import events.BotPingEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;

public class RoleBot {

    static String token = "";
    static JDA jda;

    public static void main(String[] agrgs) throws LoginException {
        jda = JDABuilder.createDefault(token)
                .addEventListeners(new RoleCommands())
                .addEventListeners(new BotPingEvent())
                .setChunkingFilter(ChunkingFilter.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .build();
    }

    public static JDA getJda() {
        return jda;
    }
}