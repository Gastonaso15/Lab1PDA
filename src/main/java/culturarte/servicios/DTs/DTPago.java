package culturarte.servicios.DTs;

import java.time.LocalDateTime;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class DTPago {
    private Long id;
    private Long colaboracionId;
    private Double monto;
    private DTTipoFormaPago formaPago;
    private DTTipoTarjeta tipoTarjeta;
    private String numeroTarjeta;
    private String fechaVencimiento;
    private String cvc;
    private String nombreTitularTarjeta;
    private String nombreBanco;
    private String numeroCuenta;
    private String nombreTitularTransferencia;
    private String numeroCuentaPayPal;
    private String nombreTitularPayPal;
    private LocalDateTime fechaPago;

    // Constructores
    public DTPago() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getColaboracionId() { return colaboracionId; }
    public void setColaboracionId(Long colaboracionId) { this.colaboracionId = colaboracionId; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public DTTipoFormaPago getFormaPago() { return formaPago; }
    public void setFormaPago(DTTipoFormaPago formaPago) { this.formaPago = formaPago; }

    public DTTipoTarjeta getTipoTarjeta() { return tipoTarjeta; }
    public void setTipoTarjeta(DTTipoTarjeta tipoTarjeta) { this.tipoTarjeta = tipoTarjeta; }

    public String getNumeroTarjeta() { return numeroTarjeta; }
    public void setNumeroTarjeta(String numeroTarjeta) { this.numeroTarjeta = numeroTarjeta; }

    public String getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(String fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public String getCvc() { return cvc; }
    public void setCvc(String cvc) { this.cvc = cvc; }

    public String getNombreTitularTarjeta() { return nombreTitularTarjeta; }
    public void setNombreTitularTarjeta(String nombreTitularTarjeta) { this.nombreTitularTarjeta = nombreTitularTarjeta; }

    public String getNombreBanco() { return nombreBanco; }
    public void setNombreBanco(String nombreBanco) { this.nombreBanco = nombreBanco; }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public String getNombreTitularTransferencia() { return nombreTitularTransferencia; }
    public void setNombreTitularTransferencia(String nombreTitularTransferencia) { this.nombreTitularTransferencia = nombreTitularTransferencia; }

    public String getNumeroCuentaPayPal() { return numeroCuentaPayPal; }
    public void setNumeroCuentaPayPal(String numeroCuentaPayPal) { this.numeroCuentaPayPal = numeroCuentaPayPal; }

    public String getNombreTitularPayPal() { return nombreTitularPayPal; }
    public void setNombreTitularPayPal(String nombreTitularPayPal) { this.nombreTitularPayPal = nombreTitularPayPal; }

    @XmlJavaTypeAdapter(AdaptadorLocalDateTime.class)
    public LocalDateTime getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDateTime fechaPago) { this.fechaPago = fechaPago; }
}

