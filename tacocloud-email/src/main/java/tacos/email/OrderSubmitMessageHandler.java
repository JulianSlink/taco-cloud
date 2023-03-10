package tacos.email;

import org.springframework.integration.handler.GenericHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class OrderSubmitMessageHandler implements GenericHandler<Order> {
    private RestTemplate rest;
    private ApiProperties apiProps;

    public OrderSubmitMessageHandler(RestTemplate rest, ApiProperties apiProps) {
        this.rest = rest;
        this.apiProps = apiProps;
    }

    @Override
    public Object handle(Order order, Map<String, Object> map) {
        rest.postForObject(apiProps.getUrl(), order, String.class);
        return null;
    }
}
