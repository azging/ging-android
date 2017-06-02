package com.azging.ging.bean;

import com.azging.ging.base.BaseBean;
import com.google.gson.annotations.SerializedName;

/**
 * Created by GG on 2017/6/2.
 */

public class QiniuTokenBean extends BaseBean {

    /**
     * UpToken : UHvoPbROWN__NRnxNKGs2VYh-Ha6ekBQbYhGnHOo:BqQQuevMXTSHVZfHB_As6YpMPvo=:eyJzY29wZSI6ImxpbmtjaXR5IiwiZGVhZGxpbmUiOjE0OTYzOTMyNzMsInVwSG9zdHMiOlsiaHR0cDpcL1wvdXAucWluaXUuY29tIiwiaHR0cDpcL1wvdXBsb2FkLnFpbml1LmNvbSIsIi1IIHVwLnFpbml1LmNvbSBodHRwOlwvXC8xODMuMTM2LjEzOS4xNiJdfQ==
     * PhotoKey : photo_fd98055def97045d3a34e3c37b218ad0
     */

    @SerializedName("UpToken") private String UpToken;
    @SerializedName("PhotoKey") private String PhotoKey;

    public String getUpToken() {
        return UpToken;
    }

    public void setUpToken(String UpToken) {
        this.UpToken = UpToken;
    }

    public String getPhotoKey() {
        return PhotoKey;
    }

    public void setPhotoKey(String PhotoKey) {
        this.PhotoKey = PhotoKey;
    }
}
