package com.jredfox.lanessentials.packets;

import java.util.UUID;

import com.evilnotch.lib.minecraft.network.NetWorkWrapper;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketNick implements IMessage {
	
	public UUID id;
	public String nick;
	public ITextComponent display;
	
	public PacketNick(){}//GuiPlayerTabOverlay

	public PacketNick(EntityPlayerMP p) {
	   	this.id = p.getUniqueID();
	   	this.nick = p.getDisplayNameString();
	   	this.display = p.getDisplayName();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.id = NetWorkWrapper.readUUID(buf);
		this.nick = ByteBufUtils.readUTF8String(buf);
		this.display = ITextComponent.Serializer.jsonToComponent(ByteBufUtils.readUTF8String(buf));
	}

	@Override
	public void toBytes(ByteBuf buf) {
		 NetWorkWrapper.writeUUID(buf, this.id);
		 ByteBufUtils.writeUTF8String(buf, this.nick);
		 ByteBufUtils.writeUTF8String(buf, ITextComponent.Serializer.componentToJson(this.display));
	}

}
