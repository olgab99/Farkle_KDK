package com.olmatech.kindle.farkle;

import java.util.Arrays;
import java.util.LinkedList;

public class Result {
	
	/*
	 * Combinations - must to be in order from 0 to 7
	 */
	public final static int ONE=0;
	public final static int FIVE =1;
	public final static int ONE3 =2;
	public final static int TWO3 =3;
	public final static int THREE3 =4;
	public final static int FOUR3= 5;
	public final static int FIVE3 = 6;
	public final static int SIX3 = 7;	
	public final static int TOTAL = 8;
	
	public final static int TOTAL_DICE = 6;
	
	public static int getScore(final int num)
	{
		switch(num)
		{
		case ONE: return 100;
		case FIVE: return 50;
		case ONE3: return 1000;
		case TWO3: return 200;
		case THREE3: return 300;
		case FOUR3: return 400;
		case FIVE3: return 500;
		case SIX3: return 600;
		default: return 0;
		
		}
	}
	
	/*
	 * rolls - dices in current roll
	 * vals - combinations selected
	 * dices - each nomination that used
	 * return - number of dices in roll used or -1 if invalid selection, -2 - no score
	 */
	public static int checkResult(final int[] rolls, final int[] vals, int[] dices)
	{
		Arrays.fill(dices, 0);
		
		int diceUsed =0;
		for(int i =0; i < vals.length; i++)
		{
			switch(vals[i])
			{
			case ONE:
				dices[0] +=1;
				diceUsed +=1;
				break;
			case FIVE: 
				dices[4] +=1;
				diceUsed +=1;
				break;
			case ONE3: 
				dices[0] +=3;
				diceUsed +=3;
				break;
			case TWO3: 
				dices[1] +=3;
				diceUsed +=3;
				break;
			case THREE3: 
				dices[2] +=3;
				diceUsed +=3;
				break;
			case FOUR3: 
				dices[3] += 3;
				diceUsed +=3;
				break;
			case FIVE3: 
				dices[4] +=3;
				diceUsed +=3;
				break;
			case SIX3: 
				dices[5] +=3;
				diceUsed +=3;
				break;
			default: break;
			}
			
			
		}
		//RBLogger.logDebug("diceUsed=" + diceUsed);		
		
		return checkInRoll(rolls, dices, diceUsed, TOTAL_DICE);		

	}
	
	private static int checkInRoll(int[] rolls, int[] dices, final int diceUsed, final int total)
	{
		if(diceUsed == 0)
		{			
			//no score
			return -2;
		}
		
		if(diceUsed > total)
		{
			return -1; //too many dices
		}
		//do we have them in roll?
		boolean ok = true;
		final int cntRolls = rolls.length;
		int num =1;
		int cnt;
		for(int i =0; i < total; i++, num++)
		{
			cnt =0;
			//how many num?
			for(int j =0; j < cntRolls; j++)
			{
				if(rolls[j] == num)
				{
					cnt +=1;
				}
			}//rolls
			if(dices[i] > cnt) //we have more then in roll
			{
				ok = false;
				break; // too many - we choose more then rolled
			}			
		}
		
		if(!ok) return -1;
		
				
		//check num of dice used
		return cntRolls - diceUsed;
	}
	
	/*
	 * Return numbers of occurences corresponding to ONE, FIVE, ....
	 */
	public static int[] getResultInArray(final int[] roll)
	{
		int[] result = new int[TOTAL];
		for(int i=0; i <TOTAL; i++)
		{
			result[i] =0;
		}
		
		result[ONE] += checkEach1(roll);	
		
		result[FIVE] += checkEach5(roll);
		
		
		RetResult r = checkThree1(roll);
		result[ONE3] +=  r.getValue();
		
		
		r = checkThree2(roll);
		result[TWO3] +=  r.getValue();
		
		
		r = checkThree3(roll);
		result[THREE3] += r.getValue();
		
		r = checkThree4(roll);
		result[FOUR3] += r.getValue();
		
		r = checkThree5(roll);
		result[FIVE3] += r.getValue();
		
		r = checkThree6(roll);
		result[SIX3] += r.getValue();
		
		
		return result;
	}
	
	/*
	 * 
	 * roll - numbers on dices between 1 and 6 and their values
	 * 
	 * returns list of integers corrsp. to Result dice values
	 */
	public static LinkedList getResult(final int[] roll)
	{
		LinkedList list = new LinkedList();		
		
		int val = checkEach1(roll);
		RetResult r;
		Integer v ;
		if(val >0)
		{
			v = new Integer(ONE);
			for(int i =0; i < val; i++)
			{
				list.add(v);
			}
		}	
		
		val = checkEach5(roll);
		if(val >0)
		{
			v = new Integer(FIVE);
			for(int i =0; i < val; i++)
			{
				list.add(v);
			}
		}	
		
		r = checkThree1(roll);
		val = r.getValue();
		if(val >0)
		{
			v = new Integer(ONE3);
			for(int i =0; i < val; i++)
			{
				list.add(v);
			}
		}	
		
		r = checkThree2(roll);
		val = r.getValue();
		if(val >0)
		{
			v = new Integer(TWO3);
			for(int i =0; i < val; i++)
			{
				list.add(v);
			}
		}	
		r = checkThree3(roll);
		val = r.getValue();
		if(val >0)
		{
			v = new Integer(THREE3);
			for(int i =0; i < val; i++)
			{
				list.add(v);
			}
		}	
		r = checkThree4(roll);
		val = r.getValue();
		if(val >0)
		{
			v = new Integer(FOUR3);
			for(int i =0; i < val; i++)
			{
				list.add(v);
			}
		}	
		r = checkThree5(roll);
		val = r.getValue();
		if(val >0)
		{
			v = new Integer(FIVE3);
			for(int i =0; i < val; i++)
			{
				list.add(v);
			}
		}	
		r = checkThree6(roll);
		val = r.getValue();
		if(val >0)
		{
			v = new Integer(SIX3);
			for(int i =0; i < val; i++)
			{
				list.add(v);
			}
		}	
		
		return list;
		
	}
	
	/*
	 * Gets list of best scores for Kindle
	 * dices - array of dices each nomination used
	 * score - score to pass to caller
	 */
	public static  LinkedList getResultForKindle(final int[] roll, int[] score)
	{
		if(roll == null || roll.length==0) return null;
		LinkedList list = new LinkedList();	
		
		Integer v ;		
		final int ln = roll.length;
		//int score = 0;
		int s = 0;
		int diceUsed =0;
		Game game = Game.getGame();
		game.resetKindleDice();
		
		RetResult r;
		r = checkThree1(roll);
		int val = r.getValue();
		int rest = r.getRest();
				
		if(val > 0)
		{
			v = new Integer(ONE3);
			s = getScore(ONE3);
			for(int i =0; i < val; i++)
			{
				list.add(v);
				score[0] += s;
				diceUsed +=3;
				game.incrementKindleDice(0, 3);				
				
			}
			//see if we still have ones
			if(val == 1)
			{				
				if(rest >0)
				{
					s = getScore(ONE);
					v = new Integer(ONE);
					for(int j =0; j < rest; j++)
					{
						list.add(v);							
					}
					score[0] += s*rest;
					diceUsed +=rest;
					game.incrementKindleDice(0, rest);	
					
				}				
			}//val == 1			
		}//have 3 x1
		else if(rest >0)
		{
			s = getScore(ONE);
			v = new Integer(ONE);
			for(int j =0; j < rest; j++)
			{
				list.add(v);
		
			}
			score[0] += s*rest;
			diceUsed +=rest;
			game.incrementKindleDice(0, rest);
			
		}
		
		if(diceUsed >= ln) return list;
		//5
		r = checkThree5(roll);
		val = r.getValue();
		rest = r.getRest();
		if(val >0)
		{
			v = new Integer(FIVE3);
			s = getScore(FIVE3);
			for(int i =0; i < val; i++)
			{
				list.add(v);
				score[0] += s;
				diceUsed +=3;
				game.incrementKindleDice(4, 3);				
			}
			if(val == 1)
			{
				if(rest > 0)
				{
					s = getScore(FIVE);
					v = new Integer(FIVE);
					for(int j =0; j < rest; j++)
					{
						list.add(v);						
					}
					score[0] += s*rest;
					diceUsed +=rest;
					game.incrementKindleDice(4, rest);					
					
				}
				
			}
		}//five
		else if(rest >0) //indiv 5s			
		{
			s = getScore(FIVE);
			v = new Integer(FIVE);
			for(int j =0; j < rest; j++)
			{
				list.add(v);						
			}
			score[0] += s*rest;
			diceUsed +=rest;
			game.incrementKindleDice(4, rest);			
		}
		
		if(diceUsed >= ln) return list;
		
		r = checkThree2(roll);
		val = r.getValue();
		if(val >0)
		{
			v = new Integer(TWO3);
			s = getScore(TWO3);
			for(int i =0; i < val; i++)
			{
				list.add(v);
				score[0] += s;
				diceUsed +=3;	
				game.incrementKindleDice(1, 3);				
			}
		}
		
		if(diceUsed >= ln) return list;
		
		r = checkThree3(roll);
		val = r.getValue();
		if(val >0)
		{
			v = new Integer(THREE3);
			s = getScore(THREE3);
			for(int i =0; i < val; i++)
			{
				list.add(v);
				score[0] += s;
				diceUsed +=3;	
				game.incrementKindleDice(2, 3);				
			}
		}
		
		if(diceUsed >= ln) return list;
		
		r = checkThree4(roll);
		val = r.getValue();
		if(val >0)
		{
			v = new Integer(FOUR3);
			s = getScore(FOUR3);
			for(int i =0; i < val; i++)
			{
				list.add(v);
				score[0] += s;
				diceUsed +=3;
				game.incrementKindleDice(3, 3);				
			}
		}
		
		if(diceUsed >= ln) return list;
		
		r = checkThree6(roll);
		val = r.getValue();
		if(val >0)
		{
			v = new Integer(SIX3);
			s = getScore(SIX3);
			for(int i =0; i < val; i++)
			{
				list.add(v);
				score[0] += s;
				diceUsed +=3;	
				game.incrementKindleDice(5, 3);				
			}
		}
		
		return list;
	}
	
	/*
	 * Gets list of best scores for Kindle
	 * dices - array of dices each nomination used
	 * score - score to pass to caller
	 */
	public static  boolean isPossibleScore(final int[] roll)
	{
		if(roll == null || roll.length==0) return false;
		Game game = Game.getGame();
		game.resetKindleDice();		
		
		RetResult r;
		r = checkThree1(roll);
		int val = r.getValue();
		int rest = r.getRest();
				
		if(val > 0 || rest >0)
		{
			return true;
		}//have 3 x1
		
		//5
		r = checkThree5(roll);
		val = r.getValue();
		rest = r.getRest();
		if(val > 0 || rest >0) return true;
		
		r = checkThree2(roll);
		val = r.getValue();
		if(val >0)
		{
			return true;
		}
		
		r = checkThree3(roll);
		val = r.getValue();
		if(val >0)
		{
			return true;
		}		
		r = checkThree4(roll);
		val = r.getValue();
		if(val >0)
		{
			return true;
		}
		r = checkThree6(roll);
		val = r.getValue();
		if(val >0)
		{
			return true;
		}
		
		return false;
	}
	
	private static int checkEach1(final int[] roll)
	{
		int res = 0;
		for(int i=0; i < roll.length; i++)
		{
			if(roll[i] == 1)
			{
				res +=1;
			}
		}
		return res;
	}
	
	private static int checkEach5(final int[] roll)
	{
		int res = 0;
		for(int i=0; i < roll.length; i++)
		{
			if(roll[i] == 5)
			{
				res +=1;
			}
		}
		return res;
	}
	
	private static RetResult checkThree1(final int[] roll)
	{
		return checkThree(roll, 1);
	}
	private static RetResult checkThree2(final int[] roll)
	{
		return checkThree(roll, 2);
	}
	private static RetResult checkThree3(final int[] roll)
	{
		return checkThree(roll, 3);
	}
	private static RetResult checkThree4(final int[] roll)
	{
		return checkThree(roll, 4);
	}
	private static RetResult checkThree5(final int[] roll)
	{
		return checkThree(roll, 5);
	}
	private static RetResult checkThree6(final int[] roll)
	{
		return checkThree(roll, 6);
	}
	
	private static RetResult checkThree(final int[] roll, final int num)
	{
		int res = 0;
		final int sz = roll.length;
		for(int i=0; i < sz; i++)
		{
			if(roll[i] == num)
			{
				res +=1;
			}
		}
		
		if(res == 6)
		{			
			return new RetResult(2, 0);
		}
		else if(res >= 3)
		{
			return new RetResult(1, res -3);
		}
		
		return new RetResult(0, res);
	}

}
