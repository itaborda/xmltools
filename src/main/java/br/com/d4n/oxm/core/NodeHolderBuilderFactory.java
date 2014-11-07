/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.d4n.oxm.core;

import java.io.InputStream;

/**
 * 
 * @author isaac
 */
public interface NodeHolderBuilderFactory {

	NodeHolderBuilder newBuilder(InputStream is) throws Exception;

}
