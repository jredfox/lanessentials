package com.EvilNotch.lanessentials.events;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.EvilNotch.lanessentials.CfgLanEssentials;
import com.EvilNotch.lanessentials.Reference;
import com.EvilNotch.lanessentials.api.CapUtil;
import com.EvilNotch.lanessentials.api.SkinUpdater;
import com.EvilNotch.lanessentials.capabilities.CapAbility;
import com.EvilNotch.lanessentials.capabilities.CapCape;
import com.EvilNotch.lanessentials.capabilities.CapHome;
import com.EvilNotch.lanessentials.capabilities.CapNick;
import com.EvilNotch.lanessentials.capabilities.CapSkin;
import com.EvilNotch.lib.minecraft.EntityUtil;
import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityContainer;
import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityReg;
import com.EvilNotch.lib.util.JavaUtil;
import com.EvilNotch.lib.util.primitive.IntObj;

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
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

public class EventHandler {
	
	@SubscribeEvent
    public void skinFix(PlayerLoggedInEvent e)
    {
		//isn't multi thread since it crashes otherwise
		if(e.player instanceof EntityPlayerMP)
			SkinUpdater.fireSkinEvent((EntityPlayerMP) e.player);
    }
	@SubscribeEvent
	public void skinNo(PlayerLoggedOutEvent e)
	{
		noSkins.remove(e.player.getName());
	}
	
	@SubscribeEvent(priority = EventPriority.LOW)
    public void login(PlayerLoggedInEvent e)
    {
	   	if(!(e.player instanceof EntityPlayerMP))
    		return;
	   	EntityPlayerMP player = (EntityPlayerMP) e.player;
	   	CapabilityContainer c = CapabilityReg.getCapabilityConatainer(player);
	   	CapUtil.updateCaps(player,c,true);//update capability like fly and god
	   	CapUtil.updateClientNicks(player);//grab all other people's nick names to you for tab
    	CapUtil.updateNickName(player);//your nick to other players
    	
    	CapHome home = (CapHome) c.getCapability(new ResourceLocation(Reference.MODID + ":" + "home"));
    	//for when people change the config sync changes if and only if override is allowed changing it to false will allow for homes < max home count
    	if(home.maxCount < CfgLanEssentials.maxHomeCount && CfgLanEssentials.overrideHomeCount)
    	{
    		System.out.println("changing home count:" + home.maxCount + " > " + CfgLanEssentials.maxHomeCount);
    		home.maxCount = CfgLanEssentials.maxHomeCount;
    	}
    }
	
	@SubscribeEvent(priority = EventPriority.LOW)
    public void respawn(PlayerRespawnEvent e)
    {
	   	if(!(e.player instanceof EntityPlayerMP))
    		return;
	   	EntityPlayerMP player = (EntityPlayerMP) e.player;
	   	CapabilityContainer c = CapabilityReg.getCapabilityConatainer(player);
	   	CapUtil.updateCaps(player,c,false);
    	CapUtil.updateNickName(player);
    }
	
	@SubscribeEvent(priority = EventPriority.LOW)
    public void hurt(LivingAttackEvent e)
    {
		if(!(e.getEntity() instanceof EntityPlayerMP) || e.getSource() == DamageSource.OUT_OF_WORLD)
			return;
		EntityPlayerMP player = (EntityPlayerMP) e.getEntity();
		if(player.capabilities.disableDamage)
			return;//no need to continue if the job is already done
		CapAbility cap = (CapAbility) CapabilityReg.getCapabilityConatainer(player).getCapability(new ResourceLocation(Reference.MODID + ":" + "ability"));
		if(cap.godEnabled)
		{
			player.capabilities.disableDamage = true;
			player.sendPlayerAbilities();
			e.setCanceled(true);
		}
    }
	
	@SubscribeEvent
    public void nickName(PlayerEvent.NameFormat e)
    {
        if(!(e.getEntityPlayer() instanceof EntityPlayerMP))
            return;
        EntityPlayerMP player = (EntityPlayerMP) e.getEntityPlayer();
        if(player.connection == null)
        	return;
        CapNick name = (CapNick) CapabilityReg.getCapability(player, new ResourceLocation(Reference.MODID + ":" + "nick"));
        if(name == null)
        {
        	System.out.println("event is firing before player has loaded from file :(");
        	return;
        }
        if(!Strings.isNullOrEmpty(name.nick))
        	e.setDisplayname(name.nick);
    }
	@SubscribeEvent
    public void nametag(PlayerEvent.StartTracking e)
    {
		if(!(e.getTarget() instanceof EntityPlayer))
			return;
		EntityPlayerMP u = (EntityPlayerMP) e.getEntityPlayer();
		EntityPlayerMP other = (EntityPlayerMP) e.getTarget();
		if(u.connection == null || other.connection == null)
			return;
		else if(u.getEntityId() == other.getEntityId() )
			return;
//		System.out.println("firing @:" + u.getName() + " with:" + other.getName());
		CapUtil.updateTrackNickName(u,other);
    }
	
	@SubscribeEvent
    public void capeCap(CapeFixEvent e)
    {
		CapCape cape = (CapCape) CapabilityReg.getCapabilityConatainer(e.getEntityPlayer()).getCapability(new ResourceLocation(Reference.MODID + ":" + "cape"));
		e.url = cape.url;
		if(CfgLanEssentials.overrideCape)
			e.overrideCape = true;
    }
	@SubscribeEvent
    public void skinCap(SkinFixEvent e)
    {
		EntityPlayerMP player = (EntityPlayerMP) e.getEntityPlayer();
		CapabilityContainer container = CapabilityReg.getCapabilityConatainer(player);
		CapSkin skin = (CapSkin) container.getCapability(new ResourceLocation(Reference.MODID + ":" + "skin"));
		
		e.newSkin = skin.skin;
		e.isAlexURL = skin.isAlex;
    }
	
	 /**
	  * for when mojang servers don't respond quick enough try again in 32 seconds
	  */
	 public static final HashMap<String,IntObj> noSkins = new HashMap();
	 public static boolean hasOnline = false;
	 public static int sTick = 0;
	 @SubscribeEvent
	 public void skinTick(ServerTickEvent e)
	 {
		 if(e.phase != Phase.END || noSkins.isEmpty())
			 return;
		 
		 Set<String> toRemove = new HashSet();
		 
		 if(sTick % 30 == 0)
		 {
			 sTick = 0;
			 //reset if server has gone offline here
			 if(hasOnline)
			 {
				 hasOnline = JavaUtil.isOnline("api.mojang.com");
				 if(!hasOnline)
					 noSkins.clear();
			 }
		 }
		 Iterator<Map.Entry<String,IntObj>> it = noSkins.entrySet().iterator();
		 while(it.hasNext())
		 {
			 Map.Entry<String,IntObj> pair = it.next();
			 IntObj i = pair.getValue();
			 if(i.integer == 32*20)
			 {
				if(!hasOnline)
				{
					hasOnline = JavaUtil.isOnline("api.mojang.com");
					if(!hasOnline)
					{
						toRemove.addAll(noSkins.keySet());
						System.out.println("server is offline will not try to re-instantiate new skins again");
						break;
					}
				}
				 String name = pair.getKey();
				 EntityPlayerMP player = (EntityPlayerMP) EntityUtil.getPlayer(name);
				 SkinUpdater.fireSkinEvent(player);
				 //if not reset add to the remove list
				 if(i.integer != 0)
					 toRemove.add(name);
			 }
			 else
				 i.integer++;
		 }
		 for(String n : toRemove)
		 {
			 noSkins.remove(n);
		 }
		 sTick++;
	 }

}
