package com.betatesters.saypresent30;

public class RecordList {

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    private String date;
    private String status;

    public RecordList(String date, String status){
        this.date = date;
        this.status = status;
    }

}
