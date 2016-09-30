package p;

import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.net.URL;
import java.util.regex.*;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws Exception {
        String urlContent = getUrlContent("http://www.ccib.ro/ro/CCIB/4/486/452/lista+firme+a.html");
        String pattern  = "(<hr />|(?<=<hr />)).*?(<hr />|</div>)";
        Pattern hrPattern = Pattern.compile(pattern, Pattern.MULTILINE | Pattern.DOTALL);
        Matcher m = hrPattern.matcher(urlContent);

        while(m.find()) {
            System.out.println("...");
            System.out.println("...");
            System.out.println(urlContent.substring(m.start(), m.end()));
        }
    }

    public static String getUrlContent(String url) throws Exception {
        StringBuilder sb = new StringBuilder();
        URL ccib = new URL(url);
        BufferedReader in = new BufferedReader(new InputStreamReader(ccib.openStream()));

        String line;
        while((line = in.readLine()) != null) sb.append(line);

        in.close();

        return sb.toString();
    }
}
