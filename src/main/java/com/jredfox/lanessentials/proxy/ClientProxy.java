package com.jredfox.lanessentials.proxy;

import com.jredfox.lanessentials.LanEssentialsConfig;
import com.jredfox.lanessentials.commands.network.CommandIP;
import com.jredfox.lanessentials.commands.network.CommandPublicIP;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraftforge.client.ClientCommandHandler;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preinit(){
		if(LanEssentialsConfig.ipCommands) {
	    	ClientCommandHandler.instance.registerCommand(new CommandIP());
	    	ClientCommandHandler.instance.registerCommand(new CommandPublicIP());
		}
	}
	
	@Override
	public int getServerPort(MinecraftServer server)
	{
		try
		{
			IntegratedServer is = (IntegratedServer) server;
			if(is.lanServerPing == null)
				return 0;
			String address = is.lanServerPing.address;
			int index = address.lastIndexOf(':');
			String port = index != -1 ? address.substring(index + 1) : address;
			return Integer.parseInt(port);
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		}
		return 0;
	}
}
