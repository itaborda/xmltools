/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.d4n.oxm.core;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * 
 * @author isaac
 */
public class W3CNodeHolderBuilderFactory implements NodeHolderBuilderFactory {

	public NodeHolderBuilder newBuilder(InputStream is) throws Exception {

		DocumentBuilderFactory newInstance = DocumentBuilderFactory.newInstance();
		DocumentBuilder newDocumentBuilder = newInstance.newDocumentBuilder();

		return new NodeHolderBuilder(newDocumentBuilder.parse(is).getDocumentElement());
	}
}
