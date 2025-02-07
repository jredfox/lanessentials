package com.evilnotch.lanessentials.events;

import com.evilnotch.lanessentials.api.CapHandler;
import com.evilnotch.lanessentials.api.LEFields;
import com.evilnotch.lanessentials.caps.CapAbility;
import com.evilnotch.lanessentials.caps.CapNick;
import com.evilnotch.lanessentials.packets.PacketNick;
import com.evilnotch.lib.minecraft.capability.registry.CapabilityRegistry;
import com.evilnotch.lib.minecraft.network.NetWorkHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

public class LEEventHandler {

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void login(PlayerLoggedInEvent e)
	{
		if (!(e.player instanceof EntityPlayerMP)) return;

		EntityPlayerMP p = (EntityPlayerMP) e.player;

		// Handles FLY, godMode, SPEED
		CapAbility cp = (CapAbility) CapabilityRegistry.getCapability(p, LEFields.ABILITY);
		p.capabilities.isFlying = cp.getIsFlying();
		cp.tick(p, CapabilityRegistry.getCapContainer(p));
		p.sendPlayerAbilities();

		// NickName
		p.refreshDisplayName();
		NetWorkHandler.INSTANCE.sendTo(new PacketNick(p), p);
	}

	@SubscribeEvent
	public void track(PlayerEvent.StartTracking e)
	{
		if (!(e.getTarget() instanceof EntityPlayer)) return;

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

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void respawn(PlayerRespawnEvent e)
	{
		if (!(e.player instanceof EntityPlayerMP)) return;
		EntityPlayerMP p = (EntityPlayerMP) e.player;
		CapAbility cp = (CapAbility) CapabilityRegistry.getCapability(p, LEFields.ABILITY);
		cp.tick(p, CapabilityRegistry.getCapContainer(p));

		// Re-SYNC NickName
		CapHandler.setNick(p, CapHandler.getNick(p), true);
	}

}
