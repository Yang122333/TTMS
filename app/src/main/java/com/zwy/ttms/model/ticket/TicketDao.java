package com.zwy.ttms.model.ticket;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zwy.ttms.model.DAO;
import com.zwy.ttms.model.DataHelper;

import java.util.ArrayList;
import java.util.List;

public class TicketDao implements DAO<Ticket> {

    private DataHelper dataHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private List<Ticket> ticketList = null;
    public TicketDao(Context context){
        dataHelper = new DataHelper(context);
    }


    @Override
    public int insert(Ticket ticket) {
        int i =0;
        try{
            db =dataHelper.getWritableDatabase();
            i = (int)db.insert(TicketData.TICKET_TABLE,null,
                    TicketValuesTransform.transformContentValues(ticket));
        }catch (Exception e){
            e.printStackTrace();
        }

        return i;
    }

    @Override
    public void delete(String whereClause, String[] whereArgs) {
        try{
            db =dataHelper.getWritableDatabase();
            db.delete(TicketData.TICKET_TABLE,whereClause,whereArgs);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(db != null)
                db.close();
        }

    }

    @Override
    public void update(String whereClause, String[] whereArgs, Ticket ticket) {
        try{
            db =dataHelper.getWritableDatabase();
            db.update(TicketData.TICKET_TABLE, TicketValuesTransform.transformContentValues(ticket),
                    whereClause,whereArgs);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(db != null)
                db.close();
        }
    }

    @Override
    public List<Ticket> queryAll() {
        return queryAction(null,null);
    }

    @Override
    public List<Ticket> queryAction(String selection, String[] selectionArgs) {
        try{
            db =dataHelper.getWritableDatabase();
            cursor = db.query(TicketData.TICKET_TABLE,null,selection,selectionArgs,
                    null,null,null);
            if(cursor.getCount() ==0)
                ticketList = null;
            else {
                if(cursor != null && cursor.moveToFirst()){
                    ticketList = new ArrayList<>();
                    do{
                        ticketList.add(TicketValuesTransform.transformTicket(cursor));
                    }while (cursor.moveToNext());
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(db != null)
                db.close();
        }
        return ticketList;
    }
    public void dbClose(){
        if(db != null)
            db.close();
    }
}
