package com.example.jfis.savethetime;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddScheduleActivity extends AppCompatActivity {

    private Spinner lecture1,lecture2,lecture3,lecture4,lecture5,lecture6,lecture7,lecture8,lecture9;
    private Button submit;
    private EditText className;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,databaseReference1,roomDatabaseReference;
    private ArrayList<String> roomId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        roomId = new ArrayList<>();
        roomId.add("none");

        className=findViewById(R.id.classNameAddScheduleText);

        lecture1=findViewById(R.id.lecture1RoomAddScheduleText);
        lecture2=findViewById(R.id.lecture2RoomAddScheduleText);
        lecture3=findViewById(R.id.lecture3RoomAddScheduleText);
        lecture4=findViewById(R.id.lecture4RoomAddScheduleText);
        lecture5=findViewById(R.id.lecture5RoomAddScheduleText);
        lecture6=findViewById(R.id.lecture6RoomAddScheduleText);
        lecture7=findViewById(R.id.lecture7RoomAddScheduleText);
        lecture8=findViewById(R.id.lecture8RoomAddScheduleText);
        lecture9=findViewById(R.id.lecture9RoomAddScheduleText);

        submit=findViewById(R.id.submitAddScheduleButton);
        radioGroup=findViewById(R.id.dayAddScheduleRadioGroup);

        firebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("schedule");

        roomDatabaseReference=FirebaseDatabase.getInstance().getReference("rooms");

        roomDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    roomId.add(postSnapshot.child("roomId").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, roomId);

        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lecture1.setAdapter(aa);
        lecture2.setAdapter(aa);
        lecture3.setAdapter(aa);
        lecture4.setAdapter(aa);
        lecture5.setAdapter(aa);
        lecture6.setAdapter(aa);
        lecture7.setAdapter(aa);
        lecture8.setAdapter(aa);
        lecture9.setAdapter(aa);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                radioButton=findViewById(i);
                String day = radioButton.getText().toString();
                String dayString="";
                if(day.equals("M")){
                    dayString="Monday";
                }else if(day.equals("T")){
                    dayString="Tuesday";
                }else if(day.equals("W")){
                    dayString="Wednesday";
                }else if(day.equals("Th")){
                    dayString="Thursday";
                }else if(day.equals("F")){
                    dayString="Friday";
                }



                Toast.makeText(AddScheduleActivity.this,  dayString+  "  selected" , Toast.LENGTH_SHORT).show();


                lecture1.setSelection(0);
                lecture2.setSelection(0);
                lecture3.setSelection(0);
                lecture4.setSelection(0);
                lecture5.setSelection(0);
                lecture6.setSelection(0);
                lecture7.setSelection(0);
                lecture8.setSelection(0);
                lecture9.setSelection(0);

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String classNameString=className.getText().toString();

                String lecture1String=lecture1.getSelectedItem().toString();
                String lecture2String=lecture2.getSelectedItem().toString();
                String lecture3String=lecture3.getSelectedItem().toString();
                String lecture4String=lecture4.getSelectedItem().toString();
                String lecture5String=lecture5.getSelectedItem().toString();
                String lecture6String=lecture6.getSelectedItem().toString();
                String lecture7String=lecture7.getSelectedItem().toString();
                String lecture8String=lecture8.getSelectedItem().toString();
                String lecture9String=lecture9.getSelectedItem().toString();

                int selectedId=radioGroup.getCheckedRadioButtonId();
                radioButton=findViewById(selectedId);
                String day = radioButton.getText().toString();
                String dayString="";
                if(day.equals("M")){
                    dayString="Monday";
                }else if(day.equals("T")){
                    dayString="Tuesday";
                }else if(day.equals("W")){
                    dayString="Wednesday";
                }else if(day.equals("Th")){
                    dayString="Thursday";
                }else if(day.equals("F")){
                    dayString="Friday";
                }

                if(classNameString.trim().isEmpty() ){
                    Toast.makeText(AddScheduleActivity.this, "Please enter class name first ", Toast.LENGTH_SHORT).show();

                }else{

                    Schedule schedule = new Schedule(lecture1String,lecture2String,lecture3String,lecture4String,lecture5String,lecture6String,lecture7String,lecture8String,lecture9String);
                    databaseReference.child(classNameString).child(day).setValue(schedule);

//                    if(! lecture1String.equalsIgnoreCase("F")) {
//                        updateFunction(lecture1String, day, 1);
//                    }
//
//                    if(! lecture2String.equalsIgnoreCase("F")) {
//                        updateFunction(lecture2String, day, 2);
//                    }
//                    if(! lecture3String.equalsIgnoreCase("F")) {
//                        updateFunction(lecture3String, day, 3);
//                    }
//                    if(! lecture4String.equalsIgnoreCase("F")) {
//                        updateFunction(lecture4String, day, 4);
//                    }
//                    if(! lecture5String.equalsIgnoreCase("F")) {
//                        updateFunction(lecture5String, day, 5);
//                    }
//                    if(! lecture6String.equalsIgnoreCase("F")) {
//                        updateFunction(lecture6String, day, 6);
//                    }
//                    if(! lecture7String.equalsIgnoreCase("F")) {
//                        updateFunction(lecture7String, day, 7);
//                    }
//                    if(! lecture8String.equalsIgnoreCase("F")) {
//                        updateFunction(lecture8String, day, 8);
//                    }
//                    if(! lecture9String.equalsIgnoreCase("F")) {
//                        updateFunction(lecture9String, day, 9);
//                    }
//
//
//                    databaseReference1.child(day).child(lecture1String).setValue("B");

                    updateDatabase();


                    Toast.makeText(AddScheduleActivity.this, " data added successfully for "+dayString, Toast.LENGTH_SHORT).show();
                    lecture1.setSelection(0);
                    lecture2.setSelection(0);
                    lecture3.setSelection(0);
                    lecture4.setSelection(0);
                    lecture5.setSelection(0);
                    lecture6.setSelection(0);
                    lecture7.setSelection(0);
                    lecture8.setSelection(0);
                    lecture9.setSelection(0);



                }

            }
        });

    }

//    private void updateFunction(final String lecture1String, final String day, final int i) {
//        databaseReference1.child(day)
//        databaseReference1.child(day).child(i+"").orderByChild("room").equalTo(lecture1String).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(!dataSnapshot.exists()){
//                    databaseReference1.child(day).child(i+"").child("room").setValue(lecture1String);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(AddScheduleActivity.this, databaseError+ "", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }

    private void updateDatabase() {

         databaseReference1=FirebaseDatabase.getInstance().getReference("freeRooms");

         databaseReference.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                     for(DataSnapshot postSnapshot1:postSnapshot.getChildren()){

                         for (DataSnapshot postSnapshot3 : postSnapshot1.getChildren()){

                             if(! postSnapshot3.getValue().equals("none")){

                                 databaseReference1.child(postSnapshot1.getKey()).child(postSnapshot3.getKey()).child(postSnapshot3.getValue().toString()).setValue("y");
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
}
