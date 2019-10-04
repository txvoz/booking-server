function cargarDepartamento(fn) {
    httpConnect("/departamento", null, "GET", function (r) {
        var html = "<select id='departamento' name='departamento' class='form-control' required>";
        html += "<option value=''>[SELECCIONAR OPCION]</option>";
        for (var i = 0; i < r.data.length; i++) {
            var o = r.data[i];
            html += "<option value='" + i + "'>" + o.fkMunicipio + "</option>";
        }
        html += "</select>";
        $("#contentDepartamento").html(html);
        fn();
    });
}


function cargarDetalle() {
    var id = getParameterByName("id");
    httpConnect("/lugar/" + id, null, "GET", function (r) {
        if (r.status !== 200) {
            alert(r.message);
            window.location.replace("?p=listarLugar");
        }
        $("#fkMunicipio").val(r.data.fkMunicipio);
        $("#fkTipoLugar").val(r.data.fkTipoLugar);
        $("#lugDescripcion").val(r.data.lugDescripcion);
        $("#lugLongitud").val(r.data.lugLongitud);
        $("#lugLatitud").val(r.data.lugLatitud);
        $("#lugCorreo").val(r.data.lugCorreo);
        $("#lugTelefono").val(r.data.lugTelefono);
        $("#lugDireccion").val(r.data.lugDireccion);
        $("#lugNombre").val(r.data.lugNombre);
        $("#id").val(id);
    }, function (e) {
        alert(e);
        window.location.replace("?p=listarLugar");
    });
}

$(function () {
    cargarDetalle();
    $("#frmUpdate").submit(function () {
        var entidad = new Object();
        entidad.nombre = $("#nombre").val();
        entidad.lugDireccion = $("#lugDireccion").val();
        entidad.lugTelefono = $("#lugTelefono").val();
        entidad.lugCorreo = $("#lugCorreo").val();
        entidad.lugLatitud = $("#lugLatitud").val();
        entidad.lugLongitud = $("#lugLongitud").val();
        entidad.lugDescripcion = $("#lugDescripcion").val();
        entidad.fkTipoLugar = $("#fkTipoLugar").val();
        entidad.fkMunicipio = $("#fkMunicipio").val();
        var jentidad = JSON.stringify(entidad);
        var id = $("#id").val();
        httpConnect("/lugar/" + id, jentidad, "PUT", function (r) {
            alert(r.message + "-" + r.data.nombre);
            alert(r.message + "-" + r.data.lugDireccion);
            alert(r.message + "-" + r.data.lugTelefono);
            alert(r.message + "-" + r.data.lugCorreo);
            alert(r.message + "-" + r.data.lugLatitud);
            alert(r.message + "-" + r.data.lugLongitud);
            alert(r.message + "-" + r.data.lugDescripcion);
            alert(r.message + "-" + r.data.fkTipoLugar);
            alert(r.message + "-" + r.data.fkMunicipio);
            window.location.replace("?p=listarLugar");
        });
        return false;
    });
});

