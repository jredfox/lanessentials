package com.EvilNotch.lanessentials.capabilities;

import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityContainer;
import com.EvilNotch.lib.minecraft.content.pcapabilites.ICapability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class CapSkin implements ICapability{
	
	public String skin = "";
	public boolean isAlex = false;
	
	public CapSkin(){}
	
	public CapSkin(String username){
		this.skin = username;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, EntityPlayer p, CapabilityContainer c) 
	{
		this.skin = nbt.getString("skin");
		if(this.skin.equals(""))
			this.skin = p.getName();
		this.isAlex = nbt.getBoolean("isAlex");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, EntityPlayer p, CapabilityContainer c) {
		nbt.setString("skin", this.skin);
		nbt.setBoolean("isAlex", this.isAlex);
	}

}