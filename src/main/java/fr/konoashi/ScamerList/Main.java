package fr.konoashi.ScamerList;

import com.google.gson.JsonElement;
import fr.konoashi.ScamerList.commands.CustomCommand;
import fr.konoashi.ScamerList.commands.DirectQueryCommand;
import fr.konoashi.ScamerList.commands.TestSidebarScoresCommand;
import fr.konoashi.ScamerList.config.LocalScammerList;
import fr.konoashi.ScamerList.config.Location;
import fr.konoashi.ScamerList.enums.OnTrade;
import fr.konoashi.ScamerList.enums.Scammer;
import fr.konoashi.ScamerList.enums.Scan;
import fr.konoashi.ScamerList.utils.References;

import fr.konoashi.ScamerList.utils.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.GuiChest;

import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.gui.PlayerListComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.commons.io.IOUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import scala.util.parsing.json.JSONObject;


import java.io.File;
import java.io.IOException;
import java.lang.ref.Reference;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Mod(modid = References.MODID, name = References.NAME , version = References.VERSION)
public class Main {



    private static final String LOCAL_SCAMMER_LIST_FILE = "config/ScammerLocalList.config";
    private static final String LOCATION_FILE = "config/Location.config";
    public static LocalScammerList LOCAL_SCAMMER_LIST;
    public static Location LOCATION;



    private Class<?> lastOpenedInventory;

    @Mod.Instance(References.MODID)
    public static Main instance;







    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {


        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ChatAutoReport());
        MinecraftForge.EVENT_BUS.register(new BreakHandler());
        MinecraftForge.EVENT_BUS.register(new References());

        Main.LOCAL_SCAMMER_LIST = new LocalScammerList(Main.LOCAL_SCAMMER_LIST_FILE);
        Main.LOCATION = new Location(Main.LOCATION_FILE);

            ClientCommandHandler.instance.registerCommand((ICommand)new DirectQueryCommand());
            ClientCommandHandler.instance.registerCommand((ICommand)new TestSidebarScoresCommand());


    }






     private void sendScammerAlertWebhook(String scammeruuid, String scammername) throws IOException {
        //DiscordWebhook webhook = new DiscordWebhook(References.SCAM_URL);
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        Minecraft mc = Minecraft.getMinecraft();

         System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");
        System.out.println(scammername);
        System.out.println(scammeruuid);
        System.out.println(formatter.format(date));
        System.out.println(mc.thePlayer.getUniqueID());
         System.out.println(mc.thePlayer.getName());
         System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");

         String uri = Translator.unobfuscate(Translator.webhook2);



         CloseableHttpClient httpclient = HttpClients.createDefault();
         HttpPost httpPost = new HttpPost(uri);
         String JSON_STRING="\n" +
                 "{\n" +
                 "    \"content\": \"ScamList has stopped a someone to trade with a scammer.\",\n" +
                 "  \"embeds\": [{\n" +
                 "    \"title\": \":warning: ScamList closed a trade\",\n" +
                 "    \"description\": \"We can't know if the player attempted to scam but he is a recognized scammer in our database so the trade was closed.\",\n" +
                 "    \"thumbnail\": {\n" +
                 "      \"url\": \"https://crafatar.com/avatars/" + scammeruuid + "?size=128&amp;overlay.png\"\n" +
                 "    },\n" +
                 "    \"footer\": {\n" +
                 "        \"text\": \"Reported by " + mc.thePlayer.getName() + " | " + formatter.format(date) + "\",\n" +
                 "        \"icon_url\": \"https://crafatar.com/avatars/" + mc.thePlayer.getUniqueID() + "?size=128&amp;overlay.png\"\n" +
                 "      },\n" +
                 "    \"color\": 16761856,\n" +
                 "    \n" +
                 "        \"fields\": [\n" +
                 "          {\n" +
                 "            \"name\": \"Report details\",\n" +
                 "            \"value\": \":arrow_forward: "+ scammername +" attempted to scam " + mc.thePlayer.getName()  +"\"  ,\n" +
                 "            \"inline\": false\n" +
                 "          }\n" +
                 "        ]\n" +
                 "        \n" +
                 "  }]\n" +
                 "}";
         HttpEntity stringEntity = new StringEntity(JSON_STRING, ContentType.APPLICATION_JSON);
         httpPost.setEntity(stringEntity);
         CloseableHttpResponse response2 = httpclient.execute(httpPost);
    }



    public static String getUuid(String name) {
        String url = "https://api.mojang.com/users/profiles/minecraft/"+name;
        try {
            String UUIDJson = IOUtils.toString(new URL(url));
            if(UUIDJson.isEmpty()) return "invalid name";
            JsonElement jsonElement = new JsonParser().parse(UUIDJson);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            System.out.println("testing for string");
            System.out.println(jsonObject.get("id").toString());
            return jsonObject.get("id").toString().substring(1, 33);
        } catch (IOException | JsonParseException e) {
            e.printStackTrace();

        }
        return "error";
    }


    public void closeTrade(String userToScan) {

        String line = EnumChatFormatting.AQUA + "____________________";
        String alertScam = EnumChatFormatting.DARK_RED + References.ScammListBrand + userToScan + References.msg4;

        String subtitle = EnumChatFormatting.DARK_RED +  userToScan + " is a recognized scammer !";
        String title = EnumChatFormatting.DARK_RED + "WARNING";
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(alertScam));
        //mc.thePlayer.addChatMessage(new ChatComponentText(ignore));
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(line));
        Minecraft.getMinecraft().thePlayer.playSound("mob.wither.spawn", 0.5F, 1);

        GuiIngame gui = Minecraft.getMinecraft().ingameGUI;
        gui.displayTitle(title, null, 20, 100, 20);
        gui.displayTitle(null, subtitle, 20, 100, 20);
        assert Minecraft.getMinecraft().thePlayer != null;
        Minecraft.getMinecraft().thePlayer.closeScreen();


        //Minecraft.getMinecraft().thePlayer.displayGUIChest();



    }



    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent e)  {
        //Minecraft.getMinecraft().ingameGUI.getTabList().func_181030_a();
        //System.out.println(Minecraft.getMinecraft().ingameGUI.getTabList());



        if (e.gui == null && GuiChest.class.equals(lastOpenedInventory)) {
            lastOpenedInventory = null;
        }
        if (e.gui != null) {
            lastOpenedInventory = e.gui.getClass();
            if (e.gui instanceof GuiChest) {
                Minecraft mc = Minecraft.getMinecraft();
                IInventory chestInventory;
                chestInventory = ((GuiChest) e.gui).lowerChestInventory;

                /*GuiPlayerTabOverlay tabList = mc.ingameGUI.getTabList();
                List<String> header = null;
                List<String> paheader = null;
                header = new ArrayList<>(Arrays.asList(tabList.header.getFormattedText().split("\n")));
                //paheader = new ArrayList<>(Arrays.asList(tabList.renderPlayerlist().getFormattedText().split("\n")));
                System.out.println(header);*/
                Tab.execute();



                if (chestInventory.hasCustomName()) {
                    if (chestInventory.getDisplayName().getUnformattedText().contains("You ")) {
                        References.set_ontrade(OnTrade.ON_TRADE);
                        References.set_scan(Scan.NO_SCAN);
                        } else {
                        References.set_ontrade(OnTrade.OFF_TRADE);
                    }
                    }
                }
            } else {
            References.set_ontrade(OnTrade.OFF_TRADE);
        }
        }



    @SubscribeEvent
    public void tick(final TickEvent.ClientTickEvent e) throws IOException {
        Minecraft mc = Minecraft.getMinecraft();
        if (!References.on_skyblock()) {

            return;
        }
        if (mc.thePlayer == null || mc.theWorld == null || mc.ingameGUI == null) {

            return;
        }

        
        final Container container = mc.thePlayer.openContainer;
        if (container == null || container.getInventory().size() - 36 != 45) {

            return;
        }
        final String username = References.get_trading_partner_username(container);
        if (username == null) {

            return;
        }
        final EntityPlayer target = References.get_player_by_username(username);
        if (target == null) {

            return;
        }



        if (References.get_scan() == Scan.NO_SCAN) {
            String uuidToScan = getUuid(username);
            String line = EnumChatFormatting.AQUA + "____________________";
            String waitingMessage2 = EnumChatFormatting.YELLOW + References.ScammListBrand + References.msg2 + EnumChatFormatting.GOLD + username;
            String waitingMessage1 = EnumChatFormatting.YELLOW + References.ScammListBrand + References.msg1;
            mc.thePlayer.addChatMessage(new ChatComponentText(line));
            mc.thePlayer.addChatMessage(new ChatComponentText(""));
            mc.thePlayer.addChatMessage(new ChatComponentText(waitingMessage1));
            mc.thePlayer.addChatMessage(new ChatComponentText(""));
            mc.thePlayer.addChatMessage(new ChatComponentText(waitingMessage2));
            mc.thePlayer.addChatMessage(new ChatComponentText(""));
            String verify = HttpURLConnectionExample.main("https://scamlist.github.io/Scam.json");
            String verifySbz = HttpURLConnectionExample.main("https://raw.githubusercontent.com/skyblockz/pricecheckbot/master/scammer.json");
            References.set_scan(Scan.ALR_SCAN);
            if (verify.contains(uuidToScan) || verifySbz.contains(uuidToScan)) {
                References.set_scammer(Scammer.IS_SCAMMER);
                Main.this.closeTrade(username);
                sendScammerAlertWebhook(uuidToScan, username);
            }else {
                if (uuidToScan.equals("invalid name")) {
                    References.set_scammer(Scammer.NO_RECOGNIZED);

                    String playerUnknowName = EnumChatFormatting.GOLD + References.ScammListBrand + username + " have a non-recognized minecraft name, maybe is a MVP++";
                    mc.thePlayer.addChatMessage(new ChatComponentText(playerUnknowName));
                    mc.thePlayer.addChatMessage(new ChatComponentText(""));
                    mc.thePlayer.addChatMessage(new ChatComponentText(line));
                } else {

                    if (Main.LOCAL_SCAMMER_LIST.contains(uuidToScan)) {
                        References.set_scammer(Scammer.LOCAL_SCAMMER);

                        Minecraft.getMinecraft().thePlayer.closeScreen();
                        String alertScam = EnumChatFormatting.DARK_RED + References.ScammListBrand + username + " is a local scammer";
                        String subtitle = EnumChatFormatting.DARK_RED +  username + " is a local scammer !";
                        String title = EnumChatFormatting.DARK_RED + "WARNING";
                        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(alertScam));
                        //mc.thePlayer.addChatMessage(new ChatComponentText(ignore));
                        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(line));
                        Minecraft.getMinecraft().thePlayer.playSound("mob.wither.spawn", 0.5F, 1);
                        GuiIngame gui = Minecraft.getMinecraft().ingameGUI;
                        gui.displayTitle(title, null, 20, 100, 20);
                        gui.displayTitle(null, subtitle, 20, 100, 20);
                        assert Minecraft.getMinecraft().thePlayer != null;


                    } else {
                        References.set_scammer(Scammer.NO_SCAMMER);
                        ChatComponentText playerSafe = new ChatComponentText(EnumChatFormatting.GREEN + References.ScammListBrand + username + References.msg3 + EnumChatFormatting.DARK_BLUE + "https://discord.gg/5mrpAR3q5D"); // Fill the string with what you want to show up in chat
                        ChatStyle style = new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/5mrpAR3q5D")); // Fill the string with your URL
                        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("https://discord.gg/5mrpAR3q5D")); // Fill the string with your URL
                        playerSafe.setChatStyle(style.setChatHoverEvent(hoverEvent));
                        mc.thePlayer.addChatMessage(playerSafe);
                        mc.thePlayer.addChatMessage(new ChatComponentText(""));
                        mc.thePlayer.addChatMessage(new ChatComponentText(line));
                    }

                }



            }
        }


    }




    @SubscribeEvent
    public void render(final GuiScreenEvent.BackgroundDrawnEvent e) {
        if (!References.on_skyblock()) {
            return;
        }
        if (References.get_ontrade() != OnTrade.ON_TRADE) {
            return;
        }
        final FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        Minecraft mc = Minecraft.getMinecraft();
        final ScaledResolution resolution = new ScaledResolution(mc);
        final int screen_width = resolution.getScaledWidth();
        final int screen_height = resolution.getScaledHeight();
        final Container container = mc.thePlayer.openContainer;
        String username = References.get_trading_partner_username(container);

        if (References.get_scammer() == Scammer.NOT_QUERYED) {
            return;
        }

        if (References.get_scammer() == Scammer.NO_SCAMMER) {
            String text1 = "\u2714 " + username + References.GUIMSG1;
            String text2 = References.GUIMSG2;
            int color1 = 5635925;
            int color2 = 16755200;
            drawing(fr, screen_width, screen_height, text1, text2, color1, color2);

        } else if (References.get_scammer() == Scammer.IS_SCAMMER) {
            String text1 = "\u274c " + username + References.GUIMSG3;
            String text2 = References.GUIMSG4;
            int color1 = 11141120;
            int color2 = 11141120;
            drawing(fr, screen_width, screen_height, text1, text2, color1, color2);
            References.set_scammer(Scammer.NOT_QUERYED);
        } else if (References.get_scammer() == Scammer.LOCAL_SCAMMER) {
            String text1 = "\u274c " + username + References.GUIMSG5;
            String text2 = References.GUIMSG6;
            int color1 = 11141120;
            int color2 = 11141120;
            drawing(fr, screen_width, screen_height, text1, text2, color1, color2);

        } else if (References.get_scammer() == Scammer.NO_RECOGNIZED) {
            String text1 = "\u274c " + username + References.GUIMSG7;
            String text2 = References.GUIMSG8;
            int color1 = 16755200;
            int color2 = 16755200;
            drawing(fr, screen_width, screen_height, text1, text2, color1, color2);

        }

    }



    public void drawing(FontRenderer fr, int screen_width, int screen_height, String text1, String text2, int color1, int color2) {
        final int text1_width = fr.getStringWidth(text1);
        final int x1 = screen_width / 2 - text1_width / 2;
        final int y1 = screen_height / 2 - 100 - 4 - 16 * ((text1 == null) ? 1 : 2);
        fr.drawString(text1, x1, y1, color1);
        final int text2_width = fr.getStringWidth(text2);
        final int x2 = screen_width / 2 - text2_width / 2;
        final int y2 = screen_height / 2 - 100 - 4 - 16;
        fr.drawString(text2, x2, y2, color2);
    }


}











