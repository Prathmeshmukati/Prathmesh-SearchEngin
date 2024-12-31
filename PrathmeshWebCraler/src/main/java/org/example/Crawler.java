package org.example;

import java.io.IOException;
import java.util.HashSet;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class Crawler {
    HashSet<String> urlset;
    int MAX_DEPTH = 2;
    Crawler(){
        urlset=new HashSet<>();
    }
    public void getPageTextandLinks(String url , int depth ){
        if(urlset.contains(url)){
            return;
        }
        if(depth>MAX_DEPTH){
            return ;
        }
        depth++;
        try {
            Document document = Jsoup.connect(url).timeout(5000).get();


            /* indexer work start from here java object save in database */
            Indexer indexer = new Indexer(document,url);
            System.out.println(document.title());
            Elements availableLinksonPage = document.select("a[href]");
            for (Element currentlink : availableLinksonPage) {
                getPageTextandLinks(currentlink.attr("abs:href"), depth);
            }
        }
        catch(IOException ioException){
            ioException.printStackTrace();

        }
    }
    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        crawler.getPageTextandLinks("https://www.geeksforgeeks.org/",1);
    }
}