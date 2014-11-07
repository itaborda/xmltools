/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.d4n.oxm.core;

import org.w3c.dom.Element;

/**
 * 
 * @author isaac
 */
public interface NodeConverterUnmarshaller {

	<T> T unmarshall(Element node) throws Exception;
}
