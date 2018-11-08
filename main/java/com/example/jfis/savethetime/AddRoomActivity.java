package com.example.jfis.savethetime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddRoomActivity extends AppCompatActivity {
    private EditText roomId, roomType,department;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        firebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("rooms");

        roomId=findViewById(R.id.roomIdAddRoomText);
        roomType=findViewById(R.id.roomTypeAddRoomText);
        department=findViewById(R.id.departmentAddRoomText);
        addButton=findViewById(R.id.addRoomAddRoomButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String roomIdString = roomId.getText().toString();
                String roomTypeString =roomType.getText().toString();
                String departmentString = department.getText().toString();

                if( roomIdString.trim().isEmpty() || roomTypeString.trim().isEmpty() || departmentString.trim().isEmpty() ){
                    Toast.makeText(AddRoomActivity.this, "Please add proper details", Toast.LENGTH_SHORT).show();
                }else{
                    Room room = new Room(roomIdString,roomTypeString,departmentString);
                    databaseReference.child(roomIdString).setValue(room);
                    Toast.makeText(AddRoomActivity.this, "data added successfully", Toast.LENGTH_SHORT).show();

                    roomId.setText("");
                    roomType.setText("");
                    department.setText("");
                }

            }
        });

    }
}
