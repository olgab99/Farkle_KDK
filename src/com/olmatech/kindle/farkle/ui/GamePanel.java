package com.olmatech.kindle.farkle.ui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.JPanel;

import com.amazon.kindle.kindlet.event.GestureDispatcher;
import com.amazon.kindle.kindlet.event.GestureEvent;
import com.amazon.kindle.kindlet.input.Gestures;
import com.olmatech.kindle.farkle.Controller;
import com.olmatech.kindle.farkle.Game;
import com.olmatech.kindle.farkle.screens.BaseScreen;
import com.olmatech.kindle.farkle.screens.GameScreen;
import com.olmatech.kindle.farkle.screens.HighScoreInputScreen;
import com.olmatech.kindle.farkle.screens.HighScoreListScreen;
import com.olmatech.kindle.farkle.screens.StartScreen;

public class GamePanel extends JPanel{
	
	private static Controller controller;
	
	
	private BaseScreen curScreen;
	
		
	public GamePanel()
	{
		super();
		controller = Controller.getController();
		
		final GestureDispatcher gestureDispatcher = new GestureDispatcher();
		this.addMouseListener(gestureDispatcher);
		 this.addMouseMotionListener(gestureDispatcher);
		 final ActionMap actionMap = getActionMap();
	        actionMap.put(Gestures.ACTION_TAP, new AbstractAction() {
	            public void actionPerformed(final ActionEvent e) {
	              //figure out button
	               final Point p = ((GestureEvent) e).getLocation();      
	               
	               if(curScreen != null)
	               {
	            	   //Log.d("Click " + p.x + "  " + p.y);
	            	   curScreen.processClick(p);
	               }
	            
	            }
	        });
		
	}
	
	public void clearScreen()
	{
		curScreen = null;
	}
	
	
	public void processStateChange(final int newState, final int oldState)
	{
		if(curScreen != null)
		{
			curScreen.processSateChange(newState, oldState);
		}
	}
	
	public void processTimeTick(final long tm)
	{
		if(curScreen != null)
		{
			curScreen.processTimeTick(tm);
		}
	}
	
	
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	protected void paintComponent(Graphics g) {
		if(Controller.isDestroyed()) return;
 		super.paintComponent(g);
		if(curScreen == null)
		{
			initScreen(this.getWidth(), this.getHeight());
			if(curScreen == null) return;
		}
		curScreen.draw(g);
	}




	private void drawStartScreen(Graphics g, final int w, final int h)
	{
		//draw buttons 
		if(curScreen == null)
		{
			initScreen(w,h);
			
		}
		curScreen.draw(g);
	}
	
	public void resetGame()
	{
		if(curScreen != null)
		{
			curScreen.resetGame();
		}
	}
	
	public void invalidateScreen()
	{
		curScreen = null;
	}
	
	/**
	 * Init screen after invalidating
	 * @param w
	 * @param h
	 */
	private void initScreen(final int w, final int h)
	{
		Game game = Game.getGame();
		final int gameMode = game.getGameMode();
		switch(gameMode)
		{
		case Game.MODE_START:
			curScreen = new StartScreen(0, 0, w, h, this);
			break;
		case Game.MODE_FREE_PLAY:
			curScreen = new GameScreen(0, 0, w, h, this);
			break;
		case Game.MODE_HIGH_SCORE_INPUT:
			curScreen = new HighScoreInputScreen(0, 0, w, h, this, "/high_score_setup.png");
			break;
		case Game.MODE_HIGH_SCORE_LIST:
			curScreen = new HighScoreListScreen(0, 0, w, h, this);
			break;
		case Game.MODE_HIGH_SCORE:
			curScreen = new HighScoreListScreen(0, 0, w, h, this); //TODO
			break;
		default:
			curScreen = new StartScreen(0, 0, w, h, this);
			break;
		}
	}
	
	

}
