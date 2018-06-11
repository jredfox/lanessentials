package com.EvilNotch.lanessentials.commands;

public class SkinData {
	
	public String uuid;
	public String value;
	public String signature;
	
	public SkinData(String u,String v, String s)
	{
		this.uuid = u;
		this.value = v;
		this.signature = s;
	}
	
	public SkinData(String u, String[] p) {
		this.uuid = u;
		this.value = p[0];
		this.signature = p[1];
	}

	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof SkinData))
			return false;
		SkinData other = (SkinData)obj;
		return this.uuid.equals(other.uuid);
	}

}
