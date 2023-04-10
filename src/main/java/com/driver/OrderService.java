package com.driver;

import java.io.PushbackReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    OrderRepository repo;

    public void addOrder(Order order) {
        repo.addOrder(order);
    }

    public void addPartner(String partnerId) {
        repo.addPartner(partnerId);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        repo.addOrderPartnerPair(orderId, partnerId);
    }

    public Order getOrderById(String orderId) {
        return repo.getOrderById(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return repo.getPartnerById(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return repo.getOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return repo.getOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders() {
        return repo.getAllOrders();
    }

    public Integer getCountOfUnassignedOrders() {
        return repo.getCountOfUnassignedOrders();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        return repo.getOrdersLeftAfterGivenTimeByPartnerId(time, partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        return repo.getLastDeliveryTimeByPartnerId(partnerId);
    }

    public void deletePartnerById(String partnerId) {
        repo.deletePartnerById(partnerId);
    }

    public void  deleteOrderById(String orderId){
        repo.deleteOrderById(orderId);
    }

}
