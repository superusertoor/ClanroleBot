package objects;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class ClanEmbed {

    private EmbedBuilder embedBuilder = new EmbedBuilder().setColor(Color.PINK).setFooter("FFA RoleBot");

    public ClanEmbed deleteClan(Clan clan) {
        embedBuilder.setDescription("> Clan gelöscht");
        embedBuilder.addField("Clan", "```" + clan.getClanName() + " [" + clan.getClanTag() + "]" + "```", true);
        return this;
    }

    public ClanEmbed memberRemoved(Clan clan, Member member) {
        embedBuilder.setDescription("> **Clanrolle entfernt**");
        embedBuilder.addField("Clanname", "```" + clan.getClanName() + " [" + clan.getClanTag() + "]" + "```", true);
        embedBuilder.addField("Member", "```" + member.getUser().getAsTag() + "```", true);
        return this;
    }

    public ClanEmbed changedTag(Clan clan, Member member, String oldTag) {
        embedBuilder.setDescription("> **Clan umbenannt**");
        embedBuilder.addField("Alter Clantag", "```" + clan.getClanName() + " [" + oldTag + "]" + "```", true);
        embedBuilder.addField("Neuer Clantag", "```" + clan.getClanName() + " [" + clan.getClanTag() + "]" + "```", true);
        embedBuilder.addField("Member", "```" + member.getUser().getAsTag() + "```", true);
        return this;
    }

    public ClanEmbed changedName(Clan clan, Member member, String oldName) {
        embedBuilder.setDescription("> **Clan umbenannt**");
        embedBuilder.addField("Alter Clanname", "```" + oldName + " [" + clan.getClanTag() + "]" + "```", true);
        embedBuilder.addField("Neuer Clanname", "```" + clan.getClanName() + " [" + clan.getClanTag() + "]" + "```", true);
        embedBuilder.addField("Member", "```" + member.getUser().getAsTag() + "```", true);
        return this;
    }

    public ClanEmbed memberAdded(Clan clan, Member member) {
        embedBuilder.setDescription("> **Clanrolle hinzugefügt**");
        embedBuilder.addField("Clan", "```" + clan.getClanName() + " [" + clan.getClanTag() + "]" + "```", true);
        embedBuilder.addField("Member", "```" + member.getUser().getAsTag() + "```", true);
        return this;
    }

    public ClanEmbed moveClan(Clan clan, Clan clan1) {
        embedBuilder.setDescription("> **Clan in Hierarchie verschoben**");
        embedBuilder.addField("Verschoben", "```" + clan.getClanName() + " [" + clan.getClanTag() + "]" + "```", true);
        embedBuilder.addField("Verschoben über", "```" + clan1.getClanName() + " [" + clan1.getClanTag() + "]" + "```", true);
        return this;
    }

    public ClanEmbed error(String error) {
        embedBuilder.setDescription("> **Ein Fehler ist aufgetreten.**\n\n**Fehler**: " + error);
        embedBuilder.setColor(Color.RED);
        return this;
    }

    public ClanEmbed fight(String clan1, String clan2) {
        embedBuilder.setDescription("> **Clan Kampf**");
        embedBuilder.addField("Clan", "```" + clan1+ "```", true);
        embedBuilder.addField("⠀", "```vs```", true);
        embedBuilder.addField("Clan", "```" + clan2+ "```",true);
        embedBuilder.setColor(Color.YELLOW);
        return this;
    }

    public ClanEmbed help() {
        embedBuilder.setDescription("> **Befehle**\n" +
                "\n**!Clan create** `Clanname` `Clantag` \n» Erstellt einen Clan\n" +
                "\n**!Clan setname** `Clanname` `Neuer Clanname` \n» Benennt einen Clan um\n" +
                "\n**!Clan settag** `Clanname` `Neuer Clantag` \n» Ändert den Clantag eines Clans\n" +
                "\n**!Clan fight** `Clanname1` `Clanname1` \n» Erstellt eine Übersicht eines Clanfights\n" +
                "\n**!Clan move** `Clanname` `Clanname` \n» Verändert die Hierarchie\n" +
                "\n**!Clan** `Clanname` \n» Clanrolle hinzufügen oder entfernen");
        return this;
    }

    public ClanEmbed createClan(Clan clan) {
        embedBuilder.setDescription("> **Der Clan wurde erstellt**");
        embedBuilder.addField("Clan", "```" + clan.getClanName() + " [" + clan.getClanTag() + "]" + "```", true);
        return this;
    }

    public ClanEmbed send(TextChannel textChannel) {
        textChannel.sendMessage(embedBuilder.build()).queue();
        return this;
    }
}