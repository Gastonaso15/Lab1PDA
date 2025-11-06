package culturarte.servicios.DTs;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;

/**
 * Adaptador JAXB para serializar/deserializar LocalDateTime como objeto complejo
 * con campos anio, mes, dia, hora, minuto, segundo, nanosegundo.
 */
public class AdaptadorLocalDateTime extends XmlAdapter<AdaptadorLocalDateTime.LocalDateTimeWS, LocalDateTime> {

    public static class LocalDateTimeWS {
        public int anio;
        public int mes;
        public int dia;
        public int hora;
        public int minuto;
        public int segundo;
        public int nanosegundo;
    }

    @Override
    public LocalDateTime unmarshal(LocalDateTimeWS v) throws Exception {
        if (v == null) return null;
        return LocalDateTime.of(v.anio, v.mes, v.dia, v.hora, v.minuto, v.segundo, v.nanosegundo);
    }

    @Override
    public LocalDateTimeWS marshal(LocalDateTime v) throws Exception {
        if (v == null) return null;
        LocalDateTimeWS ws = new LocalDateTimeWS();
        ws.anio = v.getYear();
        ws.mes = v.getMonthValue();
        ws.dia = v.getDayOfMonth();
        ws.hora = v.getHour();
        ws.minuto = v.getMinute();
        ws.segundo = v.getSecond();
        ws.nanosegundo = v.getNano();
        return ws;
    }
}


