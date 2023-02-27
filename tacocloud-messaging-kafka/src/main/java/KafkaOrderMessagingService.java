import org.springframework.kafka.core.KafkaTemplate;
import tacos.Order;

public class KafkaOrderMessagingService implements OrderMessagingService {
    private KafkaTemplate<String, Order> kafkaTemplate;

    public KafkaOrderMessagingService(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @Override
    public void sendOrder(Order order) {
        kafkaTemplate.send("tacocloud.orders.topic", order);
    }

//    在yml中配置了默认主题后，可以直接使用sendDefault()
//    @Override
//    public void sendOrder(Order order) {
//        kafkaTemplate.sendDefault(order);
//    }
}
