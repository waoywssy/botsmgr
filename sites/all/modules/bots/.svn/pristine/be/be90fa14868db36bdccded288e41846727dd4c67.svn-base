package com.boryi.bots.client.data;

/**
 * Data bundle for an event
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class EventItem extends RowItem
{
  private int botId;
  private String botName;
  private int jobId;
  private String jobName;
  private int taskId;
  private String taskName;
  private int runId;

  private String time;
  private String severity = "information";
  private String subject;
  private String detail;


  public EventItem()
  {

  }


  public EventItem(int botId, String botName, int jobId, String jobName,
      int taskId, String taskName, int runId, String time, String severity,
      String subject, String detail)
  {
    this.botId = botId;
    this.botName = botName;
    this.jobId = jobId;
    this.jobName = jobName;
    this.taskId = taskId;
    this.taskName = taskName;
    this.runId = runId;
    this.time = time;
    this.severity = severity;
    this.subject = subject;
    this.detail = detail;
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


  public int getRunId()
  {
    return runId;
  }


  public String getTime()
  {
    return time;
  }


  public String getSeverity()
  {
    return severity;
  }


  public String getSubject()
  {
    return subject;
  }


  public String getDetail()
  {
    return detail;
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
        column = Integer.toString(runId);
        break;
      case 5:
        column = time;
        break;
      case 6:
        column = severity;
        break;
      case 7:
        column = subject;
        break;
      case 8:
        column = detail;
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
        runId = Integer.valueOf(value);
        break;
      case 5:
        time = value;
        break;
      case 6:
        severity = value;
        break;
      case 7:
        subject = value;
        break;
      case 8:
        detail = value;
        break;
      case 9:
        botId = Integer.valueOf(value);
        break;
      case 10:
        jobId = Integer.valueOf(value);
        break;
      case 11:
        taskId = Integer.valueOf(value);
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
        result = runId == ivalue ? 0 : (runId > ivalue ? 1 : -1);
        break;
      case 5:
        result = time.compareTo(value);
        break;
      case 6:
        result = severity.compareTo(value);
        break;
      case 7:
        result = subject.compareTo(value);
        break;
      case 8:
        result = detail.compareTo(value);
        break;
    }
    return result;
  }
}
