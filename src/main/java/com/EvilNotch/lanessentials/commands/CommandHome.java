package com.EvilNotch.lanessentials.commands;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.EvilNotch.lanessentials.Reference;
import com.EvilNotch.lib.minecraft.EntityUtil;
import com.EvilNotch.lib.minecraft.EnumChatFormatting;
import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityReg;
import com.EvilNotch.lib.util.JavaUtil;
import com.EvilNotch.lib.util.Line.LineBase;
import com.EvilNotch.lanessentials.capabilities.CapHome;
import com.EvilNotch.lanessentials.capabilities.CapHomePoint;

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
		CapHome ch = (CapHome) CapabilityReg.getCapabilityConatainer(epmp).getCapability(new ResourceLocation(Reference.MODID + ":" + "home"));
		CapHomePoint chp = ch.getCapPoint(point);
		if(chp == null)
		{
			String s = point.equals(CommandSetHome.reservedHome) ? "unable to find default home" : "cannot find home for \"" + point + "\"";
			throw new WrongUsageException(s,new Object[0]);
		}
		EntityUtil.telePortEntity(epmp, server, chp.pos.getX() + 0.5, (double)chp.pos.getActualY(), chp.pos.getZ() + 0.5, chp.yaw, chp.pitch, chp.dimId);
	}
	/**
	 * list of homes per player doesn't show the default only the strings
	 */
	@Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
    	if(!(sender instanceof EntityPlayerMP))
    			return super.getTabCompletions(server, sender, args, targetPos);
    	
    	CapHome cap = (CapHome) CapabilityReg.getCapabilityConatainer((EntityPlayer)sender).getCapability(new ResourceLocation(Reference.MODID + ":" + "home"));
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
