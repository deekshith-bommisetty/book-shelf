package com.deekshith.bookshelf.unit.service;

import com.deekshith.bookshelf.model.Order;
import com.deekshith.bookshelf.model.OrderItem;
import com.deekshith.bookshelf.model.ShippingAddress;
import com.deekshith.bookshelf.repository.OrderRepository;
import com.deekshith.bookshelf.service.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrderServiceImplTest implements OrderServiceTest{
    @Mock
    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl orderService;

    ShippingAddress shippingAddress = new ShippingAddress("227 Clarendon Street", "Syracuse", "13210", "USA");
    OrderItem orderItemOne = new OrderItem("Introduction to Algorithms", 2, "https://d1b14unh5d6w7g.cloudfront.net/0262032937.01.S001.LXXXXXXX.jpg?Expires=1649693396&Signature=cAHAJWlxMc2zz65Z7BogPJHdNFqWfpfpAa1N71iEltfLuBlsZ-1nphp6Der75x9cs7TU0TxQjVNdSex1CuBhgcAyMDEC0zHTJjKVMILMxNMhP03wqBlecFVfHUb91VsngKXLgPwPY8LWsJs36dQnDoViDUE2mH2e94H6f22b5Y8_&Key-Pair-Id=APKAIUO27P366FGALUMQ", "6253046f42fdfc45535fdceb");
    OrderItem orderItemTwo = new OrderItem("Operating System Principles", 2, "https://m.media-amazon.com/images/I/413Nwx4UyML.jpg", "62530b1f42fdfc45535fdcec");
    ArrayList<OrderItem> OrderItemsList = new ArrayList<>(List.of(orderItemOne, orderItemTwo));

    Order order = new Order("6275834ecb267a269620cce1", "625d8afcb7f94e4cd5e5b4c5", OrderItemsList, shippingAddress, "PayPal", 0.0, 0.0, 53.78);

    @Test
    public void whenGetOrder_shouldReturnOrder() {
        Mockito.when(orderRepository.findById("6275834ecb267a269620cce1")).thenReturn(Optional.ofNullable(order));
        assertEquals("6253046f42fdfc45535fdceb", orderService.getOrder("6275834ecb267a269620cce1").getOrderItems().get(0).getProductId());

    }

    @Test
    public void whenGetOrders_shouldReturnOrders() {
        List<Order> orderList = new ArrayList<Order>(List.of(order));
        Mockito.when(orderRepository.findByUser("625d8afcb7f94e4cd5e5b4c5")).thenReturn(orderList);
        assertEquals(orderList.get(0).getId(), orderService.getOrdersById("625d8afcb7f94e4cd5e5b4c5").get(0).getId());
    }
}
