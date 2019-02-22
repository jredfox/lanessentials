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

public class CommandGod extends CommandBase
{

	@Override
	public String getName() {
		return "god";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/god";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if(!(sender instanceof EntityPlayerMP))
			return;
		EntityPlayerMP epmp = (EntityPlayerMP)sender;
		if(epmp.isCreative() || epmp.isSpectator())
			return;
		CapAbility cap = (CapAbility) CapabilityRegistry.getCapContainer(epmp).getCapability(new ResourceLocation(Reference.MODID + ":" + "ability"));
		cap.godEnabled = !cap.godEnabled;
		epmp.capabilities.disableDamage = cap.godEnabled;
		epmp.sendPlayerAbilities();
	}

}
