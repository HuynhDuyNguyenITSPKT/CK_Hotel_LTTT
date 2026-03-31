package hcmute.system.hotel.cknhom11qlhotel.model.dto.admin;

import java.math.BigDecimal;
import java.util.List;

public class RevenueChartResponse {
    private List<String> labels;
    private List<BigDecimal> values;

    public RevenueChartResponse() {
    }

    public RevenueChartResponse(List<String> labels, List<BigDecimal> values) {
        this.labels = labels;
        this.values = values;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<BigDecimal> getValues() {
        return values;
    }

    public void setValues(List<BigDecimal> values) {
        this.values = values;
    }
}
