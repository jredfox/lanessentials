package com.EvilNotch.lanessentials;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.EvilNotch.lib.main.MainJava;
import com.EvilNotch.lib.util.JavaUtil;
import com.dosse.upnp.UPnP;

public class LanUtil {
	
	public static HashMap<Integer,String> ports = new HashMap<Integer,String>();
	
	public static void doPortForwarding(int port,String protocal)
	{
		hasPorted = true;
		long time = System.currentTimeMillis();
		boolean tcp = protocal.equals("TCP") || protocal.equals("BOTH");
		boolean udp =  protocal.equals("UDP") || protocal.equals("BOTH");
		if(udp)
		{
			System.out.println("port opened UDP:" + UPnP.openPortUDP(port) + " on port:" + port);
		}
		if(tcp)
		{
			System.out.println("port opened TCP:" + UPnP.openPortTCP(port) + " on port:" + port);
		}
		ports.put(port, protocal);
		System.out.println("mapping:" + UPnP.isUPnPAvailable());
		System.out.println("time:" + (System.currentTimeMillis()-time) + "ms");
	}
	
	public static void stopPorts() 
	{
    	Iterator<Map.Entry<Integer,String>> it = ports.entrySet().iterator();
    	while(it.hasNext())
    	{
    		Map.Entry<Integer, String> pair = it.next();
    		int port = pair.getKey();
    		String protocal = pair.getValue();
    		boolean tcp = protocal.equals("TCP") || protocal.equals("BOTH");
    		boolean udp =  protocal.equals("UDP") || protocal.equals("BOTH");
    		if(tcp)
    			UPnP.closePortTCP(port);
    		if(udp)
    			UPnP.closePortUDP(port);
    	}
       	ports.clear();
	}

	/**
	 * creates port forwarding on it's own new thread so it doesn't freeze for seconds
	 */
	public static boolean hasPorted = false;
	public static void schedulePortForwarding(int serverPort, String protocal) 
	{
		System.out.println("here:" + MainJava.isClient + " hasPortedBefore:" + hasPorted);
        Thread t = new Thread(
     		   
        new Runnable() 
        { 
     	   public void run() 
     	   { 
     		   if(!UPnP.isUPnPUpNow() && hasPorted)
     		   {
     			   UPnP.refresh();
     		   }
     		   doPortForwarding(serverPort,protocal);
     	   }
        });
        t.start();
	}

}
