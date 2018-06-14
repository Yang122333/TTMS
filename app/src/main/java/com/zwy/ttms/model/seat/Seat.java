package com.zwy.ttms.model.seat;
/**
 * Created by ${Yang} on 2018/4/11.
 */
public class Seat {
    public static final int DAMAGED = 0;//损坏
    public static final int NORMAL  = 1;//正常
    public static final int CKECKED = 2;//选中
    private int status;
    private int id;
    private int studioId;
    private int number;
    private int row;
    private int column;
    public Seat(){
        status = Seat.NORMAL;
    }
    public int getStudioId() {
        return studioId;
    }

    public void setStudioId(int studioId) {
        this.studioId = studioId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getRow(int c) {

        if(number % c ==0){
            row = number / c;
        }
        else {
            row = number / c + 1;
        }

        return row;
    }

    public int getColumn(int c) {
        if(number % c ==0){
            row = number % c;
        }
        else {
            row = number % c + 1;
        }

        return column;
    }
}
