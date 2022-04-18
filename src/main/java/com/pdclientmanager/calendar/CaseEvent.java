package com.pdclientmanager.calendar;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

public class CaseEvent {
    
    private String id;
    
    @NotEmpty
    private String caseNumber;
    
    @NotEmpty
    private String attorney;
    
    @NotEmpty
    private String description;
    private String summary;
    
    @DateTimeFormat(pattern ="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startTime;
    
    @DateTimeFormat(pattern ="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endTime;
    
    public CaseEvent() {
        
    }
    
    public CaseEvent(String id, String caseNumber, String attorney,
        String description, String summary, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.caseNumber = caseNumber;
        this.attorney = attorney;
        this.description = description;
        this.summary = summary;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getCaseNumber() {
        return caseNumber;
    }
    
    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }
    
    public String getAttorney() {
        return attorney;
    }
    
    public void setAttorney(String attorney) {
        this.attorney = attorney;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getSummary() {
        return summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    public String getPrettyStartTime() {
        return formatPrettyDateTime(startTime);
    }
    
    public String getPrettyEndTime() {
        return formatPrettyDateTime(endTime);
    }
    
    
    
    @Override
    public String toString() {
        return "CaseEvent [id=" + id + ", caseNumber=" + caseNumber + ", attorney=" + attorney + ", investigator="
                + ", description=" + description + ", summary=" + summary + ", startTime=" + startTime
                + ", endTime=" + endTime + "]";
    }

    private String formatPrettyDateTime(LocalDateTime dateTime) {
        StringBuilder sb = new StringBuilder();
        sb.append(dateTime.getMonthValue());
        sb.append('-');
        sb.append(dateTime.getDayOfMonth());
        sb.append('-');
        sb.append(dateTime.getYear());
        sb.append(' ');
        int hour = dateTime.getHour();
        if(hour == 0) {
            sb.append("12");
        } else if(hour > 12) {
            sb.append(hour- 12);
        } else {
            sb.append(hour);
        }
        sb.append(':');
        int minute = dateTime.getMinute();
        if(minute == 0) {
            sb.append("00");
        } else {
            sb.append(minute);
        }
        if(hour < 12) {
            sb.append(" AM");
        } else {
            sb.append(" PM");
        }
        return sb.toString();
    }
}
