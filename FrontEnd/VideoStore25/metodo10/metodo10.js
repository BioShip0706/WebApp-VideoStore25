// Funzione chiamata quando il bottone viene cliccato
document.getElementById("callMethod").addEventListener("click", metodo2);

function metodo2() 
{

    const filmTitle = document.getElementById("filmTitle").value; // Film titlo in input

   
    if (!filmTitle) {
        alert("Per favore inserisci il nome del film");
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
        .then(response => {
            if (!response.ok) {
                throw new Error("Errore nella risposta del server");
            }
            return response.json(); 
        })
        .then(data => {
            
            if (Array.isArray(data) && data.length === 0) {
                document.getElementById("filmsTable").innerHTML = "<tr><td colspan='5'>Nessun film trovato per la lingua selezionata.</td></tr>";
                return;
            }

           
            const tableBody = document.getElementById("filmsTable").getElementsByTagName("tbody")[0];
            tableBody.innerHTML = ""; 

            data.forEach(film => {
                const row = tableBody.insertRow(); 

                // Aggiungi celle per ogni colonna
                row.insertCell(0).textContent = film.title;
                row.insertCell(1).textContent = film.storeName;
                row.insertCell(2).textContent = film.navailableCopies;
                row.insertCell(3).textContent = film.nstockCopies;
            });
        })
        .catch(error => {
            console.error('Errore durante la chiamata API:', error);
            alert('Si è verificato un errore durante il recupero dei film.');
        });
}
