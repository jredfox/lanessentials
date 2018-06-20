package com.EvilNotch.lanessentials.packets;

import com.EvilNotch.lib.Api.MCPMappings;
import com.EvilNotch.lib.Api.ReflectionUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketNickHandler extends MessegeBase<PacketDisplayNameRefresh>{
	
	public PacketNickHandler(){}

	@Override
	public void handleClientSide(PacketDisplayNameRefresh message, EntityPlayer client) 
	{
		EntityPlayer player = (EntityPlayer) client.world.getEntityByID(message.id);
		if(player == null)
		{
			System.out.println("Recieved Packet NickName For Invalid PlayerID:" + message.id + " debug:" + client.world.playerEntities);
			return;
		}
		System.out.println("setting player:" + player.getName() + " > " + message.nick);
		ReflectionUtil.setObject(player, message.nick, EntityPlayer.class, MCPMappings.getField(EntityPlayer.class, "displayname"));
	}

	@Override
	public void handleServerSide(PacketDisplayNameRefresh message, EntityPlayer player) {}

}