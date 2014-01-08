package com.boryi.bots.client.view;

import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;


/**
 * Asynchronously upload file. File can be de-selected
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class AsyncFileUpload extends Composite implements ClickHandler
{
  private final static int PROCESSING_BAR_WIDTH = 100;

  private int actionDelay = 5000;
  private int inputSize = 30;
  private int statusInterval = 1000;

  private VerticalPanel panel = new VerticalPanel();
  private HorizontalPanel displayPanel = new HorizontalPanel();

  private FormPanel formPanel = new FormPanel();
  private FileUpload fileUpload = new FileUpload();

  private CheckBox uploadedFile = new CheckBox();

  private HorizontalPanel processingBarFront = new HorizontalPanel();
  private RequestBuilder statusRequest;

  /**
   * Local filename to be uploaded
   */
  private String filename;
  /**
   * Filename saved on the server - in order to be picked up later.
   * "GUID-filename"
   */
  private String serverFilename;
  /**
   * Timer used to upload file after file has been selected
   */
  private Timer uploadTimer;
  /**
   * Timer used to remove the file from uploading after it is de-selected
   */
  private Timer cancelTimer;
  /**
   * Timer used to check the file uploading progress
   */
  private Timer statusTimer;

  private int progress = 0;

  /**
   * Constructs a panel for uploading file asynchronously
   * 
   * @param actionUrl
   *          action url to upload file by post method
   * @param name
   *          name to be used in POST method when the form is submit for file
   *          uploading
   * @param inputSize
   *          size of file input box
   * @param actionDelay
   *          delay in milliseconds for action to be executed
   */
  public AsyncFileUpload(String actionUrl, String name, int inputSize,
      int actionDelay)
  {
    this.sinkEvents(Event.ONKEYPRESS | Event.ONCLICK);

    formPanel.add(fileUpload);
    formPanel.setAction(actionUrl);
    // Because we're going to add a FileUpload widget, we'll need to set the
    // form to use the POST method, and multipart MIME encoding.
    formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
    formPanel.setMethod(FormPanel.METHOD_POST);

    // Set form elements (inputs)
    formPanel.setWidget(fileUpload);

    fileUpload.setName(name);
    fileUpload.getElement().setAttribute("size", Integer.toString(inputSize));

    // Checkbox for uploaded file
    displayPanel.add(uploadedFile);

    HorizontalPanel processingBarBack = new HorizontalPanel();
    processingBarBack.setStyleName("uploadFileProcessingBarBack");
    processingBarFront.setStyleName("uploadFileProcessingBarFront");
    processingBarFront.setWidth("0px");

    processingBarBack.add(processingBarFront);

    displayPanel.add(processingBarBack);

    displayPanel.setSpacing(4);
    displayPanel.setCellVerticalAlignment(processingBarBack, HasVerticalAlignment.ALIGN_MIDDLE);
    displayPanel.setVisible(false);

    uploadedFile.addClickHandler(this);

    panel.add(formPanel);
    panel.add(displayPanel);

    // Add an event handler to the form.
    formPanel.addSubmitHandler(new FormPanel.SubmitHandler() {
      public void onSubmit(SubmitEvent event)
      {
        uploadTimer = null;
        progress = 0;

        if (statusRequest != null)
        {
          statusTimer = new Timer() {

            @Override
            public void run()
            {
              try
              {
                statusRequest.sendRequest(null,
                    new RequestCallback() {
                      public void onError(Request request, Throwable exception)
                      { // Handle http request error - no server response
                        // received
                      }


                      public void onResponseReceived(Request request,
                          Response response)
                      {
                        if (200 == response.getStatusCode())
                        {
                          progress = Integer.valueOf(response.getText());
                          String width = ((progress * PROCESSING_BAR_WIDTH) / 100)  + "px"; 
                          processingBarFront.setWidth(width);
                        }
                        else
                        { // Handle server response error
                        }
                      }
                    });
              }
              catch (RequestException e)
              { // Request exception
              }
            }
          };
        }
        else // Fake the progressing bar 
        {
          statusTimer = new Timer() {

            @Override
            public void run()
            {
              if (progress < 90)
              {
                progress += 10;
              }
              String width = ((progress * PROCESSING_BAR_WIDTH) / 100)  + "px";
              processingBarFront.setWidth(width);
            }
          };
        }

        statusTimer.scheduleRepeating(statusInterval);
      }
    });

    formPanel.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
      public void onSubmitComplete(SubmitCompleteEvent event)
      {
        // When the form submission is successfully completed, this event is
        // fired. Assuming the service returned a response of type text/html,
        // we can get the result text here (see the FormPanel documentation for
        // further explanation).
        serverFilename = event.getResults();

        processingBarFront.setWidth(PROCESSING_BAR_WIDTH + "px");

        if (statusTimer != null)
        {
          statusTimer.cancel();
          statusTimer = null;
        }
      }
    });

    initWidget(panel);

    this.actionDelay = actionDelay;
    this.inputSize = inputSize;
  }


  /**
   * Constructs a panel for uploading file asynchronously
   * 
   * @param actionUrl
   *          action url to upload file by post method
   * @param name
   *          name to be used in POST method when the form is submit for file
   *          uploading
   * @param inputSize
   *          size of file input box
   * @param actionDelay
   *          delay in milliseconds for action to be executed
   * @param statusUrl
   *          url to check the status of file uploading process (cookie is used
   *          to identify the uploading process)
   * @param statusInterval
   *          time interval to check the uploading process
   */
  public AsyncFileUpload(String actionUrl, String name, int actionDelay,
      int inputSize, String statusUrl, int statusInterval)
  {
    this(actionUrl, name, actionDelay, inputSize);

    this.statusInterval = statusInterval;

    statusRequest = new RequestBuilder(RequestBuilder.GET, statusUrl);
  }


  @Override
  public void onBrowserEvent(Event event)
  {
    super.onBrowserEvent(event);

    int eventType = DOM.eventGetType(event);

    if ((eventType == Event.ONCLICK)
        || ((eventType == Event.ONKEYPRESS) && (event.getKeyCode() == KeyCodes.KEY_ENTER)))
    {
      EventTarget target = event.getEventTarget();
      if (Element.is(target))
      {
        if (fileUpload.getElement() == Element.as(target))
        {
          String newFilename = fileUpload.getFilename();
          if (newFilename != null
              && !newFilename.isEmpty()
              && (!newFilename.equalsIgnoreCase(filename) || eventType == Event.ONKEYPRESS))
          {
            filename = newFilename;

            if (uploadTimer != null)
            {
              uploadTimer.cancel();
            }

            // Wait for 5 seconds to upload/submit file
            uploadTimer = new Timer() {
              @Override
              public void run()
              {
                // Once filename is changed and hit enter or file-browser-ok
                // button,
                // submit the file and set uploadedFile's text to filename
                String displayedFilename = filename;
                if (filename.length() > inputSize)
                {
                  displayedFilename = "..." + filename.substring(3);
                }

                formPanel.submit();

                uploadedFile.setText(displayedFilename);
                uploadedFile.setValue(true);

                fileUpload.setVisible(false);
                displayPanel.setVisible(true);

                serverFilename = null;
              }
            };
            uploadTimer.schedule(actionDelay);
          }
        }
      }
    }
  }


  /**
   * When click on the check box for uploaded file
   */
  public void onClick(ClickEvent event)
  {
    // TODO Auto-generated method stub
    Object sender = event.getSource();
    if (sender == uploadedFile)
    {
      if (uploadedFile.getValue())
      {
        if (cancelTimer != null)
        {
          cancelTimer.cancel();
          cancelTimer = null;
        }
      }
      else
      {
        cancelTimer = new Timer() {
          @Override
          public void run()
          {
            fileUpload.setVisible(true);
            displayPanel.setVisible(false);

            serverFilename = null;
            filename = null;
          }
        };
        cancelTimer.schedule(actionDelay);
      }
    }
  }


  /**
   * Get the filename of the uploaded file on the server
   * 
   * @return the filename of the uploaded file on the server
   */
  public String getServerFilename()
  {
    return serverFilename;
  }


  /**
   * Get the uploaded filename
   * 
   * @return the uploaded filename
   */
  public String getFilename()
  {
    return filename;
  }
}
