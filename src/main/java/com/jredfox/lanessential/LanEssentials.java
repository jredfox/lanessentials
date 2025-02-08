package com.jredfox.lanessential;

import com.evilnotch.lib.main.loader.LoaderMain;
import com.evilnotch.lib.minecraft.capability.registry.CapabilityRegistry;
import com.evilnotch.lib.minecraft.network.NetWorkHandler;
import com.evilnotch.lib.minecraft.registry.GeneralRegistry;
import com.jredfox.lanessential.cap.CapabilityProvider;
import com.jredfox.lanessential.commands.CommandButcher;
import com.jredfox.lanessential.commands.CommandEnderChest;
import com.jredfox.lanessential.commands.CommandFeed;
import com.jredfox.lanessential.commands.CommandHat;
import com.jredfox.lanessential.commands.CommandHeal;
import com.jredfox.lanessential.commands.CommandHome;
import com.jredfox.lanessential.commands.CommandKick;
import com.jredfox.lanessential.commands.CommandNick;
import com.jredfox.lanessential.commands.CommandNuke;
import com.jredfox.lanessential.commands.CommandSetHealth;
import com.jredfox.lanessential.commands.CommandSetHome;
import com.jredfox.lanessential.commands.CommandSetHunger;
import com.jredfox.lanessential.commands.CommandSmite;
import com.jredfox.lanessential.commands.CommandSpawn;
import com.jredfox.lanessential.commands.CommandWorkBench;
import com.jredfox.lanessential.commands.cap.CommandFly;
import com.jredfox.lanessential.commands.cap.CommandGod;
import com.jredfox.lanessential.commands.cap.CommandSpeed;
import com.jredfox.lanessential.commands.network.CommandServerIP;
import com.jredfox.lanessential.commands.vanilla.CMDBanIp;
import com.jredfox.lanessential.commands.vanilla.CMDBanPlayer;
import com.jredfox.lanessential.commands.vanilla.CMDDeOp;
import com.jredfox.lanessential.commands.vanilla.CMDOp;
import com.jredfox.lanessential.commands.vanilla.CMDPardonIp;
import com.jredfox.lanessential.commands.vanilla.CMDPardonPlayer;
import com.jredfox.lanessential.events.LEEventHandler;
import com.jredfox.lanessential.packets.PacketNick;
import com.jredfox.lanessential.packets.PacketNickHandler;
import com.jredfox.lanessential.proxy.CommonProxy;

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

@Mod(modid = LanEssentials.MODID, name = LanEssentials.NAME, version = LanEssentials.VERSION, dependencies = "required-after:evilnotchlib")
public class LanEssentials
{
    public static final String MODID = "lanessentials";
    public static final String NAME = "Lan Essentials";
    public static final String VERSION = "0.4.0";
	
	@SidedProxy(clientSide = "com.evilnotch.lanessentials.proxy.ClientProxy", serverSide = "com.evilnotch.lanessentials.proxy.ServerProxy")
	public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	LanEssentialsConfig.loadConfig(event.getModConfigurationDirectory() );
    	MinecraftForge.EVENT_BUS.register(new LEEventHandler());
    	proxy.preinit();
    	CapabilityRegistry.registerRegistry(new CapabilityProvider());
    	
    	GeneralRegistry.registerCommand(new CommandSetHome());
    	GeneralRegistry.registerCommand(new CommandHome());
    	GeneralRegistry.registerCommand(new CommandHeal());
    	GeneralRegistry.registerCommand(new CommandSetHealth() );
    	GeneralRegistry.registerCommand(new CommandFeed());
    	GeneralRegistry.registerCommand(new CommandSetHunger());
    	GeneralRegistry.registerCommand(new CommandFly());
    	GeneralRegistry.registerCommand(new CommandGod());
    	GeneralRegistry.registerCommand(new CommandNick());
    	GeneralRegistry.registerCommand(new CommandHat());
    	GeneralRegistry.registerCommand(new CommandWorkBench());
    	GeneralRegistry.registerCommand(new CommandEnderChest());
    	GeneralRegistry.registerCommand(new CommandSmite());
    	GeneralRegistry.registerCommand(new CommandNuke());
    	GeneralRegistry.registerCommand(new CommandSpeed());
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
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	NetWorkHandler.registerMessage(PacketNickHandler.class, PacketNick.class, Side.CLIENT);
    }

}