package com.olmatech.kindle.farkle.screens;

import java.awt.Point;
import java.awt.Rectangle;

//all values based on screen sizes - MUST be defined for each device
public class SetValues {
	
	//PAPERWHITE
	//High Score input screen - hsi_
//	public final static Point hsi_ptCurHS = new Point91, 147);
//	public final static Point hsi_recPlayeName = new Point(91,266);
//	
//	//radios
//	public final static Rectangle hsi_recRadioNoLimit = new Rectangle(81,381,48,48);	
//	public final static Rectangle hsi_recRadio30sec = new Rectangle(321,381,48,48);		
//	public final static Rectangle hsi_recRadio60sec = new Rectangle(521,381,48,48);
//	
//	public final static Rectangle[] hsi_recRadioBonuses = new Rectangle[]{ new Rectangle(81,561,48,48),
//		new Rectangle(81,631,48,48),		
//		new Rectangle(81,701,48,48),
//		new Rectangle(401,561,48,48),
//		new Rectangle(401,631,48,48),
//		new Rectangle(401,701,48,48)
//	};	
//	public final static int radioInnerOffset = 12;
//	public final static int innerRadio = 24;
//	
//	//buttons
//	public final static Rectangle hsi_recViewScoresBut = new Rectangle(480,140,200,50);
//	public final static Rectangle hsi_recEditNameBut = new Rectangle(480,260,200,50);
//	public final static Rectangle hsi_recBonusInfoBut = new Rectangle(480,480,200,50);
//	public final static Rectangle hsi_recBackBut = new Rectangle(135, 820,100,50);
//	public final static Rectangle hsi_recStartGameBut = new Rectangle(285,820,200,50);
//	public final static Rectangle hsi_recHelpBut = new Rectangle(535,820,100,50);
	
	//bonuses squares
//		public final static Rectangle[] hsi_recBonuses = new Rectangle[]{
//			new Rectangle(141,561,218,48),
//			new Rectangle(141,631,218,48),
//			new Rectangle(141,701,218,48),
//			new Rectangle(461,561,218,48),
//			new Rectangle(461,631,218,48),
//			new Rectangle(461,701,218,48)		
//		};
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	//TOUCH
	//High Score input screen  - "hsi"
	public final static Point hsi_ptCurHS = new Point(31, 138);
	public final static Point hsi_ptPlayeName = new Point(31, 227);	
	
	//radios
	public final static Rectangle hsi_recRadioNoLimit = new Rectangle(21,281,48,48);	
	public final static Rectangle hsi_recRadio30sec = new Rectangle(241,281,48,48);		
	public final static Rectangle hsi_recRadio60sec = new Rectangle(421,281,48,48);
	
	public final static Rectangle[] hsi_recRadioBonuses = new Rectangle[]{ new Rectangle(21,421,48,48),
		new Rectangle(21,481,48,48),		
		new Rectangle(21,541,48,48),
		new Rectangle(311,421,48,48),
		new Rectangle(311,481,48,48),
		new Rectangle(311,541,48,48)
	};	
	public final static int radioInnerOffset = 12;
	public final static int innerRadio = 24;
	
	//bonuses squares
	public final static Rectangle[] hsi_recBonuses = new Rectangle[]{
		new Rectangle(81,421,208,48),
		new Rectangle(81,481,208,48),
		new Rectangle(81,421,208,48),
		new Rectangle(371,421,208,48),
		new Rectangle(371, 481,208,48),
		new Rectangle(371,541,208,48)		
	};
	
	
	//buttons
	public final static Rectangle hsi_recViewScoresBut = new Rectangle(380,100,200,50);
	public final static Rectangle hsi_recEditNameBut = new Rectangle(380,190,200,50);
	public final static Rectangle hsi_recBonusInfoBut = new Rectangle(380,350,200,50);
	public final static Rectangle hsi_recBackBut = new Rectangle(50,620,100,50);
	public final static Rectangle hsi_recStartGameBut = new Rectangle(200,620,200,50);
	public final static Rectangle hsi_recHelpBut = new Rectangle(450,620,100,50);
	

}
