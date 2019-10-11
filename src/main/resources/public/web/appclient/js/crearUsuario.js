$(function(){
    cargarCategorias();
    $("#frmCrear").submit(function(){
        var usuario = new Object();
        usuario.usuIdentificacion = $("#usuIdentificacion").val();
        usuario.usuNombres = $("#usuNombres").val();
        usuario.usuGenero = $("#usuGenero").val();
        usuario.usuCorreo = $("#usuCorreo").val();
        usuario.usuTelefono = $("#usuTelefono").val();
        usuario.usuAvatar = $("#usuAvatar").val();
        usuario.FkTipoIdentificacion = $("#FkTipoIdentificacion").val();
        
        var jusuario = JSON.stringify(usuario);
        
        httpConnect("/usuario",jusuario,"POST",function(r){
            alert(r.message+"-"+r.data.usuIdentificacion);
            $("button[type=reset]").click();
        });
        
        return false;
    });
});