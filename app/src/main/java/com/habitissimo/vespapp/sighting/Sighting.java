package com.habitissimo.vespapp.sighting;


import com.google.gson.annotations.SerializedName;

/**
 * Created by archi on 11/03/16.
 */
public class Sighting {

    //private int id;
    private float lat;
    private float lng;
    @SerializedName("public")
    private boolean _public;
    /*

    public static final int STATUS_PENDING = 0;
    public static final int STATUS_PROCESSING = 1;
    public static final int STATUS_PROCESSED = 2;
    public static final int STATUS_UNSENT = -1;

    public static final int TYPE_WASP = 0;
    public static final int TYPE_NEST = 1;

    public String id;
    public Location location;
    public float lat;
    public float lng;
    public int status = STATUS_UNSENT;
    public String free_text;
    public int type;
    public List<Question> available_questions = null;
    public List<String> answers = null;
    @SerializedName("public")
    public boolean _public;
    public List<Picture> pictures = null;
    public String contact = "Anónimo";

    public String source = "app";
*/
    public Sighting (float lat, float lng, boolean _public){
        //this.id=id;
        this.lat=lat;
        this.lng=lng;
        this._public=_public;
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }

    public boolean getPublic() {
        return _public;
    }

/*    public int getId() {
        return id;
    }*/

    /*
    public void addAnswer(Answer answer) {
        if (answers == null) {
            answers = new ArrayList<>();
        }
        this.answers.add(answer.id);
    }

    public void deleteAnswer(Answer answer) {
        if (answers == null) {
            answers = new ArrayList<>();
        }

        this.answers.remove(answer.id);
    }*/
}
