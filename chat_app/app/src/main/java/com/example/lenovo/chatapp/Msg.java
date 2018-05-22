package com.example.lenovo.chatapp;
/**
 * Created by lenovo on 2018/4/3.
 */

public class Msg {
    public static final int TYPE_RECIEVED = 0;
    public static final int TYPE_SENT = 1;
    public static final int[] num_to_image = new int[]{
            R.drawable.shushen1,
            R.drawable.shushen2,
            R.drawable.shutong,
            R.drawable.furen,
            R.drawable.laoye,
            R.drawable.xiaojie
    };

    private String content;
    private int num;
    private int type;

    public Msg(String content, int n, int type){
        this.type = type;
        this.num = n;
        this.content = content;
    }
    public String getContent(){
        return content;
    }
    public int getImage() { return num_to_image[num]; }
    public int getType(){
        return type;
    }
}
