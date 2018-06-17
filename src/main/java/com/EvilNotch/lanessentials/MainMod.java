package com.EvilNotch.lanessentials;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import com.EvilNotch.lib.Api.MCPMappings;
import com.EvilNotch.lib.Api.ReflectionUtil;
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
import net.minecraft.command.CommandDebug;
import net.minecraft.command.WrongUsageException;
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
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, dependencies = "required-after:evilnotchlib",acceptableRemoteVersions = "*")
public class MainMod
{

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	System.out.print("[Lan Essentials] Loading and Registering Commands");
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
    }
    
	@SubscribeEvent
    public void nickName(PlayerEvent.NameFormat e)
    {
		/*if(!(e.getEntityPlayer() instanceof EntityPlayerMP))
			return;
		EntityPlayerMP player = (EntityPlayerMP) e.getEntityPlayer();
		CapNick name = (CapNick) CapabilityReg.getCapabilityConatainer(player).getCapability(new ResourceLocation(Reference.MODID + ":" + "nick"));
    	if(!Strings.isNullOrEmpty(name.nick))
    		e.setDisplayname(name.nick);
    	SPacketPlayerListItem item = new SPacketPlayerListItem(SPacketPlayerListItem.Action.UPDATE_DISPLAY_NAME,player);
    	for(EntityPlayerMP p : player.mcServer.getPlayerList().getPlayers())
    	{
    		p.connection.sendPacket(item);
    	}*/
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
}