package com.boryi.bots.client.data;

import java.util.ArrayList;


/**
 * Data bundle for a request
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class RequestItem extends RowItem
{
  private int userId;
  private String username;
  private String title;
  private String status;
  private int progress;
  private String created;
  private String detail;

  private ArrayList<ResponseItem> responses; // responses must be ordered chronological


  public RequestItem()
  {
    this.responses = new ArrayList<ResponseItem>();
  }


  public RequestItem(int id, int userId, String username, String title,
      String status, int progress, String created, String detail,
      ArrayList<ResponseItem> responses)
  {
    this.id = id;
    this.userId = userId;
    this.username = username;
    this.title = title;
    this.status = status;
    this.progress = progress;
    this.created = created;
    this.detail = detail;
    
    if (responses == null)
    {
      this.responses = new ArrayList<ResponseItem>();
    }
    else
    {
      this.responses = responses;
    }
  }


  public int getUserId()
  {
    return userId;
  }


  public String getUsername()
  {
    return username;
  }


  public String getTitle()
  {
    return title;
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }


  public String getStatus()
  {
    return status;
  }

  
  public void setStatus(String status)
  {
    this.status = status;
  }
  

  public int getProgress()
  {
    return progress;
  }


  public void setProgress(int progress)
  {
    this.progress = progress;
  }
  
  
  public String getCreated()
  {
    return created;
  }


  public String getDetail()
  {
    return detail;
  }
  
  
  public void setDetail(String detail)
  {
    this.detail = detail;
  }

  
  public ArrayList<ResponseItem> getResponses()
  {
    return responses;
  }
  

  public String getContext()
  {
    String context = "";
    
    for (int i = 0; i < responses.size(); i++)
    {
      context = responses.get(i).toString() + "<br/><br/>" + context;
    }
    
    return context;
  }
  

  @Override
  public String getColumn(int index)
  {
    String column = "";
    switch (index)
    {
      case 0:
        column = Boolean.toString(selected);
        break;
      case 1:
        column = Integer.toString(id);
        break;
      case 2:
        column = username;
        break;
      case 3:
        column = title;
        break;
      case 4:
        column = status;
        break;
      case 5:
        column = Integer.toString(progress);
        break;
      case 6:
        column = created;
        break;
      case 7:
        column = detail;
        break;
    }
    return column;
  }


  @Override
  public void setColumn(int index, String value)
  {
    switch (index)
    {
      case 0:
        selected = Boolean.valueOf(value);
        break;
      case 1:
        id = Integer.valueOf(value);
        break;
      case 2:
        username = value;
        break;
      case 3:
        title = value;
        break;
      case 4:
        status = value;
        break;
      case 5:
        progress = Integer.valueOf(value);
        break;
      case 6:
        created = value;
        break;
      case 7:
        detail = value;
        break;
    }
  }


  @Override
  public int compareColumn(int index, String value)
  {
    int result = 0;
    switch (index)
    {
      case 0:
        result = selected == Boolean.valueOf(value) ? 0 : (selected ? 1 : -1);
        break;
      case 1:
        int ivalue = Integer.valueOf(value);
        result = id == ivalue ? 0 : (id > ivalue ? 1 : -1);
        break;
      case 2:
        result = username.compareTo(value);
        break;
      case 3:
        result = title.compareTo(value);
        break;
      case 4:
        result = status.compareTo(value);
        break;
      case 5:
        int jvalue = Integer.valueOf(value);
        result = progress == jvalue ? 0 : (progress > jvalue ? 1 : -1);
        break;
      case 6:
        result = created.compareTo(value);
        break;
      case 7:
        result = detail.compareTo(value);
        break;
    }
    return result;
  }
}
