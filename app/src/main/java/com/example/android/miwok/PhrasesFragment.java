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


public class PhrasesFragment extends Fragment {
    ArrayList<Word> phrasesArray = new ArrayList<Word>();
    MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;

    @Override
    public void onStop() {
        super.onStop();
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PhrasesFragment() {
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


    public static PhrasesFragment newInstance(String param1, String param2) {
        PhrasesFragment fragment = new PhrasesFragment();
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
        View rootview = inflater.inflate(R.layout.activity_phrases,container,false);
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        phrasesArray.add(new Word("Where are you going?","minto wuksus",R.raw.phrase_where_are_you_going));
        phrasesArray.add(new Word("What is your name?","tinnә oyaase'nә",R.raw.phrase_what_is_your_name));
        phrasesArray.add(new Word("My name is...","oyaaset...",R.raw.phrase_my_name_is));
        phrasesArray.add(new Word("How are you feeling?","michәksәs?",R.raw.phrase_how_are_you_feeling));
        phrasesArray.add(new Word("I’m feeling good.","kuchi achit",R.raw.phrase_im_feeling_good));
        phrasesArray.add(new Word("Are you coming?","әәnәs'aa?",R.raw.phrase_are_you_coming));
        phrasesArray.add(new Word("Yes,I’m coming.","hәә’ әәnәm",R.raw.phrase_yes_im_coming));
        phrasesArray.add(new Word("I’m coming.","әәnәm",R.raw.phrase_im_coming));
        phrasesArray.add(new Word("Let’s go.","yoowutis",R.raw.phrase_lets_go));
        phrasesArray.add(new Word("Come here.","әnni'nem",R.raw.phrase_come_here));

        ListView listView = rootview.findViewById(R.id.listView);
        Custom_Adapter ad = new Custom_Adapter(getContext(), R.layout.custom_layout, phrasesArray,R.color.category_phrases);
        listView.setAdapter(ad);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int result = mAudioManager.requestAudioFocus(audioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mediaPlayer = MediaPlayer.create(getContext(),phrasesArray.get(i).getAudio());
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