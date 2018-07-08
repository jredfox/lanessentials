package com.EvilNotch.lanessentials.capabilities;

import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityContainer;
import com.EvilNotch.lib.minecraft.content.pcapabilites.ICapability;
import com.EvilNotch.lib.util.ICopy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class CapSpeed  implements ICapability
{
	public float walk;
	public float fly;
	public boolean hasFlySpeed = false;
	public boolean hasWalkSpeed = false;
	
	public CapSpeed(){}
	
	public CapSpeed(float walk,float fly,boolean fe,boolean we){
		this.walk = walk;
		this.fly = fly;
		this.hasFlySpeed = fe;
		this.hasWalkSpeed = we;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt, EntityPlayer player, CapabilityContainer container) {
		this.walk = nbt.getFloat("walk");	
		this.fly = nbt.getFloat("fly");
		this.hasFlySpeed = nbt.getBoolean("flySpeedEnabled");
		this.hasWalkSpeed = nbt.getBoolean("walkSpeedEnabled");
	}
	@Override
	public void writeToNBT(NBTTagCompound nbt, EntityPlayer player, CapabilityContainer container) {
		nbt.setFloat("walk", this.walk);
		nbt.setFloat("fly", this.fly);
		nbt.setBoolean("flySpeedEnabled", this.hasFlySpeed);
		nbt.setBoolean("walkSpeedEnabled", this.hasWalkSpeed);
	}
}
