package com.olmatech.kindle.farkle.screens;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import com.olmatech.kindle.farkle.GLib;
import com.olmatech.kindle.farkle.Log;
import com.olmatech.kindle.farkle.ui.GamePanel;
import com.olmatech.kindle.farkle.ui.XButton;

//app start screen
public class StartScreen extends BaseScreen{
	
	private XButton butFreePlay;
	private XButton butHighScore;
	private XButton butVsMode;
	
	private XButton butAbout;
	private XButton butTeachMe;
	
	private Point ptTopBot = null;
	
	public StartScreen(final int x, final int y, final int w, final int h, final GamePanel p)
	{
		super(x,y,w,h, p);
		butFreePlay = new XButton(GLib.TITLES.getString("free"), GLib.TITLES.getString("play"), XButton.TYPE_SQUARE);
		butHighScore = new XButton(GLib.TITLES.getString("high"), GLib.TITLES.getString("score"), XButton.TYPE_SQUARE);
		butVsMode = new XButton(GLib.TITLES.getString("vs"), GLib.TITLES.getString("mode"), XButton.TYPE_SQUARE);
		
		butAbout = new XButton(GLib.TITLES.getString("about"), null, XButton.TYPE_LONG);
		butTeachMe = new XButton(GLib.TITLES.getString("teachme"), null, XButton.TYPE_LONG);	
		
		
		
	}

	public void draw(Graphics g) {
		//TODO - check if init done
		ptTopBot = GLib.drawStartScreenBg(g, bounds.width, bounds.height);
		if(ptTopBot == null)
		{
			return;
		}
		if(!isLayoutDone)
		{
			doLayout();
			isLayoutDone = true;
		}		
		butFreePlay.draw(g);
		butHighScore.draw(g);
		butVsMode.draw(g);
		butAbout.draw(g);
		butTeachMe.draw(g);
		
	}

	public void doLayout() {
		//lay buttons
		final Dimension sz = butFreePlay.getSize();
		int x = (this.bounds.width - sz.width *3 - BUT_GAP*2) / 2;
		int y = 20 + ptTopBot.x;
		butFreePlay.setLocation(x, y);
		x += sz.width + BUT_GAP;
		butHighScore.setLocation(x, y);
		x += sz.width + BUT_GAP;
		butVsMode.setLocation(x, y);
		
		//bottom
		final Dimension sz2 = butAbout.getSize();
		
		x = (bounds.width - sz2.width) /2;
		y = ptTopBot.y - sz2.height*2 - BUT_GAP - 20;
		butAbout.setLocation(x, y);
		y += sz2.height + BUT_GAP;
		
		butTeachMe.setLocation(x, y);
	}

	public boolean processClick(Point pt) {
				
		if(butFreePlay.getClicked(pt))
		{
			if(showYesNowDlg(GLib.TITLES.getString("freeplaymsg"), GLib.TITLES.getString("welcfreeplay")))
			{
				controller.processFreePlayClick();
			}		
			
			return true;
		}
		else if(butHighScore.getClicked(pt))
		{
			controller.processHighScoreClick();
			return true;
		}
		else if(butVsMode.getClicked(pt))
		{
			controller.processVsModeClick();
			return true;
		}
		else if(butAbout.getClicked(pt))
		{
			controller.processAboutClick();
			return true;
		}
		else if(butTeachMe.getClicked(pt))
		{			
			if(this.showYesNowDlg(GLib.TITLES.getString("teachmemsg"), GLib.TITLES.getString("teachme")))
			{
				//show teach me
				controller.processTeachMeClick();
			}
			return true;
		}		
		
		return false;
	}

	public void processSateChange(int newState, int oldState) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	

}
