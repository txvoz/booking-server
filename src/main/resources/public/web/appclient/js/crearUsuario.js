function cargarTipoIdentificacion() {
    httpConnect("/tipid", null, "GET", function (r) {
        var html = "<option value=''>[Seleccione opcion]</option>";
        for(var i = 0; i < r.data.length; i++){
            var tid = r.data[i];
            html += "<option value='"+tid.tidId+"'>"+tid.tipNombre+"</option>";
        }
        $("#fkTipoIdentificacion").html(html);
    });
}

$(function () {
    
    cargarTipoIdentificacion();
    
    $("#frmCrear").submit(function () {
        var usuario = new Object();
        usuario.usuIdentificacion = $("#usuIdentificacion").val();
        usuario.usuNombres = $("#usuNombres").val();
        usuario.usuGenero = $("input[name='usuGenero']:checked").val();;
        usuario.usuCorreo = $("#usuCorreo").val();
        usuario.usuTelefono = $("#usuTelefono").val();
        usuario.usuAvatar = $("#usuAvatar").val();
        usuario.fkTipoIdentificacion = {
            tidId:$("#fkTipoIdentificacion").val()
        };

        var jusuario = JSON.stringify(usuario);

        httpConnect("/usuario", jusuario, "POST", function (r) {
            alert(r.message + "-" + r.data.usuIdentificacion);
            $("button[type=reset]").click();
        });

        return false;
    });
});