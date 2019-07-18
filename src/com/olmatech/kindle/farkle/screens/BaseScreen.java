package com.olmatech.kindle.farkle.screens;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;

import javax.swing.JLabel;

import com.amazon.kindle.kindlet.ui.KOptionPane;
import com.olmatech.kindle.farkle.Controller;
import com.olmatech.kindle.farkle.GLib;
import com.olmatech.kindle.farkle.Game;
import com.olmatech.kindle.farkle.ScoreCombo;
import com.olmatech.kindle.farkle.Scores;
import com.olmatech.kindle.farkle.ui.GamePanel;
import com.olmatech.kindle.farkle.ui.XButton;

//base class for screens
public abstract class BaseScreen {
	
	protected Rectangle bounds;
	protected static final int BUT_GAP = 10; //pixels
	protected final static int GAP = 10;
	
	protected static Controller controller = Controller.getController();		
	protected GamePanel parent;		
	protected boolean isLayoutDone = false;
	
	//common sizes
	protected final Color black = GLib.BLACK;
	protected final Color white = GLib.WHITE;
	protected final Color ltgray = GLib.LT_GRAY;	
	protected final static int ROW_HEIGHT = GLib.LABEL_HEIGHT;
	protected final static int TEXT_OFFSET_Y =6;
	protected final static int ROW_GAP = 4;
	
	protected XButton butViewAllScoring; 
	
	protected Game game;
	
	protected Image imgBackdrop=null; //main image on the panel
	protected String imgBackdropResourceName=null; // the name of the above or null
	
	
	public BaseScreen(final int x, final int y, final int w, final int h, final GamePanel p)
	{
		parent =p;
		setBounds(x,y,w,h);
		game = Game.getGame();		
	}
	
	public BaseScreen(final int x, final int y, final int w, final int h, final GamePanel p, final String backdropImgName)
	{
		this(x,y,w,h,p);
		imgBackdropResourceName = backdropImgName;
	}
	
	public void repaint()
	{
		if(parent != null)
		{
			parent.repaint();
		}
	}
	
	public void draw(Graphics g){
		if(!isLayoutDone)
		{
			doLayout();			
		}
		if(imgBackdropResourceName != null)
		{
			if(imgBackdrop == null)
			{
				this.loadBackdropImg();
				return;
			}
			
			g.drawImage(imgBackdrop, 0, 0, null);
		}
		else
		{
			g.setColor(GLib.WHITE);
			g.fillRect(0, 0, bounds.width, bounds.height);	
			GLib.drawTopScreenPart(g, bounds.width, bounds.height, game.getTopScreenTitle(), JLabel.CENTER);		
			GLib.drawBotScreenPart(g, bounds.width, bounds.height);	
		}			
	}
	
	public void setBounds(final int x, final int y, final int w, final int h)
	{
		bounds = new Rectangle(x,y,w,h);
	}
	
	public Rectangle getBounds()
	{
		return bounds;
	}
	
	public abstract void doLayout();
	
	public abstract void processSateChange(final int newState, final int oldState);
	
	public boolean showYesNowDlg(final String msg, final String title)
	{
		if(parent == null) return false;
		final int choice = KOptionPane.showConfirmDialog(parent, msg, title,
                KOptionPane.NO_YES_OPTIONS);
        switch (choice) {
        case KOptionPane.YES_OPTION:        	 
        	return true;     
        default: return false;             
        }
		
	}
	
	public void showMsgDialog(final String msg, final String title)
	{
		if(parent == null) return;
		
		KOptionPane.showMessageDialog(parent, msg, title);		
	}
	
	protected void showInputDlg(final String title, final String msg, final String initVal)
	{
		if(parent == null) return;
		 String in = KOptionPane.showInputDialog(parent, msg, title, 
				 KOptionPane.PLAIN_MESSAGE, KOptionPane.CANCEL_SAVE_OPTIONS, initVal);		   
		 processStrInput(in);
	}
	
	protected void processStrInput(final String in)
	{
		//empty 
	}
	
	//ret true if processed
	public abstract boolean processClick(final Point pt);
	
	protected void setViewAllScoringBut(final int x, final int y)
	{
		butViewAllScoring = new XButton(GLib.TITLES.getString("viewall"), null,  XButton.TYPE_MED);
		butViewAllScoring.setLocation(x, y);
	}
	
	protected boolean checkViewAllScoringClick(final Point pt)
	{
		if(butViewAllScoring == null) return false;
		if(butViewAllScoring.getClicked(pt))
		{
			//show all scoring screen
			Controller controller = Controller.getController();
			controller.processShowAllScoringClick();
			return true;
		}
		return false;
	}
	
	public void processTimeTick(final long tm){}
	public void resetGame(){}
	
	protected void drawScoresOnRight(Graphics g, final int panelWidth, final Point ptTop)
	{
		g.translate(ptTop.x, ptTop.y);
		//draw title
		String s = GLib.TITLES.getString("scoring");
		g.setFont(GLib.boldFont);
		g.setColor(ltgray);
		
		g.fillRect(0, 0, panelWidth, ROW_HEIGHT);
		int x = (panelWidth - GLib.fmBoldFont.stringWidth(s))/2;
		int yTxt = ROW_HEIGHT - TEXT_OFFSET_Y;
		g.setColor(black);
		g.drawString(s, x, yTxt);		
		
		GLib.drawScoringImg(g, 0, ROW_HEIGHT + ROW_GAP, panelWidth);
		
		g.translate(-ptTop.x, -ptTop.y);
	}
	
	//to load backdrop image
		protected void loadBackdropImg()
		{	
			if(imgBackdropResourceName == null) return;
			final Toolkit toolkit = Toolkit.getDefaultToolkit();
			ImageObserver myObserver = new ImageObserver() {	      

				public boolean imageUpdate(Image image, int flags, int x, int y, int width, int height) {
					if ((flags & HEIGHT) != 0)
					{
				          //Log.d("Image height = " + height);
					}
				        if ((flags & WIDTH) != 0)
				        {
				        	//Log.d("Image width = " + width);
				        }
				        if ((flags & FRAMEBITS) != 0)
				        {
				        	//Log.d("Another frame finished.");
				        }
				        if ((flags & SOMEBITS) != 0)
				        {
				        	//Log.d("Image section" );
				        }
				        if ((flags & ALLBITS) != 0)
				        {
				        	EventQueue.invokeLater( new Runnable(){
								public void run() {
									repaint();	
									
								}				
							});			
				        }			        	
				        if ((flags & ABORT) != 0)
				        {
				        	//Log.err("Image load aborted...");
				        }
				        return true;				
				}

				
			    };			
			    
			    imgBackdrop = toolkit.createImage(getClass().getResource(imgBackdropResourceName));
			    toolkit.prepareImage(imgBackdrop, -1, -1, myObserver);			    
		}
		
	//draw right score part
//	protected void TESTdrawScoresOnRight(Graphics g, final int panelWidth, final Point ptTop)
//	{
//		g.translate(ptTop.x, ptTop.y);
//		//draw title
//		String s = GLib.TITLES.getString("scoring");
//		g.setFont(GLib.boldFont);
//		g.setColor(ltgray);
//		
//		g.fillRect(0, 0, panelWidth, ROW_HEIGHT);
//		
//		//fill combos rect
//		final int dy = ROW_HEIGHT + ROW_GAP;
//		int y =dy;		
//		ScoreCombo[] combo = Scores.getScoresToDraw();
//		final int cnt = combo.length;
//		int h =dy * cnt;
//		
//		g.fillRect(ptTop.x, y, panelWidth, h);
//		
//		int x = (panelWidth - GLib.fmBoldFont.stringWidth(s))/2;
//		int yTxt = ROW_HEIGHT - TEXT_OFFSET_Y;
//		g.setColor(black);
//		g.drawString(s, x, yTxt);		
//		
//		yTxt += dy;
//		y += ROW_GAP;
//		
//		x = ROW_GAP;
//		//draw combos
//		int diceCnt;
//		int dice_width = DiceBox.getWidth(DiceBox.TYPE_EXTRASMALL);
//		final int dd = dice_width + ROW_GAP;
//		int num;
//		g.setColor(black);
//		
//		for(int i =0; i < cnt; i++)
//		{
//			for(int j=0; j < Controller.TOTAL_DICE; j++)
//			{
//				diceCnt = combo[i].getDiceAt(j);
//				if(diceCnt <=0) continue;
//				num = j+1;
//				for(int k = 0; k < diceCnt; k++)
//				{
//					Dice.drawExtrasmallDiceAtLocation(g, num, x, y);
//					
//					//g.drawRect(x, y, dice_width, dice_width);
//					
//					x += dd;
//					
//					
//				}	
//				//draw text
//				s = Integer.toString(combo[i].getScore());
//				g.drawString(s, x, yTxt);
//				
//				x = ROW_GAP;
//				y += dy;
//				yTxt += dy;
//				
//			}//combo
//		}//dice combos
//		
//		g.translate(-ptTop.x, -ptTop.y);
//	}

}
