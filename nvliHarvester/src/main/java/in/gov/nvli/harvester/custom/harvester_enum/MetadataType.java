/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.custom.harvester_enum;

/**
 *
 * @author ankit
 */
public enum MetadataType {
    OAI_DC("oai_dc"),
    ORE("ore");

    private final String text;

    private MetadataType(final String text) {
        this.text = text;
    }

    public String value() {
        return this.text;
    }
}
