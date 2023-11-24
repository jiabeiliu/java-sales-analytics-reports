/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import model.Business.Business;
import model.Business.ConfigureABusiness;
import model.OrderManagement.MasterOrderList;
import model.OrderManagement.MasterOrderReport;
import model.ProductManagement.ProductCatalog;
import model.ProductManagement.ProductsReport;
import model.Supplier.Supplier;
import model.Supplier.SupplierDirectory;

import java.util.Scanner;

/**
 *
 * @author kal bugrara
 */
public class MyApplication {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    // TODO code application logic here

    Scanner sc = new Scanner(System.in);
    // 1. Populate the model +

//    Business business = ConfigureABusiness.createABusinessAndLoadALotOfData("Xerox", 50, 10, 30, 100, 10);
    Business business = ConfigureABusiness.createABusinessAndLoadRequireData("Xerox",50,30,50,300,1,3,1,10);

    // - Supplier Name
    // output: Supplier Name:
    SupplierDirectory sd = business.getSupplierDirectory();
    System.out.println("================== Supplier Name ================== ");
    sd.getSupplierList().forEach(supplier -> System.out.println(supplier.getName()));
    System.out.println("================== Supplier Name ================== ");

    // Total Sales,
    System.out.println("================== Total Sales ================== ");
    System.out.println(business.getSalesVolume());
    System.out.println("================== Total Sales ================== ");
    // - Loyalty score (Number of different customers who picked suppliers products divided by number of all customers)
    System.out.println("================== Loyalty score ================== ");
    // choose a supplier  --input supplier name
    System.out.println("Please input a supplier name: ");
    String supplierName = sc.nextLine();
    boolean isSupplierExist = false;
    while (!isSupplierExist) {
        Supplier supplier = sd.findSupplier(supplierName);
        if (supplier != null) {
            isSupplierExist = true;
            System.out.println("Supplier Name: " + supplier.getName());
            System.out.println("Loyalty Score: " + business.getLoyaltyScore(supplier));
        } else {
            System.out.println("Supplier Name: " + supplierName + " is not exist, please input again: ");
            supplierName = sc.nextLine();
        }
    }
    System.out.println("================== Loyalty score ================== ");
    //- Average spending per customer (Total sales divided by number of different customers)
    System.out.println("================== Average spending per customer ================== ");
    System.out.println(business.getAverageSpendingPerCustomer());
    System.out.println("================== Average spending per customer ================== ");
    // - Top 5 Sales Score (Total sales to top 5 Customers divided by total sales)
    System.out.println("================== Top 5 Sales Score ================== ");
      business.getTop5SalesScore();
    System.out.println("================== Top 5 Sales Score ================== ");





    sc.close();

  }
}
