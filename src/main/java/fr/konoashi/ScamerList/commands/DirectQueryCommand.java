package fr.konoashi.ScamerList.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import fr.konoashi.ScamerList.HttpURLConnectionExample;
import fr.konoashi.ScamerList.Main;
import fr.konoashi.ScamerList.enums.Scammer;
import fr.konoashi.ScamerList.utils.References;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DirectQueryCommand extends CustomCommand {


    @Override
    public String getCommandName() {
        return "slm";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return ChatFormatting.RED + "Usage: /slm check | add | remove <username | uuid>";
    }

    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        if (!References.on_skyblock()) {
            String text = EnumChatFormatting.RED + "You must be on skyblock";
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(text));
            return;
        }
        List<String> arguments = Arrays.asList(args);
        if (arguments.size() < 1) {
            throw new CommandException(this.getCommandUsage(sender));
        }
        final String subcommand = arguments.get(0);
        arguments = arguments.subList(1, arguments.size());
        String result = null;
        if (subcommand.equals("check")) {
            try {
                result = this.check(arguments);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (subcommand.equals("add")) {
            result = this.add(arguments);
        }
        else {
            if (!subcommand.equals("remove")) {
                throw new CommandException(this.getCommandUsage(sender));
            }
            result = this.remove(arguments);
        }
        if (result.length() > 0) {
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(result));
        }
    }

    private String check(final List<String> arguments) throws IOException {
        if (arguments.size() != 1) {
            return ChatFormatting.RED + "Usage: /slm check <username | uuid>";
        }
        String username = arguments.get(0);
        String uuid;
        if (username.length() == 32) {
            uuid = username;
        }
        else {
            final EntityPlayer target = References.get_player_by_username(username);
            if (target == null) {
                uuid = Main.getUuid(username);
                if (!uuid.equals("invalid name")) {
                    //HttpURLConnectionExample.main("https://raw.githubusercontent.com/skyblockz/pricecheckbot/master/scammer.json", "https://scamlist.github.io/Scam.json", uuid);
                    References.Analyst(uuid);
                    if (References.get_scammer() == Scammer.IS_SCAMMER) {
                        return EnumChatFormatting.DARK_RED + "\u2718 " + References.ScammListBrand + "" + username + " is detected as a scammer on the database";
                    } else if (Main.LOCAL_SCAMMER_LIST.contains(uuid)) {
                        return EnumChatFormatting.RED + "\u2718 " + References.ScammListBrand + "" + username + " is detected as a local scammer";
                    } else if (References.get_scammer() != Scammer.IS_SCAMMER || !Main.LOCAL_SCAMMER_LIST.contains(uuid)) {
                        System.out.println(1);
                        return EnumChatFormatting.GREEN + "\u2714 " + References.ScammListBrand + "" + username + " isn't detected as a scammer";
                    }
                    return EnumChatFormatting.GOLD + "\u26a0 " + References.ScammListBrand + "You can't check yourself";
                } else {
                    String text = EnumChatFormatting.GOLD + "\u26a0 " + References.ScammListBrand + "" + username + " isn't a minecraft recognized player";
                    return String.format(text, username);
                }
            }
            if (target.getUniqueID().equals(Minecraft.getMinecraft().thePlayer.getUniqueID())) {
                return EnumChatFormatting.GOLD + References.ScammListBrand + "You can't check yourself";
            }
            username = target.getName();
            uuid = target.getName().replace("-", "").toString();
        }
        //HttpURLConnectionExample.main("https://raw.githubusercontent.com/skyblockz/pricecheckbot/master/scammer.json", "https://scamlist.github.io/Scam.json", uuid);

        System.out.println(Main.getUuid(uuid));

        uuid = Main.getUuid(uuid);
        References.Analyst(uuid);

        if (References.get_scammer() == Scammer.IS_SCAMMER) {
            return EnumChatFormatting.DARK_RED + "\u2718 " + References.ScammListBrand + "" + username + " is detected as a scammer on the database";
        } else if (Main.LOCAL_SCAMMER_LIST.contains(uuid)) {
            return EnumChatFormatting.RED + "\u2718 " + References.ScammListBrand + "" + username + " is detected as a local scammer";
        } else {
            System.out.println(2);
            return EnumChatFormatting.GREEN + "\u2714 " + References.ScammListBrand + "" + username + " isn't detected as a scammer";
        }
    }

    private String add(final List<String> arguments) {
        if (arguments.size() != 1) {
            return ChatFormatting.RED + "Usage: /slm add <username | uuid>";
        }
        String username = arguments.get(0);
        String uuid;
        if (username.length() == 32) {
            uuid = username;
        }
        else {
            final EntityPlayer target = References.get_player_by_username(username);
            if (target == null) {
                uuid = Main.getUuid(username);
                if (!uuid.equals("invalid name")) {
                    return Main.LOCAL_SCAMMER_LIST.try_add(uuid, username);
                } else {
                    String text = EnumChatFormatting.GOLD + "\u26a0 " + References.ScammListBrand + "" + username + " isn't a minecraft recognized player";
                    return String.format(text, username);
                }


            }
            if (target.getUniqueID().equals(Minecraft.getMinecraft().thePlayer.getUniqueID())) {
                return EnumChatFormatting.GOLD + "\u26a0 " + References.ScammListBrand + "You can't add yourself on the LocalScammerList";
            }
            username = target.getName();
            uuid = target.getUniqueID().toString().replace("-", "");
        }
        return Main.LOCAL_SCAMMER_LIST.try_add(uuid, username);
    }

    private String remove(final List<String> arguments) {
        if (arguments.size() != 1) {
            return ChatFormatting.RED + "Usage: /slm remove <username | uuid>";
        }
        String username = arguments.get(0);
        String uuid;
        if (username.length() == 32) {
            uuid = username;
        }
        else {
            final EntityPlayer target = References.get_player_by_username(username);
            if (target == null) {
                uuid = Main.getUuid(username);
                if (!uuid.equals("invalid name")) {
                    return Main.LOCAL_SCAMMER_LIST.try_remove(uuid, username);
                } else {
                    String text = EnumChatFormatting.GOLD + "\u26a0 " + References.ScammListBrand + "" + username + " isn't a minecraft recognized player";
                    return String.format(text, username);
                }
            }
            if (target.getUniqueID().equals(Minecraft.getMinecraft().thePlayer.getUniqueID())) {
                return EnumChatFormatting.GOLD + "\u26a0 " + References.ScammListBrand + "You can't be on your LocalScammerList";
            }
            username = target.getName();
            uuid = target.getUniqueID().toString().replace("-", "");
        }
        return Main.LOCAL_SCAMMER_LIST.try_remove(uuid, username);
    }



}
