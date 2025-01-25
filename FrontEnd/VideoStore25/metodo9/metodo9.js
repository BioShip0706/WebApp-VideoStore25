
document.getElementById("callMethod").addEventListener("click", metodo2);

function metodo2() 
{
    
    const actorIds = document.getElementById("actorIds").value.trim();
    
    
    if (!actorIds) {
        alert("Per favore inserisci almeno un Id Attore");
        return;
    }

    const actorIdSet = new Set(actorIds.split(',').map(id => parseInt(id.trim()))); //splitto la stringa ricevuta per la virgola, assegno ogni valore (dopo il trim) trovato dentro actorSetId (in int);
    
    console.log(actorIdSet);  // Set di Long (numeri)

    
    const token = localStorage.getItem('jwtToken'); 

    if (!token) {
        alert('Non sei autenticato. Per favore effettua il login.');
        return;
    }

    let urlAttori = "";

    actorIdSet.forEach(aId => {urlAttori += `actorsIdList=${aId}&`})

    urlAttori =  urlAttori.slice(0,-1) //tutta la stringa tranne l'ultimo carattere extra '&'

    
    
             //http://localhost:8080/film/find-films-by-actors?actorsIdList=160&actorsIdList=19&actorsIdList=90&actorsIdList=85
        fetch(`http://localhost:8080/film/find-films-by-actors/?${urlAttori}`, {
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
                document.getElementById("filmsTable").innerHTML = "<tr><td colspan='5'>Nessun film trovato con gli attori specificati.</td></tr>";
                return;
            }

            // Altrimenti, popolate la tabella con i dati ricevuti
            const tableBody = document.getElementById("filmsTable").getElementsByTagName("tbody")[0];
            tableBody.innerHTML = ""; // Pulisci la tabella esistente

            data.forEach(film => {
                const row = tableBody.insertRow(); 

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
            alert('Si è verificato un errore durante il recupero dei dati.');
        });
}
