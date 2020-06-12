package com.gac.oj;

/**
 * 	排序算法	平均时间复杂度
 *	冒泡排序	O(n2)
 *	选择排序	O(n2)
 *	插入排序	O(n2)
 *	希尔排序	O(n1.5)
 *	快速排序	O(N*logN)
 *	归并排序	O(N*logN)
 *	堆排序     	O(N*logN)
 *	基数排序	O(d(n+r))
 *
 * @author gz04766
 *
 */

public class SortAlgorithmTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arrag = new int[] {42,20,17,13,28,14,23,15};
		int[] temp = new int[arrag.length];
		MinHeapSort(arrag,arrag.length-1);
		for(int i : arrag) {
			System.err.print(i + " ");
		}
	}

	//冒泡法：两个数比较大小，较大的数下沉，较小的数冒起来。
	public static void bubbleSort(int[] arrag) {
		boolean flag;
		int temp;
		for(int i = 0;i < arrag.length-1;i++) {
			flag = false;
			for (int j = arrag.length-1; j > i; j--) {
				if (arrag[j] < arrag[j-1]) {
					flag = true;
					temp = arrag[j];
					arrag[j] = arrag[j-1];
					arrag[j-1] = temp;
				}
			}
			if(!flag) {
				break;
			}
		}
	}
	
	/**
	    *    选择排序：记录最小的下标，然后与第一个更换
	 *    
	   *     在长度为N的无序数组中，第一次遍历n-1个数，找到最小的数值与第一个元素交换；
	   *      第二次遍历n-2个数，找到最小的数值与第二个元素交换；
     *   ......
            *       第n-1次遍历，找到最小的数值与第n-1个元素交换，排序完成。
	 * @param arr
	 */
	
	public static void selectionSort(int[] arr) {
		int temp;
		int minIndex;
		for (int i = 0 ; i < arr.length-1;i++) {
			minIndex = i;
			for (int j = i + 1;j < arr.length; j++) {
				if (arr[j] < arr[minIndex]) {
					minIndex = j;
				}
			}
			if(minIndex != i) {
				temp = arr[minIndex];
				arr[minIndex] = arr[i];
				arr[i] = temp;
			}
		}
	}

	/**
	   * 插入排序 
	 * 
	   * 在要排序的一组数中，假定前n-1个数已经排好序，现在将第n个数插到前面的有序数列中，
	   * 使得这n个数也是排好顺序的。如此反复循环，直到全部排好顺序。
	 * @param arr
	 */
	public static void insertSort(int[] arr) {
		int temp;
		for(int i = 0; i < arr.length-1; i++) {
			for (int j = i + 1;j > 0;j--) {
				//如果最后一个数比前一个小，就置换，如果最后一个比前一个大，直接不用循环更多的
				if(arr[j] < arr[j - 1]) {
					temp = arr[j];
					arr[j] = arr[j-1];
					arr[j-1] = temp;
				}else {
					break;
				}
			}
		}
	}
	
	/**
	 * 希尔排序
	 * 
	 * 在要排序的一组数中，根据某一增量分为若干子序列，并对子序列分别进行插入排序。
	 * 然后逐渐将增量减小,并重复上述过程。直至增量为1,此时数据序列基本有序,最后进行插入排序。
	 */
	public static void shellSort(int[] arr) {
	  int temp = 0;
	  int length = arr.length;
	  int incre = arr.length;
	  
	  while(true){
	      incre = incre/2;
	      
	      for(int k = 0;k<incre;k++){    //根据增量分为若干子序列
	          for(int i=k+incre;i<length;i+=incre){
	              for(int j=i;j>k;j-=incre){
	                  if(arr[j]<arr[j-incre]){
	                      temp = arr[j-incre];
	                      arr[j-incre] = arr[j];
	                      arr[j] = temp;
	                  }else{
	                	  //如果前面小于后面就比用比较，直接跳过
	                      break;
	                  }
	              }
	          }
	      }
	      
	      if(incre == 1){
	          break;
	      }
	  }
	}
	
	/**
	 * 先从数列中取出一个数作为key值；
	 * 将比这个数小的数全部放在它的左边，大于或等于它的数全部放在它的右边；
	 * 对左右两个小数列重复第二步，直至各区间只有1个数。
	 * 
	 * @param arr
	 * @param l
	 * @param r
	 */
	public static void quickSort(int[] arr,int l,int r) {
		if(l>=r) return;
	         
        int i = l; 
        int j = r; 
        //选择第一个数为key
        int key = arr[l];
        
        while(i<j){
            
        	//从右向左找第一个小于key的值
            while(i<j && arr[j]>=key)
                j--;
            if(i<j){
            	arr[i] = arr[j];
                i++;
            }
            
            //从左向右找第一个大于key的值
            while(i<j && arr[i]<key)
                i++;
            if(i<j){
            	arr[j] = arr[i];
                j--;
            }
        }
        //i == j
        arr[i] = key;
        //左边数组递归调用
        quickSort(arr, l, i-1);
        //右边数组递归调用
        quickSort(arr, i+1, r);
	}
	
	/**
	 * 归并排序是建立在归并操作上的一种有效的排序算法。该算法是采用分治法的一个非常典型的应用。
	 * 首先考虑下如何将2个有序数列合并。这个非常简单，只要从比较2个数列的第一个数，谁小就先取谁，
	 * 取了后就在对应数列中删除这个数。然后再进行比较，如果有数列为空，那直接将另一个数列的数据依次取出即可。
	 */
	public static void mergeSort(int a[],int first,int last,int temp[]){
	     
	     if(first < last){
	         int middle = (first + last)/2;
	         //左半部分排好序
	         mergeSort(a,first,middle,temp);
	         //右半部分排好序
	         mergeSort(a,middle+1,last,temp);
	         //合并左右部分
	         mergeArray(a,first,middle,last,temp); 
	     }
	 }
	
	public static void mergeArray(int a[],int first,int middle,int end,int temp[]){     
	     int i = first;
	     int m = middle;
	     int j = middle+1;
	     int n = end;
	     int k = 0; 
	     while(i<=m && j<=n){
	    	 //把两组从前到后，依次放入temp中
	         if(a[i] <= a[j]){
	             temp[k] = a[i];
	             k++;
	             i++;
	         }else{
	             temp[k] = a[j];
	             k++;
	             j++;
	         }
	     }   
	     //下面两个循环是把上面剩下的较大数在放入temp后面
	     while(i<=m){
	         temp[k] = a[i];
	         k++;
	         i++;
	     }   
	     while(j<=n){
	         temp[k] = a[j];
	         k++;
	         j++; 
	     }
	     
	     //将temp的所有数据存回arr中
	     for(int ii=0;ii<k;ii++){
	         a[first + ii] = temp[ii];
	     }
	 }
	
	/**
	 * 
	 * 
	 * @param a
	 * @param n
	 */
	 public static void MinHeapSort(int a[],int n){
	     int temp = 0;
	     MakeMinHeap(a,n);
	     
	     for(int i=n-1;i>0;i--){
	         temp = a[0];
	         a[0] = a[i];
	         a[i] = temp; 
	         MinHeapFixdown(a,0,i);
	     }   
	 }
	
	//构建最小堆
	public static void MakeMinHeap(int a[], int n){
	    for(int i=(n-1)/2 ; i>=0 ; i--){
	        MinHeapFixdown(a,i,n);
	    }
	}
	
	//从i节点开始调整,n为节点总数 从0开始计算 i节点的子节点为 2*i+1, 2*i+2  
	public static void MinHeapFixdown(int a[],int i,int n){
      int j = 2*i+1; //子节点
      int temp = 0;
      
      while(j<n){
          //在左右子节点中寻找最小的
          if(j+1<n && a[j+1]<a[j]){   
              j++;
          }
          
          if(a[i] <= a[j])
              break;
          
          //较大节点下移
          temp = a[i];
          a[i] = a[j];
          a[j] = temp;
          
          i = j;
          j = 2*i+1;
      }
	}
	
	/**
	 * 
	 */
	public static void RadixSort(int A[],int temp[],int n,int k,int r,int cnt[]){
	      
	      //A:原数组
	      //temp:临时数组
	      //n:序列的数字个数
	      //k:最大的位数2
	      //r:基数10
	      //cnt:存储bin[i]的个数
	      
	      for(int i=0 , rtok=1; i<k ; i++ ,rtok = rtok*r){
	          
	          //初始化
	          for(int j=0;j<r;j++){
	              cnt[j] = 0;
	          }
	          //计算每个箱子的数字个数
	          for(int j=0;j<n;j++){
	              cnt[(A[j]/rtok)%r]++;
	          }
	          //cnt[j]的个数修改为前j个箱子一共有几个数字
	          for(int j=1;j<r;j++){
	              cnt[j] = cnt[j-1] + cnt[j];
	          }
	          //重点理解
	          for(int j = n-1;j>=0;j--){      
	              cnt[(A[j]/rtok)%r]--;
	              temp[cnt[(A[j]/rtok)%r]] = A[j];
	          }
	          for(int j=0;j<n;j++){
	              A[j] = temp[j];
	          }
	      }
	  }
}
