package com.EvilNotch.lanessentials.proxy;

import com.EvilNotch.lanessentials.MainMod;
import com.EvilNotch.lanessentials.api.SkinUpdater;
import com.EvilNotch.lanessentials.client.CommandIP;
import com.EvilNotch.lanessentials.client.CommandPublicIP;
import com.EvilNotch.lanessentials.client.GuiEventReceiver;
import com.EvilNotch.lanessentials.client.LanFeildsClient;
import com.EvilNotch.lib.Api.ReflectionUtil;
import com.EvilNotch.lib.main.MainJava;
import com.EvilNotch.lib.util.JavaUtil;
import com.mojang.authlib.GameProfile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
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
    	
		//cache client's skin so when going to single player world hosting it don't take forever
		try
		{
			long time = System.currentTimeMillis();
			GameProfile profile = Minecraft.getMinecraft().getSession().getProfile();
			SkinUpdater.getSkinData(profile.getName().toLowerCase());
			JavaUtil.printTime(time, "Done Caching Client's Skin:");
	    	if(!SkinUpdater.skinCache.exists() && !SkinUpdater.uuids.isEmpty())
	    	{
	    		System.out.println("Saving UUID Cache:");
	    		SkinUpdater.saveSkinCache();
	    	}
		}
		catch(Exception ee)
		{
			System.out.println("Unable to cache client's skin things are not going to work so smoothly! retrying when world is created");
			ee.printStackTrace();
		}
	}

	public static String getPort() {
		IntegratedServer server = Minecraft.getMinecraft().getIntegratedServer();
		ThreadLanServerPing ping = (ThreadLanServerPing) ReflectionUtil.getObject(server, IntegratedServer.class, LanFeildsClient.lanServerPing);
		if(ping == null)
			return "-1";
		return (String)ReflectionUtil.getObject(ping, ThreadLanServerPing.class, LanFeildsClient.address);
	}

}
