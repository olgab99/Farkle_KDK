package com.olmatech.kindle.farkle;

import java.io.Serializable;

public class HighScoreItem implements Comparable, Serializable{
	
	private String name;
	private int score;
	
	public HighScoreItem(final String n, final int sc)
	{
		name = n;
		score = sc;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public String getPlayerName()
	{
		return name;
	}
	
	public void setPlayerName(final String val)
	{
		name = val;
	}

	public int compareTo(Object arg) {
		HighScoreItem item = (HighScoreItem)arg;
		if(item.score == score)
		{
			return name.compareTo(item.name);
		}
		else if(score > item.score) return 1;
		else return -1;		
	}

}
