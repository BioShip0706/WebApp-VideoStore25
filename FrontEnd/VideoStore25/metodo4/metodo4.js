
document.getElementById("countCustomersButton").addEventListener("click", function() 
{
    const storeName = document.getElementById("storeSelect").value; // Ottieni il nome dello store selezionato
    
    countCustomersByStore(storeName); // Chiama la funzione con il nome dello store
});


function countCustomersByStore(storeName) {

    const url = `http://localhost:8080/film/count-customers-by-store/${storeName}`;
    const token = localStorage.getItem('jwtToken');

    
    fetch(url, {
        method: 'GET', 
        headers: {
            'Content-Type': 'application/json', 
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => {
            if (!response.ok) {
                // Se la risposta non è OK, prova a leggere il testo della risposta
                return response.text().then(text => {
                    // Crea un errore usando il testo del corpo della risposta
                    throw new Error(text);
                });
            }
            // Se la risposta è OK, convertila in JSON
            return response.json();
        })
        .then(data => {
            // Se la risposta è corretta, mostra i dati
            const resultText = `Lo Store ${data.storeName} ha ${data.totalCustomers} clienti!`;
            document.getElementById("result").textContent = resultText;
            document.getElementById("result").className = "success";
        })
        .catch(error => {
            // Gestisci gli errori nel blocco catch
            document.getElementById("result").innerText = error.message || "Si è verificato un errore del server.";
            document.getElementById("result").className = "error";
        });
}

