$(function () {
    //Asiganacion del evento submit para el formulario de crear libro
    $("#frmCrear").submit(function () {

        var entidad = new Object();

        entidad.resFechaRegistro = $("#resFechaRegistro").val();
        entidad.resFechaLlegada = $("#resFechaLlegada").val();
        entidad.resFechaSalida = $("#resFechaSalida").val();
        entidad.resEstado = $('input:radio[name=resEstado]:checked').val();
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