import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.fs.Path;
import java.util.*;
import java.io.*;

public class WC
{
//permutation class


 public static class Permutation {
 
   int[][] d=new int[10][10];
   int cnt=0;
   public void combinationUtil(int arr[], int n, int r, int index,
                                int data[], int i)
    {
    
        if (index == r)
        {
            for (int j=0; j<r; j++)
            {
               
               d[cnt][j]=data[j];
               
            }
            cnt++;
            System.out.println("");
        return;
        }
 
        // When no more elements are there to put in data[]
        if (i >= n)
        return;
 
        // current is included, put next at next location
        data[index] = arr[i];
        combinationUtil(arr, n, r, index+1, data, i+1);
 
        // current is excluded, replace it with next (Note that
        // i+1 is passed, but index is not changed)
        combinationUtil(arr, n, r, index, data, i+1);
    }
 
    // The main function that prints all combinations of size r
    // in arr[] of size n. This function mainly uses combinationUtil()
    public int[][] printCombination(int arr[], int n, int r)
    {
        // A temporary array to store all combination one by one
        int data[]=new int[r];
 
        // Print all combination using temprary array 'data[]'
        combinationUtil(arr, n, r, 0, data, 0);
        int rows=0;
        for(int i=0;i<d.length;i++)
        {
            if(d[i][0]!=0)
                rows++;
        }
        int[][] temp=new int[rows][r];
        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<r;j++)
                temp[i][j]=d[i][j];
        }
        return temp;
    }
 
    
}




//permutation class






	public static class MyMapper extends MapReduceBase implements Mapper<LongWritable,Text,Text,IntWritable>
	{
		public void map(LongWritable key,Text value,OutputCollector<Text,IntWritable> out,Reporter r) throws IOException
		{

			String str=value.toString();
			//String[] arr=str.split(" ");
//my function

  String[] inp1=str.split(" ");
       int[] arr1=new int[inp1.length];
       for(int i=0;i<inp1.length;i++)
           arr1[i]=Integer.parseInt(inp1[i]);
       
       
       int[][] oo=null;
       String temp="";
       for(int i=1;i<=inp1.length;i++)
        {
            Permutation ob=new Permutation();
            oo=ob.printCombination(arr1,arr1.length,i);
           for(int j=0;j<oo.length;j++)
           {
              
               for(int k=0;k<oo[j].length;k++)
               {
                   temp+=String.valueOf(oo[j][k]);
               
               }
               //collect
               System.out.println(temp);
               temp="";
           }
           
           oo=null;
       }





//my function
			for(int i=0;i<inp1.length;i++)
			{
				out.collect(new Text(inp1[i]),new IntWritable(1));
			}
		
		}
	}




	
	public static class MyReducer extends MapReduceBase implements Reducer<Text,IntWritable,Text,IntWritable>
	{
		public void reduce(Text key, Iterator<IntWritable> abc,OutputCollector<Text,IntWritable> out, Reporter r) throws IOException
		{
			int sum=0;
			int x;
			while(abc.hasNext())
			{
				sum+=abc.next().get();
			}


	//supp count	if(sum>=2)
				out.collect(key, new IntWritable(sum));
		}

	}
	public static void main(String[] args) throws Exception
	{
		JobConf conf=new JobConf(WC.class);
		conf.setMapperClass(MyMapper.class);
		conf.setReducerClass(MyReducer.class);
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(IntWritable.class);
		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);
		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));
		JobClient.runJob(conf);
		
	}

}
