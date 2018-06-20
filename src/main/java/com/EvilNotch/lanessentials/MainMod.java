package com.EvilNotch.lanessentials;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONObject;

import com.EvilNotch.lanessentials.capabilities.CapAbility;
import com.EvilNotch.lanessentials.capabilities.CapCape;
import com.EvilNotch.lanessentials.capabilities.CapHome;
import com.EvilNotch.lanessentials.capabilities.CapNick;
import com.EvilNotch.lanessentials.capabilities.CapSkin;
import com.EvilNotch.lanessentials.commands.CommandCape;
import com.EvilNotch.lanessentials.commands.CommandFeed;
import com.EvilNotch.lanessentials.commands.CommandFly;
import com.EvilNotch.lanessentials.commands.CommandGod;
import com.EvilNotch.lanessentials.commands.CommandHeal;
import com.EvilNotch.lanessentials.commands.CommandHome;
import com.EvilNotch.lanessentials.commands.CommandKick;
import com.EvilNotch.lanessentials.commands.CommandNick;
import com.EvilNotch.lanessentials.commands.CommandSetHealth;
import com.EvilNotch.lanessentials.commands.CommandSetHome;
import com.EvilNotch.lanessentials.commands.CommandSetHunger;
import com.EvilNotch.lanessentials.commands.CommandSkin;
import com.EvilNotch.lanessentials.commands.CommandSpeed;
import com.EvilNotch.lanessentials.commands.vanilla.CommandBanIp;
import com.EvilNotch.lanessentials.commands.vanilla.CommandBanPlayer;
import com.EvilNotch.lanessentials.commands.vanilla.CommandDeOp;
import com.EvilNotch.lanessentials.commands.vanilla.CommandOp;
import com.EvilNotch.lanessentials.commands.vanilla.CommandPardonIp;
import com.EvilNotch.lanessentials.commands.vanilla.CommandPardonPlayer;
import com.EvilNotch.lanessentials.packets.NetWorkHandler;
import com.EvilNotch.lanessentials.packets.PacketDisplayNameRefresh;
import com.EvilNotch.lib.Api.MCPMappings;
import com.EvilNotch.lib.Api.ReflectionUtil;
import com.EvilNotch.lib.main.Config;
import com.EvilNotch.lib.main.MainJava;
import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityContainer;
import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityReg;
import com.EvilNotch.lib.minecraft.events.CapeFixEvent;
import com.EvilNotch.lib.minecraft.events.SkinFixEvent;
import com.EvilNotch.lib.minecraft.registry.GeneralRegistry;
import com.EvilNotch.lib.util.JavaUtil;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;

import joptsimple.internal.Strings;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandDebug;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Items;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.network.play.server.SPacketPlayerListItem.AddPlayerData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, dependencies = "required-after:evilnotchlib")
public class MainMod
{

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	Class clazz = YggdrasilMinecraftSessionService.class;
    	System.out.print("[Lan Essentials] Loading and Registering Commands session:");
    	CfgLanEssentials.loadConfig(event.getModConfigurationDirectory() );
    	MinecraftForge.EVENT_BUS.register(this);
    	CapabilityReg.registerCapProvider(new CapabilityProvider());
    	
    	GeneralRegistry.registerCommand(new CommandSetHome());
    	GeneralRegistry.registerCommand(new CommandHome());
    	GeneralRegistry.registerCommand(new CommandHeal());
    	GeneralRegistry.registerCommand(new CommandSetHealth() );
    	GeneralRegistry.registerCommand(new CommandFeed());
    	GeneralRegistry.registerCommand(new CommandSetHunger());
    	GeneralRegistry.registerCommand(new CommandFly());
    	GeneralRegistry.registerCommand(new CommandGod());
    	GeneralRegistry.registerCommand(new CommandSkin());
    	GeneralRegistry.registerCommand(new CommandSpeed());
    	GeneralRegistry.registerCommand(new CommandNick());
    	GeneralRegistry.registerCommand(new CommandCape());
    	
    	//server commands redone for client
    	if(MainJava.isClient)
    	{
    		GeneralRegistry.registerCommand(new CommandKick());
    		GeneralRegistry.registerCommand(new CommandBanIp());
    		GeneralRegistry.registerCommand(new CommandBanPlayer());
    		GeneralRegistry.registerCommand(new CommandDeOp());
    		GeneralRegistry.registerCommand(new CommandOp());
    		GeneralRegistry.registerCommand(new CommandPardonIp());
    		GeneralRegistry.registerCommand(new CommandPardonPlayer());
    		GeneralRegistry.registerCommand(new CommandDebug());
    	}
    	/*
    	 * adds ability without ASM to have more domain urls for skins/capes
    	 */
        ReflectionUtil.setFinalObject(null, Config.skinDomains, YggdrasilMinecraftSessionService.class, "WHITELISTED_DOMAINS");
    }
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	NetWorkHandler.init();
    }
    
	@SubscribeEvent
    public void nickName(PlayerEvent.NameFormat e)
    {
        if(!(e.getEntityPlayer() instanceof EntityPlayerMP))
        {
            return;
        }
        EntityPlayerMP player = (EntityPlayerMP) e.getEntityPlayer();
        if(player.connection == null)
        	return;
        CapNick name = (CapNick) CapabilityReg.getCapability(player, new ResourceLocation(Reference.MODID + ":" + "nick"));
        if(name == null)
        {
        	System.out.println("here caps null?????");
        	return;
        }
     
        if(!Strings.isNullOrEmpty(name.nick))
        {
        	e.setDisplayname(name.nick);
        }
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
	
	@SubscribeEvent(priority = EventPriority.LOW)
    public void login(PlayerLoggedInEvent e)
    {
	   	if(!(e.player instanceof EntityPlayerMP))
    		return;
	   	EntityPlayerMP player = (EntityPlayerMP) e.player;
    	CapAbility cap = (CapAbility) CapabilityReg.getCapabilityConatainer(player).getCapability(new ResourceLocation(Reference.MODID + ":" + "ability"));
    	updateCaps(player,cap,true);
    	updateNickName(player);
    }
	
	public void updateCaps(EntityPlayerMP player, CapAbility cap,boolean updateFlying) 
	{
		PlayerCapabilities pcap = player.capabilities;
		boolean used = false;
		if(!pcap.allowFlying && cap.flyEnabled)
		{
			pcap.allowEdit = true;
			if(updateFlying)
				pcap.isFlying = cap.isFlying;
			used = true;
		}
		if(!pcap.disableDamage && cap.godEnabled)
		{
			pcap.disableDamage = true;
			used = true;
		}
		if(used)
			player.sendPlayerAbilities();
	}

	@SubscribeEvent(priority = EventPriority.LOW)
    public void respawn(PlayerRespawnEvent e)
    {
	   	if(!(e.player instanceof EntityPlayerMP))
    		return;
	   	EntityPlayerMP player = (EntityPlayerMP) e.player;
	   	CapAbility cap = (CapAbility) CapabilityReg.getCapabilityConatainer(player).getCapability(new ResourceLocation(Reference.MODID + ":" + "ability"));
    	updateCaps(player,cap,false);
    	updateNickName(player);
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
    public void nametag(PlayerEvent.StartTracking e)
    {
		if(!(e.getTarget() instanceof EntityPlayer))
			return;
		EntityPlayerMP u = (EntityPlayerMP) e.getEntityPlayer();
		EntityPlayerMP other = (EntityPlayerMP) e.getTarget();
		if(u.connection == null || other.connection == null)
		{
			System.out.println("returning player not properlly logged in");
			return;
		}
		else if(u.getEntityId() == other.getEntityId() )
		{
			System.out.println("returning player is tracking itself userName:" + u.getName());
			return;
		}
		System.out.println("firing @:" + u.getName() + " with:" + other.getName());
		updateTrackNickName(u,other);
    }
	/**
	 * optimized version for when requesting entity is about to start tracking the player without updating it to everyone
	 */
	public static void updateTrackNickName(EntityPlayerMP request,EntityPlayerMP newPlayer) 
	{
    	CapNick name = (CapNick) CapabilityReg.getCapability(newPlayer, new ResourceLocation(Reference.MODID + ":" + "nick"));
    	if(Strings.isNullOrEmpty(name.nick))
    	{
    		System.out.println("returning null string");
    		return;
    	}
    	newPlayer.refreshDisplayName();
    	SPacketPlayerListItem item = new SPacketPlayerListItem();
        AddPlayerData apd = item.new AddPlayerData(newPlayer.getGameProfile(), newPlayer.ping, newPlayer.interactionManager.getGameType(), new TextComponentString(name.nick));
        ReflectionUtil.setObject(item, SPacketPlayerListItem.Action.UPDATE_DISPLAY_NAME, SPacketPlayerListItem.class, MCPMappings.getField(SPacketPlayerListItem.class, "action"));
        item.getEntries().add(apd);
    	
        request.connection.sendPacket(item);
        NetWorkHandler.INSTANCE.sendTo(new PacketDisplayNameRefresh(name.nick, newPlayer.getEntityId()), request);
	}
	public static void updateNickName(EntityPlayerMP player) 
	{
    	CapNick name = (CapNick) CapabilityReg.getCapability(player, new ResourceLocation(Reference.MODID + ":" + "nick"));
    	if(Strings.isNullOrEmpty(name.nick))
    		return;
    	player.refreshDisplayName();
    	SPacketPlayerListItem item = new SPacketPlayerListItem();
        AddPlayerData apd = item.new AddPlayerData(player.getGameProfile(), player.ping, player.interactionManager.getGameType(), new TextComponentString(name.nick));
        ReflectionUtil.setObject(item, SPacketPlayerListItem.Action.UPDATE_DISPLAY_NAME, SPacketPlayerListItem.class, MCPMappings.getField(SPacketPlayerListItem.class, "action"));
        item.getEntries().add(apd);
        
        Set<? extends EntityPlayer> li = player.getServerWorld().getEntityTracker().getTrackingPlayers(player);
        Set<EntityPlayerMP> players = new HashSet();
        for(EntityPlayer p : li)
        	players.add((EntityPlayerMP)p);
        players.add(player);
    	
        for(EntityPlayerMP p : players)
        {
            p.connection.sendPacket(item);
            if(!p.equals(player))
            {
            	NetWorkHandler.INSTANCE.sendTo(new PacketDisplayNameRefresh(name.nick, player.getEntityId()), p);
            }
        }
	}
}