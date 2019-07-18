package com.olmatech.kindle.farkle;

import java.util.Arrays;
import java.util.LinkedList;

//winning scores
public class Scores {
	
	//scores in descending order
	public final static ScoreCombo[] combos = new ScoreCombo[]{new ScoreCombo(new int[]{6,0,0,0,0,0},4000),
			new ScoreCombo(new int[]{5,0,0,0,0,0},3000),
			new ScoreCombo(new int[]{0,0,0,0,0,6},2400),
			new ScoreCombo(new int[]{4,0,0,0,0,0},2000),
			new ScoreCombo(new int[]{0,0,0,0,6,0},2000),
			new ScoreCombo(new int[]{0,0,0,0,0,5},1800),
			new ScoreCombo(new int[]{0,0,0,6,0,0},1600),
			new ScoreCombo(new int[]{0,0,0,0,5,0},1500),
			new ScoreCombo(new int[]{1,1,1,1,1,1},1500),
			new ScoreCombo(new int[]{2,2,2,0,0,0},1250),
			new ScoreCombo(new int[]{2,2,0,2,0,0},1250),
			new ScoreCombo(new int[]{2,2,0,0,2,0},1250),
			new ScoreCombo(new int[]{2,2,0,0,0,2},1250),
			new ScoreCombo(new int[]{2,0,2,2,0,0},1250),
			new ScoreCombo(new int[]{2,0,2,0,2,0},1250),
			new ScoreCombo(new int[]{2,0,2,0,0,2},1250),
			new ScoreCombo(new int[]{2,0,0,2,2,0},1250),
			new ScoreCombo(new int[]{2,0,0,2,0,2},1250),
			new ScoreCombo(new int[]{2,0,0,0,2,2},1250),
			new ScoreCombo(new int[]{0,2,2,2,0,0},1250),
			new ScoreCombo(new int[]{0,2,2,0,2,0},1250),
			new ScoreCombo(new int[]{0,2,2,0,0,2},1250),
			new ScoreCombo(new int[]{0,2,0,2,2,0},1250),
			new ScoreCombo(new int[]{0,2,0,2,0,2},1250),
			new ScoreCombo(new int[]{0,2,0,0,2,2},1250),
			new ScoreCombo(new int[]{0,0,2,2,2,0},1250),
			new ScoreCombo(new int[]{0,0,2,2,0,2},1250),
			new ScoreCombo(new int[]{0,0,2,0,1,1},1250),
			new ScoreCombo(new int[]{0,0,0,1,1,1},1250),
			new ScoreCombo(new int[]{0,0,6,0,0,0},1200),
			new ScoreCombo(new int[]{0,0,0,5,0,0},1200),
			new ScoreCombo(new int[]{0,0,0,0,0,4},1200),
			new ScoreCombo(new int[]{3,0,0,0,0,0},1000),
			new ScoreCombo(new int[]{0,0,0,0,4,0},1000),
			new ScoreCombo(new int[]{0,0,5,0,0,0},900),
			new ScoreCombo(new int[]{0,6,0,0,0,0},800),
			new ScoreCombo(new int[]{0,0,0,4,0,0},800),
			new ScoreCombo(new int[]{0,5,0,0,0,0},600),
			new ScoreCombo(new int[]{0,0,4,0,0,0},600),
			new ScoreCombo(new int[]{0,0,0,0,0,3},600),
			new ScoreCombo(new int[]{0,0,0,0,3,0},500),
			new ScoreCombo(new int[]{0,4,0,0,0,0},400),
			new ScoreCombo(new int[]{0,0,0,3,0,0},400),
			new ScoreCombo(new int[]{0,0,3,0,0,0},300),
			new ScoreCombo(new int[]{0,3,0,0,0,0},200),
			new ScoreCombo(new int[]{1,0,0,0,0,0},100),
			new ScoreCombo(new int[]{0,0,0,0,1,0},50)};

	//order in Desc score
	private final static int[] scoreOrder = new int[]{}; //TODO
	
	private static int[] scoresToDraw = new int[]{1,2,3,7,11,15,19,23};
	
	public final static int[][] scoreDice = new int[][]{new int[]{45,46}, //1 - ind 0
		new int[]{28,32,39,40,42,43,44}, //3 - ind d 1
		new int[]{3,27,31,33,36,38,41}, //4 - 2
		new int[]{1,5,7,30,34,37},  //5 - 3
		new int[]{0,2,4,6,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,29,35}}; //6  - 4
	
	public final static int ONE = 0;
	public final static int THREE = 1;
	public final static int FOUR = 2;
	public final static int FIVE = 3;
	public final static int SIX = 4;	
	
	
	public static ScoreCombo[] getScoresToDraw()
	{
//		final int cnt = scoresToDraw.length;
//		ScoreCombo[] combo = new ScoreCombo[cnt];
//		for(int i =0; i < cnt; i++)
//		{
//			combo[i] = new ScoreCombo(combos[scoresToDraw[i]].getDice(), combos[scoresToDraw[i]].getScore());
//		}
//		return combo;
		
		return combos;
	}
	
	//arr of 6 - how many dice of each num
	//diceArr - arr of 6 ints with vals = dice nomination or 0
	public static ScoreResult checkForScores(final int[] diceArrIn)
	{
		LinkedList list = getListOfAllResults(diceArrIn);
		if(list == null) return null;
		
		int cnt2 = list.size();
		if(cnt2 == 0) return null;
		
//		Log.d("Dices in array: " + diceArrIn[0] + " - " +diceArrIn[1] + " - " +diceArrIn[2] + " - " +diceArrIn[3] + " - " +diceArrIn[4] + " - " +diceArrIn[5]);
//		Log.d("Results=" + cnt2);
		//find the best ScoreResult in the list
		ScoreResult scoreRes = null, res;
		scoreRes = new ScoreResult();
		int scor=0, scor2;
		for(int i=0; i < cnt2; i++)
		{
			res = (ScoreResult)list.get(i);
			//Log.d(res.getCombosStr());
			
			scor2 = res.getTotalScore();
			if(scor2 > scor)
			{
				scor = scor2;
				scoreRes = res;
			}
		}
		
		list.clear();
		list = null;		
		return scoreRes; //TODO
	}
	
	public static boolean checkHaveScore(final int[] diceArrIn)
	{
		return checkIfHaveScored(diceArrIn);
	}
	
	
	private static int checkScore(final int[] diceArr, final int ind, final int diceCnt)
	{		
//		Log.d("checkScore in " + diceArr[0] + diceArr[1] + diceArr[2]+diceArr[3]+diceArr[4]+diceArr[5]);
//		Log.d("Combo :  " +combos[ind].getDiceAt(0) + combos[ind].getDiceAt(1) + combos[ind].getDiceAt(2)+combos[ind].getDiceAt(3)+combos[ind].getDiceAt(4)+combos[ind].getDiceAt(5));
		for(int i =0; i < Game.TOTAL_DICE; i++)
		{
			if(combos[ind].getDiceAt(i) > diceArr[i])
			{
				return -1;
			}
		}
		
		if(combos[ind].getDiceCount() == diceCnt)
		{
			return 1;
		}
		
		//check how many
		int[] maxNum = new int[Game.TOTAL_DICE];
		int num, resNum=Game.TOTAL_DICE;
		for(int i =0; i < Game.TOTAL_DICE; i++)
		{
			num = combos[ind].getDiceAt(i);
			if(num >0)
			{
				maxNum[i] = diceArr[i] / num ;
				if(maxNum[i] != 0 && maxNum[i] < resNum)
				{
					resNum = maxNum[i];
				}
			}
			else
			{
				maxNum[i] =0;
			}			
		}		
		//Log.d("REs=" + resNum);
		return resNum;
	}
	
	private static LinkedList getListOfAllResults(final int[] diceArrIn)
	{
		if(diceArrIn.length != Game.TOTAL_DICE) return null;
		LinkedList list = new LinkedList();
		//LinkedList listTot = new LinkedList();
		//count total dice
		int cntTot=0;
		int[] diceArr = new int[Game.TOTAL_DICE];
		Arrays.fill(diceArr, 0);
		for(int i =0; i < Game.TOTAL_DICE; i++)
		{
			if(diceArrIn[i] >0) 
			{
				diceArr[diceArrIn[i]-1]++;
				cntTot++;
			}
		}
		
		final int cnt = cntTot;/////////////
//		Log.d("diceArr " + diceArr[0] + diceArr[1] + diceArr[2]+diceArr[3]+diceArr[4]+diceArr[5]);		
//		Log.d("Dice count=" + cnt);
		
		ScoreResult res;
		//start checking
		//6= 6 = 5+1 = 4+1+1 = 3+3 = 3+1+1+1 = 1+1+1+1+1+1
		//5 = 5 = 4+1 = 3+1+1 = 1+1+1+1+1
		//4 = 4 = 3+1 = 1+1+1+1
		//3 = 3 = 1+1+1
		//2 = 1+1
		//1=1
		
		int ln, cnt2;
		int ind;
		int[] remDice = new int[Game.TOTAL_DICE];
		if(cnt == 1 || cnt == 2)
		{
			ln = scoreDice[ONE].length;
			res = new ScoreResult();
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[ONE][j]; 
				cnt2 = checkScore(diceArr, ind, cnt);		
				for(int k=0; k < cnt2; k++)
				{
					res.addCombo(ind, combos[ind].getScore());
				}
			}
			
			if(res.hasCombos())
			{
				list.add(res);
				//listTot.add(new Integer(res.getTotalScore()));
			}
		}
		else if(cnt == 3)
		{
			//check combos with 3
			ln = scoreDice[THREE].length;
			res = new ScoreResult();
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[THREE][j]; 
				cnt2 = checkScore(diceArr, ind, cnt);
				if(cnt2 >0)  //there can be only 1
				{					
					res.addCombo(ind, combos[ind].getScore());						
				}				
			}
			if(res.hasCombos())
			{
				list.add(res);
				//listTot.add(new Integer(res.getTotalScore()));
			}
			//now check 1s - we have only 2 - so we are OK
			ln = scoreDice[ONE].length;
			res = new ScoreResult();
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[ONE][j]; 
				cnt2 = checkScore(diceArr, ind, cnt);
				for(int k=0; k < cnt2; k++)
				{
					res.addCombo(ind, combos[ind].getScore());
				}
			}
			if(res.hasCombos())
			{
				list.add(res);
				//listTot.add(new Integer(res.getTotalScore()));
			}
		}
		else if(cnt == 4)
		{
			// check 4s
			ln = scoreDice[FOUR].length;
			res = new ScoreResult();
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[FOUR][j]; 
				cnt2 = checkScore(diceArr, ind, cnt);
				if(cnt2 > 0)//there can be only 1
				{					
					res.addCombo(ind, combos[ind].getScore());
					
				}
			}
			if(res.hasCombos())
			{
				list.add(res);
				//listTot.add(new Integer(res.getTotalScore()));
			}
			// 3+1			
			ln = scoreDice[THREE].length;
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[THREE][j]; 
				cnt2 = checkScore(diceArr, ind, cnt);
				if(cnt2 > 0)//there can be only 1
				{
					res = new ScoreResult();
					res.addCombo(ind, combos[ind].getScore());
					
					//check 1s
					//we have only 1 spot, exclude dice already taken
					int diceCnt2 = cnt;
					for(int k =0; k < Game.TOTAL_DICE; k++)
					{
						if(combos[ind].getDiceAt(k) >0)
						{
							remDice[k] = diceArr[k]-1;
							diceCnt2--;
						}
						else 
						{
							remDice[k]=diceArr[k];
						}
					}
					
					int ln2 = scoreDice[ONE].length;
					for(int k =0; k < ln2; k++)
					{
						int ind2 = scoreDice[ONE][k];
						int cnt3 = checkScore(remDice, ind2, diceCnt2);
						if(cnt3 >0 )  //only 1 can be 
						{
							res.addCombo(ind2, combos[ind].getScore());
							break; //space taken
						}
					} //for 2
					
					list.add(res);
					//listTot.add(new Integer(res.getTotalScore()));
					
				}
			}
			//1+1+1+1
			//we have only 2 1s - we are safe
			res = new ScoreResult();
			ln = scoreDice[ONE].length;
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[ONE][j]; 
				cnt2 = checkScore(diceArr, ind, cnt);
				for(int k=0; k < cnt2; k++)
				{
					res.addCombo(ind, combos[ind].getScore());
				}
			}
			if(res.hasCombos())
			{
				list.add(res);
				//listTot.add(new Integer(res.getTotalScore()));
			}
		}
		else if(cnt == 5)
		{
			//5 = 5 = 4+1 = 3+1+1 = 1+1+1+1+1
			//5s
			ln = scoreDice[FIVE].length;
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[FIVE][j]; 
				if(checkScore(diceArr, ind, cnt) > 0)  //only 1
				{
					res = new ScoreResult();
					res.addCombo(ind, combos[ind].getScore());
					list.add(res);
					//listTot.add(new Integer(res.getTotalScore()));
				}
			}
			
			//4+1 - 2
			ln = scoreDice[FOUR].length;
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[FOUR][j]; 
				if(checkScore(diceArr, ind, cnt) > 0)
				{
					res = new ScoreResult();
					res.addCombo(ind, combos[ind].getScore());
					//check 1s - exclude  
					int diceCnt2 = cnt;
					for(int k =0; k < Game.TOTAL_DICE; k++)
					{
						if(combos[ind].getDiceAt(k) >0)
						{
							remDice[k] = diceArr[k]-1;
							diceCnt2--;
						}
						else remDice[k]=diceArr[k];
					}
					int ln2 = scoreDice[ONE].length;
					for(int k =0; k < ln2; k++)
					{
						int ind2 = scoreDice[ONE][k];
						if(checkScore(remDice, ind2, diceCnt2) > 0)
						{
							res.addCombo(ind2, combos[ind2].getScore());
							
						}
					}
					list.add(res);
					//listTot.add(new Integer(res.getTotalScore()));
				}
			} // 4+1
			
			// 3+1+1
			ln = scoreDice[THREE].length;
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[THREE][j]; 
				if(checkScore(diceArr, ind, cnt) > 0)  //only 1
				{
					res = new ScoreResult();
					res.addCombo(ind, combos[ind].getScore());
					//1s
					int diceCnt2 = cnt;
					for(int k =0; k < Game.TOTAL_DICE; k++)
					{
						if(combos[ind].getDiceAt(k) >0)
						{
							remDice[k] = diceArr[k]-1;
							diceCnt2--;
						}
						else remDice[k]=diceArr[k];
					}
					int ln2 = scoreDice[ONE].length;
					for(int k =0; k < ln2; k++)
					{
						int ind2 = scoreDice[ONE][k];
						cnt2 = checkScore(remDice, ind2, diceCnt2);
						for(int m=0; m < cnt2; m++)
						{					
							res.addCombo(ind2, combos[ind2].getScore());							
						}						
					}
					list.add(res);
					//listTot.add(new Integer(res.getTotalScore()));
				}
			}
			
			//1+1+1+1+1
			ln = scoreDice[ONE].length;
			res = new ScoreResult();
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[ONE][j];
				cnt2 = checkScore(diceArr, ind, cnt);
				for(int k=0; k < cnt2; k++)
				{
					res.addCombo(ind, combos[ind].getScore());
				}
			}
			if(res.hasCombos())
			{
				list.add(res);
				//listTot.add(new Integer(res.getTotalScore()));
			}			
		}
		else if(cnt == 6)
		{
			//6=6=5+1 = 4+1+1 = 3+3 = 3+1+1+1 = 1+1+1+1+1+1
			//6
			ln = scoreDice[SIX].length; //only 1 of each
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[SIX][j]; 
				if(checkScore(diceArr, ind, cnt) > 0)  //only 1
				{
					res = new ScoreResult();
					res.addCombo(ind, combos[ind].getScore());
					list.add(res);
					//Log.d("******  6 Score " +ind + " -- "+ combos[ind].toString());
					break; // each is unique
				}
			}
			
			//5+1
			ln = scoreDice[FIVE].length;
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[FIVE][j]; 
				if(checkScore(diceArr, ind, cnt) > 0)  //only 1
				{
					res = new ScoreResult();
					res.addCombo(ind, combos[ind].getScore());					
					//check 1s - exclude  
					int diceCnt2 = cnt;
					for(int k =0; k < Game.TOTAL_DICE; k++)
					{
						if(combos[ind].getDiceAt(k) >0)
						{
							remDice[k] = diceArr[k]-1;
							diceCnt2--;
						}
						else remDice[k]=diceArr[k];
					}
					int ln2 = scoreDice[ONE].length;
					for(int k =0; k < ln2; k++)
					{
						int ind2 = scoreDice[ONE][k];
						if(checkScore(remDice, ind2, diceCnt2) > 0)
						{
							res.addCombo(ind2, combos[ind2].getScore());							
							break;
							
						}
					}
					list.add(res);
					//listTot.add(new Integer(res.getTotalScore()));
				}
			}
			//4+1+1 
			ln = scoreDice[FOUR].length;
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[FOUR][j]; 
				if(checkScore(diceArr, ind, cnt) > 0)  //only 1
				{
					res = new ScoreResult();
					res.addCombo(ind, combos[ind].getScore());
					
					//check 1s - exclude  
					int diceCnt2 = cnt;
					for(int k =0; k < Game.TOTAL_DICE; k++)
					{
						if(combos[ind].getDiceAt(k) >0)
						{
							remDice[k] = diceArr[k]-1;
							diceCnt2--;
						}
						else remDice[k]=diceArr[k];
					}
					int ln2 = scoreDice[ONE].length;
					for(int k =0; k < ln2; k++)
					{
						int ind2 = scoreDice[ONE][k];
						
						if(checkScore(remDice, ind2, diceCnt2) > 0)
						{
							for(int p=0; p < diceCnt2; p++)
							{
								res.addCombo(ind2, combos[ind2].getScore());
								
							}						
						}
					}
					list.add(res);
					//listTot.add(new Integer(res.getTotalScore()));
				}
			}
			//3+3, 3+1+1+1
			ln = scoreDice[THREE].length;
			res = new ScoreResult();
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[THREE][j];
				cnt2 = checkScore(diceArr, ind, cnt); //can be 1 or 2
				if(cnt > 0) 
				{
					//can be 1 more
					res.addCombo(ind, combos[ind].getScore());
					
					int diceCnt2 = cnt;
					for(int k =0; k < Game.TOTAL_DICE; k++)
					{
						if(combos[ind].getDiceAt(k) >0)
						{
							remDice[k] = diceArr[k]-1;
							diceCnt2--;
						}
						else remDice[k]=diceArr[k];
					}
					int ind2 = -1;
					
					if(cnt == 1)
					{
						//check other 3
						for(int k=0; k < ln; k++)
						{
							if(k ==j) continue;
							ind2 = scoreDice[THREE][k];
							if(checkScore(remDice, ind2, diceCnt2) > 0) //another 3 - can be only 1 of them
							{
								res.addCombo(ind2, combos[ind2].getScore());
								
								break;
							}
						} //other
						if(res.getCombosCount() > 1 && ind2 >=0)
						{
							list.add(res);
							//start new res
							res = new ScoreResult();
							res.addCombo(ind, combos[ind].getScore());	
							
						}
					}
					
					//check 1s
					int ln2 = scoreDice[ONE].length;
					for(int p=0; p < ln2; p++)
					{
						int ind3 = scoreDice[ONE][p];
						int cnt3 = checkScore(remDice, ind3, diceCnt2);
						for(int z=0; z < cnt3; z++)
						{
							res.addCombo(ind3, combos[ind3].getScore());
							
						}
					}
					list.add(res);
					if(cnt >1)
					{
						//add 2 threes
						res = new ScoreResult();
						res.addCombo(ind, combos[ind].getScore());
						res.addCombo(ind, combos[ind].getScore());						
						list.add(res);
					}
					
				}  // 1 or 2 three
				
			}
			
			//1+1+1+1+1+1
			ln = scoreDice[ONE].length;
			res = new ScoreResult();
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[SIX][j]; 
				cnt2 = checkScore(diceArr, ind, cnt);
				for(int k=0; k < cnt2; k++)
				{
					res.addCombo(ind, combos[ind].getScore());
				}
			}
			if(res.hasCombos())
			{
				list.add(res);
			}
			
		}
		
		return list;
		
	}
	
	
	private static boolean checkIfHaveScored(final int[] diceArrIn)
	{
		if(diceArrIn.length != Game.TOTAL_DICE) return false;		
		//LinkedList listTot = new LinkedList();
		//count total dice
		int cntTot=0;
		int[] diceArr = new int[Game.TOTAL_DICE];
		Arrays.fill(diceArr, 0);
		for(int i =0; i < Game.TOTAL_DICE; i++)
		{
			if(diceArrIn[i] >0) 
			{
				diceArr[diceArrIn[i]-1]++;
				cntTot++;
			}
		}
		
		final int cnt = cntTot;/////////////
		Log.d("checkIfHaveScored diceArr " + diceArr[0] + diceArr[1] + diceArr[2]+diceArr[3]+diceArr[4]+diceArr[5]);		
		Log.d("checkIfHaveScored Dice count=" + cnt);
		
		
		//start checking
		//6= 6 = 5+1 = 4+1+1 = 3+3 = 3+1+1+1 = 1+1+1+1+1+1
		//5 = 5 = 4+1 = 3+1+1 = 1+1+1+1+1
		//4 = 4 = 3+1 = 1+1+1+1
		//3 = 3 = 1+1+1
		//2 = 1+1
		//1=1
		
		int ln;
		int ind;
		if(cnt == 1 || cnt == 2)
		{
			ln = scoreDice[ONE].length;			
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[ONE][j]; 
				if(checkScore(diceArr, ind, cnt)>0) return true;	
				
			}			
			
		}
		else if(cnt == 3)
		{
			//check combos with 3
			ln = scoreDice[THREE].length;			
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[THREE][j]; 
				if(checkScore(diceArr, ind, cnt)>0) return true;							
			}
			
			//now check 1s - we have only 2 - so we are OK
			ln = scoreDice[ONE].length;			
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[ONE][j]; 
				if(checkScore(diceArr, ind, cnt)>0) return true;	
				
			}			
		}
		else if(cnt == 4)
		{
			// check 4s
			ln = scoreDice[FOUR].length;			
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[FOUR][j]; 
				if(checkScore(diceArr, ind, cnt)>0) return true;					
			}			
			// 3+1			
			ln = scoreDice[THREE].length;
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[THREE][j]; 
				if(checkScore(diceArr, ind, cnt) >0) return true;				
			}
			//1+1+1+1
			//we have only 2 1s - we are safe
			
			ln = scoreDice[ONE].length;
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[ONE][j]; 
				if(checkScore(diceArr, ind, cnt)>0)
				{
					return true;
				}			
			}			
		}
		else if(cnt == 5)
		{
			//5 = 5 = 4+1 = 3+1+1 = 1+1+1+1+1
			//5s
			ln = scoreDice[FIVE].length;
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[FIVE][j]; 
				if(checkScore(diceArr, ind, cnt) > 0)  //only 1
				{
					return true;
				}
			}
			
			//4+1 - 2
			ln = scoreDice[FOUR].length;
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[FOUR][j]; 
				if(checkScore(diceArr, ind, cnt) > 0)
				{
					return true;				
				}
			} // 4+1
			
			// 3+1+1
			ln = scoreDice[THREE].length;
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[THREE][j]; 
				if(checkScore(diceArr, ind, cnt) > 0)  //only 1
				{
					return true;					
				}
			}
			
			//1+1+1+1+1
			ln = scoreDice[ONE].length;
			
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[ONE][j];
				if(checkScore(diceArr, ind, cnt)>0) return true;				
			}
				
		}
		else if(cnt == 6)
		{
			//6=6=5+1 = 4+1+1 = 3+3 = 3+1+1+1 = 1+1+1+1+1+1
			//6
			ln = scoreDice[SIX].length; //only 1 of each
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[SIX][j]; 
				if(checkScore(diceArr, ind, cnt) > 0)  //only 1
				{
					return true;
				}
			}
			
			//5+1
			ln = scoreDice[FIVE].length;
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[FIVE][j]; 
				if(checkScore(diceArr, ind, cnt) > 0)  //only 1
				{
					return true;
				}
			}
			//4+1+1 
			ln = scoreDice[FOUR].length;
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[FOUR][j]; 
				if(checkScore(diceArr, ind, cnt) > 0)  //only 1
				{
					return true;
				}
			}
			//3+3, 3+1+1+1
			ln = scoreDice[THREE].length;
			
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[THREE][j];
				if(checkScore(diceArr, ind, cnt)>0) return true;				
			}
			
			//1+1+1+1+1+1
			ln = scoreDice[ONE].length;			
			for(int j=0; j < ln; j++)
			{
				ind =scoreDice[ONE][j]; 
				if(checkScore(diceArr, ind, cnt)>0) return true;				
			}
			
			
		}
		
		return false;
		
	}

}
