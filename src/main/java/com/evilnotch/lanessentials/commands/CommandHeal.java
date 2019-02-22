package com.evilnotch.lanessentials.commands;

import java.util.ArrayList;
import java.util.Iterator;

import com.evilnotch.lib.minecraft.util.EntityUtil;
import com.evilnotch.lib.util.JavaUtil;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.FoodStats;
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
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
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
		EntityUtil.disableFire(p);
		ArrayList<PotionEffect> pots = JavaUtil.toArray(p.getActivePotionEffects());
		Iterator<PotionEffect> it = pots.iterator();
		while(it.hasNext())
		{
			PotionEffect pot = it.next();
			if(pot.getPotion().isBadEffect())
			{
				p.removePotionEffect(pot.getPotion());
			}
		}
		sender.sendMessage(new TextComponentString("Healed: " + p.getName()) );
	}

}
