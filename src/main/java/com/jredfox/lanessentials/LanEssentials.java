package com.jredfox.lanessentials;

import com.evilnotch.lib.main.loader.LoaderMain;
import com.evilnotch.lib.minecraft.capability.registry.CapabilityRegistry;
import com.evilnotch.lib.minecraft.network.NetWorkHandler;
import com.evilnotch.lib.minecraft.registry.GeneralRegistry;
import com.jredfox.lanessentials.cap.CapabilityProvider;
import com.jredfox.lanessentials.commands.CommandButcher;
import com.jredfox.lanessentials.commands.CommandEnderChest;
import com.jredfox.lanessentials.commands.CommandFeed;
import com.jredfox.lanessentials.commands.CommandHat;
import com.jredfox.lanessentials.commands.CommandHeal;
import com.jredfox.lanessentials.commands.CommandKick;
import com.jredfox.lanessentials.commands.CommandNuke;
import com.jredfox.lanessentials.commands.CommandSetHealth;
import com.jredfox.lanessentials.commands.CommandSetHunger;
import com.jredfox.lanessentials.commands.CommandSmite;
import com.jredfox.lanessentials.commands.CommandSpawn;
import com.jredfox.lanessentials.commands.CommandWorkBench;
import com.jredfox.lanessentials.commands.cap.CommandFly;
import com.jredfox.lanessentials.commands.cap.CommandGod;
import com.jredfox.lanessentials.commands.cap.CommandHome;
import com.jredfox.lanessentials.commands.cap.CommandNick;
import com.jredfox.lanessentials.commands.cap.CommandSetHome;
import com.jredfox.lanessentials.commands.cap.CommandSpeed;
import com.jredfox.lanessentials.commands.cap.CommandSpeedFly;
import com.jredfox.lanessentials.commands.network.CommandServerIP;
import com.jredfox.lanessentials.commands.vanilla.CMDBanIp;
import com.jredfox.lanessentials.commands.vanilla.CMDBanPlayer;
import com.jredfox.lanessentials.commands.vanilla.CMDDeOp;
import com.jredfox.lanessentials.commands.vanilla.CMDOp;
import com.jredfox.lanessentials.commands.vanilla.CMDPardonIp;
import com.jredfox.lanessentials.commands.vanilla.CMDPardonPlayer;
import com.jredfox.lanessentials.events.LEEventHandler;
import com.jredfox.lanessentials.packets.PacketNick;
import com.jredfox.lanessentials.packets.PacketNickHandler;
import com.jredfox.lanessentials.proxy.CommonProxy;

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
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = LanEssentials.MODID, name = LanEssentials.NAME, version = LanEssentials.VERSION, dependencies = "required-after:evilnotchlib@[1.2.3.11,]")
public class LanEssentials
{
    public static final String MODID = "lanessentials";
    public static final String NAME = "Lan Essentials";
    public static final String VERSION = "0.4.1";
	
	@SidedProxy(clientSide = "com.jredfox.lanessentials.proxy.ClientProxy", serverSide = "com.jredfox.lanessentials.proxy.ServerProxy")
	public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	LanEssentialsConfig.loadConfig(event.getModConfigurationDirectory() );
    	MinecraftForge.EVENT_BUS.register(new LEEventHandler());
    	proxy.preinit();
    	CapabilityRegistry.registerRegistry(new CapabilityProvider());
    	
    	//Register Capability Commands
    	GeneralRegistry.registerCommand(new CommandGod());
    	GeneralRegistry.registerCommand(new CommandFly());
    	GeneralRegistry.registerCommand(new CommandNick());
    	GeneralRegistry.registerCommand(new CommandSpeed());
    	GeneralRegistry.registerCommand(new CommandSpeedFly());
    	GeneralRegistry.registerCommand(new CommandSetHome());
    	GeneralRegistry.registerCommand(new CommandHome());
    	
    	//Register Mod Commands
    	GeneralRegistry.registerCommand(new CommandHeal());
    	GeneralRegistry.registerCommand(new CommandSetHealth() );
    	GeneralRegistry.registerCommand(new CommandFeed());
    	GeneralRegistry.registerCommand(new CommandSetHunger());
    	GeneralRegistry.registerCommand(new CommandHat());
    	GeneralRegistry.registerCommand(new CommandWorkBench());
    	GeneralRegistry.registerCommand(new CommandEnderChest());
    	GeneralRegistry.registerCommand(new CommandSmite());
    	GeneralRegistry.registerCommand(new CommandNuke());
        GeneralRegistry.registerCommand(new CommandButcher());
        GeneralRegistry.registerCommand(new CommandSpawn());
        
        //TODO: Fix Mod Commands:
        /* Permission Levels
         * Tabbing
         * Doesn't support other players / ents
        */

        //TODO: tpaaccept tpadeny tpa
        //TODO: set max player count
        //TODO: get playerlist working with new commands
    	
    	//server commands redone for client
    	if(LoaderMain.isClient)
    	{
    		GeneralRegistry.registerCommand(new CMDBanIp());//TODO:
    		GeneralRegistry.registerCommand(new CMDBanPlayer());//TODO:
    		GeneralRegistry.registerCommand(new CommandBroadcast());//TODO:
    		GeneralRegistry.registerCommand(new CMDDeOp());//TODO:
    		GeneralRegistry.registerCommand(new CommandListBans());//TODO:
    		GeneralRegistry.registerCommand(new CommandListPlayers());//TODO:
     		GeneralRegistry.registerCommand(new CMDOp());//TODO:
    		GeneralRegistry.registerCommand(new CMDPardonPlayer());//TODO:
    		GeneralRegistry.registerCommand(new CMDPardonIp());//TODO:
    		GeneralRegistry.registerCommand(new CommandSaveAll());//WORKING
    		GeneralRegistry.registerCommand(new CommandSaveOff());//WORKING
    		GeneralRegistry.registerCommand(new CommandSaveOn());//WORKING
    		GeneralRegistry.registerCommand(new CommandStop());//WORKING
    		GeneralRegistry.registerCommand(new CommandWhitelist());//TODO:
    		
    		GeneralRegistry.registerCommand(new CommandKick());//WORKING
    		GeneralRegistry.registerCommand(new CommandDebug());//WORKING
    	}
    	
    	//remove vanilla broken command publish
    	GeneralRegistry.removeVanillaCommand("publish");
    	
    	//network server commands
    	if(LanEssentialsConfig.ipCommands)
    		GeneralRegistry.registerCommand(new CommandServerIP());
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	NetWorkHandler.registerMessage(PacketNickHandler.class, PacketNick.class, Side.CLIENT);
    }

}