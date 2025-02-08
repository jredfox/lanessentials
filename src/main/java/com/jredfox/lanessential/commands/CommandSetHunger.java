package com.jredfox.lanessential.commands;

import com.evilnotch.lib.util.JavaUtil;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.FoodStats;
import net.minecraft.util.text.TextComponentString;

public class CommandSetHunger extends CommandBase
{

	@Override
	public String getName() {
		return "sethunger";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/sethunger [int-hunger,int-saturation]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if(!(sender instanceof EntityPlayerMP)|| args.length != 2 || !JavaUtil.isStringNum(args[0]) || !JavaUtil.isStringNum(args[1]))
			throw new WrongUsageException(getUsage(sender),new Object[0]);
		
		EntityPlayerMP epmp = (EntityPlayerMP)sender;
		FoodStats fs = epmp.getFoodStats();
		int hunger = Integer.parseInt(args[0]);
		float sat = Float.parseFloat(args[1]);
		fs.setFoodLevel(hunger);
		fs.setFoodSaturationLevel(sat);
		sender.sendMessage(new TextComponentString("Set You To " + fs.getFoodLevel() + " Food and " + fs.getSaturationLevel() + " Saturation"));
	}

}
