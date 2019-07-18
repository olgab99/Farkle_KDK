package com.olmatech.kindle.farkle;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JLabel;

import com.amazon.kindle.kindlet.ui.KindletUIResources;
import com.amazon.kindle.kindlet.ui.KindletUIResources.KFontFamilyName;

public class GLib {
	
	private static Dimension screenSize; //=  new Dimension(600, 700);
	//fonts
	private final static int MARGIN = 20;
		private static final int MEDTEXT = 19;

		public final static KFontFamilyName fontFamily = KFontFamilyName.SANS_SERIF; 
		public final static Font regFont = KindletUIResources.getInstance().getFont(fontFamily, MEDTEXT, KindletUIResources.KFontStyle.PLAIN);
		public final static Font italicFont = KindletUIResources.getInstance().getFont(fontFamily, MEDTEXT, KindletUIResources.KFontStyle.ITALIC);
	    public final static Font boldFont = KindletUIResources.getInstance().getFont(fontFamily, MEDTEXT, KindletUIResources.KFontStyle.BOLD);
	    public final static Font scrollMsgFont = KindletUIResources.getInstance().getFont(fontFamily, 17, KindletUIResources.KFontStyle.ITALIC);
	    
	    public final static Font titleFont = KindletUIResources.getInstance().getFont(fontFamily, 31, KindletUIResources.KFontStyle.BOLD);
	    public static FontMetrics fmTitleFont;
	    
	    public static FontMetrics fmBoldFont;
	    
	    public final static Font buttonFont = KindletUIResources.getInstance().getFont(fontFamily, 25, KindletUIResources.KFontStyle.BOLD);
	    public static FontMetrics fmButton;
	    /*
	     * Colors
	     */
	    public final static Color BLACK = KindletUIResources.getInstance().getBackgroundColor(KindletUIResources.KColorName.BLACK);
		public final static Color WHITE = KindletUIResources.getInstance().getBackgroundColor(KindletUIResources.KColorName.WHITE);
		public final static Color DARK_GRAY = KindletUIResources.getInstance().getBackgroundColor(KindletUIResources.KColorName.DARK_GRAY);
		public final static Color LT_GRAY = KindletUIResources.getInstance().getBackgroundColor(KindletUIResources.KColorName.LIGHT_GRAY);
		
		//images
		private static Image imgMainSprite;
		
		//image coords
		private final static Rectangle REC_LOGO = new Rectangle(10, 10, 420, 110);
		private final static Rectangle REC_TOP_LEFT = new Rectangle(10,130,10,60);
		private final static Rectangle REC_TOP_RIGHT = new Rectangle(50,130,10,60);
		private final static Rectangle REC_TOP_BG = new Rectangle(30,130,10,60);
		
		//bottom start pg
		private final static Rectangle REC_BOT_LEFT = new Rectangle(10,200,10,100);
		private final static Rectangle REC_BOT_BG = new Rectangle(30, 200, 10, 100);
		private final static Rectangle REC_BOT_RIGHT = new Rectangle(50, 200, 10, 100);
		private final static Rectangle REC_BOT_IMG = new Rectangle(70,130, 310,160);
		
		private final static Locale currentLocale = Locale.getDefault(); //new Locale("en", "US");       
        public final static ResourceBundle TITLES = ResourceBundle.getBundle("titles", currentLocale);
        
        //Common screen elements
        public final static int LABEL_HEIGHT = 26;
                
        public static void setScreenSize(final int w, final int h)
        {
        	screenSize = new Dimension (w,h);
        }
        
        public static Dimension getScreenSize()
        {
        	return screenSize;
        }
		
		public static boolean initSplashScreen(final Class c, final Container cont) throws InterruptedException
		{
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
				        	Controller c = Controller.getController();
				        	if(c != null)
				        	{
				        		//Log.d("Load ximgSplash");
				        		c.doRepaint();
				        	}
				        }			        	
				        if ((flags & ABORT) != 0)
				        {
				        	Log.err("Image load aborted...");
				        }
				        return true;				
				}
			    };
			    
			    Toolkit toolkit = Toolkit.getDefaultToolkit();
			    imgMainSprite =  toolkit.getImage(c.getResource("/farkle_menu_sprites_new.gif"));
			    toolkit.prepareImage(imgMainSprite, -1, -1, myObserver);	
			    
			    fmButton = cont.getFontMetrics(buttonFont);
			    fmTitleFont = cont.getFontMetrics(titleFont);
			    fmBoldFont = cont.getFontMetrics(boldFont);
			    return true;
		}
		
		//ret bottom Logo y and top bottom img y
		public static Point drawStartScreenBg(Graphics g, final int w, final int h)
		{
			if(imgMainSprite == null) return null;
			//top
			g.setColor(LT_GRAY);
			final int topH =REC_LOGO.height + REC_TOP_LEFT.height; 
			g.fillRect(0, REC_TOP_LEFT.height, w, REC_LOGO.height);
			//draw logo
			int x = (w - REC_LOGO.width)/2;	
			
			g.drawImage(imgMainSprite, x, REC_TOP_LEFT.height, x + REC_LOGO.width, topH, 
					REC_LOGO.x, REC_LOGO.y, REC_LOGO.x + REC_LOGO.width, REC_LOGO.y + REC_LOGO.height, null);	
			
			drawTopScreenPart(g, w,h, TITLES.getString("version"), JLabel.RIGHT);			
			
			//draw bottom	
			drawBotScreenPart(g, w,h);
			
			//image
			g.drawImage(imgMainSprite, w - REC_BOT_IMG.width, h - REC_BOT_IMG.height, w, h, 
					REC_BOT_IMG.x, REC_BOT_IMG.y, REC_BOT_IMG.x+REC_BOT_IMG.width, REC_BOT_IMG.y+REC_BOT_IMG.height, null);		
			
			
			return new Point(topH, h -REC_BOT_BG.height);
			
		}
		
		
		
		public static void drawBotScreenPart(Graphics g, final int w, final int h)
		{
			if(imgMainSprite == null) return;
			//backgr
			g.drawImage(imgMainSprite, 0,h -REC_BOT_BG.height, w, h, 
					REC_BOT_BG.x, REC_BOT_BG.y, REC_BOT_BG.x + REC_BOT_BG.width, REC_BOT_BG.y + REC_BOT_BG.height, null);
			//left
			g.drawImage(imgMainSprite, 0,h -REC_BOT_LEFT.height, REC_BOT_LEFT.width, h, 
					REC_BOT_LEFT.x, REC_BOT_LEFT.y, REC_BOT_LEFT.x + REC_BOT_LEFT.width, REC_BOT_LEFT.y + REC_BOT_LEFT.height, null);
			
			//right
			g.drawImage(imgMainSprite, w-REC_BOT_RIGHT.width,h -REC_BOT_RIGHT.height, w, h, 
					REC_BOT_RIGHT.x, REC_BOT_RIGHT.y, REC_BOT_RIGHT.x + REC_BOT_RIGHT.width, REC_BOT_RIGHT.y + REC_BOT_RIGHT.height, null);
			
			
		}
		
		public static void drawScoringImg(Graphics g, final int x, final int y, final int dw)
		{
			//scoring image is 98x396 and located at 1,366
			if(imgMainSprite == null) return;
			final int dx = (dw- 98)/2;
			g.drawImage(imgMainSprite, x + dx, y, x + dx + 98, y + 396,
					1, 366, 99, 366+396, null);
			
		}
		
		public static int getBotPartHeight()
		{
			return REC_BOT_LEFT.height;
		}
		
		
		
		public static void drawTopScreenPart(Graphics g, final int w, final int h, final String title, final int titleAlign)
		{
			if(imgMainSprite == null) return;
			//top part
			//bg
			g.drawImage(imgMainSprite, 0,0, w, REC_TOP_BG.height, 
					REC_TOP_BG.x, REC_TOP_BG.y, REC_TOP_BG.x + REC_TOP_BG.width, REC_TOP_BG.y + REC_TOP_BG.height, null);
			
			//left
			g.drawImage(imgMainSprite, 0,0, REC_TOP_LEFT.width, REC_TOP_LEFT.height, 
					REC_TOP_LEFT.x, REC_TOP_LEFT.y, REC_TOP_LEFT.x + REC_TOP_LEFT.width, REC_TOP_LEFT.y + REC_TOP_LEFT.height, null);
			
			//right
			g.drawImage(imgMainSprite, w - REC_TOP_RIGHT.width,0, w, REC_TOP_RIGHT.height, 
					REC_TOP_RIGHT.x, REC_TOP_RIGHT.y, REC_TOP_RIGHT.x + REC_TOP_RIGHT.width, REC_TOP_RIGHT.y + REC_TOP_RIGHT.height, null);
		
			
			//title if any
			if(title != null && fmTitleFont != null)
			{
				int x;
				switch(titleAlign)
				{
				case JLabel.LEFT:
					x = MARGIN;
					break;
				case JLabel.RIGHT:
					x = w - fmTitleFont.stringWidth(title) - MARGIN;
					break;
				default:
					x = (w - fmTitleFont.stringWidth(title))/2;
					break;
				}
				int y = REC_TOP_BG.height - (REC_TOP_BG.height - fmTitleFont.getHeight())/2;
				g.setFont(titleFont);
				g.setColor(BLACK);
				g.drawString(title, x, y);
			}
			
		}
		
		public static int getTopPartBot()
		{
			return REC_TOP_BG.height;
		}
		
		public static void drawButtonBackground(Graphics g)
		{
			
		}

}
