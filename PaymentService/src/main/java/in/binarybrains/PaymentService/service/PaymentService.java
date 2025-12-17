package in.binarybrains.PaymentService.service;

import in.binarybrains.PaymentService.dto.ApiResponse;
import in.binarybrains.PaymentService.dto.PaymentRequestDTO;
import in.binarybrains.PaymentService.model.User;
import in.binarybrains.PaymentService.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    UserRepo userRepo;

    public ApiResponse doPayment(PaymentRequestDTO paymentRequestDTO){
        Optional<User> userOptional =userRepo.findById(paymentRequestDTO.getUserId());
        User user = null;
        ApiResponse response = null;
        if(userOptional.isPresent()){
            user = userOptional.get();
            if(user.getBalance().doubleValue() > paymentRequestDTO.getAmount()) {
//                check bal
                Double bal = user.getBalance().doubleValue();
                bal -= paymentRequestDTO.getAmount();
                user.setBalance(new BigDecimal(bal));
//                update bal save
                userRepo.save(user);
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

//        long id = Long.parseLong(""+paymentRequestDTO.getUserId());
        return null;
    }
}
