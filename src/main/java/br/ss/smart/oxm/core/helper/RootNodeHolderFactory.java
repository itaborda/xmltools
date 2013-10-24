/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ss.smart.oxm.core.helper;

import java.io.InputStream;

import br.ss.smart.oxm.core.NodeConverter;
import br.ss.smart.oxm.core.NodeConverterUnmarshaller;
import br.ss.smart.oxm.core.NodeHolder;
import br.ss.smart.oxm.core.NodeHolderBuilder;
import br.ss.smart.oxm.core.NodeHolderBuilderFactory;

/**
 * 
 * @author isaac
 */
public class RootNodeHolderFactory {

	public static <N> NodeHolder<Object, Object, N> getRoot(
			NodeHolderBuilderFactory<N> builderFactory, InputStream is) {

		NodeHolderBuilder<N> builder;
		try {
			builder = builderFactory.newBuilder(is);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		return getRoot(builder);
	}

	public static <N> NodeHolder<Object, Object, N> getRoot(
			NodeHolderBuilder<N> builder) {

		return builder.createRoot(new NodeConverter<Object, Object, N>() {
			@Override
			public Object convert(NodeHolder<Object, Object, N> holder,
					Object parent, NodeConverterUnmarshaller<N> unmarshaller)
					throws Exception {
				return null;
			}
		});
	}
}
