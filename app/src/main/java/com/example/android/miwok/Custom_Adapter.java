package com.example.android.miwok;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class Custom_Adapter extends ArrayAdapter<Word> {
    private int mcolor;

    public Custom_Adapter(@NonNull Context context, int resource, @NonNull ArrayList<Word> objects, int color) {
        super(context, resource, objects);
        this.mcolor = color;
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_layout,parent,false);
        TextView miwok_number = convertView.findViewById(R.id.textView);
        TextView number = convertView.findViewById(R.id.textView2);
        ImageView imageView = convertView.findViewById(R.id.imageView2);
        LinearLayout linearLayout = convertView.findViewById(R.id.linearLayout);
        LinearLayout linearLayout1 = convertView.findViewById(R.id.linearLayout_textView);

        String n = getItem(position).getTranslatedNumber();
        number.setText(n);

        miwok_number.setText(getItem(position).getMiwokNumber());
        if (getItem(position).hasImage()){
            imageView.setImageResource(getItem(position).getImage_resourse());
        }else {
            imageView.setVisibility(View.GONE);
            ViewGroup.LayoutParams params = linearLayout1.getLayoutParams();
            params.width = 970;
            linearLayout1.setLayoutParams(params);
        }

        int color = ContextCompat.getColor(getContext(), mcolor);
        linearLayout.setBackgroundColor(color);


//        int audio = getItem(position).getAudio();
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Word word = getItem(position);
//                Log.v("check_data_of_word", ""+word);
//
//                MediaPlayer mediaPlayer = MediaPlayer.create(getContext(),audio);
//                mediaPlayer.start();
//                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mediaPlayer) {
//                        mediaPlayer.release();
//                    }
//                });
//
//            }
//        });

        return convertView;
    }
}
