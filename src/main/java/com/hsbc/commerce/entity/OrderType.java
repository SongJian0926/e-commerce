package com.hsbc.commerce.entity;


public enum OrderType {
    //初级订单(0-500)  中级订单(500-1000) 高级订单(1000以上)
    PrimaryOrder(0.1),
    IntermediateOrder(0.15),
    AdvancedOrder(0.20);
    private Double ratio;
    OrderType(Double ratio){
        this.ratio = ratio;
    }
    public Double getRatio(){
      return ratio;
    }
}
