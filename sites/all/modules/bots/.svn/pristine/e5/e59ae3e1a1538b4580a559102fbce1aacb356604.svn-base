package com.boryi.bots.base;

import java.net.URI;

/**
 * Bot checklist entity class bundles url, crawling stage, and id related to 
 * the url
 *
 * <p><strong>Copyright 2007 - 2017 Boryi Network Information Inc.</strong></p>
 * 
 * @author Gang Huang, Ph.D.
 * @version 1.0
 * @since August 2009
 */
public class BotChecklistEntity 
{
    /**
     * The url to be crawled
     */
    protected URI url;
    /**
     * The stage of the crawling for the url.  stage must be greater than or 
     * equal to 1.  For example, top level category can be stage 1, the 
     * second level category can be stage 2.
     */
    protected int stage;
    /**
     * Id related to the url. For example, it can be the category id or item id.
     */
    protected int id;
    /**
     * Level of the checklist entity.
     */
    private int level;
    
    /**
     * Constructor to initialize an instance of BotChecklistEntity
     * 
     * @param url The url to be crawled
     * @param stage The stage of the crawling for the url.  stage must be 
     * greater than or equal to 1.  For example, top level category can be 
     * stage 1, the second level category can be stage 2.
     * @param id Id related to the url. For example, it can be the category id 
     * or item id.
     */
    public BotChecklistEntity(URI url, int stage, int id, int level)
    {
        this.url = url;
        this.stage = stage;
        this.id = id;
        this.level = level;
    }

    
    /**
     * Get the url to be crawled
     * 
     * @return The url to be crawled
     */
    public URI getUrl() 
    {
        return url;
    }
    

    /**
     * Get the stage of the crawling for the url.  stage must be 
     * greater than or equal to 1.  For example, top level category can be 
     * stage 1, the second level category can be stage 2.
     * 
     * @return The stage of the crawling for the url.  stage must be 
     * greater than or equal to 1.  For example, top level category can be 
     * stage 1, the second level category can be stage 2.
     */
    public int getStage() 
    {
        return stage;
    }

    
    /**
     * Get Id related to the url. For example, it can be the category id 
     * or item id.
     * 
     * @return Id related to the url. For example, it can be the category id 
     * or item id.
     */
    public int getId() 
    {
        return id;
    }


    /**
     * Get level related to the url
     *
     * @return The level of the url.
     */
    public int getLevel()
    {
        return level;
    }
}
