package com.EvilNotch.lanessentials;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import com.EvilNotch.lanessentials.api.LanUtil;
import com.EvilNotch.lanessentials.api.SkinUpdater;
import com.EvilNotch.lanessentials.capabilities.CapAbility;
import com.EvilNotch.lanessentials.capabilities.CapCape;
import com.EvilNotch.lanessentials.capabilities.CapNick;
import com.EvilNotch.lanessentials.capabilities.CapSkin;
import com.EvilNotch.lanessentials.capabilities.CapSpeed;
import com.EvilNotch.lanessentials.client.CommandIP;
import com.EvilNotch.lanessentials.client.CommandPublicIP;
import com.EvilNotch.lanessentials.commands.CommandCape;
import com.EvilNotch.lanessentials.commands.CommandEnderChest;
import com.EvilNotch.lanessentials.commands.CommandFeed;
import com.EvilNotch.lanessentials.commands.CommandFly;
import com.EvilNotch.lanessentials.commands.CommandFlySpeed;
import com.EvilNotch.lanessentials.commands.CommandGod;
import com.EvilNotch.lanessentials.commands.CommandHat;
import com.EvilNotch.lanessentials.commands.CommandHeal;
import com.EvilNotch.lanessentials.commands.CommandHome;
import com.EvilNotch.lanessentials.commands.CommandKick;
import com.EvilNotch.lanessentials.commands.CommandNick;
import com.EvilNotch.lanessentials.commands.CommandNuke;
import com.EvilNotch.lanessentials.commands.CommandServerIP;
import com.EvilNotch.lanessentials.commands.CommandSetHealth;
import com.EvilNotch.lanessentials.commands.CommandSetHome;
import com.EvilNotch.lanessentials.commands.CommandSetHunger;
import com.EvilNotch.lanessentials.commands.CommandSkin;
import com.EvilNotch.lanessentials.commands.CommandSmite;
import com.EvilNotch.lanessentials.commands.CommandWalkSpeed;
import com.EvilNotch.lanessentials.commands.CommandWorkBench;
import com.EvilNotch.lanessentials.commands.vanilla.CMDBanIp;
import com.EvilNotch.lanessentials.commands.vanilla.CMDBanPlayer;
import com.EvilNotch.lanessentials.commands.vanilla.CMDDeOp;
import com.EvilNotch.lanessentials.commands.vanilla.CMDOp;
import com.EvilNotch.lanessentials.commands.vanilla.CMDPardonIp;
import com.EvilNotch.lanessentials.commands.vanilla.CMDPardonPlayer;
import com.EvilNotch.lanessentials.events.CapeFixEvent;
import com.EvilNotch.lanessentials.events.SkinFixEvent;
import com.EvilNotch.lanessentials.packets.NetWorkHandler;
import com.EvilNotch.lanessentials.packets.PacketDisplayNameRefresh;
import com.EvilNotch.lanessentials.proxy.ServerProxy;
import com.EvilNotch.lib.Api.MCPMappings;
import com.EvilNotch.lib.Api.ReflectionUtil;
import com.EvilNotch.lib.main.Config;
import com.EvilNotch.lib.main.MainJava;
import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityContainer;
import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityReg;
import com.EvilNotch.lib.minecraft.registry.GeneralRegistry;
import com.EvilNotch.lib.util.JavaUtil;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;

import joptsimple.internal.Strings;
import net.minecraft.command.CommandDebug;
import net.minecraft.command.server.CommandBroadcast;
import net.minecraft.command.server.CommandListBans;
import net.minecraft.command.server.CommandListPlayers;
import net.minecraft.command.server.CommandSaveAll;
import net.minecraft.command.server.CommandSaveOff;
import net.minecraft.command.server.CommandSaveOn;
import net.minecraft.command.server.CommandStop;
import net.minecraft.command.server.CommandWhitelist;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.network.play.server.SPacketPlayerListItem.AddPlayerData;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, dependencies = "required-after:evilnotchlib")
public class MainMod
{
	
	@SidedProxy(clientSide = "com.EvilNotch.lanessentials.proxy.ClientProxy", serverSide = "com.EvilNotch.lanessentials.proxy.ServerProxy")
	public static ServerProxy proxy;
	public static File skinCache = null;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	System.out.print("[Lan Essentials] Loading and Registering Commands session:");
		File dir = event.getModConfigurationDirectory().getParentFile();
		skinCache = new File(dir,"skinCache.json");
		SkinUpdater.parseSkinCache();
    	CfgLanEssentials.loadConfig(event.getModConfigurationDirectory() );
    	MinecraftForge.EVENT_BUS.register(this);
    	proxy.preinit();
    	LanFeilds.cacheMCP();
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
    	GeneralRegistry.registerCommand(new CommandNick());
    	GeneralRegistry.registerCommand(new CommandCape());
    	GeneralRegistry.registerCommand(new CommandHat());
    	GeneralRegistry.registerCommand(new CommandWorkBench());
    	GeneralRegistry.registerCommand(new CommandEnderChest());
    	GeneralRegistry.registerCommand(new CommandSmite());
    	GeneralRegistry.registerCommand(new CommandNuke());
    	GeneralRegistry.registerCommand(new CommandWalkSpeed());
        GeneralRegistry.registerCommand(new CommandFlySpeed());
    	
    	//server commands redone for client
    	if(MainJava.isClient)
    	{
    		GeneralRegistry.registerCommand(new CMDBanIp());
    		GeneralRegistry.registerCommand(new CMDBanPlayer());
    		GeneralRegistry.registerCommand(new CommandBroadcast());
    		GeneralRegistry.registerCommand(new CMDDeOp());
    		GeneralRegistry.registerCommand(new CommandListBans());
    		GeneralRegistry.registerCommand(new CommandListPlayers());
     		GeneralRegistry.registerCommand(new CMDOp());
    		GeneralRegistry.registerCommand(new CMDPardonPlayer());
    		GeneralRegistry.registerCommand(new CMDPardonIp());
    		GeneralRegistry.registerCommand(new CommandSaveAll());
    		GeneralRegistry.registerCommand(new CommandSaveOff());
    		GeneralRegistry.registerCommand(new CommandSaveOn());
    		GeneralRegistry.registerCommand(new CommandStop());
    		GeneralRegistry.registerCommand(new CommandWhitelist());
    		GeneralRegistry.registerCommand(new CommandServerIP());
    		
    		GeneralRegistry.registerCommand(new CommandKick());
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
    /**
     * port forward on dedicated servers
     */
    @EventHandler
    public void starting(FMLServerStartingEvent event)
    {
    	if(!MainJava.isClient && CfgLanEssentials.portForwardDedicated)
    	{
    		System.out.println("Starting port forwarding dedicated Server!");
    		LanUtil.schedulePortForwarding(event.getServer().getServerPort(),CfgLanEssentials.dedicatedPortProtocal);
    	}
    }
    /**
     * Close ports on shutdown!
     */
    @EventHandler
    public void stopping(FMLServerStoppedEvent event)
    {
    	if(CfgLanEssentials.closePort)
    	{
    		long time = System.currentTimeMillis();
        	System.out.println("Stopping All MC Port Entrys from the router!");
    		LanUtil.stopPorts();
    		JavaUtil.printTime(time, "Done Closing Ports:");
    	}
		//player premium uuid cache
		SkinUpdater.saveSkinCache();
		//lan-essentials code that needs to be run elsewhere
		com.EvilNotch.lanessentials.events.EventHandler.noSkins.clear();
    }

}