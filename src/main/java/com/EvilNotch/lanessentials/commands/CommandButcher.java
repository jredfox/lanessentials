package com.EvilNotch.lanessentials.commands;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class CommandButcher extends CommandBase{
	
	public static List<String> list = null;

	@Override
	public String getName() {
		return "butcher";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/butcher [types,radius]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length == 0)
			throw new WrongUsageException("Must Specify Type/Entity");
		WorldServer w = (WorldServer) sender.getEntityWorld();
		Types type = Types.getType(args[0]);
		List<Entity> ents = w.getLoadedEntityList();
		
		if(type == null)
		{
			ResourceLocation loc = new ResourceLocation(args[0]);
			for(Entity e : ents)
			{
				ResourceLocation compare = EntityList.getKey(e);
				if(loc.equals(compare))
					e.setDead();
			}
		}
		else if(type == Types.living)
		{
			for(Entity e : ents)
			{
				if(e instanceof EntityLiving && !(e instanceof EntityPlayer))
					e.setDead();
			}
		}
		else if(type == Types.nonliving)
		{
			for(Entity e : ents)
			{
				if(!(e instanceof EntityLiving) && !(e instanceof EntityLivingBase))
					e.setDead();
			}
		}
		else if (type == Types.entityBase)
		{
			for(Entity e : ents)
			{
				if(!(e instanceof EntityLiving) && e instanceof EntityLivingBase && !(e instanceof EntityPlayer))
					e.setDead();
			}
		}
		else if (type == Types.item)
		{
			for(Entity e : ents)
			{
				if(e instanceof EntityItem)
					e.setDead();
			}
		}
		else if (type == Types.projectile)
		{
			for(Entity e : ents)
			{
				if(e instanceof Entity && e instanceof IProjectile || e instanceof EntityFireball)
					e.setDead();
			}
		}
		else if (type == Types.fire)
		{
			for(Entity e : ents)
			{
				if(e.isImmuneToFire() && !(e instanceof EntityPlayer))
					e.setDead();
			}
		}
		else if (type == Types.water)
		{
			for(Entity e : ents)
			{
				boolean water = e.isCreatureType(EnumCreatureType.WATER_CREATURE, false) || e instanceof EntityGuardian;
				if(!water && e instanceof EntityLivingBase)
				{
					water = ((EntityLivingBase)e).canBreatheUnderwater();
				}
				if(water && !(e instanceof EntityPlayer))
					e.setDead();
			}
		}
		else if (type == Types.monster)
		{
			for(Entity e : ents)
			{
				if(isHostile(e))
					e.setDead();
			}
		}
		else if (type == Types.creature)
		{
			for(Entity e : ents)
			{
				if(isCreature(e) && !(e instanceof EntityPlayer))
					e.setDead();
			}
		}
		else if(type == Types.ambient)
		{
			for(Entity e : ents)
			{
				boolean ambient = e.isCreatureType(EnumCreatureType.AMBIENT, false);
				if(ambient)
				{
					e.setDead();
				}
			}
		}
		else if(type == Types.all)
		{
			for(Entity e : ents)
			{
				if(!(e instanceof EntityPlayer))
					e.setDead();
			}
		}
	}
	/**
	 * determine if mob is hostile
	 * @param e
	 * @return
	 */
	public boolean isHostile(Entity e) 
	{
		boolean monster = e.isCreatureType(EnumCreatureType.MONSTER, false);
		if(!monster)
			return e instanceof EntityMob || e instanceof IRangedAttackMob;
		return true;
	}
	public boolean isCreature(Entity e){
		boolean creature = e.isCreatureType(EnumCreatureType.CREATURE, false);
		return creature;
	}
	@Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
		if(list == null)
		{
			list = new ArrayList();
			for(Types t : Types.values())
				list.add(t.s);
		
			List<String> li = new ArrayList();
			for(ResourceLocation loc :  EntityList.getEntityNameList())
				list.add(loc.toString());
		}
		return getListOfStringsMatchingLastWord(args,list);
    }

	public static enum Types{
		living("living"),
		nonliving("nonliving"),
		entityBase("entitybase"),
		item("item"),
		projectile("projectile"),
		fire("fire"),
		water("water"),
		monster("monster"),
		creature("creature"),
		ambient("ambient"),
		all("all");
		
		public String s;
		
		Types(String s)
		{
			this.s = s;
		}
		
		public static Types getType(String str)
		{
			str = str.toLowerCase();
			Types[] types = Types.values();
			for(Types type : types)
			{
				if(type.s.equals(str))
					return type;
			}
			return null;
		}
	}
}
