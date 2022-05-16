import javax.swing.plaf.BorderUIResource;

public class process{
    public  String name;
    private int arrtime;
    private int runtime;
    private final int deadtime;
    public int isuse;
    public int LL;



    public process(String name,int arrtime, int runtime, int deadtime) {
        this.name=name;
        this.arrtime = arrtime;
        this.runtime = runtime;
        this.deadtime = deadtime;
        initLL();
        updateisuse(0);
    }

    public void show(){
        System.out.println("线程名字"+this.name);
        System.out.println("到达时间"+this.arrtime);
        System.out.println("执行时间"+this.runtime);
        System.out.println("运行截止时间"+this.deadtime);
        System.out.println("松弛度"+this.LL);
        System.out.println("");
    }
    public int getIsuse() {
        return isuse;
    }

    public int getLL() {
        return LL;
    }

    public int getArrtime() {
        return arrtime;
    }

    public void setArrtime(int arrtime) {
        this.arrtime = arrtime;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public int getDeadtime() {
        return deadtime;
    }

    protected void initLL(){
        LL=this.deadtime-this.runtime;
        return;
    }

    public void updateLL(int time){
        this.LL=this.deadtime-this.runtime-time;
        return;
    }

    protected  void updateisuse(int time){
        if(time>=arrtime) {
            isuse=1;
        }
        else isuse=0;
    }

}
