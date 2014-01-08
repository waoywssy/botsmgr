package com.boryi.bots.client.data;

import java.util.ArrayList;

/**
 * Data bundle for tasks
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class TaskList extends TableList
{
  public TaskList()
  {
    headers = new ArrayList<String>(16);
    headers.add("Select"); // whether the task is selected
    headers.add("Bot"); // name of the bot
    headers.add("Job"); // name of the job
    headers.add("Id"); // id of the task
    headers.add("Name"); // name of the task
    headers.add("Enabled"); // whether the task is enabled: Y / N
    headers.add("Minute"); // Minute of the hour: 0 - 59
    headers.add("Hour"); // Hour of the day: 0 - 23
    headers.add("DayOfWeek"); // Day of the week: 0 - 6 (0 means Sunday)
    headers.add("DayOfMonth"); // Day of the month: 1 - 31
    headers.add("Month"); // Month of the year: 1 - 12
    headers.add("Running"); // Is the task running?
    headers.add("Server"); // name of the server the task is running
    headers.add("Created"); // when was the task created
    headers.add("Updated"); // when was the task last updated
    headers.add("Description"); // Brief description
  
    sort = "botid"; // sort by bot id, job id, then task id
    // name: sort by bot id, job id, name (, task id)
    // enabled: sort by bot id, job id, enabled, task id
    // created: sort by bot id, job id, created, task id
    // updated: sort by bot id, job id, updated, task id
  }
}
