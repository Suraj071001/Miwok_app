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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FamilyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FamilyFragment extends Fragment {
    ArrayList<Word> familyArray = new ArrayList<Word>();
    MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FamilyFragment() {
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

    public static FamilyFragment newInstance(String param1, String param2) {
        FamilyFragment fragment = new FamilyFragment();
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
    public void onStop() {
        super.onStop();
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.activity_family,container,false);
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        familyArray.add(new Word("father","әpә",R.drawable.family_father,R.raw.family_father));
        familyArray.add(new Word("mother","әṭa",R.drawable.family_mother,R.raw.family_mother));
        familyArray.add(new Word("son","angsi",R.drawable.family_son,R.raw.family_son));
        familyArray.add(new Word("daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        familyArray.add(new Word("older brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        familyArray.add(new Word("younger brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        familyArray.add(new Word("older sister","teṭe",R.drawable.family_older_sister,R.raw.family_older_sister));
        familyArray.add(new Word("younger sister","kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        familyArray.add(new Word("grandmother","ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        familyArray.add(new Word("grandfather","paapa",R.drawable.family_grandfather,R.raw.family_grandfather));

        ListView listView = rootview.findViewById(R.id.listView);
        Custom_Adapter ad = new Custom_Adapter(getContext(), R.layout.custom_layout, familyArray,R.color.category_family);
        listView.setAdapter(ad);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int result = mAudioManager.requestAudioFocus(audioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mediaPlayer = MediaPlayer.create(getActivity(),familyArray.get(i).getAudio());
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