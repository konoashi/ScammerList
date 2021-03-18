package fr.konoashi.ScamerList;

import fr.konoashi.ScamerList.enums.ScammerHead;
import fr.konoashi.ScamerList.utils.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class TagScam {




    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {

        Minecraft mc = Minecraft.getMinecraft();
        if (!References.on_skyblock()) {

            return;
        }
        if (mc.thePlayer == null || mc.theWorld == null || mc.ingameGUI == null) {

            return;
        }

        if (mc.thePlayer.getName().equals("Saynons") || mc.thePlayer.getName().equals("konoashi") || mc.thePlayer.getName().equals("Adrien662")) {
            List<EntityItem> items = mc.theWorld.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(mc.thePlayer.posX - 300, mc.thePlayer.posY - 300, mc.thePlayer.posZ - 300, mc.thePlayer.posX + 300, mc.thePlayer.posY + 300, mc.thePlayer.posZ + 300));
        for (Entity nearEntity : items) {
            if (nearEntity instanceof EntityItem) {
                if (((EntityItem) nearEntity).getEntityItem().getDisplayName().contains("Summoning Eye")) {
                    ItemStack Summ = ((EntityItem) nearEntity).getEntityItem();
                    BlockPos itempos = nearEntity.getPosition();
                    Entity renderViewEntity = Minecraft.getMinecraft().getRenderViewEntity();
                    EntityPlayer player = Minecraft.getMinecraft().thePlayer;
                    //nearEntity.setCustomNameTag("summ");

                    double viewX = renderViewEntity.prevPosX + (renderViewEntity.posX - renderViewEntity.prevPosX) * (double) event.partialTicks;
                    double viewY = renderViewEntity.prevPosY + (renderViewEntity.posY - renderViewEntity.prevPosY) * (double) event.partialTicks;
                    double viewZ = renderViewEntity.prevPosZ + (renderViewEntity.posZ - renderViewEntity.prevPosZ) * (double) event.partialTicks;

                    int iconSize = 25;


                    if (renderViewEntity == nearEntity) {
                        continue;
                    }

                    double x = nearEntity.lastTickPosX + (nearEntity.posX - nearEntity.lastTickPosX) * (double) event.partialTicks;
                    double y = nearEntity.lastTickPosY + (nearEntity.posY - nearEntity.lastTickPosY) * (double) event.partialTicks;
                    double z = nearEntity.lastTickPosZ + (nearEntity.posZ - nearEntity.lastTickPosZ) * (double) event.partialTicks;

                    x -= viewX;
                    y -= viewY;
                    z -= viewZ;

                    double distanceScale = Math.max(1, renderViewEntity.getPositionVector().distanceTo(nearEntity.getPositionVector()) / 10F);

                    if (References.on_skyblock()) {
                        y += nearEntity.height + 1.50F + (iconSize * distanceScale) / 40F;
                    } else {
                        y += nearEntity.height / 2F + 0.25F;
                    }

                    float f = 1.6F;
                    float f1 = 0.016666668F * f;
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(x, y, z);
                    GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
                    GlStateManager.scale(-f1, -f1, f1);

                    GlStateManager.scale(distanceScale, distanceScale, distanceScale);

                    GlStateManager.disableLighting();
                    GlStateManager.depthMask(false);
                    GlStateManager.disableDepth();
                    GlStateManager.enableBlend();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                    GlStateManager.enableTexture2D();
                    GlStateManager.color(1, 1, 1, 1);
                    GlStateManager.enableAlpha();


                    String nameOverlay = EnumChatFormatting.BOLD + "" + EnumChatFormatting.DARK_PURPLE + "\u26a0 " + "Summoning eye / " + EnumChatFormatting.DARK_PURPLE + Math.round((nearEntity.posX + nearEntity.posY + nearEntity.posZ) - (Minecraft.getMinecraft().thePlayer.posX + Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.posZ)) + " Meters";
                    Minecraft.getMinecraft().fontRendererObj.drawString(nameOverlay, -Minecraft.getMinecraft().fontRendererObj.getStringWidth(nameOverlay) / 2F, 25 / 2F + 15, -1, true);

                    GlStateManager.enableDepth();
                    GlStateManager.depthMask(true);
                    GlStateManager.enableLighting();
                    GlStateManager.disableBlend();
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    GlStateManager.popMatrix();
                }

                /*System.out.println(nearEntity.getDisplayName());
                System.out.println(nearEntity.getPosition());
                System.out.println(nearEntity.getCollisionBorderSize());
                System.out.println(((EntityItem) nearEntity).lifespan);
                System.out.println(nearEntity.getNBTTagCompound());
                System.out.println(nearEntity.getAir());
                System.out.println(nearEntity.getPersistentID().toString());
                System.out.println(nearEntity.getEntityWorld().playerEntities.toArray().toString());*/
            }
        }
    }

        if (!ToggleCommand.oruoToggled) {
            return;
        }




        List<EntityPlayer> players = Minecraft.getMinecraft().theWorld.playerEntities;
        for (EntityPlayer player : players) {
            if (Tab.execute().contains(player.getName())) {
                Entity renderViewEntity = Minecraft.getMinecraft().getRenderViewEntity();
                List<Entity> entities = Minecraft.getMinecraft().theWorld.getLoadedEntityList();
                for (Entity entity : entities) {
                    if (entity.getName().equalsIgnoreCase(player.getName())) {

                        if (References.isNPC(entity)) {
                            //System.out.println(entity.getName());
                            //System.out.println(entity.getUniqueID());
                            entity.setCustomNameTag("NPC");
                            return;
                        }
                        if (entity.posX == -2.500 && entity.posY == 70.000 && entity.posZ == 69.500) {

                            return;
                        }

                        if (ChatAutoReport.response == null || ChatAutoReport.response2 == null) {
                            return;
                        }



                        String UUID = player.getUniqueID().toString().replaceAll("-", "");


                        if (ChatAutoReport.response.toString().contains(UUID) || ChatAutoReport.response2.toString().contains(UUID)) {
                            References.set_head(ScammerHead.IS_SCAMMER);

                        } else if (UUID.equals(null)) {
                            References.set_head(ScammerHead.NO_RECOGNIZED);

                        } else if (Main.LOCAL_SCAMMER_LIST.contains(UUID)) {

                            References.set_head(ScammerHead.LOCAL_SCAMMER);

                        } else {
                            References.set_head(ScammerHead.NO_SCAMMER);

                        }

                        double viewX = renderViewEntity.prevPosX + (renderViewEntity.posX - renderViewEntity.prevPosX) * (double) event.partialTicks;
                        double viewY = renderViewEntity.prevPosY + (renderViewEntity.posY - renderViewEntity.prevPosY) * (double) event.partialTicks;
                        double viewZ = renderViewEntity.prevPosZ + (renderViewEntity.posZ - renderViewEntity.prevPosZ) * (double) event.partialTicks;

                        int iconSize = 25;

                        if (renderViewEntity == entity) {
                            continue;
                        }

                        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) event.partialTicks;
                        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) event.partialTicks;
                        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) event.partialTicks;

                        x -= viewX;
                        y -= viewY;
                        z -= viewZ;

                        double distanceScale = Math.max(1, renderViewEntity.getPositionVector().distanceTo(entity.getPositionVector()) / 10F);

                        if (References.on_skyblock()) {
                            y += entity.height + 1.50F + (iconSize * distanceScale) / 40F;
                        } else {
                            y += entity.height / 2F + 0.25F;
                        }

                        float f = 1.6F;
                        float f1 = 0.016666668F * f;
                        GlStateManager.pushMatrix();
                        GlStateManager.translate(x, y, z);
                        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                        GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
                        GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
                        GlStateManager.scale(-f1, -f1, f1);

                        GlStateManager.scale(distanceScale, distanceScale, distanceScale);

                        GlStateManager.disableLighting();
                        GlStateManager.depthMask(false);
                        GlStateManager.disableDepth();
                        GlStateManager.enableBlend();
                        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                        GlStateManager.enableTexture2D();
                        GlStateManager.color(1, 1, 1, 1);
                        GlStateManager.enableAlpha();

                        if (entity.getName().equalsIgnoreCase(player.getName())) {
                            if (Math.sqrt(entity.getPosition().distanceSq(mc.thePlayer.getPosition())) < 30) {
                                String nameOverlay = EnumChatFormatting.BOLD + "" + EnumChatFormatting.GREEN + entity.getName() + "  /  " + EnumChatFormatting.LIGHT_PURPLE + Math.round((entity.posX + entity.posY + entity.posZ) - (Minecraft.getMinecraft().thePlayer.posX + Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.posZ)) + " Meters";

                                if (References.get_head() == ScammerHead.IS_SCAMMER) {
                                    nameOverlay = EnumChatFormatting.BOLD + "" + EnumChatFormatting.DARK_RED + "[\u2718] "  + entity.getName() + " is a recognized scammer" ;

                                }
                                if (References.get_head() == ScammerHead.LOCAL_SCAMMER) {

                                    nameOverlay = EnumChatFormatting.BOLD + "" + EnumChatFormatting.RED + "[\u2718] " + entity.getName() + " is a recognized local scammer" ;
                                }
                                if (References.get_head() == ScammerHead.NO_SCAMMER) {

                                    nameOverlay = EnumChatFormatting.BOLD + "" + EnumChatFormatting.GREEN + "[\u2714] " + entity.getName() + " is not a recognized scammer" ;
                                }
                                if (References.get_head() == ScammerHead.NO_RECOGNIZED) {

                                    nameOverlay = EnumChatFormatting.BOLD + "" + EnumChatFormatting.GOLD + "[\u26a0] "  + entity.getName() + " is not a recognized player" ;
                                }
                                Minecraft.getMinecraft().fontRendererObj.drawString(nameOverlay, -Minecraft.getMinecraft().fontRendererObj.getStringWidth(nameOverlay) / 2F, 25 / 2F + 15, -1, true);
                            }
                        } else if (entity.getName().contains("✯") && !entity.getName().contains("Skeleton Master") && !entity.getName().contains("Shadow Assassin")) {
                            final String nameOverlay = EnumChatFormatting.GREEN + "   ✯   " + EnumChatFormatting.LIGHT_PURPLE + Math.round((entity.posX + entity.posY + entity.posZ) - (Minecraft.getMinecraft().thePlayer.posX + Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.posZ)) + " Meters";
                            if (Math.sqrt(entity.getPosition().distanceSq(mc.thePlayer.getPosition())) < 30) {

                                new Thread(() -> {
                                Minecraft.getMinecraft().fontRendererObj.drawString(nameOverlay, -Minecraft.getMinecraft().fontRendererObj.getStringWidth(nameOverlay) / 2F, 25 / 2F + 15, -1, true);

                            }).start();
                            }
                        }
                        GlStateManager.enableDepth();
                        GlStateManager.depthMask(true);
                        GlStateManager.enableLighting();
                        GlStateManager.disableBlend();
                        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                        GlStateManager.popMatrix();
                    }
                    if (References.on_skyblock()) {
                        if (entity.getName().contains("Wither Key") || entity.getName().contains("Blood Key") || entity.getName().contains("Superboom TNT") || entity.getName().contains("Blessing") || entity.getName().contains("Revive Stone")) {
                            double viewX = renderViewEntity.prevPosX + (renderViewEntity.posX - renderViewEntity.prevPosX) * (double) event.partialTicks;
                            double viewY = renderViewEntity.prevPosY + (renderViewEntity.posY - renderViewEntity.prevPosY) * (double) event.partialTicks;
                            double viewZ = renderViewEntity.prevPosZ + (renderViewEntity.posZ - renderViewEntity.prevPosZ) * (double) event.partialTicks;

                            int iconSize = 25;

                            if (renderViewEntity == entity) {
                                continue;
                            }

                            double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) event.partialTicks;
                            double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) event.partialTicks;
                            double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) event.partialTicks;

                            x -= viewX;
                            y -= viewY;
                            z -= viewZ;

                            double distanceScale = Math.max(1, renderViewEntity.getPositionVector().distanceTo(entity.getPositionVector()) / 10F);

                            if (References.on_skyblock()) {
                                y += entity.height + 0.75F + (iconSize * distanceScale) / 40F;
                            } else {
                                y += entity.height / 2F + 0.25F;
                            }

                            float f = 1.6F;
                            float f1 = 0.016666668F * f;
                            GlStateManager.pushMatrix();
                            GlStateManager.translate(x, y, z);
                            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                            GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
                            GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
                            GlStateManager.scale(-f1, -f1, f1);

                            GlStateManager.scale(distanceScale, distanceScale, distanceScale);

                            GlStateManager.disableLighting();
                            GlStateManager.depthMask(false);
                            GlStateManager.disableDepth();
                            GlStateManager.enableBlend();
                            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                            GlStateManager.enableTexture2D();
                            GlStateManager.color(1, 1, 1, 1);
                            GlStateManager.enableAlpha();

                            final String nameOverlay = EnumChatFormatting.GREEN + entity.getName() + "  /  " + EnumChatFormatting.LIGHT_PURPLE + Math.round((entity.posX + entity.posY + entity.posZ) - (Minecraft.getMinecraft().thePlayer.posX + Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.posZ)) + " Meters";
                            if (Math.sqrt(entity.getPosition().distanceSq(mc.thePlayer.getPosition())) < 50) {
                                Minecraft.getMinecraft().fontRendererObj.drawString(nameOverlay, -Minecraft.getMinecraft().fontRendererObj.getStringWidth(nameOverlay) / 2F, 25 / 2F + 10, -1, true);
                            }
                            GlStateManager.enableDepth();
                            GlStateManager.depthMask(true);
                            GlStateManager.enableLighting();
                            GlStateManager.disableBlend();
                            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                            GlStateManager.popMatrix();
                        }
                    }
                    if (References.on_skyblock()) {
                        if (entity.getName().contains("Skeleton Master")) {
                            double viewX = renderViewEntity.prevPosX + (renderViewEntity.posX - renderViewEntity.prevPosX) * (double) event.partialTicks;
                            double viewY = renderViewEntity.prevPosY + (renderViewEntity.posY - renderViewEntity.prevPosY) * (double) event.partialTicks;
                            double viewZ = renderViewEntity.prevPosZ + (renderViewEntity.posZ - renderViewEntity.prevPosZ) * (double) event.partialTicks;

                            int iconSize = 25;

                            if (renderViewEntity == entity) {
                                continue;
                            }

                            double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) event.partialTicks;
                            double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) event.partialTicks;
                            double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) event.partialTicks;

                            x -= viewX;
                            y -= viewY;
                            z -= viewZ;

                            double distanceScale = Math.max(1, renderViewEntity.getPositionVector().distanceTo(entity.getPositionVector()) / 10F);

                            if (References.on_skyblock()) {
                                y += entity.height + 0.75F + (iconSize * distanceScale) / 40F;
                            } else {
                                y += entity.height / 2F + 0.25F;
                            }

                            float f = 1.6F;
                            float f1 = 0.016666668F * f;
                            GlStateManager.pushMatrix();
                            GlStateManager.translate(x, y, z);
                            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                            GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
                            GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
                            GlStateManager.scale(-f1, -f1, f1);

                            GlStateManager.scale(distanceScale, distanceScale, distanceScale);

                            GlStateManager.disableLighting();
                            GlStateManager.depthMask(false);
                            GlStateManager.disableDepth();
                            GlStateManager.enableBlend();
                            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                            GlStateManager.enableTexture2D();
                            GlStateManager.color(1, 1, 1, 1);
                            GlStateManager.enableAlpha();

                            final String nameOverlay = EnumChatFormatting.RED + "▲ " + entity.getName() + "  /  " + EnumChatFormatting.LIGHT_PURPLE + Math.round((entity.posX + entity.posY + entity.posZ) - (Minecraft.getMinecraft().thePlayer.posX + Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.posZ)) + " Meters";
                            if (Math.sqrt(entity.getPosition().distanceSq(mc.thePlayer.getPosition())) < 25) {
                                Minecraft.getMinecraft().fontRendererObj.drawString(nameOverlay, -Minecraft.getMinecraft().fontRendererObj.getStringWidth(nameOverlay) / 2F, 25 / 2F + 10, -1, true);
                            }
                            GlStateManager.enableDepth();
                            GlStateManager.depthMask(true);
                            GlStateManager.enableLighting();
                            GlStateManager.disableBlend();
                            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                            GlStateManager.popMatrix();
                        }
                    }
                }
            }
        }


    }
}
