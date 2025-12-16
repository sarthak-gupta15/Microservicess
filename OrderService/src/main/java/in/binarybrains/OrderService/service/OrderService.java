package in.binarybrains.OrderService.service;

import in.binarybrains.OrderService.dto.AddressDTO;
import in.binarybrains.OrderService.dto.ApiResponse;
import in.binarybrains.OrderService.dto.OrderRequestDTO;
import in.binarybrains.OrderService.model.AddressModel;
import in.binarybrains.OrderService.model.OrderDetailsModel;
import in.binarybrains.OrderService.repository.AddressRepo;
import in.binarybrains.OrderService.repository.OrderDetailsRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {

    @Autowired
    AddressRepo addressRepo;

    @Autowired
    OrderDetailsRepo orderDetailsRepo;

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
                    .product_Id(orderRequestDTO.getProduct_Id())
                    .address(addressModel)
                    .build();

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
}
