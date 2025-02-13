package com.jredfox.lanessentials.proxy;

import net.minecraft.server.MinecraftServer;

public class ServerProxy extends CommonProxy {
	
	@Override
	public int getServerPort(MinecraftServer server	)
	{
		return server.getServerPort();
	}

}
