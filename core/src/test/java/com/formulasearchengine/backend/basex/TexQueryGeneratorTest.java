package com.formulasearchengine.backend.basex;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TexQueryGeneratorTest {

	@Test
	public void testQuery () throws Exception {
		final TexQueryGenerator t = new TexQueryGenerator();
		final String mml = "<math xmlns=\"http://www.w3.org/1998/Math/MathML\" id=\"p1.m1\" class=\"ltx_Math\" alttext=\"E=mc^{2}\" display=\"inline\">\n" +
			"  <apply>\n" +
			"    <eq/>\n" +
			"    <ci>E</ci>\n" +
			"    <apply>\n" +
			"      <times/>\n" +
			"      <ci>m</ci>\n" +
			"      <apply>\n" +
			"        <csymbol cd=\"ambiguous\">superscript</csymbol>\n" +
			"        <ci>c</ci>\n" +
			"        <cn type=\"integer\">2</cn>\n" +
			"      </apply>\n" +
			"    </apply>\n" +
			"  </apply>\n" +
			"</math>";
		assertEquals( mml, t.request( "E=mc^2" ) );
		assertEquals( 0, t.getOb().get("status_code") );
		assertEquals( "No obvious problems", t.getOb().get("status") );
		assertTrue( t.isSuccess() );

	}

	@Test
	public void testSen () throws Exception {
		TexQueryGenerator t = new TexQueryGenerator();
		final String withoutTexvc = "<math xmlns=\"http://www.w3.org/1998/Math/MathML\" id=\"p1.m1\" class=\"ltx_Math\" alttext=\"\\sen\" display=\"inline\">\n" +
			"  <mtext>\\sen</mtext>\n" +
			"</math>";
		final String withTexv = "<math xmlns=\"http://www.w3.org/1998/Math/MathML\" id=\"p1.m1\" class=\"ltx_Math\" alttext=\"\\sen\" display=\"inline\">\n" +
			"  <sin/>\n" +
			"</math>";
		List<NameValuePair> p = t.getParams();
		p.remove( new BasicNameValuePair( "preload", "texvc" ) );
		t.setParams( p );
		assertEquals( withoutTexvc, t.request( "\\sen" ) );
		assertEquals( "2", t.getOb().get( "status_code" ) );
		assertFalse( t.isSuccess() );
		t = new TexQueryGenerator();
		assertEquals( withTexv, t.request( "\\sen" )  );

	}

	@Test
	public void testEmpty() throws Exception {
		TexQueryGenerator t = new TexQueryGenerator();
		List<NameValuePair> p = t.getParams();
		p.clear();
		t.setParams( p );
		assertEquals( "", t.request( "" ) );
		assertEquals( 3, t.getOb().get( "status_code" ) );
		assertFalse( t.isSuccess() );
		p.add( new BasicNameValuePair( "destroy", "LaTeXML" )  );
		t.setParams( p );
		assertEquals( "", t.request( "" ) );
		assertEquals( 3, t.getOb().get( "status_code" ) );
		assertNull(t.getLastException());
	}

	@Test
	public void testErrorHandling () throws Exception {
		final TexQueryGenerator t = new TexQueryGenerator();
		t.setLaTeXMLURL( "http://example.com" );
		assertEquals( "http://example.com", t.getLaTeXMLURL() );
		assertEquals( "", t.request( "E=mc^2" ) );
		assertEquals( 4, t.getOb().get( "status_code" ) );
		assertEquals( "com.fasterxml.jackson.core.JsonParseException",
			t.getLastException().getClass().getCanonicalName() );
		t.setLaTeXMLURL( "invalid" );
		assertEquals( "", t.request( "E=mc^2" ) );
		assertEquals( "org.apache.http.client.ClientProtocolException",
			t.getLastException().getClass().getCanonicalName() );
	}

}