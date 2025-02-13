package com.jredfox.lanessentials.packets;

import com.evilnotch.lib.api.ReflectionUtil;
import com.evilnotch.lib.minecraft.network.MessegeBase;
import com.jredfox.lanessentials.LEFields;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;

public class PacketNickHandler extends MessegeBase<PacketNick> {
	
	public PacketNickHandler(){}

	@Override
	public void handleClientSide(PacketNick message, EntityPlayer c) 
	{
		Minecraft.getMinecraft().addScheduledTask(() -> 
		{
			EntityPlayerSP p = Minecraft.getMinecraft().player;
			EntityPlayer targ = p.world.getPlayerEntityByUUID(message.id);
			if(targ == null)
				return;
			
			//Set the nickname
			ReflectionUtil.setObject(targ, message.nick, EntityPlayer.class, LEFields.displayname);
			
			//Sync the displayname from the server
			NetworkPlayerInfo info = p.connection.getPlayerInfo(message.id);
			if(info != null)
				info.setDisplayName(message.display);
		});
	}

	@Override
	public void handleServerSide(PacketNick message, EntityPlayer player) {}

}