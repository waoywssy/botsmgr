package com.boryi.bots.client;

import java.util.Date;

import com.boryi.bots.client.data.BotItem;
import com.boryi.bots.client.data.BotList;
import com.boryi.bots.client.data.DataList;
import com.boryi.bots.client.data.EventList;
import com.boryi.bots.client.data.GroupItem;
import com.boryi.bots.client.data.GroupList;
import com.boryi.bots.client.data.JobItem;
import com.boryi.bots.client.data.JobList;
import com.boryi.bots.client.data.LogList;
import com.boryi.bots.client.data.QaList;
import com.boryi.bots.client.data.RequestItem;
import com.boryi.bots.client.data.RequestList;
import com.boryi.bots.client.data.ResponseItem;
import com.boryi.bots.client.data.RunList;
import com.boryi.bots.client.data.ServerItem;
import com.boryi.bots.client.data.ServerList;
import com.boryi.bots.client.data.TaskItem;
import com.boryi.bots.client.data.TaskList;
import com.boryi.bots.client.data.UserItem;
import com.boryi.bots.client.data.UserList;
import com.boryi.bots.client.data.UserPrivilege;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;


/**
 * Communicate with the server to retrieve the data. Retrieve table list in JSON
 * format. Insert or update row item in GET method
 * http://www.boryi.com:8888/bbsm/?q=
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class Communicator
{
  public final static String COOKIE_LOGIN_SESSION = "LoginSession";
  public final static String COOKIE_LOGIN_USER = "LoginUser";
  
  private final static String LOGIN_FAILED = "Server is busy, please try again.";

  //private final static String URL = "";

  private static Communicator singleton;

  /**
   * Session string to check if the user has been logged in
   */
  private String loginSession;
  /**
   * Name of logged in user
   */
  private String loginUser;
  /**
   * User privilege to view menus and use the system
   */
  private UserPrivilege userPrivilege;
  
  private UserList userList;
  private GroupList groupList;
  private ServerList serverList;
  private BotList botList;
  private JobList jobList;
  private TaskList taskList;
  private RunList runList;
  private EventList eventList;
  private QaList qaList;
  private DataList dataList;
  private RequestList requestList;
  private LogList logList;


  private Communicator()
  {

  }


  public static Communicator get()
  {
    if (singleton == null)
    {
      singleton = new Communicator();
    }
    return singleton;
  }


  /**
   * Login and set cookie, get user privilege. Save the login session and user
   * to cookies
   * 
   * @param username user name to be logged in
   * @param password password of the user
   */
  public boolean login(String username, String password)
  {
    // TODO send login request to the server
    if (username.equals("admin"))
    {
      userPrivilege = UserPrivilege.ADMIN;
      loginSession = "123456789";
      loginUser = "Administrator";
    }
    else if (username.equals("developer"))
    {
      userPrivilege = UserPrivilege.DEVELOPER;
      loginSession = "234567891";
      loginUser = "Developer";
    }
    else if (username.equals("user"))
    {
      userPrivilege = UserPrivilege.USER;
      loginSession = "345678912";
      loginUser = "User";
    }
    else if (username.equals("guest"))
    {
      userPrivilege = UserPrivilege.GUEST;
      loginSession = "456789123";
      loginUser = "Guest";
    }
    else
    {
      Window.alert(LOGIN_FAILED);
      return false;
    }
    
    // if network exception
    // Window.alert(LOGIN_FAILED);
    
    // Make the cookie expire in one day
    Date expires = new Date(new Date().getTime() + (24 * 60 * 60 * 1000));
    Cookies.setCookie(COOKIE_LOGIN_SESSION, loginSession, expires);
    Cookies.setCookie(COOKIE_LOGIN_USER, loginUser, expires);
    
    return true;
  }


  /**
   * Verify if the login session string is valid, the user is logged in by
   * sending the session string to the server. Get the user privilege.
   * 
   * @return true if the login session is valid.
   */
  public boolean verifyLogin()
  {
    loginSession = Cookies.getCookie(COOKIE_LOGIN_SESSION);
    loginUser = Cookies.getCookie(COOKIE_LOGIN_USER);

    if (loginSession != null && !loginSession.trim().isEmpty())
    {
      // TODO: Send the session cookie to server to see if the user is valid.
      // And get the user privilege if it is valid
      
      if (loginSession.equals("123456789"))
      {
        userPrivilege = UserPrivilege.ADMIN;
      }
      else if (loginSession.equals("234567891"))
      {
        userPrivilege = UserPrivilege.DEVELOPER;
      }
      else if (loginSession.equals("345678912"))
      {
        userPrivilege = UserPrivilege.USER;
      }
      else if (loginSession.equals("456789123"))
      {
        userPrivilege = UserPrivilege.GUEST;
      }
      else
      {
        return false;
      }
      return true;
    }
    return false;
  }


  public UserList getUserList()
  {
    userList = TestData.getUserList();
    return userList;
  }


  public GroupList getGroupList()
  {
    groupList = TestData.getGroupList();
    return groupList;
  }


  public ServerList getServerList()
  {
    serverList = TestData.getServerList();
    return serverList;
  }


  public BotList getBotList()
  {
    botList = TestData.getBotList();
    return botList;
  }


  public JobList getJobList()
  {
    jobList = TestData.getJobList();
    return jobList;
  }


  public TaskList getTaskList()
  {
    taskList = TestData.getTaskList();
    return taskList;
  }


  public RunList getRunList()
  {
    runList = TestData.getRunList();
    return runList;
  }
  
  
  public EventList getEventList()
  {
    eventList = TestData.getEventList();
    return eventList;
  }
  
  
  public QaList getQaList()
  {
    qaList = TestData.getQaList();
    return qaList;
  }


  public DataList getDataList()
  {
    dataList = TestData.getDataList();
    return dataList;
  }


  public RequestList getRequestList()
  {
    requestList = TestData.getRequestList();
    return requestList;
  }


  public LogList getLogList()
  {
    logList = TestData.getLogList();
    return logList;
  }


  public void saveUserItem(UserItem item)
  {

  }


  public void saveGroupItem(GroupItem item)
  {

  }


  public void saveServerItem(ServerItem item)
  {

  }


  public void saveBotItem(BotItem item)
  {

  }


  public void saveJobItem(JobItem item)
  {

  }


  public void saveTaskItem(TaskItem item)
  {

  }


  public void saveRequestItem(RequestItem item)
  {

  }


  public void saveResponseItem(ResponseItem item)
  {

  }

  
  public String getLoginUser()
  {
    return loginUser;
  }
  

  public UserPrivilege getUserPrivilege()
  {
    return userPrivilege;
  }
}
