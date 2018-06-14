package com.zwy.ttms.model.sale;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zwy.ttms.model.DataHelper;

import java.util.ArrayList;
import java.util.List;

public class SaleDao {
    private DataHelper dataHelper;
    private SQLiteDatabase db = null;
    private Cursor cursor = null;
    private List<Sale> saleList = null;
    public SaleDao(Context context){
        dataHelper = new DataHelper(context);
    }
    public void insert(Sale sale){
        try{
            db =dataHelper.getWritableDatabase();
            db.insert("sale",null,SaleValuesTransform.transformContentValues(sale));

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(db != null)
            db.close();
        }
       }
    public List<Sale> queryAction(String selection, String[] selectionArgs){
        try{
            db = dataHelper.getWritableDatabase();
            cursor = db.query("sale",null,selection,selectionArgs,null,null,null);
            if(cursor != null && cursor.moveToFirst()){
                saleList = new ArrayList<>();
                do{
                    saleList.add(SaleValuesTransform.transformSale(cursor));
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(db != null)
                db.close();
        }
        return saleList;
    }
    public List<Sale> queryAll(){
        return queryAction(null,null);
    }
}
