package com.betatesters.saypresent30;

public class SubjectList {

    private String sectionName;
    private String subjectName;
    private String timeIn;
    private String timeOut;
    private String weeks;

    public SubjectList(String sectionName, String subjectName, String timeIn, String timeOut, String weeks){
        this.sectionName = sectionName;
        this.subjectName = subjectName;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.weeks = weeks;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public String getWeeks() {
        return weeks;
    }

}
