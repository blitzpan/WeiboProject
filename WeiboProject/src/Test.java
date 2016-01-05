import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Test {
	public String fun(){
		StringBuilder sb = new StringBuilder("");
		BufferedReader br = null;
		try {
			String url = "http://hq.sinajs.cn/rn=9pl6s&list=SGE_AU99_99";
			URL realurl = new URL(url);
			URLConnection conn = realurl.openConnection();
			conn.connect();
			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String tempStr = "";
			while((tempStr = br.readLine()) != null){
				sb.append(tempStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
	public static void main(String[] args) {
		Test t = new Test();
		System.out.println(t.fun());

	}

}
