package fr.konoashi.ScamerList;

import com.mojang.realmsclient.gui.ChatFormatting;
import fr.konoashi.ScamerList.utils.RandomUsage;
import fr.konoashi.ScamerList.utils.References;
import fr.konoashi.ScamerList.utils.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import okhttp3.*;
import org.apache.http.HttpEntity;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;



import java.io.IOException;
import java.lang.ref.Reference;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ChatAutoReport {

    public String returnpseudo(String UnformattedTextMessageinit) {

        String UnformattedTextMessage = UnformattedTextMessageinit.substring(0, 8);

        if (UnformattedTextMessage.contains("[MVP++]")) {

            String waitforformatting = UnformattedTextMessageinit.substring(8);
            System.out.println(waitforformatting);
            String waitString = waitforformatting.substring(waitforformatting.indexOf(":"));
            String pseudo = waitforformatting.replace(waitString, "");
            return pseudo;
        } else {

            if (UnformattedTextMessage.contains("[MVP+]") || UnformattedTextMessage.contains("[VIP+]")) {

                String waitforformatting = UnformattedTextMessageinit.substring(7);
                System.out.println(waitforformatting);
                String waitString = waitforformatting.substring(waitforformatting.indexOf(":"));
                String pseudo = waitforformatting.replace(waitString, "");
                return pseudo;
            } else {
                if (UnformattedTextMessage.contains("[VIP]")) {

                    String waitforformatting = UnformattedTextMessageinit.substring(6);
                    System.out.println(waitforformatting);
                    String waitString = waitforformatting.substring(waitforformatting.indexOf(":"));
                    String pseudo = waitforformatting.replace(waitString, "");
                    return pseudo;

                } else {

                    String waitforformatting = UnformattedTextMessageinit;
                    System.out.println(waitforformatting);
                    String waitString = waitforformatting.substring(waitforformatting.indexOf(":"));
                    String pseudo = waitforformatting.replace(waitString, "");
                    return pseudo;
                }
            }
        }




    }

    private void sendReportWebhook(String scammeruuid, String scammername, String argument, String messagecontent, String playername, String playeruuid, long id) throws IOException {
        //DiscordWebhook webhook = new DiscordWebhook(References.SCAM_URL);
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        Minecraft mc = Minecraft.getMinecraft();
        String uri = Translator.unobfuscate(Translator.webhook1);


        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(uri);
        String JSON_STRING="{\n" +
                "    \"content\": \"ScamList has reported a potential scammer.\",\n" +
                "  \"embeds\": [{\n" +
                "    \"title\": \"ScamList AutoReport "+ "#"+ id +"\",\n" +
                "    \"description\": \"We can't know if the player attempted to scam but he was reported for chat message.\",\n" +
                "    \"thumbnail\": {\n" +
                "      \"url\": \"https://crafatar.com/avatars/"+ scammeruuid +"?size=128&amp;overlay.png\"\n" +
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
                "            \"name\": \"User Identity\",\n" +
                "            \"value\": \"Player name: `"+ scammername +"` and Player uuid: `"+ scammeruuid +"`\"  ,\n" +
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

    @SubscribeEvent
    public void onClientChatMessage(ClientChatReceivedEvent event) throws IOException {


        long id = RandomUsage.RandomLong();
        if(event.message.getFormattedText().contains("coopadd")) {
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
        } else if(event.message.getFormattedText().contains("giveaway")) {
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
        } else if(event.message.getFormattedText().contains("quitting") || event.message.getFormattedText().contains("quiting")) {
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
        } else if(event.message.getFormattedText().contains("Casino") || event.message.getFormattedText().contains("casinno")) {
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
    }  else if(event.message.getFormattedText().contains("free") && event.message.getFormattedText().contains("coins") || event.message.getFormattedText().contains("coin")) {
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
        }

    }

}
