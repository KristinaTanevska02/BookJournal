package com.example.bookjournal;

import android.content.Intent;
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

import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BooksDetailsActivity2 extends AppCompatActivity {
    private Integer bookId;
    private Integer userId;
    private TextView tvTitleAuthor, tvPublisher, date;
    private EditText etMyRating, etReview;
    private Button save;
    private DatabaseHelper dbHelper;
    private String dateAdded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_books_details2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvTitleAuthor = findViewById(R.id.tvTitleAuthor);
        tvPublisher = findViewById(R.id.tvPublisher);
        date = findViewById(R.id.date);
        etMyRating = findViewById(R.id.etMyRating);
        etReview = findViewById(R.id.etReview);
        save = findViewById(R.id.save);
        dbHelper = new DatabaseHelper(this);

        bookId = getIntent().getIntExtra("bookid", -1);
        userId = getIntent().getIntExtra("userid", -1);

        String title = getIntent().getStringExtra("title");
        String author = getIntent().getStringExtra("author");
        String publisher = getIntent().getStringExtra("publisher");
        String review = getIntent().getStringExtra("review");
        float rating = getIntent().getFloatExtra("rating", 0.0f);
        dateAdded = dbHelper.getDateAdded(userId,bookId);

        tvTitleAuthor.setText(title + " - " + author);
        tvPublisher.setText(publisher);
        etReview.setText(review != null ? review : "");
        etMyRating.setText(String.valueOf(rating));
        date.setText("Датум: " + dateAdded);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedReview = etReview.getText().toString();
                float updatedRating;

                try {
                    updatedRating = Float.parseFloat(etMyRating.getText().toString());
                } catch (NumberFormatException e) {
                    return;
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String currentDate = dateFormat.format(new Date());
                date.setText("Датум: " + currentDate);

                etReview.setText(updatedReview);
                etMyRating.setText(String.valueOf(updatedRating));
                dbHelper.updateBookReviewAndRating(userId, bookId, updatedReview, updatedRating, currentDate);
                Intent intent = new Intent(getApplicationContext(), MyBooksActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}