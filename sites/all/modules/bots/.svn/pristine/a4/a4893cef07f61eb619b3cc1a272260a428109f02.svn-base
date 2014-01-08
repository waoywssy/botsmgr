package com.boryi.bots.client.data;

/**
 * Data bundle for a job
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class JobItem extends RowItem
{
  private int botId;
  private String botName;
  private String name;
  private boolean enabled;
  private String created;
  private String updated;
  private String description;
  private String filename;


  public JobItem()
  {

  }


  public JobItem(int botId, String botName, int id, String name,
      boolean enabled, String created, String updated, String description,
      String filename)
  {
    this.botId = botId;
    this.botName = botName;
    this.id = id;
    this.name = name;
    this.enabled = enabled;
    this.created = created;
    this.updated = updated;
    this.description = description;
    this.filename = filename;
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


  public String getFilename()
  {
    return filename;
  }


  public void setFilename(String filename)
  {
    this.filename = filename;
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
        column = Integer.toString(id);
        break;
      case 3:
        column = name;
        break;
      case 4:
        column = Boolean.toString(enabled);
        break;
      case 5:
        column = created;
        break;
      case 6:
        column = updated;
        break;
      case 7:
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
        id = Integer.valueOf(value);
        break;
      case 3:
        name = value;
        break;
      case 4:
        enabled = Boolean.valueOf(value);
        break;
      case 5:
        created = value;
        break;
      case 6:
        updated = value;
        break;
      case 7:
        description = value;
        break;
      case 8:
        filename = value;
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
        int ivalue = Integer.valueOf(value);
        result = id == ivalue ? 0 : (id > ivalue ? 1 : -1);
        break;
      case 3:
        result = name.compareTo(value);
        break;
      case 4:
        result = enabled == Boolean.valueOf(value) ? 0 : (enabled ? 1 : -1);
        break;
      case 5:
        result = created.compareTo(value);
        break;
      case 6:
        result = updated.compareTo(value);
        break;
      case 7:
        result = description.compareTo(value);
        break;
    }
    return result;
  }
}
