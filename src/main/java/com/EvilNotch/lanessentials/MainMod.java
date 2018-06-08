package com.EvilNotch.lanessentials;

import java.util.List;

import com.EvilNotch.lanessentials.capabilities.CapAbility;
import com.EvilNotch.lanessentials.capabilities.CapHome;
import com.EvilNotch.lanessentials.commands.CommandFeed;
import com.EvilNotch.lanessentials.commands.CommandFly;
import com.EvilNotch.lanessentials.commands.CommandGod;
import com.EvilNotch.lanessentials.commands.CommandHeal;
import com.EvilNotch.lanessentials.commands.CommandHome;
import com.EvilNotch.lanessentials.commands.CommandHomeClear;
import com.EvilNotch.lanessentials.commands.CommandHomeRemove;
import com.EvilNotch.lanessentials.commands.CommandKick;
import com.EvilNotch.lanessentials.commands.CommandSetHealth;
import com.EvilNotch.lanessentials.commands.CommandSetHome;
import com.EvilNotch.lanessentials.commands.CommandSetHomeCount;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
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
    	GeneralRegistry.registerCommand(new CommandSetHomeCount());
    	GeneralRegistry.registerCommand(new CommandHomeClear());
    	GeneralRegistry.registerCommand(new CommandHomeRemove());
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
    		GeneralRegistry.registerCommand(new CommandDebug());
    	}
    }
    
	@SubscribeEvent(priority = EventPriority.LOW)
    public void login(PlayerLoggedInEvent e)
    {
	   	if(!(e.player instanceof EntityPlayerMP))
    		return;
	   	EntityPlayerMP player = (EntityPlayerMP) e.player;
    	PlayerCapabilities caps = player.capabilities;
    	CapAbility cap = (CapAbility) CapabilityReg.getCapabilityConatainer(player).getCapability(new ResourceLocation(Reference.MODID + ":" + "ability"));
    	if(cap.flyEnabled)
    	{
    		caps.allowFlying = cap.flyEnabled;
    		caps.isFlying = cap.isFlying;
    	}
    	if(cap.godEnabled)
    	{
    		caps.disableDamage = cap.godEnabled;
    	}
    	player.sendPlayerAbilities();
    }

	@SubscribeEvent(priority = EventPriority.LOW)
    public void respawn(PlayerRespawnEvent e)
    {
	   	if(!(e.player instanceof EntityPlayerMP))
    		return;
	   	EntityPlayerMP player = (EntityPlayerMP) e.player;
    	PlayerCapabilities caps = player.capabilities;
    	CapAbility cap = (CapAbility) CapabilityReg.getCapabilityConatainer(player).getCapability(new ResourceLocation(Reference.MODID + ":" + "ability"));
    	if(cap.flyEnabled)
    	{
    		caps.allowFlying = cap.flyEnabled;
    	}
    	if(cap.godEnabled)
    	{
    		caps.disableDamage = cap.godEnabled;
    	}
    	player.sendPlayerAbilities();
    }
}