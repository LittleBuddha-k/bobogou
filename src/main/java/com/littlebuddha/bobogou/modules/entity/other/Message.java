package com.littlebuddha.bobogou.modules.entity.other;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: LittleBuddha-k
 * \* Date: 2021-07-05
 * \* Time: 上午 09:45
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class Message {

    private String id;
    private String msg;
    private String from;
    private String to;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}