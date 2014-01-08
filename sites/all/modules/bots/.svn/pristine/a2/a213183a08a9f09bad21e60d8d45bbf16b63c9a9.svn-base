package com.boryi.bots.base;



import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Date;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Bot database access object
 *
 * <p><strong>Copyright 2007 - 2017 Boryi Network Information Inc.</strong></p>
 * 
 * @author Gang Huang, Ph.D.
 * @version 1.0
 * @since August 2009
 */
public class BotDao 
{
    /**
     * The connection to central database: run table and event table
     */
    protected Connection dbCentralConnection;
    /**
     * The connection to local database: qa table, web statistic table,
     * checklist table, bot related data tables
     */
    protected Connection dbLocalConnection;
    /**
     * Procedures for central database operations and data manipulations
     */
    protected Hashtable<String, CallableStatement> centralProcedures;
    /**
     * Procedures for local database operations and data manipulations
     */
    protected Hashtable<String, CallableStatement> localProcedures;
    /**
     * Singleton for BotDao which can be inherited to refer the derived 
     * class instance.  Always refer to the first initialization instance.
     */
    protected static BotDao instance;
    /**
     * Database sleeping time calculator
     */
    protected BotSleepingTime sleepingTime;
    
    
    /**
     * Constructor to initialize an instance of BotDao
     */
    public BotDao()
            throws Exception
    {
        initialize();
        
        sleepingTime = BotSleepingTimeFactory.getSleepingTimeMethod(
                BotConfig.getInstance().getDbRetry().getMethod(),
                BotConfig.getInstance().getDbRetry().getBase(),
                BotConfig.getInstance().getDbRetry().getStep());

        prepareCentral();
        prepareLocal();
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
     * Singleton for BotDao which can be inherited to refer the derived 
     * class instance.  Always refer to the first initialization instance.
     * 
     * @return The first initialization instance
     */
    public static BotDao getInstance()
    {
        return instance;
    }
    

    /**
     * Prepare the central database connections, and callable statements
     * 
     * @throws java.lang.ClassNotFoundException
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     * @throws java.sql.SQLException
     */
    protected void prepareCentral()
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, SQLException, InterruptedException
    {
        int retry = 0;
        boolean failed = true;
        
        while (failed)
        {
            try
            {
                if (dbCentralConnection != null && !dbCentralConnection.isClosed())
                {
                    dbCentralConnection.close();
                }
                dbCentralConnection = BotDbConnection.getConnection(
                        BotConfig.getInstance().getDbCentral());

                centralProcedures = initializeProcedures(dbCentralConnection,
                        BotConfig.getInstance().getCentralProcedures());
                
                failed = false;
            }
            catch (SQLException sex)
            {
                if (retry < BotConfig.getInstance().getDbRetry().getRetry())
                {
                    BotBase.logger.warn("Central database connection retry: " 
                            + retry);
                    Thread.sleep(sleepingTime.getSleepingTime(retry));
                    retry++;
                }
                else
                {
                    throw sex;
                }
            }
        }
    }
    

    /**
     * Prepare the local database connections, and callable statements
     * 
     * @throws java.lang.ClassNotFoundException
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     * @throws java.sql.SQLException
     */
    protected void prepareLocal()
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, SQLException, InterruptedException
    {
        int retry = 0;
        boolean failed = true;
        
        while (failed)
        {
            try
            {
                if (dbLocalConnection != null && !dbLocalConnection.isClosed())
                {
                    dbLocalConnection.close();
                }
                dbLocalConnection = BotDbConnection.getConnection(
                        BotConfig.getInstance().getDbLocal());

                localProcedures = initializeProcedures(dbLocalConnection,
                        BotConfig.getInstance().getLocalProcedures());
                
                failed = false;
            }
            catch (SQLException sex)
            {
                if (retry < BotConfig.getInstance().getDbRetry().getRetry())
                {
                    BotBase.logger.warn("Local database connection retry: " 
                            + retry);
                    Thread.sleep(sleepingTime.getSleepingTime(retry));
                    retry++;
                }
                else
                {
                    throw sex;
                }
            }
        }
    }
    
    
    /**
     * Initialize procedure callable statements
     * 
     * @param connection Database connection for the callable statements
     * @param procedures Database stored procedures
     * @return Hashtable of callable statements of the connection
     * @throws java.sql.SQLException
     */
    protected Hashtable<String, CallableStatement> initializeProcedures(
            Connection connection, Hashtable<String, String> procedures)
            throws SQLException
    {
        Hashtable<String, CallableStatement> cProcedures = 
                new Hashtable<String, CallableStatement>(procedures.size());
        
        Enumeration<String> keys = procedures.keys();
        while(keys.hasMoreElements())
        {
            String key = keys.nextElement();
            String value = procedures.get(key);
            CallableStatement cstm = connection.prepareCall(value);
            cProcedures.put(key, cstm);
        }
        
        return cProcedures;
    }
    
    
    /**
     * Get run entity by inserting a new run or getting previous run to be 
     * resumed
     * 
     * getRun: 
     * get_resumed_run(?,?,?) - botId, jobId, taskId
     * insert_run(?,?,?,?,?) - botId, jobId, taskId, rundate, serverId
     * insert_bot_run(?,?,?,?,?) - botId, jobId, taskId, runId, rundate
     */
    public void getRun() throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, SQLException, InterruptedException
    {
        int retry = 0;
        boolean failed = true;
        while (failed) // database connection failure, then retry
        {
            try
            {
                CallableStatement cs = null;
                ResultSet rs = null;
                if (BotRunEntity.getInstance().getIsResume())
                {
                    cs = localProcedures.get("get_resumed_run");

                    cs.setInt(1, BotRunEntity.getInstance().getBotId());
                    cs.setInt(2, BotRunEntity.getInstance().getJobId());
                    cs.setInt(3, BotRunEntity.getInstance().getTaskId());

                    rs = cs.executeQuery();
                    if (rs.next())
                    {
                        BotRunEntity.getInstance().setBotRunId(rs.getInt(1));
                        BotRunEntity.getInstance().setRunId(rs.getInt(2));
                        BotRunEntity.getInstance().setRunDate(rs.getTimestamp(3).getTime());
                    }
                    rs.close();
                }
                else
                {
                    cs = centralProcedures.get("insert_run");
                    cs.setInt(1, BotRunEntity.getInstance().getBotId());
                    cs.setInt(2, BotRunEntity.getInstance().getJobId());
                    cs.setInt(3, BotRunEntity.getInstance().getTaskId());
                    cs.setObject(4, new Date(BotRunEntity.getInstance().getRunDate()));
                    cs.setInt(5, BotRunEntity.getInstance().getServerId());
                    
                    rs = cs.executeQuery();
                    if (rs.next())
                    {
                        BotRunEntity.getInstance().setRunId(rs.getInt(1));
                    }
                    rs.close();
                    
                    cs = localProcedures.get("insert_bot_run");
                    cs.setInt(1, BotRunEntity.getInstance().getBotId());
                    cs.setInt(2, BotRunEntity.getInstance().getJobId());
                    cs.setInt(3, BotRunEntity.getInstance().getTaskId());
                    cs.setInt(4, BotRunEntity.getInstance().getRunId());
                    cs.setObject(5, new Date(BotRunEntity.getInstance().getRunDate()));
                    
                    rs = cs.executeQuery();
                    if (rs.next())
                    {
                        BotRunEntity.getInstance().setBotRunId(rs.getInt(1));
                    }
                }
                
                rs.close();
                failed = false;
            }
            catch (SQLException sex)
            {
                if (retry < 1)
                {
                    BotBase.logger.warn("Central database getRun failed - " 
                            + sex.toString());
                    prepareCentral();
                    retry++;
                }
                else
                {
                    throw sex;
                }
            }
        }
    }
    
    
    /**
     * Update the run status
     * 
     * updateRun: update_run(?,?,?,?,?,?,?) - botId, jobId, taskId, runId, 
     * end date and time, duration, status
     * 
     * @param status Status of the run: resumed failed, failed, running, 
     * success, resumed success
     */
    public void updateRun(BotRunStatus status) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException, SQLException, 
            InterruptedException
    {
        int retry = 0;
        boolean failed = true;
        while (failed) // database connection failure, then retry
        {
            try
            {
                CallableStatement cs = centralProcedures.get("update_run");

                cs.setInt(1, BotRunEntity.getInstance().getBotId());
                cs.setInt(2, BotRunEntity.getInstance().getJobId());
                cs.setInt(3, BotRunEntity.getInstance().getTaskId());
                cs.setInt(4, BotRunEntity.getInstance().getRunId());
                
                long endDatetime = System.currentTimeMillis();
                cs.setObject(5, BotDateFormat.getDateFormat(BotDateFormatType.SECOND).format(new Date(endDatetime)));

                double duration = (double)(endDatetime - BotRunEntity.getInstance().runDate) / 3600000d;

                cs.setDouble(6, duration);
                cs.setInt(7, status.ordinal());

                cs.execute();
        
                failed = false;
            }
            catch (SQLException sex)
            {
                if (retry < 1)
                {
                    BotBase.logger.warn("Central database updateRun failed - " 
                            + sex.toString());
                    prepareCentral();
                    retry++;
                }
                else
                {
                    throw sex;
                }
            }
        }
    }


    /**
     * Get the task status
     *
     * updateRun: get_task_status(?,?,?) - botId, jobId, taskId
     *
     * @param status Status of the run: running or not
     */
    public boolean getTaskStatus()
            throws ClassNotFoundException,
            InstantiationException, IllegalAccessException, SQLException,
            InterruptedException
    {
        boolean running = true;

        int retry = 0;
        boolean failed = true;
        
        while (failed) // database connection failure, then retry
        {
            try
            {
                CallableStatement cs = centralProcedures.get("get_task_status");

                cs.setInt(1, BotRunEntity.getInstance().getBotId());
                cs.setInt(2, BotRunEntity.getInstance().getJobId());
                cs.setInt(3, BotRunEntity.getInstance().getTaskId());
                
                ResultSet rs = cs.executeQuery();
                while (rs.next())
                {
                    if (rs.getInt(2) == BotRunEntity.getInstance().getServerId())
                    {
                        running = rs.getBoolean(1);
                    }
                }

                failed = false;
            }
            catch (SQLException sex)
            {
                if (retry < 1)
                {
                    BotBase.logger.warn("Central database getTaskStatus failed - "
                            + sex.toString());
                    prepareCentral();
                    retry++;
                }
                else
                {
                    throw sex;
                }
            }
        }
        return running;
    }


    /**
     * Update the task status
     *
     * updateRun: update_task_status(?,?,?,?,?) - botId, jobId, taskId,
     * status, serverId
     *
     * @param status Status of the run: running or not
     */
    public void updateTaskStatus(boolean running)
            throws ClassNotFoundException,
            InstantiationException, IllegalAccessException, SQLException,
            InterruptedException
    {
        int retry = 0;
        boolean failed = true;
        while (failed) // database connection failure, then retry
        {
            try
            {
                CallableStatement cs = centralProcedures.get("update_task_status");

                cs.setInt(1, BotRunEntity.getInstance().getBotId());
                cs.setInt(2, BotRunEntity.getInstance().getJobId());
                cs.setInt(3, BotRunEntity.getInstance().getTaskId());
                cs.setBoolean(4, running);
                cs.setInt(5, BotRunEntity.getInstance().getServerId());

                cs.execute();

                failed = false;
            }
            catch (SQLException sex)
            {
                if (retry < 1)
                {
                    BotBase.logger.warn("Central database updateTaskStatus failed - "
                            + sex.toString());
                    prepareCentral();
                    retry++;
                }
                else
                {
                    throw sex;
                }
            }
        }
    }
    
    
    /**
     * Save event entity for the run
     * 
     * saveEnvent: insert_event(?,?,?,?,?,?,?,?) - botId, jobId, taskId, runId, 
     * eventTime, subject, detail, severity
     * 
     * @param event Event entity
     */
    public synchronized void saveEvent(BotEventEntity event) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException, SQLException, 
            InterruptedException
    {
        int retry = 0;
        boolean failed = true;
        while (failed) // database connection failure, then retry
        {
            try
            {
                CallableStatement cs = (CallableStatement)centralProcedures.get("insert_event");

                cs.setInt(1, BotRunEntity.getInstance().getBotId());
                cs.setInt(2, BotRunEntity.getInstance().getJobId());
                cs.setInt(3, BotRunEntity.getInstance().getTaskId());
                cs.setInt(4, BotRunEntity.getInstance().getRunId());
                cs.setObject(5, BotDateFormat.getDateFormat(BotDateFormatType.MILLISECOND).format(new Date(event.getEventTime())));
                cs.setString(6, event.getSubject());
                cs.setString(7, event.getDetail());
                cs.setInt(8, event.getSeverity().ordinal());

                cs.execute();
        
                failed = false;
            }
            catch (SQLException sex)
            {
                if (retry < 1)
                {
                    BotBase.logger.warn("Central database saveEnvent failed - " 
                            + sex.toString());
                    prepareCentral();
                    retry++;
                }
                else
                {
                    throw sex;
                }
            }
        }
    }


    /**
     * Update checklist entity status
     *
     * updateChecklistStatus: update_checklist_status(?,?) - listId, status
     *
     * @param event Event entity
     */
    public synchronized void updateChecklistStatus(int listId, int status) throws 
            ClassNotFoundException, InstantiationException, IllegalAccessException,
            SQLException, InterruptedException
    {
        int retry = 0;
        boolean failed = true;
        while (failed) // database connection failure, then retry
        {
            try
            {
                CallableStatement cs = localProcedures.get("update_checklist_status");

                cs.setInt(1, listId);
                cs.setInt(2, status);

                cs.execute();

                failed = false;
            }
            catch (SQLException sex)
            {
                if (retry < 1)
                {
                    BotBase.logger.warn("Central database saveEnvent failed - "
                            + sex.toString());
                    prepareCentral();
                    retry++;
                }
                else
                {
                    throw sex;
                }
            }
        }
    }
    
    
    /**
     * Get checklist from local database
     * 
     * getChecklist: get_checklist(?,?,?) - botId, jobId, taskId
     * 
     * Checklist item status: unchecked (-1), checking (0), checked (1)
     * 
     * @return Checklist retrieved from local database
     */
    public synchronized ArrayList<BotChecklistEntity> getChecklist() throws
            ClassNotFoundException, InstantiationException, 
            IllegalAccessException, SQLException, InterruptedException,
            URISyntaxException
    {
        int retry = 0;
        while (true) // database connection failure, then retry
        {
            try
            {
                CallableStatement cs = localProcedures.get("get_checklist");
                
                cs.setInt(1, BotRunEntity.getInstance().getBotId());
                cs.setInt(2, BotRunEntity.getInstance().getJobId());
                cs.setInt(3, BotRunEntity.getInstance().getTaskId());

                ResultSet rs = cs.executeQuery();

                ArrayList<BotChecklistEntity> checklist = new ArrayList<BotChecklistEntity>();

                while (rs.next())
                {
                    checklist.add(new BotChecklistEntity(new URI(rs.getString(1)),
                            rs.getInt(2), rs.getInt(3), rs.getInt(4)));
                }

                rs.close();

                return checklist;
            }
            catch (SQLException sex)
            {
                if (retry < 1)
                {
                    BotBase.logger.warn("Local database getChecklist failed - " 
                            + sex.toString());
                    prepareLocal();
                    retry++;
                }
                else
                {
                    throw sex;
                }
            }
        }
    }
    
    
    /**
     * Get the list of items that are under checking from local database
     * 
     * getCheckinglist: get_checkinglist(?,?,?) - botId, jobId, taskId
     * 
     * Checklist item status: unchecked (-1), checking (0), checked (1)
     * 
     * @return Checking list retrieved from local database
     */
    public synchronized ArrayList<BotChecklistEntity> getCheckinglist() throws
            ClassNotFoundException, InstantiationException, 
            IllegalAccessException, SQLException, InterruptedException,
            URISyntaxException
    {
        int retry = 0;
        while (true) // database connection failure, then retry
        {
            try
            {
                CallableStatement cs = localProcedures.get("get_checkinglist");
                
                cs.setInt(1, BotRunEntity.getInstance().getBotId());
                cs.setInt(2, BotRunEntity.getInstance().getJobId());
                cs.setInt(3, BotRunEntity.getInstance().getTaskId());

                ResultSet rs = cs.executeQuery();

                ArrayList<BotChecklistEntity> checkinglist = new ArrayList<BotChecklistEntity>();

                while (rs.next())
                {
                    checkinglist.add(new BotChecklistEntity(new URI(rs.getString(1)),
                            rs.getInt(2), rs.getInt(3), rs.getInt(4)));
                }

                rs.close();

                return checkinglist;
            }
            catch (SQLException sex)
            {
                if (retry < 1)
                {
                    BotBase.logger.warn("Local database getCheckinglist failed - " 
                            + sex.toString());
                    prepareLocal();
                    retry++;
                }
                else
                {
                    throw sex;
                }
            }
        }
    }
    
    
    /**
     * Save checklist to local database
     * 
     * saveChecklist: insert_checkitem(?,?,?,?,?,?) - botId, jobId, taskId, url, 
     * stage, id
     * 
     * @param checklist Checklist to be saved to local database
     */
    public synchronized void saveChecklist(ArrayList<BotChecklistEntity> checklist)
            throws ClassNotFoundException, InstantiationException, 
            IllegalAccessException, SQLException, InterruptedException
    {
        int retry = 0;
        boolean failed = true;
        while (failed) // database connection failure, then retry
        {
            try
            {
                CallableStatement cs = localProcedures.get("insert_checkitem");

                for (int i = 0; i < checklist.size(); i++)
                {
                    BotChecklistEntity item = checklist.get(i);

                    cs.setInt(1, BotRunEntity.getInstance().getBotId());
                    cs.setInt(2, BotRunEntity.getInstance().getJobId());
                    cs.setInt(3, BotRunEntity.getInstance().getTaskId());
                    cs.setString(4, item.getUrl().toString());
                    cs.setInt(5, item.getStage());
                    cs.setInt(6, item.getId());
                    cs.setInt(7, item.getLevel());

                    cs.execute();
                }
        
                failed = false;
            }
            catch (SQLException sex)
            {
                if (retry < 1)
                {
                    BotBase.logger.warn("Local database saveChecklist failed - " 
                            + sex.toString());
                    prepareLocal();
                    retry++;
                }
                else
                {
                    throw sex;
                }
            }
        }
    }
    
    
    /**
     * Clear checklist saved in local database, and then create initial entry
     * for checklist item - usually webUrl, stage 0, id 1
     * 
     * clearChecklist: clear_checklist(?,?,?) - botId, jobId, taskId
     */
    public void clearChecklist() throws 
            ClassNotFoundException, InstantiationException, 
            IllegalAccessException, SQLException, InterruptedException
    {
        int retry = 0;
        boolean failed = true;
        while (failed) // database connection failure, then retry
        {
            try
            {
                CallableStatement cs = localProcedures.get("clear_checklist");
                
                cs.setInt(1, BotRunEntity.getInstance().getBotId());
                cs.setInt(2, BotRunEntity.getInstance().getJobId());
                cs.setInt(3, BotRunEntity.getInstance().getTaskId());

                cs.execute();
        
                failed = false;
            }
            catch (SQLException sex)
            {
                if (retry < 1)
                {
                    BotBase.logger.warn("Local database clearChecklist failed - " 
                            + sex.toString());
                    prepareLocal();
                    retry++;
                }
                else
                {
                    throw sex;
                }
            }
        }
    }
    
    
    /**
     * Mark the checklist item to be crawling
     * 
     * checkingChecklistItem: update_checkingitem(?,?,?,?) - botId, jobId,
     * taskId, url
     * 
     * @param item Checklist entity crawling
     */
    public synchronized void checkingChecklistItem(BotChecklistEntity item)
            throws ClassNotFoundException, InstantiationException, 
            IllegalAccessException, SQLException, InterruptedException
    {
        int retry = 0;
        boolean failed = true;
        while (failed) // database connection failure, then retry
        {
            try
            {
                CallableStatement cs = localProcedures.get("update_checkingitem");

                cs.setInt(1, BotRunEntity.getInstance().getBotId());
                cs.setInt(2, BotRunEntity.getInstance().getJobId());
                cs.setInt(3, BotRunEntity.getInstance().getTaskId());
                cs.setString(4, item.getUrl().toString());

                cs.execute();
        
                failed = false;
            }
            catch (SQLException sex)
            {
                if (retry < 1)
                {
                    BotBase.logger.warn("Local database checkingChecklistItem failed - " 
                            + sex.toString());
                    prepareLocal();
                    retry++;
                }
                else
                {
                    throw sex;
                }
            }
        }
    }
    
    
    /**
     * Mark the checklist item to be done with crawling
     * 
     * doneChecklistItem: update_checkeditem(?,?,?,?) - botId, jobId, taskId,
     * url
     * 
     * @param item Checklist entity done with crawling
     */
    public synchronized void doneChecklistItem(BotChecklistEntity item)
            throws ClassNotFoundException, InstantiationException, 
            IllegalAccessException, SQLException, InterruptedException
    {
        int retry = 0;
        boolean failed = true;
        while (failed) // database connection failure, then retry
        {
            try
            {
                CallableStatement cs = localProcedures.get("update_checkeditem");

                cs.setInt(1, BotRunEntity.getInstance().getBotId());
                cs.setInt(2, BotRunEntity.getInstance().getJobId());
                cs.setInt(3, BotRunEntity.getInstance().getTaskId());
                cs.setString(4, item.getUrl().toString());

                cs.execute();

                failed = false;
            }
            catch (SQLException sex)
            {
                if (retry < 1)
                {
                    BotBase.logger.warn("Local database doneChecklistItem failed - " 
                            + sex.toString());
                    prepareLocal();
                    retry++;
                }
                else
                {
                    throw sex;
                }
            }
        }
    }
    
    
    /**
     * De-checking items.  Set the items of status 0 to be status -1
     * 
     * deCheckinglist: update_decheckingitems(?,?,?) - botId, jobId, taskId
     */
    public void deCheckinglist() throws 
            ClassNotFoundException, InstantiationException, 
            IllegalAccessException, SQLException, InterruptedException
    {
        int retry = 0;
        boolean failed = true;
        while (failed) // database connection failure, then retry
        {
            try
            {
                CallableStatement cs = localProcedures.get("update_decheckingitems");
                
                cs.setInt(1, BotRunEntity.getInstance().getBotId());
                cs.setInt(2, BotRunEntity.getInstance().getJobId());
                cs.setInt(3, BotRunEntity.getInstance().getTaskId());

                cs.execute();
        
                failed = false;
            }
            catch (SQLException sex)
            {
                if (retry < 1)
                {
                    BotBase.logger.warn("Local database deCheckinglist failed - " 
                            + sex.toString());
                    prepareLocal();
                    retry++;
                }
                else
                {
                    throw sex;
                }
            }
        }
    }
    
    
    /**
     * Save web statistic data of the run
     * 
     * saveWebStatistic: insert_web_statistic(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) - 
     * brid, resume, wstart, wend, duration, actual_duration, page_hits_total, 
     * kb_downloaded_total, kb_uploaded_total, b_request_header_total, 
     * b_request_content_total, b_response_header_total, 
     * b_response_content_total, max_page_hits_per_hour, max_kb_per_hour, 
     * threads
     */
    public void saveWebStatistic() throws ClassNotFoundException, 
            InstantiationException, IllegalAccessException, SQLException, 
            InterruptedException
    {
        int retry = 0;
        boolean failed = true;
        while (failed) // database connection failure, then retry
        {
            try
            {
                CallableStatement cs = localProcedures.get("insert_web_statistic");

                cs.setInt(1, BotRunEntity.getInstance().getBotRunId());
                cs.setBoolean(2, BotRunEntity.getInstance().getIsResume());
                cs.setObject(3, new Date(BotWebStatistic.getInstance().getTimeStart()));
                cs.setObject(4, new Date(BotWebStatistic.getInstance().getTimeEnd()));
                cs.setDouble(5, ((double)(BotWebStatistic.getInstance().getTimeEnd() 
                        - BotWebStatistic.getInstance().getTimeStart()) / 3600000.0));
                cs.setDouble(6, 
                        ((double)BotWebStatistic.getInstance().getActualWebTime() / 3600000.0));
                cs.setLong(7, BotWebStatistic.getInstance().getPageHitsTotal());
                cs.setLong(8, BotWebStatistic.getInstance().getKilobytesTotal());
                cs.setLong(9, 
                        Math.round((double)BotWebStatistic.getInstance().getBytesTotalRequest() / 1024.0));
                cs.setLong(10, BotWebStatistic.getInstance().getBytesTotalRequestHeader());
                cs.setLong(11, BotWebStatistic.getInstance().getBytesTotalRequestContent());
                cs.setLong(12, BotWebStatistic.getInstance().getBytesTotalResponseHeader());
                cs.setLong(13, BotWebStatistic.getInstance().getBytesTotalResponseContent());
                cs.setInt(14, BotConfig.getInstance().getMaxPageHitsPerHour());
                cs.setInt(15, BotConfig.getInstance().getMaxKilobytesPerHour());
                cs.setInt(16, BotConfig.getInstance().getWebThreads());

                cs.execute();
        
                failed = false;
            }
            catch (SQLException sex)
            {
                if (retry < 1)
                {
                    BotBase.logger.warn("Local database saveWebStatistic failed - " 
                            + sex.toString());
                    prepareLocal();
                    retry++;
                }
                else
                {
                    throw sex;
                }
            }
        }
    }
    
    
    /**
     * Compute statistic of the data of the run and previous runs, which
     * haven't got qa statistic
     * 
     * qaComputeStatistic: qa_compute_statistic()
     */
    public void qaComputeStatistic() throws ClassNotFoundException, 
            InstantiationException, IllegalAccessException, SQLException, 
            InterruptedException
    {
        int retry = 0;
        boolean failed = true;
        while (failed) // database connection failure, then retry
        {
            try
            {
                CallableStatement cs = localProcedures.get("qa_compute_statistic");
                cs.execute();
        
                failed = false;
            }
            catch (SQLException sex)
            {
                if (retry < 1)
                {
                    BotBase.logger.warn("Local database qaComputeStatistic failed - " 
                            + sex.toString());
                    prepareLocal();
                    retry++;
                }
                else
                {
                    throw sex;
                }
            }
        }
    }


    /**
     * Analyze the statistic of the data of the run by comparing to 
     * the previous valid statistic data, create the QA report and
     * save the result to database (whether the data of the run is valid)
     * and save the result to event table
     * 
     * qaAnalyze: qa_analyze()
     * 
     * @return QA analysis result of the run
     */
    public String qaAnalyze() throws ClassNotFoundException, 
            InstantiationException, IllegalAccessException, SQLException, 
            InterruptedException
    {
        int retry = 0;
        while (true) // database connection failure, then retry
        {
            try
            {
                CallableStatement cs = localProcedures.get("qa_analyze");

                ResultSet rs = cs.executeQuery();

                String report = null;

                if (rs.next())
                {
                    report = rs.getString(1);
                }

                rs.close();

                return report;
            }
            catch (SQLException sex)
            {
                if (retry < 1)
                {
                    BotBase.logger.warn("Local database qaAnalyze failed - " 
                            + sex.toString());
                    prepareLocal();
                    retry++;
                }
                else
                {
                    throw sex;
                }
            }
        }
    }
}
