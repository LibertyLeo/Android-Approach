package com.libertyleo.beatbox;

/**
 * Created by Leo_Lei on 9/4/17.
 */

public class Sound {
    private String mAssertPath;
    private String mName;
    private Integer mSoundId;

    public Sound(String assertPath) {
        mAssertPath = assertPath;
        String[] components = assertPath.split("/");
        String filename = components[components.length - 1];
        mName = filename.replace(".wav", "");
    }

    public String getAssertPath() {
        return mAssertPath;
    }

    public String getName() {
        return mName;
    }

    public Integer getSoundId() {
        return mSoundId;
    }

    public void setSoundId(Integer soundId) {
        mSoundId = soundId;
    }
}
