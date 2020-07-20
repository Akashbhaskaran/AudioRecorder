package com.dimowner.audiorecorder.app.model;

public class FileUploadRequest {


    public FileUploadRequest(String filename, String patientId,String dept) {
        this.filename = filename;
        this.patientId = patientId;
        this.dept = dept;
    }

    String filename;
    String patientId;
    String dept;
}
