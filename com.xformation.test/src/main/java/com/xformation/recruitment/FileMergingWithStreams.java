package com.xformation.recruitment;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

public final class FileMergingWithStreams implements FileMerging {

	public final void merge(String products, String pricesByDate, String allPricesOutput) {
		
		createAFileIfItDoesNotExist(allPricesOutput);
		
		try (Stream<String> productsStream = getStreamFromAFile(products);
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(allPricesOutput), Charset.defaultCharset(), StandardOpenOption.APPEND)) {
			
			productsStream.map(product -> product.split(","))
				.map(product -> product[0].trim() + ", " + product[1].trim() + getStreamFromAFile(pricesByDate)
					.map(price -> price.split(","))
					.filter(price -> price[0].trim().equals(product[0].trim()))
					.map(price -> price[2].trim())
					.reduce("", (a, b) -> a + ", " + b))
				.forEach(line -> writeToAFile(writer, line));	
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}	

	private void createAFileIfItDoesNotExist(String file) {
		Path pathToAFile = Paths.get(file);
		if (!Files.exists(pathToAFile)) {
			try {
				Files.createFile(pathToAFile);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}	
	}
	
	private Stream<String> getStreamFromAFile(String pricesByDate) {
		try {
			return Files.lines(Paths.get(pricesByDate), Charset.defaultCharset());
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}		
	}
	
	private void writeToAFile(BufferedWriter writer, String line) {		
		try {			
			writer.write(line + System.lineSeparator());
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
