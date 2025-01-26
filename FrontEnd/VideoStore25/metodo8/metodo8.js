
document.getElementById("callMethod").addEventListener("click", metodo2);

function metodo2() 
{



 

  
    const token = localStorage.getItem('jwtToken'); 

    if (!token) {
        alert('Non sei autenticato. Per favore effettua il login.');
        return;
    }

    
    fetch(`http://localhost:8080/film/find-film-with-max-number-of-rent`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`, //token
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Errore nella risposta del server");
            }
            return response.json(); 
        })
        .then(data => {
            
            const tableBody = document.getElementById("filmsTable").getElementsByTagName("tbody")[0];
                tableBody.innerHTML = "";  // Pulisci corpo tabella,

            if (Array.isArray(data) && data.length === 0) {
                tableBody.innerHTML = "<tr><td colspan='5'>Nessun film trovato.</td></tr>";
                return;
            }

            
            //const tableBody = document.getElementById("filmsTable").getElementsByTagName("tbody")[0];
            tableBody.innerHTML = ""; 

            data.forEach(film => {
                const row = tableBody.insertRow(); 

                // Aggiunta celle alle righe
                row.insertCell(0).textContent = film.filmId;
                row.insertCell(1).textContent = film.title;
                row.insertCell(2).textContent = film.nrents;
            });
        })
        .catch(error => {
            console.error('Errore durante la chiamata API:', error);
            alert('Si è verificato un errore durante il recupero dei film.');
        });
}
