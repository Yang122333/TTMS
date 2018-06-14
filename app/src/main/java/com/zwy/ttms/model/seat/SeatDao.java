package com.zwy.ttms.model.seat;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zwy.ttms.model.DAO;
import com.zwy.ttms.model.DataHelper;

import java.util.ArrayList;
import java.util.List;

public class SeatDao implements DAO<Seat> {

    private DataHelper dataHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private List<Seat> seatList = null;
    public SeatDao(Context context){

        dataHelper = new DataHelper(context);

    }

    @Override
    public int insert(Seat seat) {
        try{
            db =dataHelper.getWritableDatabase();
            db.insert(SeatData.SEAT_TABLE,null,
                    SeatValuesTransform.transformContentValues(seat));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(db != null)
                db.close();
        }
        return 0;
    }

    @Override
    public void delete(String selection, String[] selectionArgs) {


    }

    @Override
    public void update(String whereClause, String[] whereArgs, Seat seat) {
        try{
            db =dataHelper.getWritableDatabase();
            db.update(SeatData.SEAT_TABLE, SeatValuesTransform.transformContentValues(seat),
                    whereClause,whereArgs);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(db != null)
                db.close();
        }

    }

    @Override
    public List<Seat> queryAll() {
        return queryAction(null,null);
    }

    public List<Seat> queryByStudioId(int id){

        return queryAction(SeatData.STUDIO_ID + " =?" , new String[]{String.valueOf(id)});
    }
    @Override
    public List<Seat> queryAction(String selection, String[] selectionArgs) {
        try{
            db =dataHelper.getWritableDatabase();
            cursor = db.query(SeatData.SEAT_TABLE,null,selection,selectionArgs,
                    null,null,null);
            if(cursor.getCount() ==0)
                seatList = null;
            else {
                if(cursor != null && cursor.moveToFirst()){
                    seatList = new ArrayList<>();
                    do{
                        seatList.add(SeatValuesTransform.transformSeat(cursor));
                    }while (cursor.moveToNext());
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(db != null)
                db.close();
        }
        return seatList;
    }
}
