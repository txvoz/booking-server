function cargarDetalle() {
    var id = getParameterByName("id");
    httpConnect("/usuario/" + id, null, "GET",function(r){
        if(r.status!==200){
            alert(r.message);
            window.location.replace("?p=listarUsuario");
        }
        $("#nombre").val(r.data.nombre);
        $("#id").val(id);
    },function(e){
        alert(e);
        window.location.replace("?p=listarUsuario");
    });
}

$(function () {
    cargarDetalle();
    $("#frmUpdate").submit(function(){
        var entidad = new Object();
        entidad.nombre = $("#nombre").val();
        var jentidad = JSON.stringify(entidad);
        
        var id=$("#id").val();
        httpConnect("/usuario/"+id,jentidad,"PUT",function(r){
            alert(r.message+"-"+r.data.nombre);
            window.location.replace("?p=listarUsuario");
        });
        return false;
    });
});