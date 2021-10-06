const url = "http://localhost:8080/Sprint4_Rockola/api/cancion"
const contenedor = document.querySelector('tbody')
let resultados = ''

const modalCanciones = new bootstrap.Modal(document.getElementById('modalCancion'))
const formCanciones = document.querySelector('form')
const nombreCancion = document.getElementById('nombre')
const nombreArtista = document.getElementById('artista')
const nombreGenero = document.getElementById('genero')
const codigoCancion = document.getElementById('id')
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

            res.forEach((canciones) => {
                resultados += `<tr>
                        <td width="10%">${canciones.codigo_can}</td>
                        <td width="25%">${canciones.nombre_can}</td>
                        <td width="25%">${canciones.nombre_art}</td>
                        <td width="25%">${canciones.nombre_gen}</td>
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
            "codigo_can": codigoCancion.value,
            "nombre_art": nombreArtista.value,
            "nombre_can": nombreCancion.value,
            "nombre_gen": nombreGenero.value,
            
        },
    });
    modalCanciones.hide()
})





