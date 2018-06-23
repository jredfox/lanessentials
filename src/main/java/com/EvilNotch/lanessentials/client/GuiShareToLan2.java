package com.EvilNotch.lanessentials.client;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

import com.EvilNotch.lib.Api.ReflectionUtil;
import com.dosse.upnp.UPnP;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiShareToLan;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameType;

public class GuiShareToLan2 extends GuiShareToLan {
   private GuiTextField txtPort;
   private static int port;
   public static Set<Integer> ports = new HashSet<Integer>();

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
           String s = shareToLAN(this.port,GameType.getByName(getGameMode()), getCheats());
           doPortForwarding(this.port);
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
	   return (Boolean)ReflectionUtil.getObject(this, GuiShareToLan.class, "allowCheats");
   }

   public String getGameMode() 
   {
	   return (String)ReflectionUtil.getObject(this, GuiShareToLan.class, "gameMode");
   }

   /**
    * On dedicated does nothing. On integrated, sets commandsAllowedForAll, gameType and allows external connections.
    */
   public String shareToLAN(int port,GameType type, boolean allowCheats)
   {
       try
       {
           int a = -1;

           try
           {
        	   if(port == -1)
        	   {
        		   a = HttpUtil.getSuitableLanPort();
        	   }
           }
           catch (IOException var5)
           {
               ;
           }

           if (a <= 0)
           {
               a = 25564;
           }
           if(port == -1)
        	   port = a;
           ports.add(port);
           IntegratedServer server = this.mc.getIntegratedServer();
           server.getNetworkSystem().addLanEndpoint((InetAddress)null, port);
           ReflectionUtil.setObject(server, true, IntegratedServer.class, "isPublic");
           ThreadLanServerPing ping = new ThreadLanServerPing(server.getMOTD(), port + "");
           ReflectionUtil.setFinalObject(server, ping, IntegratedServer.class, "lanServerPing");
           
           ping.start();
           server.getPlayerList().setGameType(type);
           server.getPlayerList().setCommandsAllowedForAll(allowCheats);
           this.mc.player.setPermissionLevel(allowCheats ? 4 : 0);
           return port + "";
       }
       catch (IOException var6)
       {
           return null;
       }
   }
	public static void doPortForwarding(int port)
	{
		System.out.println("debugger");
		long time = System.currentTimeMillis();
		UPnP.closePortUDP(port);
		UPnP.closePortTCP(port);
		
		System.out.println("port opened UDP:" + UPnP.openPortUDP(port) + " on port:" + port);
		System.out.println("port opened TCP:" + UPnP.openPortTCP(port) + " on port:" + port);
		System.out.println("mapping:" + UPnP.isUPnPAvailable());
		System.out.println("time:" + (System.currentTimeMillis()-time) + "ms");
	}
   
/*   @Override
   protected void actionPerformed(GuiButton button) throws IOException
   {

       try {
			 InetAddress IP = InetAddress.getLocalHost();
	         String i = (IP.getHostAddress());
	         ServerSocket sock = new ServerSocket();
	         sock.bind(new InetSocketAddress(IP, this.port)); 
	         System.out.println("failed!!!!");
			} catch (Exception e) {e.printStackTrace();}
       
	   super.actionPerformed(button);

   }
*/
   public static int getPort() {
      return port;
   }
  
}
