package handler;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import objects.Clan;

public class RoleHandler {

    public static boolean isClanRole(String role) {
        return FileHandler.clanFileExists(role);
    }

    public static boolean hasUserRole(String role, Member member) {
        return member.getRoles().contains(member.getGuild().getRolesByName(role, true).get(0));
    }

    public static void moveRole(Clan move, Clan above, Guild guild) {
        guild.modifyRolePositions().selectPosition(move.getRole()).moveTo(above.getRole().getPosition()).queue();
    }

    public static Role getRole(String name, Guild guild) {
        return guild.getRolesByName(name, true).get(0);
    }

    public static boolean roleExists(String roleName, Guild guild) {
        return guild.getRolesByName(roleName, true).size() >= 1;
    }

    public static Role createRole(String roleName, Guild guild) {
        return guild.createRole().setName(roleName).setHoisted(true).complete();
    }

    public static void deleteRole(String roleName, Guild guild) {
        guild.getRolesByName(roleName, true).get(0).delete().queue();
    }

    public static void removeRole(Role role, Member member) {
        member.getGuild().removeRoleFromMember(member, role).queue();
    }

    public static void addRole(Role role, Member member) {
        member.getGuild().addRoleToMember(member, role).queue();
    }

}