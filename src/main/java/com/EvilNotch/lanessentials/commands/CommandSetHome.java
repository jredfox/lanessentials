package com.evilnotch.lanessentials.commands;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.evilnotch.lanessentials.Reference;
import com.evilnotch.lanessentials.capabilities.CapHome;
import com.evilnotch.lanessentials.capabilities.CapHomePoint;
import com.evilnotch.lanessentials.capabilities.Pos;
import com.evilnotch.lib.minecraft.capability.registry.CapRegHandler;
import com.evilnotch.lib.minecraft.util.EnumChatFormatting;
import com.evilnotch.lib.minecraft.util.PlayerUtil;
import com.evilnotch.lib.util.JavaUtil;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class CommandSetHome  extends CommandBase
{	
	public static final String reservedHome = "default";

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
			if(args[0].equals(reservedHome) || args[0].equals("remove") || args[0].equals("count"))
				throw new WrongUsageException("home cannot be the reserved default for name \"" + args[0] + "\"",new Object[0]);
		}
		String point = args.length >= 1 ? args[0].trim() : reservedHome;
		EntityPlayerMP epmp = (EntityPlayerMP) sender;
		CapHome ch = (CapHome) CapRegHandler.getCapContainer(epmp).getCapability(new ResourceLocation(Reference.MODID + ":" + "home"));
		
		if(point.equals("clear"))
		{
			ch.capPoints.clear();
			PlayerUtil.printChat(epmp, EnumChatFormatting.RED, EnumChatFormatting.RED, "cleared homes");
			return;
		}
		else if(point.equals("remove"))
		{
			if(args.length != 2 || JavaUtil.toWhiteSpaced(args[1]).equals(""))
				throw new WrongUsageException("must have home to remove",new Object[0]);
			String toRemove = args[1].trim();
			
			boolean removed = ch.removePoint(toRemove);
			if(!removed)
				throw new WrongUsageException("unable to remove point: \"" + toRemove + "\"",new Object[0]);
			PlayerUtil.printChat(epmp, EnumChatFormatting.YELLOW, EnumChatFormatting.YELLOW, "removed home:" + toRemove);
			return;
		}
		else if(point.equals("count"))
		{
			if(args.length != 2 || !JavaUtil.isStringNum(args[1]))
				throw new WrongUsageException(getUsage(sender),new Object[0]);
			
			int size = Integer.parseInt(args[1]);
			ch.setMaxHomeCount(size);
			return;
		}
		
		EnumFacing facing = epmp.getAdjustedHorizontalFacing();
		float yaw = 0.0F;
		float pitch = 0.0F;
		if(facing == EnumFacing.NORTH)
		{
			yaw = 180.0F;
			System.out.println("North:" + epmp.rotationYaw);
		}
		else if(facing == EnumFacing.SOUTH)
		{
			yaw = 0.0F;
			System.out.println("SOUTH:" + epmp.rotationYaw);
		}
		else if(facing == EnumFacing.EAST)
		{
			yaw = 270.0F;
			System.out.println("EAST:" + epmp.rotationYaw);
		}
		else if(facing == EnumFacing.WEST)
		{
			yaw = 90.0F;
			System.out.println("WEST:" + epmp.rotationYaw);
		}
		
		CapHomePoint chp = new CapHomePoint(new Pos(sender.getPosition(),epmp.posY), epmp.dimension, point,yaw,pitch);
		
		//set home point
		if(ch.capPoints.size() >= ch.maxCount && !ch.capPoints.contains(chp))
			throw new WrongUsageException(EnumChatFormatting.YELLOW + "player house maxed out at " + ch.maxCount,new Object[0]);
		
		ch.sethome(chp);
	}
	
	/**
	 * list of homes per player doesn't show the default only the strings
	 */
	@Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
    	if(!(sender instanceof EntityPlayerMP))
    			return super.getTabCompletions(server, sender, args, targetPos);
    	
    	CapHome cap = (CapHome) CapRegHandler.getCapContainer((EntityPlayer)sender).getCapability(new ResourceLocation(Reference.MODID + ":" + "home"));
    	List<String> list = new ArrayList();
    	if(args.length <= 1)
    	{
    		list.add("count");
    		list.add("clear");
    		list.add("remove");
    	}
    	for(CapHomePoint p : cap.capPoints)
    	{
    		String point = p.toString();
    		if(!point.equals(reservedHome))
    			list.add(p.toString());
    		else if(args.length == 2)
    		{
    			if(args[0].equals("remove"))
    				list.add(reservedHome);
    		}
    	}
    	return CommandHome.orderList(list,args);
    }
	
}
