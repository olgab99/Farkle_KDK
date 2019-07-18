package com.olmatech.kindle.farkle;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import javax.swing.Timer;

import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.ui.KMenu;
import com.olmatech.kindle.farkle.ui.GamePanel;
import com.olmatech.kindle.farkle.ui.HelpPanel;
import com.olmatech.kindle.farkle.ui.InputNamesPanel;


public class Controller {
	
	private static Controller refController;
	
	private static GamePanel gamePanel;
	private final static String gamePanelName="g";
	private HelpPanel helpPanel;
	private final static String helpPanelName="h";
	private InputNamesPanel inputPanel;
	private final static String inputPanelName="in";
	
	private final static int PANEL_GAME=0;
	public final static int PANEL_HELP=1;
	private final static int PANEL_NAMES=2;
	
	private int curPanel=PANEL_GAME;
	
	
	KMenu menu;
	private KindletContext context;	
	private static Container root;
	
	private volatile static boolean isFirstTime = true;
	private volatile static boolean destroyed= false;	
		
	//caching file
	private final static String saveFile = "save.dat";
	public static String homeDir;
	
	
	private Timer timer;
	private long time=0;
	
	private Game game;
		
		private Controller()
		{
			
			game = Game.getGame();
			
		}
		
		public static Controller getController()
		{
			if(refController == null)
			{
				refController = new Controller();
			}
			
			return refController;
		}
		
		public void doRepaint()
		{
			if(root != null)
			{
				root.repaint();
			}
		}

		public void initUI(KindletContext cnt) {
			context = cnt;
			homeDir = context.getHomeDirectory().getAbsolutePath();   
			root = cnt.getRootContainer();
			root.addComponentListener(new ComponentListener(){

				public void componentHidden(ComponentEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				public void componentMoved(ComponentEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				public void componentResized(ComponentEvent e) {
					onScreenSizeChange(root.getWidth(), root.getHeight());	
					
				}

				public void componentShown(ComponentEvent arg0) {
					// TODO Auto-generated method stub
					
				}				
			});			
		}
		
		public void craeteUI()
		{
			gamePanel = new GamePanel();
			helpPanel = new HelpPanel();
			CardLayout cards = new CardLayout();
	    	root.setLayout(cards); 
	    	if(Controller.isDestroyed()) return;
	    	
	    	root.add(gamePanel, gamePanelName);  
	    	root.add(helpPanel, helpPanelName);
	    	
	    	cards.show(root, gamePanelName);
	    	root.repaint();  
	    	initStartGraphicsAsync();
 	
		}
		
		private void initStartGraphicsAsync()
		{
			Thread t = new Thread()
			{

				/* (non-Javadoc)
				 * @see java.lang.Thread#run()
				 */
				public void run() {
					try {
						GLib.initSplashScreen(gamePanel.getClass(), root);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			};
			t.start();
		}

		public static boolean isDestroyed() {
			return destroyed;
		}
		
		
		public void onStart()
		{
			if(isFirstTime)
			{
				boolean haveGame= false;
				synchronized(this)
				{
					craeteUI();
					 haveGame = restoreGame();					
					isFirstTime = false;
				}
				if(haveGame)
				{
					goToSavedGame();
				}
			}
			startTimer();
		}
		
		public void onStop()
		{
			stopTimer();
		}
		
		public void onDestroy()
		{
			destroyed = true;
			stopTimer();
			if(game.isGameMode())
			{
				//save game
				saveGame();
			}
			
		}
		
		private void goToSavedGame()
		{
			switch(game.getGameMode())
			{
			case Game.MODE_FREE_PLAY:
				//go to free play screen
				if(gamePanel != null)
				{
					gamePanel.clearScreen();
					gamePanel.repaint();
				}
				break;
			case Game.MODE_HIGH_SCORE:
				
				break;
			case Game.MODE_VS:
				
				break;
			default: break;			
			}
		}
		
		
		public void onScreenSizeChange(final int neww, final int newh)	
		{
			GLib.setScreenSize(neww, newh);	
		}
		
		//GAME
		
		public void setGameState(final int val)
		{
			if(game.getGameState() == val) return;
			game.setState(val);
			
			switch(game.getGameMode())
			{
			case Game.MODE_FREE_PLAY:
			case Game.MODE_HIGH_SCORE:
			case Game.MODE_VS:
				if(gamePanel != null)
				{
					gamePanel.processStateChange(game.getGameState(), game.getGamePrevState());
				}
				
				break;
			default: break;
			}
		}
		
		
		
		
		
		
		
		
		
//		public static void removeDiceFromCurRoll(final int ind)
//		{
//			if(ind < 0 || ind >= TOTAL_DICE) return;
//			curRoll[ind] =0;
//		}
//		
//		public static void addDiceToCurRoll(final int val)
//		{
//			for(int i=0; i < TOTAL_DICE; i++)
//			{
//				if(curRoll[i] == 0)
//				{
//					curRoll[i] = val;
//					break;
//				}
//			}			
//		}
		
		//button clicks ------------------- /////////////////////////////////////////////////////
		//START screen
		public void processFreePlayClick()
		{
			game.setGameMode(Game.MODE_FREE_PLAY);			
			game.intPlayers(1);
			if(gamePanel != null)
			{
				gamePanel.clearScreen();
				root.repaint();
			}
		}
		public void processHighScoreClick()
		{
			game.setGameMode(Game.MODE_HIGH_SCORE_INPUT);	
			game.intPlayers(1);
			if(gamePanel != null)
			{
				gamePanel.clearScreen();
				root.repaint();
			}
		}
		
		public void processAboutClick()
		{
			
		}
		
		public void processTeachMeClick()
		{
			
		}
		
		public void processVsModeClick()
		{
			
		}
		public void processShowAllScoringClick()
		{
			if(helpPanel != null)
			{
				helpPanel.setTextForMode(Game.MODE_ALL_SCORES, false);
				helpPanel.setPrevPanel(this.curPanel);
			}
			showPanel(PANEL_HELP);
		}
		
		public void processCloseHelpPanel(final int prevPanel)
		{
			showPanel(prevPanel);
		}
		
		public void processQuit()
		{
			if(gamePanel != null)
			{
				gamePanel.resetGame();
				gamePanel.invalidateScreen();
			}
			//change current screen to Start
			game.resetGame();
			
			root.repaint();
		}
		
		//////////////////// High Score Input panel clicks
		public void processViewScoresClick()
		{
			game.setGameMode(Game.MODE_HIGH_SCORE_LIST);				
			if(gamePanel != null)
			{
				gamePanel.clearScreen();
				root.repaint();
			}
		}
		
		public void processBonusInfoClick()
		{
			
		}
		
		public void procesBackClick()
		{
			game.setGameMode(Game.MODE_START);				
			if(gamePanel != null)
			{
				gamePanel.clearScreen();
				root.repaint();
			}
		}
		
		public void processStartGameClick()
		{
			
		}
		
		public void processHelpClick()
		{
			
		}
		
		///////////////////////////////////////////////////////////
		private void showPanel(final int index)
		{
			if(curPanel != index)
			{
				CardLayout cards = (CardLayout) root.getLayout();
				switch(index)
				{
				case PANEL_GAME:
					startTimer();
					cards.show(root, gamePanelName);
					break;
				case PANEL_HELP:
					stopTimer();
					cards.show(root, helpPanelName);
					break;
				case PANEL_NAMES:
					
					break;
				}
				curPanel = index;
			}
			else
			{
				root.repaint();
			}
		}
		
		////////////////////// SAVING GAME DATA //////////////////////////
		//save games 
		
		public boolean saveGame()
		{
			if(!game.getHavePlayers())
			{
				return true; //no active game
			}
			String fname =homeDir+ File.separator + saveFile;
			File f = new File(fname);
			if(f.exists())
			{			
				try
				   {
					   f.delete();
				   }
				   catch (Exception e) 
				   {
					   return false;
				   }
				
			}
			
			f = new File(fname);
			try
			{
				f.createNewFile();
			}
			catch(IOException ex)
			{			
				return false;
			}
			catch (SecurityException ex)
			{			
				return false;
			}			
			
			FileOutputStream fout = null;
			ObjectOutputStream oos = null;
			try 
			{
				fout = new FileOutputStream(f, false);
			    oos = new ObjectOutputStream(fout);
			    			    
			    game.save(oos);
			    
				
			}
		   catch (Exception e) 
		   { 	
			   Log.err("Error saving game to a file " + e.getMessage());	
				return false;
		    } 
		   finally
		   {			   
				try {
					oos.close();
					fout.close();
				} catch (IOException e) {
					Log.err("Error saving game to a file " + e.getMessage());	
				}	
			}	
			
			Log.d("Save game ");
			  
			   return true;		
		}
		
		public boolean restoreGame()
		{
			String fname =homeDir+ File.separator + saveFile;
			File f = new File(fname);			
			
			if(!f.exists())
			{				
				return false;
			}
			FileInputStream fin = null;
			ObjectInputStream ois = null;
			boolean result = true;
			try
			{
				fin = new FileInputStream(f);
			    ois = new ObjectInputStream(fin);
			    
			    result = game.restore(ois);			    
			    
			}
			 catch (Exception e) 
			   { 
				  // RBLogger.d("Error reading file " + e.getMessage());			   
				   result = false; 
			   }
			   finally
			   {
				   try {
					ois.close();
					fin.close();			
				} catch (IOException e) 
				{
					//RBLogger.d("Error closing file " + e.getMessage());
				}		   
			   }
			
			//delete game file
			f.delete();
			
			return result;
		}
		
////////////////TIMER //////////////////
	private void startTimer()
	{
		if(timer != null && timer.isRunning()) return;
		ActionListener TimeTick = new ActionListener(){
		
			public void actionPerformed(ActionEvent e) {
				processTimeTick();				
			}
			
		};
		
		//define interval	
		final int TICK = 1000; //1 sec
		timer = new Timer(TICK, TimeTick); 
		timer.setInitialDelay(TICK);
		timer.start(); 		
	}

	private void stopTimer()
	{
		if(timer != null)
		{
			timer.stop();
			timer = null;				
		}
	}

	private void processTimeTick()
	{	
			time++;
			if(gamePanel != null)
			{
				gamePanel.processTimeTick(time);
			}
	}
	
	public long getTime()
	{
		return time;
	}
	
	////////////// High Scores
	
			
}
