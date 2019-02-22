package com.evilnotch.lanessentials.capabilities;

import com.evilnotch.lib.minecraft.capability.CapContainer;
import com.evilnotch.lib.minecraft.capability.ICapability;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class CapCape implements ICapability<EntityPlayerMP>{
	
	public String url = "";
	
	public CapCape(){}
	
	public CapCape(String username){
		this.url = username;
	}

	@Override
	public void readFromNBT(EntityPlayerMP p,NBTTagCompound nbt, CapContainer c) 
	{
		this.url = nbt.getString("cape");
	}

	@Override
	public void writeToNBT(EntityPlayerMP p,NBTTagCompound nbt, CapContainer c) {
		nbt.setString("cape", this.url);
	}

}
