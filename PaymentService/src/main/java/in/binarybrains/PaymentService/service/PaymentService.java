package in.binarybrains.PaymentService.service;

import in.binarybrains.PaymentService.dto.ApiResponse;
import in.binarybrains.PaymentService.dto.OrderKafkaDTO;
import in.binarybrains.PaymentService.dto.PaymentRequestDTO;
import in.binarybrains.PaymentService.model.OrderTransaction;
import in.binarybrains.PaymentService.model.User;
import in.binarybrains.PaymentService.repository.OrderTransactionRepo;
import in.binarybrains.PaymentService.repository.UserRepo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class PaymentService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    OrderTransactionRepo orderTransactionRepo;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;



    @Transactional
    public ApiResponse doPayment(PaymentRequestDTO paymentRequestDTO){
      Optional<User> userOptional =userRepo.findById(paymentRequestDTO.getUserId());
      User user = null;
      ApiResponse response = null;
  try{
      if(userOptional.isPresent()){
          user = userOptional.get();
          log.info("bal is {}", user.getBalance());
          Double bal = user.getBalance().doubleValue();
          log.info("bal is {}", bal);
          if(user.getBalance().doubleValue() > paymentRequestDTO.getAmount()) {
//                check bal
//              Double bal = user.getBalance().doubleValue();
              bal -= paymentRequestDTO.getAmount();
              user.setBalance(new BigDecimal(bal));
//                update bal save
              userRepo.save(user);

              OrderTransaction transaction = OrderTransaction.builder()
                      .amount(paymentRequestDTO.getAmount().toString())
                      .userId(""+paymentRequestDTO.getUserId())
                      .status("Completed")
                      .productId(paymentRequestDTO.getProductId())
                      .createdAt(LocalDateTime.now())
                      .updatedAt(LocalDateTime.now())
                      .build();

              orderTransactionRepo.save(transaction);

              response = ApiResponse.builder()
                      .code(HttpStatus.OK.value())
                      .message("Payment completed successfully")
                      .result("Success")
                      .build();
              return response;


          }
          else {
              response = ApiResponse.builder()
                      .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                      .message("Not Enough Balance")
                      .result("Failed")
                      .build();
              return response;
          }
      }
      else {
          response = ApiResponse.builder()
                  .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                  .message("Invalid User Id")
                  .result("Failed")
                  .build();
          return response;
      }
  }catch (Exception e){
      log.error("Exception : {}", e.getMessage());
//      response = ApiResponse.builder()
//              .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
//              .message("Error : "+e.getMessage())
//              .result("Failed")
//              .build();
//      return response;
     throw new RuntimeException(e.getMessage());
  }

//        long id = Long.parseLong(""+paymentRequestDTO.getUserId());
//        return null;
    }

//    @Transactional
    @KafkaListener(topics = "order-place")
    public void doPaymentConsumer(String json){
        log.info("Kafka message recieved : {}", json);
        PaymentRequestDTO paymentRequestDTO = objectMapper.readValue(json, PaymentRequestDTO.class);
        Optional<User> userOptional =userRepo.findById(paymentRequestDTO.getUserId());
        User user = null;
        ApiResponse response = null;
        try{
            if(userOptional.isPresent()){
                user = userOptional.get();
                log.info("bal is {}", user.getBalance());
                Double bal = user.getBalance().doubleValue();
                log.info("bal is {}", bal);
                if(user.getBalance().doubleValue() > paymentRequestDTO.getAmount()) {
//                check bal
//              Double bal = user.getBalance().doubleValue();
                    bal -= paymentRequestDTO.getAmount();
                    user.setBalance(new BigDecimal(bal));
//                update bal save
                    userRepo.save(user);

                    OrderTransaction transaction = OrderTransaction.builder()
                            .amount(paymentRequestDTO.getAmount().toString())
                            .userId(""+paymentRequestDTO.getUserId())
                            .status("Completed")
                            .productId(paymentRequestDTO.getProductId())
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build();

                    orderTransactionRepo.save(transaction);

                    OrderKafkaDTO orderDetails = OrderKafkaDTO.builder()
                            .orderId(paymentRequestDTO.getOrderId())
                            .remark("Payment Successfully Processed")
                            .orderStatus("Success").build();

                    String orderDetailsStr = objectMapper.writeValueAsString(orderDetails);

                    sendMessage(orderDetailsStr);


//                    response = ApiResponse.builder()
//                            .code(HttpStatus.OK.value())
//                            .message("Payment completed successfully")
//                            .result("Success")
//                            .build();
//                    return response;


                }
                else {
                    OrderKafkaDTO orderDetails = OrderKafkaDTO.builder()
                            .orderId(paymentRequestDTO.getOrderId())
                            .remark("Balance Not Enough")
                            .orderStatus("Failed").build();
                    String orderDetailsStr = objectMapper.writeValueAsString(orderDetails);
                    sendMessage(orderDetailsStr);
                }
            }
            else {
//                response = ApiResponse.builder()
//                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                        .message("Invalid User Id")
//                        .result("Failed")
//                        .build();
//                return response;
                OrderKafkaDTO orderDetails = OrderKafkaDTO.builder()
                        .orderId(paymentRequestDTO.getOrderId())
                        .remark("Invalid User ID")
                        .orderStatus("Failed").build();
                String orderDetailsStr = objectMapper.writeValueAsString(orderDetails);
                sendMessage(orderDetailsStr);
            }
        }catch (Exception e){
            log.error("Exception : {}", e.getMessage());
//      response = ApiResponse.builder()
//              .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
//              .message("Error : "+e.getMessage())
//              .result("Failed")
//              .build();
//      return response;
            OrderKafkaDTO orderDetails = OrderKafkaDTO.builder()
                    .orderId(paymentRequestDTO.getOrderId())
                    .remark("Internal error occur at : "+LocalDateTime.now() )
                    .orderStatus("Failed").build();
            String orderDetailsStr = objectMapper.writeValueAsString(orderDetails);
            sendMessage(orderDetailsStr);
//            throw new RuntimeException(e.getMessage());
        }

//        long id = Long.parseLong(""+paymentRequestDTO.getUserId());
//        return null;
    }

    void sendMessage(String message ){
        kafkaTemplate.send("payment-response", message);
    }
}
