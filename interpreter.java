/*
    variable assignment (ints only)
    operators
    print
 */

import java.io.*;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Arrays;
import java.util.Scanner;

public class interpreter {
    public static void main(String args[]) throws IOException
    {
        Scanner r = new Scanner(new FileReader("code.in"));
        PrintWriter pw = new PrintWriter(System.out);
        String raw;
        StringTokenizer st; //read input line by line

        TreeMap<String, Integer> vars = new TreeMap<>();

        while (r.hasNext())
        {

            raw = r.nextLine();
            st = new StringTokenizer(raw);

            String token = st.nextToken();
            // we can do two things, print and assign. If we print, the first word in a line will be print
            if (token.equals("print"))
            {
                if (raw.indexOf(".") != (raw.length() - 1) || raw.indexOf(".") == -1)
                {
                    pw.println("Syntax error");
                    break;
                }
                else
                {
                    pw.println(handleOps(vars, raw.substring(6, raw.length())));
                }
            }
            else
            {
                String name = token;
                token = st.nextToken();
                if (!token.equals("="))
                {
                    pw.println("Syntax error");
                    break;
                }
                else
                {
                    int num = handleOps(vars, raw.substring(name.length() + 3, raw.length()));
                    vars.put(name, num);
                }
            }
            
        }

        pw.close();
        r.close();
    }

    public static int handleOps(TreeMap<String, Integer> vars, String text)
    {
        System.out.println(text);
        return 0;
    }
}