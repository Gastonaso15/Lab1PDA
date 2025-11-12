package culturarte.logica.modelos;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "colaboracion_id", unique = true)
    private Colaboracion colaboracion;
    private Double monto;
    @Enumerated(EnumType.STRING)
    private TipoFormaPago formaPago;

    @Enumerated(EnumType.STRING)
    private TipoTarjeta tipoTarjeta;
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
    public Pago() {}

    public Pago(Colaboracion colaboracion, Double monto, TipoFormaPago formaPago, LocalDateTime fechaPago) {
        this.colaboracion = colaboracion;
        this.monto = monto;
        this.formaPago = formaPago;
        this.fechaPago = fechaPago;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Colaboracion getColaboracion() { return colaboracion; }
    public void setColaboracion(Colaboracion colaboracion) { this.colaboracion = colaboracion; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public TipoFormaPago getFormaPago() { return formaPago; }
    public void setFormaPago(TipoFormaPago formaPago) { this.formaPago = formaPago; }

    public TipoTarjeta getTipoTarjeta() { return tipoTarjeta; }
    public void setTipoTarjeta(TipoTarjeta tipoTarjeta) { this.tipoTarjeta = tipoTarjeta; }

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

    public LocalDateTime getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDateTime fechaPago) { this.fechaPago = fechaPago; }
}

