package com.evilnotch.lanessentials.client;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiShareToLan;
import net.minecraftforge.client.event.GuiOpenEvent;

public class GuiEventReceiver {
   GuiScreen last = null;

   @SubscribeEvent
   public void eventHandlerPlayer(GuiOpenEvent event) 
   {
	  if(event.getGui() instanceof GuiShareToLan && !(event.getGui() instanceof GuiShareToLan2)) 
	  {
		 event.setGui(new GuiShareToLan2(this.last));
         System.out.println("CustomLanPorts: replacing GUI");
      } 
	  else 
	  {
         this.last = event.getGui();
      }
   }
}
