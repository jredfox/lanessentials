package com.evilnotch.lanessentials.proxy;

import com.evilnotch.lanessentials.api.LanFeilds;
import com.evilnotch.lib.main.MainJava;
import com.evilnotch.lib.main.loader.LoaderMain;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class ServerProxy {

	public void preinit() {
	}
	
	public void dedicatedPreinit(){
		LanFeilds.cacheDedicatedMCP();
	}

	public void closeLan(MinecraftServer server) 
	{
		if(LoaderMain.isClient)
			ClientProxy.closeLanClient(server);
		else
			ServerOnly.closeLanServer(server);
	}

	public String shareToLan(int port,WorldServer w) {
		return LoaderMain.isClient ? ClientProxy.shareToLanClient(port,w) : ServerOnly.shareToLanServer(port);
	}

}
