package com.EvilNotch.lanessentials.dl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Scanner;


public class Dlfile 
{
	public static boolean downloadUUIDFile(String PlayerName)
	{
		try 
		{
			String username = PlayerName;
			File f = new File("config/uuidFiles/");
			f.mkdirs();
			File file = new File("config/uuidFiles/" + username + ".txt");
			if(file.exists())
			{
				return false;
			}
			URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
			Scanner s = new Scanner(url.openStream());
			if(!(s.hasNextLine()))
			{
				s.close();
				return false;
			}
			
			FileOutputStream fos = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fos);    
			Writer w = new BufferedWriter(osw);  
			
			while(s.hasNextLine())
			{
				 w.write(s.nextLine());
			}
			w.close();
			s.close();
			return true;
		} 
		catch (Throwable t) 
		{
			t.printStackTrace();
			return false;
		}
	}
	
	public static String GetSkinSite(String PlayerName)
	{
		String username = PlayerName;
		File file = new File("config/uuidFiles/" + username + ".txt");
		if(!(file.exists()))
		{
			downloadUUIDFile(PlayerName);
		}
		
		try {
			Scanner sc = new Scanner(file);  
	    	String line = sc.nextLine();
	    	line = line.substring(7, line.indexOf('"', 7));
	    	System.out.println(line);
	    	sc.close();
	    	String site = "https://sessionserver.mojang.com/session/minecraft/profile/"+line+"?unsigned=false";
	    	return site;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	public static boolean downloadSkinFile(String PlayerName)
	{
	
		try {
			String username = PlayerName;
			File f = new File("config/skinFiles/");
			f.mkdirs();
			File file = new File("config/skinFiles/" + username + ".txt");
			if(file.exists())
			{
				return true;
			}
			String site = GetSkinSite(PlayerName);
			
			URL url = new URL(site);
			Scanner s = new Scanner(url.openStream());
			if(!(s.hasNextLine()))
			{
				s.close();
				return false;
			}
			
			FileOutputStream fos = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fos);    
			Writer w = new BufferedWriter(osw);  
			
			while(s.hasNextLine())
			{
				 w.write(s.nextLine());
			}
			w.close();
			s.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
