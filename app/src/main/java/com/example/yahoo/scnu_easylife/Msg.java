package com.example.yahoo.scnu_easylife;
class Msg {
    static final int TYPE_RECEIVED = 0 ;
    static final int TYPE_SEND = 1;
    private String content;
    private int type;

    Msg(String content, int type){
        this.content = content;
        this.type =type;
    }
    String getContent(){
        return content;
    }
    int getType(){
        return type;
    }
}


