package sample.Board;

import java.io.*;
import java.util.StringTokenizer;

public class FastReader {
    BufferedReader br;
    StringTokenizer st;
    String file;

    public FastReader()
    {
        file = "C:\\Users\\3arrows\\IdeaProjects\\cheesGamePlay\\src\\sample\\Gui\\chessSave\\chess.txt";
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    String next()
    {
        while (st == null || !st.hasMoreElements())
        {
            try
            {
                st = new StringTokenizer(br.readLine());
            }
            catch (IOException  e)
            {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }

    int nextInt()
    {
        return Integer.parseInt(next());
    }

    long nextLong()
    {
        return Long.parseLong(next());
    }

    double nextDouble()
    {
        return Double.parseDouble(next());
    }

    String nextLine()
    {
        String str = "";
        try
        {
            str = br.readLine();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return str;
    }
}
