package com.eyubero.marvelbank.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eyubero.marvelbank.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar
    }

}