package culturarte.servicios.DTs;

import java.time.LocalDateTime;

public class DTAccesoSitio {
    private Long id;
    private String ip;
    private String url;
    private String browser;
    private String sistemaOperativo;
    private LocalDateTime fechaHora;

    // Constructores
    public DTAccesoSitio() {}

    public DTAccesoSitio(Long id, String ip, String url, String browser,
                         String sistemaOperativo, LocalDateTime fechaHora) {
        this.id = id;
        this.ip = ip;
        this.url = url;
        this.browser = browser;
        this.sistemaOperativo = sistemaOperativo;
        this.fechaHora = fechaHora;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getBrowser() { return browser; }
    public void setBrowser(String browser) { this.browser = browser; }

    public String getSistemaOperativo() { return sistemaOperativo; }
    public void setSistemaOperativo(String sistemaOperativo) { this.sistemaOperativo = sistemaOperativo; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
}