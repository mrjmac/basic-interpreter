/*
    variable assignment (ints only)
    operators
    print

    TODO: make recursive to handle nested ifs
 */

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringTokenizer;
import java.util.TreeMap;


public class interpreter {

    public static void main(String args[]) throws IOException
    {   
        StringTokenizer st = new StringTokenizer(Files.readString(Path.of("code.in"))); // break input into tokens

        vars = new TreeMap<>();

        while (st.hasMoreTokens())
        {
            String token = st.nextToken();
            
            // the only multiline operation is an if, otherwise we can just handle the line seperately
            if (token.equals("if"))
            {
                String parse = "";
                while (!token.equals(")"))
                {
                    token = st.nextToken();
                    parse += token + " ";
                }

                tokens = new StringTokenizer(parse);
                currToken = tokens.nextToken();

                int valid = equals();
                
                if (valid != 0)
                {
                    while (!token.equals("}"))
                    {

                    }
                    
                }
                else
                {
                    while (!token.equals("}"))
                    {

                    }
                }
                
                
            }
            else
            {
                String parse = token + " ";
                while (!token.equals(";"))
                {
                    token = st.nextToken();
                    parse += token + " ";
                }

                tokens = new StringTokenizer(parse);
                currToken = tokens.nextToken();

                handleLine();
            }
            
        }
    }

    

    // turn into args
    public static String currToken;
    public static TreeMap<String, Integer> vars;
    public static StringTokenizer tokens;

    public static void handleBracket()
    {

    }
    
    public static void handleLine()
    {
        if (currToken.equals("print"))
        {
            String parse = "";
            while (!currToken.equals(";"))
            {
                currToken = tokens.nextToken();
                parse += currToken + " ";
            }

            tokens = new StringTokenizer(parse);
            currToken = tokens.nextToken();
                    
            System.out.println(equals());

        }
        else
        {
            String name = currToken;

            if (isNum(name.charAt(0) + ""))
            {
                System.out.println("Syntax error");
                System.exit(0);
            }

            currToken = tokens.nextToken();

            if (!currToken.equals("="))
            {
                System.out.println("Syntax error");
                System.exit(0);
            }
            else
            {
                String parse = "";
                while (!currToken.equals(";"))
                {
                    currToken = tokens.nextToken();
                    parse += currToken + " ";
                }

                tokens = new StringTokenizer(parse);
                currToken = tokens.nextToken();

                int num = equals();
                vars.put(name, num);
            }
        }
    }

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
