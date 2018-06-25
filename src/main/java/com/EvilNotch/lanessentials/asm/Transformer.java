package com.EvilNotch.lanessentials.asm;

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
    public final String className = "com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService";

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

        return bytes;
    }

    public byte[] patchSkins(String name) throws IOException
    {   
        InputStream lenStream = Transformer.class.getResourceAsStream("classes/" + (name.contains("$") ? "YggdrasilMinecraftSessionService$1" : "YggdrasilMinecraftSessionService"));
        byte[] bytes = IOUtils.toByteArray(lenStream);
        System.out.println("Lan Essentials Transformer Patched Class: \"" + name + "\" for skins");
        return bytes;
    }
}
