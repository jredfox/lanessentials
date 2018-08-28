package com.EvilNotch.lanessentials.capabilities;

import com.EvilNotch.lib.minecraft.content.capabilites.registry.CapContainer;
import com.EvilNotch.lib.minecraft.content.pcapabilites.IPCapability;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class CapCape implements IPCapability{
	
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
