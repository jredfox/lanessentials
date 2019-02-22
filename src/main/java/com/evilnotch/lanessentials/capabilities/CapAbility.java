package com.evilnotch.lanessentials.capabilities;

import com.evilnotch.lib.minecraft.capability.CapContainer;
import com.evilnotch.lib.minecraft.capability.ICapabilityTick;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class CapAbility implements ICapabilityTick<EntityPlayerMP>{
	/**
	 * tells if the player can fly/take damage on tick
	 */
	public boolean flyEnabled = false;
	public boolean godEnabled = false;
	
	/**
	 * almost never updated till either writing to disk or reading from nbt(player login)
	 */
	public boolean isFlying = false;
	
	public CapAbility(){
		
	}

	public CapAbility(boolean fly,boolean god) 
	{
		this.flyEnabled = fly;
		this.godEnabled = god;
	}

	@Override
	public void readFromNBT(EntityPlayerMP p,NBTTagCompound nbt,CapContainer c) {
		this.flyEnabled = nbt.getBoolean("flyEnabled");
		this.godEnabled = nbt.getBoolean("godEnabled");
		this.isFlying = nbt.getBoolean("isFlying");
	}

	@Override
	public void writeToNBT(EntityPlayerMP p,NBTTagCompound nbt,CapContainer c) {
		nbt.setBoolean("flyEnabled", this.flyEnabled);
		nbt.setBoolean("godEnabled", this.godEnabled);
		
		nbt.setBoolean("isFlying", p.capabilities.isFlying);
	}

	/**
	 * fixes mods and issues with /gamemode
	 */
	@Override
	public void tick(EntityPlayerMP p, CapContainer c) 
	{
		boolean used = false;
		if(this.flyEnabled)
		{
			if(!p.capabilities.allowFlying)
			{
				p.capabilities.allowFlying = true;
				used = true;
			}
		}
		if(this.godEnabled)
		{
			if(!p.capabilities.disableDamage)
			{
				p.capabilities.disableDamage = true;
				used = true;
			}
		}
		if(used)
			p.sendPlayerAbilities();
	}

}
