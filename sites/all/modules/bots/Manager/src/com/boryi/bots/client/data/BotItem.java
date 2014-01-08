package com.boryi.bots.client.data;

/**
 * Data bundle for a bot
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class BotItem extends RowItem
{
  private String name;
  private String version;
  private boolean enabled = true;
  private String created;
  private String updated;
  private String description;
  private String filename;


  /**
   * Constructs an instance of bot item
   */
  public BotItem()
  {

  }


  /**
   * Constructs an instance of bot item
   */
  public BotItem(int id, String name, String version, boolean enabled, String created,
      String updated, String filename, String description)
  {
    this.id = id;
    this.name = name;
    this.version = version;
    this.enabled = enabled;
    this.created = created;
    this.updated = updated;
    this.description = description;
    this.filename = filename;
  }


  /**
   * Get the name of the bot
   * 
   * @return the name of the bot
   */
  public String getName()
  {
    return name;
  }


  /**
   * Set the nam of the bot
   * 
   * @param name
   *          the nam of the bot
   */
  public void setName(String name)
  {
    this.name = name;
  }


  /**
   * Get the version of the bot
   * 
   * @return the version of the bot
   */
  public String getVersion()
  {
    return version;
  }


  /**
   * Set the version of the bot
   * 
   * @param version
   *          the version of the bot
   */
  public void setVersion(String version)
  {
    this.version = version;
  }


  /**
   * Get the description of the bot
   * 
   * @return the description of the bot
   */
  public String getDescription()
  {
    return description;
  }


  /**
   * Set the description of the bot
   * 
   * @param description
   *          the description of the bot
   */
  public void setDescription(String description)
  {
    this.description = description;
  }


  /**
   * Whether the bot is enabled
   * 
   * @return whether the bot is enabled
   */
  public boolean isEnabled()
  {
    return enabled;
  }


  /**
   * Set whether the bot is enabled
   * 
   * @param enabled
   *          whether the bot is enabled
   */
  public void setEnabled(boolean enabled)
  {
    this.enabled = enabled;
  }


  /**
   * Get when the bot was last updated
   * 
   * @return when the bot was last updated
   */
  public String getUpdated()
  {
    return updated;
  }


  /**
   * Set when the bot was last updated
   * 
   * @param updated
   *          when the bot was last updated
   */
  public void setUpdated(String updated)
  {
    this.updated = updated;
  }


  /**
   * Get when the bot was created
   * 
   * @return when the bot was created
   */
  public String getCreated()
  {
    return created;
  }


  /**
   * Set when the bot was created
   * 
   * @param created
   *          when the bot was created
   */
  public void setCreated(String created)
  {
    this.created = created;
  }


  /**
   * Get the bot filename on the server
   * 
   * @return the bot filename on the server
   */
  public String getFilename()
  {
    return filename;
  }


  /**
   * Set the bot filename on the server
   * 
   * @param filename
   *          the bot filename on the server
   */
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
        column = Integer.toString(id);
        break;
      case 2:
        column = name;
        break;
      case 3:
        column = version;
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
        id = Integer.valueOf(value);
        break;
      case 2:
        name = value;
        break;
      case 3:
        version = value;
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
        int ivalue = Integer.valueOf(value);
        result = id == ivalue ? 0 : (id > ivalue ? 1 : -1);
        break;
      case 2:
        result = name.compareTo(value);
        break;
      case 3:
        result = version.compareTo(value);
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
