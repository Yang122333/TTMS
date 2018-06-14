package com.zwy.ttms.model.ticket;

/**
 * Created by ${Yang} on 2018/4/12.
 */
public class Ticket {
    public static final int ONSALED =0;//在售
    public static final int SALED   =1;//售出
    public static final int LOCKED  =2;//锁定
    public static final int CANCEL  =3;//取消
    private int id;
    private int status;
    private int seatId;
    private int scheId;
    public Ticket(){
        status = Ticket.ONSALED;

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScheId() {
        return scheId;
    }

    public void setScheId(int scheId) {
        this.scheId = scheId;
    }
}
