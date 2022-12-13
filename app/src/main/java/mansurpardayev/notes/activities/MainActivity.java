package mansurpardayev.notes.activities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mansurpardayev.notes.R;
import mansurpardayev.notes.database.NoteDatabase;
import mansurpardayev.notes.note.Note;
import mansurpardayev.notes.note.NoteAdapter;

public class MainActivity extends Activity {
    RecyclerView recyclerView;
    NoteAdapter adapter;
    FloatingActionButton floatingActionButton;
    List<Note> notes;
    NoteDatabase database;
    EditText search;
    TextView isEmptyTxt;

    List<Note> newNoteList;
    boolean resetData = false;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        setAds();

        findById();
        database = NoteDatabase.getInstance(this);
        setNotes();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NoteAdapter(notes, this);

        if (notes.isEmpty()) isEmptyTxt.setVisibility(View.VISIBLE);
        else isEmptyTxt.setVisibility(View.GONE);

        recyclerView.setAdapter(adapter);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchTitle = editable.toString();
                newNoteList = new ArrayList<>();
                for (int i = 0; i < notes.size(); i++) {
                    if (notes.get(i).getTitle().toLowerCase(Locale.ROOT).contains(searchTitle.toLowerCase(Locale.ROOT)) ||
                            notes.get(i).getText().toLowerCase(Locale.ROOT).contains(searchTitle.toLowerCase(Locale.ROOT))) {
                        newNoteList.add(notes.get(i));
                    }
                }
                adapter.resetNotes(newNoteList);
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(MainActivity.this);
                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            startActivity(new Intent(MainActivity.this, CreateNoteActivity.class));
                            mInterstitialAd = null;
                            setAds();
                        }
                    });
                }
                  else  startActivity(new Intent(MainActivity.this, CreateNoteActivity.class));
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (notes.isEmpty()) isEmptyTxt.setVisibility(View.VISIBLE);
        else isEmptyTxt.setVisibility(View.GONE);
        if (resetData) {
            setNotes();
            adapter.resetNotes(notes);
        }
        resetData = true;
    }

    public void setNotes() {
        notes = new ArrayList<>();
        notes = NoteDatabase.getInstance(this).getNoteInterface().getAllNotes();
    }

    public void findById() {
        isEmptyTxt = findViewById(R.id.isemptytxt);
        recyclerView = findViewById(R.id.notesRecycler);
        search = findViewById(R.id.searchEdit);
        floatingActionButton = findViewById(R.id.addNewNoteBtn);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (notes.isEmpty()) isEmptyTxt.setVisibility(View.VISIBLE);
        else isEmptyTxt.setVisibility(View.GONE);
    }

    public void setAds() {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        mInterstitialAd = null;
                    }
                });
    }
}