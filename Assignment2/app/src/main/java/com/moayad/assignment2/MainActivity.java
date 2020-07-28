package com.moayad.assignment2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.InputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    EditText etFirstName, etLastName, etAddress, etPhoneNumber, etDOB, etGender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etFirstName = (EditText)findViewById(R.id.FirstName);
        etLastName = (EditText)findViewById(R.id.LastName);
        etAddress = (EditText)findViewById(R.id.Address);
        etPhoneNumber = (EditText)findViewById(R.id.PhoneNumber);
        etDOB = (EditText)findViewById(R.id.DOB);
        etGender = (EditText)findViewById(R.id.Gender);
    }

    public void SaveAsTXT(View view) {
        SaveToFile("Personal Information.txt");
    }

    public void ViewTXT(View view) {
        DisplayFile("Personal Information.txt");
    }

    public void SaveAsPDF(View view) {
        SaveToFile("Personal Information.pdf");
    }

    public void ViewPDF(View view) {
        DisplayFile("Personal Information.pdf");
    }

    void SaveToFile(String fileName){
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String address = etAddress.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();
        String DoB = etDOB.getText().toString();
        String gender = etGender.getText().toString();

        if(!firstName.equals("") && !lastName.equals("") && !address.equals("") && !phoneNumber.equals("") && !DoB.equals("") && !gender.equals("")){
            try {
                OutputStreamWriter OSR = new OutputStreamWriter(openFileOutput(fileName, 0));
                OSR.write("Personal Information:\n\n");
                OSR.write("First Name: "+firstName+"\n");
                OSR.write("Last Name: "+lastName+"\n");
                OSR.write("Address: "+address+"\n");
                OSR.write("Phone Number: "+phoneNumber+"\n");
                OSR.write("Date of Birth: "+DoB+"\n");
                OSR.write("Gender: "+gender);
                OSR.close();

                Toast.makeText(this, "Your data is saved in TXT file", Toast.LENGTH_LONG).show();
            }
            catch (Throwable e) {
                Toast.makeText(this, "Exception: "+e.toString(), Toast.LENGTH_LONG).show();
            }
        }
        else
            Toast.makeText(this,"Please fill all the fields!!",Toast.LENGTH_SHORT).show();
    }
    void DisplayFile(String fileName){
        try {
            InputStream IS = openFileInput(fileName);
            if (IS != null) {
                Intent intent = new Intent(this,ViewFile.class);
                intent.putExtra("fileName",fileName);
                startActivity(intent);
            }
        }
        catch (java.io.FileNotFoundException e) {
            Toast.makeText(this, "There is no TXT file saved!!", Toast.LENGTH_LONG).show();
        }
    }
}
