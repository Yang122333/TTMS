package com.zwy.ttms.model.seat;

import android.content.ContentValues;
import android.database.Cursor;

public class SeatValuesTransform {
    public static Seat transformSeat(Cursor cursor){

        Seat seat = new Seat();
        seat.setId(cursor.getInt(cursor.getColumnIndex(SeatData.SEAT_ID)));
        seat.setStudioId(cursor.getInt(cursor.getColumnIndex(SeatData.STUDIO_ID)));
        seat.setNumber(cursor.getInt(cursor.getColumnIndex(SeatData.SEAT_NUMBER)));
        seat.setStatus(cursor.getInt(cursor.getColumnIndex(SeatData.SEAT_STATUS)));

        return seat;
    }
    public static ContentValues transformContentValues(Seat seat){
        ContentValues contentValues =new ContentValues();

        contentValues.put(SeatData.STUDIO_ID,seat.getStudioId());
        contentValues.put(SeatData.SEAT_NUMBER,seat.getNumber());
        contentValues.put(SeatData.SEAT_STATUS,seat.getStatus());
        return contentValues;
    }
}
