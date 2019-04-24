package org.lkpnotice.turnning.mynetty.example;

/**
 * Created by liujinpeng on 2019/4/14.
 *
 * 二维数组 int[][] temp
 * 每一个数字一个楼高度
 *
 *temp[i][j] = 1
 *
 *
 *  7 2 3 4
 *  2 3 4 5
 *  2 3 4 6
 *  3 5 6 9
 *
 *  一个点的坐标，每一行的 benchmark ，每一列的benchmark
 *  每一个点属于 的行列取最小值 和 当前值的 差是这一个点不改变目标函数的一个 inc
 *  将所有的点遍历一边后每一个点计算的值的和
 *
 */
public class Test {


    /**
     * 输入数据
     * @return
     */
    public static int[][] dataInput(){
        return new int[][]{{7,2,2,3},{2,3,3,5},{3,4,4,6},{4,5,6,9}};
        /*return new int[][]{{1},{1}};*/
    }


    /**
     * 入口
     * @param input
     * @return
     */
    public long computeTheIncAmmount(int[][] input){
        if (null == input)
            throw  new RuntimeException("输入数组参数错误");

        int xSize = input.length;
        int ySize = input[0].length;

        /*记录行列的最大值*/
        int[] xMaxVal = new int[xSize];
        int[] yMaxVal = new int[ySize];


        //初始化
        for (int i=0;i<xSize;i++){
            xMaxVal[i] = Integer.MIN_VALUE;
        }

        for (int i=0;i<ySize;i++){
            yMaxVal[i] = Integer.MIN_VALUE;
        }


        //遍历一边 O(N) 计算bound
        for (int x=0;x<xSize;x++){
            for (int y=0;y<ySize;y++){
                //取得一个元素
                int target = input[x][y];
                xMaxVal[x] = xMaxVal[x]<target? target:xMaxVal[x];
                yMaxVal[y] = yMaxVal[y]<target? target:yMaxVal[y];
            }

        }

            long incAmmount = 0;
            for (int x=0;x<xSize;x++){
                for (int y=0;y<ySize;y++){
                    //取得一个元素
                    int target = input[x][y];
                    incAmmount += Math.min(xMaxVal[x],yMaxVal[y]) - target;
                }

            }


            return incAmmount;
        }




        public static void main(String[] args){
            Test t = new Test();
            int[][] input = Test.dataInput();
            long result = t.computeTheIncAmmount(input);
            System.out.println(String.format("result is %s",result));
        }



    }
