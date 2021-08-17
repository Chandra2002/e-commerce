package com.clone.ecommerce.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONObject;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonIgnoreProperties(ignoreUnknown = false)
public class Product {
    @JsonProperty("product_type")
    private String product_type;
    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("cost")
    private double cost;
    @JsonProperty("quantity")
    private int quantity;
    @JsonProperty("available_delivery_list")
    private JSONObject availableDeliveryList;
    @JsonProperty("discount")
    private int discount;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public JSONObject getAvailableDeliveryList() {
        return availableDeliveryList;
    }

    public void setAvailableDeliveryList(JSONObject availableDeliveryList) {
        this.availableDeliveryList = availableDeliveryList;
    }

    public void setAvailableDeliveryList(String availableDeliveryList) {
        JSONObject delivery = new JSONObject();
        delivery.put("1", availableDeliveryList);
        this.availableDeliveryList = delivery;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }
}
