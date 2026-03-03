package com.example.appdevactivity1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText versionInput;
    private TextView versionInfoDisplay;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize views
        versionInput = findViewById(R.id.versionInput);
        versionInfoDisplay = findViewById(R.id.versionInfoDisplay);
        searchButton = findViewById(R.id.searchButton);

        // Set window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set click listener for search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAndroidVersion();
            }
        });
    }

    private void searchAndroidVersion() {
        String codename = versionInput.getText().toString().trim().toLowerCase();
        String versionInfo = getAndroidVersionInfo(codename);

        if (versionInfo != null) {
            versionInfoDisplay.setText(versionInfo);
        } else {
            versionInfoDisplay.setText("No information found for this Android version.");
            Toast.makeText(this, "Invalid Android version", Toast.LENGTH_SHORT).show();
        }
    }

    private String getAndroidVersionInfo(String codename) {
        switch (codename) {
            case "cupcake":
                return "Android 1.5 (Cupcake)\n" +
                        "Consider using this version if you're developing for extremely legacy devices or need to understand early Android app ecosystem fundamentals. Provides basic widget support and sets the groundwork for future Android development. Useful for maintaining compatibility with very old devices.";

            case "donut":
                return "Android 1.6 (Donut)\n" +
                        "Ideal for developers targeting early Android devices with diverse screen sizes. Brings improved search capabilities and text-to-speech functionality. Best for applications requiring broad device compatibility or accessibility features on older hardware.";

            case "eclair":
                return "Android 2.0-2.1 (Eclair)\n" +
                        "Recommended for location-based applications and early social integration projects. Introduces robust GPS and mapping APIs. Critical for developers building navigation, check-in, or location-tracking applications on older Android ecosystem.";

            case "froyo":
                return "Android 2.2 (Froyo)\n" +
                        "Perfect for performance-critical applications on older devices. Introduces significant performance improvements through JIT compiler. Essential for developers optimizing apps for lower-end hardware and requiring enterprise-level device management.";

            case "gingerbread":
                return "Android 2.3 (Gingerbread)\n" +
                        "Crucial for NFC-based applications and game development. Provides enhanced sensor APIs and improved performance for graphics-intensive applications. Ideal for developers creating near-field communication apps or targeting gaming market on older devices.";

            case "honeycomb":
                return "Android 3.0 (Honeycomb)\n" +
                        "Essential for tablet-specific application development. Introduces tablet-optimized UI and fragment APIs. Best for developers creating applications specifically designed for larger screen devices and exploring multi-pane layouts.";

            case "ice cream sandwich":
                return "Android 4.0 (Ice Cream Sandwich)\n" +
                        "Recommended for creating unified phone and tablet applications. Introduces consistent design language and advanced UI toolkit. Ideal for developers seeking to create responsive applications that work across different device form factors.";

            case "jelly bean":
                return "Android 4.1-4.3 (Jelly Bean)\n" +
                        "Perfect for performance-sensitive applications requiring smooth animations. Introduces Project Butter for improved touch responsiveness. Essential for developers focused on creating high-performance, fluid user interfaces.";

            case "kitkat":
                return "Android 4.4 (KitKat)\n" +
                        "Ideal for developing applications for low-memory devices. Provides efficient memory management and print services. Best for developers targeting budget smartphones and creating memory-efficient applications.";

            case "lollipop":
                return "Android 5.0-5.1 (Lollipop)\n" +
                        "Crucial for modern material design applications. Introduces ART runtime for improved performance and Material Design guidelines. Perfect for developers creating visually consistent and high-performance applications.";

            case "marshmallow":
                return "Android 6.0 (Marshmallow)\n" +
                        "Essential for privacy-focused application development. Introduces runtime permissions model and fingerprint authentication. Ideal for developers creating security-sensitive apps with granular permission controls.";

            case "nougat":
                return "Android 7.0-7.1 (Nougat)\n" +
                        "Recommended for multi-window and advanced multitasking applications. Introduces seamless system updates and improved background processing. Best for developers creating apps that need to work efficiently in split-screen or multi-task environments.";

            case "oreo":
                return "Android 8.0-8.1 (Oreo)\n" +
                        "Ideal for creating efficient background services and notification-heavy applications. Introduces picture-in-picture mode and notification channels. Perfect for developers focusing on media apps and complex notification systems.";

            case "pie":
                return "Android 9.0 (Pie)\n" +
                        "Essential for AI-powered and context-aware applications. Introduces machine learning APIs and adaptive features. Recommended for developers creating intelligent apps with context-based functionality.";

            case "quince tart":
                return "Android 10\n" +
                        "Crucial for privacy-conscious and dark mode applications. Introduces system-wide dark theme and advanced privacy controls. Ideal for developers creating modern, privacy-focused applications with adaptive UI.";

            case "red velvet cake":
                return "Android 11\n" +
                        "Recommended for media-rich and privacy-focused applications. Introduces one-time permissions and improved media controls. Best for developers creating apps with advanced media handling and granular privacy features.";

            case "snow cone":
                return "Android 12\n" +
                        "Essential for personalized and privacy-aware applications. Introduces Material You design with dynamic theming. Ideal for developers creating highly customizable and privacy-conscious applications.";

            case "tiramisu":
                return "Android 13\n" +
                        "Perfect for localization and media-sharing applications. Introduces per-app language preferences and granular media permissions. Recommended for developers creating globally accessible and privacy-focused apps.";

            case "upside down cake":
                return "Android 14\n" +
                        "Crucial for adaptive and efficient applications. Improves support for large screens and foldable devices. Best for developers creating versatile apps that work across various device form factors with advanced power management.";

            case "vanilla ice cream":
                return "Android 15\n" +
                        "Aims to bring enhanced UI features, better battery optimization, and support for the latest hardware advancements. Expected to release in 2024.";

            case "baklava":
                return "Android 16 BETA\n" +
                        "Currently in beta, introducing new features and changes for upcoming official releases. Expected to have performance enhancements and improved app compatibility.";
            default:
                return null;
        }
    }
}