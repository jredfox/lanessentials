package com.EvilNotch.lanessentials.capabilities;

import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityContainer;
import com.EvilNotch.lib.minecraft.content.pcapabilites.ICapability;
import com.EvilNotch.lib.minecraft.content.pcapabilites.ITick;
import com.EvilNotch.lib.util.ICopy;
import com.EvilNotch.lib.util.JavaUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class CapAbility implements ICapability,ITick{
	/**
	 * tells if it can mess with player capabilities on login also used for defaulting when respawning
	 */
	public boolean flyEnabled = false;
	public boolean godEnabled = false;
	
	/**
	 * values are unsynced until it's time to write to the disk
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
	public ICopy copy() {
		return new CapAbility(this.flyEnabled,this.godEnabled);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt,EntityPlayer p,CapabilityContainer c) {
		this.flyEnabled = nbt.getBoolean("flyEnabled");
		this.godEnabled = nbt.getBoolean("godEnabled");
		this.isFlying = nbt.getBoolean("isFlying");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt,EntityPlayer p,CapabilityContainer c) {
		nbt.setBoolean("flyEnabled", this.flyEnabled);
		nbt.setBoolean("godEnabled", this.godEnabled);
		
		nbt.setBoolean("isFlying", p.capabilities.isFlying);
	}

	/**
	 * fixes mods and issues with /gamemode
	 */
	@Override
	public void tick(EntityPlayer p, CapabilityContainer c) 
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
