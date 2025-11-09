package culturarte.logica.modelos;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "accesos_sitio")
public class AccesoSitio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ip;
    @Column(length = 500)
    private String url;
    private String browser;
    private String sistemaOperativo;
    private LocalDateTime fechaHora;

    // Constructores
    public AccesoSitio() {}

    public AccesoSitio(String ip, String url, String browser, String sistemaOperativo) {
        this.ip = ip;
        this.url = url;
        this.browser = browser;
        this.sistemaOperativo = sistemaOperativo;
        this.fechaHora = LocalDateTime.now();
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