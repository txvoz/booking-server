function cargarDepartamento(){
    
    //llama a la tabla en la base  de datos
    
    var html = "<option value=''>Seleccionar</option>";
    httpConnect("/departamento", {}, "GET", function (r) {
        for (var i = 0; i < r.data.length; i++) {            
            var departamento = r.data[i];
            html += "<option value='" + departamento.depId+ "'>" + departamento.depNombre + "</option>";
        }
        $("#Departamento").html(html);
        
    });
}

$(function(){
    cargarDepartamento();
});
