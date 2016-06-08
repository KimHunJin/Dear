package kr.team12.hackathon.dear.item;

/**
 * Created by HunJin on 2016-05-29.
 */
public class MainListItem {
    private int number;
    private String context;

    public int getNumber() {
        return number;
    }

    public String getContext() {
        return context;
    }

    public MainListItem(int number, String context) {
        this.number = number;
        this.context = context;
    }
}
