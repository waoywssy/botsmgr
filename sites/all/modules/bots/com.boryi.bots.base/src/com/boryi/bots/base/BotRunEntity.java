package com.boryi.bots.base;

/**
 * Bot entity class boundles bot id, job id, task id, run id, and run date
 *
 * <p><strong>Copyright 2007 - 2017 Boryi Network Information Inc.</strong></p>
 * 
 * @author Gang Huang, Ph.D.
 * @version 1.0
 * @since August 2009
 */
public class BotRunEntity 
{
    /**
     * Id of the server
     */
    protected int serverId;

    /**
     * Id of the bot
     */
    protected int botId;
    /**
     * Id of the job of the bot
     */
    protected int jobId;
    /**
     * Id of the task of the job of the bod
     */
    protected int taskId;
    /**
     * Id of the run of the task of the job of the bot
     */
    protected int runId = 0;
    /**
     * Date and time of the run started
     */
    protected long runDate;
    /**
     * Whether the run is resumed for the previous run
     */
    protected boolean isResume;
    /**
     * Id of the bot data (bot id, job id, task id, run id), as long as using
     * the same bot database. see bot_runs_btrns table.
     */
    protected int botRunId = 0;
    /**
     * Singleton for BotRunEntity which can be inherited to refer the derived 
     * class instance.  Always refer to the first initialization instance.
     */
    protected static BotRunEntity instance;
    
    
    /**
     * Constructor to initialize an instance of BotRunEntity
     * 
     * @param serverId Id of the server
     * @param botId Id of the bot
     * @param jobId Id of the job of the bot
     * @param taskId Id of the task of the job of the bod
     * @param runDate The date and time the run started
     * @param isResume Whether the run is resumed for the previous run
     */
    public BotRunEntity(int serverId, int botId, int jobId, int taskId, long runDate,
            boolean isResume)
    {
        initialize();

        this.serverId = serverId;
        this.botId = botId;
        this.jobId = jobId;
        this.taskId = taskId;
        this.runDate = runDate;
        this.isResume = isResume;
    }
    
    
    /**
     * The first initialization instance. It can refer to the derived class 
     * instance.
     */
    protected void initialize()
    {
        if (instance == null)
        {
            instance = this;
        }
    }

    
    /**
     * Singleton for BotRunEntity which can be inherited to refer the derived 
     * class instance.  Always refer to the first initialization instance.
     * 
     * @return The first initialization instance
     */
    public static BotRunEntity getInstance()
    {
        return instance;
    }


    /**
     * Get id of the server
     *
     * @return Id of the server
     */
    public int getServerId()
    {
        return serverId;
    }


    /**
     * Get id of the bot
     * 
     * @return Id of the bot
     */
    public int getBotId() 
    {
        return botId;
    }

    
    /**
     * Get id of the job of the bot
     * 
     * @return Id of the job of the bot
     */
    public int getJobId() 
    {
        return jobId;
    }

    
    /**
     * Get id of the task of the job of the bod
     * 
     * @return Id of the task of the job of the bod
     */
    public int getTaskId() 
    {
        return taskId;
    }

    
    /**
     * Get id of the run of the task of the job of the bot
     * 
     * @return Id of the run of the task of the job of the bot
     */
    public int getRunId() 
    {
        return runId;
    }
    
    
    /**
     * Set id of the run of the task of the job of the bot
     * 
     * @param runId Id of the run of the task of the job of the bot
     */
    public void setRunId(int runId) 
    {
        this.runId = runId;
    }

    
    /**
     * Get date and time of the run
     * 
     * @return Date and time of the run
     */
    public long getRunDate() 
    {
        return runDate;
    }

    
    /**
     * Set date and time of the run
     * 
     * @param runDate Date and time of the run
     */
    public void setRunDate(long runDate) 
    {
        this.runDate = runDate;
    }
    
    
    /**
     * Get whether the run is resumed for the previous run
     * 
     * @return Whether the run is resumed for the previous run
     */
    public boolean getIsResume()
    {
        return isResume;
    }
    
    
    /**
     * Get id of the bot data (bot id, job id, task id, run id), as long as using
     * the same bot database. see bot_runs_btrns table.
     * 
     * @return Id of the bot data (bot id, job id, task id, run id), as long as using
     * the same bot database. see bot_runs_btrns table.
     */
    public int getBotRunId()
    {
        return  botRunId;
    }
    
    
    /**
     * Set id of the bot data (bot id, job id, task id, run id), as long as using
     * the same bot database. see bot_runs_btrns table.
     * 
     * @param botRunId Id of the bot data (bot id, job id, task id, run id), as long as using
     * the same bot database. see bot_runs_btrns table.
     */
    public void setBotRunId(int botRunId)
    {
        this.botRunId = botRunId;
    }
}
