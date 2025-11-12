package culturarte.servicios.DTs;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PROPERTY)
public enum DTTipoFormaPago {
    TARJETA,
    TRANSFERENCIA_BANCARIA,
    PAYPAL
}

