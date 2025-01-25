
document.getElementById("callMethod").addEventListener("click", metodo2);

function metodo2() 
{

    const customerId = document.getElementById("customerId").value; 

   
    if (!customerId) {
        alert("Per favore riempi i campi.");
        return;
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
        .then(response => {
            if (!response.ok) {
                throw new Error("Errore nella risposta del server");
            }
            return response.json(); 
        })
        .then(data => {
            
            if (Array.isArray(data) && data.length === 0) {
                document.getElementById("filmsTable").innerHTML = "<tr><td colspan='5'>Nessun film noleggiato dal customer selezionato.</td></tr>";
                return;
            }

            
            const tableBody = document.getElementById("filmsTable").getElementsByTagName("tbody")[0];
            tableBody.innerHTML = ""; // Pulisci la tabella esistente

            data.forEach(film => {
                const row = tableBody.insertRow(); 

                // Aggiungi celle per ogni colonna
                row.insertCell(0).textContent = film.filmId;
                row.insertCell(1).textContent = film.title;
                row.insertCell(2).textContent = film.storeName;
            });
        })
        .catch(error => {
            console.error('Errore durante la chiamata API:', error);
            alert('Si è verificato un errore durante il recupero dei film.');
        });
}
