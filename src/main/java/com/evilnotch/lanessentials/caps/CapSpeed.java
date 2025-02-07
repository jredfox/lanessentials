package com.evilnotch.lanessentials.caps;

import com.evilnotch.lib.minecraft.capability.CapContainer;
import com.evilnotch.lib.minecraft.capability.ICapability;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class CapSpeed  implements ICapability<EntityPlayerMP>
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
	public void readFromNBT(EntityPlayerMP player,NBTTagCompound nbt, CapContainer container) {
		this.walk = nbt.getFloat("walk");	
		this.fly = nbt.getFloat("fly");
		this.hasFlySpeed = nbt.getBoolean("flySpeedEnabled");
		this.hasWalkSpeed = nbt.getBoolean("walkSpeedEnabled");
	}
	@Override
	public void writeToNBT(EntityPlayerMP player,NBTTagCompound nbt, CapContainer container) {
		nbt.setFloat("walk", this.walk);
		nbt.setFloat("fly", this.fly);
		nbt.setBoolean("flySpeedEnabled", this.hasFlySpeed);
		nbt.setBoolean("walkSpeedEnabled", this.hasWalkSpeed);
	}
}
