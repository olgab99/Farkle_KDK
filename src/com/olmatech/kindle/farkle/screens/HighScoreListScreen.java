package com.olmatech.kindle.farkle.screens;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JLabel;

import com.olmatech.kindle.farkle.Controller;
import com.olmatech.kindle.farkle.GLib;
import com.olmatech.kindle.farkle.ui.GamePanel;
import com.olmatech.kindle.farkle.ui.XButton;

public class HighScoreListScreen extends BaseScreen{
	
	private XButton butBack;
	private XButton butClear;
	
	private final int MARGIN=50;
	private int row_height;
	private int row_width;
	
	private final int TOTAL_ROWS=10;
	
	private static Font font = GLib.boldFont;
	private static FontMetrics fm = GLib.fmBoldFont;
	private int text_top;

	public HighScoreListScreen(int x, int y, int w, int h, GamePanel p) {
		super(x, y, w, h, p);
		butBack = new XButton(GLib.TITLES.getString("back"), null, XButton.TYPE_CUSTOM);
		butClear = new XButton(GLib.TITLES.getString("clearhs"), null, XButton.TYPE_CUSTOM);
	}

	public void draw(Graphics g) {
		super.draw(g);
		//render 10 scores
		g.setFont(font);
		g.setColor(GLib.LT_GRAY);
		final int top = GLib.getTopPartBot() + ROW_GAP;
		int y = top;
		int x = MARGIN;
		final int dy = ROW_GAP+row_height;
		for(int i=0; i < TOTAL_ROWS; i++)
		{
			g.fillRect(x, y, row_height, row_height);
			x += row_height+ROW_GAP;
			g.fillRect(x, y, row_width, row_height);
			y+=dy;
			x=MARGIN;
		}
		//text
		g.setColor(GLib.BLACK);
		int num=1;
		final int x1 = MARGIN + row_height;
		final int x2 = x1 + ROW_GAP*2;
		final int x3 = bounds.width - MARGIN-ROW_GAP;
		y = text_top;
		String strnum;
		String plname="PLAYER";
		String scorestr= "300300";		
		for(int i=0; i < TOTAL_ROWS; i++,num++)
		{
			strnum = Integer.toString(num);			
			x = x1 - fm.stringWidth(strnum);
			g.drawString(strnum, x, y);
			
			g.drawString(plname, x2, y);
			
			x = x3 -fm.stringWidth(scorestr);
			g.drawString(scorestr, x, y);			
			y+=dy;			
		}
		
		butClear.draw(g);
		butBack.draw(g);		
	}

	public void doLayout() {
		final int top = GLib.getTopPartBot();
		final int botH = GLib.getBotPartHeight();
		final Dimension sz = butClear.getSize();
		int y = bounds.height - (botH - sz.height)/2 - sz.height;
		int x = bounds.width - sz.width - BUT_GAP;
		butClear.setLocation(x, y);
		butBack.setLocation(BUT_GAP, y);
		
		int cw = bounds.height - top - botH -(TOTAL_ROWS+1)*ROW_GAP;
		row_height = cw/TOTAL_ROWS;
		row_width = bounds.width - MARGIN*2 - ROW_GAP-row_height;
		text_top = top+ ROW_GAP + row_height - (row_height-fm.getAscent())/2;
		isLayoutDone = true;
	}

	public void processSateChange(int newState, int oldState) {
		// TODO Auto-generated method stub
		
	}

	public boolean processClick(Point pt) {
		if(butBack.getClicked(pt))
		{
			Controller c = Controller.getController();
			if(c!= null)
			{
				c.processQuit(); // TOIDO - go to proper screen
			}
			return true;		
		}
		return false;
	}

}
