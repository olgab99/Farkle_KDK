package com.olmatech.kindle.farkle.screens;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import com.olmatech.kindle.farkle.Controller;
import com.olmatech.kindle.farkle.GLib;
import com.olmatech.kindle.farkle.Game;
import com.olmatech.kindle.farkle.Player;
import com.olmatech.kindle.farkle.ui.GamePanel;

public class HighScoreInputScreen extends BaseScreen{
	
	//button rectangles - we will use background images
	private final Rectangle recButViewScore = SetValues.hsi_recViewScoresBut;
	private final Rectangle recButEditName = SetValues.hsi_recEditNameBut;
	private final Rectangle recButButBonusInfo = SetValues.hsi_recBonusInfoBut;
	private final Rectangle recButBack = SetValues.hsi_recBackBut;
	private final Rectangle recButStartGame = SetValues.hsi_recStartGameBut;
	private final Rectangle recButHelp = SetValues.hsi_recHelpBut;
	
	//radios
	private final Rectangle recRadioNoLimit = SetValues.hsi_recRadioNoLimit;
	private final Rectangle recRadio30sec= SetValues.hsi_recRadio30sec;
	private final Rectangle recRadio60sec = SetValues.hsi_recRadio60sec;
	
	private int selIndTime=0;
	private int selIndBonus = 0;
	
	//bonuses
	private final Rectangle[] recRadioBonuses = SetValues.hsi_recRadioBonuses;
	private final Rectangle[] recTextBonuses = SetValues.hsi_recBonuses;
	
	//text boxes
	private final Point ptTextCurScore = SetValues.hsi_ptCurHS;
	private final Point ptTextPlayerName = SetValues.hsi_ptPlayeName;	
	
	private int high_score=0;
	private String name;
	
	private final int D= SetValues.radioInnerOffset;
	private final int RD = SetValues.innerRadio;

	public HighScoreInputScreen(int x, int y, int w, int h, GamePanel p, final String bkImg) {
		super(x, y, w, h, p,bkImg);
		
		Player pl = game.getCurPlayer();		
		high_score = pl.getHighScore();
		name = pl.getName();
		final int tmLimit = pl.getTurnTimeLimit();
		switch(tmLimit)
		{
		case Game.TIME_LIMIT_30:
			selIndTime = 1;
			break;
		case Game.TIME_LIMIT_60:
			selIndTime = 2;
			break;
		default:
			selIndTime=0;
			break;
		}
		
		selIndBonus = pl.getBonus();		
	}

	public void draw(Graphics g) {
		super.draw(g);
		//draw high score and name
		g.setFont(GLib.buttonFont);
		g.setColor(GLib.BLACK);
		g.drawString(Integer.toString(high_score), ptTextCurScore.x, ptTextCurScore.y);
		if(name != null)
		{
			g.drawString(name, ptTextPlayerName.x, ptTextPlayerName.y);
		}
		Point pt = new Point();
		//draw radios
		switch(selIndTime)
		{
		case 1:
			pt.x = recRadio30sec.x + D;
			pt.y = recRadio30sec.y + D;
			break;
		case 2:
			pt.x = recRadio60sec.x + D;
			pt.y = recRadio60sec.y + D;
			break;
		default:
			pt.x = recRadioNoLimit.x + D;
			pt.y = recRadioNoLimit.y + D;
			break;
		}
		g.fillOval(pt.x, pt.y, RD, RD);
		//bonus
		pt.x = recRadioBonuses[selIndBonus].x +D;
		pt.y = recRadioBonuses[selIndBonus].y +D;
		g.fillOval(pt.x, pt.y, RD, RD);
	}

	public void doLayout() {
		
		isLayoutDone=true;
		
	}

	public void processSateChange(int newState, int oldState) {
		
		
	}
	
	

	/* (non-Javadoc)
	 * @see com.olmatech.kindle.farkle.screens.BaseScreen#processStrInput(java.lang.String)
	 */
	protected void processStrInput(String in) {
		if(in != null)
		{
			if(!name.equalsIgnoreCase(in)){
				name = in;
				game.setCurrentPlayerName(name);
				repaint();
			}					
		}
	}
	
	private void setTurnTimeLimit(final int val)
	{
		game.setCurrentPlayerTurnTimeLimit(val);
		selIndTime = val;
		repaint();
	}

	public boolean processClick(Point pt) {
		if(recButViewScore.contains(pt))
		{
			controller.processViewScoresClick();
			return true;
		}
		if(recButEditName.contains(pt))
		{
			//show edit name dlg
			this.showInputDlg("Farkle", "Change player name", name);			
			return true;
		}
		
		if(recButButBonusInfo.contains(pt))
		{
			controller.processBonusInfoClick();
			return true;
		}
		if(recButBack.contains(pt))
		{
			controller.procesBackClick();		
			return true;
		}
		if(recButStartGame.contains(pt))
		{
			
			return true;
		}
		if(recButHelp.contains(pt))
		{
			controller.processHelpClick();
			return true;
		}
		if(recRadioNoLimit.contains(pt))
		{
			setTurnTimeLimit(Game.TIME_NO_LIMIT);
			return true;
		}
		if(recRadio30sec.contains(pt))
		{
			setTurnTimeLimit(Game.TIME_LIMIT_30);
			return true;
		}
		if(recRadio60sec.contains(pt))
		{
			setTurnTimeLimit(Game.TIME_LIMIT_60);
			return true;
		}
		final int cnt = recRadioBonuses.length;
		for(int i=0; i < cnt; i++)
		{
			if(recRadioBonuses[i].contains(pt))
			{
				game.setCurrentPlayerBonus(i);
				selIndBonus =i;
				repaint();
				return true;
			}
		}
		
		return false;
	}

}
