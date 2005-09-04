package org.siri.core.readers;

import java.util.TimerTask;

/**
 * Created by IntelliJ IDEA.
 * User: Georges.Polyzois
 * Date: 2005-apr-04
 * Time: 16:01:54
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractReader extends TimerTask      
{
	void read()
	{
		readFromSource();
//		sortCommands();
		
	}
	
//	void transform();
	
	abstract void readFromSource();
	
}
