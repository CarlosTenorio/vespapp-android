package com.habitissimo.vespapp.model;

import android.content.Context;

import com.habitissimo.vespapp.R;
import com.habitissimo.vespapp.questions.Sighting;

public class    SightingUi {
    public String imageUrl;
    public int statusCode;

    public SightingUi(String imageUrl, int statusCode) {
        this.imageUrl = imageUrl;
        this.statusCode = statusCode;
    }

    public int getStatusColor(Context context) {
        SightingData sightingData = SightingData.fromCode(statusCode);
        return sightingData.getStatusColor(context);
    }

    public String getTitleFromStatusCode(Context context) {
        SightingData sightingData = SightingData.fromCode(statusCode);
        return sightingData.getFormattedTitle(context);
    }

    public enum SightingData {
        Pending(Sighting.STATUS_PENDING, R.string.status_pending, R.color.statusPending),
        Processed(Sighting.STATUS_PROCESSED, R.string.status_processed, R.color.statusValidated),
        NotSent(Sighting.STATUS_UNSENT, R.string.status_unsent, R.color.statusNotSent),
        Processing(Sighting.STATUS_PROCESSING, R.string.status_processing, R.color.statusProcessing);

        private final int textRes;
        private final int colorRes;
        private int statusCode;

        SightingData(int statusCode, int textRes, int colorRes) {
            this.statusCode = statusCode;
            this.textRes = textRes;
            this.colorRes = colorRes;
        }

        public static SightingData fromCode(int statusCode) {
            for (SightingData data : values()) {
                if (data.getStatusCode() == statusCode)
                    return data;
            }

            throw new RuntimeException("No data with code " + statusCode);
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getFormattedTitle(Context context) {
            return context.getString(textRes);
        }

        public int getStatusColor(Context context) {
            return context.getResources().getColor(colorRes);
        }
    }
}
