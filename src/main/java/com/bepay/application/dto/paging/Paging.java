package com.bepay.application.dto.paging;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Paging {
    private int totalPages; // tổng số trang
    private int indexPage; // trang hiện tại
    private int recordPerPage = 10; // số bảng ghi tối đa trên 1 trang
    private Integer page;
    private Integer size;

    public Paging(int recordPerPage) {
        this.recordPerPage = recordPerPage;

        // while remove
        this.size = recordPerPage;
    }

    public Paging(int totalPages, int indexPage, int recordPerPage) {
        this.totalPages = totalPages;
        this.indexPage = indexPage;
        this.recordPerPage = recordPerPage;

        // while remove
        this.page = indexPage;
        this.size = recordPerPage;
    }

    public Paging(int totalPages, Integer page, Integer size) {
        this.totalPages = totalPages;
        this.page = page;
        this.size = size;

        // while remove
        this.indexPage = page;
        this.recordPerPage = size;
    }

    public Paging(Integer page, Integer size) {
        this.page = page;
        this.size = size;

        // while remove
        this.indexPage = page;
        this.recordPerPage = size;
    }

    public Paging(Integer size) {
        this.size = size;

        // while remove
        this.recordPerPage = size;
    }
}
