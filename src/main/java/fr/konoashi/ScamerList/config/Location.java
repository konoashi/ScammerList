package fr.konoashi.ScamerList.config;

import fr.konoashi.ScamerList.utils.References;
import net.minecraft.util.EnumChatFormatting;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Location {
    private final List<String> location;
    private final File file;

    public Location(final String filepath) {
        this.location = new ArrayList<String>();
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

                    if (line.contains("x")||line.contains("y")) {

                        this.location.add(line);
                        System.out.printf("Loaded coordinates %s\n", line);
                    } else {
                        this.location.add("x: 500");
                        this.location.add("y: 500");
                        System.out.printf("Generated coordinates");
                    }


                    System.out.println("ERROR");

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
        return this.location.contains(uuid);
    }

    public String replace(final String x, final String y) throws IOException {

        String line = null;
        final FileReader fr = new FileReader(this.file);
        final BufferedReader br = new BufferedReader(fr);
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.length() != 0) {
                if (line.startsWith("#")) {
                    continue;
                }

                if (line.contains("x")) {
                    this.location.remove(line);
                    this.location.add(x);
                } else if (!line.contains("x")) {
                    this.location.add("x: 500");
                }
                if (line.contains("y")) {
                    this.location.remove(line);
                    this.location.add(y);
                } else if (!line.contains("y")) {
                    this.location.add("y: 500");
                }

            }
        }
        fr.close();

        String text = "good";
        return this.save_list(this.file) ? text : "Error";
    }

    public String try_remove(final String uuid, final String username) {
        if (!this.contains(uuid)) {
            String text = EnumChatFormatting.GOLD + "\u26a0 " + References.ScammListBrand + "" + username + " isn't on your LocalScammerList";
            return String.format(text, username);
        }
        this.location.remove(uuid);
        String text = EnumChatFormatting.GREEN + "\u2714 " + References.ScammListBrand + "You successfully removed " + username + " from the LocalScammerList";
        return this.save_list(this.file) ? String.format(text, username) : String.format("Error", username);
    }

    private boolean save_list(final File file) {
        try {
            final PrintWriter writer = new PrintWriter(file, "UTF-8");
            writer.println("# DO NOT MODIFY THIS FILE");
            writer.println();
            for (final String coords : this.location) {
                writer.println(coords);
            }
            writer.close();
            System.out.println("Saved location");
            return true;
        }
        catch (Exception ex) {
            System.out.printf("Error saving local scammer list: %s\n", ex.getMessage());
            return false;
        }
    }
}
