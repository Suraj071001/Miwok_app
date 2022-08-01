package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class ColourFragment extends Fragment {
    ArrayList<Word> colorArray = new ArrayList<Word>();
    MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;

    @Override
    public void onStop() {
        super.onStop();
        if (mediaPlayer!=null){
            mediaPlayer.release();
        }
        mediaPlayer = null;
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ColourFragment() {
        // Required empty public constructor
    }
    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if(i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);

            }else if (i == AudioManager.AUDIOFOCUS_LOSS){
                mediaPlayer.stop();
                mediaPlayer.release();
            }else if (i == AudioManager.AUDIOFOCUS_GAIN){
                mediaPlayer.start();

            }

        }
    };

    public static ColourFragment newInstance(String param1, String param2) {
        ColourFragment fragment = new ColourFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.activity_colors,container,false);
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        colorArray.add(new Word("Red","weṭeṭṭi",R.drawable.color_red,R.raw.color_red));
        colorArray.add(new Word("Green","chokokki",R.drawable.color_green,R.raw.color_green));
        colorArray.add(new Word("Brown","ṭakaakki",R.drawable.color_brown,R.raw.color_brown));
        colorArray.add(new Word("Gray","ṭopoppi",R.drawable.color_gray,R.raw.color_gray));
        colorArray.add(new Word("Black","kululli",R.drawable.color_black,R.raw.color_black));
        colorArray.add(new Word("White","kelelli",R.drawable.color_white,R.raw.color_white));
        colorArray.add(new Word("dusty yellow","ṭopiisә",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        colorArray.add(new Word("mustard yellow", "chiwiiṭә",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));


        ListView listView = rootview.findViewById(R.id.listView);
        Custom_Adapter ad = new Custom_Adapter(getContext(), R.layout.custom_layout, colorArray,R.color.category_colors);
        listView.setAdapter(ad);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int result = mAudioManager.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mediaPlayer = MediaPlayer.create(getContext(),colorArray.get(i).getAudio());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            if(mediaPlayer != null) {
                                mediaPlayer.release();
                                mediaPlayer = null;
                                mAudioManager.abandonAudioFocus(audioFocusChangeListener);
                            }
                        }
                    });
                }
            }
        });
        return rootview;
    }
}