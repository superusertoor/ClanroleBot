package commands;

import handler.FileHandler;
import handler.RoleHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import objects.Clan;
import objects.ClanEmbed;
import objects.ClanHandler;

public class RoleCommands extends ListenerAdapter {

    private Clan clan;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        final Message message = event.getMessage();
        final String display = message.getContentDisplay();
        final Guild guild = event.getGuild();
        final TextChannel textChannel = event.getTextChannel();
        final Member member = event.getMember();

        final String[] args = message.getContentDisplay().split(" ");
        if(display.equalsIgnoreCase("!clan")) {
            new ClanEmbed().help().send(textChannel);
            return;
        }
        if (display.startsWith("!clan create ")) {
            if (args.length == 4) {
                if(!FileHandler.clanFileExists(args[2])) {
                    Clan clan = new Clan(guild, args[2], args[3]).createClan();
                    new ClanEmbed().createClan(clan).send(textChannel);
                }else {
                    new ClanEmbed().error("Es existiert bereits ein Clan mit dem Namen **" + args[2] + "**").send(textChannel);
                }
            }
        }if (display.startsWith("!clan move ")) {
            if (args.length == 4) {
                if(FileHandler.clanFileExists(args[2])) {
                    if(FileHandler.clanFileExists(args[3])) {
                        clan = new Clan(guild, args[2]);
                        Clan clan2 = new Clan(guild, args[3]);
                        RoleHandler.moveRole(clan, clan2, guild);
                        new ClanEmbed().moveClan(clan, clan2).send(textChannel);
                    }else {
                        new ClanEmbed().error("Es wurde kein Clan mit dem Namen **" + args[3] + "** gefunden").send(textChannel);
                    }
                }else {
                    new ClanEmbed().error("Es wurde kein Clan mit dem Namen **" + args[2] + "** gefunden").send(textChannel);
                }
            }
        } if (display.startsWith("!clan delete ")) {
            if (args.length == 3) {
                if (FileHandler.clanFileExists(args[2])) {
                    Clan clan = new Clan(guild, args[2]).delete();
                    new ClanEmbed().deleteClan(clan).send(textChannel);
                } else {
                    new ClanEmbed().error("Es wurde kein Clan mit dem Namen **" + args[2] + "** gefunden").send(textChannel);
                }
            }
        }else if (display.startsWith("!clan settag ")) {
            if (args.length == 4) {
                if (FileHandler.clanFileExists(args[2])) {
                    if (ClanHandler.getClansWith(guild, "clanTag", args[3]).size() == 0) {
                        if (ClanHandler.getClansWith(guild, "clanName", args[2]).size() == 0) {
                            ClanHandler.getClans(guild).forEach(clan -> {
                                if (clan.getClanName().equalsIgnoreCase(args[2])) {
                                    String oldTag = clan.getClanTag();
                                    clan.setClanTag(args[3]);
                                    new ClanEmbed().changedTag(clan, member, oldTag).send(textChannel);
                                }
                            });
                        } else {
                            new ClanEmbed().error("Es wurde kein Clan mit dem Namen **" + args[2] + "** gefunden").send(textChannel);
                        }
                    } else {
                        new ClanEmbed().error("Es existiert bereits ein Clan mit dem Clantag **" + args[3] + "**").send(textChannel);
                    }
                }else {
                    new ClanEmbed().error("Es wurde kein Clan mit dem Namen **" + args[2] + "** gefunden").send(textChannel);
                }
            }
        }else if (display.startsWith("!clan setname ")) {
            if (args.length == 4) {
                if (FileHandler.clanFileExists(args[2])) {
                    if (!FileHandler.clanFileExists(args[3])) {
                        Clan clan = new Clan(guild, args[2]);
                        String oldName = clan.getClanName();
                        clan.setClanName(args[3]);
                        new ClanEmbed().changedName(clan, member, oldName).send(textChannel);
                    } else {
                        new ClanEmbed().error("Es existiert bereits ein Clan mit dem Namen **" + args[3] + "**").send(textChannel);
                    }
                } else {
                    new ClanEmbed().error("Es wurde kein Clan mit dem Namen **" + args[2] + "** gefunden").send(textChannel);
                }
            }
        }else if(display.startsWith("!clan fight ")) {
            if(args.length == 4) {
                message.delete().queue();
                final String clan1 = args[2];
                final String clan2 = args[3];
                new ClanEmbed().fight(clan1, clan2).send(textChannel);
            }
        }else if(display.startsWith("!clan ")) {
            if(args.length == 2) {
                if(FileHandler.clanFileExists(args[1])) {
                    Clan clan = new Clan(guild, args[1]);
                    if (!clan.containsMember(member)) {
                        clan.addMember(event.getMember());
                        new ClanEmbed().memberAdded(clan, member).send(textChannel);
                    }else {
                        clan.removeMember(event.getMember());
                        new ClanEmbed().memberRemoved(clan, member).send(textChannel);
                    }
                }else {
                    new ClanEmbed().error("Es wurde kein Clan mit dem Namen **" + args[1] + "** gefunden").send(textChannel);
                }
            }
        }
    }
}