package com.jredfox.lanessentials.proxy;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;

public class ServerProxy extends CommonProxy {
	
	@Override
	public int getServerPort(MinecraftServer server	)
	{
		return server.getServerPort();
	}

}
