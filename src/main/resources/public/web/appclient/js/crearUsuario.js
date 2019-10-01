function cargarGeneros() {
    httpConnect("/usuario", null, "GET", function (r) {
        var html = "<select id='genero' name='genero' class='form-control' required>";
        html += "<option value=''>[SELECCIONAR OPCION]</option>";
        for (var i = 0; i < r.data.length; i++) {
            var o = r.data[i];
            html += "<option value='" + i + "'>" + o.nombre + "</option>";
        }
        html += "</select>";
        $("#contentGenero").html(html);
    });
}

$(function(){
    cargarCategorias();
    $("#frmCrear").submit(function(){
        var usuario = new Object();
        usuario.id = $("#id").val();
        usuario.identificacion = $("#identificacion").val();
        usuario.nombre = $("#nombre").val();
        usuario.genero = $("#genero").val();
        usuario.correo = $("#correo").val();
        usuario.telefono = $("#telefono").val();
        usuario.avatarcode = $("#avatarcode").val();
        usuario.tipoidentificacion = $("#tipoidentificacion").val();
        
        var jusuario = JSON.stringify(usuario);
        
        httpConnect("/usuario",jusuario,"POST",function(r){
            alert(r.message+"-"+r.data.nombre);
            $("button[type=reset]").click();
        });
        
        return false;
    });
});