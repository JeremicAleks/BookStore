package com.internship.bookstore.utils;

import com.internship.bookstore.dto.OrderDTO;
import com.internship.bookstore.dto.OrderItemDTO;
import com.internship.bookstore.dto.OrderReportDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.stream.Stream;

@Component
public class PDFGenerator {

	private static Logger logger = LoggerFactory.getLogger(PDFGenerator.class);

	public static ByteArrayInputStream ordersPDFReport(OrderReportDTO orderReportDTO) {
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			PdfWriter.getInstance(document, out);
			document.open();

			// adding text to PDF file
			Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
			Paragraph para = new Paragraph("Orders Table", font);
			para.setAlignment(Element.ALIGN_CENTER);
			document.add(para);
			document.add(Chunk.NEWLINE);

			PdfPTable table = new PdfPTable(6);
			// adding PDF table Header
			Stream.of("Order ID", "Order Date", "User", "Book", 
					  "Book Price (amount)", "Full Price").forEach(headerTitle -> {
						PdfPCell header = new PdfPCell();
						Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
						header.setBackgroundColor(BaseColor.LIGHT_GRAY);
						header.setHorizontalAlignment(Element.ALIGN_CENTER);
						header.setBorderWidth(2);
						header.setPhrase(new Phrase(headerTitle, headFont));
						table.addCell(header);
					});

			for (OrderDTO oDTO : orderReportDTO.getOrderDTOList()) {
				for (OrderItemDTO oiDTO : oDTO.getOrderItemDTOList()) {
					// Order ID
					PdfPCell orderIdCell = new PdfPCell(new Phrase(oiDTO.getOrderId().toString()));
					orderIdCell.setPaddingLeft(2);
					orderIdCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					orderIdCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(orderIdCell);


					// Order Date
					PdfPCell orderDateCell = new PdfPCell(new Phrase(oDTO.getOrderDate()));
					orderDateCell.setPaddingLeft(2);
					orderDateCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					orderDateCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(orderDateCell);
					
					// Order Item Amount
					PdfPCell orderUserCell = new PdfPCell(new Phrase(oDTO.getUser().getUsername()));
					orderUserCell.setPaddingLeft(2);
					orderUserCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					orderUserCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(orderUserCell);
					

					// Book details
					PdfPCell orderedBookCell = new PdfPCell(new Phrase(
							oiDTO.getBookDTO().getName() + " (ID: " + oiDTO.getBookDTO().getBookId().toString() + ")"));
					orderedBookCell.setPaddingLeft(2);
					orderedBookCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					orderedBookCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(orderedBookCell);

				

					// Ordered Books Price
					PdfPCell orderBooksPriceAndAmountCell = new PdfPCell(
							new Phrase("$" + Double.toString(oiDTO.getTotalOrderedItemPrice()) + " (" +Integer.toString(oiDTO.getOrderedAmount()) + ")"));
					orderBooksPriceAndAmountCell.setPaddingLeft(2);
					orderBooksPriceAndAmountCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					orderBooksPriceAndAmountCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(orderBooksPriceAndAmountCell);

					// Order Total Price
					PdfPCell orderPriceCell = new PdfPCell(new Phrase("$" + Double.toString(oDTO.getOrderPrice())));
					orderPriceCell.setPaddingLeft(2);
					orderPriceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					orderPriceCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(orderPriceCell);

				}
			}

			document.add(table);
			document.close();
		} catch (DocumentException de) {
			logger.error(de.toString());
		}

		return new ByteArrayInputStream(out.toByteArray());
	}

}
