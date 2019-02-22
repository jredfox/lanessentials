package com.evilnotch.lanessentials.commands;

import com.evilnotch.lanessentials.Reference;
import com.evilnotch.lanessentials.capabilities.CapAbility;
import com.evilnotch.lib.minecraft.capability.registry.CapabilityRegistry;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

public class CommandFly extends CommandBase
{

	@Override
	public String getName() 
	{
		return "fly";
	}

	@Override
	public String getUsage(ICommandSender sender) 
	{
		return "/fly";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if(!(sender instanceof EntityPlayerMP))
			return;
		EntityPlayerMP p = (EntityPlayerMP)sender;
		CapAbility cap = (CapAbility) CapabilityRegistry.getCapContainer(p).getCapability(new ResourceLocation(Reference.MODID + ":" + "ability"));
		cap.flyEnabled = !cap.flyEnabled;
		p.capabilities.allowFlying =  cap.flyEnabled;
		
		if(!p.capabilities.allowFlying)
			p.capabilities.isFlying = false;
		p.sendPlayerAbilities();
	}

}
