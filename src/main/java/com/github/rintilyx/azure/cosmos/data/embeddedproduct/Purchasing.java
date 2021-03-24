
package com.github.rintilyx.azure.cosmos.data.embeddedproduct;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;


/**
 * Purchasing
 * <p>
 * Schema json for Purchasing
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "upcCheckDigit",
        "categoryCode",
        "categoryDescription",
        "crossOverIndicator",
        "itemSupplySource",
        "regulatedShipmentIndicator",
        "specialtyIndicator"
})
public class Purchasing implements Serializable {

    /**
     * (Required)
     */
    @JsonProperty("upcCheckDigit")

    private Long upcCheckDigit;
    @JsonProperty("categoryCode")
    private String categoryCode;
    @JsonProperty("categoryDescription")
    private String categoryDescription;
    @JsonProperty("crossOverIndicator")
    private Boolean crossOverIndicator;
    @JsonProperty("itemSupplySource")
    private String itemSupplySource;
    @JsonProperty("regulatedShipmentIndicator")
    private Boolean regulatedShipmentIndicator;
    @JsonProperty("specialtyIndicator")
    private Boolean specialtyIndicator;
    private final static long serialVersionUID = 9170983394509170015L;

    /**
     * (Required)
     */
    public Long getUpcCheckDigit() {
        return upcCheckDigit;
    }

    /**
     * (Required)
     */
    public void setUpcCheckDigit(Long upcCheckDigit) {
        this.upcCheckDigit = upcCheckDigit;
    }

    public Purchasing withUpcCheckDigit(Long upcCheckDigit) {
        this.upcCheckDigit = upcCheckDigit;
        return this;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public Purchasing withCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
        return this;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public Purchasing withCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
        return this;
    }

    public Boolean getCrossOverIndicator() {
        return crossOverIndicator;
    }

    public void setCrossOverIndicator(Boolean crossOverIndicator) {
        this.crossOverIndicator = crossOverIndicator;
    }

    public Purchasing withCrossOverIndicator(Boolean crossOverIndicator) {
        this.crossOverIndicator = crossOverIndicator;
        return this;
    }

    public String getItemSupplySource() {
        return itemSupplySource;
    }

    public void setItemSupplySource(String itemSupplySource) {
        this.itemSupplySource = itemSupplySource;
    }

    public Purchasing withItemSupplySource(String itemSupplySource) {
        this.itemSupplySource = itemSupplySource;
        return this;
    }

    public Boolean getRegulatedShipmentIndicator() {
        return regulatedShipmentIndicator;
    }

    public void setRegulatedShipmentIndicator(Boolean regulatedShipmentIndicator) {
        this.regulatedShipmentIndicator = regulatedShipmentIndicator;
    }

    public Purchasing withRegulatedShipmentIndicator(Boolean regulatedShipmentIndicator) {
        this.regulatedShipmentIndicator = regulatedShipmentIndicator;
        return this;
    }

    public Boolean getSpecialtyIndicator() {
        return specialtyIndicator;
    }

    public void setSpecialtyIndicator(Boolean specialtyIndicator) {
        this.specialtyIndicator = specialtyIndicator;
    }

    public Purchasing withSpecialtyIndicator(Boolean specialtyIndicator) {
        this.specialtyIndicator = specialtyIndicator;
        return this;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result * 31) + ((this.crossOverIndicator == null) ? 0 : this.crossOverIndicator.hashCode()));
        result = ((result * 31) + ((this.specialtyIndicator == null) ? 0 : this.specialtyIndicator.hashCode()));
        result = ((result * 31) + ((this.upcCheckDigit == null) ? 0 : this.upcCheckDigit.hashCode()));
        result = ((result * 31) + ((this.categoryCode == null) ? 0 : this.categoryCode.hashCode()));
        result = ((result * 31) + ((this.itemSupplySource == null) ? 0 : this.itemSupplySource.hashCode()));
        result = ((result * 31) + ((this.regulatedShipmentIndicator == null) ? 0 : this.regulatedShipmentIndicator.hashCode()));
        result = ((result * 31) + ((this.categoryDescription == null) ? 0 : this.categoryDescription.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Purchasing) == false) {
            return false;
        }
        Purchasing rhs = ((Purchasing) other);
        return ((((((((this.crossOverIndicator == rhs.crossOverIndicator) || ((this.crossOverIndicator != null) && this.crossOverIndicator.equals(rhs.crossOverIndicator))) && ((this.specialtyIndicator == rhs.specialtyIndicator) || ((this.specialtyIndicator != null) && this.specialtyIndicator.equals(rhs.specialtyIndicator)))) && ((this.upcCheckDigit == rhs.upcCheckDigit) || ((this.upcCheckDigit != null) && this.upcCheckDigit.equals(rhs.upcCheckDigit)))) && ((this.categoryCode == rhs.categoryCode) || ((this.categoryCode != null) && this.categoryCode.equals(rhs.categoryCode)))) && ((this.itemSupplySource == rhs.itemSupplySource) || ((this.itemSupplySource != null) && this.itemSupplySource.equals(rhs.itemSupplySource)))) && ((this.regulatedShipmentIndicator == rhs.regulatedShipmentIndicator) || ((this.regulatedShipmentIndicator != null) && this.regulatedShipmentIndicator.equals(rhs.regulatedShipmentIndicator)))) && ((this.categoryDescription == rhs.categoryDescription) || ((this.categoryDescription != null) && this.categoryDescription.equals(rhs.categoryDescription))));
    }

}
