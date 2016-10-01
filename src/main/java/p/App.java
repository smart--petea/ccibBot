package p;

import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.net.URL;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App
{
    private static Pattern hrefPattern = Pattern.compile("p.*strong.*<a href=\"(?<href>.*?)\"");
    public static String getHref(String content) {
        Matcher matcherHref = hrefPattern.matcher(content);

        if(matcherHref.find()) return matcherHref.group("href");
        return "";
    }

    public static void main( String[] args ) throws Exception {
        //String urlContent = getUrlContent("http://www.ccib.ro/ro/CCIB/4/486/452/lista+firme+a.html");
        //getCompanies(urlContent);
        String company = "<p><span style=\"font-size: small;\"><strong><a href=\"http://www.axmgroup.ro\">AXM PROD'93 SRL</a></strong></span></p><p>Produce: grunduri; lacuri; vopsele, inclusiv o gama de vopsele diluabile cu apa utilizate la finisarea lemnului expus la interior si exterior.&nbsp;<em>&nbsp;</em></p><p><em>tel: 021.316.07.09<br /></em><em>e-mail: <span style=\"text-decoration: underline;\"><a href=\"mailto:office@axmgroup.ro;\">office@axmgroup.ro</a></span>;</em><em>&nbsp;<br /></em><em><span style=\"text-decoration: underline;\"><a href=\"http://www.axmgroup.ro/\">www.axmgroup.ro</a></span></em><em>&nbsp;<br /></em></p><hr />";
        System.out.println("href = " + getHref(company));

        //String title = matcherHref.group("title");
        //System.out.println("title : " + title);
        /*

        Pattern telPattern = Pattern.compile("tel: (.*?)<");
        Matcher telMatcher = telPattern.matcher(company);

        String phones = "";
        if(telMatcher.find()) phones = telMatcher.group(1); System.out.println("phones " + phones);

        Pattern emailPattern = Pattern.compile("mailto:(.*?)\"");
        Matcher emailMatcher = emailPattern.matcher(company);
        String mails = "";
        while(emailMatcher.find()) mails = mails + emailMatcher.group(1);
        System.out.println("mails " + mails);
        */

        /*
        if(matcherHref.find()) {
            System.out.println(matcherHref.group(0));
            System.out.println(matcherHref.group(1));
            System.out.println(matcherHref.group(2));
            System.out.println(matcherHref.group(3));
        } else {
            System.out.println("not found");
        }
        */
    }

    /*
    public static List<String> getListaFirme(String content) {
        ArrayList<String> lst = new ArrayList<String>(26);
        Pattern contiguosList = Pattern.compile("<ul class=.*</ul>", Pattern.MULTILINE | Pattern.DOTALL);
        Matcher contiguosMatcher = contiguosList.matcher(content);
        contiguousMatcher.find();
        System.out.println(contiguousMatcher.group(0));

        return lst;
    }
    */

    public static void getCompanies(String content) {
        String pattern  = "(<hr />|(?<=<hr />)).*?(<hr />|</div>)";
        Pattern hrPattern = Pattern.compile(pattern, Pattern.MULTILINE | Pattern.DOTALL);
        Matcher m = hrPattern.matcher(content);

        while(m.find()) {
            System.out.println("...");
            System.out.println("...");
            System.out.println(content.substring(m.start(), m.end()));
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
