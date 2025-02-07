package com.evilnotch.lanessentials.api;

import com.evilnotch.lanessentials.caps.CapNick;
import com.evilnotch.lanessentials.caps.CapPlayer;
import com.evilnotch.lanessentials.packets.PacketNick;
import com.evilnotch.lib.minecraft.capability.registry.CapabilityRegistry;
import com.evilnotch.lib.minecraft.network.NetWorkHandler;

import net.minecraft.entity.player.EntityPlayerMP;

public class CapHandler {
	/**
	 * Sets the nickname on serverside without syncing to the client
	 */
	public static void setNick(EntityPlayerMP p, String nick, boolean sync)
	{
		CapNick c = (CapNick) CapabilityRegistry.getCapability(p, LEFields.NICK);
		c.nick = nick;
		p.refreshDisplayName();
		if(sync)
			NetWorkHandler.INSTANCE.sendToTrackingAndPlayer(new PacketNick(p), p);
	}

	public static String getNick(EntityPlayerMP p)
	{
		return ((CapNick) CapabilityRegistry.getCapability(p, LEFields.NICK)).nick;
	}

	public static CapPlayer getCapAbility(EntityPlayerMP p) 
	{
		return (CapPlayer) CapabilityRegistry.getCapability(p, LEFields.ABILITY);
	}

}
