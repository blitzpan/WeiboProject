import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Test {
	public void fun(){
		try {
			String url = "http://vip.stock.finance.sina.com.cn/mkt/#sge_gold";
			Document doc = Jsoup.connect(url).get();
			System.out.println(doc.text());
			System.out.println("--------------------------------------------");
			if(doc.text().contains("tbl_wrap")){
				System.out.println("contains");
			}
			Elements newsHeadlines = doc.select("#tbl_wrap");
			System.out.println(newsHeadlines.text());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Test t = new Test();
		t.fun();

	}

}
