package com.zwy.ttms.model.sale;

import android.content.ContentValues;
import android.database.Cursor;

public class SaleValuesTransform {
    public static Sale transformSale(Cursor cursor){

        Sale sale = new Sale();
        sale.setSale_id(cursor.getInt(cursor.getColumnIndex(SaleData.SALE_ID)));
        sale.setEmp_id(cursor.getInt(cursor.getColumnIndex(SaleData.EMP_ID)));
        sale.setTicket_id(cursor.getInt(cursor.getColumnIndex(SaleData.TICKET_ID)));
        sale.setSale_price(cursor.getInt(cursor.getColumnIndex(SaleData.SALE_PRICE)));
        sale.setSale_status(cursor.getInt(cursor.getColumnIndex(SaleData.SALE_STATUS)));
        return sale;
    }
    public static ContentValues transformContentValues(Sale sale){
        ContentValues contentValues =new ContentValues();

        contentValues.put(SaleData.EMP_ID,sale.getEmp_id());
        contentValues.put(SaleData.TICKET_ID,sale.getTicket_id());
        contentValues.put(SaleData.SALE_PRICE,sale.getSale_price());
        contentValues.put(SaleData.SALE_STATUS,sale.getSale_status());
        return contentValues;
    }

}
