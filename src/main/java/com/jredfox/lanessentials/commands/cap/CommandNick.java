package com.jredfox.lanessentials.commands.cap;

import com.jredfox.lanessentials.cap.CapHandler;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class CommandNick extends CommandBase{
	
	@Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

	@Override
	public String getName() {
		return "nick";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "nick [displayname]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(!(sender instanceof EntityPlayerMP)) return;
		EntityPlayerMP player = (EntityPlayerMP) sender;
		String nick = "";
		for(int i=0;i<args.length;i++)
			nick += (i > 0 ? " " : "") + args[i];
		nick = nick.trim();
		CapHandler.setNick(player, nick, true);
	}
}
