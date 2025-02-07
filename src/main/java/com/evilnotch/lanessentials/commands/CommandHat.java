package com.evilnotch.lanessentials.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

public class CommandHat extends CommandBase
{

	@Override
	public String getName() {
		return "hat";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/hat";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		EntityPlayerMP epmp = args.length == 0 ? (EntityPlayerMP)sender : getPlayer(server, sender, args[0]);
		InventoryPlayer inv = epmp.inventory;
		ItemStack hat = inv.armorInventory.get(EntityEquipmentSlot.HEAD.getIndex());
		ItemStack is  = inv.getCurrentItem();
		inv.mainInventory.set(epmp.inventory.currentItem, hat);
		inv.armorInventory.set(EntityEquipmentSlot.HEAD.getIndex(), is);
	}

}
