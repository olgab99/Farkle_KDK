package com.olmatech.kindle.farkle.screens;

import java.awt.Graphics;
import java.awt.Point;

import com.olmatech.kindle.farkle.Controller;
import com.olmatech.kindle.farkle.GLib;
import com.olmatech.kindle.farkle.Game;
import com.olmatech.kindle.farkle.ScoreCombo;
import com.olmatech.kindle.farkle.Scores;
import com.olmatech.kindle.farkle.ui.GamePanel;

public class AllScoresScreen extends BaseScreen{

	public AllScoresScreen(int x, int y, int w, int h, GamePanel p) {
		super(x, y, w, h, p);
		
	}

	public void draw(Graphics g) {
		//draw title
		Point ptTop = new Point(50,50);
				String s = GLib.TITLES.getString("scoring");
				g.setFont(GLib.boldFont);
				g.setColor(ltgray);
				
				g.fillRect(0, 0, bounds.width, ROW_HEIGHT);
				
				//fill combos rect
				final int dy = ROW_HEIGHT + ROW_GAP;
				int y =dy;		
				ScoreCombo[] combo = Scores.getScoresToDraw();
				final int cnt = combo.length;
				int h =dy * cnt;
				
				g.fillRect(ptTop.x, y, bounds.width, h);
				
				int x = (bounds.width - GLib.fmBoldFont.stringWidth(s))/2;
				int yTxt = ROW_HEIGHT - TEXT_OFFSET_Y;
				g.setColor(black);
				g.drawString(s, x, yTxt);		
				
				yTxt += dy;
				y += ROW_GAP;
				
				x = ROW_GAP;
				//draw combos
				int diceCnt;
				int dice_width = DiceBox.getWidth(DiceBox.TYPE_SMALL);
				final int dd = dice_width + ROW_GAP;
				int num;
				g.setColor(black);
				
				for(int i =0; i < cnt; i++)
				{
					for(int j=0; j < Game.TOTAL_DICE; j++)
					{
						diceCnt = combo[i].getDiceAt(j);
						if(diceCnt <=0) continue;
						num = j+1;
						for(int k = 0; k < diceCnt; k++)
						{
							Dice.drawSmallDiceAtLocation(g, num, x, y);
							
							//g.drawRect(x, y, dice_width, dice_width);
							
							x += dd;
							
							
						}	
						//draw text
						s = Integer.toString(combo[i].getScore());
						g.drawString(s, x, yTxt);
						
						x = ROW_GAP;
						y += dy;
						yTxt += dy;
						
					}//combo
				}//dice combos
		
	}

	public void doLayout() {
		// TODO Auto-generated method stub
		
	}

	public void processSateChange(int newState, int oldState) {
		// TODO Auto-generated method stub
		
	}

	public boolean processClick(Point pt) {
		// TODO Auto-generated method stub
		return false;
	}

}
