package com.wsw.cloud.product.interfaces.response;

import lombok.Getter;

import java.util.List;

@Getter
public class PageResponse<T> extends ApiResponse<List<T>> {
    private final long total;
    private final long pages;
    private final int pageNum;
    private final int pageSize;

    private PageResponse(List<T> data, long total, long pages, int pageNum, int pageSize) {
        super(0, "success", data);
        this.total = total;
        this.pages = pages;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public static <T> PageResponse<T> of(List<T> data, long total, long pages, int pageNum, int pageSize) {
        return new PageResponse<>(data, total, pages, pageNum, pageSize);
    }
}


