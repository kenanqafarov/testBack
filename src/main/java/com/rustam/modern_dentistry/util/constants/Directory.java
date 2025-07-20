package com.rustam.modern_dentistry.util.constants;

public interface Directory {
    String domain = "http://195.7.6.10:80";
    String pathPatInsuranceBalance = "media/patient/insurance/balance";
    String pathPatPhoto = "media/patient/photo";
    String pathPatXray = "media/patient/xray";
    String pathPatVideo = "media/patient/video";
    String pathDentalOrder = "media/laboratory/dental/order";

    static String getUrl(String path, String fileName) {
        return domain + "/" + path + "/" + fileName;
    }
}
