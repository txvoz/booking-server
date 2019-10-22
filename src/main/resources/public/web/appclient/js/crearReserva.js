function cargarAlojamiento() {
    httpConnect("/alojamiento", null, "GET", function (r) {
        var html = "<select id='aloId' name='aloId' class='form-control' required>";
        html += "<option value=''>[SELECCIONAR OPCION]</option>";
        for (var i = 0; i < r.data.length; i++) {
            var o = r.data[i];
            html += "<option value='" + o.aloId + "'>" + o.aloCodigo + "</option>";
        }
        html += "</select>";
        $("#contentAlojamiento").html(html);
    });
}
function cargarCliente() {
    httpConnect("/usuario", null, "GET", function (r) {
        var html = "<select id='usuId' name='usuId' class='form-control' required>";
        html += "<option value=''>[SELECCIONAR OPCION]</option>";
        for (var i = 0; i < r.data.length; i++) {
            var o = r.data[i];
            html += "<option value='" + o.usuId + "'>" + o.usuNombres + "</option>";
        }
        html += "</select>";
        $("#contentCliente").html(html);
    });
}

$(function () {
    cargarAlojamiento();
    cargarCliente();
    
    //Asiganacion del evento submit para el formulario de crear libro
    $("#frmCrear").submit(function () {

        var entidad = new Object();

        entidad.resFechaRegistro = $("#resFechaRegistro").val();
        entidad.resFechaLlegada = $("#resFechaLlegada").val();
        entidad.resFechaSalida = $("#resFechaSalida").val();
        entidad.resEstado = $("#estadoReservado").val();
        entidad.resPago = $("#resPago").val();        
        entidad.fkAlojamiento = {
            aloId: $("#fkAlojamiento").val()
        };
        entidad.fkCliente = {
            usuId: $("#fkCliente").val()
        };

        var jentidad = JSON.stringify(entidad);

        httpConnect("/reserva", jentidad, "POST", function (r) {
            alert(r.message + " - con fecha: " + r.data.resFechaRegistro);
            $("button[type=reset]").click();
        }, function (r) {
            alert(r.message);
        });

        return false;
    });
});