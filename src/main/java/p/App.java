package p;

import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.net.URL;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
import java.util.LinkedList;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * Hello world!
 *
 */
public class App
{
    //patterns
    private static Pattern hrefPattern = Pattern.compile("p.*?strong.*?<a href=\"(?<href>.*?)\"");
    private static Pattern titlePattern = Pattern.compile("p.*?strong.*?>(?<title>[^>]+?)</");
    private static Pattern phonesPattern = Pattern.compile("tel: (?<phones>.*?)<");
    private static Pattern emailPattern = Pattern.compile("mailto:(?<mail>.*?)\"");
    private static Pattern firmaPattern = Pattern.compile("href=\"(?<firma>.*?)\"");

    public static String getHref(String content) {
        Matcher matcherHref = hrefPattern.matcher(content);

        if(matcherHref.find()) return matcherHref.group("href");
        return "";
    }

    public static String getTitle(String content) {
        Matcher matcherTitle = titlePattern.matcher(content);

        if(matcherTitle.find()) return matcherTitle.group("title");
        return "";
    }

    public static String getPhones(String content) {
        Matcher matcherPhones = phonesPattern.matcher(content);

        if(matcherPhones.find()) return matcherPhones.group("phones");
        return "";
    }

    public static String getMails(String content) {
        Matcher matcherEmail = emailPattern.matcher(content);

        String mails = "";
        while(matcherEmail.find()) mails = mails + matcherEmail.group("mail");

        return mails;
    }


    public static void main( String[] args ) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bot");
        EntityManager em = emf.createEntityManager();

        String aUrl = "http://www.ccib.ro/ro/CCIB/4/486/452/lista+firme+a.html";
        processUrl(aUrl, em);
        List<String> firmeList = getListaFirme(aUrl);
        for(String firmaUrl : firmeList) {
            processUrl(firmaUrl, em);
        }

        em.close();
        emf.close();
    }

    public static void processUrl(String url, EntityManager em) throws Exception {
        System.out.println("Processing - " + url);

        em.getTransaction().begin();

        for(String company: getCompanies(getUrlContent(url))) {
            //String company = "<p><span style=\"font-size: small;\"><strong><a href=\"http://www.axmgroup.ro\">AXM PROD'93 SRL</a></strong></span></p><p>Produce: grunduri; lacuri; vopsele, inclusiv o gama de vopsele diluabile cu apa utilizate la finisarea lemnului expus la interior si exterior.&nbsp;<em>&nbsp;</em></p><p><em>tel: 021.316.07.09<br /></em><em>e-mail: <span style=\"text-decoration: underline;\"><a href=\"mailto:office@axmgroup.ro;\">office@axmgroup.ro</a></span>;</em><em>&nbsp;<br /></em><em><span style=\"text-decoration: underline;\"><a href=\"http://www.axmgroup.ro/\">www.axmgroup.ro</a></span></em><em>&nbsp;<br /></em></p><hr />";
            System.out.println("href = " + getHref(company));
            System.out.println("title = " + getTitle(company));
            System.out.println("phones = " + getPhones(company));
            System.out.println("mails = " + getMails(company));
        }

        em.getTransaction().commit();
    }

    public static List<String> getListaFirme(String url) throws Exception {
        String content = getUrlContent(url);
        LinkedList<String> lstt = new LinkedList<String>();

        Pattern contiguosList = Pattern.compile("<ul class=(?<firme>.*?)</ul>", Pattern.MULTILINE | Pattern.DOTALL);
        Matcher contiguosMatcher = contiguosList.matcher(content);
        contiguosMatcher.find();

        Matcher firmaMatcher = firmaPattern.matcher(contiguosMatcher.group("firme"));
        while(firmaMatcher.find()) {
            lstt.push(firmaMatcher.group("firma"));
        }

        return lstt;
    }

    public static List<String> getCompanies(String content) {
        LinkedList<String> lstt = new LinkedList<String>();
        String pattern  = "(?<company>(<hr />|(?<=<hr />)).*?(<hr />|</div>))";
        Pattern hrPattern = Pattern.compile(pattern, Pattern.MULTILINE | Pattern.DOTALL);
        Matcher m = hrPattern.matcher(content);

        while(m.find()) {
            lstt.push(m.group("company"));
        }

        return lstt;
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
