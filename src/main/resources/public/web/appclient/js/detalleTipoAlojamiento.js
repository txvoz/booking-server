function cargarDetalle() {
    var id = getParameterByName("id");
    httpConnect("/alojamiento/" + id, null, "GET",function(r){
        if(r.status!==200){
            alert(r.message);
            window.location.replace("?p=listarTipoAlojamiento");
        }
        $("#catNombre").val(r.data.catNombre);
        $("#catId").val(id);
    },function(e){
        alert(e);
        window.location.replace("?p=listarTipoAlojamiento");
    });
}

$(function () {
    cargarDetalle();
    $("#frmUpdate").submit(function(){
        var entidad = new Object();
        entidad.catNombre = $("#catNombre").val();
        var jentidad = JSON.stringify(entidad);
        
        var id=$("#catId").val();
        httpConnect("/alojamiento/"+id,jentidad,"PUT",function(r){
            alert(r.message+"-"+r.data.catNombre);
            window.location.replace("listarTipoAlojamiento");
        });
        return false;
    });
});

