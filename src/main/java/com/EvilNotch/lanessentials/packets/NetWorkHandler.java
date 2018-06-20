package com.EvilNotch.lanessentials.packets;

import com.EvilNotch.lanessentials.Reference;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetWorkHandler {
	
	public static SimpleNetworkWrapper INSTANCE;
	static int networkid = 0;
	
	public static void init()
	{
		INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);
		INSTANCE.registerMessage(PacketNickHandler.class, PacketDisplayNameRefresh.class, networkid++, Side.CLIENT);
	}
}