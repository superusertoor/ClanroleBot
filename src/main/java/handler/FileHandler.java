package handler;

import java.io.File;
import java.io.IOException;

public class FileHandler {

    static File clans = new File("Clans");

    static {
        if(!clans.exists()) {
            clans.mkdir();
        }
    }

    public static boolean clanFileExists(String name) {
        return new File(clans.getPath() + "/" + name.toLowerCase()).exists();
    }

    public static void createClanFile(String name) {
        try {
            new File(clans.getPath() + "/" + name).createNewFile();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void renameClanFile(String name) {
        deleteClanFile(name);
        createClanFile(name);
    }

    public static void deleteClanFile(String name) {
        new File(clans.getPath() + "/" + name).delete();
    }
}