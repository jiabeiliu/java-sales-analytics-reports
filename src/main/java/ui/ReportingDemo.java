package ui;

import java.util.Locale;
import model.Business.Business;
import model.CustomerManagement.CustomerProfile;
import model.OrderManagement.Order;
import model.ProductManagement.Product;
import model.Supplier.Supplier;

/** Deterministic synthetic fixture for reproducing the coursework metrics. */
public final class ReportingDemo {
    private ReportingDemo() {}

    public static Business createSampleBusiness() {
        Business business = new Business("Sample Company");
        Supplier north = business.getSupplierDirectory().newSupplier("North Supply");
        Supplier south = business.getSupplierDirectory().newSupplier("South Supply");
        Product northProduct = north.getProductCatalog().newProduct("Classroom Kit", 1, 200, 50);
        Product southProduct = south.getProductCatalog().newProduct("Science Kit", 1, 200, 50);
        int[] totals = {100, 80, 60, 40, 20, 10, 0};
        for (int index = 0; index < totals.length; index++) {
            CustomerProfile customer = business.getCustomerDirectory().newCustomerProfile(
                    business.getPersonDirectory().newPerson("Demo Customer " + (index + 1)));
            if (totals[index] == 0) continue;
            Supplier supplier = index < 4 ? north : south;
            Product product = index < 4 ? northProduct : southProduct;
            Order order = business.getMasterOrderList().newOrder(customer);
            order.newOrderItem(product, totals[index], 1, supplier);
        }
        return business;
    }

    public static void main(String[] args) {
        Business business = createSampleBusiness();
        Supplier north = business.getSupplierDirectory().findSupplier("North Supply");
        System.out.println("Synthetic sales report — no real customers or transactions");
        System.out.println("Total sales: " + business.getSalesVolume());
        System.out.printf(Locale.ROOT, "Average per purchasing customer: %.2f%n", business.getAverageSpendingPerCustomer());
        System.out.printf(Locale.ROOT, "Top-five customer sales share: %.4f%n", business.getTop5SalesScore());
        System.out.printf(Locale.ROOT, "North Supply customer loyalty: %.4f%n", business.getLoyaltyScore(north));
    }
}
