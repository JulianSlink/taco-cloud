package tacos.kitchen.messaging.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import tacos.Order;

public class RabbitOrderReceiver {
    private RabbitTemplate rabbit;
    private MessageConverter converter;

    @Autowired
    public RabbitOrderReceiver(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
        this.converter = rabbit.getMessageConverter();
    }

    //   使用ParameterizedTypeReference转换器必须要实现SmartMessageConverter
//  目前Jackson2JsonMessageConverter是唯一可选的内置实现
    public Order receiveOrder() {
        return rabbit.receiveAndConvert("tacocloud.order.queue",
                new ParameterizedTypeReference<Order>() {
                });
    }

//    自动将Message对象转换为Order对象
//    public Order receiveOrder() {
//        return (Order) rabbit.receiveAndConvert("tacocloud.order.queue");
//    }

//    public Order receiveOrder() {
//        Message message = rabbit.receive("tacocloud.orders");
//        return message != null
//                ? (Order) converter.fromMessage(message)
//                : null;
//    }
}
