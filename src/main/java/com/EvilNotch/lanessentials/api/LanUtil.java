package com.EvilNotch.lanessentials.api;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.EvilNotch.lanessentials.client.LanFeildsClient;
import com.EvilNotch.lanessentials.proxy.ClientProxy;
import com.EvilNotch.lanessentials.proxy.ServerProxy;
import com.EvilNotch.lib.Api.ReflectionUtil;
import com.EvilNotch.lib.main.MainJava;
import com.EvilNotch.lib.minecraft.EntityUtil;
import com.EvilNotch.lib.minecraft.EnumChatFormatting;
import com.EvilNotch.lib.util.JavaUtil;
import com.dosse.upnp.UPnP;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameType;

public class LanUtil {
	
	public static HashMap<Integer,String> ports = new HashMap<Integer,String>();
	
	public static boolean doPortForwarding(int port,String protocal)
	{
		hasPorted = true;
		long time = System.currentTimeMillis();
		boolean tcp = protocal.equals("TCP");
		boolean udp =  protocal.equals("UDP");
		boolean sucess = false;
		if(udp)
		{
			try
			{
				sucess = UPnP.openPortUDP(port);
				//catch any exceptions from illegal arguments
				System.out.println("port opened UDP:" + sucess + " on port:" + port);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		else if(tcp)
		{
			try
			{
				sucess = UPnP.openPortTCP(port);
				System.out.println("port opened TCP:" + sucess + " on port:" + port);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		ports.put(port, protocal);
		System.out.println("mapping:" + UPnP.isUPnPAvailable());
		System.out.println("time:" + (System.currentTimeMillis()-time) + "ms");
		return sucess;
	}
	
	public static void stopPorts() 
	{
    	Iterator<Map.Entry<Integer,String>> it = ports.entrySet().iterator();
    	while(it.hasNext())
    	{
    		Map.Entry<Integer, String> pair = it.next();
    		int port = pair.getKey();
    		String protocal = pair.getValue();
    		boolean tcp = protocal.equals("TCP");
    		boolean udp =  protocal.equals("UDP");
    		if(tcp)
    			UPnP.closePortTCP(port);
    		if(udp)
    			UPnP.closePortUDP(port);
    	}
       	ports.clear();
	}

	/**
	 * creates port forwarding on it's own new thread so it doesn't freeze for seconds
	 */
	public static boolean hasPorted = false;
	public static void schedulePortForwarding(int serverPort, String protocal) 
	{
		System.out.println("here:" + MainJava.isClient + " hasPortedBefore:" + hasPorted);
        Thread t = new Thread(
     		   
        new Runnable() 
        { 
     	   public void run() 
     	   { 
     		   long time = System.currentTimeMillis();
     		   if(!UPnP.isUPnPUpNow() && hasPorted)
     		   {
     			   UPnP.refresh();
     		   }
     		   boolean sucess = doPortForwarding(serverPort,protocal);
     		   if(!sucess)
     		   {
     			   EntityUtil.broadCastMessege(EnumChatFormatting.RED + "Port Opening Failed:" + serverPort + " Protocal:" + protocal + " " + JavaUtil.getTime(time) + "ms");
     		   }
     		   else
     		   {
     			  EntityUtil.broadCastMessege(EnumChatFormatting.AQUA + "Port Open:" + EnumChatFormatting.YELLOW + serverPort + EnumChatFormatting.BLUE + " Protocal:" + EnumChatFormatting.YELLOW + protocal + " " + JavaUtil.getTime(time) + "ms");
     		   }
     	   }
        });
        t.start();
	}
	
	public static String getMCPort(MinecraftServer server){
		return MainJava.isClient ? ClientProxy.getPort() : ServerProxy.getServerPort(server);
	}
	/**
	  * On dedicated does nothing. On integrated, sets commandsAllowedForAll, gameType and allows external connections.
	 */
   public static String shareToLAN(int port,GameType type, boolean allowCheats)
   {
       try
       {
           int a = getRandomPort();
           
           if(port <= 0)
        	   port = a;
           Minecraft mc = Minecraft.getMinecraft();
           IntegratedServer server = mc.getIntegratedServer();
           server.getNetworkSystem().addLanEndpoint((InetAddress)null, port);
           ReflectionUtil.setObject(server, true, IntegratedServer.class, LanFeildsClient.isPublic);
           ThreadLanServerPing ping = new ThreadLanServerPing(server.getMOTD(), port + "");
           ReflectionUtil.setObject(server, ping, IntegratedServer.class, LanFeildsClient.lanServerPing);
           
           ping.start();
           server.getPlayerList().setGameType(type);
           server.getPlayerList().setCommandsAllowedForAll(allowCheats);
           mc.player.setPermissionLevel(allowCheats ? 4 : 0);
           return port + "";
       }
       catch (IOException var6)
       {
           return null;
       }
   }
	public static int getRandomPort()
	{
        int a = -1;
        try
        {
     		 a = HttpUtil.getSuitableLanPort();
        }
        catch (IOException var5)
        {
            ;
        }
        if (a <= 0)
        {
            a = 25564;
        }
        return a;
	}
}
