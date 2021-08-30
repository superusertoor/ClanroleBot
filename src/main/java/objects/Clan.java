package objects;

import com.google.gson.*;
import handler.RoleHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Clan {

    private String clanName;
    private String clanTag;
    private JsonObject jsonClan = new JsonObject();
    private JsonArray jsonMembers = new JsonArray();
    private JsonParser jsonParser = new JsonParser();
    private File file;
    private Guild guild;
    private Role role;

    public Clan(Guild guild, String clanName, String clanTag) {
        this.guild = guild;
        this.clanName = clanName;
        this.clanTag = clanTag;
        this.file = new File("Clans/" + clanName);
    }

    public Clan(Guild guild, String clanName) {
        this.file = new File("Clans/" + clanName);
        this.clanName = clanName;
        try {
            FileReader fw = new FileReader(file.getPath());
            JsonElement jsonElement = jsonParser.parse(fw);
            this.jsonClan = jsonElement.getAsJsonObject();
            this.guild = guild;
            this.clanTag = jsonClan.get("clanTag").getAsString();
            this.jsonMembers = jsonClan.get("members").getAsJsonArray();
            this.role = RoleHandler.getRole(clanName + " [" + clanTag + "]", guild);
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public boolean containsMember(Member member) {
        for(int i=0;i<jsonMembers.size();i++) {
            if(member.getId().equals(jsonMembers.get(i).getAsString())) {
               return true;
            }
        }
        return false;
    }

    public Clan addMember(Member member) {
        this.jsonMembers.add(member.getId());
        guild.addRoleToMember(member, role).queue();
        refresh();
        return this;
    }

    public Clan setClanName(String clanName) {
        File f = new File("Clans/" + clanName);
        this.role.getManager().setName(clanName + " [" + clanTag + "]").queue();
        this.file.renameTo(f);
        this.clanName = clanName;
        jsonClan.addProperty("clanName", clanName);
        refresh();
        return this;
    }

    public JsonObject properties() {
        return jsonClan;
    }

    public Clan setClanTag(String clanTag) {
        this.role.getManager().setName(clanName + " [" + clanTag + "]").queue();
        this.clanTag = clanTag;
        jsonClan.addProperty("clanTag", clanTag);
        refresh();
        return this;
    }

    public Role getRole() {
        return role;
    }

    public Clan removeMember(Member member) {
        for(int i=0;i<jsonMembers.size();i++) {
            if(member.getId().equals(jsonMembers.get(i).getAsString())) {
                this.jsonMembers.remove(i);
                guild.removeRoleFromMember(member, role).queue();
            }
        }
        refresh();
        return this;
    }

    public String getClanName() {
        return clanName;
    }

    public String getClanTag() {
        return clanTag;
    }

    public List<Member> getMembersList() {
        List<Member> membersList = new ArrayList<>();
        for (Iterator<JsonElement> it = jsonMembers.iterator(); it.hasNext(); ) {
            membersList.add(guild.getMemberById(it.next().getAsString()));
        }
        return membersList;
    }

    public Clan delete() {
        file.delete();
        role.delete().queue();
        return this;
    }

    public Clan createClan() {
        jsonClan.addProperty("clanName", clanName);
        jsonClan.addProperty("clanTag", clanTag);
        jsonClan.addProperty("guildId", guild.getId());
        jsonClan.add("members", jsonMembers);
        role = RoleHandler.createRole(clanName + " [" + clanTag + "]", guild);
        refresh();
        return this;
    }

    private void refresh() {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            //Files.write(Paths.get(file.getPath()), gson.toJson(jsonClan).getBytes());
            FileWriter fw = new FileWriter("Clans/" + clanName);
            fw.write(gson.toJson(jsonClan));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}