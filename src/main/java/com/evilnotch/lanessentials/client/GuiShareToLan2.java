package com.evilnotch.lanessentials.client;

import java.io.IOException;

import com.evilnotch.lanessentials.api.LanUtil;
import com.evilnotch.lib.api.ReflectionUtil;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiShareToLan;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameType;

public class GuiShareToLan2 extends GuiShareToLan {
   private GuiTextField txtPort;
   private static int port;

   public GuiShareToLan2(GuiScreen par1GuiScreen) {
      super(par1GuiScreen);
   }

   public void initGui() {
      super.initGui();
      this.txtPort = new GuiTextField(50,this.fontRenderer, this.width / 2 - 155, 150, 150, 20);
      this.txtPort.setFocused(true);
      this.txtPort.setText("25565");
      port = 25565;
   }

   public void updateScreen() {
      this.txtPort.updateCursorCounter();
   }

   public void drawScreen(int par1, int par2, float par3) {
      super.drawScreen(par1, par2, par3);
      this.txtPort.drawTextBox();
      this.fontRenderer.drawString("Open on port: (10000 - 65535)", this.width / 2 - 155, 135, 16777215, false);
   }

   protected void keyTyped(char par1, int par2) {
      if(this.txtPort.isFocused()) {
         this.txtPort.textboxKeyTyped(par1, par2);
         int port = 0;

         try {
            port = Integer.valueOf(this.txtPort.getText()).intValue();
         } catch (NumberFormatException var5) {}
           	this.port = port;
         }

      if(par2 == 28 || par2 == 156) {
         try {
			super.actionPerformed((GuiButton)this.buttonList.get(0));
		} catch (IOException e) {}
      }
      
      ((GuiButton)this.buttonList.get(0)).enabled = port >= 10000 && port <= '\uffff';
   }
   @Override
   protected void actionPerformed(GuiButton button) throws IOException
   {
	   if(button.id != 101)
		   super.actionPerformed(button);
	   else
	   {
           this.mc.displayGuiScreen((GuiScreen)null);
           String s = LanUtil.shareToLanClient(this.port,GameType.getByName(getGameMode()), getCheats());
           
           ITextComponent itextcomponent;
           if (s != null)
           {
               itextcomponent = new TextComponentTranslation("commands.publish.started", new Object[] {s});
           }
           else
           {
               itextcomponent = new TextComponentString("commands.publish.failed");
           }

           this.mc.ingameGUI.getChatGUI().printChatMessage(itextcomponent);
	   }
   }
   
   public boolean getCheats() {
	   return (Boolean)ReflectionUtil.getObject(this, GuiShareToLan.class, LanFeildsClient.allowCheats);
   }

   public String getGameMode() 
   {
	   return (String)ReflectionUtil.getObject(this, GuiShareToLan.class, LanFeildsClient.gameMode);
   }

   public static int getPort() {
      return port;
   }
  
}
