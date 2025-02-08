package com.jredfox.lanessential.cap;

import com.evilnotch.lib.minecraft.capability.registry.CapabilityRegistry;
import com.evilnotch.lib.minecraft.network.NetWorkHandler;
import com.jredfox.lanessential.LEFields;
import com.jredfox.lanessential.packets.PacketNick;

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

	public static CapAbility getCapAbility(EntityPlayerMP p) 
	{
		return (CapAbility) CapabilityRegistry.getCapability(p, LEFields.ABILITY);
	}

}
