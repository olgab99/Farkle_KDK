package com.olmatech.kindle.farkle;

import java.util.Arrays;

public class ScoreCombo
{
	
	private int[] dice = new int[Game.TOTAL_DICE];
	private int score;
	private int diceCount=0;
	
	public ScoreCombo(final int[] darr, final int s)
	{
		for(int i =0; i < Game.TOTAL_DICE; i++)
		{
			dice[i] = darr[i];
			diceCount +=dice[i];
		}
		score = s;
	}
	
		
	public void setScore(final int val)
	{
		score = val;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public int[] getDice()
	{
		return dice;
	}
	
	//ret array of 6 int with values for dice in combo
	public int[] getArrayOfSingleDice()
	{
		int[] res = new int[Game.TOTAL_DICE];
		Arrays.fill(res, 0);
		int ind=0, num=1;
		for(int i=0; i < Game.TOTAL_DICE; i++, num++)
		{
			for(int j=0; j < dice[i]; j++)
			{
				res[ind] = num;
				ind++;
			}
		}
		return res;
	}
	
	public int getDiceAt(final int ind)
	{
		//if(ind <0 || ind >= Controller.TOTAL_DICE) return -1;
		return dice[ind];		
	}
	
	public void copy(final ScoreCombo combo)
	{
		System.arraycopy(combo.getDice(), 0, dice, 0, Game.TOTAL_DICE);
		score = combo.getScore();
	}
	
	public int getDiceCount()
	{
		return diceCount;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("dice:");
		for(int i =0; i < dice.length; i++)
		{
			buf.append(dice[i]);
		}
		buf.append("score:");
		buf.append(score);
		return buf.toString();
		
	}
	
	
}