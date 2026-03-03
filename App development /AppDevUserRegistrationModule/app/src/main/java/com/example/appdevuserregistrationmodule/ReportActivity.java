package com.example.appdevuserregistrationmodule;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        ImageView reportProfileImage = findViewById(R.id.reportProfileImage);
        TextView personalSummary = findViewById(R.id.personalSummary);
        TextView educationalSummary = findViewById(R.id.educationalSummary);
        TextView criminalSummary = findViewById(R.id.criminalSummary);
        Button btnBack = findViewById(R.id.btnBack);

        Bundle receivedData = getIntent().getExtras();
        if (receivedData == null || !receivedData.containsKey("firstName")) {
            Toast.makeText(this, "No registration data found!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if (receivedData.containsKey("profileImage")) {
            byte[] byteArray = receivedData.getByteArray("profileImage");
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            reportProfileImage.setImageBitmap(bitmap);
        }

        personalSummary.setText(formatPersonalData(receivedData));
        educationalSummary.setText(formatEducationalData(receivedData));
        criminalSummary.setText(formatCriminalData(receivedData));

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, PersonalBackgroundActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    private String formatPersonalData(Bundle data) {
        StringBuilder sb = new StringBuilder();
        sb.append("• Name: ")
                .append(data.getString("firstName", ""))
                .append(" ")
                .append(data.getString("middleName", ""))
                .append(" ")
                .append(data.getString("lastName", ""))
                .append("\n\n");

        sb.append("• Email: ").append(data.getString("email", "")).append("\n");
        sb.append("• Phone: ").append(data.getString("phone", "")).append("\n");
        sb.append("• Gender: ").append(data.getString("gender", "")).append("\n\n");

        sb.append("• Height: ").append(data.getString("height", "")).append(" m\n");
        sb.append("• Weight: ").append(data.getString("weight", "")).append(" kg\n");
        sb.append("• Civil Status: ").append(data.getString("civilStatus", "")).append("\n\n");

        appendIfNotEmpty(sb, "Pagibig No", data.getString("pagibigNo"));
        appendIfNotEmpty(sb, "TIN No", data.getString("tinNo"));
        appendIfNotEmpty(sb, "Philhealth", data.getString("philhealth"));
        appendIfNotEmpty(sb, "GSIS No", data.getString("gsisNo"));

        sb.append("• Emergency Contact:\n   ")
                .append(data.getString("emergencyName", ""))
                .append(" (")
                .append(data.getString("emergencyRelationship", ""))
                .append(")\n   ")
                .append(data.getString("emergencyContact", ""));

        return sb.toString();
    }

    private String formatEducationalData(Bundle data) {
        StringBuilder sb = new StringBuilder();
        sb.append("• Elementary:\n   ")
                .append(data.getString("elementarySchool", ""))
                .append("\n   ")
                .append(data.getString("elementaryDegree", ""))
                .append("\n\n");

        sb.append("• Secondary:\n   ")
                .append(data.getString("secondarySchool", ""))
                .append("\n   ")
                .append(data.getString("secondaryDegree", ""))
                .append("\n\n");

        appendEducationIfExists(sb, "Vocational", data.getString("vocationalSchool"), data.getString("vocationalDegree"));
        appendEducationIfExists(sb, "College", data.getString("collegeSchool"), data.getString("collegeDegree"));
        appendEducationIfExists(sb, "Graduate Studies", data.getString("graduateSchool"), data.getString("graduateDegree"));

        return sb.toString();
    }

    private String formatCriminalData(Bundle data) {
        StringBuilder sb = new StringBuilder();
        appendCriminalAnswer(sb, "1. Administrative offense",
                data.getBoolean("q1Yes", false), data.getString("q1Details", ""));
        appendCriminalAnswer(sb, "2. Criminally charged",
                data.getBoolean("q2Yes", false), data.getString("q2Details", ""));
        appendCriminalAnswer(sb, "3. Convicted of crime",
                data.getBoolean("q3Yes", false), data.getString("q3Details", ""));
        appendCriminalAnswer(sb, "4a. Indigenous group member",
                data.getBoolean("q4aYes", false), data.getString("q4aDetails", ""));
        appendCriminalAnswer(sb, "4b. Person with disability",
                data.getBoolean("q4bYes", false), data.getString("q4bDetails", ""));
        appendCriminalAnswer(sb, "4c. Solo parent",
                data.getBoolean("q4cYes", false), data.getString("q4cDetails", ""));

        return sb.toString();
    }

    private void appendIfNotEmpty(StringBuilder sb, String label, String value) {
        if (value != null && !value.isEmpty()) {
            sb.append("• ").append(label).append(": ").append(value).append("\n\n");
        }
    }

    private void appendEducationIfExists(StringBuilder sb, String level, String school, String degree) {
        if (school != null && !school.isEmpty()) {
            sb.append("• ").append(level).append(":\n   ")
                    .append(school).append("\n   ")
                    .append(degree).append("\n\n");
        }
    }

    private void appendCriminalAnswer(StringBuilder sb, String question, boolean answeredYes, String details) {
        sb.append("• ").append(question).append(": ")
                .append(answeredYes ? "Yes" : "No");
        if (answeredYes && !details.isEmpty()) {
            sb.append("\n   Details: ").append(details);
        }
        sb.append("\n\n");
    }
}