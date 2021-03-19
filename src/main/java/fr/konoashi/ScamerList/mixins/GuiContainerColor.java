package fr.konoashi.ScamerList.mixins;


import fr.konoashi.ScamerList.utils.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;


@Mixin(GuiContainer.class)
public abstract class GuiContainerColor extends GuiScreen {

    @Shadow protected abstract void drawSlot(Slot p_drawSlot_1_);

    @Shadow protected abstract void drawItemStack(ItemStack p_drawItemStack_1_, int p_drawItemStack_2_, int p_drawItemStack_3_, String p_drawItemStack_4_);

    @Shadow protected abstract void drawGuiContainerBackgroundLayer(float v, int i, int i1);

    private final GuiContainer that = (GuiContainer) (Object) this;



    @Inject(method = "drawSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItemOverlayIntoGUI(Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V"))
    private void renderBids(Slot slot, CallbackInfo info)
    {
        if (this.that instanceof GuiChest)
        {
            //System.out.println("Unwork1");
            GuiChest chest = (GuiChest)this.that;
            if (chest.lowerChestInventory.getDisplayName().getUnformattedText().contains("Auctions:"))
            {
                //System.out.println("Unwork2");
                this.renderHubOverlay(slot);
            }
        }
    }



    private void renderHubOverlay(Slot slot)
    {
        if (slot.getStack() != null && slot.getStack().hasTagCompound())
        {
            //System.out.println("Work1");
            NBTTagCompound compound = slot.getStack().getTagCompound().getCompoundTag("display");

            if (compound.getTagId("Lore") == 9)
            {
                //System.out.println("Work2");
                NBTTagList list = compound.getTagList("Lore", 8);

                if (list.tagCount() > 0)
                {
                    //System.out.println("Work3");
                    for (int j1 = 0; j1 < list.tagCount(); ++j1)
                    {

                        int slotLeft = slot.xDisplayPosition;
                        int slotTop = slot.yDisplayPosition;
                        int slotRight = slotLeft + 16;
                        int slotBottom = slotTop + 16;
                        String lore = EnumChatFormatting.getTextWithoutFormattingCodes(list.getStringTagAt(j1));

                        String ToolTip = slot.getStack().getTooltip(Minecraft.getMinecraft().thePlayer, true).toString();

                        if (ToolTip.contains("Sold")) {
                            return;
                        }

                        if (ToolTip.contains("Margin:") && slot.getStack().getDisplayName().contains("Crystal Fragment")) {

                            if (ToolTip.contains("LowestBIN")) {
                                ToolTip = ToolTip.substring(0, ToolTip.indexOf("LowestBIN")-5);
                                System.out.println(ToolTip);
                            }

                            int i = Integer.parseInt(ToolTip.substring(ToolTip.indexOf("Margin:")+8, ToolTip.length()-1).replaceAll(" ", ""));

                            if (i<0) {



                            } else if (i>5000) {
                                int color = References.to32BitColor(190, 45, 255, 45);
                                this.zLevel = 260.0F;
                                this.drawGradientRect(slotLeft, slotTop, slotRight, slotBottom, color, color);
                                this.zLevel = 0.0F;
                                break;

                            } else {
                                int color = References.to32BitColor(190, 255, 215, 0);
                                this.zLevel = 260.0F;
                                this.drawGradientRect(slotLeft, slotTop, slotRight, slotBottom, color, color);
                                this.zLevel = 0.0F;
                                break;

                            }

                        }
                    }
                }
            }
        }
    }



    private void drawCurrentSelectedPet(Slot slot)
    {
        if (slot.getStack() != null && slot.getStack().hasTagCompound())
        {
            NBTTagCompound compound = slot.getStack().getTagCompound().getCompoundTag("display");

            if (compound.getTagId("Lore") == 9)
            {
                NBTTagList list = compound.getTagList("Lore", 8);

                if (list.tagCount() > 0)
                {
                    for (int j1 = 0; j1 < list.tagCount(); ++j1)
                    {
                        int slotLeft = slot.xDisplayPosition;
                        int slotTop = slot.yDisplayPosition;
                        int slotRight = slotLeft + 16;
                        int slotBottom = slotTop + 16;
                        String lore = EnumChatFormatting.getTextWithoutFormattingCodes(list.getStringTagAt(j1));
                        int green = References.to32BitColor(150, 85, 255, 85);

                        if (lore.startsWith("Click to despawn"))
                        {
                            this.drawGradientRect(slotLeft, slotTop, slotRight, slotBottom, green, green);
                            break;
                        }
                    }
                }
            }
        }
    }


}
