package com.boryi.bots.client;

import java.util.ArrayList;

import com.boryi.bots.client.data.BotItem;
import com.boryi.bots.client.data.BotList;
import com.boryi.bots.client.data.DataItem;
import com.boryi.bots.client.data.DataList;
import com.boryi.bots.client.data.EventItem;
import com.boryi.bots.client.data.EventList;
import com.boryi.bots.client.data.GroupItem;
import com.boryi.bots.client.data.GroupList;
import com.boryi.bots.client.data.JobItem;
import com.boryi.bots.client.data.JobList;
import com.boryi.bots.client.data.LogItem;
import com.boryi.bots.client.data.LogList;
import com.boryi.bots.client.data.QaItem;
import com.boryi.bots.client.data.QaList;
import com.boryi.bots.client.data.RequestItem;
import com.boryi.bots.client.data.RequestList;
import com.boryi.bots.client.data.ResponseItem;
import com.boryi.bots.client.data.RowItem;
import com.boryi.bots.client.data.RunItem;
import com.boryi.bots.client.data.RunList;
import com.boryi.bots.client.data.ServerItem;
import com.boryi.bots.client.data.ServerList;
import com.boryi.bots.client.data.TaskItem;
import com.boryi.bots.client.data.TaskList;
import com.boryi.bots.client.data.UserItem;
import com.boryi.bots.client.data.UserList;
import com.boryi.bots.client.data.UserPrivilege;


public class TestData
{
  public static UserList getUserList()
  {
    UserList list = new UserList();

    int total = 30;
    int size = 30;
    list.setTotal(total);
    list.setCurrentPage(1);
    list.setSort("Id");
    list.setSortAsc(true);
    list.setRowsPerPage(50);

    ArrayList<RowItem> items = new ArrayList<RowItem>(size);
    for (int i = 1; i <= size; i++)
    {
      UserItem item = new UserItem(i % 6, "Group name " + (i % 6), i, "User "
          + i, "password " + i, true, "nickname " + i, "2009-09-10 10:00:00",
          "2009-09-20 10:00:00", "2009-09-10 11:00:00", "2009-09-21 10:00:00",
          "12.34.95." + i, i % 5,
          "User description by Boryi Network Information Inc. " + i);

      item.setSelected((i % 2) == 1);
      item.setEnabled((i % 6) == 1);

      items.add(item);
    }
    list.setRows(items);

    return list;
  }


  public static GroupList getGroupList()
  {
    GroupList list = new GroupList();

    int total = 20;
    int size = 20;
    list.setTotal(total);
    list.setCurrentPage(1);
    list.setSort("Id");
    list.setSortAsc(true);
    list.setRowsPerPage(25);

    ArrayList<RowItem> items = new ArrayList<RowItem>(size);
    for (int i = 1; i <= size; i++)
    {
      GroupItem item = new GroupItem(i, "admin", "Group name " + i, true,
          "2009-09-10 10:00:00", "2009-09-21 10:00:00",
          "Group description by Boryi Network Information Inc. " + i);

      item.setSelected((i % 2) == 1);
      switch (i % 4)
      {
        case 0:
          item.setCategory(UserPrivilege.ADMIN);
          break;
        case 1:
          item.setCategory(UserPrivilege.DEVELOPER);
          break;
        case 2:
          item.setCategory(UserPrivilege.USER);
          break;
        case 3:
          item.setCategory(UserPrivilege.GUEST);
          break;
      }

      items.add(item);
    }
    list.setRows(items);

    return list;
  }


  public static ServerList getServerList()
  {
    ServerList list = new ServerList();

    int total = 140;
    int size = 40;
    list.setTotal(total);
    list.setCurrentPage(3);
    list.setSort("id");
    list.setSortAsc(false);
    list.setRowsPerPage(50);

    ArrayList<RowItem> items = new ArrayList<RowItem>(size);
    for (int i = 1; i <= size; i++)
    {
      ServerItem item = new ServerItem(i, "Server name " + i, "10.0.10." + i,
          true, (float) (((i % 4) + 1.0f) / 8.0f),
          (float) (((i % 5) + 1.0f) / 5.0f),
          (float) (((i % 8) + 1.0f) * 10.0f), (float) (i / 2.0),
          (float) (i / 4.0), "2009-09-15 10:00:00", "2009-09-16 18:00:00",
          "Bot detailed description for Bot Id: " + i
              + ", which were developed by Boryi Network Information Inc.");
      item.setSelected((i % 5) == 1);

      items.add(item);
    }
    list.setRows(items);

    return list;
  }


  public static BotList getBotList()
  {
    BotList list = new BotList();

    int total = 600;
    int size = 50;
    list.setTotal(total);
    list.setCurrentPage(6);
    list.setSort("name");
    list.setSortAsc(false);
    list.setRowsPerPage(50);

    ArrayList<RowItem> items = new ArrayList<RowItem>(size);
    for (int i = 1; i <= size; i++)
    {
      BotItem item = new BotItem(i, "Bot name " + i, "v1.0." + i, true,
          "2009-09-15 10:00:00", "2009-09-16 18:00:00", "Bot Jar file " + i,
          "Bot detailed description for Bot Id: " + i
              + ", which were developed by Boryi Network Information Inc.");
      item.setSelected((i % 5) == 1);

      items.add(item);
    }
    list.setRows(items);

    return list;
  }


  public static JobList getJobList()
  {
    JobList list = new JobList();

    int total = 630;
    int size = 30;
    list.setTotal(total);
    list.setCurrentPage(13);
    list.setSort("name");
    list.setSortAsc(false);
    list.setRowsPerPage(50);

    ArrayList<RowItem> items = new ArrayList<RowItem>(size);
    for (int i = 1; i <= size; i++)
    {
      JobItem item = new JobItem(i % 10 + 1, "Bot name " + (i % 10),
          i % 10 + 1, "Job name " + (i % 10), (i % 3) == 1,
          "2009-02-12 21:02:01", "2009-09-21 12:10:11",
          "Job description from Boryi Network Information Inc. " + i,
          "Job_configuration_file_" + i);
      item.setSelected((i % 5) == 1);

      items.add(item);
    }
    list.setRows(items);

    return list;
  }


  public static TaskList getTaskList()
  {
    TaskList list = new TaskList();

    int total = 630;
    int size = 150;
    list.setTotal(total);
    list.setCurrentPage(4);
    list.setSort("name");
    list.setSortAsc(false);
    list.setRowsPerPage(150);

    ArrayList<RowItem> items = new ArrayList<RowItem>(size);
    for (int i = 1; i <= size; i++)
    {
      TaskItem item = new TaskItem(i % 10 + 1, "Bot name " + (i % 10),
          ((i % 10 + 1) % 3 + 1), "Job name " + ((i % 10 + 1) % 3 + 1),
          i % 10 + 1, "Task name " + (i % 10), (i % 10) == 1, i % 60, i % 24,
          i % 7, i % 31, i % 12, (i % 10) == 3, i % 15, "Server name "
              + (i % 15), "2009-02-12 21:02:01", "2009-09-21 12:10:11",
          "Task description from Boryi Network Information Inc. " + i);
      item.setSelected((i % 5) == 1);

      items.add(item);
    }
    list.setRows(items);

    return list;
  }


  public static RunList getRunList()
  {
    RunList list = new RunList();

    int total = 630;
    int size = 150;
    list.setTotal(total);
    list.setCurrentPage(4);
    list.setSort("id");
    list.setSortAsc(false);
    list.setRowsPerPage(150);

    ArrayList<RowItem> items = new ArrayList<RowItem>(size);
    for (int i = 1; i <= size; i++)
    {
      RunItem item = new RunItem(i % 10 + 1, "Bot name " + (i % 10),
          ((i % 10 + 1) % 3 + 1), "Job name " + ((i % 10 + 1) % 3 + 1),
          i % 10 + 1, "Task name " + (i % 10), (i % 10) + 1,
          "2009-02-12 21:02:01", "2009-09-21 12:10:11", (float) ((i % 30) + 1),
          (i % 5), (i % 5) + 1, "Server name " + ((i % 5) + 1));

      items.add(item);
    }
    list.setRows(items);

    return list;
  }


  public static EventList getEventList()
  {
    EventList list = new EventList();

    int total = 630;
    int size = 150;
    list.setTotal(total);
    list.setCurrentPage(4);
    list.setSort("time");
    list.setSortAsc(false);
    list.setRowsPerPage(150);

    ArrayList<RowItem> items = new ArrayList<RowItem>(size);
    for (int i = 1; i <= size; i++)
    {
      EventItem item = new EventItem(i % 10 + 1, "Bot name " + (i % 10),
          ((i % 10 + 1) % 3 + 1), "Job name " + ((i % 10 + 1) % 3 + 1),
          i % 10 + 1, "Task name " + (i % 10), (i % 10) + 1,
          "2009-02-12 21:02:0" + Float.toString(((float) i / 1000.0f)),
          "Warning", "Subject " + i, "Event detail " + ((i % 5) + 1)
              + " from Boryi Network Information Inc.");

      items.add(item);
    }
    list.setRows(items);

    return list;
  }


  public static QaList getQaList()
  {
    QaList list = new QaList();

    int total = 435;
    int size = 50;
    list.setTotal(total);
    list.setCurrentPage(6);
    list.setSort("time");
    list.setSortAsc(false);
    list.setRowsPerPage(50);

    ArrayList<RowItem> items = new ArrayList<RowItem>(size);
    for (int i = 1; i <= size; i++)
    {
      QaItem item = new QaItem(i % 10 + 1, "Bot name " + (i % 10),
          ((i % 10 + 1) % 3 + 1), "Job name " + ((i % 10 + 1) % 3 + 1),
          i % 10 + 1, "Task name " + (i % 10), (i % 10) + 1,
          "2009-02-12 21:02:0" + Float.toString(((float) i / 1000.0f)),
          (i % 10) != 3, "QA detail " + ((i % 5) + 1)
              + " from Boryi Network Information Inc.");

      items.add(item);
    }
    list.setRows(items);

    return list;
  }


  public static DataList getDataList()
  {
    DataList list = new DataList();

    int total = 435;
    int size = 50;
    list.setTotal(total);
    list.setCurrentPage(6);
    list.setSort("time");
    list.setSortAsc(false);
    list.setRowsPerPage(50);

    ArrayList<RowItem> items = new ArrayList<RowItem>(size);
    for (int i = 1; i <= size; i++)
    {
      DataItem item = new DataItem(i, "Data subject " + i, "data_file_" + i,
          "2009-02-12 21:02:0" + Float.toString(((float) i / 1000.0f)),
          "2009-02-17 21:02:0" + Float.toString(((float) i / 1000.0f)),
          "Data description " + ((i % 5) + 1)
              + " from Boryi Network Information Inc.");

      items.add(item);
    }
    list.setRows(items);

    return list;
  }


  public static RequestList getRequestList()
  {
    RequestList list = new RequestList();

    int total = 435;
    int size = 50;
    list.setTotal(total);
    list.setCurrentPage(6);
    list.setSort("time");
    list.setSortAsc(false);
    list.setRowsPerPage(50);

    ArrayList<RowItem> items = new ArrayList<RowItem>(size);
    for (int i = 1; i <= size; i++)
    {
      ArrayList<ResponseItem> rItems = new ArrayList<ResponseItem>();
      for (int j = 0; j < (i % 5); j++)
      {
        ResponseItem rItem = new ResponseItem(j, j, "User name " + j,
            "2009-09-21 21:02:0" + Float.toString(((float) i / 1000.0f)),
            "Response detail " + j
                + " from Developer groups in Boryi Network Information Inc.");
        rItems.add(rItem);
      }
      RequestItem item = new RequestItem(i, i % 5, "User name " + (i % 5),
          "Request title_" + i, "Request status " + i, i, "2009-02-12 21:02:0"
              + Float.toString(((float) i / 1000.0f)), "Request detail "
              + ((i % 5) + 1) + " from Boryi Network Information Inc.", rItems);

      items.add(item);
    }
    list.setRows(items);

    return list;
  }


  public static LogList getLogList()
  {
    LogList list = new LogList();

    int total = 630;
    int size = 150;
    list.setTotal(total);
    list.setCurrentPage(4);
    list.setSort("id");
    list.setSortAsc(false);
    list.setRowsPerPage(150);

    ArrayList<RowItem> items = new ArrayList<RowItem>(size);
    for (int i = 1; i <= size; i++)
    {
      LogItem item = new LogItem(i, "2009-09-10 00.00.0"
          + Float.toString(((float) i / 1000.0f)), "Log subject " + i,
          "Log detail " + i + " from Boryi Network Information Inc.");

      items.add(item);
    }
    list.setRows(items);

    return list;
  }
}
