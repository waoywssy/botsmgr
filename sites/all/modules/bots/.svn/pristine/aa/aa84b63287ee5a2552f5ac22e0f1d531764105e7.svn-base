package com.boryi.bots.client.data;

import java.util.ArrayList;

/**
 * Data bundle of run data
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class DataList extends TableList
{
  public DataList()
  {
    headers = new ArrayList<String>(7);
    headers.add("Select"); // whether the run data is selected
    headers.add("Id");
    headers.add("Subject"); // subject of the run data
    headers.add("File"); // download link for the run data 
    headers.add("Created"); // when the run data file was created
    headers.add("Expire"); // when the run data file will be expired
    headers.add("Description"); // description of the run data

    sort = "subject"; // sort by subject
    // file: sort by filename
    // created: sort by created
    // expire: sort by expire
  }
}
