package com.formulasearchengine.backend.basex.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.formulasearchengine.backend.basex.Client;
import com.formulasearchengine.backend.basex.XMLHelper;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by Moritz on 14.03.2015.
 */
public class MathRequest {
	String type = "";
	String query = "";
	String response = "";

	public String getType () {
		return type;
	}

	public String getQuery () {
		return query;
	}

	public String getResponse () {
		return response;
	}

	boolean success = false;
	public MathRequest(String query){
		this.query = query;
	}

	public MathRequest (String type, String query, String response) {
		this.type = type;
		this.query = query;
		this.response = response;
	}

	public MathRequest () {
	}

	public MathRequest setType (final String type) {
		this.type = type;
		return this;
	}

	public MathRequest setQuery (final String query) {
		this.query = query;
		return this;
	}

	public MathRequest setResponse (final String response) {
		this.response = response;
		return this;
	}

	@JsonIgnore
	public MathRequest run () {
		if (type == null || type == "" ){
			type = "mws";
		}
		Client client = new Client();
		client.setShowTime( false ); //for testing
		switch ( type ){
			case "tex" :
				response = client.runTexQuery( query );
				success = true;
				break;
			case "xquery" :
				response = client.runXQuery( query );
				success =true;
				break;
			default:
				try {
					Document doc = XMLHelper.String2Doc( query );
					response = client.runMWSQuery( doc );
					success =true;
				} catch ( ParserConfigurationException | NullPointerException | IOException e ) {
					e.printStackTrace();
					response = "No valid XML Document retrieved.";
					success = false;
				}

		}
		return this;
	}
	public boolean isSuccess () {
		return success;
	}
}

/* 		String result = "";
		try {
			Client client = new Client();
			result = client.runXQuery( query );
		} catch ( Exception e ) {

		}
		if ( result.length() == 0 ) {
			result = "no data retrieved";
		}
		return result; */
