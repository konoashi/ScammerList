package fr.konoashi.ScamerList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class Tab {


    @SubscribeEvent
    public static void tickEvent(final TickEvent.ClientTickEvent e) {



    }



    public static String execute() {
        Minecraft minecraft = Minecraft.getMinecraft();
        final NetHandlerPlayClient netHandlerPlayClient = minecraft.getNetHandler();
            final Collection<NetworkPlayerInfo> playerInfoMap = netHandlerPlayClient.getPlayerInfoMap();
            final GuiPlayerTabOverlay tabOverlay = minecraft.ingameGUI.getTabList();
            String outputText;
            outputText = playerInfoMap.stream().map(tabOverlay::getPlayerName).collect(Collectors.joining(", "));


            return cleanColour(outputText);

    }

    public static String cleanColour(String in) {
        return in.replaceAll("(?i)\\u00A7.", "");
    }

    public static String Commisions1(String execute) {


        if(execute.contains("Mithril:")) {
            execute = "           ," + execute;
            int index = execute.indexOf("Mithril:");
            String tabedit = execute.substring(index -20);
            tabedit = tabedit.substring(tabedit.indexOf("Mithril:") -16, tabedit.indexOf("Mithril:")+15);
            tabedit = tabedit.substring(0, tabedit.lastIndexOf(","));
            if(tabedit.contains(",")) {
                tabedit = tabedit.substring(tabedit.indexOf(",")+3);
            }
            if(tabedit.contains(",")) {
                tabedit = tabedit.substring(0, tabedit.lastIndexOf(","));
            }
            for (int i=0; i<5;i++) {
                if(tabedit.startsWith(",")) {
                    tabedit = tabedit.substring(1);
                }
                if(tabedit.startsWith(" ")) {
                    tabedit = tabedit.substring(1);
                }
            }

            return tabedit;

        } else if(execute.contains("Mithril Miner:")) {
            int index = execute.indexOf("Mithril Miner:");
            String tabedit = execute.substring(index);
            return tabedit.substring(0, tabedit.indexOf(","));

        } else if(execute.contains("Goblin Slayer:")) {
            int index = execute.indexOf("Goblin Slayer:");
            String tabedit = execute.substring(index);
            return tabedit.substring(0, tabedit.indexOf(","));

        } else if(execute.contains("Ice Walker Slayer:")) {
            int index = execute.indexOf("Ice Walker Slayer:");
            String tabedit = execute.substring(index);
            return tabedit.substring(0, tabedit.indexOf(","));

        } else if(execute.contains("Titanium Miner:")) {
            int index = execute.indexOf("Titanium Miner:");
            String tabedit = execute.substring(index);
            return tabedit.substring(0, tabedit.indexOf(","));

        } else if(execute.contains("Titanium:")) {
            execute = "           ," + execute;
            int index = execute.indexOf("Titanium:");
            String tabedit = execute.substring(index -20);
            tabedit = tabedit.substring(tabedit.indexOf("Titanium:") -20, tabedit.indexOf("Titanium:")+15);
            tabedit = tabedit.substring(0, tabedit.lastIndexOf(","));
            if(tabedit.contains(",")) {
                tabedit = tabedit.substring(tabedit.indexOf(",")+3);
            }
            if(tabedit.contains(",")) {
                tabedit = tabedit.substring(0, tabedit.lastIndexOf(","));
            }
            if(tabedit.substring(0, 1).contains(" ")) {
                tabedit = tabedit.substring(1, tabedit.length());
            }
            return tabedit;

        } else {
            return null;
        }

    }

    public static String Commisions2(String execute) {

        String tab = execute;

        if(tab.contains("Goblin Raid:")) {
            tab = tab + ",  ";
            int index = tab.lastIndexOf("Goblin Raid:");
            String tabedit = tab.substring(index);
            return tabedit.substring(0, tabedit.indexOf(","));

        } else if(tab.contains("Golden Goblin Slayer:")) {
            tab = tab + ",  ";
            int index = tab.lastIndexOf("Golden Goblin Slayer:");
            String tabedit = tab.substring(index);
            return tabedit.substring(0, tabedit.indexOf(","));

        } else if(tab.contains("Raffle:")) {
        tab = tab + ",  ";
        int index = tab.lastIndexOf("Raffle:");
        String tabedit = tab.substring(index);
        return tabedit.substring(0, tabedit.indexOf(","));

        } else if(tab.contains("Powder Ghast Puncher:")) {
            tab = tab + ",  ";
            int index = tab.lastIndexOf("Powder Ghast Puncher:");
            String tabedit = tab.substring(index);
            return tabedit.substring(0, tabedit.indexOf(","));

        } else if(tab.contains("Ice Walker Slayer:")) {
            tab = tab + ",  ";
            int index = tab.lastIndexOf("Ice Walker Slayer:");
            String tabedit = tab.substring(index);
            return tabedit.substring(0, tabedit.indexOf(","));

        } else if(tab.contains("Lucky Raffle:")) {
            tab = tab + ",  ";
            int index = tab.lastIndexOf("Lucky Raffle:");
            String tabedit = tab.substring(index);
            return tabedit.substring(0, tabedit.indexOf(","));

        } else if(tab.contains("Star Sentry Puncher:")) {
            tab = tab + ",  ";
            int index = tab.lastIndexOf("Star Sentry Puncher:");
            String tabedit = tab.substring(index);
            return tabedit.substring(0, tabedit.indexOf(","));


        } else if(tab.contains("2x Mithril Powder Collector:")) {
            tab = tab + ",  ";
            int index = tab.lastIndexOf("2x Mithril Powder Collector:");
            String tabedit = tab.substring(index);
            return tabedit.substring(0, tabedit.indexOf(","));


        } else {
            return null;
        }

    }

    public static String Powder(String execute) {


        if (execute.contains("Mithril Powder:")) {
            int index = execute.indexOf("Mithril Powder:");
            String tabedit = execute.substring(index, execute.length());
            tabedit = tabedit.substring(0, tabedit.indexOf(", "));
            return tabedit;
        } else  {
            return "An error occurred";
        }
    }






}
