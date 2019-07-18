package com.olmatech.kindle.farkle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import edu.emory.mathcs.backport.java.util.LinkedList;

public class Player {
	public final static int MAX_TURNS = 99;
	public final static int MAX_ROLLS = 5;
	
	private String name;
	private int total_score;
	//turn scores
	private LinkedList listScores; //list of Integers of turn scores
	private int currentTurnIndex =0; 
	private int curRoll = 0; // up to 5 rolls
	private int[] rollScores = new int[MAX_ROLLS];
	
	//temp to keep cur scrore after score check
	private int curCheckedScore=0;
	
	private boolean isHuman = true;
	
	private int bonus= Game.BONUS_NO_BONUS;
	private int high_score=0;
	private int time_limit; // 0 - no limit, 30 sec, 60 sec
	
	public Player(final int index)
	{
		this(index, "Player " + (index +1));
		
	}
	
	public Player(final int index, final String n)
	{
		if(n== null) name = "Player " + (index +1);
		else name = n;
		total_score =0;
		listScores = new LinkedList();
		currentTurnIndex =0;
		Arrays.fill(rollScores, 0);
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(final String val)
	{
		name = val;
	}
	
	public int getTotalScore()
	{
		return total_score;
	}
	
	public void addToTotalScore(final int val)
	{
		total_score +=val;
	}
	
	public void setTurnScore(final int val)
	{
		if(listScores.size() >= currentTurnIndex + 1)
		{
			Integer v = (Integer)listScores.get(currentTurnIndex);
			total_score -= v.intValue();
			listScores.remove(currentTurnIndex);
			listScores.add(currentTurnIndex, new Integer(val));
		}
		else
		{
			listScores.add(new Integer(val));
		}
		total_score +=val;
	}
	
	public int[] getLastScores(final int num)
	{
		if(listScores == null) return null;
		
		int sz = listScores.size();
		if(sz >=num)
		{
			int[] res = new int[num];
			Integer val;
			for(int i = sz-num; i < sz; i++ ){
				val = (Integer)listScores.get(i);
				if(val != null)
				{
					res[i] = val.intValue();
				}
				else
				{
					res[i] = 0;
				}				
				
			}
			return res;
		}
		else //sz < num
		{
			int[] res = new int[sz];
			Integer val;
			for(int i=0; i < sz; i++)
			{
				val = (Integer)listScores.get(i);
				if(val != null)
				{
					res[i] = val.intValue();
				}
				else
				{
					res[i] = 0;
				}				
			}
			return res;
			
		}
	}
	
	public int getTotalTurns()
	{
		return (listScores!=null)? listScores.size() :0;
	}
	
	public void addTurnAndScore(final int val)
	{
		listScores.add(new Integer(val));
		currentTurnIndex = listScores.size() -1;
		total_score +=val;
	}
	
	public void setTotalScore(final int val)
	{
		total_score = val;
	}
	
	public int getTurnScore(final int t)
	{
		if(t <0 || t >= listScores.size()) return 0;
		
		Integer val = (Integer)listScores.get(t);
		return val.intValue();
	}
	
	public int getCurTurnScore()
	{
		if(currentTurnIndex <0 || currentTurnIndex >= listScores.size()) return 0;
		Integer val = (Integer)listScores.get(currentTurnIndex);
		return val.intValue();
	}
	
	
	public void setRollScore(final int val)
	{
		rollScores[curRoll] = val;
	}
	
	public int getCurRollScore()
	{
		return rollScores[curRoll];
	}
	
	public boolean incrementCurRollNum()
	{
		if(curRoll < MAX_ROLLS)
		{
			curRoll++;
			return true;
		}
		return false;
	}
	
	public boolean isLastRoll()
	{
		return (curRoll==(MAX_ROLLS-1))? true : false;
	}
	
	public int getCurRoll()
	{
		return curRoll;
	}
	
	public void setCurRoll(final int val)
	{
		curRoll = val;
	}
	
	public void setCurCheckScore(final int val)
	{
		curCheckedScore = val;
	}
	
	public int getCurCheckScore()
	{
		return curCheckedScore;
	}
	
	public void completeTurn()
	{
		int score =0;
		for(int i=0; i < MAX_ROLLS; i++)
		{
			score += rollScores[i];
		}
		setTurnScore(score);
		currentTurnIndex++;
		curRoll =0;
		Arrays.fill(rollScores, 0);
		
	}
	
	public boolean isLastTurn()
	{
		return (currentTurnIndex >= MAX_TURNS)? true : false;
	}
	
	public void farkleTheTurn()
	{
		setTurnScore(0);
		currentTurnIndex++;
		curRoll =0;
		Arrays.fill(rollScores, 0);
		
	}
	
	public void setHuiman(final boolean val)
	{
		isHuman = val;
	}
	
	public boolean getHuman()
	{
		return isHuman;
	}
	
	public void save(ObjectOutputStream oos) throws IOException
	{
		oos.writeObject(name);
		oos.writeInt(total_score);
		//write list of turn scores
		final int sz = listScores.size();
		oos.writeInt(sz);
		Integer val;
		for(int i=0; i < sz; i++)
		{
			val = (Integer)listScores.get(i);
			if(val != null)
			{
				oos.writeInt(val.intValue());
			}
			else
			{
				oos.writeInt(0);
			}				
		}
		oos.writeInt(currentTurnIndex);
		oos.writeInt(curRoll);
		int cnt = rollScores.length;
		for(int i =0; i < cnt; i++)
		{
			oos.writeInt(rollScores[i]);
		}
		
		oos.writeInt(curCheckedScore);
		oos.writeBoolean(isHuman);
		oos.writeInt(bonus);
		oos.writeInt(high_score);
		oos.writeInt(time_limit);
		
	}
	
	public void read(ObjectInputStream ois) throws IOException, ClassNotFoundException
	{
		name = (String)ois.readObject();
		total_score = ois.readInt();
		int sz = ois.readInt();
		if(listScores == null)
		{
			listScores = new LinkedList();
		}
		else
		{
			listScores.clear();
		}
		
		int val;
		for(int i=0; i < sz; i++)
		{
			val = ois.readInt();
			listScores.add(new Integer(val));
		}
		currentTurnIndex = ois.readInt();
		curRoll = ois.readInt();
		for(int i=0; i < MAX_ROLLS; i++)
		{
			rollScores[i] = ois.readInt();
		}
		curCheckedScore = ois.readInt();
		isHuman = ois.readBoolean();
		bonus = ois.readInt();
		high_score = ois.readInt();
		time_limit = ois.readInt();
		
	}
	
	/////////// ------------- Bonuses / H Scores ----------------//////////////////
	public void setBonus(final int b)
	{
		bonus = b;
	}
	
	public int getBonus()
	{
		return bonus;
	}
	
	public void setHighScore(final int val)
	{
		high_score = val;
	}
	
	public int getHighScore()
	{
		return high_score;
	}
	
	public void setTurnTimeLimit(final int val)
	{
		time_limit = val;
	}
	
	public int getTurnTimeLimit()
	{
		return time_limit;
	}

}
