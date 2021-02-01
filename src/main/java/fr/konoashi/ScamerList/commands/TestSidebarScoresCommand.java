package fr.konoashi.ScamerList.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import fr.konoashi.ScamerList.Main;
import fr.konoashi.ScamerList.config.Location;
import fr.konoashi.ScamerList.utils.ScoreboardUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ChatComponentText;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TestSidebarScoresCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "dvm";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return ChatFormatting.RED + "Usage: /dvm x y";
    }


    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        System.out.println(Arrays.toString(args));
        List<String> arguments = Arrays.asList(args);


        try {
            Main.LOCATION.replace("x: " + arguments.get(0), "y: " + arguments.get(1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
