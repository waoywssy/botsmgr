package com.boryi.bots.client.data;

/**
 * Data bundle for a run
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class RunItem extends RowItem
{
  private int botId;
  private String botName;
  private int jobId;
  private String jobName;
  private int taskId;
  private String taskName;

  private String start;
  private String end;
  private float duration;
  private int result;
  private int serverId;
  private String serverName;


  public RunItem()
  {

  }


  public RunItem(int botId, String botName, int jobId, String jobName,
      int taskId, String taskName, int id, String start, String end,
      float duration, int result, int serverId, String serverName)
  {
    this.botId = botId;
    this.botName = botName;
    this.jobId = jobId;
    this.jobName = jobName;
    this.taskId = taskId;
    this.taskName = taskName;
    this.id = id;
    this.start = start;
    this.end = end;
    this.duration = duration;
    this.result = result;
    this.serverId = serverId;
    this.serverName = serverName;
  }


  public int getBotId()
  {
    return botId;
  }


  public String getBotName()
  {
    return botName;
  }


  public int getJobId()
  {
    return jobId;
  }


  public String getJobName()
  {
    return jobName;
  }


  public int getTaskId()
  {
    return taskId;
  }


  public String getTaskName()
  {
    return taskName;
  }


  public String getStart()
  {
    return start;
  }


  public String getEnd()
  {
    return end;
  }


  public float getDuration()
  {
    return duration;
  }


  public int getResult()
  {
    return result;
  }


  public int getServerId()
  {
    return serverId;
  }


  public String getServerName()
  {
    return serverName;
  }


  @Override
  public String getColumn(int index)
  {
    String column = "";
    switch (index)
    {
      case 0:
        column = Boolean.toString(selected);
        break;
      case 1:
        column = botName;
        break;
      case 2:
        column = jobName;
        break;
      case 3:
        column = taskName;
        break;
      case 4:
        column = Integer.toString(id);
        break;
      case 5:
        column = start;
        break;
      case 6:
        column = end;
        break;
      case 7:
        column = Float.toString(duration);
        break;
      case 8:
        column = Integer.toString(result);
        break;
      case 9:
        column = serverName;
        break;
    }
    return column;
  }


  @Override
  public void setColumn(int index, String value)
  {
    switch (index)
    {
      case 0:
        selected = Boolean.valueOf(value);
        break;
      case 1:
        botName = value;
        break;
      case 2:
        jobName = value;
        break;
      case 3:
        taskName = value;
        break;
      case 4:
        id = Integer.valueOf(value);
        break;
      case 5:
        start = value;
        break;
      case 6:
        end = value;
        break;
      case 7:
        duration = Float.valueOf(value);
        break;
      case 8:
        result = Integer.valueOf(value);
        break;
      case 9:
        serverName = value;
        break;
      case 10:
        botId = Integer.valueOf(value);
        break;
      case 11:
        jobId = Integer.valueOf(value);
        break;
      case 12:
        taskId = Integer.valueOf(value);
        break;
      case 13:
        serverId = Integer.valueOf(value);
        break;
    }
  }


  @Override
  public int compareColumn(int index, String value)
  {
    int result = 0;
    switch (index)
    {
      case 0:
        result = selected == Boolean.valueOf(value) ? 0 : (selected ? 1 : -1);
        break;
      case 1:
        result = botName.compareTo(value);
        break;
      case 2:
        result = jobName.compareTo(value);
        break;
      case 3:
        result = taskName.compareTo(value);
        break;
      case 4:
        int ivalue = Integer.valueOf(value);
        result = id == ivalue ? 0 : (id > ivalue ? 1 : -1);
        break;
      case 5:
        result = start.compareTo(value);
        break;
      case 6:
        result = end.compareTo(value);
        break;
      case 7:
        float fvalue = Float.valueOf(value);
        result = duration == fvalue ? 0 : (duration > fvalue ? 1 : -1);
        break;
      case 8:
        int jvalue = Integer.valueOf(value);
        result = this.result == jvalue ? 0 : (this.result > jvalue ? 1 : -1);
        break;
      case 9:
        result = serverName.compareTo(value);
        break;
    }
    return result;
  }
}
