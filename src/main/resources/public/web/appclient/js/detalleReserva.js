function cargarDetalle() {
    var id = getParameterByName("id");
    httpConnect("/reserva/" + id, null, "GET", function (r) {
        if (r.status !== 200) {
            alert(r.message);
            window.location.replace("?p=listarReserva");
        }

        $("#resFechaRegistro").val(r.data.resFechaRegistro);
        $("#resFechaLlegada").val(r.data.resFechaLlegada);
        $("#resFechaSalida").val(r.data.resFechaSalida);
        cargarEstado();
        $("#resPago").val(r.data.resPago);
        cargarAlojamiento();
        cargarCliente();
        $("#id").val(id);

    }, function (e) {
        alert(e);
        window.location.replace("?p=listarAutor");
    });
}

function cargarEstado() {
    httpConnect("/alojamiento", null, "GET", function (r) {
        var html = "<select id='resEstado' name='resEstado' class='form-control' required>";
        html += "<option value=''>[SELECCIONAR OPCION]</option>";
        for (var i = 0; i < r.data.length; i++) {
            var o = r.data[i];
            html += "<option value='" + o.resEstado + "'>" + o.resEstado + "</option>";
        }
        html += "</select>";
        $("#contentEstado").html(html);
    });
}


function cargarAlojamiento() {
    httpConnect("/alojamiento", null, "GET", function (r) {
        var html = "<select id='fkAlojamiento' name='fkAlojamiento' class='form-control' required>";
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
        var html = "<select id='fkCliente' name='fkCliente' class='form-control' required>";
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
    cargarDetalle();
    
    $("#frmUpdateReserva").submit(function () {
        var entidad = new Object();

        entidad.resFechaRegistro = $("#resFechaRegistro").val();
        entidad.resFechaLlegada = $("#resFechaLlegada").val();
        entidad.resFechaSalida = $("#resFechaSalida").val();
        entidad.resEstado = $("#resEstado").val();
        entidad.resPago = $("#resPago").val();
        entidad.fkAlojamiento = {
            aloId: $("#fkAlojamiento").val()
        };
        entidad.fkCliente = {
            usuId: $("#fkCliente").val()
        };

        var jentidad = JSON.stringify(entidad);

        var id = $("#id").val();
        httpConnect("/reserva/" + id, jentidad, "PUT", function (r) {
            alert(r.message + "-" + r.data.nombre);
            window.location.replace("?p=listarReserva");
        });
        return false;
    });
});