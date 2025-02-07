package com.evilnotch.lanessentials.events;

import com.evilnotch.lanessentials.CfgLanEssentials;
import com.evilnotch.lanessentials.LanEssentials;
import com.evilnotch.lanessentials.api.CapUtil;
import com.evilnotch.lanessentials.api.LanFields;
import com.evilnotch.lanessentials.caps.CapAbility;
import com.evilnotch.lanessentials.caps.CapHome;
import com.evilnotch.lanessentials.caps.CapNick;
import com.evilnotch.lanessentials.packets.PacketNick;
import com.evilnotch.lib.minecraft.capability.CapContainer;
import com.evilnotch.lib.minecraft.capability.registry.CapabilityRegistry;
import com.evilnotch.lib.minecraft.network.NetWorkHandler;

import joptsimple.internal.Strings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

public class LEEventHandler {
	
	@SubscribeEvent(priority = EventPriority.LOW)
    public void login(PlayerLoggedInEvent e)
    {
	   	if(!(e.player instanceof EntityPlayerMP))
    		return;
	   	
	   	EntityPlayerMP p = (EntityPlayerMP) e.player;
	   	p.refreshDisplayName();
	   	NetWorkHandler.INSTANCE.sendTo(new PacketNick(p), p);
    }
	
	@SubscribeEvent
	public void track(PlayerEvent.StartTracking e) 
	{
		if (!(e.getTarget() instanceof EntityPlayer))
			return;
		
		//Handle Nickname Syncing
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
		if(e.getEntityPlayer() instanceof EntityPlayerMP)
		{
			EntityPlayerMP mp = (EntityPlayerMP) e.getEntityPlayer();
		   	CapNick capNick = (CapNick) CapabilityRegistry.getCapability(mp, LanFields.NICK);
		   	if(capNick != null && !capNick.nick.isEmpty())
		   		e.setDisplayname(capNick.nick);
		}
    }
	
//	@SubscribeEvent(priority = EventPriority.LOW)
//    public void login(PlayerLoggedInEvent e)
//    {
//	   	if(!(e.player instanceof EntityPlayerMP))
//    		return;
//	   	EntityPlayerMP player = (EntityPlayerMP) e.player;
//	   	CapContainer c = CapabilityRegistry.getCapContainer(player);
//	   	CapUtil.updateCaps(player,true);//update capability like fly and god
//	   	CapUtil.updateClientNicks(player);//grab all other people's nick names to you for tab
//    	CapUtil.updateNickName(player);//your nick to other players
//    	
//    	CapHome home = (CapHome) c.getCapability(new ResourceLocation(LanEssentials.MODID + ":" + "home"));
//    	//for when people change the config sync changes if and only if override is allowed changing it to false will allow for homes < max home count
//    	if(home.maxCount < CfgLanEssentials.maxHomeCount && CfgLanEssentials.overrideHomeCount)
//    		home.maxCount = CfgLanEssentials.maxHomeCount;
//    }
	
//	@SubscribeEvent(priority = EventPriority.LOW)
//    public void respawn(PlayerRespawnEvent e)
//    {
//	   	if(!(e.player instanceof EntityPlayerMP))
//    		return;
//	   	EntityPlayerMP player = (EntityPlayerMP) e.player;
//	   	CapUtil.updateCaps(player,false);
//    	CapUtil.updateNickName(player);
//    }
	
//	@SubscribeEvent(priority = EventPriority.LOW)
//    public void hurt(LivingAttackEvent e)
//    {
//		if(!(e.getEntity() instanceof EntityPlayerMP) || e.getSource() == DamageSource.OUT_OF_WORLD)
//			return;
//		EntityPlayerMP player = (EntityPlayerMP) e.getEntity();
//		if(player.capabilities.disableDamage)
//			return;//no need to continue if the job is already done
//		CapAbility cap = (CapAbility) CapabilityRegistry.getCapContainer(player).getCapability(new ResourceLocation(LanEssentials.MODID + ":" + "ability"));
//		if(cap.godEnabled)
//		{
//			player.capabilities.disableDamage = true;
//			player.sendPlayerAbilities();
//			e.setCanceled(true);
//		}
//    }

}
