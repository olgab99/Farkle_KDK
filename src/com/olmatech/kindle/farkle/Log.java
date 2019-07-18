package com.olmatech.kindle.farkle;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;


public class Log {
	
	private static  File homeDir;
	private static Logger logger = Logger.getLogger(Log.class.getName());
	
	public static void setHomeDir(File f)
	{
		homeDir = f;
	}
	
	public static Logger getLogger()
	{
		if( logger != null)
		{
			return logger;
		}
		if(homeDir == null) return null;
		//logger
		//context.getHomeDirectory()
        try 
        {
        	final PatternLayout layout = new PatternLayout("%p - %m%n");
        	final FileAppender appender = new FileAppender(layout, homeDir + File.separator + "debug.log");
        	BasicConfigurator.configure(appender);
        	Logger.getRootLogger().setLevel(Level.ALL);
        } 
        catch (IOException e) 
    	{
    	// Handle configuration error
        	logger = null;
    	}
        return logger;
	}
	
	public static void d(String msg)
	{
		if(logger != null)
		{
			logger.debug(msg);
		}
	}
	
	public static void err(String msg)
	{
		if(logger != null)
		{
			logger.error("ERROR: " + msg);
		}
	}

}