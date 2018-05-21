package com.api.rest.util;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
import java.io.Serializable;

public class DateParameter implements Serializable {
    private Date date;

    /**
    * Default empty DateParameter constructor
    */
    public DateParameter() {
        super();
    }

    /**
    * Default empty DateParameter constructor
    */
    public DateParameter(String date) {
        super();

        if (date == null || date.isEmpty()) {
            this.date = null;
            return;
        }

        DateFormat CRAZY_FORMAT = new SimpleDateFormat("dd-MMM-yyyy");

        try {
            this.date = CRAZY_FORMAT.parse(date);
        }
        catch(Exception ex) {
            this.date = null;
        }
    }

	/**
	* Returns value of date
	* @return
	*/
	public Date getDate() {
		return date;
	}

	/**
	* Sets new value of date
	* @param
	*/
	public void setDate(Date date) {
		this.date = date;
	}
}
