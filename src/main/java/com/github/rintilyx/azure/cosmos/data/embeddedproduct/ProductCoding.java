
package com.github.rintilyx.azure.cosmos.data.embeddedproduct;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;


/**
 * ProductCoding
 * <p>
 * Schema json for ProductCoding
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "upc",
        "barcode",
        "ndc",
        "quickCode",
        "wic"
})
public class ProductCoding implements Serializable {

    /**
     * (Required)
     */
    @JsonProperty("upc")

    private String upc;
    @JsonProperty("barcode")
    private String barcode;
    @JsonProperty("ndc")
    private String ndc;
    @JsonProperty("quickCode")
    private String quickCode;
    @JsonProperty("wic")
    private String wic;
    private final static long serialVersionUID = -987566007104509619L;

    /**
     * (Required)
     */
    public String getUpc() {
        return upc;
    }

    /**
     * (Required)
     */
    public void setUpc(String upc) {
        this.upc = upc;
    }

    public ProductCoding withUpc(String upc) {
        this.upc = upc;
        return this;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public ProductCoding withBarcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    public String getNdc() {
        return ndc;
    }

    public void setNdc(String ndc) {
        this.ndc = ndc;
    }

    public ProductCoding withNdc(String ndc) {
        this.ndc = ndc;
        return this;
    }

    public String getQuickCode() {
        return quickCode;
    }

    public void setQuickCode(String quickCode) {
        this.quickCode = quickCode;
    }

    public ProductCoding withQuickCode(String quickCode) {
        this.quickCode = quickCode;
        return this;
    }

    public String getWic() {
        return wic;
    }

    public void setWic(String wic) {
        this.wic = wic;
    }

    public ProductCoding withWic(String wic) {
        this.wic = wic;
        return this;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result * 31) + ((this.wic == null) ? 0 : this.wic.hashCode()));
        result = ((result * 31) + ((this.upc == null) ? 0 : this.upc.hashCode()));
        result = ((result * 31) + ((this.quickCode == null) ? 0 : this.quickCode.hashCode()));
        result = ((result * 31) + ((this.ndc == null) ? 0 : this.ndc.hashCode()));
        result = ((result * 31) + ((this.barcode == null) ? 0 : this.barcode.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ProductCoding) == false) {
            return false;
        }
        ProductCoding rhs = ((ProductCoding) other);
        return ((((((this.wic == rhs.wic) || ((this.wic != null) && this.wic.equals(rhs.wic))) && ((this.upc == rhs.upc) || ((this.upc != null) && this.upc.equals(rhs.upc)))) && ((this.quickCode == rhs.quickCode) || ((this.quickCode != null) && this.quickCode.equals(rhs.quickCode)))) && ((this.ndc == rhs.ndc) || ((this.ndc != null) && this.ndc.equals(rhs.ndc)))) && ((this.barcode == rhs.barcode) || ((this.barcode != null) && this.barcode.equals(rhs.barcode))));
    }

}
