package com.boryi.bots.client.data;

import java.util.ArrayList;


/**
 * Data bundle for runs
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class RunList extends TableList
{
  public RunList()
  {
    headers = new ArrayList<String>(10);
    headers.add("Select"); // whether the run is selected
    headers.add("Bot"); // name of the bot
    headers.add("Job"); // name of the job
    headers.add("Task"); // name of the task
    headers.add("Id"); // id of the job
    headers.add("Start"); // when the run was started
    headers.add("End"); // when the run was finished
    headers.add("Duration"); // duration of the run
    headers.add("Result"); // result of the run, success or failed or running or
                           // resume_success or resume_failed
    headers.add("Server"); // name of the server that the run was running on

    sort = "id"; // sort by bot id, job id, task id, then run id
    // start: sort by bot id, job id, task id, start (, run id)
    // result: sort by bot id, job id, task id, result, run id
  }
}
