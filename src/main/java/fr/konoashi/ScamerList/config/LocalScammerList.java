package fr.konoashi.ScamerList.config;

import fr.konoashi.ScamerList.utils.References;
import net.minecraft.util.EnumChatFormatting;

import java.util.Iterator;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.File;
import java.util.List;

public class LocalScammerList
{
    private final List<String> loaded_scammers;
    private final File file;

    public LocalScammerList(final String filepath) {
        this.loaded_scammers = new ArrayList<String>();
        this.file = new File(filepath);
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            }
            catch (Exception ex) {
                System.out.printf("Error creating local scammer list: %s\n", ex.getMessage());
            }
            return;
        }
        this.load_list();
    }

    private void load_list() {
        String line = null;
        try {
            final FileReader fr = new FileReader(this.file);
            final BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.length() != 0) {
                    if (line.startsWith("#")) {
                        continue;
                    }
                    if (line.length() == 32) {
                        this.loaded_scammers.add(line);
                        System.out.printf("Loaded scammer %s\n", line);
                    }
                    else {
                        System.out.printf("Ignored erroneous line %s\n", line);
                    }
                }
            }
            fr.close();
            this.save_list(this.file);
            System.out.println("Loaded local scammer list");
        }
        catch (Exception ex) {
            System.out.printf("Error loading local scammer list on line `%s`: %s\n", line, ex.getMessage());
            this.save_list(this.file);
        }
    }

    public boolean contains(final String uuid) {
        return this.loaded_scammers.contains(uuid);
    }

    public String try_add(final String uuid, final String username) {
        if (this.contains(uuid)) {
            String text = EnumChatFormatting.GOLD + "\u26a0 " + References.ScammListBrand + " " + username + " is already on your LocalScammerList";
            return String.format(text, username);
        }
        this.loaded_scammers.add(uuid);
        String text = EnumChatFormatting.GREEN + "\u2714 " + References.ScammListBrand + "" + username + " was successfully added on your LocalScammerList";
        return this.save_list(this.file) ? String.format(text, username) : String.format("Error", username);
    }

    public String try_remove(final String uuid, final String username) {
        if (!this.contains(uuid)) {
            String text = EnumChatFormatting.GOLD + "\u26a0 " + References.ScammListBrand + "" + username + " isn't on your LocalScammerList";
            return String.format(text, username);
        }
        this.loaded_scammers.remove(uuid);
        String text = EnumChatFormatting.GREEN + "\u2714 " + References.ScammListBrand + "You successfully removed " + username + " from the LocalScammerList";
        return this.save_list(this.file) ? String.format(text, username) : String.format("Error", username);
    }

    private boolean save_list(final File file) {
        try {
            final PrintWriter writer = new PrintWriter(file, "UTF-8");
            writer.println("# DO NOT MODIFY THIS FILE");
            writer.println();
            for (final String uuid : this.loaded_scammers) {
                writer.println(uuid);
            }
            writer.close();
            System.out.println("Saved local scammer list");
            return true;
        }
        catch (Exception ex) {
            System.out.printf("Error saving local scammer list: %s\n", ex.getMessage());
            return false;
        }
    }
}
