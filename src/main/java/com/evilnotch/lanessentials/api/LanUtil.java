package com.evilnotch.lanessentials.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.dosse.upnp.UPnP;
import com.evilnotch.lanessentials.CfgLanEssentials;
import com.evilnotch.lanessentials.MainMod;
import com.evilnotch.lanessentials.proxy.ClientProxy;
import com.evilnotch.lanessentials.proxy.ServerOnly;
import com.evilnotch.lib.main.MainJava;
import com.evilnotch.lib.main.loader.LoaderMain;
import com.evilnotch.lib.minecraft.util.EnumChatFormatting;
import com.evilnotch.lib.minecraft.util.PlayerUtil;
import com.evilnotch.lib.util.JavaUtil;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.HttpUtil;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldServer;

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
				sucess = UPnP.openPortUDP(port,"MCLanEssentials");
				//catch any exceptions from illegal arguments
				System.out.println("port opened UDP:" + sucess + " on port:" + port);
			}
			catch(Throwable e)
			{
				e.printStackTrace();
				return false;
			}
		}
		else if(tcp)
		{
			try
			{
				sucess = UPnP.openPortTCP(port,"MCLanEssentials");
				System.out.println("port opened TCP:" + sucess + " on port:" + port);
			}
			catch(Throwable e)
			{
				e.printStackTrace();
				return false;
			}
		}
		else
			System.out.println("UNKNOWN PROTOCAL Please Report This to Mod Auther As Only TCP and UDP are Supported!:\"" + protocal + "\"" );
		ports.put(port, protocal);
		System.out.println("mapping:" + UPnP.isUPnPAvailable());
		System.out.println("time:" + (System.currentTimeMillis()-time) + "ms");
		return sucess;
	}
	
	public static void stopPorts() 
	{
		if(!CfgLanEssentials.closePort)
			return;
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
    		else if(udp)
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
		System.out.println("here:" + LoaderMain.isClient + " hasPortedBefore:" + hasPorted);
        Thread t = new Thread(
     		   
        new Runnable() 
        { 
     	   public void run() 
     	   { 
     		   long time = System.currentTimeMillis();
     		   if(!UPnP.isUPnPUpNow() && hasPorted)
     		   {
     			   UPnP.refreshProgram();
     		   }
     		   boolean sucess = doPortForwarding(serverPort, protocal);
     		   if(!sucess)
     		   {
     			   PlayerUtil.broadCastMessege(EnumChatFormatting.RED + "Port Opening Failed:" + serverPort + " Protocal:" + protocal + " " + JavaUtil.getTime(time) + "ms");
     		   }
     		   else
     		   {
     			  PlayerUtil.broadCastMessege(EnumChatFormatting.AQUA + "Port Open:" + EnumChatFormatting.YELLOW + serverPort + EnumChatFormatting.BLUE + " Protocal:" + EnumChatFormatting.YELLOW + protocal + " " + JavaUtil.getTime(time) + "ms");
     		   }
     	   }
        });
        t.start();
	}
	
	public static String getMCPort(MinecraftServer server){
		return LoaderMain.isClient ? ClientProxy.getPort() : "" + ServerOnly.getServerPort(server);
	}
	
	/**
	 * for server side only integrated/dedicated
	 */
	public static String shareToLAN(int port,WorldServer w)
	{
		return MainMod.proxy.shareToLan(port,w);
	}
	public static String shareToLanClient(int port, GameType type, boolean allowCheats) {
		return ClientProxy.shareToLanClient(port, type, allowCheats);
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
	/**
	 * will return rnd if the port returns 0 or -1
	 */
	public static int getMCPortSafley(MinecraftServer server) 
	{
		Integer i = Integer.parseInt(LanUtil.getMCPort(server));
		if(i <= 0)
			return LanUtil.getRandomPort();
		else
			return i;
	}
}
