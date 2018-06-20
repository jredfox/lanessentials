package com.EvilNotch.lanessentials.commands;

import com.EvilNotch.lanessentials.MainMod;
import com.EvilNotch.lanessentials.Reference;
import com.EvilNotch.lanessentials.capabilities.CapNick;
import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityReg;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

public class CommandNick extends CommandBase{

	@Override
	public String getName() {
		return "nick";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "nick [displayname]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		EntityPlayer player = (EntityPlayer) sender;
		CapNick name = (CapNick) CapabilityReg.getCapabilityConatainer(player).getCapability(new ResourceLocation(Reference.MODID + ":" + "nick"));
		name.nick = args.length == 1 ? args[0] : player.getName();
		MainMod.updateNickName((EntityPlayerMP) player);
	}
}
