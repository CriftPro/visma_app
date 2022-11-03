package com.visma.webapp.model;

// MODEL FILTERS FOR JSON BODY REQUEST

public class FilterRequest {
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilterParameter() {
        return filterParameter;
    }

    public void setFilterParameterfilterParameter(String filter) {
        this.filterParameter = filter;
    }

    public MeetingDate getDate_begin() {
        return date_begin;
    }

    public void setDate_begin(MeetingDate date_begin) {
        this.date_begin = date_begin;
    }

    public MeetingDate getDate_end() {
        return date_end;
    }

    public void setDate_end(MeetingDate date_end) {
        this.date_end = date_end;
    }

    private String description;
    private String filterParameter;
    private MeetingDate date_begin;
    private MeetingDate date_end;
}
