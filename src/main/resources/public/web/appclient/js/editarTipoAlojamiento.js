$(function (){
    cargarTipoAlojamiento();
    
    $("#frmEditarTipoAlojamiento").submit(function (){
        var tipoAlojamiento = new Object();
        var jTipoAlojamiento;
        var id = $("#id").val();
        
        tipoAlojamiento.talNombre = $("#tipoAlojamiento").val();  
        jTipoAlojamiento = JSON.stringify(tipoAlojamiento);
        
        httpConnect("/tipoAlojamiento/"+id, jTipoAlojamiento, "PUT", function (r) {
            alert(r.message + "-" + r.data.talNombre);
            window.location.replace("listarTipoAlojamiento");
        });       
        return false;
    });
    
});

function cargarTipoAlojamiento(){
    var html = "<option value=''>Seleccionar</option>";
    httpConnect("/tipoAlojamiento",{},"GET",function (r) {
        for(var i = 0; i<r.data.length; i++){
            var tipoAlojamiento = r.data[i];
            html += "<option value='"+tipoAlojamiento.talId+"'>"+tipoAlojamiento.talNombre+"</option>";            
        }
        $("#tipoAlojamiento").html(html);
    });
}



