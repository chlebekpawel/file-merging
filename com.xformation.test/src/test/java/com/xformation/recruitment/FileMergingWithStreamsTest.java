package com.xformation.recruitment;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Test;

public class FileMergingWithStreamsTest {
	
	private final FileMerging fileMerging = new FileMergingWithStreams();
	private final String products= "src\\test\\resources\\products.csv";
	private final String pricesByDate = "src\\test\\resources\\prices_by_date.csv";
	private final String allPricesExpected = "src\\test\\resources\\all_prices_expected.csv";
	private final String allPricesActual = "src\\test\\resources\\all_prices_actual.csv";		
			
	@Test
	public void testFileMerging() throws IOException {
		fileMerging.merge(products, pricesByDate, allPricesActual);
		String allPricesExpectedFile = readFileAsString(allPricesExpected);
		String allPricesActualFile = readFileAsString(allPricesActual);
		assertEquals(allPricesExpectedFile, allPricesActualFile);		
	}
	
	@After
	public void after() throws IOException {
		Files.deleteIfExists(Paths.get(allPricesActual));
	}
	
	private static String readFileAsString(String fileString) throws IOException {
        Path file = Paths.get(fileString);
        return new String(Files.readAllBytes(file));
    }

}
