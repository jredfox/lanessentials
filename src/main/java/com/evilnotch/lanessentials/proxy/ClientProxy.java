package com.evilnotch.lanessentials.proxy;

import com.evilnotch.lanessentials.client.CommandIP;
import com.evilnotch.lanessentials.client.CommandPublicIP;

import net.minecraftforge.client.ClientCommandHandler;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preinit(){
    	ClientCommandHandler.instance.registerCommand(new CommandIP());
    	ClientCommandHandler.instance.registerCommand(new CommandPublicIP());
	}
}
