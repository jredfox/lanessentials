package com.EvilNotch.lanessentials.proxy;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import com.EvilNotch.lanessentials.api.LanFeilds;
import com.EvilNotch.lanessentials.api.LanUtil;
import com.EvilNotch.lib.Api.ReflectionUtil;
import com.EvilNotch.lib.main.MainJava;
import com.EvilNotch.lib.minecraft.EntityUtil;
import com.EvilNotch.lib.util.JavaUtil;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetworkSystem;
import net.minecraft.network.rcon.RConThreadMain;
import net.minecraft.network.rcon.RConThreadQuery;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ServerProxy {

	public void preinit() {
	}
	
	public void dedicatedPreinit(){
		LanFeilds.cacheDedicatedMCP();
	}

	public void closeLan(MinecraftServer server) 
	{
		if(MainJava.isClient)
			ClientProxy.closeLanClient(server);
		else
			ServerOnly.closeLanServer(server);
	}

	public String shareToLan(int port,WorldServer w) {
		return MainJava.isClient ? ClientProxy.shareToLanClient(port,w) : ServerOnly.shareToLanServer(port);
	}

}
