package iiitd;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Readeer {
    static BufferedReader reader;
    static StringTokenizer tokenizer;

    static void init(InputStream input) {
        reader = new BufferedReader(
                     new InputStreamReader(input) );
        tokenizer = new StringTokenizer("");
    }

    static String next() throws IOException {
        while ( ! tokenizer.hasMoreTokens() ) {
            tokenizer = new StringTokenizer(
                   reader.readLine() );
        }
        return tokenizer.nextToken();
    }

    static int nextInt() throws IOException {
        return Integer.parseInt( next() );
    }
	
    static double nextDouble() throws IOException {
        return Double.parseDouble( next() );
    }
}

public class Heaps {
	int size;
	int bits;
	node[] array;
	static class node
	{
		int data;
		int frequency;
		node left,right;
		String code;
		node(int f,node r,node l)
		{
			frequency=f;
			left=l; right=r;
		}
		node(int d,int f)
		{
			data=d; frequency=f;
			left=null; right=null;
		}
	}
	Heaps(int s,int length)
	{
		array=new node[length];
		size=s;
		bits=0;
	}
	void put(node temp,String s)
	{
		temp.code=s;
	}
	void insert(node temp)
	{
		array[size++]=temp;
		int current=size-1; int parent=current/2;
		while(parent>0)
		{
			if(array[current].frequency<array[parent].frequency)
			{
				node in=array[current];
				array[current]=array[parent];
				array[parent]=in;
				current=parent;
				parent=parent/2;
			}
			else
			{				
				break;
			}
		}
	}
	
	node delete()
	{
		node give=array[1];
		array[1]=array[--size];
		/* if(array[2].frequency>array[3].frequency)
		{
			node temp=array[3];
			array[3]=array[1];
			array[1]=temp;
		}
		else
		{
			node temp=array[2];
			array[2]=array[1];
			array[1]=temp;
		} */
		return give;
	}
	void heapify()
	{
		int current;
		if(size%2!=0)
			current=size/2;
		else
			current=size/2-1;
		while(current>=1)
		{
			helper(current);
			current--;
			
		}
		
	}
	void helper(int current) {
		while (2*current <= size-1) 
		{
		int left=2*current; 
		int right=left+1;
		int target;
		if (right <=size-1 && array[right].frequency < array[left].frequency)
			target = right;
		else       
			target=left;
		if (array[target].frequency < array[current].frequency)
		{
			node temp=array[current];
			array[current] = array[target];
			array[target]=temp;
			current = target;
		}
		else
			break;
		}
	}
	void generate(node huff,String s)
	{
		if (huff.left == null && huff.right == null) {
			put(huff,s);
			bits=bits+s.length()*huff.frequency;
            return;
        }    
        generate(huff.left,s+'0');
        generate(huff.right,s+'1');
    }
	public static void main(String[] arg) throws IOException
	{
		Readeer.init(System.in);
		int x=Readeer.nextInt(); int y=Readeer.nextInt();
		int[][] arr;
		arr=new int[y][x];
		int[] freqarray=new int[256];
		int[] nonzero=new int[256];
		Arrays.fill(freqarray,0);
		int k=0;
		for(int i=0;i<y;i++)
		{
			for(int j=0;j<x;j++)
			{
				arr[i][j]=Readeer.nextInt();
				freqarray[arr[i][j]]++;
				if(freqarray[arr[i][j]]==1)
					nonzero[k++]=arr[i][j];
			}
		}
		int divider=8*x*y;
		int[] frenew=new int[26];
		int p=0;
		for(int i=0;i<256;i++)
		{
			frenew[p]+=freqarray[i];
			if(i%10==9)
				p++;
		}
		frenew[24]+=frenew[25];
		int[] arrdata=new int[25];
		int count=0;
		int[] freq=new int[25];
		for(int i=0;i<25;i++)
		{
			if(frenew[i]!=0)
			{
				arrdata[count]=i*10+5;
				freq[count++]=frenew[i];
			}
		}
		Heaps obj=new Heaps(k+1,257);
		Heaps obj1=new Heaps(count+1,257);
		int pointer=1; int pointer1=1;
		for(int i=0;i<k;i++)
		{
			obj.array[pointer]=new node(nonzero[i],freqarray[nonzero[i]]);
			pointer++;
		}
		for(int i=0;i<count;i++)
		{
			obj1.array[pointer1]=new node(arrdata[i],freq[i]);
			pointer1++;
		}
		obj.heapify(); obj1.heapify();
		
		int j=k; int l=count;
	    while(j>1)
	    {
	    	node TobeInserted;
	    	node temp1,temp2;
	    	if(obj.size!=3)
	    	{temp1=obj.delete();
	    	obj.heapify();
	    	temp2=obj.delete();
	    	obj.heapify();
	    	
	      int frequency=temp1.frequency+temp2.frequency;
	       TobeInserted=new node(frequency,temp2,temp1);}
	    	else
	    		 {
	    		int sum=obj.array[1].frequency+obj.array[2].frequency;
	    		node t1=obj.array[1];
	    		node t2=obj.array[2];
	    		TobeInserted=new node(sum,t2,t1);
	    		 }
	      
	      obj.insert(TobeInserted);
	      if(j==2)
	      {
	    	  obj.delete();
	    	  obj.heapify();
	    	  obj.delete();
	      }
	      j--; 
	    }
	    while(l>1)
	    {
	    	node TobeInserted;
	    	node temp1,temp2;
	    	if(obj1.size!=3)
	    	{temp1=obj1.delete();
	    	obj1.heapify();
	    	temp2=obj1.delete();
	    	obj1.heapify();
	    	
	      int frequency=temp1.frequency+temp2.frequency;
	       TobeInserted=new node(frequency,temp2,temp1);}
	    	else
	    		 {
	    		int sum=obj1.array[1].frequency+obj1.array[2].frequency;
	    		node t1=obj1.array[1];
	    		node t2=obj1.array[2];
	    		TobeInserted=new node(sum,t2,t1);
	    		 }
	      
	      obj1.insert(TobeInserted);
	      if(l==2)
	      {
	    	  obj1.delete();
	    	  obj1.heapify();
	    	  obj1.delete();
	      }
	      l--;
	      
	    }
	    String o=""; String h="";
	    if(obj.array[1].left==null && obj.array[1].right==null)
	    {
	    	obj.bits=obj.array[1].frequency;
	    }
	    else
	    	{
	    	obj.generate(obj.array[1],o);
	    	}
	    int scale = (int) Math.pow(10,1);
	    double a1 =(double)divider/obj.bits;
	    double a=(double) Math.round(a1*scale)/scale;
	    System.out.println(a);
	    if(obj1.array[1].left==null && obj1.array[1].right==null)
	    {
	    	obj1.bits=obj1.array[1].frequency;
	    }
	    else
	    {
	    	{
		    	obj1.generate(obj1.array[1],h);
		    }	
	    }
	    double a2 =(double)divider/obj1.bits;
	    double b=(double) Math.round(a2*scale)/scale;
	    System.out.println(b);
	}
}


