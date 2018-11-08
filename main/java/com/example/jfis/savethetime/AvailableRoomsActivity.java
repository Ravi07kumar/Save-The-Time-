package com.example.jfis.savethetime;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AvailableRoomsActivity extends AppCompatActivity {

    private RadioGroup dayRadioGroup, lectureRadioGroup;
    private RadioButton dayRadioButton, lectureRadioButton;
    private ListView roomIdListView;
    private DatabaseReference databaseReference, databaseReference1;
    private List roomIdList;
    private List allRooms, l1;
    private Button checkButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_rooms);

        databaseReference = FirebaseDatabase.getInstance().getReference("freeRooms");
        databaseReference1 = FirebaseDatabase.getInstance().getReference("rooms");

        dayRadioGroup = findViewById(R.id.dayAvailableRoomRadioGroup);
        lectureRadioGroup = findViewById(R.id.lectureAvailableRoomRadioGroup);
        roomIdListView = findViewById(R.id.roomIdAvailableRoomListView);
        checkButton = findViewById(R.id.checkRoomAvailableRoomButton);

        roomIdList = new ArrayList<>();
        allRooms = new ArrayList<>();
        l1 = new ArrayList<>();

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(AvailableRoomsActivity.this);
                progressDialog.setMessage("Wait or check for internet connection");

                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//progressDialog.setIndeterminate(true);
                progressDialog.setProgress(0);
                progressDialog.show();

                final int totalProgressTime =100;
                final Thread thread= new Thread(){
                    @Override
                    public void run(){
                        int jumpTime=2;
                        while(jumpTime<totalProgressTime){
                            try{
                                sleep(200);
                                jumpTime+=2;
                                progressDialog.setProgress(jumpTime);
                            }catch(Exception e){}
                        }
                    }
                };
                thread.start();
                roomIdList.clear();
                allRooms.clear();
                int daySelcetd = dayRadioGroup.getCheckedRadioButtonId();
                int lectureSelected = lectureRadioGroup.getCheckedRadioButtonId();

                dayRadioButton = findViewById(daySelcetd);
                lectureRadioButton = findViewById(lectureSelected);

                final String daySelctedString = dayRadioButton.getText().toString();
                final String lectureSelectedString = "lectur" + lectureRadioButton.getText().toString();


                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                              Toast.makeText(AvailableRoomsActivity.this, "hello", Toast.LENGTH_SHORT).show();
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                                    if (postSnapshot.getKey().equals(daySelctedString)) {
                                        Toast.makeText(AvailableRoomsActivity.this, "bye", Toast.LENGTH_SHORT).show();

                                        for (DataSnapshot postSnapshot1 : postSnapshot.getChildren()) {
                                            Toast.makeText(AvailableRoomsActivity.this, postSnapshot1.getKey() + "", Toast.LENGTH_SHORT).show();

                                            if (postSnapshot1.getKey().equalsIgnoreCase(lectureSelectedString)) {
                                                Toast.makeText(AvailableRoomsActivity.this, "ggg", Toast.LENGTH_SHORT).show();
                                                for (DataSnapshot postSnapshot3 : postSnapshot1.getChildren()) {
                                                    roomIdList.add(postSnapshot3.getKey());
                                                }
                                                Toast.makeText(AvailableRoomsActivity.this, roomIdList + "", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                };
                Thread t = new Thread(run);
                t.start();

                try {
                    t.join();
                } catch (Exception e) {


                } finally {

                    databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                                Toast.makeText(AvailableRoomsActivity.this, postSnapShot.getKey() + "", Toast.LENGTH_SHORT).show();
                                allRooms.add(postSnapShot.getKey());

                            }
                            try {
                                Thread.sleep(10000);
                            } catch (Exception e) {
                            } finally {


                                l1.clear();
                                l1 = subtract(allRooms, roomIdList);
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AvailableRoomsActivity.this,
                                        android.R.layout.simple_list_item_1, android.R.id.text1, l1);

                                roomIdListView.setAdapter(adapter);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


            }
        });

    }

    public <T> List<T> subtract(List<T> list1, List<T> list2) {
        List<T> result = new ArrayList<T>();
        Set<T> set2 = new HashSet<T>(list2);
        for (T t1 : list1) {
            if (!set2.contains(t1)) {
                result.add(t1);
            }
        }
        return result;
    }

}

