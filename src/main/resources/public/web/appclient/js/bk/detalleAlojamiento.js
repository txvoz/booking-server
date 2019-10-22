function cargarDetalle() {
    var id = getParameterByName("id");
    httpConnect("/alojamiento/" + id, null, "GET",function(r){
        if(r.status!==200){
            alert(r.message);
            window.location.replace("?p=listarTipoAlojamiento");
        }
        $("#nombre").val(r.data.nombre);
        $("#id").val(id);
    },function(e){
        alert(e);
        window.location.replace("?p=listarTipoAlojamiento");
    });
}

$(function () {
    cargarDetalle();
    $("#frmUpdate").submit(function(){
        var entidad = new Object();
        entidad.nombre = $("#nombre").val();
        var jentidad = JSON.stringify(entidad);
        
        var id=$("#id").val();
        httpConnect("/alojamiento/"+id,jentidad,"PUT",function(r){
            alert(r.message+"-"+r.data.nombre);
            window.location.replace("?p=listarTipoAlojamiento");
        });
        return false;
    });
});

