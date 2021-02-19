package fr.konoashi.ScamerList;

import com.mojang.realmsclient.gui.ChatFormatting;
import fr.konoashi.ScamerList.enums.Scammer;
import fr.konoashi.ScamerList.enums.WhereMsg;
import fr.konoashi.ScamerList.utils.RandomUsage;
import fr.konoashi.ScamerList.utils.References;
import fr.konoashi.ScamerList.utils.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.Reference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class ChatAutoReport {

    public static StringBuffer response;
    public static StringBuffer response2;



    public static String returnpseudo(String UnformattedTextMessageinit) {

        String UnformattedTextMessage = UnformattedTextMessageinit.substring(0, UnformattedTextMessageinit.length());



        if (UnformattedTextMessage.startsWith("From ")) {
            UnformattedTextMessageinit = UnformattedTextMessageinit.substring(5);

            References.set_where(WhereMsg.PRIVATE);
        } else if (UnformattedTextMessage.startsWith("To ")) {
            UnformattedTextMessageinit = UnformattedTextMessageinit.substring(3);
            References.set_where(WhereMsg.PRIVATE);

        } else if (UnformattedTextMessage.startsWith("Officer ")) {
            UnformattedTextMessageinit = UnformattedTextMessageinit.substring(10);
            References.set_where(WhereMsg.OFFICER_GUILD);

        } else if (UnformattedTextMessage.startsWith("Party ")) {
            UnformattedTextMessageinit = UnformattedTextMessageinit.substring(8);
            References.set_where(WhereMsg.PARTY);

        } else if (UnformattedTextMessage.startsWith("Co-op ")) {
            UnformattedTextMessageinit = UnformattedTextMessageinit.substring(8);
            References.set_where(WhereMsg.COOP_CHAT);

        } else if (UnformattedTextMessage.startsWith("Guild ")) {
            UnformattedTextMessageinit = UnformattedTextMessageinit.substring(8);
            References.set_where(WhereMsg.GUILD);

        } else {
            References.set_where(WhereMsg.PUBLIC);
        }


        if (UnformattedTextMessage.contains("[MVP++]")) {

            if (UnformattedTextMessage.startsWith("[✌] ")) {
                UnformattedTextMessageinit = UnformattedTextMessageinit.substring(4);
                References.set_where(WhereMsg.ISLAND_GUEST);
            }
            if (UnformattedTextMessageinit.startsWith(" ")) {
                UnformattedTextMessageinit = UnformattedTextMessageinit.substring(0, 1);

            }
            if (!UnformattedTextMessageinit.startsWith("[MVP++]")) {
                UnformattedTextMessageinit = UnformattedTextMessageinit.substring(UnformattedTextMessageinit.indexOf("[MVP++]"));


            }
            String waitforformatting = UnformattedTextMessageinit.substring(8);
            System.out.println(waitforformatting);
            String waitString = UnformattedTextMessageinit.substring(UnformattedTextMessageinit.indexOf(":"));
            String isguild = UnformattedTextMessageinit.substring(UnformattedTextMessageinit.indexOf(":")-1, UnformattedTextMessageinit.indexOf(":"));
            if (isguild.equals("]")) {
                waitforformatting = waitforformatting.substring(0, waitforformatting.indexOf("[")-1);
            }
            waitforformatting = waitforformatting.replace(waitString, "");

            if (waitforformatting.contains(" ")) {
                waitforformatting = waitforformatting.replace(" ", "");
            }
            return waitforformatting;
        } else {

            if (UnformattedTextMessage.contains("[MVP+]") || UnformattedTextMessage.contains("[VIP+]")) {

                if (UnformattedTextMessage.startsWith("[✌] ")) {
                    if (UnformattedTextMessage.contains("[MVP+]")) {
                        UnformattedTextMessageinit = UnformattedTextMessageinit.substring(UnformattedTextMessageinit.indexOf("[MVP+]")-1);
                        References.set_where(WhereMsg.ISLAND_GUEST);
                    }
                    if (UnformattedTextMessage.contains("[VIP+]")) {
                        UnformattedTextMessageinit = UnformattedTextMessageinit.substring(UnformattedTextMessageinit.indexOf("[VIP+]")-1);
                        References.set_where(WhereMsg.ISLAND_GUEST);
                    }
                }
                if (UnformattedTextMessageinit.startsWith(" ")) {
                    UnformattedTextMessageinit = UnformattedTextMessageinit.substring(0, 1);

                }
                if (!UnformattedTextMessageinit.startsWith("[MVP+]") || !UnformattedTextMessageinit.startsWith("[VIP+]")) {
                    if (UnformattedTextMessageinit.contains("[MVP+]")) {

                        UnformattedTextMessageinit = UnformattedTextMessageinit.substring(UnformattedTextMessageinit.indexOf("[MVP+]"));
                    } else {

                        UnformattedTextMessageinit = UnformattedTextMessageinit.substring(UnformattedTextMessageinit.indexOf("[VIP+]"));
                    }


                }
                String waitforformatting = UnformattedTextMessageinit.substring(7);
                System.out.println(waitforformatting);
                String waitString = waitforformatting.substring(waitforformatting.indexOf(":"));
                waitforformatting = waitforformatting.replace(waitString, "");
                if (waitforformatting.contains("[")) {
                    waitforformatting = waitforformatting.substring(waitforformatting.indexOf("[")-1, waitforformatting.length());
                }
                return waitforformatting;
            } else {
                if (UnformattedTextMessage.contains("[VIP]") || UnformattedTextMessage.contains("[MVP]")) {


                    if (UnformattedTextMessage.startsWith("[✌] ")) {
                        if (UnformattedTextMessage.contains("[VIP]")) {
                            UnformattedTextMessageinit = UnformattedTextMessageinit.substring(UnformattedTextMessageinit.indexOf("[VIP]")-1);
                            References.set_where(WhereMsg.ISLAND_GUEST);
                        }
                        if (UnformattedTextMessage.contains("[MVP]")) {
                            UnformattedTextMessageinit = UnformattedTextMessageinit.substring(UnformattedTextMessageinit.indexOf("[MVP]")-1);
                            References.set_where(WhereMsg.ISLAND_GUEST);
                        }
                    }
                    if (UnformattedTextMessageinit.startsWith(" ")) {
                        UnformattedTextMessageinit = UnformattedTextMessageinit.substring(0, 1);

                    }
                    if (!UnformattedTextMessageinit.startsWith("[VIP]") || !UnformattedTextMessageinit.startsWith("[MVP]")) {
                        if (UnformattedTextMessageinit.contains("[VIP]")) {

                            UnformattedTextMessageinit = UnformattedTextMessageinit.substring(UnformattedTextMessageinit.indexOf("[VIP]"));
                        } else {

                            UnformattedTextMessageinit = UnformattedTextMessageinit.substring(UnformattedTextMessageinit.indexOf("[MVP]"));
                        }


                    }
                    String waitforformatting = UnformattedTextMessageinit.substring(6);
                    System.out.println(waitforformatting);
                    String waitString = UnformattedTextMessageinit.substring(UnformattedTextMessageinit.indexOf(":"));
                    String isguild = UnformattedTextMessageinit.substring(UnformattedTextMessageinit.indexOf(":")-1, UnformattedTextMessageinit.indexOf(":"));
                    if (isguild.equals("]")) {
                        waitforformatting = waitforformatting.substring(0, waitforformatting.indexOf("[")-1);
                    }
                    waitforformatting = waitforformatting.replace(waitString, "");

                    if (waitforformatting.contains(" ")) {
                        waitforformatting = waitforformatting.replace(" ", "");
                    }
                    return waitforformatting;

                } else {



                    if (UnformattedTextMessage.startsWith("[✌] ")) {

                        UnformattedTextMessageinit = UnformattedTextMessageinit.substring(6);
                        if (UnformattedTextMessageinit.startsWith("[")) {
                            UnformattedTextMessageinit = UnformattedTextMessageinit.substring(UnformattedTextMessageinit.indexOf("]")+1);

                        }

                        References.set_where(WhereMsg.ISLAND_GUEST);


                    } else if (UnformattedTextMessageinit.startsWith("[")) {
                        UnformattedTextMessageinit = UnformattedTextMessageinit.substring(UnformattedTextMessageinit.indexOf("]")+1);
                        System.out.println(UnformattedTextMessageinit);
                    }
                    System.out.println(UnformattedTextMessageinit);
                    String waitString = UnformattedTextMessageinit.substring(UnformattedTextMessageinit.indexOf(":"));
                    String waitforformatting = UnformattedTextMessageinit;
                    String isguild = UnformattedTextMessageinit.substring(UnformattedTextMessageinit.indexOf(":")-1, UnformattedTextMessageinit.indexOf(":"));
                    if (isguild.equals("]")) {
                        waitforformatting = waitforformatting.substring(0, waitforformatting.indexOf("[")-1);
                    }
                    waitforformatting = waitforformatting.replace(waitString, "");

                    if (waitforformatting.contains(" ")) {
                        waitforformatting = waitforformatting.replace(" ", "");
                    }
                    return waitforformatting;
                }
            }
        }




    }

    private void sendReportWebhook(String scammeruuid, String scammername, String argument, String messagecontent, String playername, String playeruuid, long id) {
        //DiscordWebhook webhook = new DiscordWebhook(References.SCAM_URL);
        String uri = Translator.unobfuscate(Translator.webhook1);




        new Thread(() -> {
            String place = "ERROR";
            if (References.get_where() == WhereMsg.PUBLIC) {
                place = "PUBLIC CHAT";
            } else if (References.get_where() == WhereMsg.COOP_CHAT) {
                place = "COOP CHAT";

            } else if (References.get_where() == WhereMsg.GUILD) {
                place = "GUILD CHAT";

            } else if (References.get_where() == WhereMsg.ISLAND_GUEST) {
                place = "GUEST IN ISLAND CHAT";

            } else if (References.get_where() == WhereMsg.ISLAND_MASTER) {
                place = "MASTER IN ISLAND CHAT";

            } else if (References.get_where() == WhereMsg.OFFICER_GUILD) {
                place = "GUILD OFFICER CHAT";

            } else if (References.get_where() == WhereMsg.PRIVATE) {
                place = "PRIVATE CHAT";

            } else if (References.get_where() == WhereMsg.PARTY) {
                place = "PARTY CHAT";

            }
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(uri);
        String JSON_STRING="{\n" +
                "    \"content\": \"ScamList has reported a potential scammer.\",\n" +
                "  \"embeds\": [{\n" +
                "    \"title\": \"ScamList AutoReport "+ "#"+ id +"\",\n" +
                "    \"description\": \"We can't know if the player attempted to scam but he was reported for chat message.\",\n" +
                "    \"thumbnail\": {\n" +
                "      \"url\": \"https://cravatar.eu/avatar/"+ scammername +"/128.png\"\n" +
                "    },\n" +
                "    \"footer\": {\n" +
                "        \"text\": \"Reported by "+ playername +"\",\n" +
                "        \"icon_url\": \"https://crafatar.com/avatars/"+ playeruuid +"?size=128&amp;overlay.png\"\n" +
                "      },\n" +
                "    \"color\": 16761856,\n" +
                "    \n" +
                "        \"fields\": [\n" +
                "          {\n" +
                "            \"name\": \"Message detail\",\n" +
                "            \"value\": \"```"+ messagecontent +"```\"  ,\n" +
                "            \"inline\": false\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"Report reason\",\n" +
                "            \"value\": \"The message contain `"+ argument +"` banned argument\"  ,\n" +
                "            \"inline\": false\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"Message Location\",\n" +
                "            \"value\": \"The message was send in `"+ place +"` place\"  ,\n" +
                "            \"inline\": false\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"User Identity\",\n" +
                "            \"value\": \"Player name: `"+ scammername +"` and Player uuid: `"+ scammeruuid +"`\"  ,\n" +
                "            \"inline\": false\n" +
                "          }\n" +
                "        ]\n" +
                "        \n" +
                "  }]\n" +
                "}";
        if (place.equals("ERROR")) {
            Thread.currentThread().interrupt();
            References.set_where(WhereMsg.NO_STATEMENT);
        }
        HttpEntity stringEntity = new StringEntity(JSON_STRING, ContentType.APPLICATION_JSON);
        httpPost.setEntity(stringEntity);

            try {
                CloseableHttpResponse response2 = httpclient.execute(httpPost);
                References.set_where(WhereMsg.NO_STATEMENT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        

    }



    @SubscribeEvent
    public void onClientChatMessage(ClientChatReceivedEvent event) {

        /*if(event.message.getFormattedText().contains("Mana")) {
            String Tablist = Tab.execute();

            int index = Tablist.indexOf("Crit Chance:")-1;
            String tabedit = Tablist.substring(index);
            tabedit = tabedit.substring(0, tabedit.indexOf(","));
            if (tabedit.startsWith(" ")) {
                tabedit = tabedit.substring(1);
            }
            tabedit = tabedit.substring(13);

            event.message = new ChatComponentText(ChatFormatting.BLUE + tabedit + " Crit Chance     " + ChatFormatting.RESET).appendSibling(event.message);

        }*/

        if (event.message.getFormattedText().toLowerCase().contains("you are playing on profile")) {

            String url = "https://raw.githubusercontent.com/skyblockz/pricecheckbot/master/scammer.json";
            String url2 = "https://scamlist.github.io/Scam.json";
            new Thread(() -> {
                References.set_scammer(Scammer.NOT_QUERYED);


                URL obj = null;

                try {
                    obj = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection con = null;
                try {
                    assert obj != null;
                    con = (HttpURLConnection) obj.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    assert con != null;
                    con.setRequestMethod("GET");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }

                int responseCode = 0;
                try {
                    responseCode = con.getResponseCode();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("GET Response Code :: " + responseCode);
                BufferedReader in = null;
                try {
                    in = new BufferedReader(new InputStreamReader(
                            con.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String inputLine = null;
                StringBuffer response = new StringBuffer();

                while (true) {
                    try {
                        assert in != null;
                        if ((inputLine = in.readLine()) == null) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    response.append(inputLine);
                    ChatAutoReport.response = response;

                }
                try {
                    in.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }


                URL obj2 = null;
                try {
                    obj2 = new URL(url2);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection con2 = null;
                try {
                    assert obj2 != null;
                    con2 = (HttpURLConnection) obj2.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    assert con2 != null;
                    con2.setRequestMethod("GET");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }

                int responseCode2 = 0;
                try {
                    responseCode2 = con2.getResponseCode();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("GET Response Code :: " + responseCode2);
                BufferedReader in2 = null;
                try {
                    in2 = new BufferedReader(new InputStreamReader(
                            con2.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String inputLine2 = null;
                StringBuffer response2 = new StringBuffer();

                while (true) {
                    try {
                        assert in2 != null;
                        if ((inputLine2 = in2.readLine()) == null) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    response2.append(inputLine2);
                    ChatAutoReport.response2 = response2;

                }
                try {
                    in2.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }




            }).start();
        }



        if (References.get_where() == WhereMsg.PARTY) {
            if (!ToggleCommand.joinInformationToggled) {
                return;
            }
            //MinecraftServer.getServer().getCommandManager().executeCommand(Minecraft.getMinecraft().thePlayer, "p leave");
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/p leave");
            References.set_where(WhereMsg.NO_STATEMENT);
        }
        if (event.message.getFormattedText().toLowerCase().contains("has invited you to join their party!")) {
            if (!ToggleCommand.threeManToggled) {
                return;
            }
            String msg = event.message.getUnformattedText();
            msg = msg.substring(30, msg.indexOf("has")-1);
            if (msg.contains("-----------------------------")) {
                msg.replaceAll("-", "");
            }
            String User = event.message.getFormattedText();
            if (msg.startsWith("[")) {
                msg = msg.substring(msg.indexOf("]")+2);

            }
            if (msg.contains(" ")) {
                msg = msg.replaceAll(" ", "");
            }
            User = User.substring(User.indexOf("[")-3, User.indexOf("has")-1);
            if (User.startsWith(" ")) {
                User = User.replace(" ", "");
            }
            if (ChatAutoReport.response == null || ChatAutoReport.response2 == null) {
                return;
            }

            String uuid = Main.getUuid(msg);




            if (ChatAutoReport.response.toString().contains(uuid) || ChatAutoReport.response2.toString().contains(uuid)) {
                String alertScam = EnumChatFormatting.DARK_RED + References.ScammListBrand + "The party of " +  User + EnumChatFormatting.DARK_RED +" isn't safe, stay in sure and take care";
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(alertScam));

            } else if (uuid.equals("invalid name")) {
                String alertScam = EnumChatFormatting.GOLD + References.ScammListBrand + "The party of " +  User + EnumChatFormatting.GOLD +" can't be resolved because this account doesn't exist";
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(alertScam));

            } else if (Main.LOCAL_SCAMMER_LIST.contains(uuid)) {

                String alertScam = EnumChatFormatting.RED + References.ScammListBrand + "The party of " +  User + EnumChatFormatting.RED +" isn't safe, stay in sure and take care (local)";
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(alertScam));


            } else {
                String alertScam = EnumChatFormatting.GREEN + References.ScammListBrand + "The party of " +  User + EnumChatFormatting.GREEN +" isn't recognized as dangerous";
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(alertScam));

            }
        }
        if (!ToggleCommand.blazeToggled) {
            return;
        }


        long id = RandomUsage.RandomLong();
        if(event.message.getFormattedText().toLowerCase().contains("coopadd")) {
            if (!References.on_skyblock()) {
                return;
            }
            String arg2 = returnpseudo(Tab.cleanColour(event.message.getUnformattedText()));
            if (arg2 == null) {
                return;
            }
            if (arg2.equals(Minecraft.getMinecraft().getSession().getUsername())) {
                return;
            }

            String arg1 = Main.getUuid(arg2);
            String arg3 = "COOPADD";
            String arg4 = event.message.getUnformattedText().substring(event.message.getUnformattedText().indexOf(":") +2);
            String arg5 = Minecraft.getMinecraft().getSession().getUsername();
            String arg6 = Minecraft.getMinecraft().getSession().getPlayerID();
            event.message = new ChatComponentText(ChatFormatting.RED + "\u26a0 " + ChatFormatting.RESET).appendSibling(event.message);

            sendReportWebhook(arg1, arg2, arg3, arg4, arg5, arg6, id);
            Minecraft.getMinecraft().thePlayer.playSound("mob.wither.spawn", 0.5F, 1);

        } else if(event.message.getFormattedText().toLowerCase().contains("giveaway")) {
            if (!References.on_skyblock()) {
                return;
            }
            String arg2 = returnpseudo(Tab.cleanColour(event.message.getUnformattedText()));
            if (arg2 == null) {
                return;
            }
            if (arg2.equals(Minecraft.getMinecraft().getSession().getUsername())) {
                return;
            }
            String arg1 = Main.getUuid(arg2);
            String arg3 = "GIVEAWAY";
            String arg4 = event.message.getUnformattedText().substring(event.message.getUnformattedText().indexOf(":") +2);
            String arg5 = Minecraft.getMinecraft().getSession().getUsername();
            String arg6 = Minecraft.getMinecraft().getSession().getPlayerID();
            event.message = new ChatComponentText(ChatFormatting.RED + "\u26a0 " + ChatFormatting.RESET).appendSibling(event.message);
            sendReportWebhook(arg1, arg2, arg3, arg4, arg5, arg6, id);
        } else if(event.message.getFormattedText().toLowerCase().contains("quitting") || event.message.getFormattedText().toLowerCase().contains("quiting")) {
            if (!References.on_skyblock()) {
                return;
            }

            String arg2 = returnpseudo(Tab.cleanColour(event.message.getUnformattedText()));
            if (arg2 == null) {
                return;
            }
            if (arg2.equals(Minecraft.getMinecraft().getSession().getUsername())) {
                return;
            }
            String arg1 = Main.getUuid(arg2);
            String arg3 = "QUITTING";
            String arg4 = event.message.getUnformattedText().substring(event.message.getUnformattedText().indexOf(":") +2);
            String arg5 = Minecraft.getMinecraft().getSession().getUsername();
            String arg6 = Minecraft.getMinecraft().getSession().getPlayerID();
            event.message = new ChatComponentText(ChatFormatting.RED + "\u26a0 " + ChatFormatting.RESET).appendSibling(event.message);
            sendReportWebhook(arg1, arg2, arg3, arg4, arg5, arg6, id);
            Minecraft.getMinecraft().thePlayer.playSound("mob.wither.spawn", 0.5F, 1);

        } else if(event.message.getFormattedText().toLowerCase().contains("casino") || event.message.getFormattedText().toLowerCase().contains("casinno")) {
        if (!References.on_skyblock()) {
            return;
        }

        String arg2 = returnpseudo(Tab.cleanColour(event.message.getUnformattedText()));
        if (arg2 == null) {
            return;
        }
        if (arg2.equals(Minecraft.getMinecraft().getSession().getUsername())) {
            return;
        }
        String arg1 = Main.getUuid(arg2);
        String arg3 = "CASINO";
        String arg4 = event.message.getUnformattedText().substring(event.message.getUnformattedText().indexOf(":") +2);
        String arg5 = Minecraft.getMinecraft().getSession().getUsername();
        String arg6 = Minecraft.getMinecraft().getSession().getPlayerID();
        event.message = new ChatComponentText(ChatFormatting.RED + "\u26a0 " + ChatFormatting.RESET).appendSibling(event.message);
        sendReportWebhook(arg1, arg2, arg3, arg4, arg5, arg6, id);
            Minecraft.getMinecraft().thePlayer.playSound("mob.wither.spawn", 0.5F, 1);

        }  else if(event.message.getFormattedText().toLowerCase().contains("free") && event.message.getFormattedText().toLowerCase().contains("coins")) {
            if (!References.on_skyblock()) {
                return;
            }

            String arg2 = returnpseudo(Tab.cleanColour(event.message.getUnformattedText()));
            if (arg2 == null) {
                return;
            }
            if (arg2.equals(Minecraft.getMinecraft().getSession().getUsername())) {
                return;
            }
            String arg1 = Main.getUuid(arg2);
            String arg3 = "FREE COINS SCAM";
            String arg4 = event.message.getUnformattedText().substring(event.message.getUnformattedText().indexOf(":") +2);
            String arg5 = Minecraft.getMinecraft().getSession().getUsername();
            String arg6 = Minecraft.getMinecraft().getSession().getPlayerID();
            event.message = new ChatComponentText(ChatFormatting.RED + "\u26a0 " + ChatFormatting.RESET).appendSibling(event.message);
            sendReportWebhook(arg1, arg2, arg3, arg4, arg5, arg6, id);
            Minecraft.getMinecraft().thePlayer.playSound("mob.wither.spawn", 0.5F, 1);

        } else if(event.message.getFormattedText().toLowerCase().contains("cheap coin")) {
        if (!References.on_skyblock()) {
            return;
        }
        String arg2 = returnpseudo(Tab.cleanColour(event.message.getUnformattedText()));
        if (arg2 == null) {
            return;
        }
        if (arg2.equals(Minecraft.getMinecraft().getSession().getUsername())) {
            return;
        }
        String arg1 = Main.getUuid(arg2);
        String arg3 = "IRL TRADE";
        String arg4 = event.message.getUnformattedText().substring(event.message.getUnformattedText().indexOf(":") +2);
        String arg5 = Minecraft.getMinecraft().getSession().getUsername();
        String arg6 = Minecraft.getMinecraft().getSession().getPlayerID();
        event.message = new ChatComponentText(ChatFormatting.RED + "\u26a0 " + ChatFormatting.RESET).appendSibling(event.message);
        sendReportWebhook(arg1, arg2, arg3, arg4, arg5, arg6, id);
    }

    }

}
