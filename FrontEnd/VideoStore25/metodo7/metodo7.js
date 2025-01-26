
document.getElementById("callMethod").addEventListener("click", metodo2);

function metodo2() 
{

    const customerId = document.getElementById("customerId").value; 

   
    if (!customerId) {
        alert("Per favore riempi i campi.");
        return;
    }

    if(!document.getElementById("customerId").checkValidity())
    {
        alert("Inserisci dati validi");
        return
    }
    
    const token = localStorage.getItem('jwtToken'); 

    if (!token) {
        alert('Non sei autenticato. Per favore effettua il login.');
        return;
    }

   
    fetch(`http://localhost:8080/film/find-all-films-rent-by-one-customer/${customerId}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.text())  // Ricevi la risposta come testo
        .then(text => {
            try {
                const data = JSON.parse(text);  // Prova a fare il parsing della risposta come JSON
                const tableBody = document.getElementById("filmsTable").getElementsByTagName("tbody")[0];
    
                // Verifica se la risposta è un array di film
                if (Array.isArray(data)) {
                    if (data.length === 0) {
                        tableBody.innerHTML = "<tr><td colspan='5'>Nessun film noleggiato dal customer selezionato.</td></tr>";
                    } else {
                        tableBody.innerHTML = "";  // Pulisci la tabella esistente
    
                        data.forEach(film => {
                            const row = tableBody.insertRow();
                            row.insertCell(0).textContent = film.filmId;
                            row.insertCell(1).textContent = film.title;
                            row.insertCell(2).textContent = film.storeName;
                        });
                    }
                } else {
                    // Se la risposta non è un array, mostra il messaggio di errore
                    tableBody.innerHTML = `<tr><td colspan='5'>${data}</td></tr>`;
                }
            } catch (error) {
                // Se non è json entra qua.
                console.error('Errore nel parsing dei dati:', error);
                const tableBody = document.getElementById("filmsTable").getElementsByTagName("tbody")[0];
                tableBody.innerHTML = `<tr><td colspan='5'>${text}</td></tr>`;  // risposta
            }
        })
        .catch(error => {
         
            console.error('Errore durante la chiamata API:', error);
            const tableBody = document.getElementById("filmsTable").getElementsByTagName("tbody")[0];
            tableBody.innerHTML = `<tr><td colspan='5'>Si è verificato un errore durante il recupero dei film.</td></tr>`;
        });
}
