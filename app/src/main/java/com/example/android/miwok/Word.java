package com.example.android.miwok;

public class Word {
    private String miwokNumber;
    private String translatedNumber;
    private int image_resourse = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;
    private int audio;

    public Word(){

    }

    public Word(String translatedNumber,String miwokNumber,int audio){
        this.miwokNumber = miwokNumber;
        this.translatedNumber = translatedNumber;
        this.audio = audio;
    }
    public Word(String translatedNumber,String miwokNumber,int image_resourse,int audio){
        this.miwokNumber = miwokNumber;
        this.translatedNumber = translatedNumber;
        this.image_resourse = image_resourse;
        this.audio = audio;
    }
    public boolean hasImage() {
        return image_resourse != NO_IMAGE_PROVIDED;
    }

    public String getMiwokNumber() {
        return miwokNumber;
    }

    public String getTranslatedNumber() {
        return translatedNumber;
    }

    public Integer getImage_resourse() {
        return image_resourse;
    }

    public int getAudio() {
        return audio;
    }

    @Override
    public String toString() {
        return "Word{" +
                "miwokNumber='" + miwokNumber + '\'' +
                ", translatedNumber='" + translatedNumber + '\'' +
                ", image_resourse=" + image_resourse +
                ", audio=" + audio +
                '}';
    }
}
