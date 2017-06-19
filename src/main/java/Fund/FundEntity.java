package Fund;

import java.util.Objects;

public class FundEntity {
    private final int id;
    private final String name;
    private final FundType fundType;

    public FundEntity(int id, String name, FundType fundType) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.fundType = Objects.requireNonNull(fundType, "fundType must not be null");
    }

    public int getId() {
        return id;
    }

    String getName() {
        return name;
    }

    public FundType getFundType() {
        return fundType;
    }
}