package com.bouncetoacure.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.UUID;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import android.util.Log;

public class HttpWebService {
	
    public static final int HTTP_TIMEOUT = 10000;
    private static int BUFFER_SIZE = 8000;
    private static HttpClient httpWebService;

    // have single instance of client
    private static HttpClient getHttpClient() {
        if (httpWebService == null) {
            httpWebService = new DefaultHttpClient();
            final HttpParams params = httpWebService.getParams();
            HttpConnectionParams.setConnectionTimeout(params, HTTP_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, HTTP_TIMEOUT);
            ConnManagerParams.setTimeout(params, HTTP_TIMEOUT);
        }
        return httpWebService;
    }

    // perform http POST to the specified URL with the specified params
    public static String executeHttpPost(String url, ArrayList<NameValuePair> postParameters) throws Exception {
    	// allows multiple users to access same URLs
    	url = url + "?unused=" + UUID.randomUUID();
    	BufferedReader in = null;
    	
    	// initialize the client and send the command
        try {
            HttpClient client = getHttpClient();
            HttpPost request = new HttpPost(url);
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
            request.setEntity(formEntity);
            HttpResponse response = client.execute(request);
            
            // read the response
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()), BUFFER_SIZE);
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();

            String result = sb.toString();
            
            // return the response
            return result;
        } finally {
        	// always close the reader stream
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // perform http GET to the specified URL with the specified params
    public static String executeHttpGet(String url) throws Exception {
    	// allows multiple users to access same URLs
    	url = url + "?unused=" + UUID.randomUUID();
    	
        StringBuilder builder = new StringBuilder();
        HttpClient client = getHttpClient();
        HttpGet request = new HttpGet();
        request.setURI(new URI(url));
        
        // initialize the client and send the command
        try {            
            HttpResponse response = client.execute(request);
            StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
            
			// process the HTTP_OK response
			if (statusCode == 200) {
				Log.i(HttpWebService.class.toString(), "System Status OK");
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content), BUFFER_SIZE);
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				Log.e(HttpWebService.class.toString(), "Failed HTTP GET");
			} 
        } catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        // return the response
		return builder.toString();            
    }
}
