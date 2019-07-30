package com.netease.nim.uikit.common.util.media;

import android.os.Handler;
import android.util.Log;

import com.netease.nim.uikit.common.media.model.GLImage;
import com.netease.nim.uikit.common.util.sys.TimeUtil;
import com.netease.nimlib.sdk.media.record.AudioRecorder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * MediaUtil
 */
public class MediaUtil {


    public static final String TAG = "MediaUtil";
    public  static AudioRecorder mAudioRecorder;
    public static FractionListener mFractionListener;

    /**
     * 更新话筒状态
     */
    public static int BASE = 1;
    /**
     * 间隔取样时间
     */
    private static int SPACE = 100;
    private static final Handler mHandler = new Handler();
    private static Runnable mUpdateMicStatusTimer = new Runnable() {
        @Override
        public void run() {
            updateMicStatus(mAudioRecorder);
        }
    };


    public static void cancleRecordHandler(){
        if(mUpdateMicStatusTimer != null){
            mHandler.removeCallbacks(mUpdateMicStatusTimer);
        }
    }


    public static Map<String, List<GLImage>> divideMedias(List<GLImage> images) {
        Map<String, List<GLImage>> imageItemMap = new LinkedHashMap<>();
        for (int i = 0; i < images.size(); i++) {
            GLImage GLImage = images.get(i);
            String date = TimeUtil.getDateString(GLImage.getAddTime());
            if (imageItemMap.get(date) != null) {
                List<GLImage> GLImageList = imageItemMap.get(date);
                GLImageList.add(GLImage);
                imageItemMap.put(date, GLImageList);
            } else {
                List<GLImage> GLImageList = new ArrayList<>(1);
                GLImageList.add(GLImage);
                imageItemMap.put(date, GLImageList);
            }
        }

        return imageItemMap;
    }


    public static void updateMicStatus(AudioRecorder audioRecorder) {
        if (audioRecorder != null) {
            mAudioRecorder = audioRecorder;
            double ratio = (double) audioRecorder.getCurrentRecordMaxAmplitude() / BASE;
            // 分贝
            double db = 0;
            if (ratio > 1) {
                db = 20 * Math.log10(ratio);
            }
            Log.d(TAG, "分贝值----->：" + db);
            if (mFractionListener != null) {
                mFractionListener.updateFraction((db - 60) / (90 - 60));
            }
            mHandler.postDelayed(mUpdateMicStatusTimer, SPACE);
        }
    }

    public static void setFractionListener(FractionListener fractionListener) {
        mFractionListener = fractionListener;
    }

    /**
     * 分贝百分比监听，默认初始值为60,最大值90
     */
    public interface FractionListener {
        /**
         * 传入当前增长分贝的百分比进度
         *
         * @param curFraction
         */
        void updateFraction(double curFraction);
    }
}