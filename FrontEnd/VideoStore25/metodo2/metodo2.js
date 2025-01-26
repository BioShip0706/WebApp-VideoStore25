
document.getElementById("callMethod").addEventListener("click", metodo2);

function metodo2() {
    const languageId = document.getElementById("languageId").value; 

    // Controlliamo che l'ID sia valido
    if (!languageId || languageId < 1 || languageId > 20) {
        alert("Per favore inserisci un ID lingua valido tra 1 e 20.");
        return;
    }

    if(!document.getElementById("languageId").checkValidity())
    {
        alert("Inserisci dati validi!");
        return;
    }
    
    const token = localStorage.getItem('jwtToken'); 

    if (!token) {
        alert('Non sei autenticato. Per favore effettua il login.');
        return;
    }

   
    fetch(`http://localhost:8080/film/find-films-by-language/${languageId}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.text())  // Ricevi la risposta come testo
        .then(text => {
            try {
                const data = JSON.parse(text);  // Prova a fare il parsing come JSON in caso ritornasse List<FilmResponse> filmsByLanguage
                const tableBody = document.getElementById("filmsTable").getElementsByTagName("tbody")[0];
                tableBody.innerHTML = "";  // Pulisci solo il corpo della tabella, non gli header
    
                if (Array.isArray(data)) {
                    if (data.length === 0) {
                        tableBody.innerHTML = "<tr><td colspan='5'>Nessun film trovato per la lingua selezionata.</td></tr>";
                    } else {
                        data.forEach(film => {
                            const row = tableBody.insertRow();
                            row.insertCell(0).textContent = film.filmId;
                            row.insertCell(1).textContent = film.title;
                            row.insertCell(2).textContent = film.description;
                            row.insertCell(3).textContent = film.releaseYear;
                            row.insertCell(4).textContent = film.languageName;
                        });
                    }
                } else {
                    // Se è una stringa (es. errore o messaggio)
                    tableBody.innerHTML = `<tr><td colspan='5'>${data}</td></tr>`;
                }
            } catch (error) {
                console.error('Errore nel parsificare la risposta JSON:', error);
                const tableBody = document.getElementById("filmsTable").getElementsByTagName("tbody")[0];
                tableBody.innerHTML = `<tr><td colspan='5'>${text}</td></tr>`;
            }
        })
        .catch(error => {
            console.error('Errore durante la chiamata API:', error);
            const tableBody = document.getElementById("filmsTable").getElementsByTagName("tbody")[0];
            tableBody.innerHTML = `<tr><td colspan='5'>Si è verificato un errore durante il recupero dei film.</td></tr>`;
        });
    
}
