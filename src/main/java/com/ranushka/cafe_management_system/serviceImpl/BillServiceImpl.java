package com.ranushka.cafe_management_system.serviceImpl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ByteBuffer;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ranushka.cafe_management_system.JWT.JwtFilter;
import com.ranushka.cafe_management_system.POJO.Bill;
import com.ranushka.cafe_management_system.constents.CafeConstant;
import com.ranushka.cafe_management_system.dao.BillDao;
import com.ranushka.cafe_management_system.service.BillService;
import com.ranushka.cafe_management_system.util.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
@Service
public class BillServiceImpl implements BillService {

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    BillDao billDao;

    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        log.info("inside generateReport--->");
        try{
            String fileName;

            if(validateRequestMap(requestMap)){

                if(requestMap.containsKey("isGenerate") && !(Boolean) requestMap.get("isGenerate")){
                    fileName = (String) requestMap.get("uuid");
                }else{
                    fileName = CafeUtils.getUUID();
                    requestMap.put("uuid", fileName);
                    insertBill(requestMap);
                }

                String data = "Name: "+requestMap.get("name") +"\n"+
                                "Contact Number: "+requestMap.get("contactNumber") +"\n"+
                                "Email: "+requestMap.get("email") +"\n"+
                                "Payment Method: "+requestMap.get("paymentMethod");

                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(CafeConstant.STORE_LOCATION+"//"+fileName+".pdf"));

                document.open();
                setRectangleInPdf(document);

                Paragraph header = new Paragraph("Cafe Management System", getFonts("Headder"));
                header.setAlignment(Element.ALIGN_CENTER);
                document.add(header);

                Paragraph paragraph = new Paragraph(data+"\n \n", getFonts("Data"));
                document.add(paragraph);

                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                addTableHeader(table);

                JSONArray jsonArray = CafeUtils.getJsonArrayFromString((String) requestMap.get("productDetails"));
                for (int i=0; i<jsonArray.length(); i++){
                    addRows(table, CafeUtils.getMapFromJson(jsonArray.getString(i)));
                }

                document.add(table);

                Paragraph footer = new Paragraph("Total : "+requestMap.get("totalAmount")+"\n"
                +"Thank you for visiting. Please visit again!!", getFonts("Data"));

                document.add(footer);
                document.close();

                return new ResponseEntity<>("{\"uuid\":\""+fileName+"\"}",HttpStatus.OK);
            }
            return CafeUtils.getResponseEntity("Required data not found !!", HttpStatus.BAD_REQUEST);

        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void addRows(PdfPTable table, Map<String, Object> data) {
        log.info("inside addRows::::");
        table.addCell((String) data.get("name"));
        table.addCell((String) data.get("category"));
        table.addCell((String) data.get("quantity"));
        table.addCell(Double.toString((Double) data.get("price")));
        table.addCell(Double.toString((Double) data.get("total")));
    }

    private void addTableHeader(PdfPTable table) {
        log.info("inside addTableHeader:::");

        Stream.of("Name", "Category", "Quantity", "Price", "Sub Total")
                .forEach(columnTitle -> {
                    PdfPCell headers = new PdfPCell();
                    headers.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    headers.setBorderWidth(2);
                    headers.setPhrase(new Phrase(columnTitle));
                    headers.setBackgroundColor(BaseColor.YELLOW);
                    headers.setHorizontalAlignment(Element.ALIGN_CENTER);
                    headers.setVerticalAlignment(Element.ALIGN_CENTER);
                    table.addCell(headers);
                });
    }

    private Font getFonts(String type) {
        log.info("inside getFonts::::");

        switch (type){

            case "Header":
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLACK);
                headerFont.setStyle(Font.BOLD);
                return headerFont;
            case "Data":
                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, BaseColor.BLACK);
                dataFont.setStyle(Font.BOLD);
                return dataFont;
            default:
                return new Font();

        }
    }

    private void setRectangleInPdf(Document document) throws DocumentException {

        log.info("inside setRectangleInPdf::::");

        Rectangle rectangle = new Rectangle(577,825, 18, 15);

        rectangle.enableBorderSide(1);
        rectangle.enableBorderSide(2);
        rectangle.enableBorderSide(4);
        rectangle.enableBorderSide(8);
        rectangle.setBorderColor(BaseColor.BLACK);
        rectangle.setBorderWidth(1);

        document.add(rectangle);
    }

    private void insertBill(Map<String, Object> requestMap) {

        try{

            Bill bill = new Bill();

            bill.setUuid((String) requestMap.get("uuid"));
            bill.setName((String) requestMap.get("name"));
            bill.setEmail((String) requestMap.get("email"));
            bill.setContactNumber((String) requestMap.get("contactNumber"));
            bill.setPaymentMethod((String) requestMap.get("paymentMethod"));
            bill.setTotal(Integer.parseInt((String) requestMap.get("totalAmount")));
            bill.setProductDetail((String) requestMap.get("productDetails"));
            bill.setCreatedBy(jwtFilter.currentUser());

            billDao.save(bill);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean validateRequestMap(Map<String, Object> requestMap) {
         return requestMap.containsKey("name") &&
                requestMap.containsKey("contactNumber") &&
                requestMap.containsKey("email") &&
                requestMap.containsKey("productDetails") &&
                requestMap.containsKey("totalAmount");
    }
}
