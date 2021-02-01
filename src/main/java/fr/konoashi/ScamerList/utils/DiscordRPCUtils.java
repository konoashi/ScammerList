package fr.konoashi.ScamerList.utils;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;

public class DiscordRPCUtils {

    private static IPCClient client;
    private static boolean connected;

    public static void start() {
        try {
            System.out.println("Discord RPC loading up...");
            if (isActive()) {
                return;
            }
            client = new IPCClient(795635303485538315L);
            client.setListener(new IPCListener() {
                @Override
                public void onReady(IPCClient client) {
                    System.out.println("The Discord RPC is READY!");
                }
            });
            client.connect();
        } catch (Throwable ex) {
            System.out.println("DiscordRP encountered error while starting.");
            ex.printStackTrace();
        }
    }

    public static void stop() {
        if (isActive()) {
            client.close();
            connected = false;
        }
    }

    public static boolean isActive() {
        return client != null && connected;
    }

    public static void update(String firstLine, String secondLine, String fileName) {
        RichPresence presence = new RichPresence.Builder()
                .setDetails(firstLine)
                .setState(secondLine)
                .setLargeImage(fileName, "ScammerList. MOD | https://discord.gg/Cx56wfP8dY")
                .build();
        client.sendRichPresence(presence);
    }
}
