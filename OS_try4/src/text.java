import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;

public class text {
    private static ArrayList<process> arr;
    private static int time;
    private static process pr;

    public static void main(String[] args) throws IOException {
        arr= new ArrayList<process>();

        arr.add(new process("p1",0,10,50));
        arr.add(new process("p4",10,70,200));
        arr.add(new process("p3",30,10,70));
        arr.add(new process("p2",20,10,30));
        arr.add(new process("p5",50,20,90));
        /**
        System.out.println("****************LLF算法****************");
        System.out.println("请输入本次实验数据数量：");
        int i=getInt();
        input(i);
         */
        //System.out.println();
        time=0;

        String pre="";
        while(!arr.isEmpty()){
            while ((pr=selectprocess(arr))==null){
                time++;
                updateAllLL(arr,time);
            }
            if(pre!=pr.name){
                System.out.println("现在的时间为"+time);
                show(arr);
                System.out.println("根据算法切换进程运行，现运行进程为"+pr.name);
                //System.out.println("\n");
            }
            /**
            if(pre!=p.name){
                System.out.println("现在的时间是"+time);
                System.out.println("运行任务的信息：");
                p.show();
            }*/
            run(pr);
            if(pr!=null){
                pre=pr.name;
            }
        }
    }

    private static void input(int i) throws IOException {
        for(int num=0;num<i;num++){
            System.out.println("请输入进程名字：");
            String name=getString();
            System.out.println("请输入进程到达时间：");
            int arrtime=getInt();
            System.out.println("请输入进程运行时间：");
            int runime=getInt();
            System.out.println("请输入进程截止时间：");
            int deadtime=getInt();
            arr.add(new process(name,arrtime,runime,deadtime));
        }
    }

    private static int getInt() throws IOException {
        String s = getString();
        return Integer.parseInt(s);
    }

    private static String getString() throws IOException {
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader buff = new BufferedReader(in);
        String s = buff.readLine();
        return s;
    }

    public static process selectprocess(ArrayList<process> L){
        int minLL;
        if(L.size()==1)
            return L.get(0);
        if(pr!=null){
            minLL=pr.LL;
        }
        else minLL=Integer.MAX_VALUE;
        for (int i = 0; i < L.size(); i++) {
            if(L.get(i).isuse!=0&&L.get(i).LL==0){
                return L.get(i);
            }
            if(L.get(i).isuse!=0&&minLL>L.get(i).LL){
                minLL=L.get(i).LL;
                pr=L.get(i);
            }
        }
        //show(L);
        return pr;
    }

    public static void run(process p){
        p.setRuntime(p.getRuntime()-1);
        //System.out.println(time+"");
        if(p.getRuntime()==0) {
            endprocess(p);
            System.out.print("线程"+p.name+"完成");
            System.out.println("\n");
            pr=null;
        }
        time++;
        updateAllLL(arr,time);
        //System.out.println("**************");
        //System.out.println("线程"+p.name+p.getRuntime());
        //System.out.println("**************");
    }

    public static  void endprocess(process p){
        arr.remove(arr.indexOf(p));

    }

    public static void updateAllLL(ArrayList<process> p, int time){
        for (process i:p) {
            i.updateLL(time);
            i.updateisuse(time);
        }

    }

    public static void show(ArrayList<process> p){
        String arr;
        System.out.print("线程名字\t");
        System.out.print("线程是否到达\t");
        System.out.print("线程松弛度\t");
        System.out.println("到达时间");
        for (process i:p) {
            arr=(i.isuse==1?"已到达":"未到达");
            System.out.print(i.name+"\t\t");
            System.out.print(arr+"\t"+"\t");
            System.out.print(i.LL+"\t\t\t");
            System.out.println(i.getArrtime()+"\t\t");
        }
    }


}
