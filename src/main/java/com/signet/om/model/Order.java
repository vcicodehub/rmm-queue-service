package com.signet.om.model;

import lombok.Data;

@Data
public class Order {
    private String id;
    private String name;
    private String vendorName;
    private String comment;
}
