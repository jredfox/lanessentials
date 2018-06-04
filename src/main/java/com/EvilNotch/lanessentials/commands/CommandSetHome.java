package com.EvilNotch.lanessentials.commands;

import com.EvilNotch.lanessentials.Reference;
import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityReg;
import com.EvilNotch.lanessentials.capabilities.CapHome;
import com.EvilNotch.lanessentials.capabilities.CapHomePoint;
import com.EvilNotch.lanessentials.capabilities.Pos;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

public class CommandSetHome  extends CommandBase
{	
	public static final String reservedHome = "default_home0";

	@Override
	public String getName() 
	{
		return "sethome";
	}

	@Override
	public String getUsage(ICommandSender sender) 
	{
		return "/sethome [homeName]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if(!(sender instanceof EntityPlayerMP))
			return;
		if(args.length == 1)
		{
			if(args[0].equals(reservedHome))
				throw new WrongUsageException("home cannot be the reserved default for name \"" + reservedHome + "\"",new Object[0]);
		}
		String point = args.length == 1 ? args[0].trim() : reservedHome;
		EntityPlayerMP epmp = (EntityPlayerMP) sender;
		CapHome ch = (CapHome) CapabilityReg.getCapabilityConatainer(epmp).getCapability(new ResourceLocation(Reference.MODID + ":" + "home"));
		CapHomePoint chp = new CapHomePoint(new Pos(sender.getPosition(),epmp.posY), epmp.dimension, point);
		
		//set home point
		ch.sethome(chp);
	}
	
}
