package com.olmatech.kindle.farkle.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javax.swing.JPanel;

import com.olmatech.kindle.farkle.Controller;
import com.olmatech.kindle.farkle.GLib;
import com.olmatech.kindle.farkle.Game;
import com.olmatech.kindle.farkle.Log;
import com.olmatech.kindle.farkle.ScoreCombo;
import com.olmatech.kindle.farkle.Scores;
import com.olmatech.kindle.farkle.screens.Dice;
import com.olmatech.kindle.farkle.screens.DiceBox;

//Note: added file

//to render pageble text on screen
public class TextHelper {
	private LinkedList textLines = null;		
	private int linesPerPage;
	private int dicePerPage=0;
	private int totalPages;
	private int page =1;
	private int lineH;
	private HelpSection[] helpText;	
	
	private Rectangle helpRect= null;
	
	private static int SCROLL_MSG_Y;
	private Font textFont;
	private Font boldFont;
	private Font titleFont;
	private FontMetrics fmRegFont;	
	private FontMetrics fmBoldFont;
	private FontMetrics fmTitleFont;
	
	private Dimension SIZE=null;
	
	private final static char TITLE_MARK='^';
	private final static char ASTART = '@'; //bold text 
	private final static String AEND = "*";
	public final static String SPACESTR = " ";
	public final static String PIPE = "|";
	public final static char PIPECHAR = '|';
	private int space_width;
	
	private Point location;
	
	private JPanel parent;
	private boolean formatedText = false;
	
	private int textForMode =-1; //mode of cur text
	final private static ResourceBundle HELP = ResourceBundle.getBundle("help");  //TODO - get correct
	
	private final Color black = GLib.BLACK;
	private final Color white = GLib.WHITE;
	private final Color ltgray = GLib.LT_GRAY;	
	private final static int ROW_HEIGHT = GLib.LABEL_HEIGHT;
	private final static int TEXT_OFFSET_Y =6;
	private final static int ROW_GAP = 4;
	
	
	public TextHelper(final JPanel p)
	{		
		parent = p;
			
		//TODO - set fonts
		textFont = GLib.regFont;
		boldFont = GLib.boldFont;
		titleFont = GLib.boldFont;
	
		fmTitleFont = parent.getFontMetrics(titleFont);
		fmRegFont = parent.getFontMetrics(textFont);			
		fmBoldFont = parent.getFontMetrics(boldFont);
		
		lineH = fmRegFont.getHeight();			
		space_width = fmRegFont.stringWidth(SPACESTR);		
		
		
	}
	
	public boolean isSizeSet()
	{
		if(SIZE == null) return false;
		return true;
	}
	
	public void setSizes(final int w, final int h, final int x, final int y, final int hMargin)
	{
		SIZE = new Dimension(w,h);
		location = new Point(x,y);	
		SCROLL_MSG_Y = SIZE.height - lineH;
		helpRect = new Rectangle(hMargin, fmTitleFont.getHeight(), SIZE.width - hMargin*2, SIZE.height-lineH*2);
		linesPerPage = helpRect.height / lineH - 2;		
	}
	
	public void setFormatted(final boolean val)
	{
		formatedText = val;
	}
	
	public void setText(final int forMode)
	{
		if(textForMode == forMode)
		{
			return;
		}
		
		
		if(textLines != null)
		{
			textLines.clear();
			textLines = null;
		}		
		
		textForMode = forMode;
		totalPages =0;
		page=1;
		
		
		//TODO - set proper text from Resources
		
		if(forMode == Game.MODE_HELP)//overview
		{
			helpText = new HelpSection[1];
			helpText[0] = new HelpSection();
			helpText[0].title = HELP.getString("helpt1");
			helpText[0].text = new StringBuffer();
			helpText[0].text.append(HELP.getString("help1"));	
			
		}
		else if(forMode == Game.MODE_ABOUT)
		{
			helpText = new HelpSection[1];
			helpText[0] = new HelpSection();
			helpText[0].title = HELP.getString("aboutt");
			helpText[0].text = new StringBuffer();
			helpText[0].text.append(HELP.getString("about"));	
		}
		else if(textForMode == Game.MODE_ALL_SCORES)
		{
			helpText=null;
			textLines = null;
			this.dicePerPage =0;
			page=1;
		}
		
		
		
	}
	
	public boolean haveText(final int forMode)
	{
		if(textLines == null)
		{
			return false;
		}
		if(forMode != textForMode)
		{
			this.clearText();
			return false;
		}
		return true;
	}
	
	public void draw(Graphics g)
	{
		g.translate(location.x, location.y);
		if(textForMode == Game.MODE_ALL_SCORES)
		{		
			Log.d("Helper - textForMode == Controller.MODE_ALL_SCORES");	
			
				//draw title
				final int panelWidth =SIZE.width;				
					String s = GLib.TITLES.getString("scoring");
					g.setFont(GLib.boldFont);		
					g.setColor(black);
					//fill combos rect
					final int diceh=DiceBox.getWidth(DiceBox.TYPE_SMALL);
					final int dy = diceh + ROW_GAP;
					int y =dy;		
					ScoreCombo[] combo = Scores.getScoresToDraw();
					final int cnt = combo.length;
					
					int x = (panelWidth - GLib.fmBoldFont.stringWidth(s))/2;
					int yTxt = ROW_HEIGHT - TEXT_OFFSET_Y;
					
					g.drawString(s, x, yTxt);						
					
					y += ROW_GAP;
					final int left = 60;
					x = left;
					yTxt = y + diceh - (diceh - GLib.fmBoldFont.getAscent())/2;
					//draw combos
					int diceCnt;
					int dice_width = DiceBox.getWidth(DiceBox.TYPE_SMALL);
					final int dd = dice_width + ROW_GAP;
					int num;
					g.setColor(black);
					
					final int boty= SIZE.height - lineH;
					int first, last;
					if(dicePerPage==0)
					{
						first =0;
						last = cnt;
					}
					else
					{
						first = (page-1)*dicePerPage;
						last = first + dicePerPage;
						if(last >cnt) last = cnt;
					}
					
					g.setFont(GLib.regFont);
					
					for(int i =first; i < last; i++)
					{
						//draw each dice row
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
						}//combo
						//draw text
						s = Integer.toString(combo[i].getScore());
						g.drawString(s, x, yTxt);
						
						x = left;
						y += dy;
						yTxt += dy;
						
						//see if we need to calculate pages
						if(this.dicePerPage == 0)
						{
							if((y+dy) >= boty)
							{
								dicePerPage = i+1;
								final int rem = cnt%dicePerPage;
								if(rem >0)
								{
									totalPages = cnt/dicePerPage +1;
								}
								else
								{
									totalPages = cnt/dicePerPage;
								}								
								break; //done
							}
						}						
					}//for - dice combos
					
					g.drawString("> Page " + page + " of " + totalPages + ". Swipe to scroll.", ROW_GAP, boty+lineH);
			
		}
		else
		{
			if(textLines == null)
			{
				textLines = new LinkedList();
				page =1;
				if(helpText == null)
				{
					textLines.add("Error rendering text. Please try again.");
				}
				else
				{					
					split(this.helpRect.width, helpText, textLines);
				}
				
				calcPages();
			}	
			if(this.formatedText)
			{
				this.drawFormattedText(g);
			}
			else
			{
				drawText(g, helpRect.x, true, textLines);
			}		
		
			if(totalPages >1)
			{
			//	g.setFont(MainPanel.fontGridSubscript);  //TODO
				g.drawString("> Swipe to scroll. Page " + page + " of " + totalPages, helpRect.x, SCROLL_MSG_Y);				
			}
		}		
		
		
		g.translate(-location.x, -location.y);
	}
	
	public Dimension getSize()
	{
		return SIZE;
	}
	
	public Point getLocation()
	{
		return location;
	}
	
	public void clearText()
	{
		helpText = null;
		
		if(textLines != null)
		{
			textLines.clear();
			textLines = null;
		}
		
	}
	
	public void cleanUp()
	{
		clearText();
		fmRegFont = null;
		fmTitleFont = null;
	}
	
	
	public void nextPage()
	{
		if(page < totalPages)
		{			
			page++;			
		}
	}
	
	public void prevPage()
	{
		if(page >1)
		{
			page--;			
		}
	}
	
	private void drawText(Graphics g, final int offsetX, final boolean centerTitle, final LinkedList txtLines)
	{
		if(txtLines == null) return;
		final int sz = txtLines.size();
		if(sz == 0) return;
		
		boolean isTextFont = true;		
		g.setFont(textFont);
		int firstLine = linesPerPage*(page-1);
		int lastLine = linesPerPage*page;
		int cnt = (lastLine <= sz)? lastLine : sz;
		
		final int top = this.helpRect.y;
		int y = top;
		Object o;			
		String str;
		for(int i =firstLine; i < cnt; i++)
		{
			o = txtLines.get(i);
			if(o != null)
			{	
				str = o.toString();
				if(str.length() > 1 && str.charAt(0) == TITLE_MARK)
				{
					str = str.substring(1);
					g.setFont(titleFont);
					isTextFont = false;					
					final int x = (centerTitle)? (helpRect.width - this.fmTitleFont.stringWidth(str))/2
							: offsetX;
					
					g.drawString(str, x,y);
					y += this.fmTitleFont.getHeight();
				}
				else
				{
					if(!isTextFont)
					{
						isTextFont = true;		
						g.setFont(textFont);
					}
					g.drawString(str, offsetX,y);
					y = y +lineH;
				}
				
			}
			else
			{
				if(y > top)  //not the first line
				{
					y = y +lineH;
				}					
			}
		}
	}
	
	private void drawFormattedText(Graphics g)
	{
		int firstLine = linesPerPage*(page-1);
		int lastLine = linesPerPage*page;
		int cnt = (lastLine <= textLines.size())? lastLine : textLines.size();
		final int top = this.helpRect.y;
		int y = top;
		Object o;			
		String s, s2;
		int start, end, curInd =0;
		int ln, x, strln;		

		for(int i =firstLine; i < cnt; i++)
		{
			o= textLines.get(i);
			if(o != null)
			{
				s = o.toString();
				if(s != null)
				{					
					//title?
					if(s.length() > 1 && s.charAt(0) == TITLE_MARK)
					{
						s = s.substring(1);
						g.setFont(titleFont);
						x = (helpRect.width - this.fmTitleFont.stringWidth(s))/2;
						g.drawString(s, x,y);
						y += this.fmTitleFont.getHeight();
						continue;
					}
					
					x = helpRect.x;		
					
					//find all Bold words
					//ASATRT should be already matched with AEND
					start = s.indexOf(ASTART);
					//first portion
					strln = s.length();
					if(start < 0) // bold can be only at beggining of the line
					{
						end = s.indexOf(AEND);
						if(end >0)
						{
							s2 = s.substring(0, end);
							g.setFont(boldFont);
							g.drawString(s2, x, y); //bold		
							//Log.d("BOLD 1: " + s2);
							
							end++;							
							if(end < strln)
							{
								x +=fmRegFont.stringWidth(s2)+ space_width;
								s2 = s.substring(end);
								g.setFont(textFont);
								g.drawString(s2, x, y);		
								//Log.d("REG 1: " + s2);
							}
							
						}
						else if(end ==0)
						{
							end++;
							if(end < strln)
							{
								s2 = s.substring(end);
								g.setFont(textFont);
								g.drawString(s2, x, y);		
								//Log.d("REG 2: " + s2);
							}							
						}
						else
						{
							g.setFont(textFont);
							g.drawString(s, x, y);		
							//Log.d("REG 3: " + s);
						}
						
					}
					else //some bold start >=0
					{
						end = s.indexOf(AEND);
						if(end >=0 && end < start)
						{
							//bold in the begining
							if(end >0)
							{
								s2 = s.substring(0, end);
								g.setFont(boldFont);
								g.drawString(s2, x, y);	
								//Log.d("BOLD 2: " + s2);
								ln = this.fmBoldFont.stringWidth(s2);
								x += ln+space_width;
								
							}
							curInd = end+1;
											
						}
						else
						{
							curInd = 0;		
						}	
						
						boolean lineEndRendered = false;
											
						while(start >=0)
						{	
							lineEndRendered = false;
							if(start > curInd)
							{
								
								s2 = s.substring(curInd, start);
								ln = this.fmRegFont.stringWidth(s2);
								g.setFont(textFont);
								g.drawString(s2, x, y);	
								//Log.d("REG 4: " + s2);
								x += ln+space_width;								
							}
							
							curInd = start +1;
							
							end = s.indexOf(AEND, curInd);
							if(end <=0 || start >= end)  //no closing tag on this line - ends on bold
							{
								s2 = s.substring(curInd); //all bold - done
								g.setFont(boldFont);
								g.drawString(s2, x, y); //bold		
								//Log.d("BOLD 3: " + s2);
								lineEndRendered = true;
								break;
							
							}							
							else
							{
								//bold portion of text
								s2 = s.substring(curInd, end);
								curInd = end+1;								
							}							
							
							ln = this.fmBoldFont.stringWidth(s2);
							g.setFont(boldFont);
							g.drawString(s2, x, y); //bold
							//Log.d("BOLD 6: " + s2);						
							x += ln+space_width;							
							
							start = s.indexOf(ASTART, curInd);
							
						}//while
						//draw the rest if any
						if(!lineEndRendered && curInd < s.length())
						{
							s2 = s.substring(curInd);
							if(s2 != null)
							{
								g.setFont(textFont);
								g.drawString(s2, x, y);	
								//Log.d("REG 6: " + s2);
							}
						}
					}	
				}
				
				y = y +lineH;
			}
			else
			{
				if(y > top)  //not the first line
				{
					y = y +lineH;
				}					
			}
		}
	}
	
	
	//TODO - add correction for titles
	private void calcPages()
	{
		page =1;
		int sz;		
		
		if(textLines == null) 
		{
			totalPages = 1;
			return;
		}
		sz = textLines.size();			
		
		if(sz > linesPerPage && linesPerPage > 0)
		{
			final int rem = sz % linesPerPage;
			if(rem >0)
			{
				totalPages = sz/linesPerPage +1;
			}
			else
			{
				totalPages = sz/linesPerPage;
			}
		}
		else
		{
			totalPages = 1;
		}		
		
	}
	
	private void split(final int txtWidth, final HelpSection[] hlpSec, LinkedList txtLines)
	{
		if(hlpSec == null) return;	
		
		final int cnt = hlpSec.length;
		int sz, rem;

		final int oneLess = linesPerPage-1;
		final int twoLess = oneLess-1;
		
			//render the rest or bail out 
			for(int i=0; i < cnt; i++)
			{	
				if(hlpSec[i] == null)
				{
					continue;
					
				}			
				if(hlpSec[i].title != null)
				{
					if(i >0)
					{
						txtLines.add(null);	//space before
					}

					sz = txtLines.size(); //see if the Title will be the last line on the page
					if(sz >0)
					{
						if(sz == oneLess)
						{
							txtLines.add(null);
						}
						else if(sz == twoLess)
						{
							txtLines.add(null);
							txtLines.add(null);
						}
						else
						{
							rem = sz % this.linesPerPage;
							if(rem == oneLess) //we have only 1 line left
							{
								txtLines.add(null);
							}
							else if(rem == twoLess)
							{
								
								txtLines.add(null);
								txtLines.add(null);
							}
						}
						
					}
					
					txtLines.add(TITLE_MARK + hlpSec[i].title);
					//splitTitle(helpText[i].title, helpRect.width,this.fmBoldFont);
					txtLines.add(null);
					
				}				
				if(hlpSec[i].text != null)
				{					
					splitBody(hlpSec[i].text, true, txtWidth, this.fmRegFont, txtLines);
				}
			}
		
		
		
			
		//remove empty lines
		if(txtLines != null)
		{
			int lastLine = txtLines.size() -1;
			
			while(lastLine >=0)
			{
				if(txtLines.get(lastLine)== null)
				{
					txtLines.remove(lastLine);
					lastLine--;
				}
				else
				{
					//TEST
//					Object o = textLines.get(lastLine);
//					String s = o.toString().trim();
					//Log.d("----- >>> LAST LINE=" + s + " ln =" + s.length()); ////////////////////												
					lastLine = -1;
					break;
				}
			}
		}			
		
	}
	
	private void splitBody(StringBuffer text, final boolean addEmptyLn, final int w, final FontMetrics m, LinkedList txtLines)
	{	
		
		int startInd =0;
		//split chunk
		String word;		
		int spaceInd;
		StringBuffer buf = new StringBuffer();
		StringBuffer line = new StringBuffer();
		
		StringBuffer par;			
		int nextNL=text.indexOf(PIPE);
		int parStart = 0;
		if(nextNL <=0) nextNL = text.length(); 	
		
		while(nextNL > 0)
		{
			par = new StringBuffer(text.substring(parStart,nextNL ).trim());			
					
			spaceInd = par.indexOf(SPACESTR);		
			startInd = 0;
			boolean lastWord = false;	
			//check if we have only one word in par
			if(spaceInd <=0 && par.length() > 0)
			{				

				txtLines.add(par.toString()); //TODO - what if a very long word?
			}
			else
			{
				while(spaceInd > 0)
				{	
					word = par.substring(startInd, spaceInd).trim();
					
					if(startInd > 0) buf.append(SPACESTR);
					buf.append(word);			
					if(m.stringWidth(buf.toString()) < w)
					{
						//not enough
						if(startInd > 0) line.append(SPACESTR);
						line.append(word);	
						if(lastWord)
						{							
						
							txtLines.add(line.toString());	 
							
							line = new StringBuffer();
							buf = new StringBuffer();
							lastWord = false;					
						}
					}
					else //too long
					{						

						txtLines.add(line.toString());							
						line = new StringBuffer();
						line.append(word);				
						buf = new StringBuffer();
						buf.append(word);					
						
					}
					startInd = spaceInd +1;
					spaceInd = par.indexOf(SPACESTR, startInd);						
					//get the last word if any
					if(spaceInd <=0)
					{
						if(startInd < par.length()-1)
						{
							spaceInd = par.length(); //continue adding
							lastWord = true;
						}
						else  //last word in par startInd == par.length()-1
						{
							//add last word
							if(line != null && line.length() >0)
							{								
								
								txtLines.add(word);
							}							
							line = new StringBuffer();
							buf = new StringBuffer();
						}
					}				
				}	//while
			} //else					
			
			parStart = nextNL +1;
			nextNL=text.indexOf(PIPE, parStart);
			if(nextNL > 0 && line.length() > 0 && line.charAt(line.length() -1) != PIPECHAR) 
			{
				txtLines.add(line.toString());					
				line = new StringBuffer();				
			}
			
			if(nextNL <=0 && parStart < text.length() -1)
			{
				nextNL = text.length(); 
			}
			if(addEmptyLn)
			{				
				txtLines.add(null);
			}
			
		}//while
		
		if(line.length() >0)
		{
			//remove 'empty' line
			if(addEmptyLn)
			{
				txtLines.remove(txtLines.size()-1);
			}
			txtLines.add(line.toString());	
		}
		else if(addEmptyLn)
		{
			final int linesLast = txtLines.size()-1;
			if(linesLast > 0)
			{
				Object o = txtLines.get(txtLines.size()-1);
				if(o==null)
				{					
					txtLines.remove(txtLines.size()-1);
				}
			}			
		}
		
		
	}
	
	public static void splitClues(StringBuffer text, final int w, final FontMetrics m, LinkedList textLines, final int maxLines)
	{		
		int startInd =0;
		//split chunk
		String word;		
		int spaceInd;
		StringBuffer buf = new StringBuffer();
		StringBuffer line = new StringBuffer();
		
		StringBuffer par;			
		int nextNL=text.indexOf(TextHelper.PIPE);
		int parStart = 0;
		if(nextNL <=0) nextNL = text.length(); 	
		int lineCnt =0;
		while(nextNL > 0)
		{
			par = new StringBuffer(text.substring(parStart,nextNL ).trim());			
					
			spaceInd = par.indexOf(TextHelper.SPACESTR);		
			startInd = 0;
			boolean lastWord = false;	
			//check if we have only one word in par
			if(spaceInd <=0 && par.length() > 0)
			{	
				textLines.add(par.toString()); //TODO - what if a very long word?
				lineCnt++;
				if(lineCnt >= maxLines)
				{
					break;
				}
			}
			else
			{
				while(spaceInd > 0)
				{	
					word = par.substring(startInd, spaceInd).trim();
					
					if(startInd > 0) buf.append(TextHelper.SPACESTR);
					buf.append(word);			
					if(m.stringWidth(buf.toString()) < w)
					{
						//not enough
						if(startInd > 0) line.append(TextHelper.SPACESTR);
						line.append(word);	
						if(lastWord)
						{							
						
							textLines.add(line.toString());	 
							lineCnt++;
							if(lineCnt >= maxLines)
							{
								break;
							}
							
							line = new StringBuffer();
							buf = new StringBuffer();
							lastWord = false;					
						}
					}
					else //too long
					{						

						textLines.add(line.toString());		
						lineCnt++;
						if(lineCnt >= maxLines)
						{
							break;
						}
						line = new StringBuffer();
						line.append(word);				
						buf = new StringBuffer();
						buf.append(word);					
						
					}
					startInd = spaceInd +1;
					spaceInd = par.indexOf(TextHelper.SPACESTR, startInd);						
					//get the last word if any
					if(spaceInd <=0)
					{
						if(startInd < par.length()-1)
						{
							spaceInd = par.length(); //continue adding
							lastWord = true;
						}
						else  //last word in par startInd == par.length()-1
						{
							//add last word
							if(line != null && line.length() >0)
							{								
								
								textLines.add(word);
								lineCnt++;
								if(lineCnt >= maxLines)
								{
									break;
								}
							}							
							line = new StringBuffer();
							buf = new StringBuffer();
						}
					}				
				}	//while 2
			} //else					
			if(lineCnt >= maxLines)
			{
				break;
			}
			parStart = nextNL +1;
			nextNL=text.indexOf(TextHelper.PIPE, parStart);
			if(nextNL > 0 && line.length() > 0 && line.charAt(line.length() -1) != TextHelper.PIPECHAR) 
			{
				textLines.add(line.toString());					
				line = new StringBuffer();				
			}
			
			if(nextNL <=0 && parStart < text.length() -1)
			{
				nextNL = text.length(); 
			}
			
			
		}//while
		
		if(lineCnt < maxLines && line.length() >0)
		{
			textLines.add(line.toString());	
		}
	
		
		
	}
	
	
	
	private class HelpSection {

		public String title;
		public StringBuffer text;
		
		public HelpSection(){}
		
		public void append(final String s)
		{
			if(text == null) text = new StringBuffer();
			text.append(s);
		}
		
		public void append(final char s)
		{
			if(text == null) text = new StringBuffer();
			text.append(s);
		}
	}

}
