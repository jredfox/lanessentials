package com.evilnotch.lanessentials.events;

import com.evilnotch.lanessentials.LEFields;
import com.evilnotch.lanessentials.cap.CapAbility;
import com.evilnotch.lanessentials.cap.CapHandler;
import com.evilnotch.lanessentials.cap.CapNick;
import com.evilnotch.lanessentials.packets.PacketNick;
import com.evilnotch.lib.minecraft.capability.registry.CapabilityRegistry;
import com.evilnotch.lib.minecraft.network.NetWorkHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

public class LEEventHandler {

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void login(PlayerLoggedInEvent e)
	{
		if (!(e.player instanceof EntityPlayerMP)) 
			return;
		EntityPlayerMP p = (EntityPlayerMP) e.player;

		// Handles FLY, godMode, SPEED
		CapAbility ca = (CapAbility) CapabilityRegistry.getCapability(p, LEFields.ABILITY);
		ca.sync(p);
		
		// NickName
		p.refreshDisplayName();
		NetWorkHandler.INSTANCE.sendTo(new PacketNick(p), p);
	}
	
	@SubscribeEvent
	public void clone(PlayerEvent.Clone e)
	{
		if (!(e.getEntityPlayer() instanceof EntityPlayerMP)) 
			return;
		EntityPlayerMP np = (EntityPlayerMP) e.getEntityPlayer();
		CapHandler.setNick(np, CapHandler.getNick(np), true);// Re-SYNC NickName
	}
	
	@SubscribeEvent
	public void respawn(PlayerRespawnEvent e)
	{
		if (!(e.player instanceof EntityPlayerMP)) return;
		EntityPlayerMP mp = (EntityPlayerMP) e.player;
		CapAbility ca = (CapAbility) CapabilityRegistry.getCapability(mp, LEFields.ABILITY);
		ca.sync(mp);
	}

	@SubscribeEvent
	public void track(PlayerEvent.StartTracking e)
	{
		if (!(e.getTarget() instanceof EntityPlayer)) 
			return;

		// Handle Nickname Syncing
		EntityPlayerMP targ = (EntityPlayerMP) e.getTarget();
		EntityPlayerMP u = (EntityPlayerMP) e.getEntityPlayer();
		targ.refreshDisplayName();
		NetWorkHandler.INSTANCE.sendTo(new PacketNick(targ), u);
	}

	/**
	 * Support Custom Display Names However let other mods override this
	 */
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void nickName(PlayerEvent.NameFormat e)
	{
		if (e.getEntityPlayer() instanceof EntityPlayerMP) 
		{
			EntityPlayerMP mp = (EntityPlayerMP) e.getEntityPlayer();
			CapNick capNick = (CapNick) CapabilityRegistry.getCapability(mp, LEFields.NICK);
			if (capNick != null && !capNick.nick.isEmpty())
				e.setDisplayname(capNick.nick);
		}
	}

}
