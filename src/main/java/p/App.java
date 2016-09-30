package p;

import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.net.URL;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws Exception {
        System.out.println(getUrl("http://www.ccib.ro/ro/CCIB/4/486/452/lista+firme+a.html"));
    }

    public static String getUrl(String url) throws Exception {
        StringBuilder sb = new StringBuilder();
        URL ccib = new URL(url);
        BufferedReader in = new BufferedReader(new InputStreamReader(ccib.openStream()));

        String line;
        while((line = in.readLine()) != null) sb.append(line);

        in.close();

        return sb.toString();
    }
}
