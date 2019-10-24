function cargarmunicipo(){
    
    //llama a la tabla en la base  de datos
    
    var html = "<option value=''>Seleccionar</option>";
    httpConnect("/Municipio", {}, "GET", function (r) {
        for (var i = 0; i < r.data.length; i++) {
            var municipio = r.data[i];
            html += "<option value='" + municipio.munId+ "'>" + municipio.munNombre + "</option>";
        }
        $("#Id_municipio").html(html);
        
    });
}


$(function(){
    cargarmunicipo();
});
