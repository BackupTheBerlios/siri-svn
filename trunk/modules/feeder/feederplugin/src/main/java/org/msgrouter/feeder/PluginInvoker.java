package org.msgrouter.feeder;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Georges Polyzois
 */
public class PluginInvoker
{
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(PluginInvoker.class);



   public static final void main(String[] args) {
      ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("feeder-plugin.xml");
      FeederPlugin ref = (FeederPluginImpl)ctx.getBean("feederPluginImpl123");
       ref.execute();

     // CommandLineView clv = (CommandLineView)ctx.getBean("commandLineView");
     // clv.printAllBikes();
   }

}
