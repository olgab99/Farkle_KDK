package com.olmatech.kindle.farkle;

import java.util.LinkedList;

public class ScoreResult {
	
	private LinkedList combos;
	private int total=0;
	
	public ScoreResult()
	{
		
	}
	
	public void addCombo(final int ind, final int score)
	{
		if(combos == null) combos = new LinkedList();
		combos.add(new Integer(ind));
		total += score;
	}
	
	public int getCombosCount()
	{
		return (combos != null)? combos.size() : 0;
	}
	
	public LinkedList getCombos()
	{
		return combos;
	}
	
	public int[] getCombosScoreIndexes()
	{
		if(combos == null) return null;
		final int cnt = combos.size();
		if(cnt == 0) return null;
		int[] res = new int[cnt];
		Integer val;
		for(int i=0; i < cnt; i++)
		{
			val = (Integer)combos.get(i);
			res[i] = val.intValue();
		}
		return res;
	}
	
	public int getTotalScore()
	{
		return total;
	}
	
	public boolean hasCombos()
	{
		return (total >0)? true : false;
	}
	
	//test
	public String getCombosStr()
	{
		if(combos == null) return null;
		StringBuffer buf = new StringBuffer();
		final int cnt = combos.size();
		if(cnt ==0) return "NO  ";
		int ind;
		Integer val;
		ScoreCombo c;
		for(int i=0; i < cnt; i++)
		{
			val = (Integer)combos.get(i);
			ind = val.intValue();
			c = Scores.combos[ind];
			buf.append("Combo #" + ind + " Dice: ");
			for(int j=0; j < 6; j++)
			{
				buf.append(c.getDiceAt(j));
				buf.append(" -- ");
			}
			buf.append("  ||| ");
		}
		buf.append(" <<>>");
		return buf.toString();
	}
	

}
