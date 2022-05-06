package com.deekshith.bookshelf.payload.response;

import java.util.List;

public class Response<T> {
    private List<T> data;

    public Response(List<T> data) {
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
