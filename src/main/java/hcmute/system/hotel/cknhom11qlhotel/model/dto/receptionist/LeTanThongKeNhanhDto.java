package hcmute.system.hotel.cknhom11qlhotel.model.dto.receptionist;

public class LeTanThongKeNhanhDto {
    private final String tieuDe;
    private final String giaTri;
    private final String moTa;
    private final String iconCss;
    private final String gradientCss;

    public LeTanThongKeNhanhDto(String tieuDe,
                                String giaTri,
                                String moTa,
                                String iconCss,
                                String gradientCss) {
        this.tieuDe = tieuDe;
        this.giaTri = giaTri;
        this.moTa = moTa;
        this.iconCss = iconCss;
        this.gradientCss = gradientCss;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public String getGiaTri() {
        return giaTri;
    }

    public String getMoTa() {
        return moTa;
    }

    public String getIconCss() {
        return iconCss;
    }

    public String getGradientCss() {
        return gradientCss;
    }
}
