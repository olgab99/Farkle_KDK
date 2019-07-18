package com.olmatech.kindle.farkle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

//all related to current game
public class Game {
	
	private static Game refGame;
	
	//high score list - array of high scores - 
		private ArrayList highScoreList; // list of HiScoreItem objects
		private Options refOptions;	
		
		//game states	
		public final static int STATE_NONE=0;
		public final static int STATE_ROLL =1; //going to roll
		public final static int STATE_CHECK=2; //checking
		public final static int STATE_BANK=3;
		public final static int STATE_HELP=4;
		public final static int STATE_ABOUT=5;
		public final static int STATE_FARKLE_ON_ROLL=6;
		
		private int gameState = STATE_NONE;
		private int prevState= STATE_NONE;
		
		//game mode
		public final static int MODE_FREE_PLAY=0;
		public final static int MODE_HIGH_SCORE =1;
		public final static int MODE_VS=2;
		public final static int MODE_TEACH_ME=3;
		public final static int MODE_START=4;	
		public final static int MODE_INPUT_NAMES=6;		
		public final static int MODE_ALL_SCORES=7;
		public final static int MODE_HELP=8;
		public final static int MODE_ABOUT=9;
		public final static int MODE_HIGH_SCORE_INPUT= 10;  //HighScoreInputScreen
		public final static int MODE_HIGH_SCORE_LIST=11; //HighScoreListScreen
		
		private int gameMode =MODE_START;
		
		public static final int TOTAL_DICE =6;
		private final static int MAXRAND = 6;	
		
		//Kindle turn data
		/*
		 * List of nomination used by Kindle in current turn
		 */
		public int[] kdicesUsed = new int[TOTAL_DICE];
		
		//players
		private Player[] players;
		private int turn =0; //player turn
		
		//dice	
		private int[] curRoll = new int[TOTAL_DICE];
		private Random rand;
		
		//options
		private static long turnTime =0;  //time passed for this turn 
		
		//Bonuces
		public final static int BONUS_NO_BONUS=0;
		public final static int BONUS_REROLL_FARKLE=1;
		public final static int BONUS_5_MAGNET =2;
		public final static int BONUS_1_MAGNET =3;
		public final static int BONUS_3_MAGNET =4;
		public final static int BONUS_LUCKY_LAST_ROLL=5;		
		//private static int bonus = BONUS_NO_BONUS;
		
		//corerspond to indexes in list
		public final static int TIME_NO_LIMIT=0;
		public final static int TIME_LIMIT_30= 1;
		public final static int TIME_LIMIT_60=2;
		
	private Game()
	{
		Date todaysDate = new java.util.Date();
		rand = new Random(todaysDate.getTime());
		refOptions = Options.getOptions();
	}
	
	public static Game getGame()
	{
		if(refGame == null) refGame = new Game();
		return refGame;
	}
	
	public int getGameMode()
	{
		return gameMode;
	}
	
	public String getTopScreenTitle()
	{
		switch(gameMode)
		{
		case MODE_FREE_PLAY: return GLib.TITLES.getString("freeplay");
		case MODE_HIGH_SCORE: return GLib.TITLES.getString("hsore");
		case MODE_VS: return GLib.TITLES.getString("vs");
		case MODE_TEACH_ME: return GLib.TITLES.getString("teachme");
		case MODE_START: return null;
		case MODE_INPUT_NAMES: return GLib.TITLES.getString("input");
		case MODE_ALL_SCORES: return GLib.TITLES.getString("allscores");
		case MODE_HELP: return GLib.TITLES.getString("help");
		case MODE_ABOUT: return GLib.TITLES.getString("about");
		default: return null;
		
		}
	};
	
	public boolean isGameMode()
	{
		switch(gameMode)
		{
		case MODE_FREE_PLAY:
		case MODE_HIGH_SCORE:
		case MODE_VS:
			return true;
		default: return false;
		}
	}
	
	public void setState(final int val)
	{
		if(gameState == val) return;
		prevState = gameState;
		gameState = val;
	}
	
	public void setGameMode(final int val)
	{
		gameMode = val;
	}
	
		
	public int getGameState()
	{
		return gameState;
	}
	
	public int getGamePrevState()
	{
		return prevState;
	}
	
	public void resetGame()
	{
		gameMode =MODE_START;
		gameState = STATE_NONE;
		prevState= STATE_NONE;
	}
	
	public void setTurnTime(final long val)
	{
		turnTime = val;
	}
	
	public long getTurnTime()
	{
		return turnTime;
	}
	
	//Players info -------------------------------///////////////////////////////////////////////////
	
	public void intPlayers(final int num)
	{
		players = new Player[num];
		turn = 0;
		for(int i=0; i < num; i++)
		{
			players[i] = new Player(i);			
		}
		
	}
	
	public boolean getHavePlayers()
	{
		if(players == null || players.length ==0)
		{
			return false; //no active game
		}
		return true;
	}
			public Player getCurPlayer()
			{
				if(turn <0 || turn >= players.length) return null;
				return players[turn];
			}
			
			public void setCurrentPlayerBonus(final int val)
			{
				if(turn <0 || turn >= players.length) return;
				players[turn].setBonus(val);
			}
			
			public void setCurrentPlayerTurnTimeLimit(final int val)
			{
				if(turn <0 || turn >= players.length) return;
				players[turn].setTurnTimeLimit(val);
			}
			
			public String getCurrentPalyerName()
			{
				if(turn <0 || turn >= players.length) return GLib.TITLES.getString("defname");
				return players[turn].getName();
			}
			
			public void setCurrentPlayerName(final String val)
			{
				if(turn <0 || turn >= players.length) return;
				String name = players[turn].getName();
				 players[turn].setName(val);
				 //update name in high score list
				 if(highScoreList != null)
				 {
					 final int cnt = highScoreList.size();
					//highScoreList
					HighScoreItem item;
					for(int i=0; i < cnt; i++)
					{
						item = (HighScoreItem)highScoreList.get(i);
						if(item == null ) continue;
						String plname = item.getPlayerName();
						if(plname == null) continue;
						if(plname.equalsIgnoreCase(name))
						{
							item.setPlayerName(val);
						}						
					}
				 }				
				 
			}
			
			public int getCurPlayerTurnScore(final int t)
			{
				if(turn <0 || turn >= players.length) return 0;
				return players[turn].getTurnScore(t);
			}
			
			public int getCurPlayerCurTurnScore()
			{
				return players[turn].getCurTurnScore();
			}
			
			public void setCurPlayerRollScore(final int val)
			{
				if(turn <0 || turn >= players.length) return;
				players[turn].setRollScore(val);
			}
			
			public int getCurPlayerRollScore()
			{
				return players[turn].getCurRollScore();
			}
			
			
			public int getCurPlayerRoll()
			{
				return players[turn].getCurRoll();
			}

			////////////////////////////// DICE ROLLS ////////////////////////////////////////////
		//Rolling dice
		public int[] rollDice(final int numToRoll)
		{
			final int cnt = (numToRoll >0 && numToRoll <= TOTAL_DICE)? numToRoll : TOTAL_DICE;
			Arrays.fill(curRoll, 0);
			
			for(int i=0; i < cnt; i++)
			{
				curRoll[i] = rand.nextInt(MAXRAND) + 1;		
			}
			return curRoll;
			
		}
		
		public void resetKindleDice()
		{
			for(int i=0; i < TOTAL_DICE; i++)
			{
				kdicesUsed[i] =0;
			}
		}
		
		public void setKindleDice(final int ind, final int val)
		{
			kdicesUsed[ind] =val;
		}
		
		public void incrementKindleDice(final int ind, final int val)
		{
			kdicesUsed[ind] += val;
		}
	
	public void save(ObjectOutputStream oos) throws IOException
	{
		oos.writeInt(gameMode);
	    oos.writeInt(gameState);
	    if(curRoll  != null || curRoll.length == TOTAL_DICE)
	    {
	    	for(int i=0; i < TOTAL_DICE; i++)
	    	{
	    		oos.writeInt(curRoll[i]);
	    	}
	    }
	    else
	    {
	    	for(int i=0; i < TOTAL_DICE; i++)
	    	{
	    		oos.writeInt(0);
	    	}
	    }
	    oos.writeLong(turnTime);
	    //options
	    refOptions.save(oos);
	   
	    //players
	    final int cnt = players.length;
	    oos.writeInt(cnt);
	    for(int i=0; i < cnt; i++)
	    {
	    	players[i].save(oos);
	    }
	}
	
	public boolean restore(final ObjectInputStream ois) throws IOException, ClassNotFoundException
	{
		boolean result = true;
		gameMode = ois.readInt();
	    gameState = ois.readInt();
	    curRoll = new int[TOTAL_DICE];
	    for(int i=0; i < TOTAL_DICE; i++)
    	{
	    	curRoll[i] = ois.readInt();			    	
    	}
	    turnTime = ois.readLong();
	    
	    refOptions.read(ois);
	    int cnt = ois.readInt();
	    if(cnt >0)
	    {
	    	players = new Player[cnt];
	    	for(int i=0; i < cnt; i++)
		    {
	    		players[i] = new Player(i);
		    	players[i].read(ois);
		    }
	    }
	    else
	    {
	    	players = null;
	    	result = false;
	    	
	    }
	    return result;
	}
	
	///////////////////// --------- Player High Score -------------------- ////////////////////
	/***
	 * Get player high score by name
	 * @return
	 */
	public int getCurPlayerHighScore()
	{
		if(highScoreList == null) return 0;
		final int cnt = highScoreList.size();
		if(cnt ==0) return 0;
		//highScoreList
		String name = this.getCurrentPalyerName();
		HighScoreItem item;
		for(int i=0; i < cnt; i++)
		{
			item = (HighScoreItem)highScoreList.get(i);
			if(item == null ) continue;
			String plname = item.getPlayerName();
			if(plname == null) continue;
			if(plname.equalsIgnoreCase(name))
			{
				return item.getScore();
			}
			
		}
		return 0;		
	}

}
