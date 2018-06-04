package com.EvilNotch.lanessentials;

import com.EvilNotch.lanessentials.commands.CommandFeed;
import com.EvilNotch.lanessentials.commands.CommandFly;
import com.EvilNotch.lanessentials.commands.CommandGod;
import com.EvilNotch.lanessentials.commands.CommandHeal;
import com.EvilNotch.lanessentials.commands.CommandHome;
import com.EvilNotch.lanessentials.commands.CommandKick;
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
import com.EvilNotch.lib.main.MainJava;
import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityReg;
import com.EvilNotch.lib.minecraft.registry.GeneralRegistry;

import net.minecraft.command.CommandDebug;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, dependencies = "required-after:evilnotchlib",acceptableRemoteVersions = "*")
public class MainMod
{

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {	
    	System.out.print("[Lan Essentials] Loading and Registering Commands");
    	CfgLanEssentials.loadConfig(event.getModConfigurationDirectory() );
    	CapabilityReg.registerCapProvider(new CapabilityProvider());
    	
    	GeneralRegistry.registerCommand(new CommandSetHome());
    	GeneralRegistry.registerCommand(new CommandHome());
    	GeneralRegistry.registerCommand(new CommandHeal());
    	GeneralRegistry.registerCommand(new CommandSetHealth() );
    	GeneralRegistry.registerCommand(new CommandFeed());
    	GeneralRegistry.registerCommand(new CommandFly());
    	GeneralRegistry.registerCommand(new CommandGod());
    	GeneralRegistry.registerCommand(new CommandSetHunger());
    	GeneralRegistry.registerCommand(new CommandSkin());
    	GeneralRegistry.registerCommand(new CommandSpeed());
    	
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
    	}
    	GeneralRegistry.registerCommand(new CommandDebug());
    }
}