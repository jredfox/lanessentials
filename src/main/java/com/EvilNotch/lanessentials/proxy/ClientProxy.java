package com.EvilNotch.lanessentials.proxy;

import com.EvilNotch.lanessentials.client.CommandIP;
import com.EvilNotch.lanessentials.client.CommandPublicIP;
import com.EvilNotch.lanessentials.client.GuiEventReceiver;
import com.EvilNotch.lanessentials.client.LanFeildsClient;
import com.EvilNotch.lib.Api.ReflectionUtil;
import com.EvilNotch.lib.minecraft.registry.GeneralRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends ServerProxy{
	
	@Override
	public void preinit(){
		LanFeildsClient.cacheMCP();
    	MinecraftForge.EVENT_BUS.register(new GuiEventReceiver());
    	ClientCommandHandler.instance.registerCommand(new CommandIP());
    	ClientCommandHandler.instance.registerCommand(new CommandPublicIP());
	}

	public static String getPort() {
		IntegratedServer server = Minecraft.getMinecraft().getIntegratedServer();
		ThreadLanServerPing ping = (ThreadLanServerPing) ReflectionUtil.getObject(server, IntegratedServer.class, LanFeildsClient.lanServerPing);
		if(ping == null)
			return "-1";
		return (String)ReflectionUtil.getObject(ping, ThreadLanServerPing.class, LanFeildsClient.address);
	}

}
