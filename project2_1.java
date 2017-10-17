import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class project2_1 {
	public static void main(String[] args) throws FileNotFoundException {
    	double a =0;
    	double b=0;
    	int count=0;
    	double[][] class1= new double[202][2];
    	
    	double[][] m = new double[][] {
    		{0,0,0,0,72},
    		{1,0,0,0,-273},
    		{0,1,0,0,-73},
    		{0,0,1,0,51.25},
    		{0,0,0,1,3.25}
        };
    	Scanner first= new Scanner(new File("DataP2.txt"));
    	
    	while(first.hasNextDouble()){
    		a= first.nextDouble();
    		b=first.nextDouble();
    		if (count<class1.length){
    			class1[count][0]=a;
    			class1[count][1]=b;
    			count++;
    		}
    		
    	}
    	
    	first.close();
    	System.out.println(toString(class1));
    	System.out.println(toString(meanvector(class1)));
    	System.out.println(toString(covariance(class1)));
    	System.out.println((trace(covariance(class1))));
    	System.out.println((determinant(covariance(class1),2))+"\n");
    	System.out.println(toString(eigenval((class1))));
    	System.out.println(toString(eigenvec(class1)));
    	//lev(m);
    	
    	
    	
    	
    }
	public static void lev(double[][]m){
		double p1;
		double p2;
		double p3;
		double p4;
		double p5;
		String x;
		p1=trace(m);
		double[][] mtemp=new double[5][5];
		
		
		for (int i=0; i<m.length;i++){
			mtemp[i][i]=m[i][i]-p1;
		}
		mtemp=multiplyByMatrix(m,mtemp);
		p2=trace(mtemp)/2;
		
		for (int i=0; i<m.length;i++){
			mtemp[i][i]=m[i][i]-p2;
		}
		mtemp=multiplyByMatrix(m,mtemp);
		p3=trace(mtemp)/3;
		
		for (int i=0; i<m.length;i++){
			mtemp[i][i]=m[i][i]-p3;
		}
		mtemp=multiplyByMatrix(m,mtemp);
		p4=trace(mtemp)/4;
		
		for (int i=0; i<m.length;i++){
			mtemp[i][i]=m[i][i]-p4;
		}
		mtemp=multiplyByMatrix(m,mtemp);
		p5=trace(mtemp)/5;
		System.out.print(p5+"+"+p4+"x +"+p3+"x^2 +"+p2+"x^3 +"+p1+"x^4 + x^5");
		
		int[] a= { 1, 2, 3, 4,5,6,7,8,9,10,11,12,13,14 };
		double total=0;
		int count=0;
		double mean=0;
		
		   
		
		while ((a= permute(a)) != null){
			double totaltemp=0;
			System.out.println(Arrays.toString(a));
			for(int i=0; i<a.length-1;i++){
				double tempx=0;
				double tempy=0;
				double tempx1=0;
				double tempy1=0;
				
				if (a[i]==1){
					tempx=0.372090608;
					tempy=0.432608199;
				}
				if (a[i]==2){
					tempx=0.38029396;
					tempy=0.713361671;
					
				}
				if (a[i]==3){
					tempx=0.898119767;
					tempy=0.772681874;
					
				}
				if (a[i]==4){
					tempx=0.167702257;
					tempy=0.99219988;
					
				}
				if (a[i]==5){
					tempx=0.686992927;
					tempy=0.93838682;
					
				}
				if (a[i]==6){
					tempx=0.274830532;
					tempy=0.127452799;
				}
				if (a[i]==7){
					tempx=0.424618695;
					tempy=0.817378304;
				}
				if (a[i]==8){
					tempx=0.478824774;
					tempy=0.093485707;
				}
				if (a[i]==9){
					tempx=0.656087536;
					tempy=0.875909204;
				}
				if (a[i]==10){
					tempx=0.240264048;
					tempy=0.324621796;
				}
				if (a[i]==11){
					tempx=0.830124281;
					tempy=0.076594979;
				}
				if (a[i]==12){
					tempx=0.72887909;
					tempy=0.622051319;
				}
				if (a[i]==13){
					tempx=0.442833825;
					tempy=0.387846749;
				}
				if (a[i]==14){
					tempx=0.088127924;
					tempy=0.547910343;
				}
				if (a[i+1]==1){
					tempx1=0.372090608;
					tempy1=0.432608199;
				}
				if (a[i+1]==2){
					tempx1=0.38029396;
					tempy1=0.713361671;
					
				}
				if (a[i+1]==3){
					tempx1=0.898119767;
					tempy1=0.772681874;
					
				}
				if (a[i+1]==4){
					tempx1=0.167702257;
					tempy1=0.99219988;
					
				}
				if (a[i+1]==5){
					tempx1=0.686992927;
					tempy1=0.93838682;
					
				}
				if (a[i+1]==6){
					tempx1=0.274830532;
					tempy1=0.127452799;
				}
				if (a[i+1]==7){
					tempx1=0.424618695;
					tempy1=0.817378304;
				}
				if (a[i+1]==8){
					tempx1=0.478824774;
					tempy1=0.093485707;
				}
				if (a[i+1]==9){
					tempx1=0.656087536;
					tempy1=0.875909204;
				}
				if (a[i+1]==10){
					tempx1=0.240264048;
					tempy1=0.324621796;
				}
				if (a[i+1]==11){
					tempx1=0.830124281;
					tempy1=0.076594979;
				}
				if (a[i+1]==12){
					tempx1=0.72887909;
					tempy1=0.622051319;
				}
				if (a[i+1]==13){
					tempx1=0.442833825;
					tempy1=0.387846749;
				}
				if (a[i+1]==14){
					tempx1=0.088127924;
					tempy1=0.547910343;
				}
				totaltemp=total+Math.sqrt(Math.pow((tempx-tempx1), 2)+Math.pow((tempy-tempy1), 2));
				
			}
			total=total+Math.pow((totaltemp-mean), 2);
			count++;
		}
		mean=total/count;
		mean=Math.sqrt(mean);
	
		
	}
	public static void swap(int[] s, int i, int j) {
	    int t= s[i];
	    s[i]= s[j]; s[j]= t;
	}
	 
	public static int[] permute(int[] t) {
	     
	    int i, j;
	     
	    for (i= t.length-1; --i >= 0;)
	         if (t[i] < t[i+1])
	             break;
	     
	    if (i < 0) return null;
	     
	    for (j= t.length; --j > i;)
	        if (t[i] < t[j])
	            break;
	     
	    swap(t, i, j);
	     
	    for (j= t.length; ++i < --j;)
	        swap(t, i, j);
	     
	    return t;
	}
	public static void exhaustive(){
		
	}
	
	
	
	
	public static double[][] eigenvec(double[][] class1){
		double[][] x=new double[2][2];
		double[][] x1=new double[2][2];
		double[][] x2=new double[2][2];
		double[][] eigenval=eigenval(class1);
		double temp;
		double temp1;
		temp=Math.sqrt(.337626*.337626+.94128*.94128);
		temp1=Math.sqrt(.337626*.337626+.94128*.94128);
        
		x[0][0]=.337626/temp;
		x[0][1]=.94128/temp;
		x[1][0]=-.94128/temp1;
		x[1][1]=.337626/temp1;
		
		
		
		
		return x;
	}
	public static double[][] sub(double[][] class1, double[][] class2){
		double [][] x= new double [2][2];
		x[0][0]=class1[0][0]-class2[0][0];
		x[0][1]=class1[0][1]-class2[0][1];
		x[1][0]=class1[1][0]-class2[1][0];
		x[1][1]=class1[1][1]-class2[1][1];
		
		
		
		return x;
		
	}
    
	public static double[][] eigenval(double[][] class1){
		double[][] x=new double[1][2];
		x[0][0]=(trace(covariance(class1)) +Math.sqrt((trace(covariance(class1))*trace(covariance(class1)))-4*determinant(covariance(class1),2))  )/2;
		x[0][1]=(trace(covariance(class1)) -Math.sqrt((trace(covariance(class1))*trace(covariance(class1)))-4*determinant(covariance(class1),2))  )/2;
		return x;
		
	}

	
	
	public static double trace(double[][] class1){
		double x=0;
		for (int i=0; i<class1.length;i++){
			x=x+class1[i][i];
		}
		return x;
	}
	public static double determinant(double a[][], int n){
    	double det = 0;
    			int sign = 1, p = 0, q = 0;
    	

    	if(n==1){
    		det = a[0][0];
    	}
    	else{
    		double b[][] = new double[n-1][n-1];
    		for(int x = 0 ; x < n ; x++){
    			p=0;q=0;
    			for(int i = 1;i < n; i++){
    				for(int j = 0; j < n;j++){
    					if(j != x){
    						b[p][q++] = a[i][j];
    						if(q % (n-1) == 0){
    							p++;
    							q=0;
    						}
    					}
    				}
    			}
    			det = det + a[0][x] *
                                  determinant(b, n-1) *
                                  sign;
    			sign = -sign;
    		}
    	}
    	return det;
    }
    public static double[][] covariance(double[][] class1){
    	
    	double[][] temp=new double[2][1];
    	double[][] temp2=new double[1][2];
    	double[][] tempx= new double[][]{
    		{0,0},
    		{0,0}
    	};
    	double[][] meanvec= meanvector(class1);
    	for(int i = 0; i < class1.length; i++) {        
    		temp[0][0]=subtract(class1[i],meanvec[0])[0]; 
    		temp[1][0]=subtract(class1[i],meanvec[0])[1]; 
    		temp2[0][0]=temp[0][0];
    		temp2[0][1]=temp[1][0];
    		
    		tempx=add(tempx, multiplyByMatrix(temp,temp2));
    		
            
    	}
    	
        	
    	tempx=scalarMultiplication(tempx,(1./class1.length));
        
        
    	
    	
    	
    	return tempx;
    }
    public static double[][] add(double[][] a, double[][] b) {
        int m = a.length;
        int n = a[0].length;
        double[][] c = new double[2][2];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                c[i][j] = a[i][j] + b[i][j];
        return c;
    }
    public static double[][] multiplyByMatrix(double[][] m1, double[][] m2) {
        int m1ColLength = m1[0].length; // m1 columns length
        int m2RowLength = m2.length;    // m2 rows length
        if(m1ColLength != m2RowLength) return null; // matrix multiplication is not possible
        int mRRowLength = m1.length;    // m result rows length
        int mRColLength = m2[0].length; // m result columns length
        double[][] mResult = new double[mRRowLength][mRColLength];
        for(int i = 0; i < mRRowLength; i++) {         // rows from m1
            for(int j = 0; j < mRColLength; j++) {     // columns from m2
                for(int k = 0; k < m1ColLength; k++) { // columns from m1
                    mResult[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return mResult;
    }
    public static double[][] scalarMultiplication(double[][] A,double num) {
	    double[][] arrayScalarMul = new double[A.length][A[0].length];
	
	    for (int x=0; x < arrayScalarMul.length; x++) {
	        for (int y=0; y < arrayScalarMul[x].length; y++) {
	            arrayScalarMul[x][y] = num * A[x][y];
	        }
	    }
	    return arrayScalarMul;
	}
    public static double[] subtract(double[] a, double[] b) {
        
        int n = a.length;
        double[] c = new double[n];
                for (int j = 0; j < n; j++){
                c[j] = a[j] - b[j];
                }
        return c;
    }

    public static double[][] meanvector(double[][] class1){
    	double x= 0;
    	double x1= 0;
    	double[][] temp=new double[1][2];
    	
    	for(int i = 0; i < 202; i++) {        
                      
            
            	x= x + class1[i][0];
            	x1= x1+ class1[i][1] ;
            
            	
            
    	}
    	
    	temp[0][0]=(x * (1.0/202));
    	
    	temp[0][1]=(x1 * (1.0/202));
    	
    	
    	
       
    	return temp;
    	
    }
	
	public static String toString(double[][] m) {
        String result = "";
        for(int i = 0; i < m.length; i++) {
            for(int j = 0; j < m[i].length; j++) {
                result += String.format("%11.9f", m[i][j]);
                result+= " ";
            }
            result += "\n";
        }
        return result;
    }
	
	public static double[][] transposeMatrix(double[][] a)
    {
            double temp;
            for(int i=0 ; i<(a.length/2 + 1); i++)
            {
                for(int j=i ; j<(a[0].length) ; j++)
                {
                    temp = a[i][j];
                    a[i][j] = a[j][i];
                    a[j][i] = temp;
                }
            }

            return a;
        }

}
