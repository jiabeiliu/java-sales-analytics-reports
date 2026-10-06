package model.Business;

import static org.junit.Assert.assertEquals;

import model.CustomerManagement.CustomerProfile;
import model.Supplier.Supplier;
import org.junit.Test;
import ui.ReportingDemo;

public class BusinessMetricsTest {
    @Test
    public void syntheticFixtureProducesReproducibleMetrics() {
        Business business = ReportingDemo.createSampleBusiness();
        Supplier north = business.getSupplierDirectory().findSupplier("North Supply");
        assertEquals(310, business.getSalesVolume());
        assertEquals(310.0 / 6, business.getAverageSpendingPerCustomer(), 0.000001);
        assertEquals(300.0 / 310, business.getTop5SalesScore(), 0.000001);
        assertEquals(4.0 / 7, business.getLoyaltyScore(north), 0.000001);
        assertEquals("Demo Customer 1", business.getCustomerDirectory().getCustomerList().get(0).getCustomerId());
    }

    @Test
    public void emptyBusinessHasDefinedZeroMetrics() {
        Business business = new Business("Empty");
        assertEquals(0.0, business.getAverageSpendingPerCustomer(), 0.0);
        assertEquals(0.0, business.getTop5SalesScore(), 0.0);
        assertEquals(0.0, business.getLoyaltyScore(new Supplier("Any")), 0.0);
    }

    @Test
    public void nonPurchasingCustomerDoesNotChangeAverage() {
        Business business = ReportingDemo.createSampleBusiness();
        CustomerProfile extra = business.getCustomerDirectory().newCustomerProfile(
                business.getPersonDirectory().newPerson("Extra nonbuyer"));
        assertEquals(0, extra.getTotalSales());
        assertEquals(310.0 / 6, business.getAverageSpendingPerCustomer(), 0.000001);
    }
}
