package com.gac.oj;

import java.util.ArrayList;
import java.util.Iterator;

public class DES_Key {
	
	public static void main(String[] args) {
		//Scanner input = new Scanner(System.in);
//		System.out.println("请输入十六进制明文：");
//		String plaintxt = input.next();
//		Integer tmpInt = Integer.valueOf(plaintxt,16);
//		String binary_plain = Integer.toBinaryString(tmpInt);
//		System.out.println(binary_plain);
//		System.out.println("请输入十六进制明文密钥：");
//		String Key = input.next();
		String Key = "1234567890ABCDFE";
		String binary_Key = hexString2binaryString(Key);
		System.out.print("binary_Key:");
		//0001 0010 0011 0100 0101 0110 0111 1000 1001 0000 1010 1011 1100 1101 1111 1110
		System.out.println(binary_Key);
//		char[] a = binary_Key.toCharArray();
//		for(int i=0;i<64;i++) {
//			System.out.print(i + ":[" + a[i] + "];");
//		}
//		System.out.println();
		ArrayList<char[]> resultKi = new ArrayList<char[]>();//存放16个Ki
		GenerateKey(binary_Key,resultKi);
		
		Iterator<char[]> it = resultKi.iterator();
		int KiIndex = 0;
		while(it.hasNext()){
			char []temp = (char[]) it.next();
			System.out.print("第"+(KiIndex+1)+"轮密钥:");
			System.out.println(temp);
			KiIndex++;
		}
		
		String Plain = "1111111111111110";
		String binary_Plain = hexString2binaryString(Plain);
		System.out.print("binary_Plain:");
		System.out.println(binary_Plain);
		
		char[] DESEncrypt = EncryptCircleTrans(binary_Plain, resultKi);
		System.out.print("DES加密结果：");
		System.out.println(DESEncrypt);
		
		char[] DESDecrypt = DecryptCircleTrans(ToString(DESEncrypt), resultKi);
		System.out.print("DES解密结果：");
		System.out.println(DESDecrypt);
			
	}
	
	//辅助函数：将char转为String
	public static String ToString(char[]a){
		String b = "";
		for(int i=0;i<a.length;i++){
			b += a[i];
		}
		return b;
	}
	
	//char数组转为int
		public static int[] trsnChar2Int(char[] CharArr){
			int [] resultArr = new int[CharArr.length];
			for(int i=0;i<CharArr.length;i++){
				resultArr[i] = CharArr[i]-'0';
			}
			return resultArr;
		}
		
	//十六进制转2进制字符串
	public static String hexString2binaryString(String hexString)  
    {  
        if (hexString == null)  
            return null;  
        String bString = "", tmp;  
        for (int i = 0; i < hexString.length(); i++)  
        {  
            tmp = "0000"  
                    + Integer.toBinaryString(Integer.parseInt(hexString  
                            .substring(i, i + 1), 16));  
            bString += tmp.substring(tmp.length() - 4);  
        }  
        return bString;  
    }  

	//密钥选择置换PC_1 (64-56)
	public static char[] KeyPermutation1(String Key){
		char [] key = Key.toCharArray();//Get 64bits key array
		//System.out.println(key);
		char [] resultKey = new char[56];
		
		int PC_1[] = new int[]{
				57,49,41,33,25,17,9,1,
				58,50,42,34,26,18,10,2,
				59,51,43,35,27,19,11,3,
				60,52,44,36,63,55,47,39,
				31,23,15,7,62,54,46,38,
				30,22,14,6,61,53,45,37,
				29,21,13,5,28,20,12,4};
		
		for(int i=0;i<resultKey.length;i++){
			resultKey[i] = key[PC_1[i]-1];	
		}
		return resultKey;
		
	}
	//CD选择置换PC_2 (56-48)
	public static char[] KeyPermutation2(char[] CD){
		char [] cd = CD;//Get 56bits CD array
		//System.out.println(cd.length);
		char [] resultCd = new char[48];
		int PC_2[] = new int[]{14,17,11,24,1,5,3,28,15,6,21,10,
				23,19,12,4,26,8,16,7,27,20,13,2,
				41,52,31,37,47,55,30,40,51,45,33,48,
				44,49,39,56,34,53,46,42,50,36,29,32};
		
		for(int i=0;i<48;i++){
			resultCd[i] = cd[PC_2[i]-1];
		}
		return resultCd;
		
	}
	
	//循环生成密钥Ki
	public static char[] CircleGenKey(char[]C,char[]D,int flag){
		char [] Ci = new char[28];
		char [] Di = new char[28];
		char [] ResultKey = new char[56];

//		if(flag == 2) {
//			for(int i = 0;i < C.length;i++) {
//				System.out.print(C[i]);
//			}
//			System.out.println();
//			for(int i = 0;i < D.length;i++) {
//				System.out.print(D[i]);
//			}
//			System.out.println();
//		}
//		
//		if(flag == 0) {
//			for(int i=0;i<28;i++){
//				Ci[i] = C[(i+1)%28];
//				Di[i] = D[(i+1)%28];
//			}
//
//			System.out.println(Ci);
//			System.out.println(Di);
//		}
		
		if(flag == 0 || flag == 1 || flag == 8 || flag == 15 ){
			//循环左移1
			for(int i=0;i<28;i++){
				Ci[i] = C[(i+1)%28];
				Di[i] = D[(i+1)%28];
			}

			if(flag == 15)
				Ci[27] = '0';
			
			for(int i=0;i<28;i++){
				ResultKey[i]= Ci[i];
			}
			for(int j=28;j<56;j++){
				ResultKey[j]= Di[j-28];
			}
		}else{
			//循环左移2
	        char temp0 = C[0];
	        char temp00 = D[0];
			for(int i=0;i<28;i++){	
				C[i] = C[(i+1)%28];
				D[i] = D[(i+1)%28];
			}
			C[27]=temp0;
			D[27]=temp00;
			
			char temp1=C[0];
			char temp11=D[0];
			for(int i=0;i<28;i++){
				C[i] = C[(i+1)%28];
				D[i] = D[(i+1)%28];		
			}
			C[27]=temp1;
			D[27]=temp11;
			
//			if(flag == 2) {
//				System.out.println(C);
//				System.out.println(D);
//			}
			
			for(int i=0;i<28;i++){
				ResultKey[i]= C[i];
			}
			for(int j=28;j<56;j++){
				ResultKey[j]= D[j-28];
			}
		}
		char[] ResultKi = KeyPermutation2(ResultKey);
		
		//任意看一行即可知道原理
//		if(flag == 0) {
//			for(int i=0;i<ResultKi.length;i++) {
//				System.out.print(i+ ":[" + ResultKi[i] + "];");
//			}
//			System.out.println();
//		}
		
		return ResultKi;//ki	
	}
	
	//生成密钥16轮Ki
	public static void GenerateKey(String Key,ArrayList<char[]> resultKi){
		
		char [] keySeed = KeyPermutation1(Key);//Get 56bits key that after OptPermutation PC_1
//		for(int i=0;i<keySeed.length;i++) {
//			//System.out.print(i+ ":[" + keySeed[i] + "];");
//			//输出种子秘钥
//			System.out.print(keySeed[i]);
//		}
//		System.out.println();
		char [] LeftC0 = new char[28];
		for(int i=0;i<LeftC0.length;i++){
			LeftC0[i] = keySeed[i];
		}
		char [] RightD0 = new char[28];
		for(int j=0;j<RightD0.length;j++){
			
			RightD0[j] = keySeed[j+28];
		}
		char [] LeftC = LeftC0;
		char [] RightD = RightD0;
		
		for(int i=0;i<16;i++){
			char[] temp = CircleGenKey(LeftC,RightD,i);
			resultKi.add(temp);
			//System.out.println(temp);
		}
	}

	//将原明文进行IP置换
	public static char[] InitialPermutation(char[] plaintxt)
	{
		char plain[] = plaintxt;
		int[] IP = new int[]{58,50,42,34,26,18,10,2,60,52,44,36,28,20,12,4,
				62,54,46,38,30,22,14,6,64,56,48,40,32,24,16,8,
				57,49,41,33,25,17,9,1,59,51,43,35,27,19,11,3,
				61,53,45,37,29,21,13,5,63,55,47,39,31,23,15,7};

		char[]result = new char[64];
		for(int i=0; i< plaintxt.length; i++){
			result[i]= plain[IP[i]-1];
		}
		return result ;
	}
	//IP 逆置换
	public static char[] ReverseInitialPermutation(char[]afterCircle)
	{
		char[] afterCircleTmp = afterCircle;
		char[] resultReverseIP = new char[64];
		int [] IPReverse = new int[]{
				40, 8, 48, 16, 56, 24, 64, 32, 39, 7, 47, 15, 55, 23, 63, 31,
				38, 6, 46, 14, 54, 22, 62, 30, 37, 5, 45, 13, 53, 21, 61, 29,
			    36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51, 19, 59, 27,
				34, 2, 42, 10, 50, 18, 58, 26, 33, 1, 41,  9, 49, 17, 57, 25
		};
		
		for(int i=0;i<resultReverseIP.length;i++){
			resultReverseIP[i] = afterCircleTmp[IPReverse[i]-1];
		}
		
		return resultReverseIP;
	}
	
	//异或操作
	public static char[] XorAandB(char[]A,char[]B){
		char[]tmpA = A;
		char[]tmpB = B;
		int []OperateA = trsnChar2Int(tmpA);
		int []OperateB = trsnChar2Int(tmpB);
		if(OperateA.length != OperateB.length){
			return null;
		}
		
		char[] XorResult = new char[OperateA.length];
		
		for(int i=0;i<OperateA.length;i++){
			int temp = OperateA[i]^OperateB[i];
			XorResult[i] = (char) ('0'+temp);
		}
		return XorResult;
		
	}
	
	//E 扩展
	public static char[] FunctionExtension(char[] Ri){
		char[] tempRi = Ri;
		char[] resultRi = new char[48];
		int[] Extension = new int[]{32,1,2,3,4,5,4,5,6,7,8,9,8,9,10,11,12,13,
				12,13,14,15,16,17,16,17,18,19,20,21,20,21,22,23,24,25,
				24,25,26,27,28,29,28,29,30,31,32,1};
		for(int i=0;i<48;i++){
			resultRi[i] = tempRi[Extension[i]-1];
		}
		
		return resultRi;
	}

	//加密函数f 置换函数P
	public static char[] FunctionPermutation(char[] Sout ){
		char[] tempSout = Sout;
		char[] resultPout = new char[32];
		int [] Permutation = new int[]{16,7,20,21,29,12,28,17,1,15,23,26,5,18,31,10,
				2,8,24,14,32,27,3,9,19,13,30,6,22,11,4,25};
		for(int i=0;i<32;i++){
			resultPout[i] = tempSout[Permutation[i]-1];
		}
		return resultPout;
	}
	
	//加密函数f S盒变换
	public static char[] SBoxTrans(char[] E_nor_Ki){
		int [][][] Sbox = new int[][][]{
			{{14,	 4,	13,	 1,  2, 15, 11,  8,  3, 10,  6, 12,  5,  9,  0,  7},
			 {0, 15,  7,  4, 14,  2, 13,  1, 10,  6, 12, 11,  9,  5,  3,  8},
			 {4,  1, 14,  8, 13,  6,  2, 11, 15, 12,  9,  7,  3, 10,  5,  0},
		     {15, 12,  8,  2,  4,  9,  1,  7,  5, 11,  3, 14, 10,  0,  6, 13}},
			// S2 
		    {{15,  1,  8, 14,  6, 11,  3,  4,  9,  7,  2, 13, 12,  0,  5, 10},
			 {3, 13,  4,  7, 15,  2,  8, 14, 12,  0,  1, 10,  6,  9, 11,  5},
			 {0, 14,  7, 11, 10,  4, 13,  1,  5,  8, 12,  6,  9,  3,  2, 15},
		     {13,  8, 10,  1,  3, 15,  4,  2, 11,  6,  7, 12,  0,  5, 14,  9}},
			// S3 
		    {{10,  0,  9, 14,  6,  3, 15,  5,  1, 13, 12,  7, 11,  4,  2,  8},
			 {13,  7,  0,  9,  3,  4,  6, 10,  2,  8,  5, 14, 12, 11, 15,  1},
			 {13,  6,  4,  9,  8, 15,  3,  0, 11,  1,  2, 12,  5, 10, 14,  7},
		     {1, 10, 13,  0,  6,  9,  8,  7,  4, 15, 14,  3, 11,  5,  2, 12}},
			// S4 
		     {{7, 13, 14,  3,  0,  6,  9, 10,  1,  2,  8,  5, 11, 12,  4, 15},
			  {13,  8, 11,  5,  6, 15,  0,  3,  4,  7,  2, 12,  1, 10, 14,  9},
			  {10,  6,  9,  0, 12, 11,  7, 13, 15,  1,  3, 14,  5,  2,  8,  4},
			  { 3, 15,  0,  6, 10,  1, 13,  8,  9,  4,  5, 11, 12,  7,  2, 14}},
			// S5 
		     {{2, 12,  4,  1,  7, 10, 11,  6,  8,  5,  3, 15, 13,  0, 14,  9},
			  {14, 11,  2, 12,  4,  7, 13,  1,  5,  0, 15, 10,  3,  9,  8,  6},
		      { 4,  2,  1, 11, 10, 13,  7,  8, 15,  9, 12,  5,  6,  3,  0, 14},
		      {11,  8, 12,  7,  1, 14,  2, 13,  6, 15,  0,  9, 10,  4,  5,  3}},
			// S6 
		    {{12,  1, 10, 15,  9,  2,  6,  8,  0, 13,  3,  4, 14,  7,  5, 11},
			 {10, 15,  4,  2,  7, 12,  9,  5,  6,  1, 13, 14,  0, 11,  3,  8},
			 {9, 14, 15,  5,  2,  8, 12,  3,  7,  0,  4, 10,  1, 13, 11,  6},
		     {4,  3,  2, 12,  9,  5, 15, 10, 11, 14,  1,  7,  6,  0,  8, 13}},
			// S7 
		     {{4, 11,  2, 14, 15,  0,  8, 13,  3, 12,  9,  7,  5, 10,  6,  1},
			  {13,  0, 11,  7,  4,  9,  1, 10, 14,  3,  5, 12,  2, 15,  8,  6},
			  {1,  4, 11, 13, 12,  3,  7, 14, 10, 15,  6,  8,  0,  5,  9,  2},
		      {6, 11, 13,  8,  1,  4, 10,  7,  9,  5,  0, 15, 14,  2,  3, 12}},
			// S8 
		    {{13,  2,  8,  4,  6, 15, 11,  1, 10,  9,  3, 14,  5,  0, 12,  7},
			 {1, 15, 13,  8, 10,  3,  7,  4, 12,  5,  6, 11,  0, 14,  9,  2},
			 {7, 11,  4,  1,  9, 12, 14,  2,  0,  6, 10, 13, 15,  3,  5,  8},
		     {2,  1, 14,  7,  4, 10,  8, 13, 15, 12,  9,  0,  3,  5,  6, 11}}};
		    char outBox[] = new char[32];
			for(int i=0;i<8;i++){
				char[] temp6in = new char[6];
				
				for(int i1=0;i1<6;i1++){
					 temp6in[i1] = E_nor_Ki[i*6+i1];
				}
				//System.out.println(temp6in);
				int [] tem6in_int = trsnChar2Int(temp6in);
				int row = tem6in_int[0]*2+tem6in_int[5];
				int column = tem6in_int[1]*8+tem6in_int[2]*4+tem6in_int[3]*2+tem6in_int[4];
				int soutInt = Sbox[i][row][column];
				String HexSoutmp = Integer.toHexString(soutInt);
				
				char[] binarySoutChar = hexString2binaryString(HexSoutmp).toCharArray();
				
				int [] binarySoutInt = trsnChar2Int(binarySoutChar);

				for(int j=0;j<4;j++){
					outBox[i*4+j] = (char) ('0'+binarySoutInt[j]);
				}
			}
			return outBox;
	}
	
	//F函数
	public static char[] EcryptFunction(char[]Ri_1,char[]Ki){
		char []tmpRi_1 = Ri_1;
		char []tmpKi = Ki;

		char[] ExtensionR = FunctionExtension(tmpRi_1);//获得Ri经过E扩展后的数组32-48
//		for(int i=0;i<ExtensionR.length;i++) {
//			System.out.print(i+ ":[" + ExtensionR[i] + "];");
//		}
//		System.out.println();
		
		//Ri'与Ki异或操作
		char [] resultXorAandB = XorAandB(tmpKi,ExtensionR);
		//System.out.println(resultXorAandB);
		char[]resultSBoxTrans = SBoxTrans(resultXorAandB);
		
		char[] FunctionResult = FunctionPermutation(resultSBoxTrans);
		
		return FunctionResult;
	}
	
	//DES 16轮加密圈变换
	public static char[] EncryptCircleTrans(String IPtxt,ArrayList<char[]> listKi){
		char[] IPtxt_tmp = IPtxt.toCharArray();
		char []resultOfIP = InitialPermutation(IPtxt_tmp);//明文IP初始置换
		char[] afterIPRi = new char[32];
		char[] afterIPLi = new char[32];
		
//		for(int i=0;i<resultOfIP.length;i++) {
//			System.out.print(i+ ":[" + resultOfIP[i] + "];");
//		}
//			System.out.println();
		
 		for(int i=0;i<32;i++){//获得初始L0和R0
			afterIPLi[i] = resultOfIP[i];
			afterIPRi[i] = resultOfIP[i+32];
		}

 		char[] resultRi = afterIPRi;
 		char[] resultLi = afterIPLi;
 
// 		System.out.println(resultLi);
// 		System.out.println(resultRi);
 		
		for(int i=0;i<16;i++){//循环16次
			char[]resultRitmp = resultRi;
			char[]resultLitmp = resultLi;
			resultLi = resultRitmp;//Li = Ri-1

			char[] FOut = EcryptFunction(resultRitmp,listKi.get(i));
			resultRi = XorAandB(resultLitmp,FOut);
			
			//查看第一个即可知道原理
//			if(i == 0) {
//				System.out.println(resultRi);
//			}
		}
		char[] finalCircleResult = new char[64];
		for(int i=0;i<32;i++){
			finalCircleResult[i] = resultLi[i];
			finalCircleResult[32+i] = resultRi[i];
		}
		
		char[] resultEncrypted = ReverseInitialPermutation(finalCircleResult);
				
		return resultEncrypted;
	}
	//DES 16轮解密圈变换
	public static char[] DecryptCircleTrans(String Encrypted,ArrayList<char[]> listKi){
		char[] IEncrypted_tmp = Encrypted.toCharArray();
		char []resultOfIP = InitialPermutation(IEncrypted_tmp);//密文IP-1逆置换
		char[] afterIPRi = new char[32];
		char[] afterIPLi = new char[32];
		
 		for(int i=0;i<32;i++){//获得初始L16和R16
			afterIPLi[i] = resultOfIP[i];
			afterIPRi[i] = resultOfIP[i+32];
		}
 		
 		char[] resultRi = afterIPRi;
 		char[] resultLi = afterIPLi;
 
		for(int i=15;i>=0;i--){//循环16次
			char[]resultRitmp = resultRi;
			char[]resultLitmp = resultLi;
			resultRi = resultLitmp;//Ri-1 = Li
			
			char[] FOut = EcryptFunction(resultLitmp,listKi.get(i));
			resultLi = XorAandB(resultRitmp,FOut);
		}
		char[] finalCircleResult = new char[64];
		for(int i=0;i<32;i++){
			finalCircleResult[i] = resultLi[i];
			finalCircleResult[32+i] = resultRi[i];
		}
		
		char[] resultDecrypted = ReverseInitialPermutation(finalCircleResult);
		
		return resultDecrypted;
	}
}