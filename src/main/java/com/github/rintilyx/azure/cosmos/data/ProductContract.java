
package com.github.rintilyx.azure.cosmos.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.rintilyx.azure.cosmos.data.embeddedproduct.ProductCoding;
import com.github.rintilyx.azure.cosmos.data.embeddedproduct.Purchasing;
import com.github.rintilyx.azure.cosmos.data.enumsproduct.DocumentTypology;
import com.github.rintilyx.azure.cosmos.data.enumsproduct.DrugClassTypology;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * Product
 * <p>
 * Schema json for Product
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "bucket",
        "_self",
        "documentType",
        "productCode",
        "departmentNumber",
        "drugClassFederal",
        "itemDescription",
        "lastUpdateTime",
        "packSize",
        "purchasing",
        "productCoding",
        "manufacturerName"
})
public class ProductContract implements Serializable {


    @JsonProperty("id")
    private String id;
    /**
     * (Required)
     */

    @JsonProperty("bucket")

    private String bucket;
    @JsonProperty("_self")
    private String self;
    /**
     * DocumentTypology
     * (Required)
     */
    @JsonProperty("documentType")
    @JsonPropertyDescription("DocumentTypology")

    private DocumentTypology documentType;
    /**
     * (Required)
     */
    @JsonProperty("productCode")

    private String productCode;
    /**
     * (Required)
     */
    @JsonProperty("departmentNumber")

    private String departmentNumber;
    /**
     * DrugClassTypology
     * (Required)
     */
    @JsonProperty("drugClassFederal")
    @JsonPropertyDescription("DrugClassTypology")

    private DrugClassTypology drugClassFederal;
    /**
     * (Required)
     */
    @JsonProperty("itemDescription")

    private String itemDescription;
    /**
     * (Required)
     */
    @JsonProperty("lastUpdateTime")

    private Long lastUpdateTime;
    /**
     * (Required)
     */
    @JsonProperty("packSize")

    private BigDecimal packSize;
    /**
     * Purchasing
     * <p>
     * Schema json for Purchasing
     * (Required)
     */
    @JsonProperty("purchasing")
    @JsonPropertyDescription("Schema json for Purchasing")


    private Purchasing purchasing;
    /**
     * ProductCoding
     * <p>
     * Schema json for ProductCoding
     * (Required)
     */
    @JsonProperty("productCoding")
    @JsonPropertyDescription("Schema json for ProductCoding")


    private ProductCoding productCoding;
    @JsonProperty("manufacturerName")
    private String manufacturerName;
    private final static long serialVersionUID = -8989125466716778294L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductContract withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * (Required)
     */
    public String getBucket() {
        return bucket;
    }

    /**
     * (Required)
     */
    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public ProductContract withBucket(String bucket) {
        this.bucket = bucket;
        return this;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public ProductContract withSelf(String self) {
        this.self = self;
        return this;
    }

    /**
     * DocumentTypology
     * (Required)
     */
    public DocumentTypology getDocumentType() {
        return documentType;
    }

    /**
     * DocumentTypology
     * (Required)
     */
    public void setDocumentType(DocumentTypology documentType) {
        this.documentType = documentType;
    }

    public ProductContract withDocumentType(DocumentTypology documentType) {
        this.documentType = documentType;
        return this;
    }

    /**
     * (Required)
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * (Required)
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public ProductContract withProductCode(String productCode) {
        this.productCode = productCode;
        return this;
    }

    /**
     * (Required)
     */
    public String getDepartmentNumber() {
        return departmentNumber;
    }

    /**
     * (Required)
     */
    public void setDepartmentNumber(String departmentNumber) {
        this.departmentNumber = departmentNumber;
    }

    public ProductContract withDepartmentNumber(String departmentNumber) {
        this.departmentNumber = departmentNumber;
        return this;
    }

    /**
     * DrugClassTypology
     * (Required)
     */
    public DrugClassTypology getDrugClassFederal() {
        return drugClassFederal;
    }

    /**
     * DrugClassTypology
     * (Required)
     */
    public void setDrugClassFederal(DrugClassTypology drugClassFederal) {
        this.drugClassFederal = drugClassFederal;
    }

    public ProductContract withDrugClassFederal(DrugClassTypology drugClassFederal) {
        this.drugClassFederal = drugClassFederal;
        return this;
    }

    /**
     * (Required)
     */
    public String getItemDescription() {
        return itemDescription;
    }

    /**
     * (Required)
     */
    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public ProductContract withItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
        return this;
    }

    /**
     * (Required)
     */
    public Long getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * (Required)
     */
    public void setLastUpdateTime(Long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public ProductContract withLastUpdateTime(Long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
        return this;
    }

    /**
     * (Required)
     */
    public BigDecimal getPackSize() {
        return packSize;
    }

    /**
     * (Required)
     */
    public void setPackSize(BigDecimal packSize) {
        this.packSize = packSize;
    }

    public ProductContract withPackSize(BigDecimal packSize) {
        this.packSize = packSize;
        return this;
    }

    /**
     * Purchasing
     * <p>
     * Schema json for Purchasing
     * (Required)
     */
    public Purchasing getPurchasing() {
        return purchasing;
    }

    /**
     * Purchasing
     * <p>
     * Schema json for Purchasing
     * (Required)
     */
    public void setPurchasing(Purchasing purchasing) {
        this.purchasing = purchasing;
    }

    public ProductContract withPurchasing(Purchasing purchasing) {
        this.purchasing = purchasing;
        return this;
    }

    /**
     * ProductCoding
     * <p>
     * Schema json for ProductCoding
     * (Required)
     */
    public ProductCoding getProductCoding() {
        return productCoding;
    }

    /**
     * ProductCoding
     * <p>
     * Schema json for ProductCoding
     * (Required)
     */
    public void setProductCoding(ProductCoding productCoding) {
        this.productCoding = productCoding;
    }

    public ProductContract withProductCoding(ProductCoding productCoding) {
        this.productCoding = productCoding;
        return this;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public ProductContract withManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
        return this;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result * 31) + ((this.packSize == null) ? 0 : this.packSize.hashCode()));
        result = ((result * 31) + ((this.documentType == null) ? 0 : this.documentType.hashCode()));
        result = ((result * 31) + ((this.purchasing == null) ? 0 : this.purchasing.hashCode()));
        result = ((result * 31) + ((this.manufacturerName == null) ? 0 : this.manufacturerName.hashCode()));
        result = ((result * 31) + ((this.productCoding == null) ? 0 : this.productCoding.hashCode()));
        result = ((result * 31) + ((this.bucket == null) ? 0 : this.bucket.hashCode()));
        result = ((result * 31) + ((this.drugClassFederal == null) ? 0 : this.drugClassFederal.hashCode()));
        result = ((result * 31) + ((this.productCode == null) ? 0 : this.productCode.hashCode()));
        result = ((result * 31) + ((this.departmentNumber == null) ? 0 : this.departmentNumber.hashCode()));
        result = ((result * 31) + ((this.self == null) ? 0 : this.self.hashCode()));
        result = ((result * 31) + ((this.id == null) ? 0 : this.id.hashCode()));
        result = ((result * 31) + ((this.itemDescription == null) ? 0 : this.itemDescription.hashCode()));
        result = ((result * 31) + ((this.lastUpdateTime == null) ? 0 : this.lastUpdateTime.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ProductContract) == false) {
            return false;
        }
        ProductContract rhs = ((ProductContract) other);
        return ((((((((((((((this.packSize == rhs.packSize) || ((this.packSize != null) && this.packSize.equals(rhs.packSize))) && ((this.documentType == rhs.documentType) || ((this.documentType != null) && this.documentType.equals(rhs.documentType)))) && ((this.purchasing == rhs.purchasing) || ((this.purchasing != null) && this.purchasing.equals(rhs.purchasing)))) && ((this.manufacturerName == rhs.manufacturerName) || ((this.manufacturerName != null) && this.manufacturerName.equals(rhs.manufacturerName)))) && ((this.productCoding == rhs.productCoding) || ((this.productCoding != null) && this.productCoding.equals(rhs.productCoding)))) && ((this.bucket == rhs.bucket) || ((this.bucket != null) && this.bucket.equals(rhs.bucket)))) && ((this.drugClassFederal == rhs.drugClassFederal) || ((this.drugClassFederal != null) && this.drugClassFederal.equals(rhs.drugClassFederal)))) && ((this.productCode == rhs.productCode) || ((this.productCode != null) && this.productCode.equals(rhs.productCode)))) && ((this.departmentNumber == rhs.departmentNumber) || ((this.departmentNumber != null) && this.departmentNumber.equals(rhs.departmentNumber)))) && ((this.self == rhs.self) || ((this.self != null) && this.self.equals(rhs.self)))) && ((this.id == rhs.id) || ((this.id != null) && this.id.equals(rhs.id)))) && ((this.itemDescription == rhs.itemDescription) || ((this.itemDescription != null) && this.itemDescription.equals(rhs.itemDescription)))) && ((this.lastUpdateTime == rhs.lastUpdateTime) || ((this.lastUpdateTime != null) && this.lastUpdateTime.equals(rhs.lastUpdateTime))));
    }

}
