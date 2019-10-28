function cargarTipoAlojamiento(){
    var id = getParameterByName("id");
    var html = "<option value=''>Seleccionar</option>";
    httpConnect("/tipoAlojamiento",{},"GET",function (r) {        
        for(var i = 0; i<r.data.length; i++){
            var tipoAlojamiento = r.data[i];
            html += "<option value='"+tipoAlojamiento.talNombre+"'>"+tipoAlojamiento.talNombre+"</option>";            
        }
        html += "<input type=\"text\" class=\"form-control\" id=\"talNombreOtro\" name=\"talNombreOtro\"/>";
        
        $("#id").val(id);
        $("#tipoAlojamiento").html(html);
    });
}

$(function (){
    cargarTipoAlojamiento();
    
    $("#frmDetalleTipoAlojamiento").submit(function (){
        var tipoAlojamiento = new Object();
        var jTipoAlojamiento;
        var id = $("#id").val();
                
        tipoAlojamiento.talNombre = $("#tipoAlojamiento").val();  
        jTipoAlojamiento = JSON.stringify(tipoAlojamiento);
        
        httpConnect("/tipoAlojamiento/"+id, jTipoAlojamiento, "PUT", function (r) {
            alert(r.message + "-" + r.data.nombre);
            window.location.replace("?p=listarTipoalojamiento");
        });       
        return false;
    });    
});
