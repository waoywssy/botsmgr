package com.boryi.bots.client.data;

/**
 * Data bundle for a task
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class TaskItem extends RowItem
{
  private int botId;
  private String botName;
  private int jobId;
  private String jobName;
  
  private String name;
  private boolean enabled = true;
  
  private int minute;
  private int hour;
  private int dayOfWeek;
  private int dayOfMonth;
  private int month;
  
  private boolean running;
  private int serverId;
  private String serverName;
  private String created;
  private String updated;
  private String description;

  
  public TaskItem()
  {
    
  }
  
  
  public TaskItem(int botId, String botName, int jobId, String jobName,
      int id, String name, boolean enabled, int minute, int hour, int dayOfWeek,
      int dayOfMonth, int month, boolean running, int serverId, String serverName,
      String created, String updated, String description)
  {
    this.botId = botId;
    this.botName = botName;
    this.jobId = jobId;
    this.jobName = jobName;
    this.id = id;
    this.name = name;
    this.enabled = enabled;
    
    this.minute = minute;
    this.hour = hour;
    this.dayOfWeek = dayOfWeek;
    this.dayOfMonth = dayOfMonth;
    this.month = month;
    
    this.running = running;
    this.serverId = serverId;
    this.serverName = serverName;
    this.created = created;
    this.updated = updated;
    this.description = description;
  }
  
  
  public int getBotId()
  {
    return botId;
  }


  public void setBotId(int botId)
  {
    this.botId = botId;
  }


  public String getBotName()
  {
    return botName;
  }


  public void setBotName(String botName)
  {
    this.botName = botName;
  }


  public int getJobId()
  {
    return jobId;
  }


  public void setJobId(int jobId)
  {
    this.jobId = jobId;
  }


  public String getJobName()
  {
    return jobName;
  }


  public void setJobName(String jobName)
  {
    this.jobName = jobName;
  }


  public String getName()
  {
    return name;
  }


  public void setName(String name)
  {
    this.name = name;
  }


  public boolean isEnabled()
  {
    return enabled;
  }


  public void setEnabled(boolean enabled)
  {
    this.enabled = enabled;
  }


  public int getMinute()
  {
    return minute;
  }


  public void setMinute(int minute)
  {
    this.minute = minute;
  }


  public int getHour()
  {
    return hour;
  }


  public void setHour(int hour)
  {
    this.hour = hour;
  }


  public int getDayOfWeek()
  {
    return dayOfWeek;
  }


  public void setDayOfWeek(int dayOfWeek)
  {
    this.dayOfWeek = dayOfWeek;
  }


  public int getDayOfMonth()
  {
    return dayOfMonth;
  }


  public void setDayOfMonth(int dayOfMonth)
  {
    this.dayOfMonth = dayOfMonth;
  }


  public int getMonth()
  {
    return month;
  }


  public void setMonth(int month)
  {
    this.month = month;
  }


  public boolean isRunning()
  {
    return running;
  }


  public void setRunning(boolean running)
  {
    this.running = running;
  }


  public int getServerId()
  {
    return serverId;
  }


  public void setServerId(int serverId)
  {
    this.serverId = serverId;
  }


  public String getServerName()
  {
    return serverName;
  }


  public void setServerName(String serverName)
  {
    this.serverName = serverName;
  }


  public String getCreated()
  {
    return created;
  }


  public void setCreated(String created)
  {
    this.created = created;
  }


  public String getUpdated()
  {
    return updated;
  }


  public void setUpdated(String updated)
  {
    this.updated = updated;
  }


  public String getDescription()
  {
    return description;
  }


  public void setDescription(String description)
  {
    this.description = description;
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
        column = Integer.toString(id);
        break;
      case 4:
        column = name;
        break;
      case 5:
        column = Boolean.toString(enabled);
        break;
      case 6:
        column = Integer.toString(minute);
        break;
      case 7:
        column = Integer.toString(hour);
        break;
      case 8:
        column = Integer.toString(dayOfWeek);
        break;
      case 9:
        column = Integer.toString(dayOfMonth);
        break;
      case 10:
        column = Integer.toString(month);
        break;
      case 11:
        column = Boolean.toString(running);
        break;
      case 12:
        column = serverName;
        break;
      case 13:
        column = created;
        break;
      case 14:
        column = updated;
        break;
      case 15:
        column = description;
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
        id = Integer.valueOf(value);
        break;
      case 4:
        name = value;
        break;
      case 5:
        enabled = Boolean.valueOf(value);
        break;
      case 6:
        minute = Integer.valueOf(value);
        break;
      case 7:
        hour = Integer.valueOf(value);
        break;
      case 8:
        dayOfWeek = Integer.valueOf(value);
        break;
      case 9:
        dayOfMonth = Integer.valueOf(value);
        break;
      case 10:
        month = Integer.valueOf(value);
        break;
      case 11:
        running = Boolean.valueOf(value);
        break;
      case 12:
        serverName = value;
        break;
      case 13:
        created = value;
        break;
      case 14:
        updated = value;
        break;
      case 15:
        description = value;
        break;
      case 16:
        botId = Integer.valueOf(value);
        break;
      case 17:
        jobId = Integer.valueOf(value);
        break;
      case 18:
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
        int ivalue = Integer.valueOf(value);
        result = id == ivalue ? 0 : (id > ivalue ? 1 : -1);
        break;
      case 4:
        result = name.compareTo(value);
        break;
      case 5:
        result = enabled == Boolean.valueOf(value) ? 0 : (enabled ? 1 : -1);
        break;
      case 6:
        int iminute = Integer.valueOf(value);
        result = minute == iminute ? 0 : (minute > iminute ? 1 : -1);
        break;
      case 7:
        int ihour = Integer.valueOf(value);
        result = hour == ihour ? 0 : (hour > ihour ? 1 : -1);
        break;
      case 8:
        int idayofweek = Integer.valueOf(value);
        result = dayOfWeek == idayofweek ? 0 : (dayOfWeek > idayofweek ? 1 : -1);
        break;
      case 9:
        int idayofmonth = Integer.valueOf(value);
        result = dayOfMonth == idayofmonth ? 0 : (dayOfMonth > idayofmonth ? 1 : -1);
        break;
      case 10:
        int imonth = Integer.valueOf(value);
        result = month == imonth ? 0 : (month > imonth ? 1 : -1);
        break;
      case 11:
        result = running == Boolean.valueOf(value) ? 0 : (running ? 1 : -1);
        break;
     case 12:
        result = serverName.compareTo(value);
        break;
     case 13:
       result = created.compareTo(value);
       break;
     case 14:
       result = updated.compareTo(value);
       break;
     case 15:
       result = description.compareTo(value);
       break;
    }
    return result;
   }
}
