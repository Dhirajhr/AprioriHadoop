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