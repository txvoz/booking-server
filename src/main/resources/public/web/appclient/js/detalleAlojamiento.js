/**
 * @autor: Andrés Felipe Mera Tróchez
 */

/**
 * Metodo para cargar los datos por nombre de la tabla Lugar.
 * @returns {undefined}
 */
function cargarLugar() {
    httpConnect("/lugar", null, "GET", function (r) {
        var html = "<select id='lugar' name='lugar' class='form-control' required>";
        html += "<option value=''>[SELECCIONAR OPCION]</option>";
        for (var i = 0; i < r.data.length; i++) {
            var obj = r.data[i];
            html += "<option value='" + i + "'>" + obj.lugNombre + "</option>";
        }
        html += "</select>";
        $("#contentLugar").html(html);
    });
}
/**
 * Metodo para cargar los datos por nombre de la tabla tipo Alojamiento.
 * @returns {undefined}
 */
function cargarTipoAlojamiento() {
    httpConnect("/tipoAlojamiento", null, "GET", function (r) {
        var html = "<select id='tipoAlojamiento' name='tipoAlojamiento' class='form-control' required>";
        html += "<option value=''>[SELECCIONAR OPCION]</option>";
        for (var i = 0; i < r.data.length; i++) {
            var obj = r.data[i];
            html += "<option value='" + i + "'>" + obj.talNombre + "</option>";
        }
        html += "</select>";
        $("#contentTipoAlojamiento").html(html);
    });
}

function cargarDetalle() {
    var id = getParameterByName("id");
    httpConnect("/alojamiento/" + id, null, "GET", function (r) {
        if (r.status !== 200) {
            alert(r.message);
            window.location.replace("?p=listarAlojamiento");
        }
        $("#codigo").val(r.data.aloCodigo);
        $("#capacidad").val(r.data.aloCapacidad);
        $("#precio").val(r.data.aloPrecio);
        $("#descripcion").val(r.data.aloDescripcion);
        $("#lugar").val(r.data.fkLugar);
        $("#tipoAlojamiento").val(r.data.fkTipoAlojamiento);
        $("#id").val(id);
    }, function (e) {
        alert(e);
        window.location.replace("?p=listarAlojamiento");
    });
}
/**
 * Metodo principal de carga y ejecucion de la vista.
 * @returns {undefined}
 */
$(function () {
    cargarLugar();
    cargarTipoAlojamiento();
    cargarDetalle();
    $("#frmUpdate").submit(function () {
        var entidad = new Object();
        entidad.aloCodigo = $("#codigo").val();
        entidad.aloCapacidad = $("#capacidad").val();
        entidad.aloPrecio = $("#precio").val();
        entidad.aloDescripcion = $("#descripcion").val();
        entidad.fkLugar = {
            lugId: $("fkLugar").val()
        };
        entidad.fkTipoAlojamiento = {
            talId: $("fkTipoAlojamiento").val()
        };
        var jentidad = JSON.stringify(entidad);
        var id = $("#id").val();
        httpConnect("/alojamiento/" + id, jentidad, "PUT", function (r) {
            alert(r.message + "-" + r.data.aloCodigo);
            window.location.replace("?p=listarAlojamiento");
        });
        return false;
    });
});

