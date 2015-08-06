package com.globalradio.mo.data;


import com.sun.syndication.feed.module.DCModuleImpl;
import com.sun.syndication.feed.module.ModuleImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateModuleImpl extends ModuleImpl implements DateModule {

    private Date date;

    public DateModuleImpl() {
        super(DateModuleImpl.class, URI);
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public Class getInterface() {
        return DateModule.class;
    }

    @Override
    public void copyFrom(Object o) {
        DCModuleImpl dcModule = (DCModuleImpl) o;
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        cal.setTime(dcModule.getDate());
        setDate(cal.getTime());
    }
}
