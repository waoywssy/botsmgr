package com.boryi.bots.client.data;

import java.util.ArrayList;


/**
 * Data bundle for QA results
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class QaList extends TableList
{
  public QaList()
  {
    headers = new ArrayList<String>(8);
    headers.add("Select"); // whether the QA result is selected
    headers.add("Bot"); // name of the bot
    headers.add("Job"); // name of the job
    headers.add("Task"); // name of the task
    headers.add("RunId"); // id of the run
    headers.add("Time"); // when the run started
    headers.add("Valid"); // whether the run data is valid based on QA
    headers.add("Detail"); // display the result from bot's QA table, separate
                           // the columns by comma

    sort = "id"; // sort by bot id, job id, task id, run id
    // time: sort by time, bot id, job id, task id, run id
    // valid: sort by valid, bot id, job id, task id, run id
  }
}
