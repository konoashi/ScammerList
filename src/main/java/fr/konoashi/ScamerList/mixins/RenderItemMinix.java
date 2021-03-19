package fr.konoashi.ScamerList.mixins;

import fr.konoashi.ScamerList.utils.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItem.class)
public class RenderItemMinix {
        private static final ResourceLocation RARITY = new ResourceLocation("scammerlist","gui/rarity.png");



        @Inject(method = "renderItemIntoGUI(Lnet/minecraft/item/ItemStack;II)V", at = @At("HEAD"))
        private void renderRarity(ItemStack itemStack, int xPosition, int yPosition, CallbackInfo info)
        {

                String ToolTip = itemStack.getTooltip(Minecraft.getMinecraft().thePlayer, true).toString();

                if (ToolTip.contains("Sold")) {
                        return;
                }

                if (ToolTip.contains("Margin:") && itemStack.getDisplayName().contains("Crystal Fragment")) {

                        int i = Integer.parseInt(ToolTip.substring(ToolTip.indexOf("Margin:")+8, ToolTip.length()-1).replaceAll(" ", ""));

                        if (i<0) {
                                float alpha = 1F;
                                float colorRed = 1F/255 * 240;
                                float colorGreen= 1F/255 * 10;
                                float colorBlue= 1F/255 * 20;
                                GlStateManager.pushMatrix();
                                GlStateManager.disableLighting();
                                GlStateManager.disableDepth();
                                GlStateManager.enableBlend();
                                GlStateManager.enableAlpha();
                                Minecraft.getMinecraft().getTextureManager().bindTexture(RARITY);
                                GlStateManager.color(colorRed,colorGreen, colorBlue, alpha);
                                GlStateManager.blendFunc(770, 771);
                                GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_BLEND);
                                Gui.drawModalRectWithCustomSizedTexture(xPosition, yPosition, 0, 0, 16, 16, 16, 16);
                                GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
                                GlStateManager.enableLighting();
                                GlStateManager.enableDepth();
                                GlStateManager.disableAlpha();
                                GlStateManager.popMatrix();


                        } else if (i>5000) {
                                float alpha = 1F;
                                float colorRed = 1F/255 * 10;
                                float colorGreen= 1F/255 * 240;
                                float colorBlue= 1F/255 * 20;
                                GlStateManager.disableLighting();
                                GlStateManager.disableDepth();
                                GlStateManager.enableBlend();
                                GlStateManager.enableAlpha();
                                Minecraft.getMinecraft().getTextureManager().bindTexture(RARITY);
                                GlStateManager.color(colorRed,colorGreen, colorBlue, alpha);
                                GlStateManager.blendFunc(770, 771);
                                GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_BLEND);
                                Gui.drawModalRectWithCustomSizedTexture(xPosition, yPosition, 0, 0, 16, 16, 16, 16);
                                GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
                                GlStateManager.enableLighting();
                                GlStateManager.enableDepth();
                                GlStateManager.disableAlpha();


                        } else {

                                float alpha = 1F;
                                float colorRed = 1F/255 * 255;
                                float colorGreen= 1F/255 * 170;
                                float colorBlue= 1F/255 * 10;
                                GlStateManager.pushMatrix();
                                GlStateManager.disableLighting();
                                GlStateManager.disableDepth();
                                GlStateManager.enableBlend();
                                GlStateManager.enableAlpha();
                                Minecraft.getMinecraft().getTextureManager().bindTexture(RARITY);
                                GlStateManager.color(colorRed,colorGreen, colorBlue, alpha);
                                GlStateManager.blendFunc(770, 771);
                                GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_BLEND);
                                Gui.drawModalRectWithCustomSizedTexture(xPosition, yPosition, 0, 0, 16, 16, 16, 16);
                                GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
                                GlStateManager.enableLighting();
                                GlStateManager.enableDepth();
                                GlStateManager.disableAlpha();
                                GlStateManager.popMatrix();

                        }
                } if (ToolTip.contains("Margin: Sold") && itemStack.getDisplayName().contains("Crystal Fragment")) {

                float alpha = 1F;
                float colorRed = 1F/255 * 0;
                float colorGreen= 1F/255 * 0;
                float colorBlue= 1F/255 * 0;
                GlStateManager.pushMatrix();
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.enableBlend();
                GlStateManager.enableAlpha();
                Minecraft.getMinecraft().getTextureManager().bindTexture(RARITY);
                GlStateManager.color(colorRed,colorGreen, colorBlue, alpha);
                GlStateManager.blendFunc(770, 771);
                GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_BLEND);

                Gui.drawModalRectWithCustomSizedTexture(xPosition, yPosition, 0, 0, 16, 16, 16, 16);
                GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
                GlStateManager.disableAlpha();
                GlStateManager.popMatrix();

            }



        }


}
