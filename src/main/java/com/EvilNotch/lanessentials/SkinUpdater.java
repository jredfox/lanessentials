package com.EvilNotch.lanessentials;

import com.EvilNotch.lib.minecraft.EntityUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketEntityEquipment;
import net.minecraft.network.play.server.SPacketHeldItemChange;
import net.minecraft.network.play.server.SPacketPlayerAbilities;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.network.play.server.SPacketRespawn;
import net.minecraft.network.play.server.SPacketSetExperience;
import net.minecraft.network.play.server.SPacketSpawnPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.management.PlayerList;
import net.minecraft.world.GameType;

public class SkinUpdater
{
  public static void updateSkin(EntityPlayerMP p)
  {
	SPacketPlayerListItem removeInfo;
	SPacketDestroyEntities removeEntity;
	SPacketSpawnPlayer addNamed;
    SPacketPlayerListItem addInfo;
    SPacketRespawn respawn;
    try
    {
      int entId = p.getEntityId();
      
      removeInfo = new SPacketPlayerListItem(SPacketPlayerListItem.Action.REMOVE_PLAYER,p);
      
      removeEntity = new SPacketDestroyEntities(new int[] { entId });
      
      addNamed = new SPacketSpawnPlayer(p);
      
      addInfo = new SPacketPlayerListItem(SPacketPlayerListItem.Action.ADD_PLAYER,p);
      
      respawn = new SPacketRespawn(p.dimension, p.getServerWorld().getDifficulty(), p.getServerWorld().getWorldType(), p.getServer().getGameType());
      
      for (EntityPlayer pOnlines : p.getServerWorld().playerEntities)
      {
    	  EntityPlayerMP pOnline = (EntityPlayerMP)pOnlines;
    	  NetHandlerPlayServer con = pOnline.connection;
        if (pOnline.equals(p))
        {
          con.sendPacket(removeInfo);
          con.sendPacket(addInfo);
          con.sendPacket(respawn);
          
          p.connection.setPlayerLocation(p.posX, p.posY, p.posZ, p.rotationYaw, p.rotationPitch);
          p.interactionManager.setWorld(p.getServerWorld());
          p.connection.sendPacket(new SPacketPlayerAbilities(p.capabilities));    
        }
        else 
        {
          con.sendPacket(removeEntity);
          con.sendPacket(removeInfo);
          con.sendPacket(addInfo);
          con.sendPacket(addNamed);
        }
      }
    }
    catch (Exception localException) {}
  }
}