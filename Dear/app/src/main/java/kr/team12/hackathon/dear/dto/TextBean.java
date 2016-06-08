package kr.team12.hackathon.dear.dto;

import java.util.List;

/**
 * Created by HunJin on 2016-05-29.
 */
public class TextBean {
    String success;
    List<String> data;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
