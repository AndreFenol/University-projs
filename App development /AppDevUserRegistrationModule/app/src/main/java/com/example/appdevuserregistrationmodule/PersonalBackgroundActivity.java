package com.example.appdevuserregistrationmodule;

import android.provider.MediaStore;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

public class PersonalBackgroundActivity extends AppCompatActivity {

    private ImageView imageProfile;
    private Button btnTakePhoto;
    private Bitmap profileBitmap;
    private static final int CAMERA_REQUEST = 100;
    private EditText firstName, middleName, lastName, email, phone, height, weight;
    private EditText pagibigNo, tinNo, philhealth, gsisNo;
    private EditText emergencyName, emergencyContact;
    private Spinner spinnerRelationship;
    private RadioGroup genderGroup, civilStatusGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_background);

        initializeViews();
        setupRelationshipSpinner();
        imageProfile = findViewById(R.id.imageProfile);
        btnTakePhoto = findViewById(R.id.btnTakePhoto);

        btnTakePhoto.setOnClickListener(v -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "No camera app found", Toast.LENGTH_SHORT).show();
            }
        });


        Button btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(v -> {
            if (validatePersonalBackground()) {
                Bundle personalBundle = new Bundle();


                // Personal Information
                personalBundle.putString("firstName", firstName.getText().toString());
                personalBundle.putString("middleName", middleName.getText().toString());
                personalBundle.putString("lastName", lastName.getText().toString());
                personalBundle.putString("email", email.getText().toString());

                // Gender and Contact
                RadioButton selectedGender = findViewById(genderGroup.getCheckedRadioButtonId());
                personalBundle.putString("gender", selectedGender.getText().toString());
                personalBundle.putString("phone", phone.getText().toString());

                // Physical Attributes
                personalBundle.putString("height", height.getText().toString());
                personalBundle.putString("weight", weight.getText().toString());

                // Civil Status
                RadioButton selectedCivilStatus = findViewById(civilStatusGroup.getCheckedRadioButtonId());
                personalBundle.putString("civilStatus", selectedCivilStatus.getText().toString());

                // Government IDs
                personalBundle.putString("pagibigNo", pagibigNo.getText().toString());
                personalBundle.putString("tinNo", tinNo.getText().toString());
                personalBundle.putString("philhealth", philhealth.getText().toString());
                personalBundle.putString("gsisNo", gsisNo.getText().toString());

                // Emergency Contact
                personalBundle.putString("emergencyName", emergencyName.getText().toString());
                personalBundle.putString("emergencyContact", emergencyContact.getText().toString());
                personalBundle.putString("emergencyRelationship", spinnerRelationship.getSelectedItem().toString());

                // Add Profile Image
                if (profileBitmap != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    profileBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // Compress as PNG
                    byte[] byteArray = stream.toByteArray();
                    personalBundle.putByteArray("profileImage", byteArray);

                }

                Intent intent = new Intent(this, EducationalBackgroundActivity.class);
                intent.putExtras(personalBundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            profileBitmap = (Bitmap) data.getExtras().get("data");
            imageProfile.setImageBitmap(profileBitmap);
        }
    }
    private void initializeViews() {
        firstName = findViewById(R.id.firstName);
        middleName = findViewById(R.id.middleName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        pagibigNo = findViewById(R.id.pagibigNo);
        tinNo = findViewById(R.id.tinNo);
        philhealth = findViewById(R.id.philhealth);
        gsisNo = findViewById(R.id.gsisNo);
        emergencyName = findViewById(R.id.emergencyName);
        emergencyContact = findViewById(R.id.emergencyContact);
        spinnerRelationship = findViewById(R.id.spinnerRelationship);
        genderGroup = findViewById(R.id.radioGenderGroup);
        civilStatusGroup = findViewById(R.id.radioCivilStatusGroup);
    }

    private void setupRelationshipSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.relationship_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRelationship.setAdapter(adapter);
    }

    private boolean validatePersonalBackground() {
        if (profileBitmap == null) {  // NEW: Photo validation
            showToast("Profile photo is required");
            return false;
        }
        if (firstName.getText().toString().isEmpty()) {
            showToast("Please enter your first name");
            return false;
        }
        if (lastName.getText().toString().isEmpty()) {
            showToast("Please enter your last name");
            return false;
        }
        if (email.getText().toString().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            showToast("Please enter a valid email address");
            return false;
        }
        if (genderGroup.getCheckedRadioButtonId() == -1) {
            showToast("Please select your gender");
            return false;
        }
        if (phone.getText().toString().isEmpty()) {
            showToast("Please enter your phone number");
            return false;
        }
        if (height.getText().toString().isEmpty()) {
            showToast("Please enter your height");
            return false;
        }
        if (weight.getText().toString().isEmpty()) {
            showToast("Please enter your weight");
            return false;
        }
        if (civilStatusGroup.getCheckedRadioButtonId() == -1) {
            showToast("Please select your civil status");
            return false;
        }
        if (emergencyName.getText().toString().isEmpty()) {
            showToast("Please enter emergency contact name");
            return false;
        }
        if (emergencyContact.getText().toString().isEmpty()) {
            showToast("Please enter emergency contact number");
            return false;
        }
        return true;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}