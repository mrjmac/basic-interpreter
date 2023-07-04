/*
    variable assignment (ints only)
    operators
    print
 */

import java.io.*;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.ArrayDeque;
import java.util.Scanner;

public class interpreter {

    public static void main(String args[]) throws IOException
    {
        Scanner r = new Scanner(new FileReader("code.in"));
        PrintWriter pw = new PrintWriter(System.out);
        String raw;
        StringTokenizer st; //read input line by line

        vars = new TreeMap<>();

        while (r.hasNext())
        {

            raw = r.nextLine();
            st = new StringTokenizer(raw);

            String token = st.nextToken();
            // we can do two things, print and assign. If we print, the first word in a line will be print
            if (token.equals("print"))
            {
                tokens = new StringTokenizer(raw.substring(6));
                currToken = tokens.nextToken();
                    
                pw.println(expression());

            }
            else
            {
                String name = token;
                //TODO: name can't start with a number and can't be "END"
                token = st.nextToken();
                if (!token.equals("="))
                {
                    pw.println("Syntax error");
                    System.exit(0);
                }
                else
                {
                    tokens = new StringTokenizer(raw.substring(name.length() + 3));
                    currToken = tokens.nextToken();
                    int num = expression();
                    vars.put(name, num);
                }
            }
            
        }

        pw.close();
        r.close();
    }

    public static String currToken;
    public static TreeMap<String, Integer> vars;
    public static StringTokenizer tokens;

    public static int expression()
    {
        
        return 0;
    }

    public static int add()
    {
        

        return 0;
    }

    public static int multi()
    {
        

        return 0;
    }

    public static int parse()
    {
        return 0;
    }
    

    public static boolean isNum(String token)
    {
        if (vars.get(token) != null)
        {
            currToken = vars.get(token) + "";
            return true;
        }
        else
        {
            try 
            { 
                Integer.parseInt(token); 
            } 
            catch(NumberFormatException e) 
            { 
                return false; 
            } 
            catch(NullPointerException e) 
            {
                return false;
            }
            return true;
        }
    }
}