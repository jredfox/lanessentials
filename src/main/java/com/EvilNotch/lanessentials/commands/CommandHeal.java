package com.EvilNotch.lanessentials.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.EvilNotch.lib.minecraft.EntityUtil;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.FoodStats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class CommandHeal extends CommandBase
{

	@Override
	public String getName() {
		return "heal";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/heal";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(!(sender instanceof EntityPlayerMP))
			return;
		EntityPlayerMP p = (EntityPlayerMP)sender;
		
		//health reset
		p.setHealth(p.getMaxHealth());
		
		//food
		FoodStats fs = p.getFoodStats();
		fs.setFoodLevel(20);
		if(fs.getSaturationLevel() < 5.0F)
			fs.setFoodSaturationLevel(5.0F);
		
		//fire && potions
		p.setFire(0);
		Iterator<PotionEffect> it = p.getActivePotionEffects().iterator();
		while(it.hasNext())
		{
			PotionEffect pot = it.next();
			if(pot.getPotion().isBadEffect())
			{
				it.remove();
			}
		}
		sender.sendMessage(new TextComponentString("Healed: " + p.getName()) );
	}

}
