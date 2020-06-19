package com.github.scalvet.pdfsplitter.pdf;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

public class PdfActions {
	public void splitDocument(File pdf) {
		String path = pdf.getParent();
		String name = pdf.getName().toLowerCase();
		if (!name.endsWith(".pdf")) {
			throw new PdfException("Selected file must end with .pdf suffix -> " + name);
		}

		try (PDDocument document = PDDocument.load(pdf)) {

			String namePrefix = name.substring(0, name.lastIndexOf(".pdf"));
			// Instantiating Splitter class
			Splitter splitter = new Splitter();

			// splitting the pages of a PDF document
			List<PDDocument> Pages = splitter.split(document);

			// Creating an iterator
			Iterator<PDDocument> iterator = Pages.listIterator();

			// Saving each page as an individual document
			int i = 1;
			while (iterator.hasNext()) {
				PDDocument pd = iterator.next();
				pd.save(path + '/' + namePrefix + i++ + ".pdf");
			}
		} catch (IOException e) {
			throw new PdfException(e);
		}
	}
}
