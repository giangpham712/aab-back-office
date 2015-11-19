package com.aabplastic.backoffice.controllers.mvc;

import com.aabplastic.backoffice.excel.ProductionSheetExcelExporter;
import com.aabplastic.backoffice.excel.models.ProductionSheetOrder;
import com.aabplastic.backoffice.excel.models.ProductionSheetOrderItem;
import com.aabplastic.backoffice.exceptions.ResourceNotFoundException;
import com.aabplastic.backoffice.locale.MessageByLocaleService;
import com.aabplastic.backoffice.models.*;
import com.aabplastic.backoffice.models.dto.OrderDto;
import com.aabplastic.backoffice.services.CustomerService;
import com.aabplastic.backoffice.services.OrderService;
import com.aabplastic.backoffice.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class OrdersController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private MessageByLocaleService messageByLocaleService;

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String listOrders(Model model) throws Exception {

        int page = 1;
        int limit = 1000;
        String search = "";

        Page<Order> orders = orderService.listOrders(search, page, limit, "orderNumber", Sort.Direction.ASC);

        Iterable<Customer> customers = customerService.getAllCustomers();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));

        String jsonOrders = objectMapper.writeValueAsString(orders.getContent());
        String jsonCustomers = objectMapper.writeValueAsString(customers);

        model.addAttribute("totalPages", orders.getTotalPages());
        model.addAttribute("customers", jsonCustomers);
        model.addAttribute("orders", jsonOrders);
        model.addAttribute("page", page);
        model.addAttribute("limit", limit);

        return "list-orders";
    }

    @RequestMapping(value = "/orders/new", method = RequestMethod.GET)
    public String newOrder(Model model) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));

        Iterable<Customer> customers = customerService.getAllCustomers();
        String jsonCustomers = objectMapper.writeValueAsString(customers);

        Order order = new Order();

        if (Iterators.size(customers.iterator()) > 0) {
            order.setCustomerId(Iterators.get(customers.iterator(), 0).getId());
        }

        order.getItems().add(new OrderItem());
        String jsonOrder = objectMapper.writeValueAsString(order);

        model.addAttribute("headerTitle", "New order");
        model.addAttribute("customers", jsonCustomers);
        model.addAttribute("order", jsonOrder);
        model.addAttribute("estimate", objectMapper.writeValueAsString(null));

        model.addAttribute("mode", "new");

        return "edit-order";
    }

    @RequestMapping(value = "/orders/edit/{id}", method = RequestMethod.GET)
    public String editOrder(Model model, @PathVariable long id) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));

        OrderDto order = orderService.getOrderById(id);

        if (order == null) {
            throw new ResourceNotFoundException("Not found");
        }

        String jsonOrder = objectMapper.writeValueAsString(order);

        Iterable<Customer> customers = customerService.getAllCustomers();
        String jsonCustomers = objectMapper.writeValueAsString(customers);

        Estimate estimate = orderService.getEstimateByOrderId(order.getId());
        String jsonEstimate = objectMapper.writeValueAsString(estimate);

        model.addAttribute("order", jsonOrder);
        model.addAttribute("headerTitle", order.getOrderNumber());
        model.addAttribute("customers", jsonCustomers);
        model.addAttribute("estimate", jsonEstimate);

        model.addAttribute("mode", "edit");
        return "edit-order";
    }

    @RequestMapping(value = "/orders/productionsheet/{id}", method = RequestMethod.GET)
    public String productionSheet(Model model, @PathVariable long id) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));

        OrderDto order = orderService.getOrderById(id);

        if (order == null) {
            throw new ResourceNotFoundException("Not found");
        }

        Iterable<Product> products = productService.listProducts();
        Map<Long, Product> productMap = StreamSupport.stream(products.spliterator(), false).collect(Collectors.toMap(Product::getId, Function.identity()));

        ProductionSheetOrder productionSheetOrder = buildProductionSheetOrder(order, order.getOrderNumber(), productMap);

        String jsonOrder = objectMapper.writeValueAsString(order);
        String jsonProductionSheetOrder = objectMapper.writeValueAsString(productionSheetOrder);

        model.addAttribute("headerTitle", MessageFormat.format("{0} - Production sheet", order.getOrderNumber()));
        model.addAttribute("order", jsonOrder);
        model.addAttribute("productionSheetOrder", jsonProductionSheetOrder);
        model.addAttribute("message", messageByLocaleService);

        return "production-sheet";
    }

    @RequestMapping(value = "/orders/productionsheet/{id}", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded" )
    public void generateProductionSheet(@PathVariable long id, @RequestParam String orderName, HttpServletResponse response) throws IOException {

        OrderDto order = orderService.getOrderById(id);
        if (order == null) {
            throw new ResourceNotFoundException("Not found");
        }

        Iterable<Product> products = productService.listProducts();
        Map<Long, Product> productMap = StreamSupport.stream(products.spliterator(), false).collect(Collectors.toMap(Product::getId, Function.identity()));

        ProductionSheetOrder productionSheetOrder = buildProductionSheetOrder(order, orderName, productMap);

        ProductionSheetExcelExporter exporter = new ProductionSheetExcelExporter();

        String filename = exporter.exportExcelFile(productionSheetOrder);

        File file = new File("temp/" + filename);
        InputStream is = new FileInputStream(file);

        response.setContentType("application/octet-stream");
        // Response header
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        // Read from the file and write into the response
        OutputStream os = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        os.flush();
        os.close();
        is.close();
    }

    private ProductionSheetOrder buildProductionSheetOrder(OrderDto order, String orderName, Map<Long, Product> productMap) {

        ProductionSheetOrder productionSheetOrder = new ProductionSheetOrder();
        productionSheetOrder.setCustomer(order.getCustomer().getDisplayName());
        productionSheetOrder.setOrderName(orderName);
        productionSheetOrder.setOrderNumber(order.getOrderNumber());
        productionSheetOrder.setProductionSheetOrderItems(order.getItems().stream().map(item -> {

            ProductionSheetOrderItem productionSheetOrderItem = new ProductionSheetOrderItem();

            productionSheetOrderItem.setProductionSheetOrder(productionSheetOrder);
            productionSheetOrderItem.setProductId(String.valueOf(item.getProductId()));
            productionSheetOrderItem.setProductName(productMap.get(item.getProductId()).getName());

            NumberFormat formatter1 = new DecimalFormat("#0.000");
            NumberFormat formatter2 = new DecimalFormat("#0.00");

            productionSheetOrderItem.setThickness(formatter1.format(item.getThickness()));
            productionSheetOrderItem.setWidth(String.valueOf((int) item.getWidth()));
            productionSheetOrderItem.setBlowingWidth(String.valueOf((int) item.getBlowingWidth()));
            productionSheetOrderItem.setLength(String.valueOf((int) item.getLength()));
            productionSheetOrderItem.setEmboss(item.getEmboss());

            double gusset = (item.getBlowingWidth() - item.getWidth()) / 2;
            double actualThickness = calculateActualThickness(item.getThickness());

            double weightPerLengthUnit = actualThickness * item.getBlowingWidth() * 2 * 9 / 10;
            int lengthPerRoll = getLengthPerRoll(weightPerLengthUnit);
            double weightPerRoll = weightPerLengthUnit * lengthPerRoll / 1000;
            double unitWeight = (item.getWidth() + gusset * 2) * item.getLength() * 0.09 * actualThickness * 2 / 110 ;
            double totalWeight = unitWeight * item.getQuantity() / 1000;
            double totalBlowingWeight = totalWeight * 1.16;
            int totalRolls = (int) Math.ceil(totalBlowingWeight / weightPerRoll);

            productionSheetOrderItem.setGusset(String.valueOf(gusset));

            productionSheetOrderItem.setWeightPerLengthUnit(formatter2.format(weightPerLengthUnit));
            productionSheetOrderItem.setLengthPerRoll(String.valueOf(lengthPerRoll));
            productionSheetOrderItem.setWeightPerRoll(formatter2.format(weightPerRoll));
            productionSheetOrderItem.setTotalWeight(formatter2.format(totalWeight));
            productionSheetOrderItem.setTotalBlowingWeight(String.valueOf(totalBlowingWeight));
            productionSheetOrderItem.setTotalRolls(String.valueOf(totalRolls));


            return productionSheetOrderItem;

        }).collect(Collectors.toList()));

        return productionSheetOrder;
    }

    private double calculateActualThickness(double thickness) {
        double tolerance;
        if (thickness < 0.013) {
            tolerance = 0;
        } else if (thickness < 0.014) {
            tolerance = 2;
        } else {
            tolerance = 5;
        }

        return (100 - tolerance) * thickness / 100;
    }

    private int getLengthPerRoll(double weightPerLengthUnit) {
        int lengthPerRoll = 4000;
        while (lengthPerRoll * weightPerLengthUnit / 1000 > 90) {
            lengthPerRoll = lengthPerRoll - 500;
        }

        return lengthPerRoll;
    }
}
