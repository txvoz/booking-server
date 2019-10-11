function cargarDetalle() {
    var id = getParameterByName("id");
    httpConnect("/usuario/" + id, null, "GET",function(r){
        if(r.status!==200){
            alert(r.message);
            window.location.replace("?p=listarUsuario");
        }
        $("#usuId").val(r.data.usuId);
        $("#usuIdentificacion").val(r.data.usuIdentificacion);
        $("#usuNombre").val(r.data.usuNombre);
        $("#usuGenero").val(r.data.usuGenero);
        $("#usuCorreo").val(r.data.usuCorreo);
        $("#usuTelefono").val(r.data.usuTelefono);
        $("#usuAvatar").val(r.data.usuAvatar);
    },function(e){
        alert(e);
        window.location.replace("?p=listarUsuario");
    });
}

$(function () {
    cargarCategorias(cargarDetalle);
    cargarDetalle();
    $("#frmUpdate").submit(function(){
        var entidad = new Object();
        entidad.usuIdentificacion = $("#usuIdentificacion").val();
        entidad.usuNombre = $("#usuNombre").val();
        entidad.usuGenero = $("#usuGenero").val();
        entidad.usuCorreo = $("#usuCorreo").val();
        entidad.usuTelefono = $("#usuTelefono").val();
        entidad.usuAvatar = $("#usuAvatar").val();
        var jentidad = JSON.stringify(entidad);
        
            var id=$("#id").val();
        httpConnect("/usuario/"+id,jentidad,"PUT",function(r){
            alert(r.message+"-"+r.data.nombre);
            window.location.replace("?p=listarUsuario");
        });
        return false;
    });
});