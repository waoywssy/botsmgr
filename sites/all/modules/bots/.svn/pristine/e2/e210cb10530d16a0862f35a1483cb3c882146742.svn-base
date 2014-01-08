package com.boryi.bots.client.data;

import java.util.ArrayList;


/**
 * Data bundle for a user group
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class GroupItem extends RowItem
{
  private UserPrivilege category = UserPrivilege.USER;
  private String categoryString = "User";
  private String name;
  private boolean enabled = true;
  private String created;
  private String updated;
  private String description;
  /**
   * Ids of bots accessible by the group
   */
  private ArrayList<Integer> bots = null;


  /**
   * Constructs an instance of group item
   */
  public GroupItem()
  {
  }


  public GroupItem(int id, String categoryString, String name, boolean enabled,
      String created, String updated, String description)
  {
    this.id = id;
    setCategoryString(categoryString);
    this.name = name;
    this.enabled = enabled;
    this.created = created;
    this.updated = updated;
    this.description = description;
  }


  /**
   * Get group category type
   * 
   * @return group category type
   */
  public UserPrivilege getCategory()
  {
    return category;
  }


  /**
   * Set group category type
   * 
   * @param category
   *          group category type
   */
  public void setCategory(UserPrivilege category)
  {
    this.category = category;

    switch (category)
    {
      case ADMIN:
        categoryString = "Admin";
        break;
      case DEVELOPER:
        categoryString = "Dev";
        break;
      case USER:
        categoryString = "User";
        break;
      case GUEST:
        categoryString = "Guest";
        break;
    }
  }


  /**
   * Get group category string
   * 
   * @return group category string
   */
  public String getCategoryString()
  {
    return categoryString;
  }


  /**
   * Set group category string
   * 
   * @param categoryString
   *          group category string
   */
  public void setCategoryString(String categoryString)
  {
    this.categoryString = categoryString;

    String value = categoryString.toLowerCase();
    if (value == "admin")
    {
      category = UserPrivilege.ADMIN;
    }
    else if (value == "dev")
    {
      category = UserPrivilege.DEVELOPER;
    }
    else if (value == "user")
    {
      category = UserPrivilege.USER;
    }
    else if (value == "guest")
    {
      category = UserPrivilege.GUEST;
    }
  }


  /**
   * Get group name
   * 
   * @return group name
   */
  public String getName()
  {
    return name;
  }


  /**
   * Set group name
   * 
   * @param name
   *          group name
   */
  public void setName(String name)
  {
    this.name = name;
  }


  /**
   * Get group description
   * 
   * @return group description
   */
  public String getDescription()
  {
    return description;
  }


  /**
   * Set group description
   * 
   * @param description
   *          group description
   */
  public void setDescription(String description)
  {
    this.description = description;
  }


  /**
   * If the group is enabled
   * 
   * @return if the group is enabled
   */
  public boolean isEnabled()
  {
    return enabled;
  }


  /**
   * Set whether to enable the group
   * 
   * @param enabled
   *          whether to enable the group
   */
  public void setEnabled(boolean enabled)
  {
    this.enabled = enabled;
  }


  /**
   * Get the date and time that the group was created
   * 
   * @return the date and time that the group was created
   */
  public String getCreated()
  {
    return created;
  }


  /**
   * Set the date and time that the group was updated
   * 
   * @return the date and time that the group was updated
   */
  public String getUpdated()
  {
    return updated;
  }


  /**
   * Get ids of bots that the group have accessibility to. Admin group has
   * accessibility to all the bots.
   * 
   * @return ids of bots that the group have accessibility to
   */
  public ArrayList<Integer> getBots()
  {
    return bots;
  }


  /**
   * Get a csv string representation of the bots
   * 
   * @return a csv string representation of the bots
   */
  public String getBotsString()
  {
    if (bots == null)
    {
      return "";
    }
    String botsString = "";
    for (int i = 0; i < bots.size(); i++)
    {
      botsString += ", " + bots.get(i).toString();
    }
    if (botsString.isEmpty())
    {
      return "";
    }
    return botsString.substring(2);
  }


  /**
   * Set ids of bots that the group have accessibility to. Admin group has
   * accessibility to all the bots.
   * 
   * @param bots
   *          ids of bots that the group have accessibility to
   */
  public void setBots(ArrayList<Integer> bots)
  {
    this.bots = bots;
  }


  /**
   * Set the bots by given the csv string representation of the bots
   * 
   * @param botsString
   *          the csv string representation of the bots
   */
  public void setBotsString(String botsString)
  {
    String[] bots = botsString.split(",\\s");
    this.bots = new ArrayList<Integer>(bots.length);
    for (int i = 0; i < bots.length; i++)
    {
      this.bots.add(Integer.valueOf(bots[i]));
    }
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
        column = categoryString;
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
        id = Integer.valueOf(value);
        break;
      case 2:
        categoryString = value;
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
        result = categoryString.compareTo(value);
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
