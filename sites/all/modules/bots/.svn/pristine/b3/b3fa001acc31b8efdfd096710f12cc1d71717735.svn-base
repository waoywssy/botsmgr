package com.boryi.bots.base;

import java.util.Date;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Example main class for the bot
 *
 * <p><strong>Copyright 2007 - 2017 Boryi Network Information Inc.</strong></p>
 * 
 * @author Gang Huang, Ph.D.
 * @version 1.0
 * @since August 2009
 */
public class BotMain 
{
    public static void main(String[] args)
    {
        Hashtable<String, Object> values;
        try
        {
            values = BotBase.getArguments(args);
        }
        catch (Exception exArgs)
        {
            System.out.println("usage: ");
            System.out.println();
            System.out.println("java -jar botname botId jobId taskId isResume config");
            System.out.println("java -jar botname botId jobId taskId config");
            System.out.println("java -jar botname botId jobId isResume config");
            System.out.println("java -jar botname botId jobId config");
            return;
        }
        
        int botId = ((Integer)values.get("botId")).intValue();
        int jobId = ((Integer)values.get("jobId")).intValue();
        int taskId = ((Integer)values.get("taskId")).intValue();
        boolean isResume = ((Boolean)values.get("resume")).booleanValue();
        String configFile = (String)values.get("config");
        
        try
        {
            BotRunEntity run = new BotRunEntity(1, botId, jobId, taskId,
                    System.currentTimeMillis(), isResume);
            
            BotConfig config = new BotConfig(configFile);
            BotDao dao = new BotDao();
            
            BotChecklist checklist = new BotChecklist();
            BotWebStatistic webStatistic = new BotWebStatistic();

            BotThread botThread = new BotThread();
            
            for (int i = 0; i < config.getWebThreads(); i++)
            {
                BotWebThread webThread = new BotWebThread();
            }

            BotBase base = new BotBase();
        }
        catch (Exception ex)
        {
            BotBase.logger.fatal("Bot Id: " + botId
                    + " Job Id: " + jobId
                    + " Task Id: " + taskId
                    + BotDateFormat.getDateFormat(BotDateFormatType.MILLISECOND).format(new Date())
                    + ex.toString());
        }
    }
}
