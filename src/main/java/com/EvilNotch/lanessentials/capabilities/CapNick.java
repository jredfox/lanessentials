package com.EvilNotch.lanessentials.capabilities;

import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityContainer;
import com.EvilNotch.lib.minecraft.content.pcapabilites.ICapability;
import com.EvilNotch.lib.util.ICopy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class CapNick implements ICapability{

	public String nick = "";
	
	public CapNick(){}
	
	public CapNick(String username){
		this.nick = username;
	}

	@Override
	public ICopy copy() {
		return new CapSkin(this.nick);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, EntityPlayer p, CapabilityContainer c) 
	{
		this.nick = nbt.getString("nick");
		if(this.nick.equals(""))
			this.nick = p.getName();
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, EntityPlayer p, CapabilityContainer c) {
		nbt.setString("nick", this.nick);
	}

}
