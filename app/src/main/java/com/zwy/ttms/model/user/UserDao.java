package com.zwy.ttms.model.user;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zwy.ttms.model.DAO;
import com.zwy.ttms.model.DataHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${Yang} on 2018/4/26.
 */

public class UserDao implements DAO<User> {
    private Context context;
    private DataHelper dataHelper;
    private SQLiteDatabase db = null;
    private Cursor cursor = null;
    private List<User> list = null;


    public UserDao(Context context){

        dataHelper =new DataHelper(context);
        this.context = context;
        User user = new User();
        user.setLogin_name("1");
        user.setPassword("1");
        user.setRole(1);
        insert(user);
    }
    @Override
    public int insert(User user) {
        int i  = 0;
        try{
            List<User> users =queryAll();
            db = dataHelper.getWritableDatabase();
            int flag =-1; // 是否存在账号
            if(users != null){
                for(User s: users){

                    if( (s.getLogin_name().toString() ).equals(user.getLogin_name() ) ){
                        flag++; // 如果存在账号 ++
                        i = -1;
                    }
                }
            }

            if(flag == -1){
                if(!user.getLogin_name().isEmpty() && !user.getPassword().isEmpty()) {
                    if (user.getLogin_name().indexOf(" ") == -1
                            && user.getPassword().indexOf(" ") == -1) {
                        i = (int) db.insert(UserData.USER_TABLE,null, UserValuesTransform.transformContentValues(user));


                    }//#if
                    else{
                        i = -2;
                     }//#else

                }//#if(!isEmpty())
                else{
                    i = -3;
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(db != null)
            db.close();
            return i;
        }
    }

    public void deleteById(int id){
        delete(UserData.USERID+"=?",new String[]{String.valueOf(id)});
    }
    @Override
    public void delete(String whereClauses, String[] whereArgs) {

    try{
        db = dataHelper.getWritableDatabase();

        db.delete(UserData.USER_TABLE,whereClauses,whereArgs);
    }catch (Exception e){

    }finally {
        if(db != null)
            db.close();
    }
    }

    public void updateById(int UserId ,User user){

        update(UserData.USERID+"=?",new String[]{String.valueOf(UserId)},user);

    }

    @Override
    public void update(String whereClauses, String[] whereArgs, User user) {
        try{
            db = dataHelper.getWritableDatabase();

            db.update(UserData.USER_TABLE, UserValuesTransform.transformContentValues(user),whereClauses,whereArgs);
        }catch (Exception e ){
            e.printStackTrace();
        }finally {
            if(db != null)
                db.close();
        }
    }

   public User queryByName(String name){
      return  queryAction(UserData.LOGINNAME + "=?",new String[]{name}).get(0);
   }

    @Override
    public List<User> queryAll() {
        return queryAction(null,null);
    }

    @Override
    public List<User> queryAction(String selection, String[] selectionArgs) {

        try {
            db = dataHelper.getReadableDatabase();
            if(db != null) {
                cursor = db.query(UserData.USER_TABLE,
                        new String[]{UserData.USERID, UserData.LOGINNAME, UserData.PASSWORD, UserData.ROLE}, selection, selectionArgs, null, null,null);
                if(cursor.getCount()==0)
                    list =null;

                else if(cursor != null && cursor.moveToFirst()){
                    list = new ArrayList<User>();
                    do{
                        list.add(UserValuesTransform.transformUser(cursor));
                    }while (cursor.moveToNext());
                }
            }
        }
        catch (Exception e ){
            e.printStackTrace();
        }finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }

        }
        return list;
    }
    public void close(){
        if(db != null)
            db.close();
    }
    public int findLastestId(){
        int id =0;
        try{
            db = dataHelper.getReadableDatabase();

            // select id from user_table order by id limit 0,1;
            Cursor cursor = db.query(UserData.USER_TABLE,new String[]{UserData.USERID},null,null,null,null,UserData.USERID+" desc","0,1");
            if(cursor.moveToFirst()){
                do{
                    id = cursor.getInt(cursor.getColumnIndex(UserData.USERID));
                }while (cursor.moveToNext());
            }

        }catch (Exception e ){
            e.printStackTrace();
        }finally {
            if(db != null)
                db.close();
        }
        return id;
    }
    public void deleteDataBase(){
        dataHelper.deleteDatabase(context);
    }
}
