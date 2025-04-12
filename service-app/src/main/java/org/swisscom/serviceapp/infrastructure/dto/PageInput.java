package org.swisscom.serviceapp.infrastructure.dto;

import org.springframework.data.domain.Sort;

/**
 * Class to help with pagination of services
 */
public class PageInput{
    private Integer offset;
    private Integer pageSize;
    private final String sortByDefault = "createdDateTime";
    private final Sort.Direction sortDirectionDefault = Sort.Direction.DESC;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortByDefault() {
        return sortByDefault;
    }

    public Sort.Direction getSortDirectionDefault() {
        return sortDirectionDefault;
    }
}
