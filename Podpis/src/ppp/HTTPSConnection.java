package ppp;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.net.URL;

//import javax.net.ssl.HttpsURLConnection;
//
//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.*;
//import org.apache.commons.io.IOUtils;

public class HTTPSConnection {
	
	String komunikatHTTPS = "";
	
	public String getKomunikatHTTPS() {
		return komunikatHTTPS;
	}

	public void setKomunikatHTTPS(String komunikatHTTPS) {
		this.komunikatHTTPS = komunikatHTTPS;
	}

	public void conTest1(String nazwaPliku) {
	
		try {
// XXXXXXXXXXXXXXXXXXXXXX ZMIENIÆ LINK XXXXXXXXXXXXXXXXXXXXXX
			URL url = new URL("https://linkdoroboty/sign_local.php?file_name="+ nazwaPliku);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			System.out.println("Odpowiedz z HTTPS: " + connection.getResponseMessage());
			//System.out.println("Odpowiedz z 1: " + connection.getContentEncoding());
			InputStream inputStream = connection.getInputStream();
			InputStreamReader isReader = new InputStreamReader(inputStream);
			BufferedReader reader = new BufferedReader(isReader);
		      StringBuffer sb = new StringBuffer();
		      String str;
		      while((str = reader.readLine())!= null){
		         sb.append(str);
		      }
		      System.out.println(sb.toString());

			setKomunikatHTTPS(""+sb);

//		} catch (JsonParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}

