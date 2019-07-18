package com.olmatech.kindle.farkle;

public class RetResult {
	
	private int val;// how many given
	private int rest;
	
	public RetResult()
	{
		val =0;
		rest =0;
	}
	
	public RetResult(final int v, final int r)
	{
		val = v;
		rest = r;
	}
	
	public int getValue()
	{
		return val;
	}
	
	public int getRest()
	{
		return rest;
	}

}
