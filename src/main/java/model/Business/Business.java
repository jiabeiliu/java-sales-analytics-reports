/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Business;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
        int totalCustomer = customerList.size();
        int totalSales = masterorderlist.getSalesVolume();
        return (double) totalSales / totalCustomer;

    }

    public void getTop5SalesScore() {
        /**
         * - Top 5 Sales Score (Total sales to top 5 Customers
         * divided by total sales)
         */
        int totalSales = masterorderlist.getSalesVolume();
        BigDecimal total = new BigDecimal(totalSales);
        List<CustomerProfile> customerList = customerdirectory.getCustomerList();
        customerList.sort((o1, o2) -> o2.getTotalSales() - o1.getTotalSales());
        for (int i = 0; i < 5; i++) {
            CustomerProfile customerProfile = customerList.get(i);
            BigDecimal cus = new BigDecimal(customerProfile.getTotalSales());
            // 保留6位小数
            DecimalFormat df = new DecimalFormat("0.000000");
            System.out.println(customerProfile.getCustomerId() + ":" + df.format(cus.divide(total, 6, BigDecimal.ROUND_HALF_UP)));
        }

    }
}
