package com.evilnotch.lanessentials.proxy;

import java.net.InetAddress;
import java.util.List;

import com.evilnotch.lanessentials.api.LanFeilds;
import com.evilnotch.lanessentials.api.LanUtil;
import com.evilnotch.lib.api.ReflectionUtil;
import com.evilnotch.lib.minecraft.util.PlayerUtil;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetworkSystem;
import net.minecraft.network.rcon.RConThreadMain;
import net.minecraft.network.rcon.RConThreadQuery;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.PropertyManager;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ServerOnly {
	
	/**
	 * Closes ports and lan worlds thus world is only joinable via your own system
	 */
	public static void closeLanServer(MinecraftServer s) 
	{
		List<EntityPlayerMP> players = s.getPlayerList().getPlayers();
		for(EntityPlayerMP p : players)
			if(!PlayerUtil.isPlayerOwner(p))
				PlayerUtil.disconnectPlayer(p, new TextComponentString("Lan World Resetting"));
		
		DedicatedServer server = (DedicatedServer)s;
		System.out.println("Stopping ports!");
		LanUtil.stopPorts();
    	NetworkSystem network = server.getNetworkSystem();
    	if(network != null)
    	{
    		System.out.println("Terminating connections...");
    		network.terminateEndpoints();
    	}
	}
	
	/**
	 * shares to lan on dedicated server side
	 */
	public static String shareToLanServer(int port)
	{
		try
		{
			DedicatedServer server = (DedicatedServer) FMLCommonHandler.instance().getMinecraftServerInstance();
			InetAddress inet = InetAddress.getByName(server.getServerHostname());
			server.setServerPort(port);
			server.getNetworkSystem().addLanEndpoint((InetAddress)null, port);
            
			PropertyManager settings = (PropertyManager) ReflectionUtil.getObject(server, DedicatedServer.class, LanFeilds.propertyManager);
			if (settings.getBooleanProperty("enable-query", false))
            {
                RConThreadQuery rconQueryThread = new RConThreadQuery(server);
                ReflectionUtil.setObject(server, rconQueryThread, DedicatedServer.class, LanFeilds.rconQueryThread);
                rconQueryThread.startThread();
            }

            if (settings.getBooleanProperty("enable-rcon", false))
            {
                RConThreadMain rconThread = new RConThreadMain(server);
                ReflectionUtil.setObject(server, rconThread, DedicatedServer.class, LanFeilds.rconThread);
                rconThread.startThread();
            }
            
			return port + "";
		}
		catch(Exception e)
		{
			return null;
		}
	}

	public static int getServerPort(MinecraftServer server) {
		return server.getServerPort();
	}

}
