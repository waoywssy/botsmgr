package com.boryi.bots.client.data;

import java.util.ArrayList;


/**
 * Data bundle for events
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class EventList extends TableList
{
  public EventList()
  {
    headers = new ArrayList<String>(9);
    headers.add("Select"); // whether the event is selected
    headers.add("Bot"); // name of the bot
    headers.add("Job"); // name of the job
    headers.add("Task"); // name of the task
    headers.add("RunId"); // id of the run
    headers.add("Time"); // when the event happened
    headers.add("Severity"); // severity of the event
    headers.add("Subject"); // subject of the event
    headers.add("Detail"); // details of the event

    sort = "time"; // sort by bot id, job id, task id, run id, then time of the
                 // event
    // severity: sort by bot id, job id, task id, run id, severity, event time
    // subject: sort by bot id, job id, task id, run id, subject, event time 
  }
}
