package fr.konoashi.ScamerList;

import com.google.common.collect.Maps;
import fr.konoashi.ScamerList.utils.References;
import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.renderer.DestroyBlockProgress;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.lang.ref.Reference;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BreakHandler {

    private int length;
    private Font TextFormatting;



    public void onBreakEvent() throws NoSuchFieldException, IllegalAccessException {


      /*  System.out.println("------");
        System.out.println(event.getResult());
        System.out.println(event.getExpToDrop());
        System.out.println(event.getPlayer());
        System.out.println(event.state.getBlock().getRegistryName());
        System.out.println(event.state.getBlock());
        System.out.println(event.state.getBlock().getDefaultState());
        System.out.println("------");*/

        //render.sendBlockBreakProgress();
        Field renderField;
        renderField = RenderGlobal.class.getDeclaredField("damagedBlocks");
        renderField.setAccessible(true);
        Map<Integer, DestroyBlockProgress> damagedBlocks = Maps.<Integer, DestroyBlockProgress>newHashMap();
        renderField.get(Minecraft.getMinecraft().renderGlobal);
        for (Map.Entry<Integer, DestroyBlockProgress> entry : damagedBlocks.entrySet()) {
            DestroyBlockProgress destroyblockprogress = entry.getValue();
            System.out.println(destroyblockprogress.toString());
            System.out.println(destroyblockprogress.getPartialBlockDamage());
            System.out.println(destroyblockprogress.getPosition());
            System.out.println(renderField.getName());

        }



    }





    @SubscribeEvent
    public void ItemPickupEvent(PlayerEvent.ItemPickupEvent event) {

        System.out.println(event.pickedUp.getName());
        System.out.println(event.pickedUp.getEntityItem());


    }

    @SubscribeEvent
    public void tickEvent(final TickEvent e) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null || mc.theWorld == null || mc.ingameGUI == null) {

            return;
        }
        //System.out.println(Minecraft.getMinecraft().theWorld.getScoreboard().getObjectiveInDisplaySlot(1).getCriteria());
        BlockPos.ORIGIN.getX();
        BlockPos.ORIGIN.getY();



    }

    public static void onPlayerDestroyBlock(BlockPos loc) {
        Minecraft mc = Minecraft.getMinecraft();
        ItemStack heldItem = Minecraft.getMinecraft().thePlayer.getHeldItem();
        if (heldItem != null) {
            Block block = mc.theWorld.getBlockState(loc).getBlock();
            if ((block.equals(Blocks.wool))) {
                System.out.println("Prismarine");
            }
        }
    }





    public static boolean stringContainsNumber(String s)
    {
        Pattern p = Pattern.compile( "[0-9]" );
        Matcher m = p.matcher( s );

        return m.find();
    }

    public static String addChar(String str, char ch, int position) {
        StringBuilder sb = new StringBuilder(str);
        sb.insert(position, ch);
        return sb.toString();
    }

    @SubscribeEvent
    public void renderDwarven(RenderGameOverlayEvent event) throws NoSuchFieldException, IllegalAccessException {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null || mc.theWorld == null || mc.ingameGUI == null) {

            return;
        }
        if (event.type != ElementType.EXPERIENCE) return;
        new mcGui.DwarvenGui(Minecraft.getMinecraft());
        if (!References.on_skyblock()) {
            return;
        }
    }




}
