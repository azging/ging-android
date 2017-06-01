package com.azging.ging.bean;

import com.azging.ging.base.BaseBean;

/**
 * Created by GG on 2017/5/31.
 */

public class PhotoUri extends BaseBean {
    private int type;//1 file path;2 uri
    private String uriStr;
    private String webUriStr;

    public PhotoUri(int type, String tmpStr) {
        this.setType(type);
        this.setUriStr(tmpStr);
    }

    public PhotoUri() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhotoUri photoUri = (PhotoUri) o;

        if (uriStr.equals(photoUri.uriStr)) return true;
        if (webUriStr!=null&&photoUri.webUriStr!=null)
            if (webUriStr.equals(photoUri.webUriStr)) return true;
        return false;
    }

    @Override
    public int hashCode() {
        int result = uriStr != null ? uriStr.hashCode() : 0;
        result = 31 * result + (webUriStr != null ? webUriStr.hashCode() : 0);
        return result;
    }

    public String getUriStr() {
        return uriStr;
    }

    public void setUriStr(String uriStr) {
        this.uriStr = uriStr;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getWebUriStr() {
        return webUriStr;
    }

    public void setWebUriStr(String webUriStr) {
        this.webUriStr = webUriStr;
    }
}
