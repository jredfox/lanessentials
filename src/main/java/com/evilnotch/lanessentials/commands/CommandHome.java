package com.evilnotch.lanessentials.commands;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.evilnotch.lanessentials.Reference;
import com.evilnotch.lanessentials.capabilities.CapHome;
import com.evilnotch.lanessentials.capabilities.CapHomePoint;
import com.evilnotch.lib.minecraft.capability.registry.CapabilityRegistry;
import com.evilnotch.lib.minecraft.util.TeleportUtil;
import com.evilnotch.lib.util.JavaUtil;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class CommandHome  extends CommandBase
{

	@Override
	public String getName() {
		return "home";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/home [homeName]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if(!(sender instanceof EntityPlayerMP))
			return;
		EntityPlayerMP epmp = (EntityPlayerMP) sender;

		String point = args.length >= 1 ? args[0].trim() : CommandSetHome.reservedHome;
		CapHome ch = (CapHome) CapabilityRegistry.getCapContainer(epmp).getCapability(new ResourceLocation(Reference.MODID + ":" + "home"));
		CapHomePoint chp = ch.getCapPoint(point);
		if(chp == null)
		{
			String s = point.equals(CommandSetHome.reservedHome) ? "unable to find default home" : "cannot find home for \"" + point + "\"";
			throw new WrongUsageException(s,new Object[0]);
		}
		TeleportUtil.telePortEntitySync(epmp, server, chp.pos.getX() + 0.5, (double)chp.pos.getActualY(), chp.pos.getZ() + 0.5, chp.yaw, chp.pitch, chp.dimId);
	}
	/**
	 * list of homes per player doesn't show the default only the strings
	 */
	@Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
    	if(!(sender instanceof EntityPlayerMP))
    			return super.getTabCompletions(server, sender, args, targetPos);
    	
    	CapHome cap = (CapHome) CapabilityRegistry.getCapContainer((EntityPlayer)sender).getCapability(new ResourceLocation(Reference.MODID + ":" + "home"));
    	List<String> list = new ArrayList();
    	for(CapHomePoint p : cap.capPoints)
    	{
    		String point = p.toString();
    		if(!point.equals(CommandSetHome.reservedHome))
    			list.add(p.toString());
    	}
    	return orderList(list,args);
    }

	public static List<String> orderList(List<String> list, String[] args) {
		return getListOfStringsMatchingLastWord(args,JavaUtil.toStaticStringArray(list));
	}

}
