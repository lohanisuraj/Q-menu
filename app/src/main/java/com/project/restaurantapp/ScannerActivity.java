package com.project.restaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends Activity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    @Override
    protected void onCreate(Bundle state) {

        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view

        setContentView(mScannerView);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mScannerView.stopCamera();           // Stop camera on pause

    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume

    }

    @Override
    public void handleResult(Result result) {

        final Result res=result;
            mScannerView.stopCamera();
        DatabaseReference databaseres= FirebaseDatabase.getInstance().getReference("resturant").child(result.getText());
        databaseres.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()==null){
                    Toast.makeText(ScannerActivity.this,"Please Scan Again Resturant Not Found", Toast.LENGTH_LONG).show();
                    return;
                }else{
                    Intent myIntent = new Intent(ScannerActivity.this, RestaurantViwer.class);
                    myIntent.putExtra("id",res.getText());
                    ScannerActivity.this.startActivity(myIntent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
