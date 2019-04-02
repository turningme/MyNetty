package org.lkpnotice.turnning.mynetty.example.other;

/**
 * Created by liujinpeng on 2019/3/31.
 */
public class SubArrayMaxSum {




    private int[] initData(){
        return new int[]{1,4,-2,-3,-1};
    }



    private int maxSum(int[] input){
        if (null == input || 0==input.length){
            return 0;
        }

        //

        int sum=input[0];
        int max = sum;

        for (int i=1;i<input.length;i++){
            if (sum<0){//前i个和小于0 ，子段起点改变
                sum = input[i];
            }else{//前i个和不小于0，子段起点不变，终点+1
                sum += input[i];
            }

            max =  max < sum ? sum:max;
        }



        return max;
    }



    public static void main(String[] args){
        SubArrayMaxSum subArrayMaxSum = new SubArrayMaxSum();
        int result = subArrayMaxSum.maxSum(subArrayMaxSum.initData());
        System.out.println(String.format("result is %s",result));
    }
}
