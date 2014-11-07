/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.d4n.oxm.client.parser.factory;

import br.com.d4n.oxm.client.LoteGuia;
import br.com.d4n.oxm.core.NodeConverter;
import br.com.d4n.oxm.core.NodeConverterUnmarshaller;
import br.com.d4n.oxm.core.NodeHolder;
import br.com.d4n.oxm.core.helper.BaseNodeHolderFactory;

/**
 *
 * @author isaac
 */
public class DefaultGuiaDadosNodeHolderFactory extends
		BaseNodeHolderFactory<LoteGuia.GuiaTISS.Dados, LoteGuia.GuiaTISS> {

	private static final int DADOS_INDEX = 1;

	@Override
	public NodeHolder<LoteGuia.GuiaTISS.Dados, LoteGuia.GuiaTISS> create(
			final NodeHolder<LoteGuia.GuiaTISS, ?> holderParent) throws Exception {

		return holderParent.set(DADOS_INDEX, new NodeConverter<LoteGuia.GuiaTISS.Dados, LoteGuia.GuiaTISS>() {
			@Override
			public LoteGuia.GuiaTISS.Dados convert(NodeHolder<LoteGuia.GuiaTISS.Dados, LoteGuia.GuiaTISS> holder,
					LoteGuia.GuiaTISS parent, NodeConverterUnmarshaller unmarshaller) throws Exception {

				LoteGuia.GuiaTISS.Dados dados;
				if ("guiaSP-SADT".equals(parent.getValue())) {
					dados = holderParent.set("guiasTISS/*/dadosAutorizacao",
							new NodeConverter<LoteGuia.GuiaTISS.Dados, LoteGuia.GuiaTISS>() {
								@Override
								public LoteGuia.GuiaTISS.Dados convert(
										NodeHolder<LoteGuia.GuiaTISS.Dados, LoteGuia.GuiaTISS> holder,
										LoteGuia.GuiaTISS parent, NodeConverterUnmarshaller unmarshaller)
										throws Exception {
									return new LoteGuia.GuiaTISS.Dados(holder.getNode().getNodeName());
								}
							}).parse(unmarshaller);
				} else if ("guia1234".equals(parent.getValue())) {
					dados = holderParent.set("guiasTISS/*/dadosSolicitante",
							new NodeConverter<LoteGuia.GuiaTISS.Dados, LoteGuia.GuiaTISS>() {
								@Override
								public LoteGuia.GuiaTISS.Dados convert(
										NodeHolder<LoteGuia.GuiaTISS.Dados, LoteGuia.GuiaTISS> holder,
										LoteGuia.GuiaTISS parent, NodeConverterUnmarshaller unmarshaller)
										throws Exception {
									return new LoteGuia.GuiaTISS.Dados(holder.getNode().getNodeName());
								}
							}).parse(unmarshaller);
				} else {
					dados = holderParent.set("guiasTISS/*/dadosExecutante",
							new NodeConverter<LoteGuia.GuiaTISS.Dados, LoteGuia.GuiaTISS>() {
								@Override
								public LoteGuia.GuiaTISS.Dados convert(
										NodeHolder<LoteGuia.GuiaTISS.Dados, LoteGuia.GuiaTISS> holder,
										LoteGuia.GuiaTISS parent, NodeConverterUnmarshaller unmarshaller)
										throws Exception {
									return new LoteGuia.GuiaTISS.Dados(holder.getNode().getNodeName());
								}
							}).parse(unmarshaller);
				}
				parent.setDados(dados);
				return dados;
			}
		});

	}
}
