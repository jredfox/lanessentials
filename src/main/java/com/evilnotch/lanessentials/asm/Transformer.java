package com.evilnotch.lanessentials.asm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;

import net.minecraft.launchwrapper.IClassTransformer;

public class Transformer implements IClassTransformer
{
    /* private final String obfClassName = "qh"; */
    public static final String className = "com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService";
    public static final String inputBase = "assets/lanessentials/classes/";

    @Override
    public byte[] transform(String name, String newName, byte[] bytes)
    {
        if(name.contains(className))
        {
            try
            {
                return patchSkins(name);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        else if(name.equals("bjq"))
        {
        	//patch gui multi player tab
        	try
        	{
				return patchGui(name);
        	}
        	catch(IOException e)
        	{
        		e.printStackTrace();
        	}
        }

        return bytes;
    }
    
    public byte[] patchGui(String name) throws IOException
    {   
    	String[] parts = name.split("\\.");
    	name = parts[parts.length-1];
        InputStream lenStream = Transformer.class.getClassLoader().getResourceAsStream(inputBase + "client/gui/" + name);
        byte[] bytes = IOUtils.toByteArray(lenStream);
        System.out.println("Lan Essentials Transformer Patched Class: \"" + name + "\" for nick");
        return bytes;
    }

    public byte[] patchSkins(String name) throws IOException
    {   
        InputStream lenStream = Transformer.class.getClassLoader().getResourceAsStream(inputBase + (name.contains("$") ? "YggdrasilMinecraftSessionService$1" : "YggdrasilMinecraftSessionService"));
        System.out.println(inputBase + (name.contains("$") ? "YggdrasilMinecraftSessionService$1" : "YggdrasilMinecraftSessionService"));
        byte[] bytes = IOUtils.toByteArray(lenStream);
        System.out.println("Lan Essentials Transformer Patched Class: \"" + name + "\" for skins");
        return bytes;
    }
}
