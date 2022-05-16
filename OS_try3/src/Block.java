public class Block {
    public int pageNum;     //页号
    public int accessed;    //页的访问字段

    public Block(int pageNum, int accessed) {
        super();
        this.pageNum = pageNum;
        this.accessed = accessed;
    }

    @Override
    public String toString() {
        return "物理块信息 [页号=" + pageNum + ", 访问位=" + accessed + "]";
    }
}
