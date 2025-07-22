package com.ndl.trustviec.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface PageableUtils {
    int MAX_ITEM_PER_PAGE = 100;

    static Pageable of(int page, int size) {
        return PageRequest.of(page, Math.min(size, MAX_ITEM_PER_PAGE));
    }

    static Pageable of(int page, int size, Map<String, String> sorts, boolean isQueryNative) {
        if (sorts == null || sorts.isEmpty()) {
            List<Sort.Order> rs = new ArrayList<>();
            if (isQueryNative) {
                rs.add(new Sort.Order(Sort.Direction.DESC, "created_time"));
            } else {
                rs.add(new Sort.Order(Sort.Direction.DESC, "createdTime"));
            }
            return PageRequest.of(page, Math.min(size, MAX_ITEM_PER_PAGE), Sort.by(rs));
        }

        List<Sort.Order> rs = new ArrayList<>();
        sorts.forEach((k, v) -> rs.add(new Sort.Order(Sort.Direction.valueOf(v.toUpperCase()), k)));

        if (rs.isEmpty()) {
            if (isQueryNative) {
                rs.add(new Sort.Order(Sort.Direction.DESC, "created_time"));
            } else {
                rs.add(new Sort.Order(Sort.Direction.DESC, "createdTime"));
            }
        }

        return PageRequest.of(page, Math.min(size, MAX_ITEM_PER_PAGE), Sort.by(rs));
    }
}
