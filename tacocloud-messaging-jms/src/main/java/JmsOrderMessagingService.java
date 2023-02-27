import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import tacos.Order;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

@Service
public class JmsOrderMessagingService implements OrderMessagingService {
    private JmsTemplate jms;
    //将消息放松至默认目的地之外的地方，可以通过为send()设置参数指定
    //方式1传递Destination对象
    private Destination orderQueue;

    @Autowired
    public JmsOrderMessagingService(JmsTemplate jms, Destination orderQueue) {
        this.jms = jms;
        this.orderQueue = orderQueue;
    }

    @Autowired
    public JmsOrderMessagingService(JmsTemplate jms) {
        this.jms = jms;
    }

    private Message addOrderSource(Message message) throws JMSException {
        message.setStringProperty("X_ORDER_SOURCE", "WEB");
        return message;
    }

    @Override
    public void sendOrder(Order order) {
        jms.convertAndSend("tacocloud.order.queue", order, this::addOrderSource);
    }

//    通过MessagePostProcessor添加X_ORDER_SOURCE头信息
//    @Override
//    public void sendOrder(Order order) {
//        jms.convertAndSend("tacocloud.order.queue", order, message -> {
//            message.setStringProperty("X_ORDER_SOURCE", "WEB");
//            return message;
//        });
//    }

    //convertAndSend()简化消息发布，它不需要MessageCreator
    //接受一个Destination对象或String值来确定目的地
//    @Override
//    public void sendOrder(Order order) {
//        jms.convertAndSend("tacocloud.order.queue", order);
//    }

    //在实践中指定目的地名更为常见
//    @Override
//    public void sendOrder(Order order) {
//        jms.send("tacocloud.order.queue", session -> session.createObjectMessage(order));
//    }

    //发送到默认目的地
//    @Override
//    public void sendOrder(Order order) {
//        jms.send(session -> session.createObjectMessage(order));
//    }

    //方式1传递Destination对象
//    @Override
//    public void sendOrder(Order order) {
//        jms.send(orderQueue, session -> session.createObjectMessage(order));
//    }
}
