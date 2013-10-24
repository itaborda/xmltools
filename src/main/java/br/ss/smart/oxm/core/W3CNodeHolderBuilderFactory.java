/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ss.smart.oxm.core;

import java.io.InputStream;

import nu.xom.Builder;

/**
 * 
 * @author isaac
 */
public class W3CNodeHolderBuilderFactory implements
		NodeHolderBuilderFactory<org.w3c.dom.Node> {

	public NodeHolderBuilder<org.w3c.dom.Node> newBuilder(InputStream is)
			throws Exception {

		nu.xom.Document doc = new Builder().build(is);

		return new NodeHolderBuilder<org.w3c.dom.Node>(doc.getRootElement());
	}
}
