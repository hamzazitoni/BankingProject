package com.cfgbank.MainApps;

import com.cfgbank.controller.CustomerController;
import com.cfgbank.service.CustomerService;
import com.cfgbank.view.CustomerView;

public class MainApp {
    public static void main(String[] args) {
        CustomerService customerService = new CustomerService();
        CustomerController customerController = new CustomerController(customerService);
        CustomerView customerView = new CustomerView(customerController);

        customerView.start();
    }
}

