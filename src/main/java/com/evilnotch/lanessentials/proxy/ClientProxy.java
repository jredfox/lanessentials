package com.evilnotch.lanessentials.proxy;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import com.evilnotch.lanessentials.api.LanFeilds;
import com.evilnotch.lanessentials.api.LanUtil;
import com.evilnotch.lanessentials.client.CommandIP;
import com.evilnotch.lanessentials.client.CommandPublicIP;
import com.evilnotch.lanessentials.client.GuiEventReceiver;
import com.evilnotch.lanessentials.client.LanFeildsClient;
import com.evilnotch.lib.api.ReflectionUtil;
import com.evilnotch.lib.minecraft.util.PlayerUtil;
import com.evilnotch.lib.util.JavaUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetworkSystem;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldServer;
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
	/**
	 * does nothing on the client side
	 */
	@Override
	public void dedicatedPreinit(){
		
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
	
	public static void closeLanClient(MinecraftServer server)
	{
		List<EntityPlayerMP> players = server.getPlayerList().getPlayers();
		for(EntityPlayerMP p : players)
			if(!PlayerUtil.isPlayerOwner(p))
				PlayerUtil.disconnectPlayer(p, new TextComponentString("Lan World Resetting...."));
		
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
        ReflectionUtil.setObject(server, false, IntegratedServer.class, LanFeildsClient.isPublic);
        JavaUtil.printTime(time, "done closing network Client:");
	}

	public static String shareToLanClient(int port, GameType type, boolean allowCheats) 
	{
	    try
	    {
	        int a = LanUtil.getRandomPort();
	        
	        if(port <= 0)
	     	   port = a;
		   if(port <= 0)
			   port = 25564;
	        Minecraft mc = Minecraft.getMinecraft();
	        IntegratedServer server = mc.getIntegratedServer();
	        server.getNetworkSystem().addLanEndpoint((InetAddress)null, port);
	        ReflectionUtil.setObject(server, true, IntegratedServer.class, LanFeildsClient.isPublic);
	        ThreadLanServerPing ping = new ThreadLanServerPing(server.getMOTD(), port + "");
	        ReflectionUtil.setObject(server, ping, IntegratedServer.class, LanFeildsClient.lanServerPing);
	           
	        ping.start();
	        server.getPlayerList().setGameType(type);
	        server.getPlayerList().setCommandsAllowedForAll(allowCheats);
	        mc.player.setPermissionLevel(allowCheats ? 4 : mc.player.getPermissionLevel());
	        return port + "";
	    }
	    catch (IOException var6)
	    {
	        return null;
	    }
	}
	public static String shareToLanClient(int port, WorldServer w) 
	{
        Minecraft mc = Minecraft.getMinecraft();
        IntegratedServer server = mc.getIntegratedServer();
        PlayerList list = server.getPlayerList();
        GameType type = (GameType) ReflectionUtil.getObject(list, PlayerList.class, LanFeilds.gameType);
        boolean allowCheats = w.getWorldInfo().areCommandsAllowed();
        if(type == null)
        	type = GameType.SURVIVAL;
        else
        {
        	allowCheats = (Boolean) ReflectionUtil.getObject(list, PlayerList.class, LanFeildsClient.commandsAllowedForAll);
        }
		return shareToLanClient(port,type,allowCheats);
	}

}
