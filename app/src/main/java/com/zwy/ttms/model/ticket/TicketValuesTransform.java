package com.zwy.ttms.model.ticket;

import android.content.ContentValues;
import android.database.Cursor;

public class TicketValuesTransform {
    public static Ticket transformTicket(Cursor cursor){

        Ticket ticket = new Ticket();
        ticket.setId(cursor.getInt(cursor.getColumnIndex(TicketData.TICKET_ID)));
        ticket.setScheId(cursor.getInt(cursor.getColumnIndex(TicketData.SCHE_ID)));
        ticket.setSeatId(cursor.getInt(cursor.getColumnIndex(TicketData.SEAT_ID)));
        ticket.setStatus(cursor.getInt(cursor.getColumnIndex(TicketData.TICKET_STATUS)));

        return ticket;
    }
    public static ContentValues transformContentValues(Ticket ticket){
        ContentValues contentValues =new ContentValues();

        contentValues.put(TicketData.SCHE_ID,ticket.getScheId());
        contentValues.put(TicketData.SEAT_ID,ticket.getSeatId());
        contentValues.put(TicketData.TICKET_STATUS,ticket.getStatus());

        return contentValues;
    }
}
