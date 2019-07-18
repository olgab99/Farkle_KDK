package com.olmatech.kindle.farkle.screens;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Arrays;

import javax.swing.JLabel;

import com.olmatech.kindle.farkle.Controller;
import com.olmatech.kindle.farkle.GLib;
import com.olmatech.kindle.farkle.Game;
import com.olmatech.kindle.farkle.Log;
import com.olmatech.kindle.farkle.Player;
import com.olmatech.kindle.farkle.ScoreCombo;
import com.olmatech.kindle.farkle.ScoreResult;
import com.olmatech.kindle.farkle.Scores;
import com.olmatech.kindle.farkle.ui.GamePanel;
import com.olmatech.kindle.farkle.ui.XButton;

public class GameScreen extends BaseScreen{
		
	private XButton butRoll;
	private XButton butQuit;
	private XButton butBank;
	
	private XButton butCheck; //will overlap buton Bank
	
	private int side_panel_width = 100;
	private int side_panel_top;	
	
	private Rectangle recTotalTurnScore;
	private Rectangle recSubTotal;
	
	//med dice boxes
	private DiceBox[] topDiceBoxes = new DiceBox[Game.TOTAL_DICE];
	
	//strings
	private final static String TOTAL =  GLib.TITLES.getString("total");
	
	
	//5 rows of small dice boxes
	private DiceBox[][] diceRows = new DiceBox[5][];
	
	//dice we roll
	private DiceBox[] diceToRoll = new DiceBox[Game.TOTAL_DICE];
	private int[] diceArray = new int[Game.TOTAL_DICE];  //to re-use
	
	//game states	
	private final int ROWS = 10;	
	//private int prevState = -1;	
	private int left;
	
	//Farkle on roll
	private long time_farkle_on_roll_start=0;
	private int FARKLE_ON_ROLL_TIME = 5; // sec
	
	private final  int TOTAL_DICE = Game.TOTAL_DICE;	
	
	public GameScreen(int x, int y, int w, int h, GamePanel p) {
		super(x, y, w, h, p);
		
		butRoll = new XButton(GLib.TITLES.getString("roll"), null, XButton.TYPE_LONG);
		butQuit = new XButton(GLib.TITLES.getString("quit"), null, XButton.TYPE_MED);
		butBank = new XButton(GLib.TITLES.getString("bank"), null, XButton.TYPE_LONG);
		butBank.setVisible(false);
		
		butCheck = new XButton(GLib.TITLES.getString("check"), null, XButton.TYPE_LONG);
		
		
		for(int i=0; i < Game.TOTAL_DICE; i++)
		{
			topDiceBoxes[i] = new DiceBox(DiceBox.TYPE_MED);
			diceToRoll[i] = new DiceBox(DiceBox.TYPE_LG);
		}
		
		diceRows[0] = new DiceBox[TOTAL_DICE];
		diceRows[1] = new DiceBox[TOTAL_DICE-1];
		diceRows[2] = new DiceBox[TOTAL_DICE-2];
		diceRows[3] = new DiceBox[TOTAL_DICE-3];
		diceRows[4] = new DiceBox[TOTAL_DICE-4];
		
		int cnt = diceRows.length;
		int cnt2;
		//set result boxes
		for(int i=0; i < cnt; i++)
		{
			cnt2 = diceRows[i].length;
			for(int j=0; j < cnt2; j++)
			{
				diceRows[i][j] = new DiceBox(DiceBox.TYPE_SMALL);
				diceRows[i][j].setVisible(false);
				
			}
		}
		
				
		//TEST
		int n = 1;
		for(int i=0; i <TOTAL_DICE; i++, n++ ){
			diceRows[0][i].setDiceValue(0);
			//topDiceBoxes[i].setDiceValue(n);
			diceToRoll[i].setDiceValue(0);
		}		
	}
	
	public void resetGame()
	{
		int cnt = diceRows.length;
		int cnt2;
		//set result boxes
		for(int i=0; i < cnt; i++)
		{
			cnt2 = diceRows[i].length;
			for(int j=0; j < cnt2; j++)
			{
				diceRows[i][j].setVisible(false);
				
			}
		}
		for(int i=0; i < TOTAL_DICE; i++)
		{
			topDiceBoxes[i].setDiceValue(0);
			diceToRoll[i].setDiceValue(0);
			diceArray[i]=0;
		}
		butRoll.setEnabled(true);
		butRoll.setVisible(true);
		butBank.setEnabled(false);
		butBank.setVisible(false);
		butCheck.setVisible(true);
		butCheck.setEnabled(false);
		time_farkle_on_roll_start=0;
		
	}
	
	private void clearTopDice()
	{
		for(int i=0; i < TOTAL_DICE; i++)
		{
			topDiceBoxes[i].setDiceValue(0);			
		}
	}
	
	private void hideResultBoxes()
	{
		final int cnt = diceRows.length;
		for(int i=0; i < cnt; i++)
		{
			final int cnt2 = diceRows[i].length;
			for(int j=0; j < cnt2; j++)
			{
				diceRows[i][j].setDiceValue(0);
				diceRows[i][j].setVisible(false);
				
			}
		}
	}

	public void draw(Graphics g) {
		super.draw(g);
		
		//draw bottom buttons
		butRoll.draw(g);
		butQuit.draw(g);
		butCheck.draw(g);
		
		//side panels
//		g.setColor(GLib.LT_GRAY);
//		g.fillRect(GAP, side_panel_top, side_panel_width, 100);
		
		Player pl = game.getCurPlayer();
		
		g.setFont(GLib.boldFont);
		drawLeftPanel(g, pl);
		drawTotalTurnScore(g, pl);
		butBank.draw(g);
		drawMedDiceBoxes(g);
		drawDiceRows(g);
		drawSubTotalScore(g, pl);
		this.drawDiceToRoll(g);
		
		if(butViewAllScoring != null)
		{
			butViewAllScoring.draw(g);
		}
		
		drawScoresOnRight(g, side_panel_width, new Point(bounds.width -side_panel_width - GAP,side_panel_top));
	}
	
	private void drawMedDiceBoxes(Graphics g)
	{
		if(topDiceBoxes == null) return;
		final int cnt = topDiceBoxes.length;
		for(int i=0; i < cnt; i++)
		{
			topDiceBoxes[i].draw(g);
		}
	}
	
	private void drawSubTotalScore(Graphics g, Player pl)
	{
		g.setColor(ltgray);
		g.fillRect(recSubTotal.x, recSubTotal.y, recSubTotal.width, recSubTotal.height);
		g.setColor(black);
		g.drawRect(recSubTotal.x, recSubTotal.y, recSubTotal.width, recSubTotal.height);
		
		//get cur subtotal score to draw
		
		if(pl!= null)
		{
			int score = pl.getCurCheckScore();
			//Log.d("drawSubTotalScore  score=" +  score);
			if(score >0)
			{
				g.setFont(GLib.titleFont);
				g.setColor(black);
				String s = Integer.toString(score);
				final int x = (recSubTotal.width - GLib.fmTitleFont.stringWidth(s))/2;
				final int dy = (recSubTotal.height - GLib.fmTitleFont.getAscent())/2; 
				g.drawString(s, x + recSubTotal.x, recSubTotal.y + recSubTotal.height - dy);
			}
		}
		
	}
	
	private void drawDiceRows(Graphics g)
	{
		int cnt = diceRows.length;
		int cnt2;
		for(int i=0; i < cnt; i++)
		{
			cnt2 = diceRows[i].length;
			for(int j=0; j < cnt2; j++)
			{
				diceRows[i][j].draw(g);
			}
		}
		
	}
	
	private void drawDiceToRoll(Graphics g)
	{
		if(diceToRoll == null) return;
		final int cnt = diceToRoll.length;
		for(int i=0; i < cnt; i++)
		{
			diceToRoll[i].draw(g);
		}
	}
	
	private void drawTotalTurnScore(Graphics g, Player pl)
	{
		g.setColor(ltgray);
		g.fillRect(recTotalTurnScore.x, recTotalTurnScore.y, recTotalTurnScore.width, recTotalTurnScore.height);
		//draw number		
		g.setColor(black);		
		
		if(pl!= null)
		{
			int score = pl.getTotalScore();			
			if(score >0)
			{
				g.setFont(GLib.titleFont);
				g.setColor(black);
				String s = Integer.toString(score);
				FontMetrics fm = g.getFontMetrics();
				int x = (recTotalTurnScore.width - fm.stringWidth(s))/2;
				g.drawString(s, x + recTotalTurnScore.x, recTotalTurnScore.y + recTotalTurnScore.height - 5);
			}
		}
		
	}
	
	private void drawLeftPanel(Graphics g, Player pl)
	{
		int[] scores= null;
		int totalScore=0;
		int totalTurns = 0;
		if(pl != null)
		{
			scores = pl.getLastScores(ROWS);
			totalScore = pl.getTotalScore();
			totalTurns = pl.getTotalTurns();
		}
		
		final int arrLn = (scores != null)? scores.length : 0;
		final int scoresToShow = (arrLn < ROWS)? arrLn : ROWS;
		final int top_lbl_w =100;
		
		//draw label and 10 rows			
		final int scoreW = 70; //side_panel_width - ROW_GAP - ROW_HEIGHT;
		
		left = (side_panel_width - scoreW)/2;
		int x1 = left; //GAP;
		final int x2 = x1 + ROW_GAP + ROW_HEIGHT;
		int y = side_panel_top;
		final int dy = ROW_HEIGHT + ROW_GAP;
		
		//label
		g.setColor(ltgray);
		g.fillRect(x1, y, top_lbl_w, ROW_HEIGHT);
		
		y += dy;
		for(int i=0; i < ROWS; i++)
		{
			g.fillRect(x1, y, ROW_HEIGHT, ROW_HEIGHT);
			g.fillRect(x2, y, scoreW, ROW_HEIGHT);
			y += dy;
		}
		
		final int scoresBot = y + ROW_HEIGHT-5;
		
		//total
		g.fillRect(x1, y, top_lbl_w, ROW_HEIGHT);
		y += dy;
		//score
		g.fillRect(x1, y, top_lbl_w, ROW_HEIGHT);
		
		//text
		//will get players last 10 scores - TODO
		
		g.setColor(black);
		
		String name = game.getCurrentPalyerName();
		int x = left + (top_lbl_w - GLib.fmBoldFont.stringWidth(name))/2;
		y = side_panel_top + ROW_HEIGHT - TEXT_OFFSET_Y;
		
		g.drawString(name, x, y);
		
		y += dy;
		
		//scores - test for now
		String score = null; ///////////////////
		int num = (scoresToShow == ROWS)? totalTurns-ROWS+1 : 1;
		String s1;	
		final int xend = x2 + scoreW -2;
		final int dx = GAP + ROW_HEIGHT -2;
		
		for(int i=0; i < scoresToShow; i++, num++)
		{
			g.setColor(white);
			s1 = Integer.toString(num);
			x1 = dx - GLib.fmBoldFont.stringWidth(s1);
			g.drawString(s1, x1, y);
			
			score = Integer.toString(scores[i]); 
			g.setColor(black);
			x1 = xend -GLib.fmBoldFont.stringWidth(score);
			g.drawString(score, x1, y);
			
			y += dy;
			
		}
		
		//total		
		x = left + (top_lbl_w - GLib.fmBoldFont.stringWidth(TOTAL))/2;
		g.drawString(TOTAL, x, scoresBot);		
				
		score = Integer.toString(totalScore);
		x = left + (top_lbl_w - GLib.fmBoldFont.stringWidth(score))/2;
		g.drawString(score, x, scoresBot + dy);		
	}

	public void doLayout() {
		final int top = GLib.getTopPartBot();
		final int botH = GLib.getBotPartHeight();
		
		//bottom buttons - butRoll centered
		final Dimension sz = butRoll.getSize();
		int x = (this.bounds.width - sz.width) / 2;
		int y = bounds.height - (botH - sz.height)/2 - sz.height;
		butRoll.setLocation(x, y);
		x += sz.width + BUT_GAP;
		butQuit.setLocation(x, y);		
		
		//side panels
		side_panel_top = top+GAP;
		side_panel_width = (bounds.width - (DiceBox.getWidth(DiceBox.TYPE_MED)*6 + GAP*9))/2;
		
		//center
		this.recTotalTurnScore = new Rectangle(side_panel_width +GAP*2, side_panel_top, 
				bounds.width - side_panel_width*2 - GAP*5 - butBank.getSize().width, butBank.getSize().height);
		
		butBank.setLocation(recTotalTurnScore.x + recTotalTurnScore.width +GAP, side_panel_top);
		butCheck.setLocation(butBank.getLocX(), butBank.getLocY());
		
		final int left = GAP*2 + side_panel_width;
		//to dice boxes
		x = left;
		
		y = recTotalTurnScore.y + recTotalTurnScore.height + GAP;
		int dx = DiceBox.getWidth(DiceBox.TYPE_MED) + GAP;
		for(int i =0; i < TOTAL_DICE; i++)
		{
			this.topDiceBoxes[i].setLocation(x, y);
			x += dx;	
		}
		
		//center of center
		y = y + dx;
		recSubTotal = new Rectangle(bounds.width -side_panel_width - GAP - dx*2, y,
				dx*2-GAP, ROW_HEIGHT*2);
		
		//dice rows
		//diceRows
		int cnt = diceRows.length;
		int cnt2;		
		
		y = recSubTotal.y;		
		//middle boxes
		
		dx = DiceBox.getWidth(DiceBox.TYPE_SMALL)+2;
		for(int i=0; i < cnt; i++)
		{
			cnt2 = diceRows[i].length;
			x = left;
			for(int j=0; j < cnt2; j++)
			{
				diceRows[i][j].setLocation(x, y);
				x += dx;
			}
			y += dx;
		}
		
		//dice to roll		
		dx = DiceBox.getWidth(DiceBox.TYPE_LG) + GAP;
		x = left;
		y = bounds.height - botH -dx;
		this.diceToRoll[0].setLocation(x, y);
		x += dx;
		this.diceToRoll[1].setLocation(x, y);
		x += dx;
		this.diceToRoll[2].setLocation(x, y);
		x = left + dx + 4;
		y -= dx;
		this.diceToRoll[3].setLocation(x, y);
		x += dx;
		this.diceToRoll[4].setLocation(x, y);
		y -= dx;
		this.diceToRoll[5].setLocation(x, y);
		
		final Dimension szMed = XButton.getButSize(XButton.TYPE_MED);
		setViewAllScoringBut(bounds.width - szMed.width - GAP ,bounds.height - botH -szMed.height);
	}

	public boolean processClick(Point pt) {
		//bank
		if(butBank.getClicked(pt))
		{
			processBankClick();
			repaint();
			return true;
		}
		else if(butRoll.getClicked(pt))
		{
			butRoll.setEnabled(false);
			doRoll();
			return true;
		}
		else if(butQuit.getClicked(pt))
		{		
			Controller c = Controller.getController();
			if(c!= null)
			{
				c.processQuit();
			}
			return true;			
		}
		else if(butCheck.getClicked(pt))
		{
			checkForScores();
			return true;
		}
		else if(checkViewAllScoringClick(pt))
		{
			//show Help screen with all dice combos
			
			return true;
		}
		else
		{
			//dice
			final int cnt = diceToRoll.length;
			for(int i= 0; i < cnt; i++)
			{
				if(diceToRoll[i].getClicked(pt))
				{
					if(diceToRoll[i].getDiceValue() >0)
					{
						processBotDiceClick(i);
					}
					return true;
					
				}
			}
			for(int i= 0; i < cnt; i++)
			{
				if(topDiceBoxes[i].getClicked(pt))
				{
					if(topDiceBoxes[i].getDiceValue() >0 )
					{
						processTopDiceClick(i);
					}
					return true;
				}
			}
		}
		return false;
	}
	
	public void processSateChange(final int newState, final int oldState) {
		switch(newState)
		{
		case Game.STATE_BANK:
			if(!butBank.getVisible())
			{
				butBank.setVisible(true);
				butBank.setEnabled(true);
				butCheck.setVisible(false);
				butCheck.setEnabled(false);				
			}
			break;
		case Game.STATE_CHECK:				
			butBank.setVisible(false);
			butBank.setEnabled(false);
			butCheck.setVisible(true);
			butCheck.setEnabled(true);
			repaint();
			break;
		case Game.STATE_ROLL:
			butBank.setVisible(false);
			butBank.setEnabled(false);
			butCheck.setVisible(true);
			butCheck.setEnabled(false);
			butRoll.setEnabled(true);
			Player pl = game.getCurPlayer();
			if(pl!= null)
			{
				pl.setCurCheckScore(0);
			}
			repaint();
			break;
		case Game.STATE_FARKLE_ON_ROLL:
			butBank.setVisible(false);
			butBank.setEnabled(false);
			butCheck.setVisible(false);
			butCheck.setEnabled(false);
			break;
		}
		
	}
	
	
	
	private void doRoll()
	{	
		int cnt=0;
		for(int i=0; i < TOTAL_DICE; i++)
		{
			if(diceToRoll[i].getDiceValue() >0)
			{
				cnt++;
			}
		}
		if(cnt ==0) cnt = TOTAL_DICE;
		int[] roll = game.rollDice(cnt);
		
		if(roll != null)
		{
			
			for(int i=0; i < TOTAL_DICE; i++)
			{
				diceToRoll[i].setDiceValue(roll[i]);
				
			}
		}		
		
		for(int p=0; p < TOTAL_DICE; p++)
		{
			diceToRoll[p].setShow(true);				
		}
		
		//TEST
//		diceToRoll[0].setDiceValue(1);
//		diceToRoll[1].setDiceValue(1);
//		diceToRoll[2].setDiceValue(1);
//		diceToRoll[3].setDiceValue(1);
//		diceToRoll[4].setDiceValue(1);
//		diceToRoll[5].setDiceValue(1);
		
		//check for Farkle
		if(!getHaveScore())
		{
			controller.setGameState(Game.STATE_FARKLE_ON_ROLL);
			Controller c = Controller.getController();
			time_farkle_on_roll_start = c.getTime();
			//Farkle - TODO - we have to show the values to player before Farkling
			//TODO - display dice to the user for 2 secs
			repaint();
			return;
		}		
		
		controller.setGameState(Game.STATE_CHECK);
		repaint();
	}
	
	private void doFarkleFromRoll()
	{
//		for(int j=0; j < Controller.TOTAL_DICE; j++)
//		{
//			this.diceToRoll[j].setDiceValue(0);
//		}
		Player pl = game.getCurPlayer();
		doFarkle(pl);	
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.olmatech.kindle.farkle.screens.BaseScreen#processTimeTick(long)
	 */
	public void processTimeTick(long tm) {
		if(time_farkle_on_roll_start <=0) return;
		if(time_farkle_on_roll_start + FARKLE_ON_ROLL_TIME <= tm)
		{
			time_farkle_on_roll_start =0;
			doFarkleFromRoll();			
		}
	}

	//checking scores on dice in top row
	private void checkForScores()
	{
		for(int i=0; i < TOTAL_DICE; i++)
		{
			diceArray[i] = this.topDiceBoxes[i].getDiceValue();
		}	
		
		Player pl = game.getCurPlayer();
		if(pl != null)
		{
			pl.setCurCheckScore(0);
		}
		
		ScoreResult score = Scores.checkForScores(diceArray);	
				
		if(score != null)
		{
			Log.d("checkForScores roll=" + pl.getCurRoll());
			//draw results			
			int[] res = score.getCombosScoreIndexes();
			ScoreCombo c;
			int k;			
			if(res != null)
			{
				int[] resultDiceRow = new int[TOTAL_DICE];
				Arrays.fill(resultDiceRow, 0);
				int resIndex = 0;
				int ln = res.length;
				int totalScore=0;
				//Log.d("checkForScores score.getCombosScoreIndexes() arr ln=" + ln);
				for(int j=0; j < ln; j++)
				{
					c = Scores.combos[res[j]];	
					totalScore += c.getScore();
					int[] vals = c.getArrayOfSingleDice();										
					if(vals == null) continue;
					k = vals.length;
					//Log.d("SCORE ind = " + res[j] + "  ln=" + k);
					for(int m =0; m < k; m++)
					{
						if(vals[m] == 0)
						{
							break;
						}
						resultDiceRow[resIndex] = vals[m];//diceRows[row][ind].setDiceValue(vals[m]); //////////////////////////////
						resIndex++;
						//Log.d("Set result to " + vals[m]);				
						//remove from top array
						for(int p=0; p < TOTAL_DICE; p++)
						{
							if(topDiceBoxes[p].getDiceValue()  == vals[m])
							{
								topDiceBoxes[p].setDiceValue(0); /////////////////////////
								//Log.d("Remove from top " + vals[m]);
								break;
							}
						}
						
					}
					
				}//for
				
//				for(int i= ind; i < rowLn; i++)
//				{
//					diceRows[row][i].setDiceValue(0);
//				}
				
				//remove from top row and roll again
				int val;
				for(int p=0; p < TOTAL_DICE; p++)
				{
					val = topDiceBoxes[p].getDiceValue();
					if(val >0)
					{
						for(int q=0; q < TOTAL_DICE; q++)
						{
							if(diceToRoll[q].getDiceValue() ==0)
							{
								diceToRoll[q].setDiceValue(val);
								topDiceBoxes[p].setDiceValue(0);
								break;
							}
						}
					}
				}
				
				//display on top row
				for(int p =0; p < TOTAL_DICE; p++)
				{
					topDiceBoxes[p].setDiceValue(resultDiceRow[p]);
				}
				
				
				if(pl != null)
				{
					pl.setCurCheckScore(totalScore);
					//Log.d("Check score -setCurCheckScore " +  totalScore);
				}
				
				controller.setGameState(Game.STATE_BANK);
							
			}
			else
			{
				//no score - set scores to 0
				//TODO - Farkle?
				doFarkle(pl);
				
			}			
			
		}
		else  //no scores
		{
			for(int j=0; j < TOTAL_DICE; j++)
			{
				this.topDiceBoxes[j].setDiceValue(0);
			}
			
			doFarkle(pl);
		}		
		
		repaint();
	}
	
	private void doFarkle(Player pl)
	{
		if(this.showYesNowDlg("No score. Farkle?", "Farkle"))
		{
			pl.farkleTheTurn();
			clearDiceToRoll(true);
			hideResultBoxes();
			clearTopDice();
			controller.setGameState(Game.STATE_ROLL);
		}	
		else
		{
			controller.setGameState(Game.STATE_CHECK);
		}
	}
	
	private void clearDiceToRoll(final boolean clear)
	{
		if(clear)
		{
			for(int p=0; p < TOTAL_DICE; p++)
			{
				diceToRoll[p].setDiceValue(0);				
			}
		}
		else
		{
			for(int p=0; p < TOTAL_DICE; p++)
			{
				diceToRoll[p].setShow(false);				
			}
		}
		
	}
	
	
	private void processBankClick()
	{
		//TODO - write scores, update player
		Player pl = game.getCurPlayer();
		int row = pl.getCurRoll();
		final int rowLn = diceRows[row].length;
		
		int score = pl.getCurCheckScore();
		pl.setRollScore(score);
		pl.setCurCheckScore(0);
		
		//see if this is the last roll in this turn
		if(pl.isLastRoll())
		{
			//set next turn
			pl.completeTurn();
			for(int i=0; i < Player.MAX_ROLLS; i++)
			{
				for(int j =0; j < diceRows[i].length; j++)
				{
					diceRows[i][j].setDiceValue(0);
					diceRows[i][j].setVisible(false);
				}
				for(int j =0; j < TOTAL_DICE; j++)
				{
					topDiceBoxes[j].setDiceValue(0);
				}
			}
			this.clearDiceToRoll(true);
		}
		else
		{
			pl.incrementCurRollNum();
			int j =0, val;
			for(int i =0; i < TOTAL_DICE; i++)
			{
				val = this.topDiceBoxes[i].getDiceValue();
				if(val >0 )
				{
					if(j < rowLn)
					{
						diceRows[row][j].setDiceValue(val);
						j++;
					}
					topDiceBoxes[i].setDiceValue(0);
				}				
			}//for
			//set row visible
			for(int i=0; i < rowLn; i++)
			{
				diceRows[row][i].setVisible(true);
			}
			clearDiceToRoll(false);
		}	
		
		controller.setGameState(Game.STATE_ROLL);
	}
	
	
	
	private void setFirstRoll()
	{
		Player pl = game.getCurPlayer();
		if(pl != null)
		{
			pl.setCurRoll(0);
		}
	}
	
	private void processBotDiceClick(final int ind)
	{
		//check if dice has number and display it on top
		//then re-calculate combination and score
		final int val = diceToRoll[ind].getDiceValue();
		 diceToRoll[ind].setDiceValue(0);
		if(val >0)
		{
			final int cnt = topDiceBoxes.length;
			for(int i=0; i < cnt; i++)
			{
				if(topDiceBoxes[i].getDiceValue()==0)
				{
					topDiceBoxes[i].setDiceValue(val);
					break;
				}
			}//for
			//recalc - TODO
			
			controller.setGameState(Game.STATE_CHECK);
			repaint();  //TODO - optimize
			
			
			
		}//val
	}
	
	private void processTopDiceClick(final int ind)
	{
		//put dice on bot row
		for(int i=0; i < TOTAL_DICE; i++)
		{
			if(this.diceToRoll[i].getDiceValue() ==0)
			{
				diceToRoll[i].setDiceValue(this.topDiceBoxes[ind].getDiceValue());
				topDiceBoxes[ind].setDiceValue(0);
				break;
			}
		}
		
		controller.setGameState(Game.STATE_CHECK);
		repaint();  //TODO - optimize
	}

	//////////////// TEST
	private void test()
	{
		ScoreCombo cin;
		ScoreResult score;
		for(int i =0; i < Scores.scoreDice[Scores.SIX].length; i++)
		{
			int comboInd = Scores.scoreDice[Scores.SIX][i];
			
			//cin = Scores.combos[i];
			cin = Scores.combos[comboInd];
			int[] singleDice = cin.getArrayOfSingleDice();
			score = Scores.checkForScores(singleDice);
			int[] res = score.getCombosScoreIndexes();
			if(res== null || res.length ==0)
			{
				Log.d(">>> TEST ERR : no result for dice combo " + comboInd + " - " + cin.toString());
			}
			else if(res.length >1)
			{
				Log.d(">>> TEST: for combo " + comboInd  + " - " +  cin.toString() + " results:");
				StringBuffer buf = new StringBuffer();
				for(int j=0; j < res.length; j++)
				{
					buf.append("#");
					buf.append(res[j]);
					buf.append(" - ");
					buf.append(Scores.combos[res[j]].toString());
					buf.append(" - ");
				}
				Log.d(buf.toString());
			}
			else
			{
				if(res[0] != comboInd)
				{
					Log.d(">>> TEST ERR for combo " + comboInd + " result =" + res[0]);
				}
				else
				{
					Log.d("TEST OK for combo " + comboInd + " result =" + res[0]);
				}
				
			}			
			
		}
		
		this.showMsgDialog("Test done", "test");
	}

	private boolean getHaveScore()
	{
		int[] arrDice = new int[TOTAL_DICE];
		for(int i=0; i < TOTAL_DICE; i++)
		{
			arrDice[i] = this.diceToRoll[i].getDiceValue();
		}	
		
		return Scores.checkHaveScore(arrDice);	
	}
	
}
