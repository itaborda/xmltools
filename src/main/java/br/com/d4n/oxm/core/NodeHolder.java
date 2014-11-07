/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.d4n.oxm.core;

import java.util.Collection;
import java.util.List;

import org.w3c.dom.Element;

/**
 * 
 * @author isaac
 */
public interface NodeHolder<T, P> {

	T parse(NodeConverterUnmarshaller unmarshaller) throws Exception;

	Element getNode();

	NodeHolder<?, ?> get(int i);

	List<NodeHolder<?, ?>> get(String xpath);

	<C> NodeHolder<C, T> set(int i, final NodeConverter<C, T> converter);

	<C> NodeHolder<C, T> set(String xpath, final NodeConverter<C, T> callback) throws Exception;

	NodeHolder<?, P> next();

	NodeHolder<?, P> previous();

	NodeHolder<P, ?> getParent();

	Collection<? extends NodeHolder<?, T>> getChildNodes();

	boolean hasNext();

	boolean hasPrevious();

	boolean isRoot();

	int getIndex();

	int getPosition();
}
