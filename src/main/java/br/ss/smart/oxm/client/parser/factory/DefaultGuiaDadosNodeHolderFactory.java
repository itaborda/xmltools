/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ss.smart.oxm.client.parser.factory;

import org.w3c.dom.Node;

import br.ss.smart.oxm.client.LoteGuia;
import br.ss.smart.oxm.core.NodeConverter;
import br.ss.smart.oxm.core.NodeConverterUnmarshaller;
import br.ss.smart.oxm.core.NodeHolder;
import br.ss.smart.oxm.core.helper.BaseNodeHolderFactory;

/**
 *
 * @author isaac
 */
public class DefaultGuiaDadosNodeHolderFactory extends BaseNodeHolderFactory<LoteGuia.GuiaTISS.Dados, LoteGuia.GuiaTISS, Node> {

    private static final int DADOS_INDEX = 0;

    @Override
    public NodeHolder<LoteGuia.GuiaTISS.Dados, LoteGuia.GuiaTISS, Node> create(final NodeHolder<LoteGuia.GuiaTISS, ?, Node> holderParent) throws Exception {

        return holderParent.set(DADOS_INDEX, new NodeConverter<LoteGuia.GuiaTISS.Dados, LoteGuia.GuiaTISS, Node>() {
            @Override
            public LoteGuia.GuiaTISS.Dados convert(NodeHolder<LoteGuia.GuiaTISS.Dados, LoteGuia.GuiaTISS, Node> holder, LoteGuia.GuiaTISS parent, NodeConverterUnmarshaller<Node> unmarshaller) throws Exception {

                LoteGuia.GuiaTISS.Dados dados;
                if ("guiaSP-SADT".equals(parent.getValue())) {
                    dados = holderParent.set("dadosAutorizacao", new NodeConverter<LoteGuia.GuiaTISS.Dados, LoteGuia.GuiaTISS, Node>() {
                        @Override
                        public LoteGuia.GuiaTISS.Dados convert(NodeHolder<LoteGuia.GuiaTISS.Dados, LoteGuia.GuiaTISS, Node> holder, LoteGuia.GuiaTISS parent, NodeConverterUnmarshaller<Node> unmarshaller) throws Exception {
                            return new LoteGuia.GuiaTISS.Dados(holder.getDOMNode().getNodeName());
                        }
                    }).parse(unmarshaller);
                } else if ("guia1234".equals(parent.getValue())) {
                    dados = holderParent.set("dadosSolicitante", new NodeConverter<LoteGuia.GuiaTISS.Dados, LoteGuia.GuiaTISS, Node>() {
                        @Override
                        public LoteGuia.GuiaTISS.Dados convert(NodeHolder<LoteGuia.GuiaTISS.Dados, LoteGuia.GuiaTISS, Node> holder, LoteGuia.GuiaTISS parent, NodeConverterUnmarshaller<Node> unmarshaller) throws Exception {
                            return new LoteGuia.GuiaTISS.Dados(holder.getDOMNode().getNodeName());
                        }
                    }).parse(unmarshaller);
                } else {
                    dados = holderParent.set("dadosExecutante", new NodeConverter<LoteGuia.GuiaTISS.Dados, LoteGuia.GuiaTISS, Node>() {
                        @Override
                        public LoteGuia.GuiaTISS.Dados convert(NodeHolder<LoteGuia.GuiaTISS.Dados, LoteGuia.GuiaTISS, Node> holder, LoteGuia.GuiaTISS parent, NodeConverterUnmarshaller<Node> unmarshaller) throws Exception {
                            return new LoteGuia.GuiaTISS.Dados(holder.getDOMNode().getNodeName());
                        }
                    }).parse(unmarshaller);
                }
                parent.setDados(dados);
                return dados;
            }
        });

    }
}
