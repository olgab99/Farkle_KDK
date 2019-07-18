package com.olmatech.kindle.farkle.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import com.amazon.kindle.kindlet.ui.KindletUIResources;
import com.olmatech.kindle.farkle.GLib;
import com.olmatech.kindle.farkle.Log;

//button drawn on the screen
public class XButton {	
	private final static Font butFont = GLib.buttonFont;
	
	private final static Color BLACK = GLib.BLACK;
	private final static Color WHITE = GLib.WHITE;
	private final static Color GRAY = GLib.DARK_GRAY;
	
	private final static int RAD = 20;
		
	public final static int TYPE_SQUARE=0;
	public final static int TYPE_LONG=1;
	public final static int TYPE_SMALL =2;
	public final static int TYPE_MED =3;
	public final static int TYPE_CUSTOM=4;
	
	//tbd
	private final static Dimension TYPE_SQUARE_SIZE = new Dimension(120,120);
	private final static Dimension TYPE_LONG_SIZE = new Dimension(200,50);
	private final static Dimension TYPE_MED_SIZE = new Dimension(100,50);
	private final static Dimension TYPE_SMALL_SIZE = new Dimension(50,30);
	
	private int but_type;
	private Dimension but_size;
		
	private String titleLine1;
	private String titleLine2;
	private Rectangle bounds;	
	private boolean selected = false;
	
	private Point ptLine1;;
	private Point ptLine2 = null;
	
	private boolean enabled = true;
	private boolean visible = true;
	
		
	public XButton(final String txtLn1, final String txtLn2, final int tp)
	{
		titleLine1 = txtLn1;
		but_type = tp;
		titleLine2 = txtLn2;	
		
		if(GLib.fmButton == null) return;
		
		setSizes();
	}
	
	public XButton(final String txtLn1, final String txtLn2, final int x, final int y, final int tp)
	{
		this(txtLn1, txtLn2, tp);
		setLocation(x,y);
		
	}
	
	public XButton(final String txtLn1, final int x, final int y, final int tp)
	{
		this(txtLn1, null, x,y, tp);		
	}
	
	private void setSizes()
	{
		int line1y, line2y = 0;
		int but_w;
		//textX = (WIDTH - GLib.fmButton.stringWidth(title))/2;
		switch(but_type)
		{
		case TYPE_SQUARE:
			but_size=new Dimension (TYPE_SQUARE_SIZE.width, TYPE_SQUARE_SIZE.height);
			int half = TYPE_SQUARE_SIZE.height/2;
			line1y = half - 6; //(half -GLib.fmButton.getAscent())/2;
			line2y = half + GLib.fmButton.getAscent() + 6;		
			but_w= TYPE_SQUARE_SIZE.width;
			break;
		case TYPE_SMALL:
			but_size=new Dimension (TYPE_SMALL_SIZE.width,TYPE_SMALL_SIZE.height);
			line1y = TYPE_SMALL_SIZE.height - (TYPE_SMALL_SIZE.height -GLib.fmButton.getAscent())/2 -2;
			but_w = TYPE_SMALL_SIZE.width;
			break;		
		case TYPE_LONG:
			but_size=new Dimension (TYPE_LONG_SIZE.width,TYPE_LONG_SIZE.height);
			line1y = TYPE_LONG_SIZE.height - (TYPE_LONG_SIZE.height -GLib.fmButton.getAscent())/2 -2;
			but_w= TYPE_LONG_SIZE.width;
			break;			
		case TYPE_CUSTOM:
			but_size = new Dimension(GLib.fmButton.stringWidth(titleLine1) + 10, TYPE_LONG_SIZE.height);
			line1y = but_size.height - (but_size.height -GLib.fmButton.getAscent())/2 -2;
			but_w = but_size.width;
			break;
		default: // TYPE_MED:
			but_size=new Dimension (TYPE_MED_SIZE.width,TYPE_MED_SIZE.height);
			line1y = TYPE_MED_SIZE.height - (TYPE_MED_SIZE.height -GLib.fmButton.getAscent())/2 -2;
			but_w= TYPE_MED_SIZE.width;
			break;
		
		}
		
		
		if(titleLine1 != null)
		{
			int sz = GLib.fmButton.stringWidth(titleLine1);
			ptLine1 = new Point((but_w -sz)/2, line1y);
			//Log.d("Line1 x =" + ptLine1.x + "  sz=" + sz + "  titleLine1=" + titleLine1);
		}
		if(titleLine2 != null)
		{
			int sz = GLib.fmButton.stringWidth(titleLine2);
			ptLine2 = new Point((but_w -sz)/2, line2y);
		}
	}
	
	
	public void setLocation(final int x, final int y)
	{
		bounds = new Rectangle(x,y,but_size.width, but_size.height);
		//Log.d("But " + titleLine1 + " bounds.w=" + bounds.width);
	}
	
	public int getLocX()
	{
		return (bounds != null)? bounds.x : -1;
	}
	
	public int getLocY()
	{
		return (bounds != null)? bounds.y : -1;
	}
	
	public static Dimension getButSize(final int tp)
	{
		switch(tp)
		{
		case TYPE_SQUARE:
			return new Dimension(TYPE_SQUARE_SIZE.width, TYPE_SQUARE_SIZE.height);
		case TYPE_SMALL:
			return new Dimension(TYPE_SMALL_SIZE.width, TYPE_SMALL_SIZE.height);
		case TYPE_LONG:
			return new Dimension(TYPE_LONG_SIZE.width, TYPE_LONG_SIZE.height);
		case TYPE_MED:
			return new Dimension(TYPE_MED_SIZE.width, TYPE_MED_SIZE.height);
		default: return null;
		}
	}
	
	public Dimension getSize()
	{
		return but_size;
	}
	
	
	public boolean getClicked(Point pt)
	{
		if(!enabled || !visible) return false;
		return bounds.contains(pt);
	}
	
	public void draw(Graphics g)
	{
		if(!visible) return;
		if(ptLine1 == null)
		{
			setSizes();
		}
		
		g.translate(bounds.x, bounds.y);
		if(enabled)
		{
			g.setColor(BLACK);
		}
		else
		{
			g.setColor(GRAY);
		}
		
		if(selected)
		{
			g.drawRoundRect(0, 0, bounds.width, bounds.height, RAD, RAD);
			
		}
		else
		{
			g.fillRoundRect(0, 0, bounds.width, bounds.height, RAD, RAD);
			g.setColor(WHITE);
		}
		g.setFont(butFont);
		if(titleLine1 != null)
		{
			g.drawString(titleLine1, ptLine1.x, ptLine1.y);
		}
		if(titleLine2 != null)
		{
			g.drawString(titleLine2, ptLine2.x, ptLine2.y);
		}
		
		
		g.translate(-bounds.x, -bounds.y);
	}
	
	public void setSelected(final boolean val)
	{
		selected = val;
	}
	
	public void setEnabled(final boolean val)
	{
		enabled = val;
	}
	
	public boolean getEnabled()
	{
		return enabled;
	}
	
	public void setVisible(final boolean val)
	{
		visible = val;
	}
	
	public boolean getVisible()
	{
		return visible;
	}
	

}
