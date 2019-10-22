function cargarTipoLugar(fn) {
    httpConnect("/tipolugar", null, "GET", function (r) {
        var html = "<select id='fkTipoLugar' name='fkTipoLugar' class='form-control' required>";
        html += "<option value=''>[SELECCIONAR OPCION]</option>";
        for (var i = 0; i < r.data.length; i++) {
            var o = r.data[i];
            html += "<option value='" + o.tluId + "'>" + o.tluNombre + "</option>";
        }
        html += "</select>";
        $("#contentTipoLugar").html(html);
        fn();
    });
}

function cargarMunicipio(fn) {
    httpConnect("/Municipio", null, "GET", function (r) {
        var html = "<select id='fkMunicipio' name='fkMunicipio' class='form-control' required>";
        html += "<option value=''>[SELECCIONAR OPCION]</option>";
        for (var i = 0; i < r.data.length; i++) {
            var o = r.data[i];
            html += "<option value='" + o.munId + "'>" + o.munNombre + "</option>";
        }
        html += "</select>";
        $("#contentMunicipio").html(html);
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
    cargarTipoLugar();
    cargarMunicipio();
    $("#frmUpdate").submit(function () {
        var entidad = new Object();
        entidad.lugNombre = $("#lugNombre").val();
        entidad.lugDireccion = $("#lugDireccion").val();
        entidad.lugTelefono = $("#lugTelefono").val();
        entidad.lugCorreo = $("#lugCorreo").val();
        entidad.lugLatitud = $("#lugLatitud").val();
        entidad.lugLongitud = $("#lugLongitud").val();
        entidad.lugDescripcion = $("#lugDescripcion").val();
        entidad.fkTipoLugar = {
            tluId:$("#fkTipoLugar").val()
        };
         entidad.fkMunicipio = {
            munId:$("#fkMunicipio").val()
        };
        
        var jentidad = JSON.stringify(entidad);
        var id = $("#id").val();
        httpConnect("/lugar/" + id, jentidad, "PUT", function (r) {
            alert(r.message );
            window.location.replace("?p=listarLugar");
        });
        return false;
    });
});

