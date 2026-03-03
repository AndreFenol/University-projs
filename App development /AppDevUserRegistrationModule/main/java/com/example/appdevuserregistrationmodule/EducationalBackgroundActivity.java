package com.example.appdevuserregistrationmodule;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton; // Keep import for listener type
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EducationalBackgroundActivity extends AppCompatActivity {

    private EditText elementarySchool, elementaryDegree;
    private EditText secondarySchool, secondaryDegree;
    private EditText vocationalSchool, vocationalDegree;
    private EditText collegeSchool, collegeDegree;
    private EditText graduateSchool, graduateDegree;
    private CheckBox chkElementary, chkSecondary, chkCollege, chkGraduate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educational_background);

        initializeViews();

        chkSecondary.setEnabled(false);
        chkSecondary.setChecked(false);
        chkCollege.setEnabled(false);
        chkCollege.setChecked(false);
        chkGraduate.setEnabled(false);
        chkGraduate.setChecked(false);


        setupCheckboxListeners();

        Button btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(v -> {
            if (validateForm()) {
                Bundle allData = new Bundle();


                if (getIntent().getExtras() != null) {
                    allData.putAll(getIntent().getExtras());
                }


                if (chkElementary.isChecked()) {
                    allData.putString("elementarySchool", elementarySchool.getText().toString());
                    allData.putString("elementaryDegree", elementaryDegree.getText().toString());
                }
                if (chkSecondary.isChecked()) {
                    allData.putString("secondarySchool", secondarySchool.getText().toString());
                    allData.putString("secondaryDegree", secondaryDegree.getText().toString());
                }
                if (chkCollege.isChecked()) {
                    allData.putString("collegeSchool", collegeSchool.getText().toString());
                    allData.putString("collegeDegree", collegeDegree.getText().toString());
                }
                if (chkGraduate.isChecked()) {
                    allData.putString("graduateSchool", graduateSchool.getText().toString());
                    allData.putString("graduateDegree", graduateDegree.getText().toString());
                }

                // Add vocational data (always optional)
                allData.putString("vocationalSchool", vocationalSchool.getText().toString());
                allData.putString("vocationalDegree", vocationalDegree.getText().toString());

                Intent intent = new Intent(this, CriminalBackgroundActivity.class);
                intent.putExtras(allData);
                startActivity(intent);
            }
        });
    }

    private void initializeViews() {
        elementarySchool = findViewById(R.id.elementarySchool);
        elementaryDegree = findViewById(R.id.elementaryDegree);
        secondarySchool = findViewById(R.id.secondarySchool);
        secondaryDegree = findViewById(R.id.secondaryDegree);
        vocationalSchool = findViewById(R.id.vocationalSchool);
        vocationalDegree = findViewById(R.id.vocationalDegree);
        collegeSchool = findViewById(R.id.collegeSchool);
        collegeDegree = findViewById(R.id.collegeDegree);
        graduateSchool = findViewById(R.id.graduateSchool);
        graduateDegree = findViewById(R.id.graduateDegree);

        chkElementary = findViewById(R.id.chkElementary);
        chkSecondary = findViewById(R.id.chkSecondary);
        chkCollege = findViewById(R.id.chkCollege);
        chkGraduate = findViewById(R.id.chkGraduate);
    }

    private void setupCheckboxListeners() {


        chkElementary.setOnCheckedChangeListener((buttonView, isChecked) -> {

            chkSecondary.setEnabled(isChecked);
            if (!isChecked) {
                chkSecondary.setChecked(false);
            }

            elementarySchool.setEnabled(isChecked);
            elementaryDegree.setEnabled(isChecked);
            if (!isChecked) {
                elementarySchool.setText("");
                elementaryDegree.setText("");
            }
        });

        chkSecondary.setOnCheckedChangeListener((buttonView, isChecked) -> {

            chkCollege.setEnabled(isChecked);
            if (!isChecked) {
                chkCollege.setChecked(false);
            }

            secondarySchool.setEnabled(isChecked);
            secondaryDegree.setEnabled(isChecked);
            if (!isChecked) {
                secondarySchool.setText("");
                secondaryDegree.setText("");
            }
        });

        chkCollege.setOnCheckedChangeListener((buttonView, isChecked) -> {

            chkGraduate.setEnabled(isChecked);
            if (!isChecked) {
                chkGraduate.setChecked(false);
            }


            collegeSchool.setEnabled(isChecked);
            collegeDegree.setEnabled(isChecked);
            if (!isChecked) {
                collegeSchool.setText("");
                collegeDegree.setText("");
            }
        });


        chkGraduate.setOnCheckedChangeListener((buttonView, isChecked) -> {
            graduateSchool.setEnabled(isChecked);
            graduateDegree.setEnabled(isChecked);
            if (!isChecked) {
                graduateSchool.setText("");
                graduateDegree.setText("");
            }
        });
    }

    private boolean validateForm() {

        if (chkElementary.isChecked()) {
            if (elementarySchool.getText().toString().trim().isEmpty()) {
                showToast("Please enter elementary school name");
                return false;
            }
            if (elementaryDegree.getText().toString().trim().isEmpty()) {
                showToast("Please enter elementary education");
                return false;
            }
        }

        if (chkSecondary.isChecked()) {
            if (secondarySchool.getText().toString().trim().isEmpty()) {
                showToast("Please enter secondary school name");
                return false;
            }
            if (secondaryDegree.getText().toString().trim().isEmpty()) {
                showToast("Please enter secondary education");
                return false;
            }
        }

        if (chkCollege.isChecked()) {
            if (collegeSchool.getText().toString().trim().isEmpty()) {
                showToast("Please enter college name");
                return false;
            }
            if (collegeDegree.getText().toString().trim().isEmpty()) {
                showToast("Please enter college degree");
                return false;
            }
        }

        if (chkGraduate.isChecked()) {
            if (graduateSchool.getText().toString().trim().isEmpty()) {
                showToast("Please enter graduate school name");
                return false;
            }
            if (graduateDegree.getText().toString().trim().isEmpty()) {
                showToast("Please enter graduate degree");
                return false;
            }
        }


        return true;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}