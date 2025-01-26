// Funzione chiamata quando il bottone viene cliccato
document.getElementById("callMethod").addEventListener("click", metodo2);

function metodo2() 
{

    const filmTitle = document.getElementById("filmTitle").value; // Film titlo in input

   
    if (!filmTitle) {
        alert("Per favore inserisci il nome del film");
        return;
    }

    if(!document.getElementById("filmTitle").checkValidity())
    {
        alert("Inserisci dati validi");
        return;
    }


    const token = localStorage.getItem('jwtToken'); 

    if (!token) {
        alert('Non sei autenticato. Per favore effettua il login.');
        return;
    }

   
         //http://localhost:8080/film/find-rentable-films/?title=ACE%20GOLDFINGER
         fetch(`http://localhost:8080/film/find-rentable-films/?title=${filmTitle}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.text()) //Corpo risposta in testo
            .then(text => 
            {

                const tableBody = document.getElementById("filmsTable").getElementsByTagName("tbody")[0];
                try {

                    const data = JSON.parse(text); //Prova parse
                    tableBody.innerHTML = ""; // Pulisci il corpo della tabella
        
                    // Verifica se la risposta è un array e gestisci i casi
                    if (Array.isArray(data) && data.length === 0) {
                        tableBody.innerHTML = "<tr><td colspan='5'>Nessun film trovato per il titolo selezionato.</td></tr>";
                        return;
                    }
        
                    // Popola la tabella con i dati ricevuti
                    data.forEach(film => {
                        const row = tableBody.insertRow();
                        row.insertCell(0).textContent = film.title;
                        row.insertCell(1).textContent = film.storeName;
                        row.insertCell(2).textContent = film.navailableCopies;
                        row.insertCell(3).textContent = film.nstockCopies;
                    });
                } catch (error) {
                    // Parsing fallito , messaggio responseEntity
                    console.error('Errore nel parsing della risposta:', error);
                    tableBody.innerHTML = `<tr><td colspan='5'>${text}</td></tr>`;
                }
            })
            .catch(error => {
                // Gestione errori per problemi di rete o altre eccezioni
                const tableBody = document.getElementById("filmsTable").getElementsByTagName("tbody")[0];
                tableBody.innerHTML = `<tr><td colspan='5'>Errore durante il recupero dei dati: ${error.message}</td></tr>`;
                console.error('Errore durante la chiamata API:', error);
            });
}
