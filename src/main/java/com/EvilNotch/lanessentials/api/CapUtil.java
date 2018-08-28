package com.EvilNotch.lanessentials.api;

import java.util.HashSet;
import java.util.Set;

import com.EvilNotch.lanessentials.Reference;
import com.EvilNotch.lanessentials.capabilities.CapAbility;
import com.EvilNotch.lanessentials.capabilities.CapNick;
import com.EvilNotch.lanessentials.capabilities.CapSpeed;
import com.EvilNotch.lanessentials.packets.NetWorkHandler;
import com.EvilNotch.lanessentials.packets.PacketDisplayNameRefresh;
import com.EvilNotch.lib.Api.MCPMappings;
import com.EvilNotch.lib.Api.ReflectionUtil;
import com.EvilNotch.lib.minecraft.content.capabilites.registry.CapContainer;
import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityReg;

import joptsimple.internal.Strings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.network.play.server.SPacketPlayerListItem.AddPlayerData;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

public class CapUtil {
	
	public static void updateCaps(EntityPlayerMP player, CapContainer container,boolean login) 
	{
		CapAbility cap = (CapAbility) container.getCapability(new ResourceLocation(Reference.MODID + ":" + "ability"));
		CapSpeed speed = (CapSpeed) container.getCapability( new ResourceLocation(Reference.MODID + ":" + "speed"));
		PlayerCapabilities pcap = player.capabilities;
		boolean used = false;
		if(!pcap.allowFlying && cap.flyEnabled)
		{
			pcap.allowFlying = true;
			if(login)
				pcap.isFlying = cap.isFlying;
			used = true;
		}
		if(!pcap.disableDamage && cap.godEnabled)
		{
			pcap.disableDamage = true;
			used = true;
		}
		if(speed.hasFlySpeed)
        {
            ReflectionUtil.setObject(pcap, speed.fly, PlayerCapabilities.class, LanFeilds.flySpeed);
            used = true;
        }
        if(speed.hasWalkSpeed)
        {
        	ReflectionUtil.setObject(pcap, speed.walk, PlayerCapabilities.class, LanFeilds.walkSpeed);
            used = true;
        }
		if(used)
			player.sendPlayerAbilities();
	}
	
	/**
	 * makes player on login aware of all player nicknames
	 */
	public static void updateClientNicks(EntityPlayerMP request) 
	{
		Set<? extends EntityPlayer> players = request.getServerWorld().getEntityTracker().getTrackingPlayers(request);
		for(EntityPlayerMP newPlayer : request.mcServer.getPlayerList().getPlayers())
		{
			if(!request.equals(newPlayer))
			{
		    	CapNick name = (CapNick) CapabilityReg.getCapability(newPlayer, new ResourceLocation(Reference.MODID + ":" + "nick"));
		    	if(Strings.isNullOrEmpty(name.nick))
		    	{
		    		continue;
		    	}
		    	SPacketPlayerListItem item = new SPacketPlayerListItem();
		        AddPlayerData apd = item.new AddPlayerData(newPlayer.getGameProfile(), newPlayer.ping, newPlayer.interactionManager.getGameType(), new TextComponentString(name.nick));
		        ReflectionUtil.setObject(item, SPacketPlayerListItem.Action.UPDATE_DISPLAY_NAME, SPacketPlayerListItem.class, MCPMappings.getField(SPacketPlayerListItem.class, LanFeilds.nickAction));
		        item.getEntries().add(apd);
		    	
		        request.connection.sendPacket(item);
		        if(players.contains(newPlayer))
		        {
		        	NetWorkHandler.INSTANCE.sendTo(new PacketDisplayNameRefresh(name.nick, newPlayer.getEntityId()), request);
		        }
			}
		}
	}
	/**
	 * optimized version for when requesting entity is about to start tracking the player without updating it to everyone
	 */
	public static void updateTrackNickName(EntityPlayerMP request,EntityPlayerMP newPlayer)
	{
    	CapNick name = (CapNick) CapabilityReg.getCapability(newPlayer, new ResourceLocation(Reference.MODID + ":" + "nick"));
    	if(Strings.isNullOrEmpty(name.nick))
    	{
    		return;
    	}
    	SPacketPlayerListItem item = new SPacketPlayerListItem();
        AddPlayerData apd = item.new AddPlayerData(newPlayer.getGameProfile(), newPlayer.ping, newPlayer.interactionManager.getGameType(), new TextComponentString(name.nick));
        ReflectionUtil.setObject(item, SPacketPlayerListItem.Action.UPDATE_DISPLAY_NAME, SPacketPlayerListItem.class, MCPMappings.getField(SPacketPlayerListItem.class, LanFeilds.nickAction));
        item.getEntries().add(apd);
    	
        request.connection.sendPacket(item);
        NetWorkHandler.INSTANCE.sendTo(new PacketDisplayNameRefresh(name.nick, newPlayer.getEntityId()), request);
	}
	public static void updateNickName(EntityPlayerMP player) 
	{
    	CapNick name = (CapNick) CapabilityReg.getCapability(player, new ResourceLocation(Reference.MODID + ":" + "nick"));
    	if(Strings.isNullOrEmpty(name.nick))
    		return;
    	player.refreshDisplayName();
    	SPacketPlayerListItem item = new SPacketPlayerListItem();
        AddPlayerData apd = item.new AddPlayerData(player.getGameProfile(), player.ping, player.interactionManager.getGameType(), new TextComponentString(name.nick));
        ReflectionUtil.setObject(item, SPacketPlayerListItem.Action.UPDATE_DISPLAY_NAME, SPacketPlayerListItem.class, MCPMappings.getField(SPacketPlayerListItem.class, LanFeilds.nickAction));
        item.getEntries().add(apd);
        
        Set<? extends EntityPlayer> li = player.getServerWorld().getEntityTracker().getTrackingPlayers(player);
        Set<EntityPlayerMP> players = new HashSet();
        for(EntityPlayer p : li)
        	players.add((EntityPlayerMP)p);
    	
        for(EntityPlayerMP p : players)
        {
            if(!p.equals(player))
            {
            	NetWorkHandler.INSTANCE.sendTo(new PacketDisplayNameRefresh(name.nick, player.getEntityId()), p);
            }
        }
        for(EntityPlayerMP p : player.mcServer.getPlayerList().getPlayers())
        {
        	p.connection.sendPacket(item);
        }
	}
	
}
