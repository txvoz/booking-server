function cargarDetalle() {
    var id = getParameterByName("id");
    httpConnect("/usuario/" + id, null, "GET",function(r){
        if(r.status!==200){
            alert(r.message);
            window.location.replace("?p=listarUsuario");
        }
        $("#id").val(id);
        $("#identificacion").val(r.data.identificacion);
        $("#nombre").val(r.data.nombre);
        $("#genero").val(r.data.genero);
        $("#correo").val(r.data.correo);
        $("#telefono").val(r.data.telefono);
        $("#avatarcode").val(r.data.avatarcode);
        $("#tipoidentificacion").val(r.data.tipoidentificacion);
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
        entidad.id = $("#id").val();
        entidad.identificacion = $("#identificacion").val();
        entidad.nombre = $("#nombre").val();
        entidad.genero = $("#genero").val();
        entidad.correo = $("#correo").val();
        entidad.telefono = $("#telefono").val();
        entidad.avatarcode = $("#avatarcode").val();
        entidad.tipoidentificacion = $("#tipoidentificacion").val();
        var jentidad = JSON.stringify(entidad);
        
        var id=$("#id").val();
        httpConnect("/usuario/"+id,jentidad,"PUT",function(r){
            alert(r.message+"-"+r.data.nombre);
            window.location.replace("?p=listarUsuario");
        });
        return false;
    });
});