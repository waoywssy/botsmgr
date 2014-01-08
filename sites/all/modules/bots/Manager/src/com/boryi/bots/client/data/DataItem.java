package com.boryi.bots.client.data;

/**
 * Data bundle for a run data
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class DataItem extends RowItem
{
  private String subject;
  private String file;
  private String created;
  private String expire;
  private String description;

  public DataItem()
  {
  }
  
  public DataItem(int id, String subject, String file, String created, String expire, String description)
  {
    this.id = id;
    this.subject = subject;
    this.file = file;
    this.created = created;
    this.expire = expire;
    this.description = description;
  }
  
  public String getSubject()
  {
    return subject;
  }


  public String getFile()
  {
    return file;
  }


  public String getCreated()
  {
    return created;
  }


  public String getExpire()
  {
    return expire;
  }


  public String getDescription()
  {
    return description;
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
        column = subject;
        break;
      case 3:
        column = file;
        break;
      case 4:
        column = created;
        break;
      case 5:
        column = expire;
        break;
      case 6:
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
        id = Integer.valueOf(value);
        break;
      case 2:
        subject = value;
        break;
      case 3:
        file = value;
        break;
      case 4:
        created = value;
        break;
      case 5:
        expire = value;
        break;
      case 6:
        description = value;
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
        int ivalue = Integer.valueOf(value);
        result = id == ivalue ? 0 : (id > ivalue ? 1 : -1);
        break;
      case 2:
        result = subject.compareTo(value);
        break;
      case 3:
        result = file.compareTo(value);
        break;
      case 4:
        result = created.compareTo(value);
        break;
      case 5:
        result = expire.compareTo(value);
        break;
      case 6:
        result = description.compareTo(value);
        break;
    }
    return result;
  }
}
