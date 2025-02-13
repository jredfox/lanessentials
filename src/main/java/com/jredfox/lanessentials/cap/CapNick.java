package com.jredfox.lanessentials.cap;

import com.evilnotch.lib.minecraft.capability.CapContainer;
import com.evilnotch.lib.minecraft.capability.ICapabilityTick;
import com.evilnotch.lib.minecraft.network.NetWorkHandler;
import com.jredfox.lanessentials.packets.PacketNick;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Team;

public class CapNick implements ICapabilityTick<EntityPlayerMP> {

	public String nick = "";
	
	protected Team ct;
	protected String dn;
	
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

	@Override
	public void tick(EntityPlayerMP p, CapContainer c) 
	{
		Team t = p.getTeam();
		String d = p.getDisplayName().getFormattedText();
		//Detect team change and handle it
		if(t != this.ct)
		{
			System.out.println("TeamChange Detected:" + t + " prev:" + this.ct);
			NetWorkHandler.INSTANCE.sendToAll(new PacketNick(p));
		}
		//Detect Color Changes
		else if(t != null && d != null && !d.equals(this.dn))
		{
			System.out.println("Color Change Detected:" + d + " prev:" + this.dn);
			NetWorkHandler.INSTANCE.sendToAll(new PacketNick(p));
		}
		this.ct = t;
		this.dn = d;
	}

}
