package com.zwy.ttms.conductor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zwy.ttms.R;

import java.util.ArrayList;
import java.util.List;

public class ChosePlayActivity extends Activity{
    private List<String> scheduleList = new ArrayList<>();

    //private List<Schedule> scheduleList = new ArrayList<>();
    //private ScheduleDao scheduleDao;
//    private Schedule schedule ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        ListView lv = (ListView)findViewById(R.id.sale_play_list);
        ArrayAdapter<String> adapter =new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,scheduleList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChosePlayActivity.this,SaleTicket.class);


//                intent.putExtra("id",scheduleList.get(position).getid() );  //传入演出计划id

                //批量创建票

//                ticketDao =new TicketDao(ChosePlayActivity.this);
//                int studio_id = scheduleList.get(position).getstudio_id();
//                StudioDao studioDao = new StudioDao(ChosePlayActivity.this);
//                int studio_count = studioDao.queryById(studio_id).get(0).getSeatCount();
//                for(int i =1;i<=studio_count;i ++)
//                {
//                    Ticket ticket =new Ticket();
//                    ticket.setSeatId(i);
//                    ticket.setScheId(scheduleList.get(position).getId());
//                    ticket.setStatus(Ticket.ONSALED);
//                    ticketDao.insert(ticket);
//                }



                startActivity(intent);
            }
        });
    }

    private void init() {
//        scheduleDao = new ScheduleDao(this);
//        Schedule schedule =new Schedule();
//        scheduleList = scheduleDao.queryAll();
        for(int i = 0 ; i<10 ;i++){
            scheduleList.add(i+"");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
