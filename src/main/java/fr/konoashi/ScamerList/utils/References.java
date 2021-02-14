package fr.konoashi.ScamerList.utils;

import fr.konoashi.ScamerList.enums.*;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.StringUtils;
import net.minecraft.init.Blocks;

//import java.io.BufferedReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;


public class References {


    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)§[0-9A-FK-OR]");
    public static final String MODID = "scammerList";
    public static final String NAME = "ScammerList";
    public static final String VERSION = "1.0.4 PRE3";

    public static final String ScammListBrand = "[ScammerList] ";
    public static final String msg1 = "Request data from the database...";
    public static final String msg2 = "Waiting for data about ";
    public static final String msg3 = " isn't recognize as a scammer but stay safe and screen and if he scam you, please request to add the player in the list with proofs on this discord: ";
    public static final String msg4 = " is a recognized scammer in our database, you can't trade with him !";




    public static String GUIMSG1 = " isn't recognize as a scammer our database";
    public static String GUIMSG2 = "Stay safe and screen, you can report on the discord";

    public static String GUIMSG3 = " is a known scammer in our database";
    public static String GUIMSG4 = "Stay extremely safe, isn't recommended to trade with him";

    public static String GUIMSG5 = " is a known scammer only in our local database";
    public static String GUIMSG6 = "Stay safe and screen, hope you will prove he is a real scammer";

    public static String GUIMSG7 = " isn't recognize as minecraft player";
    public static String GUIMSG8 = "Stay safe and screen, you can report on the discord";

    public static String GUIMSG9 = " Please wait, the game is currently searching in the database the ";
    public static String GUIMSG10 = "Stay safe and screen, you can report on the discord";






    private static OnTrade ontrade;
    private static Scan scan;
    private static Scammer scammer;
    private static WaitingText waitingText;
    private static WhereMsg where;


    public static String mainurl(String url) throws IOException {


        System.out.println("GET DONE");
        return sendGET(url);


    }

    private static String sendGET(String GET_URL) throws IOException {
        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();


    }

    public static String stripColor(final String input) {
        return STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")

    static void saveConfigFile(File configFile, String toSave) {
        try {
            if (!configFile.isFile()) {
                configFile.createNewFile();
            }
            Files.write(Paths.get(configFile.getAbsolutePath()),
                    toSave.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean on_skyblock() {
        if (Minecraft.getMinecraft().theWorld == null) {
            return false;
        }
        final Scoreboard scoreboard = Minecraft.getMinecraft().theWorld.getScoreboard();

        final ScoreObjective sidebarObjective  = scoreboard.getObjectiveInDisplaySlot(1);
        if (sidebarObjective != null ) {
            String objectiveName = stripColor(sidebarObjective.getDisplayName());
            if (objectiveName.contains("SKYBLOCK")) {
               return objectiveName.contains("SKYBLOCK");
            } else {
                return sidebarObjective.getName().contains("SBScoreboard");
            }




        } else {
            return false;
        }


    }

    public static String get_trading_partner_username(final Container container) {
        final ItemStack stack = container.getInventory().get(41);
        if (stack == null || ItemStack.areItemStackTagsEqual(stack, new ItemStack(Blocks.stained_hardened_clay))) {
            return null;
        }
        final List<String> tooltip = (List<String>)stack.getTooltip((EntityPlayer)Minecraft.getMinecraft().thePlayer, false);
        if (tooltip.size() < 2) {
            return null;
        }
        try {
            if (!StringUtils.stripControlCodes((String)tooltip.get(1)).startsWith("Trading with")) {
                return null;
            }
            String username = StringUtils.stripControlCodes((String)tooltip.get(1)).substring("Trading with ".length());
            if (username.contains(" ")) {
                username = username.split(" ")[1];
            }
            return username.substring(0, username.length() - 1);
        }
        catch (IndexOutOfBoundsException ignored) {
            return null;
        }
    }


    public static EntityPlayer get_player_by_username(final String username) {
        for (final EntityPlayer ent : Minecraft.getMinecraft().theWorld.playerEntities) {
            if (ent.getName().toLowerCase().equals(username.toLowerCase())) {
                return ent;
            }
        }
        return null;
    }

    public static OnTrade get_ontrade() {
        return References.ontrade;
    }

    public static void set_ontrade(final OnTrade ontrade) {
        References.ontrade = ontrade;

    }

    public static Scan get_scan() {
        return References.scan;
    }

    public static void set_scan(final Scan scan) {
        References.scan = scan;

    }

    public static Scammer get_scammer() {
        return References.scammer;
    }

    public static void set_scammer(final Scammer scammer) {
        References.scammer = scammer;

    }

    public static WaitingText get_stat() {
        return References.waitingText;
    }

    public static void set_stat(final WaitingText waitingText) {
        References.waitingText = waitingText;

    }

    public static WhereMsg get_where() {
        return References.where;
    }

    public static void set_where(final WhereMsg where) {
        References.where = where;

    }



}