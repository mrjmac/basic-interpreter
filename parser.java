import java.util.StringTokenizer;
import java.util.TreeMap;

public class parser {

    public static String currToken;
    public static TreeMap<String, Integer> vars;
    public static StringTokenizer tokens;

    public static int parse(StringTokenizer more, String curr, TreeMap<String, Integer> var)
    {
        currToken = curr;
        vars = var;
        tokens = more;

        return equals();
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
            int ans = equals();
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
