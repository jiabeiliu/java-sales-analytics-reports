/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.util.Scanner;

import model.Business.Business;
import model.Business.ConfigureABusiness;
import model.OrderManagement.MasterOrderList;
import model.OrderManagement.MasterOrderReport;
import model.ProductManagement.ProductCatalog;
import model.ProductManagement.ProductsReport;
import model.Supplier.Supplier;
import model.Supplier.SupplierDirectory;

/**
 *
 * @author kal bugrara
 */
public class DigitalMarketingApplication {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    // TODO code application logic here

    // 1. Populate the model +

    Business business = ConfigureABusiness.createABusinessAndLoadALotOfData("Xerox", 50, 10, 30, 100, 10);

    SupplierDirectory sd = business.getSupplierDirectory();

    MasterOrderList mol = business.getMasterOrderList();
    MasterOrderReport orderReport = mol.generateMasterOrderReport();

    // 2. Maybe some interaction with the user (optional)

    Scanner sc = new Scanner(System.in);

    boolean exitCode = false;

    while (!exitCode) {
      System.out.println("Welcome to Sunday Lab demo app. Please pick an option:");
      System.out.println("1. Print Product Performance Report");
      System.out.println("2. Print Master Order Report");
      System.out.println("3. Exit");

      String input = sc.next();

      // System.out.println(input);

      if (input.equals("1")) {
        Supplier randomSupplier = sd.pickRandomSupplier();
        ProductCatalog pd = randomSupplier.getProductCatalog();
        ProductsReport myFirstReport = pd.generateProductPerformanceReport("Name");
        myFirstReport.printProductReport();

      }

      if (input.equals("2"))
        orderReport.printOrderReport();

      if (input.equals("3"))
        exitCode = true;
    }

    System.out.println("Thank you, have a nice day.");

    // 3. Show some analytics (Summarizing, comparing, sorting, grouping data by
    // some criteria)

    // business.printShortInfo();

    // Faker magicBox = new Faker();

    // System.out.println("================== First, Last name ==================
    // ");
    // for (int index=0; index < 50; index++){
    // String fullName = magicBox.name().fullName();
    // System.out.println(fullName);
    // }

    // System.out.println("================== Companies ================== ");

    // for (int index=0; index < 50; index++){
    // String companyName = magicBox.company().name();
    // System.out.println(companyName);
    // }

    // System.out.println("================== Products ================== ");

    // for (int index=0; index < 50; index++){
    // String product = magicBox.commerce().productName();
    // System.out.println(product);
    // }

    // System.out.println("================== Yoda Quotes ================== ");

    // for (int index=0; index < 50; index++){
    // String quote = magicBox.yoda().quote();
    // System.out.println(quote);
    // }

    sc.close();

  }
}
