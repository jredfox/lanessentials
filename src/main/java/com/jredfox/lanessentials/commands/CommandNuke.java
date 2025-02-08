package com.jredfox.lanessentials.commands;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

public class CommandNuke extends CommandBase{
	
   @Override
   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws PlayerNotFoundException, CommandException 
   {		 
	   EntityPlayerMP player = args.length == 0 ? (EntityPlayerMP) sender : getPlayer(server, sender, args[0]);
       BlockPos loc = player.getPosition();
       WorldServer w = player.getServerWorld();
       
       for (int x = -10; x <= 10; x += 5) 
       {
          for (int z = -10; z <= 10; z += 5) 
          {
             BlockPos tntloc = new BlockPos(loc.getX() + x, getHighestBlockYAt(w,loc) + 64, loc.getZ() + z);
             EntityTNTPrimed e = new EntityTNTPrimed(w);
             e.setLocationAndAngles(tntloc.getX(), tntloc.getY(), tntloc.getZ(), 0, 0);
             w.spawnEntity(e);
          }
       }
   }
   
   public int getHighestBlockYAt(WorldServer w, BlockPos loc) 
   {
	   return  w.getHeight(loc).getY();
   }
   
   @Override
   public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) 
   {
	   if (args.length == 1) 
		   return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
	   return Collections.emptyList();
   }
   
   @Override
   public String getName() 
   {
	   return "nuke";
   }
   
   @Override
   public String getUsage(ICommandSender sender) 
   {
	   return "nuke [entity]";
   }

}
