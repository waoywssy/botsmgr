package com.boryi.bots.client.data;

import java.util.ArrayList;

/**
 * Data bundle of logs
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class LogList extends TableList
{
  public LogList()
  {
    headers = new ArrayList<String>(5);
    headers.add("Select"); // whether the log is selected
    headers.add("Id"); // id of the log
    headers.add("Time"); // when the log was written 
    headers.add("Subject"); // subject of the log
    headers.add("Detail"); // detail of the log

    sort = "id"; // sort by id
    // subject: sort by subject
  }
}
