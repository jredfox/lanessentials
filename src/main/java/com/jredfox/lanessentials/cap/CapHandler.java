package com.jredfox.lanessentials.cap;

import com.evilnotch.lib.minecraft.capability.registry.CapabilityRegistry;
import com.evilnotch.lib.minecraft.network.NetWorkHandler;
import com.jredfox.lanessentials.LEFields;
import com.jredfox.lanessentials.packets.PacketNick;

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
		c.dn = p.getDisplayName().getFormattedText();//prevent duplicate packet
		if(sync)
			NetWorkHandler.INSTANCE.sendToAll(new PacketNick(p));
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
