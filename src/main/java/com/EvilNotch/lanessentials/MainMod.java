package com.EvilNotch.lanessentials;

import java.io.File;

import com.EvilNotch.lanessentials.api.LanFeilds;
import com.EvilNotch.lanessentials.api.LanUtil;
import com.EvilNotch.lanessentials.api.SkinUpdater;
import com.EvilNotch.lanessentials.commands.CommandButcher;
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
import com.EvilNotch.lanessentials.commands.CommandSetHealth;
import com.EvilNotch.lanessentials.commands.CommandSetHome;
import com.EvilNotch.lanessentials.commands.CommandSetHunger;
import com.EvilNotch.lanessentials.commands.CommandSkin;
import com.EvilNotch.lanessentials.commands.CommandSmite;
import com.EvilNotch.lanessentials.commands.CommandSpawn;
import com.EvilNotch.lanessentials.commands.CommandWalkSpeed;
import com.EvilNotch.lanessentials.commands.CommandWorkBench;
import com.EvilNotch.lanessentials.commands.network.CommandCloseLan;
import com.EvilNotch.lanessentials.commands.network.CommandOpenToLan;
import com.EvilNotch.lanessentials.commands.network.CommandOpenToNet;
import com.EvilNotch.lanessentials.commands.network.CommandPortForward;
import com.EvilNotch.lanessentials.commands.network.CommandServerIP;
import com.EvilNotch.lanessentials.commands.vanilla.CMDBanIp;
import com.EvilNotch.lanessentials.commands.vanilla.CMDBanPlayer;
import com.EvilNotch.lanessentials.commands.vanilla.CMDDeOp;
import com.EvilNotch.lanessentials.commands.vanilla.CMDOp;
import com.EvilNotch.lanessentials.commands.vanilla.CMDPardonIp;
import com.EvilNotch.lanessentials.commands.vanilla.CMDPardonPlayer;
import com.EvilNotch.lanessentials.packets.NetWorkHandler;
import com.EvilNotch.lanessentials.proxy.ServerOnly;
import com.EvilNotch.lanessentials.proxy.ServerProxy;
import com.EvilNotch.lib.Api.ReflectionUtil;
import com.EvilNotch.lib.main.MainJava;
import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityReg;
import com.EvilNotch.lib.minecraft.registry.GeneralRegistry;
import com.EvilNotch.lib.util.JavaUtil;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;

import net.minecraft.command.CommandDebug;
import net.minecraft.command.server.CommandBroadcast;
import net.minecraft.command.server.CommandListBans;
import net.minecraft.command.server.CommandListPlayers;
import net.minecraft.command.server.CommandSaveAll;
import net.minecraft.command.server.CommandSaveOff;
import net.minecraft.command.server.CommandSaveOn;
import net.minecraft.command.server.CommandStop;
import net.minecraft.command.server.CommandWhitelist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, dependencies = "required-after:evilnotchlib")
public class MainMod
{
	
	@SidedProxy(clientSide = "com.EvilNotch.lanessentials.proxy.ClientProxy", serverSide = "com.EvilNotch.lanessentials.proxy.ServerProxy")
	public static ServerProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	System.out.print("[Lan Essentials] Loading and Registering Commands session:");
		File dir = event.getModConfigurationDirectory().getParentFile();
		SkinUpdater.skinCache = new File(dir,"skinCache.json");
		SkinUpdater.parseSkinCache();
    	CfgLanEssentials.loadConfig(event.getModConfigurationDirectory() );
    	MinecraftForge.EVENT_BUS.register(new com.EvilNotch.lanessentials.events.EventHandler());
    	proxy.preinit();
    	LanFeilds.cacheMCP();
    	proxy.dedicatedPreinit();
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
        GeneralRegistry.registerCommand(new CommandButcher());
        GeneralRegistry.registerCommand(new CommandSpawn());
    	
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
    		
    		GeneralRegistry.registerCommand(new CommandKick());
    		GeneralRegistry.registerCommand(new CommandDebug());
    	}
    	
    	//remove vanilla broken command publish
    	GeneralRegistry.removeVanillaCommand("publish");
    	//network server commands
		GeneralRegistry.registerCommand(new CommandServerIP());
    	GeneralRegistry.registerCommand(new CommandPortForward());
    	GeneralRegistry.registerCommand(new CommandOpenToLan());
    	GeneralRegistry.registerCommand(new CommandOpenToNet());
    	GeneralRegistry.registerCommand(new CommandCloseLan());
    	
    	/*
    	 * adds ability without ASM to have more domain urls for skins/capes
    	 */
        ReflectionUtil.setFinalObject(null, CfgLanEssentials.skinDomains, YggdrasilMinecraftSessionService.class, "WHITELISTED_DOMAINS");
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
    		LanUtil.schedulePortForwarding(ServerOnly.getServerPort(event.getServer()),CfgLanEssentials.dedicatedPortProtocal);
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