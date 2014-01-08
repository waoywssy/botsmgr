package com.boryi.bots.client.data;

/**
 * Data bundle for a user
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class UserItem extends RowItem
{
  private int groupId;
  private String groupName;
  private String name;
  private String password;
  private boolean enabled;
  private String nickname;
  private String created;
  private String updated;
  private String firstLogin;
  private String lastLogin;
  private String lastIp;
  private int logins;
  private String description;


  public UserItem()
  {

  }


  public UserItem(int groupId, String groupName, int id, String name,
      String password, boolean enabled, String nickname, String created,
      String updated, String firstLogin, String lastLogin, String lastIp,
      int logins, String description)
  {
    this.groupId = groupId;
    this.groupName = groupName;
    this.id = id;
    this.name = name;
    this.password = password;
    this.enabled = enabled;
    this.nickname = nickname;
    this.created = created;
    this.updated = updated;
    this.firstLogin = firstLogin;
    this.lastLogin = lastLogin;
    this.lastIp = lastIp;
    this.logins = logins;
    this.description = description;
  }


  public int getGroupId()
  {
    return groupId;
  }


  public void setGroupId(int groupId)
  {
    this.groupId = groupId;
  }


  public String getGroupName()
  {
    return groupName;
  }


  public void setGroupName(String groupName)
  {
    this.groupName = groupName;
  }


  public String getName()
  {
    return name;
  }


  public void setName(String name)
  {
    this.name = name;
  }


  public String getPassword()
  {
    return password;
  }


  public void setPassword(String password)
  {
    this.password = password;
  }


  public boolean isEnabled()
  {
    return enabled;
  }


  public void setEnabled(boolean enabled)
  {
    this.enabled = enabled;
  }


  public String getNickname()
  {
    return nickname;
  }


  public void setNickname(String nickname)
  {
    this.nickname = nickname;
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


  public String getFirstLogin()
  {
    return firstLogin;
  }


  public void setFirstLogin(String firstLogin)
  {
    this.firstLogin = firstLogin;
  }


  public String getLastLogin()
  {
    return lastLogin;
  }


  public void setLastLogin(String lastLogin)
  {
    this.lastLogin = lastLogin;
  }


  public String getLastIp()
  {
    return lastIp;
  }


  public void setLastIp(String lastIp)
  {
    this.lastIp = lastIp;
  }


  public int getLogins()
  {
    return logins;
  }


  public void setLogins(int logins)
  {
    this.logins = logins;
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
        column = groupName;
        break;
      case 2:
        column = Integer.toString(id);
        break;
      case 3:
        column = name;
        break;
      case 4:
        column = password;
        break;
      case 5:
        column = Boolean.toString(enabled);
        break;
      case 6:
        column = nickname;
        break;
      case 7:
        column = created;
        break;
      case 8:
        column = updated;
        break;
      case 9:
        column = firstLogin;
        break;
      case 10:
        column = lastLogin;
        break;
      case 11:
        column = lastIp;
        break;
      case 12:
        column = Integer.toString(logins);
        break;
      case 13:
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
        groupName = value;
        break;
      case 2:
        id = Integer.valueOf(value);
        break;
      case 3:
        name = value;
        break;
      case 4:
        password = value;
        break;
      case 5:
        enabled = Boolean.valueOf(value);
        break;
      case 6:
        nickname = value;
        break;
      case 7:
        created = value;
        break;
      case 8:
        updated = value;
        break;
      case 9:
        firstLogin = value;
        break;
      case 10:
        lastLogin = value;
        break;
      case 11:
        lastIp = value;
        break;
      case 12:
        logins = Integer.valueOf(value);
        break;
      case 13:
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
        result = groupName.compareTo(value);
        break;
      case 2:
        int ivalue = Integer.valueOf(value);
        result = id == ivalue ? 0 : (id > ivalue ? 1 : -1);
        break;
      case 3:
        result = name.compareTo(value);
        break;
      case 4:
        result = password.compareTo(value);
        break;
      case 5:
        result = enabled == Boolean.valueOf(value) ? 0 : (enabled ? 1 : -1);
        break;
      case 6:
        result = created.compareTo(value);
        break;
      case 7:
        result = updated.compareTo(value);
        break;
      case 8:
        result = firstLogin.compareTo(value);
        break;
      case 9:
        result = lastLogin.compareTo(value);
        break;
      case 10:
        result = lastIp.compareTo(value);
        break;
      case 11:
        int jvalue = Integer.valueOf(value);
        result = logins == jvalue ? 0 : (logins > jvalue ? 1 : -1);
        break;
      case 12:
        result = description.compareTo(value);
        break;
    }
    return result;
  }
}
