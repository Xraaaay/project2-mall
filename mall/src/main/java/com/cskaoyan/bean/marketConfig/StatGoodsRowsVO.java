package com.cskaoyan.bean.marketConfig;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @since 2022/07/18 17:04
 * @author lyx
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatGoodsRowsVO {


    /**
     * amount : 2989
     * orders : 10
     * day : 2022-07-15
     * products : 11
     */

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private int amount;
    private int orders;
    private String day;
    private int products;

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setProducts(int products) {
        this.products = products;
    }

    public int getAmount() {
        return amount;
    }

    public int getOrders() {
        return orders;
    }

    public String getDay() {
        return day;
    }

    public int getProducts() {
        return products;
    }
}
