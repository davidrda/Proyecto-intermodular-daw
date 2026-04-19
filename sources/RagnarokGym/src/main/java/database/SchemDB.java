package database;

public interface SchemDB {

    // CLIENTES
    String TAB_CLIENTES = "clientes";
    String CLI_ID = "id_cliente";
    String CLI_NOMBRE = "nombre";
    String CLI_APELLIDOS = "apellidos";
    String CLI_DNI = "dni";
    String CLI_EMAIL = "email";
    String CLI_TELEFONO = "telefono";
    String CLI_FECHA_ALTA = "fecha_alta";
    String CLI_FECHA_BAJA = "fecha_baja";

    // ENTRENADORES
    String TAB_ENTRENADORES = "entrenadores";
    String ENT_ID = "id_entrenador";
    String ENT_NOMBRE = "nombre";
    String ENT_APELLIDOS = "apellidos";
    String ENT_DNI = "dni";
    String ENT_EMAIL = "email";
    String ENT_TELEFONO = "telefono";
    String ENT_ESPECIALIDAD = "especialidad";
    String ENT_FECHA_CONTRATACION = "fecha_contratacion";
    String ENT_FECHA_BAJA = "fecha_baja";

    // SALAS
    String TAB_SALAS = "salas";
    String SAL_ID = "id_sala";
    String SAL_NOMBRE = "nombre";
    String SAL_TIPO = "tipo";
    String SAL_CAPACIDAD = "capacidad";

    // CLASES
    String TAB_CLASES = "clases";
    String CLA_ID = "id_clase";
    String CLA_ID_ENTRENADOR = "id_entrenador";
    String CLA_ID_SALA = "id_sala";
    String CLA_NOMBRE = "nombre";
    String CLA_NIVEL = "nivel";
    String CLA_DURACION = "duracion_minutos";

    // HORARIOS
    String TAB_HORARIOS = "horarios";
    String HOR_ID = "id_horario";
    String HOR_ID_CLASE = "id_clase";
    String HOR_DIA_SEMANA = "dia_semana";
    String HOR_HORA_INICIO = "hora_inicio";
    String HOR_HORA_FIN = "hora_fin";

    // RESERVAS
    String TAB_RESERVAS = "reservas";
    String RES_ID = "id_reserva";
    String RES_ID_CLIENTE = "id_cliente";
    String RES_ID_HORARIO = "id_horario";
    String RES_FECHA_RESERVA = "fecha_reserva";
    String RES_ESTADO = "estado";

    // SERVICIOS
    String TAB_SERVICIOS = "servicios";
    String SER_ID = "id_servicio";
    String SER_NOMBRE = "nombre";
    String SER_PRECIO = "precio";
    String SER_FECHA_ALTA = "fecha_alta";
    String SER_FECHA_BAJA = "fecha_baja";

    // PAGOS
    String TAB_PAGOS = "pagos";
    String PAG_ID = "id_pago";
    String PAG_ID_SERVICIO = "id_servicio";
    String PAG_ID_CLIENTE = "id_cliente";
    String PAG_METODO_PAGO = "metodo_pago";
    String PAG_FECHA_PAGO = "fecha_pago";
    String PAG_IMPORTE = "importe";
    String PAG_ESTADO = "estado";
}
