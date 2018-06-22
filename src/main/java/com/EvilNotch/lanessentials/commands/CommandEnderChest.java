package com.EvilNotch.lanessentials.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatList;

public class CommandEnderChest extends CommandBase{

	@Override
	public String getName() {
		return "echest";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/echest";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if(!(sender instanceof EntityPlayerMP))
			return;
		EntityPlayerMP player = (EntityPlayerMP)sender;
        InventoryEnderChest inventoryenderchest = player.getInventoryEnderChest();
        player.displayGUIChest(inventoryenderchest);
        player.addStat(StatList.ENDERCHEST_OPENED);
	}

}
