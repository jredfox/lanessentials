package com.jredfox.lanessentials.proxy;

import com.jredfox.lanessentials.client.CommandIP;
import com.jredfox.lanessentials.client.CommandPublicIP;

import net.minecraftforge.client.ClientCommandHandler;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preinit(){
    	ClientCommandHandler.instance.registerCommand(new CommandIP());
    	ClientCommandHandler.instance.registerCommand(new CommandPublicIP());
	}
}
