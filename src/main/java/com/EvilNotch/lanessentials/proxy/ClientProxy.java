package com.EvilNotch.lanessentials.proxy;

import com.EvilNotch.lanessentials.client.GuiEventReceiver;

import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends ServerProxy{
	
	@Override
	public void preinit(){
    	MinecraftForge.EVENT_BUS.register(new GuiEventReceiver());
	}

}
