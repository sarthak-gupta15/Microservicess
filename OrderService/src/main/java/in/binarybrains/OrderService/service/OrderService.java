package in.binarybrains.OrderService.service;

import in.binarybrains.OrderService.dto.*;
import in.binarybrains.OrderService.model.AddressModel;
import in.binarybrains.OrderService.model.OrderDetailsModel;
import in.binarybrains.OrderService.model.ProductModel;
import in.binarybrains.OrderService.repository.AddressRepo;
import in.binarybrains.OrderService.repository.OrderDetailsRepo;
import in.binarybrains.OrderService.repository.ProductsRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class OrderService {

    @Autowired
    AddressRepo addressRepo;

    @Autowired
    OrderDetailsRepo orderDetailsRepo;

    @Autowired
    ProductsRepo productsRepo;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public ApiResponse placeOrder(OrderRequestDTO orderRequestDTO) {
        try {
            //        AddressModel addressModel = new AddressModel();
//        addressModel.getPlotNum(orderRequestDTO.getAddress().getPlotNum());
            AddressDTO addressDTO = orderRequestDTO.getAddress();
            AddressModel addressModel = AddressModel.builder()
                    .plotNum(addressDTO.getPlotNum())
                    .addressLine1(addressDTO.getAddressLine1())
                    .addressLine2(addressDTO.getAddressLine2())
                    .pinCode(addressDTO.getPinCode())
                    .city(addressDTO.getCity())
                    .state(addressDTO.getState())
                    .mob(addressDTO.getMob()).build();

//        OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
            OrderDetailsModel orderDetailsModel = OrderDetailsModel.builder()
                    .user_Id(orderRequestDTO.getUser_Id())
                    .product_Id(orderRequestDTO.getProduct_Id().toString())
                    .address(addressModel)
                    .build();

//            amount using product id from database (table products  )
            Double amount = null;
            Optional<ProductModel> opProduct = productsRepo.findById(orderRequestDTO.getProduct_Id());
            if(opProduct.isPresent()){
                ProductModel productModel = opProduct.get();
                amount= productModel.getPrice().doubleValue();
            }
            else{
                throw new RuntimeException("product not found");
            }
//            api call payment
//            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<PaymentRequestDTO> request = new HttpEntity<>(PaymentRequestDTO.builder()
                    .userId(Long.parseLong(orderRequestDTO.getUser_Id()))
                    .productId(orderRequestDTO.getProduct_Id().toString())
                    .amount(amount)
                    .build()); // request body
            ResponseEntity<Object> response = restTemplate
                    .exchange("http://127.0.0.1:7070/payment", HttpMethod.POST, request, Object.class);



//            if api call is success then oly we place order
            if(response.getStatusCode()== HttpStatus.OK){
                orderDetailsModel.setOrderStatus("Success");
                addressRepo.save(addressModel);
                orderDetailsRepo.save(orderDetailsModel);

            }
            else{
                orderDetailsModel.setOrderStatus("Failed");
                orderDetailsModel.setRemark("payment failed");
                addressRepo.save(addressModel);
                orderDetailsRepo.save(orderDetailsModel);
                ApiResponse apiResponse = ApiResponse.builder()
                        .message("Order Failed")
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .result("Failed").build();
                return apiResponse;
            }
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Order Place Successfully")
                    .code(HttpStatus.OK.value())
                    .result("Success").build();
            return apiResponse;

        } catch (Exception ex) {
            log.error("Exception : {}", ex.getMessage());
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Error occur while placing Order")
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .result("Failed").build();
            return apiResponse;
        }
    }

    public ApiResponse placeOrderWithKafka(OrderRequestDTO orderRequestDTO) {
        try {
            //        AddressModel addressModel = new AddressModel();
//        addressModel.getPlotNum(orderRequestDTO.getAddress().getPlotNum());
            AddressDTO addressDTO = orderRequestDTO.getAddress();
            AddressModel addressModel = AddressModel.builder()
                    .plotNum(addressDTO.getPlotNum())
                    .addressLine1(addressDTO.getAddressLine1())
                    .addressLine2(addressDTO.getAddressLine2())
                    .pinCode(addressDTO.getPinCode())
                    .city(addressDTO.getCity())
                    .state(addressDTO.getState())
                    .mob(addressDTO.getMob()).build();

//        OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
           UUID orderId =  UUID.randomUUID();

            OrderDetailsModel orderDetailsModel = OrderDetailsModel.builder()
                    .orderId(orderId)
                    .user_Id(orderRequestDTO.getUser_Id())
                    .product_Id(orderRequestDTO.getProduct_Id().toString())
                    .address(addressModel)
                    .build();

//            amount using product id from database (table products  )
            Double amount = null;
            Optional<ProductModel> opProduct = productsRepo.findById(orderRequestDTO.getProduct_Id());
            if(opProduct.isPresent()){
                ProductModel productModel = opProduct.get();
                amount= productModel.getPrice().doubleValue();
            }
            else{
                throw new RuntimeException("product not found");
            }
   PaymentRequestDTO paymentRequestDTO =PaymentRequestDTO.builder()
           .orderId(orderId)
                    .userId(Long.parseLong(orderRequestDTO.getUser_Id()))
                    .productId(orderRequestDTO.getProduct_Id().toString())
                    .amount(amount).build();

            String json = objectMapper.writeValueAsString(paymentRequestDTO);

//      produce message for kafka so payment service can process the order
            kafkaTemplate.send("order-place", json);
//            order service -> kafka (order-place) -> paymentservice

                orderDetailsModel.setOrderStatus("Pending");
                addressRepo.save(addressModel);
                orderDetailsRepo.save(orderDetailsModel);

            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Order Place Successfully")
                    .code(HttpStatus.OK.value())
                    .result("Success").build();
            return apiResponse;

        } catch (Exception ex) {
            log.error("Exception : {}", ex.getMessage());
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Error occur while placing Order")
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .result("Failed").build();
            return apiResponse;
        }
    }

    @KafkaListener(topics = "payment-response")
    public void paymentResponse(String json) {
        log.info("received the message from kafka for payment status");

        try {
            OrderKafkaDTO orderDetails =
                    objectMapper.readValue(json, OrderKafkaDTO.class);

            Optional<OrderDetailsModel> orderOP =
                    orderDetailsRepo.findById(orderDetails.getOrderId());

            if (orderOP.isPresent()) {
                OrderDetailsModel order = orderOP.get();
                order.setRemark(orderDetails.getRemark());
                order.setOrderStatus(orderDetails.getOrderStatus());
                orderDetailsRepo.save(order);

                log.info("order payment status updated for orderId={}",
                        orderDetails.getOrderId());
            } else {
                log.warn("order not found for orderId={}",
                        orderDetails.getOrderId());
            }

        } catch (Exception e) {
            log.error("Failed to process payment-response message: {}", json, e);
        }
    }

}
