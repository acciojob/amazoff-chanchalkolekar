package com.driver;

import java.util.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    private HashMap<String, Order> db1order;
    private HashMap<String, DeliveryPartner> db2partner;
    private HashMap<String, HashSet<String>> db3orderpartner;
    private HashMap<String, String> db4partnerorder;

    public OrderRepository() {
        this.db1order = new HashMap<String, Order>();

        this.db2partner = new HashMap<String, DeliveryPartner>();
        this.db3orderpartner = new HashMap<String, HashSet<String>>();
        this.db4partnerorder = new HashMap<String, String>();
    }

    public void addOrder(Order order) {
        db1order.put(order.getId(), order);
    }

    public void addPartner(String partnerId) {
        DeliveryPartner dp = new DeliveryPartner(partnerId);
        db2partner.put(dp.getId(), dp);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        if (db1order.containsKey(orderId) && db2partner.containsKey(partnerId)) {
            HashSet<String> current = new HashSet<>();
            if (db3orderpartner.containsKey(partnerId)) {
                current = db3orderpartner.get(partnerId);
            }
            current.add(orderId);
            db3orderpartner.put(partnerId, current);

            DeliveryPartner dp = db2partner.get(partnerId);
            dp.setNumberOfOrders(current.size());

            db4partnerorder.put(orderId, partnerId);
        }
    }

    public Order getOrderById(String order) {
        return db1order.get(order);
    }

    public DeliveryPartner getPartnerById(String partner) {
        return db2partner.get(partner);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        Integer answer = 0;
        if (db2partner.containsKey(partnerId)) {
            DeliveryPartner dp = db2partner.get(partnerId);
            answer = dp.getNumberOfOrders();
        }
        return answer;
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        List<String> list = new ArrayList<>();
        HashSet<String> temporary = new HashSet<>();
        if (db3orderpartner.containsKey(partnerId)) {
            temporary = db3orderpartner.get(partnerId);
        }
        for (String name : temporary) {
            list.add(name);
        }
        return list;
    }

    public List<String> getAllOrders() {
        List<String> list = new ArrayList<>();
        for (String name : db1order.keySet()) {
            list.add(name);
        }
        return list;
    }

    public Integer getCountOfUnassignedOrders() {
        Integer answer = 0;
        List<String> list = new ArrayList<>(db1order.keySet());
        for (String order : list) {
            if (!db4partnerorder.containsKey(order)) {
                answer++;
            }
        }

        return answer;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        String[] strarr = time.split(":", 2);
        Integer intialanswer = 0, finalanswer = 0;
        for (int i = 0; i < strarr.length; i++) {
            String demo = strarr[i];
            if (i == 0) {
                int temp = Integer.parseInt(demo);
                intialanswer = temp * 60;
            } else {
                int temp = Integer.parseInt(demo);
                finalanswer = intialanswer + temp;
            }
        }

        Integer count = 0;
        if (db3orderpartner.containsKey(partnerId)) {
            HashSet<String> hash = db3orderpartner.get(partnerId);
            for (String order : hash) {
                if (db1order.containsKey(order)) {
                    Order od = db1order.get(order);
                    if (finalanswer < od.getDeliveryTime()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        Integer time = 0;

        if (db3orderpartner.containsKey(partnerId)) {
            HashSet<String> order = db3orderpartner.get(partnerId);
            for (String od : order) {
                if (db1order.containsKey(od)) {
                    Order current = db1order.get(od);
                    time = Math.max(time, current.getDeliveryTime());
                }
            }
        }
        Integer hours = time * 60;
        Integer min = time % 60;

        String HH = Integer.toString(hours);
        String MM = Integer.toString(min);

        return HH + ":" + MM;
    }

    public void deletePartnerById(String PartnerId) {
        HashSet<String> hash = new HashSet<>();
        if (db3orderpartner.containsKey(PartnerId)) {
            hash = db3orderpartner.get(PartnerId);
            for (String order : hash) {
                if (db4partnerorder.containsKey(order)) {
                    db4partnerorder.remove(order);
                }
            }
            db3orderpartner.remove(PartnerId);
        }
        if (db2partner.containsKey(PartnerId)) {
            db2partner.remove(PartnerId);
        }
    }

    public void deleteOrderById(String orderId) {
        if (db4partnerorder.containsKey(orderId)) {
            String partner = db4partnerorder.get(orderId);
            HashSet<String> hash = db3orderpartner.get(orderId);
            hash.remove(orderId);
            db3orderpartner.put(partner, hash);

            DeliveryPartner dl = db2partner.get(partner);
            dl.setNumberOfOrders(hash.size());

        }
        if(db1order.containsKey(orderId)){
            db1order.remove(orderId);
        }
    }

}
