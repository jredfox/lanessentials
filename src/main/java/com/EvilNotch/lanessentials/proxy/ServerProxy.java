package com.EvilNotch.lanessentials.proxy;

import net.minecraft.server.MinecraftServer;

public class ServerProxy {

	public void preinit() {
	}

	public static String getServerPort(MinecraftServer server) {
		return "" + server.getServerPort();
	}

}
