package org.dlsu.arrowsmith.classes.dtos.ASSYSTX2;

import java.util.Iterator;

public class PageOfferingDTO
{
    private boolean hasNext;
    private boolean hasPrev;
    private int currPageNum;
    private int totalPages;
    private int pageSize;
    private Iterator currPartialOfferings;

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isHasPrev() {
        return hasPrev;
    }

    public void setHasPrev(boolean hasPrev) {
        this.hasPrev = hasPrev;
    }

    public int getCurrPageNum() {
        return currPageNum;
    }

    public void setCurrPageNum(int currPageNum) {
        this.currPageNum = currPageNum;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Iterator getCurrPartialOfferings() {
        return currPartialOfferings;
    }

    public void setCurrPartialOfferings(Iterator currPartialOfferings) {
        this.currPartialOfferings = currPartialOfferings;
    }
}
