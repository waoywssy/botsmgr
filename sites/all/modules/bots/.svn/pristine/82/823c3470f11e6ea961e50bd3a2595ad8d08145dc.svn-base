package com.boryi.bots.client.data;

import java.util.ArrayList;

/**
 * Data bundle for jobs
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class JobList extends TableList
{
  public JobList()
  {
    headers = new ArrayList<String>(8);
    headers.add("Select"); // whether the job is selected
    headers.add("Bot"); // name of the bot
    headers.add("Id"); // id of the job
    headers.add("Name"); // name of the job
    headers.add("Enabled"); // whether the job is enabled: Y / N
    headers.add("Created"); // when was the job created
    headers.add("Updated"); // when was the job last updated
    headers.add("Description"); // Brief description
  
    sort = "id"; // sort by bot id and then job id
    // name: sort by bot id, name (, job id)
    // enabled: sort by bot id, enabled, job id
    // created: sort by bot id, created, job id
    // updated: sort by bot id, updated, job id
  }
}
