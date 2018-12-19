package com.example.idotalmor.realmdatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.idotalmor.realmdatabase.model.Student;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";//create by typing logt+enter

    public static boolean active = false;

    EditText name,marks;
    TextView display;
    Button saveButton;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        marks = findViewById(R.id.marks);
        display = findViewById(R.id.display);

        saveButton = findViewById(R.id.saveButton);
        active = true;

        Log.i(TAG,"onCreate:View Initialization done");

        realm = Realm.getDefaultInstance();
        //readData();

        //delete the entire database in the default file(default.realm) we can store objects in multiple files with multiple Realm Configuration files.
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                readData();
            }
        });

    }

    private void saveData(){

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Student student = bgRealm.createObject(Student.class);
                student.setName(name.getText().toString().trim());
                student.setAge(Integer.parseInt(marks.getText().toString().trim()));//the trim remove the start and end spaces in the string.
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                Log.d(TAG,"onSuccess:Data Written Successfully");

                //coming from background thread - check if activity is alive
                if(active){
                    readData();

                }
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Log.d(TAG,"onError:Error Occured - "+error.getMessage());

            }
        });


    }

    private void readData(){

        RealmResults<Student> students = realm.where(Student.class).findAll();
        display.setText("");
        String data = "";

        for(Student student:students){

            Log.d(TAG,"readData");
            data = data+ "\n" +student.toString();

        }
        display.setText(data);

    }

    @Override
    protected void onStop() {
        super.onStop();
        active = false;

    }
}
