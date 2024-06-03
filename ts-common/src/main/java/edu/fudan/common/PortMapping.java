package edu.fudan.common;

import java.util.HashMap;

public class PortMapping {
    static HashMap<String, Integer> portMap = new HashMap<>();

    static {
        portMap.put("ts-auth-service", 12340);
        portMap.put("ts-user-service", 12342);
        portMap.put("ts-verification-code-service", 15678);
        portMap.put("ts-route-service", 11178);
        portMap.put("ts-contacts-service", 12347);
        portMap.put("ts-order-service", 12031);
        portMap.put("ts-order-other-service", 12032);
        portMap.put("ts-config-service", 15679);
        portMap.put("ts-station-service", 12345);
        portMap.put("ts-train-service", 14567);
        portMap.put("ts-travel-service", 12346);
        portMap.put("ts-travel2-service", 16346);
        portMap.put("ts-preserve-service", 14568);
        portMap.put("ts-preserve-other-service", 14569);
        portMap.put("ts-basic-service", 15680);
        portMap.put("ts-price-service", 16579);
        portMap.put("ts-notification-service", 17853);
        portMap.put("ts-security-service", 11188);
        portMap.put("ts-inside-payment-service", 18673);
        portMap.put("ts-execute-service", 12386);
        portMap.put("ts-payment-service", 19001);
        portMap.put("ts-rebook-service", 18886);
        portMap.put("ts-cancel-service", 18885);
        portMap.put("ts-assurance-service", 18888);
        portMap.put("ts-seat-service", 18898);
        portMap.put("ts-travel-plan-service", 14322);
        portMap.put("ts-news-service", 12862);
        portMap.put("ts-voucher-service", 16101);
        portMap.put("ts-route-plan-service", 14578);
        portMap.put("ts-food-service", 18856);
        portMap.put("ts-consign-service", 16111);
        portMap.put("ts-consign-price-service", 16110);
        portMap.put("ts-admin-basic-info-service", 18767);
        portMap.put("ts-admin-order-service", 16112);
        portMap.put("ts-admin-route-service", 16113);
        portMap.put("ts-admin-travel-service", 16114);
        portMap.put("ts-admin-user-service", 16115);
        portMap.put("ts-avatar-service", 17001);
        portMap.put("ts-delivery-service", 18808);
        portMap.put("ts-food-delivery-service", 18957);
        portMap.put("ts-gateway-service", 18888);
        portMap.put("ts-station-food-service", 18855);
        portMap.put("ts-train-food-service", 19999);
    }

    public static Integer getPort(String serviceName) {
        return portMap.get(serviceName);
    }

    public static String getServiceUrl(String serviceName) {
        Integer port = PortMapping.getPort(serviceName);
        if (port == null) {
            throw new RuntimeException("Can not find service mapping: " + serviceName);
        }
        return "http://" + serviceName + ":" + port;
    }

}
