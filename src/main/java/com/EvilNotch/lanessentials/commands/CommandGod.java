package com.EvilNotch.lanessentials.commands;

import com.EvilNotch.lanessentials.Reference;
import com.EvilNotch.lanessentials.capabilities.CapAbility;
import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityReg;

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
		epmp.capabilities.disableDamage = !epmp.capabilities.disableDamage;
		epmp.sendPlayerAbilities();
		CapAbility cap = (CapAbility) CapabilityReg.getCapabilityConatainer(epmp).getCapability(new ResourceLocation(Reference.MODID + ":" + "ability"));
		cap.godEnabled = epmp.capabilities.disableDamage;
	}

}
