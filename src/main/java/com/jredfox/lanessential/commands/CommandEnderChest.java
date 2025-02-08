package com.jredfox.lanessential.commands;

import java.util.Arrays;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatList;

public class CommandEnderChest extends CommandBase{
	
    /**
     * Get a list of aliases for this command. <b>Never return null!</b>
     */
    public List<String> getAliases()
    {
        return Arrays.<String>asList("enderchest");
    }

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
