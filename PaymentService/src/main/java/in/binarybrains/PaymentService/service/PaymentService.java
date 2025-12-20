package in.binarybrains.PaymentService.service;

import in.binarybrains.PaymentService.dto.ApiResponse;
import in.binarybrains.PaymentService.dto.PaymentRequestDTO;
import in.binarybrains.PaymentService.model.OrderTransaction;
import in.binarybrains.PaymentService.model.User;
import in.binarybrains.PaymentService.repository.OrderTransactionRepo;
import in.binarybrains.PaymentService.repository.UserRepo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
