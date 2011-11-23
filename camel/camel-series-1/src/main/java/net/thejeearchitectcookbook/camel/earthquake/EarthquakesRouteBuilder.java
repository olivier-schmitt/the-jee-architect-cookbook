package net.thejeearchitectcookbook.camel.earthquake;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spring.Main;

/**
 * A route builder for earthquakes datas.
 */
public class EarthquakesRouteBuilder extends RouteBuilder {

	/**
	 * A main() so we can easily run these routing rules in our IDE
	 */
	public static void main(String... args) throws Exception {
		Main.main(args);
	}

	public void configure() {

		from("quartz://dataTimer?cron=0+*+*+*+*+?").to("direct:datas");

		from("direct:datas")
		.to("http4://earthquake.usgs.gov/earthquakes/catalogs/eqs7day-M1.txt")
		.unmarshal().csv().process(new Processor() {

			public void process(Exchange exchange) throws Exception {

				Message message = exchange.getIn();

				// ...
			}
		});
		
		from("direct:datas2")
		.to("http4://earthquake.usgs.gov/earthquakes/catalogs/eqs7day-M1.txt")
		.unmarshal().csv().process(new Processor() {

			public void process(Exchange exchange) throws Exception {

				Message message = exchange.getIn();

				List<List<String>> datas = (List<List<String>>) message
						.getBody();
				// Skip header
				datas = datas.subList(1, datas.size() - 1);
				// Process my data
				for (List<String> row : datas) {
					// Process Row
					String datetime = row.get(3);
					String region = row.get(9);
					String magnitude = row.get(6);
					
				}
			}
		});
		
		/*
		
		from("direct:datas")
				.to(
						"http4://earthquake.usgs.gov/earthquakes/catalogs/eqs7day-M1.txt")
				.unmarshal().csv().log("$").process(new Processor() {

					public void process(Exchange exchange) throws Exception {

						Message message = exchange.getIn();

						List<List<String>> datas = (List<List<String>>) message
								.getBody();
						// Skip header
						datas = datas.subList(1, datas.size() - 1);
						// Process my data
						for (List<String> row : datas) {
							// Process Row
							String datetime = row.get(3);
							String region = row.get(9);
							String magnitude = row.get(6);
							
						}
					}
				});
*/
	}
}
