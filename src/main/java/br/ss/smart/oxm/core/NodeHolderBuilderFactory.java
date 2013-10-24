/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ss.smart.oxm.core;

import java.io.InputStream;

/**
 * 
 * @author isaac
 */
public interface NodeHolderBuilderFactory<Node> {

	NodeHolderBuilder<Node> newBuilder(InputStream is) throws Exception;

}
