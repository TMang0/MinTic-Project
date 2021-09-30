const url = "http://localhost:8080/Reto4_Rockola/api/cancion"
const contenedor = document.querySelector('tbody')
let resultados = ''

const modalCanciones = new bootstrap.Modal(document.getElementById('modalCancion'))
const formCanciones = document.querySelector('form')
const nombreCancion = document.getElementById('nombre_can')
const nombreArtista = document.getElementById('nombre_art')
const nombreGenero = document.getElementById('nombre_gen')
const codigoCancion = document.getElementById('codigo_can')
let opcion = ''

btnCrear.addEventListener('click', () => {
    
    nombreCancion.value = ''
    nombreArtista.value = ''
    nombreGenero.value = ''
    codigoCancion.value = ''
    codigoCancion.disabled = false
    modalCanciones.show()
    opcion = 'crear'
})

const ajax = (options) => {
    let { url, method, success, error, data } = options;
    const xhr = new XMLHttpRequest();

    xhr.addEventListener("readystatechange", (e) => {
        if (xhr.readyState !== 4) return;

        if (xhr.status >= 200 && xhr.status < 300) {
            let json = JSON.parse(xhr.responseText);
            success(json);
        } else {
            let message = xhr.statusText || "Ocurrió un error";
            error(`Error ${xhr.status}: ${message}`);
        }
    });

    xhr.open(method || "GET", url);
    xhr.setRequestHeader("Content-type", "application/json; charset=utf-8");
    xhr.send(JSON.stringify(data));
};

const getAll = () => {
    ajax({
        url: url,
        method: "GET",
        success: (res) => {
            console.log(res);

            res.forEach((cancion) => {
                resultados += `<tr>
                        <td width="10%">${cancion.codigo_can}</td>
                        <td width="25%">${cancion.nombre_can}</td>
                        <td width="25%">${cancion.nombre_art}</td>
                        <td width="25%">${cancion.nombre_gen}</td>
                        <td class="text-center" width="20%"><a class="btnEditar btn btn-primary">Editar</a><a class="btnBorrar btn btn-danger">Borrar</a></td>
                    </tr>`
            });

            contenedor.innerHTML = resultados
        },
        error: (err) => {
            console.log(err);
            $table.insertAdjacentHTML("afterend", `<p><b>${err}</b></p>`);
        },
    });
};

document.addEventListener("DOMContentLoaded", getAll);

document.addEventListener("click", (e) => {

    if (e.target.matches(".btnBorrar")) {
        const fila = e.target.parentNode.parentNode
        const id = fila.firstElementChild.innerHTML
        console.log(id)
        alertify.confirm(`¿Estás seguro de eliminar el id ${id}?`,
            function () {
                ajax({
                    url: url + "/" + id,
                    method: "DELETE",
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    success: (res) => location.reload(),
                    error: (err) => alert(err),
                });
                alertify.success('Registro eliminado')
            },
            function () {
                alertify.error('Cancel')
            });


    }
    if (e.target.matches(".btnEditar")) {
        const fila = e.target.parentNode.parentNode
        codigoCancion.value = fila.children[0].innerHTML
        nombreCancion.value = fila.children[1].innerHTML
        nombreArtista.value = fila.children[2].innerHTML
        nombreGenero.value = fila.children[3].innerHTML
        codigoCancion.disabled = true
        opcion = 'editar'
        modalCanciones.show()
    }
})

const fila = e.target.parentNode.parentNode
codigoCancion.value = fila.children[0].innerHTML

formCanciones.addEventListener('submit', (e) => {
    e.preventDefault()
    let metodo = "POST"
    if (opcion == 'editar') {
        metodo = "PUT"
 
    }
    ajax({
        url: url,
        method: metodo,
        headers: {
            'Content-Type': 'application/json'
        },
        success: (res) => location.reload(),
        error: (err) =>
            $form.insertAdjacentHTML("afterend", `<p><b>${err}</b></p>`),
        data: {
            "codigo": codigo_can.value,
            "artista": nombre_art.value,
            "cancion": nombre_can.value,
            "genero": nombre_gen.value,
            
        },
    });
    modalCanciones.hide()
})





