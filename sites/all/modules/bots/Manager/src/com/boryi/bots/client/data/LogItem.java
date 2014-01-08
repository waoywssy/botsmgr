package com.boryi.bots.client.data;

/**
 * Data bundle for a log
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class LogItem extends RowItem
{
  private String time;
  private String subject;
  private String detail;

  
  public LogItem()
  {
    
  }
  
  
  public LogItem(int id, String time, String subject, String detail)
  {
    this.id = id;
    this.time = time;
    this.subject = subject;
    this.detail = detail;
  }
  
  
  public String getTime()
  {
    return time;
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
        column = Integer.toString(id);
        break;
      case 2:
        column = time;
        break;
      case 3:
        column = subject;
        break;
      case 4:
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
        time = value;
        break;
      case 2:
        subject = value;
        break;
      case 3:
        detail = value;
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
        result = time.compareTo(value);
        break;
      case 3:
        result = subject.compareTo(value);
        break;
      case 5:
        result = detail.compareTo(value);
        break;
    }
    return result;
  }
}
