import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class pageAPP {
        public final static int block_num=4;
        static int pc;
        static int loss_num;
        static int[] random_num=new int[320];
        static Block[] blocks;
        static int block_index;

    public static void main(String[] args) throws IOException {
        init();
        System.out.println("请输入执行指令号（0~320）：");
        while(true){
            pc = getInt();
            if(pc>320||pc<0){
                System.out.println("无效输入，请重新输入");
                System.out.println("请输入执行指令号（0~320）：");
                continue;
            }
            else break;
        }
        create_randomnum();

        while(true){
            System.out.println("输入指令：");
            System.out.println("1.算法模拟：");
            System.out.println("2.退出");
            int choice = getInt();
            if(choice==1){
                while(true){
                    System.out.println("对应的调用队列：");
                    page_show();
                    init();
                    System.out.println("选择置换算法：");
                    System.out.println("1.最佳置换算法（OPT）：");
                    System.out.println("2.最近最久未使用（LRU）：");
                    System.out.println("3.先进先出算法（FIFO）：");
                    System.out.println("4.退出");
                    int choice2 = getInt();
                    switch (choice2) {
                        case 1:
                            System.out.println("===============");
                            System.out.println("最佳置换算法（FIFO）");
                            System.out.println("===============");
                            OPT();
                            break;
                        case 2:
                            System.out.println("=================");
                            System.out.println("最近最久未使用（LRU）");
                            System.out.println("=================");
                            LRU();
                            break;
                        case 3:
                            System.out.println("=================");
                            System.out.println("先进先出算法（FIFO）");
                            System.out.println("=================");
                            FIFO();
                            break;
                        case 4:
                            System.out.println("程序结束");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("无效输入，请重新输入");
                            continue;
                    }
                }
            }
            else if(choice==2){
                System.out.println("程序结束");
                System.exit(1);
                break;
            }
            else{
                System.out.println("无效输入，请重新输入");
                continue;
            }
        }



    }

    //
    //具体算法实现
    private static void LRU() {
        int page,index=0;
        for (int i = 0; i < 320; i++) {
            page=(int)(random_num[i]/10);
            index=findExit(page);
            if(index==-1){//缺页中断
                int space = findSpace();
                if(space==-1){
                    update();
                    int num = findPage();
                    blocks[num].pageNum = page;
                    blocks[num].accessed= 0;
                    LRUshow();
                    loss_num++;
                }
                else {
                    blocks[space].pageNum=page;
                    LRUshow();
                    update();
                    loss_num++;
                }
            }else{
                update();
                blocks[index].accessed=0;
            }
        }
        System.out.println("缺页次数：" + loss_num);
        int answer = (loss_num * 100)/320;
        System.out.println("缺页率：" + answer + "%");
    }

    private static void FIFO() {
        int page,index=0;
        for (int i = 0; i < 320; i++) {
            page=(int)(random_num[i]/10);
            if(findExit(page)==-1){
                int space = findSpace();
                if(space==-1){
                    index= index % block_num;
                    blocks[index].pageNum=page;
                    index++;
                    show();
                    loss_num++;
                }
                else {
                    blocks[space].pageNum=page;
                    show();
                    loss_num++;
                }
            }
        }
        System.out.println("缺页次数：" + loss_num);
        int answer = (loss_num * 100)/320;
        System.out.println("缺页率：" + answer + "%");
    }

    private static void OPT() {
        int page;
        for (int i = 0; i < 320; i++) {
            page=(int)(random_num[i]/10);
            if(findExit(page)==-1){
                int space = findSpace();
                if(space==-1){
                    for (int j = 0; j < block_num; j++) {
                        int index=blocks[j].pageNum;
                        for (int j1 = i; j1 < 300; j1++) {
                            if(index == (int)random_num[j1]/10 ){
                                blocks[j].accessed = -j1;
                                break;
                            }else if(j1==300) {
                                blocks[j].accessed = Integer.MIN_VALUE;
                            }
                        }
                    }
                    OPTshow();
                    int res =findPage();
                    blocks[res].pageNum=page;
                    blocks[res].accessed = 0;
                    OPTshow();
                    loss_num++;

                }
                else {
                    blocks[space].pageNum=page;
                    OPTshow();
                    loss_num++;
                }
            }
        }
        System.out.println("缺页次数：" + loss_num);
        int answer = (loss_num * 100)/320;
        System.out.println("缺页率：" + answer + "%");
    }

    public static int  findExit(int page){
        for (int i = 0; i < block_num; i++) {
            if(page==blocks[i].pageNum)
                return i;
        }
        return -1;
    }

    public static int findSpace(){
        for (int i = 0; i < block_num; i++) {
            if(blocks[i].pageNum == -1)
                return i;
        }
        return -1;
    }

    public static int findPage(){
        int index=0;
        for (int i = 0; i < block_num; i++) {
            if(blocks[i].accessed < blocks[index].accessed)
                index = i;
        }
        return index;
    }

    public static void update(){
        int index=0;
        for (int i = 0; i < block_num; i++) {
            blocks[i].accessed--;
        }

    }

    private static void page_show() {
        for (int i = 0; i < 320; i++) {
            int a = random_num[i]/10;    //a代表页号，将temp中的指令转化为调用的页
            if (a<10) {
                System.out.print("0"+a+"  ");
            }else {
                System.out.print(a + "  ");
            }
            if ((i+1)%10 == 0) {
                System.out.println("");
            }
        }    
    }

    private static void create_randomnum(){
        int flag=0;
        System.out.println("随机数生成：");
        for(int i=0;i<320;i++){
            random_num[i]=pc;
            int a = (int)(java.lang.Math.random()*320);
            if(flag%2==0){
                pc = (++pc)%320;
            }else if (flag==1){
                pc = a%(pc-1);
            }else if (flag==3){
                pc = pc+1+(a%(320-pc-1));
            }
            flag=(++flag)%4;
            if ((i+1)%10 == 0) System.out.println("");


            System.out.print(i+":"+ random_num[i] + " ");
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

    private static void init() {
        blocks=new Block[block_num];
        pc=loss_num=0;
        for(int i=0;i<block_num;i++){
            blocks[i]=new Block(-1,0);
        }
    }

    private static void OPTshow() {
        System.out.println("");
        for (int i = 0; i < block_num; i++) {
            System.out.print("内存块:"+i+"  ");
            if (blocks[i].pageNum!=-1) {
                if (blocks[i].pageNum<10) {
                    System.out.println("0" + blocks[i].pageNum+ " *最近出现的位置"+Math.abs(blocks[i].accessed));

                }else {
                    System.out.println(blocks[i].pageNum + " *最近出现的位置"+Math.abs(blocks[i].accessed));

                }
            }else{
                System.out.println("空");
            }
        }
    }

    private static void LRUshow() {
        System.out.println("");
        for (int i = 0; i < block_num; i++) {
            System.out.print("内存块:"+i+"  ");
            if (blocks[i].pageNum!=-1) {
                if (blocks[i].pageNum<10) {
                    System.out.println("0" + blocks[i].pageNum+ " *最久未使用"+Math.abs(blocks[i].accessed));

                }else {
                    System.out.println(blocks[i].pageNum + " *最久未使用"+Math.abs(blocks[i].accessed));

                }
            }else{
                System.out.println("空");
            }
        }
    }
    private static void show() {
        System.out.println("");
        for (int i = 0; i < block_num; i++) {
            System.out.print("内存块:"+i+"  ");
            if (blocks[i].pageNum!=-1) {
                if (blocks[i].pageNum<10) {
                    System.out.println("0" + blocks[i].pageNum);

                }else {
                    System.out.println(blocks[i].pageNum );

                }
            }else{
                System.out.println("空");
            }
        }
    }
}
