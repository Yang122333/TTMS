package com.zwy.ttms.model.studio;

import android.content.ContentValues;
import android.database.Cursor;



public class StudioValuesTransform {
    public static Studio transformStudio(Cursor cursor){

        Studio studio = new Studio();
        studio.setId(cursor.getInt(cursor.getColumnIndex(StudioData.STUDIO_ID)));
        studio.setColumn(cursor.getInt(cursor.getColumnIndex(StudioData.STUDIO_COL_COUNT)));
        studio.setRow(cursor.getInt(cursor.getColumnIndex(StudioData.STUDIO_ROW_COUNT)));
        studio.setName(cursor.getString(cursor.getColumnIndex(StudioData.STUDIO_NAME)));
        studio.setState(cursor.getInt(cursor.getColumnIndex(StudioData.STUDIO_STATUS)));
        studio.setSeatInfo(cursor.getString(cursor.getColumnIndex(StudioData.SEAT_INFO)));


        return studio;
    }
    public static ContentValues transformContentValues(Studio studio){
        ContentValues contentValues =new ContentValues();

        contentValues.put(StudioData.STUDIO_COL_COUNT,studio.getColumn());
        contentValues.put(StudioData.STUDIO_ROW_COUNT,studio.getRow());
        contentValues.put(StudioData.STUDIO_NAME,studio.getName());
        contentValues.put(StudioData.STUDIO_STATUS,studio.getState());
        contentValues.put(StudioData.SEAT_INFO,studio.getSeatInfo());
        return contentValues;
    }
}
