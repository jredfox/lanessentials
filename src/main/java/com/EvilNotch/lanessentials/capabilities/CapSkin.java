package com.EvilNotch.lanessentials.capabilities;

import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityContainer;
import com.EvilNotch.lib.minecraft.content.pcapabilites.ICapability;
import com.EvilNotch.lib.util.ICopy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class CapSkin implements ICapability{
	
	public String skin = "";
	
	public CapSkin(){}
	
	public CapSkin(String username){
		this.skin = username;
	}

	@Override
	public ICopy copy() {
		return new CapSkin(this.skin);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, EntityPlayer p, CapabilityContainer c) 
	{
		this.skin = nbt.getString("skin");
		if(this.skin.equals(""))
			this.skin = p.getName();
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, EntityPlayer p, CapabilityContainer c) {
		nbt.setString("skin", this.skin);
	}

}