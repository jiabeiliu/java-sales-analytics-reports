/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Business;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import model.CustomerManagement.ChannelCatalog;
import model.CustomerManagement.CustomerDirectory;
import model.CustomerManagement.CustomerProfile;
import model.CustomerManagement.MarketCatalog;
import model.MarketingManagement.MarketingPersonDirectory;
import model.OrderManagement.MasterOrderList;
import model.Personnel.EmployeeDirectory;
import model.Personnel.PersonDirectory;
import model.ProductManagement.ProductSummary;
import model.ProductManagement.ProductsReport;
import model.ProductManagement.SolutionOfferCatalog;
import model.SalesManagement.SalesPersonDirectory;
import model.Supplier.Supplier;
import model.Supplier.SupplierDirectory;
import model.UserAccountManagement.UserAccountDirectory;

/**
 *
 * @author kal bugrara
 */
public class Business {

    String name;
    PersonDirectory persondirectory;
    MasterOrderList masterorderlist;
    SupplierDirectory suppliers;
    MarketCatalog marketcatalog;
    ChannelCatalog channelcatalog;
    SolutionOfferCatalog solutionoffercatalog;
    CustomerDirectory customerdirectory;
    EmployeeDirectory employeedirectory;
    SalesPersonDirectory salespersondirectory;
    UserAccountDirectory useraccountdirectory;
    MarketingPersonDirectory marketingpersondirectory;

    public Business(String n) {
        name = n;
        masterorderlist = new MasterOrderList();
        suppliers = new SupplierDirectory();
//        solutionoffercatalog = new SolutionOfferCatalog();
        persondirectory = new PersonDirectory();
        customerdirectory = new CustomerDirectory(this);
        salespersondirectory = new SalesPersonDirectory(this);
        useraccountdirectory = new UserAccountDirectory();
        marketingpersondirectory = new MarketingPersonDirectory(this);
        employeedirectory = new EmployeeDirectory(this);

    }

    public int getSalesVolume() {
        return masterorderlist.getSalesVolume();

    }

    public PersonDirectory getPersonDirectory() {
        return persondirectory;
    }

    public UserAccountDirectory getUserAccountDirectory() {
        return useraccountdirectory;
    }
    public MarketingPersonDirectory getMarketingPersonDirectory() {
        return marketingpersondirectory;
    }

    public SupplierDirectory getSupplierDirectory() {
        return suppliers;
    }

    public ProductsReport getSupplierPerformanceReport(String n) {
        Supplier supplier = suppliers.findSupplier(n);
        if (supplier == null) {
            return null;
        }
        return supplier.prepareProductsReport();

    }

    public ArrayList<ProductSummary> getSupplierProductsAlwaysAboveTarget(String n) {

        ProductsReport productsreport = getSupplierPerformanceReport(n);
        return productsreport.getProductsAlwaysAboveTarget();

    }

    public int getHowManySupplierProductsAlwaysAboveTarget(String n) {
        ProductsReport productsreport = getSupplierPerformanceReport(n); // see above
        int i = productsreport.getProductsAlwaysAboveTarget().size(); //return size of the arraylist
        return i;
    }

    public CustomerDirectory getCustomerDirectory() {
        return customerdirectory;
    }

    public SalesPersonDirectory getSalesPersonDirectory() {
        return salespersondirectory;
    }

    public MasterOrderList getMasterOrderList() {
        return masterorderlist;
    }
        public EmployeeDirectory getEmployeeDirectory() {
        return employeedirectory;
    }

    public void printShortInfo(){
        System.out.println("Checking what's inside the business hierarchy.");
        suppliers.printShortInfo();
        customerdirectory.printShortInfo();
        masterorderlist.printShortInfo();
    }

    public Double getLoyaltyScore(Supplier supplier) {
        /**
         * Loyalty score (Number of different customers who
         * picked suppliers products divided by number of all
         * customers)
         */
        List<CustomerProfile> customerList = customerdirectory.getCustomerList();
        int totalCustomer = customerList.size();
        if (supplier == null || totalCustomer == 0) return 0.0;
        int totalCustomerPickedSupplier = 0;
        for (CustomerProfile customerProfile : customerList) {
            if (customerProfile.isPickedSupplier(supplier)) {
                totalCustomerPickedSupplier++;
            }
        }
        return (double) totalCustomerPickedSupplier / totalCustomer;
    }

    public Double getAverageSpendingPerCustomer() {
        /**
         * - Average spending per customer (Total sales divided
         * by number of different customers)
         */
        List<CustomerProfile> customerList = customerdirectory.getCustomerList();
        long purchasingCustomers = customerList.stream().filter(customer -> customer.getTotalSales() > 0).count();
        if (purchasingCustomers == 0) return 0.0;
        int totalSales = masterorderlist.getSalesVolume();
        return (double) totalSales / purchasingCustomers;

    }

    public Double getTop5SalesScore() {
        /**
         * - Top 5 Sales Score (Total sales to top 5 Customers
         * divided by total sales)
         */
        int totalSales = masterorderlist.getSalesVolume();
        if (totalSales == 0) return 0.0;
        return customerdirectory.getCustomerList().stream()
                .sorted(Comparator.comparingInt(CustomerProfile::getTotalSales).reversed())
                .limit(5)
                .mapToInt(CustomerProfile::getTotalSales)
                .sum() / (double) totalSales;
    }
}
