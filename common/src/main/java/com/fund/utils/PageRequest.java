package com.fund.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * 分页查询条件
 * <p>
 * Create at 2023/03/08 00:16
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/08
 * @since 1.0.0
 */
public class PageRequest implements Serializable {
    private long pageNumber;
    private int pageSize;

    protected PageRequest() {
        this.pageNumber = 1L;
        this.pageSize = 10;
    }

    public PageRequest(long pageNumber, int pageSize) {
        this.pageNumber = 1L;
        this.pageSize = 10;
        if (pageNumber < 1L) {
            throw new IllegalArgumentException("Page index must not be less than zero!");
        } else if (pageSize < 1) {
            throw new IllegalArgumentException("Page size must not be less than one!");
        } else {
            this.pageNumber = pageNumber;
            this.pageSize = pageSize;
        }
    }

    public PageRequest next() {
        return new PageRequest(this.pageNumber + 1L, this.pageSize);
    }

    public PageRequest previous() {
        return this.pageNumber == 0L ? this : new PageRequest(this.pageNumber - 1L, this.pageSize);
    }

    public PageRequest first() {
        return this.pageNumber == 1L ? this : new PageRequest(1L, this.pageSize);
    }

    @JsonIgnore
    public long getOffset() {
        return this.pageNumber * (long)this.pageSize;
    }

    public PageRequest previousOrFirst() {
        return this.hasPrevious() ? this.previous() : this.first();
    }

    public boolean hasPrevious() {
        return this.pageNumber > 1L;
    }

    public long getPageNumber() {
        return this.pageNumber;
    }

    public int getPageSize() {
        return this.pageSize;
    }


    public PageRequest setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public PageRequest setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    @Override
    public String toString() {
        return "PageRequest{" +
                "pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                '}';
    }
}

