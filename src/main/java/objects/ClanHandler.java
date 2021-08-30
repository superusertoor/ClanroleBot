package objects;

import net.dv8tion.jda.api.entities.Guild;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClanHandler {

    public static List<Clan> getClans(Guild guild) {
        List<Clan> list = new ArrayList<>();
        File clans = new File("Clans/");
        for(File clanFile : clans.listFiles()) {
            list.add(new Clan(guild, clanFile.getName()));
        }
        return list;
    }

    public static List<Clan> getClansWith(Guild guild, String key, String value) {
        List<Clan> list = new ArrayList<>();
        File clans = new File("Clans/");
        for(File clanFile : clans.listFiles()) {
            Clan clan = new Clan(guild, clanFile.getName());
            if(clan.properties().get(key).getAsString().equalsIgnoreCase(value)){
                list.add(clan);
            }
        }
        return list;
    }
}