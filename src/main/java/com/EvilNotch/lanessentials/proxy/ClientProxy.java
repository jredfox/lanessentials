package com.EvilNotch.lanessentials.proxy;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.EvilNotch.lanessentials.MainMod;
import com.EvilNotch.lanessentials.api.LanUtil;
import com.EvilNotch.lanessentials.api.SkinUpdater;
import com.EvilNotch.lanessentials.client.CommandIP;
import com.EvilNotch.lanessentials.client.CommandPublicIP;
import com.EvilNotch.lanessentials.client.GuiEventReceiver;
import com.EvilNotch.lanessentials.client.LanFeildsClient;
import com.EvilNotch.lib.Api.ReflectionUtil;
import com.EvilNotch.lib.main.MainJava;
import com.EvilNotch.lib.util.JavaUtil;
import com.mojang.authlib.GameProfile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
import net.minecraft.network.NetworkSystem;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.world.GameType;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends ServerProxy{
	
	@Override
	public void preinit(){
		LanFeildsClient.cacheMCP();
    	MinecraftForge.EVENT_BUS.register(new GuiEventReceiver());
    	ClientCommandHandler.instance.registerCommand(new CommandIP());
    	ClientCommandHandler.instance.registerCommand(new CommandPublicIP());
	}

	public static String getPort() {
		IntegratedServer server = Minecraft.getMinecraft().getIntegratedServer();
		ThreadLanServerPing ping = (ThreadLanServerPing) ReflectionUtil.getObject(server, IntegratedServer.class, LanFeildsClient.lanServerPing);
		if(ping == null)
			return "-1";
		String port = (String)ReflectionUtil.getObject(ping, ThreadLanServerPing.class, LanFeildsClient.address);
		if(port == null)
			return "-1";
		return port;
	}
	@Override
	public void closeLan(MinecraftServer server)
	{
		System.out.println("Stopping ports Client!");
		long time = System.currentTimeMillis();
		LanUtil.stopPorts();
		ThreadLanServerPing ping = (ThreadLanServerPing) ReflectionUtil.getObject(server, IntegratedServer.class, LanFeildsClient.lanServerPing);
        if (ping != null)
        {
        	System.out.println("Stopping Lan Client!");
        	ping.interrupt();
        	ReflectionUtil.setObject(server, null, IntegratedServer.class, LanFeildsClient.lanServerPing);
        	NetworkSystem network = server.getNetworkSystem();
        	if(network != null)
        	{
        		System.out.println("Terminating connections Client...");
        		network.terminateEndpoints();
        	}
        }
        JavaUtil.printTime(time, "done closing network Client:");
	}
	public String shareToLan(int port, GameType type, boolean allowCheats) 
	{
	    try
	    {
	        int a = LanUtil.getRandomPort();
	        
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

}
