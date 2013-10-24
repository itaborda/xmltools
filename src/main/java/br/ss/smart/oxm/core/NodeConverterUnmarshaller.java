/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ss.smart.oxm.core;

/**
 * 
 * @author isaac
 */
public interface NodeConverterUnmarshaller<Node> {

	<T> T unmarshall(Node node) throws Exception;
}
