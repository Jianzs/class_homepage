package top.zhengsj.klass.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class FetchUtil {
    public static Document getHtml(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    public static void main(String[] args) {
        try {
            Document document = Jsoup.connect("http://yingkebao.top/f/5abe5686bb7c7c74a76b70c0").get();
            Elements form = document.getElementsByTag("form");
            Elements style = document.getElementsByTag("style");
            String html = form.html();
            System.out.println(style.html());
            System.out.println(html);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
