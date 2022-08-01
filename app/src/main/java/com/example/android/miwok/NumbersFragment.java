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


public class NumbersFragment extends Fragment {
    ArrayList<Word> numbersArray = new ArrayList<Word>();
    MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NumbersFragment() {
        // Required empty public constructor
    }
    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if(i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);

            }else if (i == AudioManager.AUDIOFOCUS_LOSS){
                if (mediaPlayer!=null){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }

            }else if (i == AudioManager.AUDIOFOCUS_GAIN){
                mediaPlayer.start();

            }

        }
    };

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NumbersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NumbersFragment newInstance(String param1, String param2) {
        NumbersFragment fragment = new NumbersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mediaPlayer!=null){
            mediaPlayer.release();
        }
        mediaPlayer = null;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.activity_numbers,container,false);
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        numbersArray.add(new Word("one","lutti",R.drawable.number_one,R.raw.number_one));
        numbersArray.add(new Word("Two","otiiko",R.drawable.number_two,R.raw.number_two));
        numbersArray.add(new Word("Three","tolookosu",R.drawable.number_three,R.raw.number_three));
        numbersArray.add(new Word("Four","oyyisa",R.drawable.number_four,R.raw.number_four));
        numbersArray.add(new Word("Five","massokka",R.drawable.number_five,R.raw.number_five));
        numbersArray.add(new Word("Six","temmokka",R.drawable.number_six,R.raw.number_six));
        numbersArray.add(new Word("Seven","kenekaku",R.drawable.number_seven,R.raw.number_seven));
        numbersArray.add(new Word("Eight","kawinta",R.drawable.number_eight,R.raw.number_eight));
        numbersArray.add(new Word("nine","wo'e",R.drawable.number_nine,R.raw.number_nine));
        numbersArray.add(new Word("Ten","na'accha",R.drawable.number_ten,R.raw.number_ten));

        ListView listView = rootview.findViewById(R.id.listView);
        Custom_Adapter ad = new Custom_Adapter(getContext(), R.layout.custom_layout,numbersArray,R.color.category_numbers);
        listView.setAdapter(ad);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int result = mAudioManager.requestAudioFocus(audioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mediaPlayer = MediaPlayer.create(getActivity(),numbersArray.get(i).getAudio());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            if(mediaPlayer != null) {
                                mediaPlayer.release();
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