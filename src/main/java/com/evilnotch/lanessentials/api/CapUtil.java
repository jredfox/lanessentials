package com.evilnotch.lanessentials.api;

public class CapUtil {
	
//	public static void updateCaps(EntityPlayerMP player, boolean login) 
//	{
//		CapContainer container = CapabilityRegistry.getCapContainer(player);
//		CapAbility cap = (CapAbility) container.getCapability(LanFields.ABILITY);
//		CapSpeed speed = (CapSpeed) container.getCapability(LanFields.SPEED);
//		PlayerCapabilities pcap = player.capabilities;
//		boolean used = false;
//		if(!pcap.allowFlying && cap.flyEnabled)
//		{
//			pcap.allowFlying = true;
//			if(login)
//				pcap.isFlying = cap.isFlying;
//			used = true;
//		}
//		if(!pcap.disableDamage && cap.godEnabled)
//		{
//			pcap.disableDamage = true;
//			used = true;
//		}
//		if(speed.hasFlySpeed)
//        {
//            ReflectionUtil.setObject(pcap, speed.fly, PlayerCapabilities.class, LanFields.flySpeed);
//            used = true;
//        }
//        if(speed.hasWalkSpeed)
//        {
//        	ReflectionUtil.setObject(pcap, speed.walk, PlayerCapabilities.class, LanFields.walkSpeed);
//            used = true;
//        }
//		if(used)
//			player.sendPlayerAbilities();
//	}
//	
//	/**
//	 * makes player on login aware of all player nicknames
//	 */
//	public static void updateClientNicks(EntityPlayerMP request) 
//	{
//		Set<? extends EntityPlayer> players = request.getServerWorld().getEntityTracker().getTrackingPlayers(request);
//		for(EntityPlayerMP newPlayer : request.mcServer.getPlayerList().getPlayers())
//		{
//			if(!request.equals(newPlayer))
//			{
//		    	CapNick name = (CapNick) CapabilityRegistry.getCapability(newPlayer, new ResourceLocation(LanEssentials.MODID + ":" + "nick"));
//		    	if(Strings.isNullOrEmpty(name.nick))
//		    	{
//		    		continue;
//		    	}
//		    	SPacketPlayerListItem item = new SPacketPlayerListItem();
//		        AddPlayerData apd = item.new AddPlayerData(newPlayer.getGameProfile(), newPlayer.ping, newPlayer.interactionManager.getGameType(), new TextComponentString(name.nick));
//		        ReflectionUtil.setObject(item, SPacketPlayerListItem.Action.UPDATE_DISPLAY_NAME, SPacketPlayerListItem.class, LanFields.nickAction);
//		        item.getEntries().add(apd);
//		    	
//		        request.connection.sendPacket(item);
//		        if(players.contains(newPlayer))
//		        {
//		        	NetWorkHandler.INSTANCE.sendTo(new PacketNick(name.nick, newPlayer.getEntityId()), request);
//		        }
//			}
//		}
//	}
//	/**
//	 * optimized version for when requesting entity is about to start tracking the player without updating it to everyone
//	 */
//	public static void updateTrackNickName(EntityPlayerMP request,EntityPlayerMP newPlayer)
//	{
//    	CapNick name = (CapNick) CapabilityRegistry.getCapability(newPlayer, new ResourceLocation(LanEssentials.MODID + ":" + "nick"));
//    	if(Strings.isNullOrEmpty(name.nick))
//    	{
//    		return;
//    	}
//    	SPacketPlayerListItem item = new SPacketPlayerListItem();
//        AddPlayerData apd = item.new AddPlayerData(newPlayer.getGameProfile(), newPlayer.ping, newPlayer.interactionManager.getGameType(), new TextComponentString(name.nick));
//        ReflectionUtil.setObject(item, SPacketPlayerListItem.Action.UPDATE_DISPLAY_NAME, SPacketPlayerListItem.class, LanFields.nickAction);
//        item.getEntries().add(apd);
//    	
//        request.connection.sendPacket(item);
//        NetWorkHandler.INSTANCE.sendTo(new PacketNick(name.nick, newPlayer.getEntityId()), request);
//	}
//	
//	public static void updateNickName(EntityPlayerMP player) 
//	{
//    	CapNick name = (CapNick) CapabilityRegistry.getCapability(player, LanFields.NICK);
//    	if(Strings.isNullOrEmpty(name.nick))
//    		return;
//    	player.refreshDisplayName();
//    	SPacketPlayerListItem item = new SPacketPlayerListItem();
//        AddPlayerData apd = item.new AddPlayerData(player.getGameProfile(), player.ping, player.interactionManager.getGameType(), new TextComponentString(name.nick));
//        ReflectionUtil.setObject(item, SPacketPlayerListItem.Action.UPDATE_DISPLAY_NAME, SPacketPlayerListItem.class, LanFields.nickAction);
//        item.getEntries().add(apd);
//        
//        Set<? extends EntityPlayer> li = player.getServerWorld().getEntityTracker().getTrackingPlayers(player);
//        Set<EntityPlayerMP> players = new HashSet();
//        for(EntityPlayer p : li)
//        	players.add((EntityPlayerMP)p);
//    	
//        for(EntityPlayerMP p : players)
//        {
//            if(!p.equals(player))
//            {
//            	NetWorkHandler.INSTANCE.sendTo(new PacketNick(name.nick, player.getEntityId()), p);
//            }
//        }
//        for(EntityPlayerMP p : player.mcServer.getPlayerList().getPlayers())
//        {
//        	p.connection.sendPacket(item);
//        }
//	}
	
}
