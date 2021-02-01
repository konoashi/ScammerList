package fr.konoashi.ScamerList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.GuiIngameForge;
import org.lwjgl.opengl.GL11;

public class mcGui extends Gui {


    public static class DwarvenGui {
        public DwarvenGui(Minecraft minecraft) {

            String tab = Tab.execute();

            if(tab.contains("Dwarven")) {


                String execute = Tab.execute();
                final FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
                final ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
                //final int screen_width = resolution.getScaledWidth();
                //final int screen_height = resolution.getScaledHeight();
                String text1 =  "\u272a Dwarven Mines";
                String text2 = " " + Tab.Commisions1(execute);
                String text3 = "\u27a4 Commissions:";
                String text4 = " " + Tab.Commisions2(execute);
                String text5 = "\u27a4 Mined Ores:";
                String text6 = "";
                String text7 = "\u27a4 Powders:";
                String text8 = Tab.Powder(execute);
                int color1 = 2926893;
                int color2 = 16761600;
                int color3 = 8607;
                int color4 = 16761600;
                int color5 = 11184810;
                int color7 = 16777215;
                int color8 = 43520;

                double pr = 0;

                if(BreakHandler.stringContainsNumber(text2)) {

                    char[] chars = text2.toCharArray();
                    StringBuilder sb = new StringBuilder();
                    for(char c : chars){
                        if(Character.isDigit(c)){
                            sb.append(c);
                            pr = Integer.parseInt(String.valueOf(sb));
                            if(pr >= 100) {
                                String number = String.valueOf(pr);
                                number = number.substring(0, number.indexOf(".")-1);
                                pr = Integer.parseInt(String.valueOf(number));

                            }

                        }
                    }

                    if(pr < 30.0) {
                        color2 = 11141120;
                    }

                    if(pr >= 30.0 && pr < 50.0) {
                        color2 = 16755200;
                    }

                    if(pr >= 50.0 && pr < 75.0) {
                        color2 = 16777045;
                    }

                    if(pr >= 75.0) {
                        color2 = 5635925;
                    }

                } else if(text2.contains("DONE")) {
                    text2 = "\u2714 "+text2.substring(1, text2.length());
                    color2 = 43520;
                }

                if(BreakHandler.stringContainsNumber(text4)) {
                    char[] chars = text4.toCharArray();
                    StringBuilder sb = new StringBuilder();
                    for(char c : chars){
                        if(Character.isDigit(c)){
                            sb.append(c);
                            pr = Integer.parseInt(String.valueOf(sb));
                            if(pr >= 100) {
                                String number = String.valueOf(pr);
                                number = number.substring(0, number.indexOf(".")-1);
                                pr = Integer.parseInt(String.valueOf(number));

                            }
                        }
                    }

                    if(pr < 30) {
                        color4 = 11141120;
                    }

                    if(pr >= 30 && pr < 50) {
                        color4 = 16755200;
                    }

                    if(pr >= 50 && pr < 75) {
                        color4 = 16777045;
                    }

                    if(pr >= 75) {
                        color4 = 5635925;
                    }

                } else if(text4.contains("DONE")) {
                    text4 = "\u2714 "+text4.substring(1, text4.length());;
                    color4 = 43520;
                }




                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                //final int x1 = screen_width/2;
                //final int y1 = screen_height/2;
                final int x1 = 3;
                final int y1 = 150;
                fr.drawString(text1, x1, y1, color1, true);

                final int text3_width = fr.getStringWidth(text3);
                final int x3 = x1+15 ;
                final int y3 = y1+20;
                fr.drawString(text3, x3, y3, color3, true);

                final int text2_width = fr.getStringWidth(text2);
                final int x2 = x3+10 ;
                final int y2 = y3+15;
                fr.drawString(text2, x2, y2, color2, true);

                final int text4_width = fr.getStringWidth(text4);
                final int y4 = y2+10;
                fr.drawString(text4, x2, y4, color4, true);

                final int text5_width = fr.getStringWidth(text5);
                final int y5 = y4+15;
                fr.drawString(text5, x3, y5, color5, true);

                final int text7_width = fr.getStringWidth(text5);
                final int y7 = y5+15;
                fr.drawString(text7, x3, y7, color7, true);

                final int text8_width = fr.getStringWidth(text5);
                final int y8 = y7+10;
                fr.drawString(text8, x2, y8, color8, true);
                GlStateManager.popMatrix();
                GL11.glColor3f(1, 1, 1);
                GlStateManager.disableRescaleNormal();
                GlStateManager.disableBlend();

                Minecraft.getMinecraft().getTextureManager().bindTexture(Gui.icons);








            }


        }
    }
}