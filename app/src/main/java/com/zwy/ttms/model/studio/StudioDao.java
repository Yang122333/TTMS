package com.zwy.ttms.model.studio;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zwy.ttms.model.DAO;
import com.zwy.ttms.model.DataHelper;

import java.util.ArrayList;
import java.util.List;

public class StudioDao implements DAO<Studio> {

    private DataHelper dataHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private List<Studio> studioList = null;
    public StudioDao(Context context){
        dataHelper = new DataHelper(context);
    }
    @Override
    public int insert(Studio studio) {
        int i =0;
        try{
            db =dataHelper.getWritableDatabase();
             i = (int)db.insert(StudioData.STUDIO_TABLE,null,
                    StudioValuesTransform.transformContentValues(studio));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(db != null)
                db.close();
        }
        return i;
    }

    public void deleteById(int id){
        delete(StudioData.STUDIO_ID+" = ?" ,new String[]{String.valueOf(id)});
    }

    @Override
    public void delete(String whereClause, String[] whereArgs) {
        try{
            db =dataHelper.getWritableDatabase();
            db.delete(StudioData.STUDIO_TABLE,whereClause,whereArgs);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(db != null)
                db.close();
            }
    }

    public void updateById(int id,Studio studio){
        update(StudioData.STUDIO_ID + " = ?",new String[]{String.valueOf(id)},studio);
    }
    @Override
    public void update(String whereClause, String[] whereArgs, Studio studio) {
        try{
            db =dataHelper.getWritableDatabase();
            db.update(StudioData.STUDIO_TABLE,StudioValuesTransform.transformContentValues(studio),
                    whereClause,whereArgs);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(db != null)
                db.close();
        }
    }

    @Override
    public List<Studio> queryAll() {
        return queryAction(null,null);
    }

    public List<Studio> queryById(int id){
        return queryAction(StudioData.STUDIO_ID+"=? ",new String[]{String.valueOf(id)});
    }
    @Override
    public List<Studio> queryAction(String selection, String[] selectionArgs) {
        try{
            db =dataHelper.getWritableDatabase();
            cursor = db.query(StudioData.STUDIO_TABLE,null,selection,selectionArgs,
                    null,null,null);
            if(cursor.getCount() ==0)
                studioList = null;
            else {
                if(cursor != null && cursor.moveToFirst()){
                    studioList = new ArrayList<>();
                    do{
                        studioList.add(StudioValuesTransform.transformStudio(cursor));
                    }while (cursor.moveToNext());
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(db != null)
                db.close();
        }
        return studioList;
    }
}
