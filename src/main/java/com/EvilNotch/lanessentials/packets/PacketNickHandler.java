package com.evilnotch.lanessentials.packets;

import com.evilnotch.lib.api.ReflectionUtil;
import com.evilnotch.lib.api.mcp.MCPMappings;
import com.evilnotch.lib.minecraft.network.MessegeBase;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class PacketNickHandler extends MessegeBase<PacketDisplayNameRefresh>{
	
	public PacketNickHandler(){}

	@Override
	public void handleClientSide(PacketDisplayNameRefresh message, EntityPlayer client) 
	{
		Minecraft.getMinecraft().addScheduledTask(() -> 
		{
			EntityPlayer player = (EntityPlayer) client.world.getEntityByID(message.id);
			if(player == null)
			{
				System.out.println("Recieved Packet NickName For Invalid PlayerID:" + message.id + " debug:" + client.world.playerEntities);
				return;
			}
//			System.out.println("setting player:" + player.getName() + " > " + message.nick);
			ReflectionUtil.setObject(player, message.nick, EntityPlayer.class, MCPMappings.getField(EntityPlayer.class, "displayname"));
		});
	}

	@Override
	public void handleServerSide(PacketDisplayNameRefresh message, EntityPlayer player) {}

}