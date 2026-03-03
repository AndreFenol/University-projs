package com.example.appdevuserregistrationmodule;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CriminalBackgroundActivity extends AppCompatActivity {

    private CheckBox cb1Yes, cb1No, cb2Yes, cb2No, cb3Yes, cb3No;
    private CheckBox cb4aYes, cb4aNo, cb4bYes, cb4bNo, cb4cYes, cb4cNo;
    private EditText details1, details2, details3, details4a, details4b, details4c;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criminal_background);

        initializeViews();
        setupAllCheckboxListeners();

        btnSubmit.setOnClickListener(v -> {
            if (validateForm()) {
                proceedToReportActivity();
            }
        });
    }

    private void initializeViews() {
        // Question 1
        cb1Yes = findViewById(R.id.cb1Yes);
        cb1No = findViewById(R.id.cb1No);
        details1 = findViewById(R.id.details1);

        // Question 2
        cb2Yes = findViewById(R.id.cb2Yes);
        cb2No = findViewById(R.id.cb2No);
        details2 = findViewById(R.id.details2);

        // Question 3
        cb3Yes = findViewById(R.id.cb3Yes);
        cb3No = findViewById(R.id.cb3No);
        details3 = findViewById(R.id.details3);

        // Question 4a
        cb4aYes = findViewById(R.id.cb4aYes);
        cb4aNo = findViewById(R.id.cb4aNo);
        details4a = findViewById(R.id.details4a);

        // Question 4b
        cb4bYes = findViewById(R.id.cb4bYes);
        cb4bNo = findViewById(R.id.cb4bNo);
        details4b = findViewById(R.id.details4b);

        // Question 4c
        cb4cYes = findViewById(R.id.cb4cYes);
        cb4cNo = findViewById(R.id.cb4cNo);
        details4c = findViewById(R.id.details4c);

        // Submit button
        btnSubmit = findViewById(R.id.btnSubmit);

        // Disable all details fields by default
        disableAllDetailsFields();
    }

    private void setupAllCheckboxListeners() {
        // Question 1 Listeners
        cb1Yes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cb1No.setChecked(false);
                details1.setEnabled(true);
            } else if (!cb1No.isChecked()) {
                details1.setEnabled(false);
                details1.setText("");
            }
        });

        cb1No.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cb1Yes.setChecked(false);
                details1.setEnabled(false);
                details1.setText("");
            }
        });

        // Question 2 Listeners
        cb2Yes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cb2No.setChecked(false);
                details2.setEnabled(true);
            } else if (!cb2No.isChecked()) {
                details2.setEnabled(false);
                details2.setText("");
            }
        });

        cb2No.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cb2Yes.setChecked(false);
                details2.setEnabled(false);
                details2.setText("");
            }
        });

        // Question 3 Listeners
        cb3Yes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cb3No.setChecked(false);
                details3.setEnabled(true);
            } else if (!cb3No.isChecked()) {
                details3.setEnabled(false);
                details3.setText("");
            }
        });

        cb3No.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cb3Yes.setChecked(false);
                details3.setEnabled(false);
                details3.setText("");
            }
        });

        // Question 4a Listeners
        cb4aYes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cb4aNo.setChecked(false);
                details4a.setEnabled(true);
            } else if (!cb4aNo.isChecked()) {
                details4a.setEnabled(false);
                details4a.setText("");
            }
        });

        cb4aNo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cb4aYes.setChecked(false);
                details4a.setEnabled(false);
                details4a.setText("");
            }
        });

        // Question 4b Listeners
        cb4bYes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cb4bNo.setChecked(false);
                details4b.setEnabled(true);
            } else if (!cb4bNo.isChecked()) {
                details4b.setEnabled(false);
                details4b.setText("");
            }
        });

        cb4bNo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cb4bYes.setChecked(false);
                details4b.setEnabled(false);
                details4b.setText("");
            }
        });

        // Question 4c Listeners
        cb4cYes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cb4cNo.setChecked(false);
                details4c.setEnabled(true);
            } else if (!cb4cNo.isChecked()) {
                details4c.setEnabled(false);
                details4c.setText("");
            }
        });

        cb4cNo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cb4cYes.setChecked(false);
                details4c.setEnabled(false);
                details4c.setText("");
            }
        });
    }

    private void disableAllDetailsFields() {
        details1.setEnabled(false);
        details2.setEnabled(false);
        details3.setEnabled(false);
        details4a.setEnabled(false);
        details4b.setEnabled(false);
        details4c.setEnabled(false);
    }

    private boolean validateForm() {
        // Validate all questions
        if (!validateQuestion(cb1Yes, cb1No, details1, "Question 1")) return false;
        if (!validateQuestion(cb2Yes, cb2No, details2, "Question 2")) return false;
        if (!validateQuestion(cb3Yes, cb3No, details3, "Question 3")) return false;
        if (!validateQuestion(cb4aYes, cb4aNo, details4a, "Question 4a")) return false;
        if (!validateQuestion(cb4bYes, cb4bNo, details4b, "Question 4b")) return false;
        if (!validateQuestion(cb4cYes, cb4cNo, details4c, "Question 4c")) return false;

        return true;
    }

    private boolean validateQuestion(CheckBox yes, CheckBox no, EditText details, String questionName) {
        // Check if at least one option is selected
        if (!yes.isChecked() && !no.isChecked()) {
            showToast("Please answer " + questionName);
            return false;
        }

        // If "Yes" is checked, details must not be empty
        if (yes.isChecked() && details.getText().toString().trim().isEmpty()) {
            showToast("Please provide details for " + questionName);
            return false;
        }

        return true;
    }

    private void proceedToReportActivity() {
        Intent intent = new Intent(this, ReportActivity.class);

        // Pass all previous data
        if (getIntent().getExtras() != null) {
            intent.putExtras(getIntent().getExtras());
        }

        // Add criminal background data
        intent.putExtra("q1Yes", cb1Yes.isChecked());
        intent.putExtra("q1Details", details1.getText().toString());
        intent.putExtra("q2Yes", cb2Yes.isChecked());
        intent.putExtra("q2Details", details2.getText().toString());
        intent.putExtra("q3Yes", cb3Yes.isChecked());
        intent.putExtra("q3Details", details3.getText().toString());
        intent.putExtra("q4aYes", cb4aYes.isChecked());
        intent.putExtra("q4aDetails", details4a.getText().toString());
        intent.putExtra("q4bYes", cb4bYes.isChecked());
        intent.putExtra("q4bDetails", details4b.getText().toString());
        intent.putExtra("q4cYes", cb4cYes.isChecked());
        intent.putExtra("q4cDetails", details4c.getText().toString());

        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}