package com.EvilNotch.lanessentials.packets;

import java.util.Collection;

import com.EvilNotch.lib.Api.MCPMappings;
import com.EvilNotch.lib.Api.ReflectionUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

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
			System.out.println("setting player:" + player.getName() + " > " + message.nick);
			ReflectionUtil.setObject(player, message.nick, EntityPlayer.class, MCPMappings.getField(EntityPlayer.class, "displayname"));
		});
	}

	@Override
	public void handleServerSide(PacketDisplayNameRefresh message, EntityPlayer player) {}

}