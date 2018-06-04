package com.EvilNotch.lanessentials.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandSpeed extends CommandBase
{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "speed";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "/speed [double multiplier]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		//TODO make ajustments to prevent things like setting your speed to 0 haveing no way to change out again
		if(!(sender instanceof EntityPlayerMP))
			return;
		double defaultFly = 0.05;
		double defaultWalk = 0.1;
		EntityPlayerMP epmp = (EntityPlayerMP)sender;
		double multiplier = Double.parseDouble(args[0]);
		PlayerCapabilities pc = epmp.capabilities;
		float flySpeed = (float) (pc.getFlySpeed()*multiplier);
		float walkSpeed = (float) (pc.getWalkSpeed()*multiplier);
		pc.setFlySpeed(flySpeed);
		pc.setPlayerWalkSpeed(walkSpeed);
		epmp.sendPlayerAbilities();
	}
}
