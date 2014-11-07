/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.d4n.oxm.test;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.testng.annotations.Test;
import org.w3c.dom.Element;

import br.com.d4n.oxm.client.LoteGuia;
import br.com.d4n.oxm.client.parser.LoteParserV2;
import br.com.d4n.oxm.client.parser.LoteParserV2_A_1;
import br.com.d4n.oxm.core.NodeConverterUnmarshaller;
import br.com.d4n.oxm.core.NodeHolder;

/**
 * 
 * @author isaac
 */
public class NodeParserTest {

	@Test
	public void parseNodetest() {

		try {

			// parseWithJAXB(parserTest.defaultSample("loteGuias.xml"));

			parseNullMarshaller(update1Sample("loteGuias.xml"));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public NodeHolder<LoteGuia, ?> defaultSample(String filePath) throws Exception {

		InputStream is = NodeParserTest.class.getResourceAsStream(filePath);

		return new LoteParserV2().createHolder(is);
	}

	public NodeHolder<LoteGuia, ?> update1Sample(String filePath) throws Exception {

		InputStream is = NodeParserTest.class.getResourceAsStream(filePath);

		return new LoteParserV2_A_1().createHolder(is);
	}

	private static void parseNullMarshaller(NodeHolder<LoteGuia, ?> root) throws Exception {

		LoteGuia guia = root.parse(new NodeConverterUnmarshaller() {

			@Override
			public <T> T unmarshall(Element node) throws Exception {
				return null;
			}
		});

		System.out.println(guia);
	}

	private static void parseWithJAXB(NodeHolder<LoteGuia, ?> root) throws Exception {

		JAXBContext jaxb;
		try {
			jaxb = JAXBContext.newInstance(LoteGuia.class, LoteGuia.GuiaTISS.class, LoteGuia.GuiaTISS.Dados.class,
					LoteGuia.GuiaTISS.Procedimento.class);
			final Unmarshaller unmarshaller = jaxb.createUnmarshaller();

			LoteGuia guia = root.parse(new NodeConverterUnmarshaller() {

				@SuppressWarnings("unchecked")
				@Override
				public <T> T unmarshall(Element node) throws Exception {
					return (T) unmarshaller.unmarshal(node);
				}
			});

			System.out.println(guia);
		} catch (JAXBException e) {
			throw e;
		}

	}
}
