package com.olmatech.kindle.farkle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Options {
	
	private static Options refOptions;
	
	private long turnTimeOption= 0; //0 - no limit
	//bonuses
	//indexes in array
	public final static int BONUS_NONE=0;
	public final static int BONUS_TRIPLE=1;
	public final static int BONUS_REROLL =2;
	
	private int bonusesSet = BONUS_NONE;
	
	public final static int OPT_GAME_END_TURNS=0;
	public final static int OPT_GAME_END_SCORE =1;
	private int gameEndOption = OPT_GAME_END_TURNS;
	private int gameEndOptionValue = Player.MAX_TURNS;	
	
	private Options(){};
	
	public static Options getOptions()
	{
		if(refOptions == null) refOptions = new Options();
		return refOptions;
	}
	
	public void setGameEndOption(final int opt)
	{
		gameEndOption = opt;
	}
	
	public int getGameEndOption()
	{
		return gameEndOption;
	}
	
	public int getGameOptionVal()
	{
		return gameEndOptionValue;
	}
	
	public void setBonus(final int bonus)
	{
		bonusesSet = bonus;
	}
	
	public int getBonusesSet()
	{
		return bonusesSet;
	}
	
	public void setMaxTurnTime(final long val)
	{
		turnTimeOption = val;
	}
	
	public long getMaxTurnTimeOpt()
	{
		return turnTimeOption;
	}
	
	public void save(ObjectOutputStream oos) throws IOException
	{		
			oos.writeLong(turnTimeOption);
			
			//total bonuses
			oos.writeInt(bonusesSet);			
			oos.writeInt(gameEndOption);
			oos.writeInt(gameEndOptionValue);				
			
	}
	
	public void read(ObjectInputStream ois) throws IOException
	{
		turnTimeOption = ois.readLong();
		bonusesSet = ois.readInt();
		gameEndOption = ois.readInt();
		gameEndOptionValue = ois.readInt();
		
	}
	

}
