
document.getElementById("returnButton").addEventListener("click", function() //Ritorna copia
{
    addUpdateRental("Return");
});

document.getElementById("rentButton").addEventListener("click", function() //Noleggia copia
{
    addUpdateRental("Rent");
});


function addUpdateRental(scelta) 
{
    const filmId = document.getElementById("filmId").value;
    const storeId = document.getElementById("storeId").value

    // Verifica che tutti i campi siano compilati
    if (!filmId || !storeId ) {
        document.getElementById("result").innerText = "Compila tutti i campi.";
        return;
    }

    // Request java
    const rentalRequest = {
        customerId: parseInt(localStorage.getItem('customerId')),
        storeId: parseInt(storeId),
        filmId: parseInt(filmId)
    };

    const token = localStorage.getItem('jwtToken');

    
              // http://localhost:8080/film/update-film/1002
    const url = `http://localhost:8080/film/add-update-rental/${scelta}`; 

    
    fetch(url, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}` 
        },
        body: JSON.stringify(rentalRequest)
    })
        .then((response) => {
            if (!response.ok) {
                return response.text();
            }
            return response.text(); 
        })
        .then((data) => {
            document.getElementById("result").innerText = data; 
        })
        .catch((error) => {
            document.getElementById("result").innerText = error.message;
        });
};
