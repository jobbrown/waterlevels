package com.jobbrown.rmc.alerts;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MobileAlert implements Alert {

	/**
	 * API Base URL
	 */
	private String baseURL = "https://api.txtlocal.com/send/?";
	
	/**
	 * API Credentials
	 */
	private String username = "rs@jobbrown.com";
	private String hash = "77c6f74e0c199f122938543d5580a0ca5f5ba61f";
	
	/**
	 * Who the message should come from. Limited to 11 chars
	 */
	private String from = "FloodAlerts";
	
	@Override
	public void alert(String alertable, String message) {
		if(this.validates(alertable)) {
			// Build the URL
			String urlString = this.baseURL + getQueryString(alertable, message);
			
			URLConnection connection;
			try {
				connection = new URL(urlString).openConnection();
				InputStream response = connection.getInputStream();
				
				System.out.println("API returned " + response);
			} catch (IOException e) {
				e.printStackTrace();
			}	
		} else {
			System.out.println("I tried to validate it - it failed");
		}
	}
	
	/**
	 * Check if passed string validates as a mobile number
	 * @param alertable the mobile number
	 * @return boolean whether or not its valid
	 */
	@Override
	public boolean validates(String alertable)
	{
		if(alertable.matches("-?\\d+(\\.\\d+)?")) {
			if(alertable.substring(0, 2).equals("07")) {
				return (alertable.length() == 11);
			} else {
				System.out.println("Numbers should start with 07");
				return false;
			}
		} else {
			System.out.println("Not numeric");
			return false;
		}
	}
	
	/**
	 * Builds up a query string for sending a text message
	 * @param number the number for sending to
	 * @param message the message to send
	 * @return String query string for use with base url
	 */
	private String getQueryString(String number, String message)
	{
		String queryString = "username=" + encode(this.username);
		queryString += "&hash=" + encode(this.hash);
		queryString += "&numbers=" + encode(number);
		queryString += "&sender=" + encode(this.from);
		queryString += "&message=" + encode(message);
		
		
		return queryString;
	}

	/**
	 * Encode a passed string ready for inclusion in a url
	 * @param enc the string
	 * @return encoded string
	 */
	private String encode(String enc)
	{
		try {
			return URLEncoder.encode(enc, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("Error encoding string");
			return "";
		}
	}
}
