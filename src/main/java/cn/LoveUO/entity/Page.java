package cn.LoveUO.entity;

public class Page{
    Integer start;        // 开始数据的索引
    Integer count;        // 每一页的数量
    Integer total;        // 总共的数据量
    Integer totalPage;        //总共的页数


    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public String toString() {
        return "Page{" +
                "start=" + start +
                ", count=" + count +
                ", total=" + total +
                ", totalPage=" + totalPage +
                '}';
    }
}
