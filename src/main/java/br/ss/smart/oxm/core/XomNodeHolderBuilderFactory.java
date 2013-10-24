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
public class XomNodeHolderBuilderFactory implements
		NodeHolderBuilderFactory<nu.xom.Node> {

	public NodeHolderBuilder<nu.xom.Node> newBuilder(InputStream is)
			throws Exception {

		nu.xom.Document doc = new Builder().build(is);

		return new NodeHolderBuilder<nu.xom.Node>(doc.getRootElement());
	}
}
