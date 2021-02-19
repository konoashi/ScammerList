package fr.konoashi.ScamerList;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class ToggleCommand extends CommandBase implements ICommand {
    //TODO: To add a feature, add something here.
    public static boolean coordsToggled;
    public static boolean outlineTextToggled;
    public static boolean threeManToggled;
    public static boolean oruoToggled;
    public static boolean blazeToggled;
    public static boolean creeperToggled;
    public static boolean joinInformationToggled;
    public static boolean mobClearToggled;
    public static boolean amongUsSolverToggled;
    public static boolean discordRpcToggled;
    public static boolean witherKeyToggled;
    public static boolean waterToggled;
    public static boolean hideImplosionToggled;
    public static boolean skeletonMasterToggled;

    @Override
    public String getCommandName() {
        return "aaaaaaaaaaaaaaaaaa";
    }

    @Override
    public String getCommandUsage(ICommandSender arg0) {
        return "/" + getCommandName() + " ";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }


    @Override
    public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
        final EntityPlayer player = (EntityPlayer) arg0;
        final ConfigHandler cf = new ConfigHandler();

        if (arg1.length == 0) {
            player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Usage: " + getCommandUsage(arg0)));
            return;
        }

        if (arg1[0].equalsIgnoreCase("coords")) {
            coordsToggled = !coordsToggled;
            ConfigHandler.writeBooleanConfig("toggles", "Coords", coordsToggled);
        } else if (arg1[0].equalsIgnoreCase("threemanpuzzle")) {
            threeManToggled = !threeManToggled;
            ConfigHandler.writeBooleanConfig("toggles", "ThreeManPuzzle", threeManToggled);
        } else if (arg1[0].equalsIgnoreCase("oruopuzzle")) {
            oruoToggled = !oruoToggled;
            ConfigHandler.writeBooleanConfig("toggles", "OruoPuzzle", oruoToggled);
        } else if (arg1[0].equalsIgnoreCase("blazepuzzle")) {
            blazeToggled = !blazeToggled;
            ConfigHandler.writeBooleanConfig("toggles", "BlazePuzzle", blazeToggled);
        } else if (arg1[0].equalsIgnoreCase("creeperpuzzle")) {
            creeperToggled = !creeperToggled;
            ConfigHandler.writeBooleanConfig("toggles", "joinInformation", creeperToggled);
        } else if (arg1[0].equalsIgnoreCase("joinInformation")) {
            joinInformationToggled = !joinInformationToggled;
            ConfigHandler.writeBooleanConfig("toggles", "joinInformation", joinInformationToggled);
        } else if (arg1[0].equalsIgnoreCase("mobClear")) {
            mobClearToggled = !mobClearToggled;
            ConfigHandler.writeBooleanConfig("toggles", "mobClear", mobClearToggled);
        } else if (arg1[0].equalsIgnoreCase("amongUsSolver")) {
            amongUsSolverToggled = !amongUsSolverToggled;
            ConfigHandler.writeBooleanConfig("toggles", "amongUsSolver", amongUsSolverToggled);
        } else if (arg1[0].equalsIgnoreCase("witherKey")) {
            witherKeyToggled = !witherKeyToggled;
            ConfigHandler.writeBooleanConfig("toggles", "witherKey", witherKeyToggled);
        } else if (arg1[0].equalsIgnoreCase("discordRpc")) {
            discordRpcToggled = !discordRpcToggled;
            ConfigHandler.writeBooleanConfig("discordRpc", "discordRpc", discordRpcToggled);
        } else if (arg1[0].equalsIgnoreCase("waterSolver")) {
            waterToggled = !waterToggled;
            ConfigHandler.writeBooleanConfig("waterSolver", "waterSolver", waterToggled);
        } else if (arg1[0].equalsIgnoreCase("hideImplosion")) {
            hideImplosionToggled = !hideImplosionToggled;
            ConfigHandler.writeBooleanConfig("hideImplosion", "hideImplosion", hideImplosionToggled);
        } else if (arg1[0].equalsIgnoreCase("skeletonMaster")) {
            skeletonMasterToggled = !skeletonMasterToggled;
            ConfigHandler.writeBooleanConfig("skeletonMaster", "skeletonMaster", skeletonMasterToggled);
        }
    }
}
