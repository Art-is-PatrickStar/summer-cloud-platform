package com.wsw.cloud.product.domain.repository.pagination;

import java.util.Collections;
import java.util.List;

public class PageResult<T> {
    private final long total;
    private final long pages;
    private final int pageNum;
    private final int pageSize;
    private final List<T> records;

    public PageResult(long total, long pages, int pageNum, int pageSize, List<T> records) {
        this.total = total;
        this.pages = pages;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.records = records == null ? Collections.emptyList() : Collections.unmodifiableList(records);
    }

    public long getTotal() {
        return total;
    }

    public long getPages() {
        return pages;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public List<T> getRecords() {
        return records;
    }
}


