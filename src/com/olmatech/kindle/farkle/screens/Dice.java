package com.olmatech.kindle.farkle.screens;

import java.awt.Graphics;

import com.olmatech.kindle.farkle.GLib;

//draw one dice
public class Dice {
	
	

	
	public Dice(final int w, final int h)
	{
		
	}
	
	public static void drawDice(Graphics g, final int x, final int y, final int num, final int tp, final int w)
	{
		int DOT;
		int x1, x2, x3, w2 = 0, left=0, r=10;
		switch(tp)
		{
		case DiceBox.TYPE_SMALL:  //32x32
			DOT = 6;
			x1=6;
			x2=14;
			x3=22;
			w2 = w -4;
			left = 2;			
			break;
		case DiceBox.TYPE_MED: //48x48 - 5,12,24  + 6
			DOT = 7;
			x1=11;
			x2=21;
			x3=30;
			w2 = w-12;
			left =6;			
			r = 10;
			break;
		case DiceBox.TYPE_LG:  //92x92 - 12,36,60 +6
			DOT = 16;
			x1=18;
			x2=42;
			x3=66;
			w2 = w-8;
			left =4;			
			r = 20;
			break;
		case DiceBox.TYPE_EXTRASMALL:
			DOT = 2;
			x1=2;
			x2=5;
			x3=8;
			w2 = w;
			left =x;			
			r = 4;			
			break;
		default: return;
		}
		
		//12 , 30, 48		
		g.setColor(GLib.WHITE);
		g.fillRoundRect(left, left, w2, w2,r,r);
		g.setColor(GLib.BLACK);
		g.drawRoundRect(left, left, w2, w2, r,r);
		
		switch(num)
		{		
		case 1:
			g.fillOval(x2, x2, DOT, DOT);
			break;
		case 2:
			g.fillOval(x1, x3, DOT, DOT);
			g.fillOval(x3, x1, DOT, DOT);
			break;
		case 3:
			g.fillOval(x1, x3, DOT, DOT);
			g.fillOval(x3, x1, DOT, DOT);
			g.fillOval(x2, x2, DOT, DOT);
			break;
		case 4:
			g.fillOval(x1, x1, DOT, DOT);
			g.fillOval(x1, x3, DOT, DOT);
			g.fillOval(x3, x3, DOT, DOT);
			g.fillOval(x3, x1, DOT, DOT);
			break;
		case 5:
			g.fillOval(x1, x1, DOT, DOT);
			g.fillOval(x1, x3, DOT, DOT);
			g.fillOval(x3, x3, DOT, DOT);
			g.fillOval(x3, x1, DOT, DOT);
			g.fillOval(x2, x2, DOT, DOT);
			break;
		case 6:
			g.fillOval(x1, x1, DOT, DOT);
			g.fillOval(x1, x3, DOT, DOT);
			g.fillOval(x3, x3, DOT, DOT);
			g.fillOval(x3, x1, DOT, DOT);	
			g.fillOval(x1, x2, DOT, DOT);
			g.fillOval(x3,x2, DOT, DOT);
			break;
		default: break;
		}
	}
	
	public static void drawSmallDiceAtLocation(Graphics g, final int num, final int x, final int y)
	{
		final int DOT = 6; //2;
		final int x1=6; //2;
		final int x2=14; //5;
		final int x3=22; //8;
		final int w2 = DiceBox.getWidth(DiceBox.TYPE_SMALL);
					
		final int r = 10;	
		
		g.translate(x, y);
		
		g.setColor(GLib.WHITE);
		g.fillRoundRect(0, 0, w2, w2,r,r);
		g.setColor(GLib.BLACK);
		g.drawRoundRect(0, 0, w2, w2, r,r);
		
		switch(num)
		{		
		case 1:
			g.fillOval(x2, x2, DOT, DOT);
			break;
		case 2:
			g.fillOval(x1, x3, DOT, DOT);
			g.fillOval(x3, x1, DOT, DOT);
			break;
		case 3:
			g.fillOval(x1, x3, DOT, DOT);
			g.fillOval(x3, x1, DOT, DOT);
			g.fillOval(x2, x2, DOT, DOT);
			break;
		case 4:
			g.fillOval(x1, x1, DOT, DOT);
			g.fillOval(x1, x3, DOT, DOT);
			g.fillOval(x3, x3, DOT, DOT);
			g.fillOval(x3, x1, DOT, DOT);
			break;
		case 5:
			g.fillOval(x1, x1, DOT, DOT);
			g.fillOval(x1, x3, DOT, DOT);
			g.fillOval(x3, x3, DOT, DOT);
			g.fillOval(x3, x1, DOT, DOT);
			g.fillOval(x2, x2, DOT, DOT);
			break;
		case 6:
			g.fillOval(x1, x1, DOT, DOT);
			g.fillOval(x1, x3, DOT, DOT);
			g.fillOval(x3, x3, DOT, DOT);
			g.fillOval(x3, x1, DOT, DOT);	
			g.fillOval(x1, x2, DOT, DOT);
			g.fillOval(x3,x2, DOT, DOT);
			break;
		default: break;
		}
		g.translate(-x, -y);
	}
	
	private void drawDots(Graphics g, final int num)
	{
		//50 x 50
		final int DOT = 16;
		switch(num)
		{		
		case 1:
			g.fillOval(30, 30, DOT, DOT);
			break;
		case 2:
			g.fillOval(12, 48, DOT, DOT);
			g.fillOval(48, 16, DOT, DOT);
			break;
		case 3:
			g.fillOval(12, 48, DOT, DOT);
			g.fillOval(48, 16, DOT, DOT);
			g.fillOval(30, 30, DOT, DOT);
			break;
		case 4:
			g.fillOval(12, 12, DOT, DOT);
			g.fillOval(12, 48, DOT, DOT);
			g.fillOval(48, 48, DOT, DOT);
			g.fillOval(48, 12, DOT, DOT);
			break;
		case 5:
			g.fillOval(12, 12, DOT, DOT);
			g.fillOval(12, 48, DOT, DOT);
			g.fillOval(48, 48, DOT, DOT);
			g.fillOval(48, 12, DOT, DOT);
			g.fillOval(30, 30, DOT, DOT);
			break;
		case 6:
			g.fillOval(12, 12, DOT, DOT);
			g.fillOval(12, 48, DOT, DOT);
			g.fillOval(48, 48, DOT, DOT);
			g.fillOval(48, 12, DOT, DOT);	
			g.fillOval(12, 30, DOT, DOT);
			g.fillOval(48,30, DOT, DOT);
			break;
		default: break;
		}
	}

}
