package com.example.pdfcreator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class MainActivity2 extends AppCompatActivity {

    private PDFView pdfView;

    Button btnPrint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        pdfView = findViewById(R.id.pdfView);

        btnPrint = findViewById(R.id.btnPrint);

        // Get the PDF file path from the intent
        String filePath = getIntent().getStringExtra("PDF_PATH");

        // Load and display the PDF file
        pdfView.fromFile(new File(filePath))
                .enableSwipe(true) // Allows to swipe pages horizontally
                .load();

        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Creating a URI from the file path
                Uri pdfUri = FileProvider.getUriForFile(MainActivity2.this,
                        "com.example.pdfcreator.FileProvider", new File(filePath));

                // Create an intent with ACTION_SEND
                Intent i = new Intent(Intent.ACTION_SEND);

                // Set the type of the content to "application/pdf"
                i.setType("application/pdf");

                // Set the URI of the file to be shared
                i.putExtra(Intent.EXTRA_STREAM, pdfUri);

                // Set the package name of PrinterShare app
                //ru.a402d.rawbtprinter
                //com.dynamixsoftware.printershare
                i.setPackage("ru.a402d.rawbtprinter");

                try {
                    // Start the activity with the specified intent
                    startActivity(i);
                } catch (Exception e) {
                    // If PrinterShare app is not installed or fails to open, notify the user
                    Toast.makeText(MainActivity2.this, "app not installed", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }
}
