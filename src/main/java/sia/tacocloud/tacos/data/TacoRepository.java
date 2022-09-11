package sia.tacocloud.tacos.data;

import sia.tacocloud.tacos.web.Taco;

public interface TacoRepository {
    Taco save(Taco design);
}
