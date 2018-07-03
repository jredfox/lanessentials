package com.EvilNotch.lanessentials.proxy;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.EvilNotch.lanessentials.api.LanFeilds;
import com.EvilNotch.lanessentials.api.LanUtil;
import com.EvilNotch.lib.Api.ReflectionUtil;
import com.EvilNotch.lib.minecraft.EntityUtil;
import com.EvilNotch.lib.util.JavaUtil;

import net.minecraft.network.NetworkSystem;
import net.minecraft.network.rcon.RConThreadMain;
import net.minecraft.network.rcon.RConThreadQuery;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.PropertyManager;
import net.minecraft.world.GameType;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ServerProxy {

	public void preinit() {
	}

	public static String getServerPort(MinecraftServer server) {
		return "" + server.getServerPort();
	}

	/**
	 * Closes ports and lan worlds thus world is only joinable via your own system
	 */
	public void closeLan(MinecraftServer s) 
	{
		System.out.println("Stopping ports!");
		long time = System.currentTimeMillis();
		DedicatedServer server = (DedicatedServer)s;
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
	public String shareToLan(int port, GameType type, boolean allowCheats)
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
		catch(IOException e)
		{
			return null;
		}
	}

}
