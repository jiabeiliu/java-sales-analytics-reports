# Sales Analytics Reports — Java Coursework Extension

A small Java CLI project that calculates sales and customer metrics over an in-memory business model. The original domain model and random-data generator came from INFO 5001 coursework led by Kal Bugrara; this portfolio extension adds a **deterministic synthetic demo**, metric corrections, tests, and reproducible documentation. It is not a production analytics service or a full-stack application.

## Demo

`ui.ReportingDemo` seeds seven fictional customer profiles (six purchasers), two fictional suppliers, and six transactions. Running it prints:

```text
Synthetic sales report — no real customers or transactions
Total sales: 310
Average per purchasing customer: 51.67
Top-five customer sales share: 0.9677
North Supply customer loyalty: 0.5714
```

The figures are fixed for demonstration; they are not business results. The top-five metric is the **combined sales of the five highest-spending customers divided by total sales**. Loyalty is the share of all customer profiles that purchased from the selected supplier. Average spend uses purchasing customers only.

## Run

Requires Java 17 and Maven 3.8+:

```bash
mvn test
mvn -q exec:java -Dexec.mainClass=ui.ReportingDemo
```

`ui.MyApplication` remains the historical interactive coursework entry point. It generates random data, prompts for a supplier, and is not suitable for comparing fixed numeric results. The deterministic demo above is the recommended portfolio walkthrough.

## Verification

`src/test/java/model/Business/BusinessMetricsTest.java` checks the synthetic metrics, empty-business behavior, and a non-purchasing customer edge case. The commands above were verified with Java 17 and Apache Maven 3.9.16: **3 tests passed**, and the CLI printed the exact values shown above.

## Structure and limitations

- `src/main/java/model/`: coursework business, customer, supplier, order, and product objects.
- `src/main/java/ui/ReportingDemo.java`: new deterministic sample and console walkthrough.
- `src/main/java/ui/MyApplication.java`: original random-data assignment interface.
- `src/test/java/`: metric tests.

All data stays in memory. There is no persistence, authentication, REST API, real customer data, dashboard, or deployment. Currency is deliberately unspecified because the source model stores integer amounts without currency metadata. A larger backend project would need explicit money types, documented data sources, stable IDs, persistence, API boundaries, and authorization; none is claimed here.
