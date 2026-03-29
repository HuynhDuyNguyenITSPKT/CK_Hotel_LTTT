package hcmute.system.hotel.cknhom11qlhotel.model.dto.manager;

import java.util.List;

public class ManagerBookingTrendDto {
    private final List<String> labels;
    private final List<Long> values;

    public ManagerBookingTrendDto(List<String> labels, List<Long> values) {
        this.labels = labels;
        this.values = values;
    }

    public List<String> getLabels() {
        return labels;
    }

    public List<Long> getValues() {
        return values;
    }
}
