package com.gfk.common.model;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List;

/**
 * 通用分页响应对象
 * 通过静态工厂方法构建
 *
 * @author wzl
 * @version 1.0 2023/3/1
 */
@Data
public class PageResponse<T> {
    private long pageSize;

    private long pageNo;

    private long total;

    private long totalPages;

    private List<T> list;

    public PageResponse() {
    }

    public static <T> PageResponse<T> fullArgs(int pageSize, int pageNo, int total, int totalPages, List<T> list) {
        PageResponse<T> response = new PageResponse<>();
        response.setPageSize(pageSize);
        response.setPageNo(pageNo);
        response.setTotal(total);
        response.setTotalPages(totalPages);
        response.setList(list);
        return response;
    }

    public static <T> PageResponse<T> simpleArgs(int pageSize, int pageNo, List<T> list) {
        PageResponse<T> response = new PageResponse<>();
        response.setPageSize(pageSize);
        response.setPageNo(pageNo);
        response.setTotal(0);
        response.setTotalPages(0);
        response.setList(list);
        return response;
    }

    public static <T> PageResponse<T> ofPage(Page<T> page) {
        PageResponse<T> response = new PageResponse<>();
        response.setPageSize(page.getSize());
        response.setPageNo(page.getCurrent());
        response.setTotal(page.getTotal());
        response.setTotalPages(page.getPages());
        response.setList(page.getRecords());
        return response;
    }

    public static <T> PageResponse<T> ofCustomizedPage(Page page, List<T> list) {
        PageResponse<T> response = new PageResponse<>();
        response.setPageSize(page.getSize());
        response.setPageNo(page.getCurrent());
        response.setTotal(page.getTotal());
        response.setTotalPages(page.getPages());
        response.setList(list);
        return response;
    }


}
