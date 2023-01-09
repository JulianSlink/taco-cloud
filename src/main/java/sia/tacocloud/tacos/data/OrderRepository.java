package sia.tacocloud.tacos.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import sia.tacocloud.tacos.Order;
import sia.tacocloud.tacos.User;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Object findByUserOderByPlacedAtDesc(User user);

    Object findByUserOderByPlacedAtDesc(User user, Pageable pageable);
}
