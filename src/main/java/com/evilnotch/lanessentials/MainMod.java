package com.evilnotch.lanessentials;

import java.io.File;

import com.evilnotch.lanessentials.api.LanFeilds;
import com.evilnotch.lanessentials.api.LanUtil;
import com.evilnotch.lanessentials.api.SkinUpdater;
import com.evilnotch.lanessentials.commands.CommandButcher;
import com.evilnotch.lanessentials.commands.CommandCape;
import com.evilnotch.lanessentials.commands.CommandEnderChest;
import com.evilnotch.lanessentials.commands.CommandFeed;
import com.evilnotch.lanessentials.commands.CommandFly;
import com.evilnotch.lanessentials.commands.CommandFlySpeed;
import com.evilnotch.lanessentials.commands.CommandGod;
import com.evilnotch.lanessentials.commands.CommandHat;
import com.evilnotch.lanessentials.commands.CommandHeal;
import com.evilnotch.lanessentials.commands.CommandHome;
import com.evilnotch.lanessentials.commands.CommandKick;
import com.evilnotch.lanessentials.commands.CommandNick;
import com.evilnotch.lanessentials.commands.CommandNuke;
import com.evilnotch.lanessentials.commands.CommandSetHealth;
import com.evilnotch.lanessentials.commands.CommandSetHome;
import com.evilnotch.lanessentials.commands.CommandSetHunger;
import com.evilnotch.lanessentials.commands.CommandSkin;
import com.evilnotch.lanessentials.commands.CommandSmite;
import com.evilnotch.lanessentials.commands.CommandSpawn;
import com.evilnotch.lanessentials.commands.CommandWalkSpeed;
import com.evilnotch.lanessentials.commands.CommandWorkBench;
import com.evilnotch.lanessentials.commands.network.CommandCloseLan;
import com.evilnotch.lanessentials.commands.network.CommandOpenToLan;
import com.evilnotch.lanessentials.commands.network.CommandOpenToNet;
import com.evilnotch.lanessentials.commands.network.CommandPortForward;
import com.evilnotch.lanessentials.commands.network.CommandServerIP;
import com.evilnotch.lanessentials.commands.vanilla.CMDBanIp;
import com.evilnotch.lanessentials.commands.vanilla.CMDBanPlayer;
import com.evilnotch.lanessentials.commands.vanilla.CMDDeOp;
import com.evilnotch.lanessentials.commands.vanilla.CMDOp;
import com.evilnotch.lanessentials.commands.vanilla.CMDPardonIp;
import com.evilnotch.lanessentials.commands.vanilla.CMDPardonPlayer;
import com.evilnotch.lanessentials.packets.PacketDisplayNameRefresh;
import com.evilnotch.lanessentials.packets.PacketNickHandler;
import com.evilnotch.lanessentials.proxy.ServerOnly;
import com.evilnotch.lanessentials.proxy.ServerProxy;
import com.evilnotch.lib.api.ReflectionUtil;
import com.evilnotch.lib.main.loader.LoaderMain;
import com.evilnotch.lib.minecraft.capability.registry.CapabilityRegistry;
import com.evilnotch.lib.minecraft.network.NetWorkHandler;
import com.evilnotch.lib.minecraft.registry.GeneralRegistry;
import com.evilnotch.lib.util.JavaUtil;
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
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, dependencies = "required-after:evilnotchlib")
public class MainMod
{
	
	@SidedProxy(clientSide = "com.evilnotch.lanessentials.proxy.ClientProxy", serverSide = "com.evilnotch.lanessentials.proxy.ServerProxy")
	public static ServerProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	System.out.print("[Lan Essentials] Loading and Registering Commands session:");
		File dir = event.getModConfigurationDirectory().getParentFile();
		SkinUpdater.skinCache = new File(dir,"skinCache.json");
		SkinUpdater.parseSkinCache();
    	CfgLanEssentials.loadConfig(event.getModConfigurationDirectory() );
    	MinecraftForge.EVENT_BUS.register(new com.evilnotch.lanessentials.events.EventHandler());
    	proxy.preinit();
    	LanFeilds.cacheMCP();
    	proxy.dedicatedPreinit();
    	CapabilityRegistry.registerRegistry(new CapabilityProvider());
    	
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
    	if(LoaderMain.isClient)
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
    	NetWorkHandler.registerMessage(PacketNickHandler.class, PacketDisplayNameRefresh.class, Side.CLIENT);
    }
    
    /**
     * port forward on dedicated servers
     */
    @EventHandler
    public void starting(FMLServerStartingEvent event)
    {			
    	if(!LoaderMain.isClient && CfgLanEssentials.portForwardDedicated)
    	{
    		System.out.println("Starting port forwarding dedicated Server!");
    		LanUtil.schedulePortForwarding(ServerOnly.getServerPort(event.getServer()), CfgLanEssentials.dedicatedPortProtocal);
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
		com.evilnotch.lanessentials.events.EventHandler.noSkins.clear();
    }

}