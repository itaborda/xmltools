/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ss.smart.oxm.test;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.w3c.dom.Node;

import br.ss.smart.oxm.client.LoteGuia;
import br.ss.smart.oxm.client.parser.LoteParserV2;
import br.ss.smart.oxm.client.parser.LoteParserV2_A_1;
import br.ss.smart.oxm.core.NodeConverterUnmarshaller;
import br.ss.smart.oxm.core.NodeHolder;

/**
 * 
 * @author isaac
 */
public class NodeParserTest {

	public static void main(String[] args) {

		NodeParserTest parserTest = new NodeParserTest();

		try {

			// parseWithJAXB(parserTest.defaultSample("loteGuias.xml"));

			parseNullMarshaller(parserTest.update1Sample("loteGuias.xml"));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public NodeHolder<LoteGuia, ?, Node> defaultSample(String filePath)
			throws Exception {

		InputStream is = NodeParserTest.class.getResourceAsStream(filePath);

		return new LoteParserV2().createHolder(is);
	}

	public NodeHolder<LoteGuia, ?, Node> update1Sample(String filePath)
			throws Exception {

		InputStream is = NodeParserTest.class.getResourceAsStream(filePath);

		return new LoteParserV2_A_1().createHolder(is);
	}

	private static void parseNullMarshaller(NodeHolder<LoteGuia, ?, Node> root)
			throws Exception {

		LoteGuia guia = root
				.parse(new NodeConverterUnmarshaller<org.w3c.dom.Node>() {

					@Override
					public <T> T unmarshall(org.w3c.dom.Node node)
							throws Exception {
						return null;
					}
				});

		System.out.println(guia);
	}

	private static void parseWithJAXB(NodeHolder<LoteGuia, ?, Node> root)
			throws Exception {

		JAXBContext jaxb;
		try {
			jaxb = JAXBContext.newInstance(LoteGuia.class,
					LoteGuia.GuiaTISS.class, LoteGuia.GuiaTISS.Dados.class,
					LoteGuia.GuiaTISS.Procedimento.class);
			final Unmarshaller unmarshaller = jaxb.createUnmarshaller();

			LoteGuia guia = root
					.parse(new NodeConverterUnmarshaller<org.w3c.dom.Node>() {

						@SuppressWarnings("unchecked")
						@Override
						public <T> T unmarshall(org.w3c.dom.Node node)
								throws Exception {
							return (T) unmarshaller.unmarshal(node);
						}
					});

			System.out.println(guia);
		} catch (JAXBException e) {
			throw e;
		}

	}
}
