package com.evilnotch.lanessentials.packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketDisplayNameRefresh implements IMessage{
	
	public String nick = "null";
	public int id;
	
	public PacketDisplayNameRefresh(){
		
	}
	
	public PacketDisplayNameRefresh(String name,int id){
		this.nick = name;
		this.id = id;
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		this.id = buffer.readInt();
		this.nick = ByteBufUtils.readUTF8String(buffer);
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		 buffer.writeInt(this.id);
		 ByteBufUtils.writeUTF8String(buffer, this.nick);
	}

}
