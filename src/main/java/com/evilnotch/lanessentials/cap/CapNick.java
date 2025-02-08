package com.evilnotch.lanessentials.cap;

import com.evilnotch.lib.minecraft.capability.CapContainer;
import com.evilnotch.lib.minecraft.capability.ICapability;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class CapNick implements ICapability<EntityPlayerMP>{

	public String nick = "";
	
	public CapNick(){}
	
	public CapNick(String username){
		this.nick = username;
	}

	@Override
	public void readFromNBT(EntityPlayerMP p, NBTTagCompound nbt, CapContainer c) {
		this.nick = nbt.getString("nick");
	}

	@Override
	public void writeToNBT(EntityPlayerMP p, NBTTagCompound nbt, CapContainer c) {
		nbt.setString("nick", this.nick);
	}

}
