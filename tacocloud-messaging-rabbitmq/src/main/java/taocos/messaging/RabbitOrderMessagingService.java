package taocos.messaging;

import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tacos.Order;

@Service
public class RabbitOrderMessagingService implements OrderMessagingService {
    private RabbitTemplate rabbit;

    @Autowired
    public RabbitOrderMessagingService(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    //    使用convertAndSend()让RabbitTemplate处理转换操作
    @Override
    public void sendOrder(Order order) {
        rabbit.convertAndSend("tacocloud.order", order,
                message -> {
                    MessageProperties props = message.getMessageProperties();
                    props.setHeader("X_ORDER_SOURCE", "WEB");
                    return message;
                });
    }

//    通过MessageConverter将Order转换为Message对象
//    必须通过MessageProperties来提供消息属性
//    如果不需要设置使用默认的MessageProperties即可
//    @Override
//    public void sendOrder(Order order) {
//        MessageConverter converter = rabbit.getMessageConverter();
//        MessageProperties props = new MessageProperties();
//        props.setHeader("X_ORDER_SOURCE","WEB");
//        Message message = converter.toMessage(order, props);
//        rabbit.send("tacocloud.order", message);
//    }
}
