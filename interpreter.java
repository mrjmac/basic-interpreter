/*
    variable assignment (ints only)
    operators
    print
 */

import java.io.*;
import java.util.StringTokenizer;
import java.util.TreeMap;
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
            // we can do two things, print and assign.
            if (token.equals("print"))
            {
                tokens = new StringTokenizer(raw.substring(6));
                currToken = tokens.nextToken();
                    
                pw.println(equals());

            }
            else if (token.equals("if"))
            {
                tokens = new StringTokenizer(raw.substring(3));
                currToken = tokens.nextToken();

                int valid = equals();

                if (valid == 0)
                {
                    r.nextLine();
                }
            }
            else
            {
                String name = token;

                if (isNum(name.charAt(0) + ""))
                {
                    pw.println("Syntax error");
                    System.exit(0);
                }

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
                    int num = equals();
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

    public static int equals()
    {
        int left = or();

        while (currToken.equals("=="))
        {
            currToken = tokens.nextToken();
            int right = or();

            if (left != right)
            {
                left = 0;

            }
            else
            {
                if (left == 0)
                {
                    left = 1;
                }
            }
        }

        return left;
    }

    public static int or()
    {
        int left = xor();

        while (currToken.equals("|"))
        {
            currToken = tokens.nextToken();
            left = left | xor();
        }

        return left;
    }

    public static int xor()
    {
        int left = and();

        while (currToken.equals("^"))
        {
            currToken = tokens.nextToken();
            left = left ^ and();
        }

        return left;
    }
    
    public static int and()
    {
        int left = shift();

        while (currToken.equals("&"))
        {
            currToken = tokens.nextToken();
            left = left & shift();
        }

        return left;
    }

    public static int shift()
    {
        int left = add();

        while (currToken.equals(">>") || currToken.equals("<<"))
        {
            if (currToken.equals(">>"))
            {
                currToken = tokens.nextToken();
                left = left >> add();
            }
            else
            {
                currToken = tokens.nextToken();
                left = left << add();
            }
            
        }

        return left;
    }

    public static int add()
    {
        int left = multi();

        while (currToken.equals("+") || currToken.equals("-"))
        {
            if (currToken.equals("+"))
            {
                currToken = tokens.nextToken();
                left += multi();
            }
            else
            {
                currToken = tokens.nextToken();
                left -= multi();
            }
            
        }

        return left;
    }

    public static int multi()
    {
        int left = parse();
        while (currToken.equals("*") || currToken.equals("/"))
        {
            if (currToken.equals("*"))
            {
                currToken = tokens.nextToken();
                left *= parse();
            }
            else
            {
                currToken = tokens.nextToken();
                left /= parse();
            }
        }

        return left;
    }

    public static int parse()
    {
        if (currToken.equals("("))
        {
            currToken = tokens.nextToken();
            int ans = add();
            if (tokens.hasMoreElements())
            {
                currToken = tokens.nextToken();
            }
            return ans;
        }
        else if (isNum(currToken))
        {
            int temp = Integer.parseInt(currToken);
            if (tokens.hasMoreElements())
            {
                currToken = tokens.nextToken();
            }
            return temp;
        }
        else if (vars.get(currToken) != null)
        {
            int temp = vars.get(currToken);
            if (tokens.hasMoreElements())
            {
                currToken = tokens.nextToken();
            }
            return temp;
        }
        else
        {
            System.out.println("Syntax Error");
            System.exit(0);
        }

        return 0;
    }
    

    public static boolean isNum(String token)
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