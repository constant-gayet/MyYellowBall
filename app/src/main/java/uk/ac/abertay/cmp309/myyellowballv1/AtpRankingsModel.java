package uk.ac.abertay.cmp309.myyellowballv1;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;

import java.io.Serializable;

public class AtpRankingsModel implements Parcelable {
    private int rank;
    private int points;
    private String name;
    private String country_code;

    public AtpRankingsModel(int rank, int points, String name, String country_code) {
        this.rank = rank;
        this.points = points;
        this.name = name;
        this.country_code = country_code;
    }

    protected AtpRankingsModel(Parcel in) {
        rank = in.readInt();
        points = in.readInt();
        name = in.readString();
        country_code = in.readString();
    }

    public static final Creator<AtpRankingsModel> CREATOR = new Creator<AtpRankingsModel>() {
        @Override
        public AtpRankingsModel createFromParcel(Parcel in) {
            return new AtpRankingsModel(in);
        }

        @Override
        public AtpRankingsModel[] newArray(int size) {
            return new AtpRankingsModel[size];
        }
    };

    @Override
    public String toString() {
        return "AtpRankingsModel{" +
                "rank=" + rank +
                ", points=" + points +
                ", name='" + name + '\'' +
                ", country_code='" + country_code + '\'' +
                '}';
    }

    public int getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(rank);
        parcel.writeInt(points);
        parcel.writeString(name);
        parcel.writeString(country_code);
    }
}
