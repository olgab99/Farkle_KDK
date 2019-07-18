package com.olmatech.kindle.farkle.screens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import com.olmatech.kindle.farkle.GLib;

//to draw dice box of given size = always square
public class DiceBox {
	
	public final static int TYPE_SMALL = 0;
	public final static int TYPE_MED =1;
	public final static int TYPE_LG=2;
	public final static int TYPE_EXTRASMALL =3;
	
	private final static int SZ_SMALL = 32;
	private final static int SZ_MED = 48;
	private final static int SZ_LG= 100;
	private final static int SZ_EXTRASMALL = 12;
	
	private final static Color BLACK = GLib.BLACK;
	private final static Color LGRAY = GLib.LT_GRAY;
	private final static Color DGRAY = GLib.DARK_GRAY;
	
	private int boxType;
	//private Point location;
	private Rectangle bounds;
	private boolean visible = true;
	private boolean show = true; // false if dice have value, but we can't show it
	
	private int dice =0;  //num on dice, 0 - no dice
	
	public DiceBox(final int tp)
	{
		boxType = tp;
	}
	
	public void setLocation(final int x, final int y)
	{
		switch(boxType)
		{
		case TYPE_SMALL:
			bounds = new Rectangle(x,y,SZ_SMALL, SZ_SMALL);
			break;			
		case TYPE_MED:
			bounds = new Rectangle(x,y,SZ_MED, SZ_MED);
			break;	
		case TYPE_LG:
			bounds = new Rectangle(x,y,SZ_LG, SZ_LG);
			break;
		case TYPE_EXTRASMALL:
			bounds = new Rectangle(x,y,SZ_EXTRASMALL, SZ_EXTRASMALL);
			break;	
		}
	}
	
	public void setDiceValue(final int val)
	{
		dice = val;
	}
	
	public int getDiceValue()
	{
		return dice;
	}
	
	public void setVisible(final boolean val)
	{
		visible = val;
	}
	
	public boolean getVisible()
	{
		return visible;
	}
	
	public static int getWidth(final int tp)
	{
		switch(tp)
		{
		case TYPE_SMALL:
			return SZ_SMALL;			
		case TYPE_MED:
			return SZ_MED;
		case TYPE_EXTRASMALL:
			return SZ_EXTRASMALL;
		default:
			return SZ_LG;
		}
	}
	
	private int getWidth()
	{
		switch(boxType)
		{
		case TYPE_SMALL:
			return SZ_SMALL;			
		case TYPE_MED:
			return SZ_MED;
		case TYPE_EXTRASMALL:
			return SZ_EXTRASMALL;
		default:
			return SZ_LG;
		}
	}
	
	
	public void draw(Graphics g)
	{
		if(!visible) return;
		g.translate(bounds.x, bounds.y);
		switch(boxType)
		{
		case TYPE_SMALL:
			g.setColor(LGRAY);
			g.fillRoundRect(0,0, SZ_SMALL, SZ_SMALL, 5, 5);
			g.setColor(BLACK);
			g.drawRoundRect(0,0, SZ_SMALL, SZ_SMALL, 5, 5);
			break;
		case TYPE_MED:
			g.setColor(LGRAY);
			g.fillRoundRect(0,0, SZ_MED, SZ_MED, 10, 10);
			g.setColor(BLACK);
			g.fillRoundRect(2,2, SZ_MED-4, SZ_MED-4, 10, 10);
			break;
		case TYPE_LG:
			g.setColor(DGRAY);
			g.fillRoundRect(0,0, SZ_LG, SZ_LG, 20,20);
			g.setColor(LGRAY);
			g.drawRoundRect(2,2, SZ_LG-4, SZ_LG-4, 20,20);
			break;
		case TYPE_EXTRASMALL:
			g.setColor(DGRAY);
			g.fillRoundRect(0,0, SZ_EXTRASMALL, SZ_EXTRASMALL, 4,4);
			break;
		}
		
		if(dice >0 && show)
		{
			Dice.drawDice(g, 0, 0, dice, boxType, getWidth());
		}
		
		g.translate(-bounds.x, -bounds.y);
	}
	
	
	public boolean getClicked(Point pt)
	{
		return bounds.contains(pt);
	}
	
	public void setShow(final boolean val)
	{
		show = val;
	}
	
	public boolean getShow()
	{
		return show;
	}
	

}
