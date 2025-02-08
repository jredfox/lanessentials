package com.jredfox.lanessential.proxy;

import com.jredfox.lanessential.client.CommandIP;
import com.jredfox.lanessential.client.CommandPublicIP;

import net.minecraftforge.client.ClientCommandHandler;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preinit(){
    	ClientCommandHandler.instance.registerCommand(new CommandIP());
    	ClientCommandHandler.instance.registerCommand(new CommandPublicIP());
	}
}
