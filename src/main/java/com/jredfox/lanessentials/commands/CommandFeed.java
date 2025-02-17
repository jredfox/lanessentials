package com.jredfox.lanessentials.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.FoodStats;
import net.minecraft.util.text.TextComponentString;

public class CommandFeed extends CommandBase
{

	@Override
	public String getName() 
	{
		return "feed";
	}

	@Override
	public String getUsage(ICommandSender sender) 
	{
		return "/feed";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if(!(sender instanceof EntityPlayerMP))
			return;
		EntityPlayerMP epmp = (EntityPlayerMP)sender;
		FoodStats fs = epmp.getFoodStats();
		
		fs.setFoodLevel(20);
		if(fs.getSaturationLevel() < 5.0F)
			fs.setFoodSaturationLevel(5.0F);
		
		sender.sendMessage(new TextComponentString(epmp.getName() + " is full"));
	}
    /**
     * get selectors working
     */
    public boolean isUsernameIndex(String[] args, int index)
    {
        return index == 0;
    }

}
