package com.boryi.bots.client.data;

import java.util.ArrayList;

/**
 * Data bundle of requests
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class RequestList extends TableList
{
  public RequestList()
  {
    headers = new ArrayList<String>(8);
    headers.add("Select"); // whether the request is selected
    headers.add("Id"); // id of the user
    headers.add("User"); // name of the user
    headers.add("Title"); // title of the request
    headers.add("Status"); // status of the request
    headers.add("Progress"); // progress of the request
    headers.add("Created"); // when the request was created
    headers.add("Detail"); // detail of the request

    sort = "id"; // sort by request id
    // user: sort by  
    // title: sort by 
    // status: sort by   
    // created: sort by created
  }
}
