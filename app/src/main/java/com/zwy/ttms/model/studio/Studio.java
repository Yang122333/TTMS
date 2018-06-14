package com.zwy.ttms.model.studio;

/**
 * Created by ${Yang} on 2018/4/12.
 */

public class Studio {
    public static final int CLOSED = 0;//演出厅关闭
    public static final int OPENED = 1;//演出厅正常
    private int id;
    private String name;
    private int row;
    private int column;
    private int state;
    private String seatInfo;
    public Studio(){
        name = id+"号演出厅";
        state = Studio.OPENED;
        row = 4;
        column = 7;
    }
    public int getSeatCount(){
        return row*column;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setRow(int row) {
        this.row = row;
    }
    public int getRow(){
        return row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getColumn(){
        return column;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeatInfo() {
        return seatInfo;
    }

    public void setSeatInfo(String seatInfo) {
        this.seatInfo = seatInfo;
    }
}
