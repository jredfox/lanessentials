package com.EvilNotch.lanessentials.capabilities;

import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityContainer;
import com.EvilNotch.lib.minecraft.content.pcapabilites.ICapability;
import com.EvilNotch.lib.util.ICopy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class CapCape implements ICapability{
	
	public String url = "";
	
	public CapCape(){}
	
	public CapCape(String username){
		this.url = username;
	}

	@Override
	public ICopy copy() {
		return new CapSkin(this.url);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, EntityPlayer p, CapabilityContainer c) 
	{
		this.url = nbt.getString("cape");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, EntityPlayer p, CapabilityContainer c) {
		nbt.setString("cape", this.url);
	}

}
