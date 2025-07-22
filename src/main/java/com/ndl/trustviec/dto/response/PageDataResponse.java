package com.ndl.trustviec.dto.response;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PageDataResponse<E> {
    private long totalElements;
    private int page;
    private int size;
    private List<E> data;

    public static <E> PageDataResponse<E> of(Page<E> page) {
        PageDataResponse<E> rs = new PageDataResponse<E>();
        rs.setTotalElements(page.getTotalElements());
        rs.setSize(page.getSize());
        rs.setPage(page.getNumber());
        rs.setData(page.getContent());
        return rs;
    }

    public static <E> PageDataResponse<E> of(String totalElements, List<E> data) {
        PageDataResponse<E> rs = new PageDataResponse<>();
        rs.setTotalElements(Long.parseLong(totalElements));
        rs.setData(data);
        return rs;
    }

}
