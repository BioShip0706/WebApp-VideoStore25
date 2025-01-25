
document.getElementById("callMethod").addEventListener("click", metodo2);

function metodo2() {
    const languageId = document.getElementById("languageId").value; 

    // Controlliamo che l'ID sia valido
    if (!languageId || languageId < 1 || languageId > 20) {
        alert("Per favore inserisci un ID lingua valido tra 1 e 20.");
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
        .then(response => {
            if (!response.ok) {
                throw new Error("Errore nella risposta del server");
            }
            return response.json(); 
        })
        .then(data => {
            // Se non ci sono film
            if (Array.isArray(data) && data.length === 0) {
                document.getElementById("filmsTable").innerHTML = "<tr><td colspan='5'>Nessun film trovato per la lingua selezionata.</td></tr>";
                return;
            }

            //Popolo tabella
            const tableBody = document.getElementById("filmsTable").getElementsByTagName("tbody")[0];
            tableBody.innerHTML = ""; // Pulisci la tabella esistente

            data.forEach(film => {
                const row = tableBody.insertRow(); // Aggiungi una nuova riga alla tabella

                // Aggiungi celle per ogni colonna
                row.insertCell(0).textContent = film.filmId;
                row.insertCell(1).textContent = film.title;
                row.insertCell(2).textContent = film.description;
                row.insertCell(3).textContent = film.releaseYear;
                row.insertCell(4).textContent = film.languageName;
            });
        })
        .catch(error => {
            console.error('Errore durante la chiamata API:', error);
            alert('Si è verificato un errore durante il recupero dei film.');
        });
}
