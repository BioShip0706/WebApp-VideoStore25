
document.getElementById("countRentalsBtn").addEventListener("click", function()
{
    countRentals();
});



function countRentals() 
{

    const storeId = document.getElementById("storeId").value
    const dateStart = document.getElementById("dateStart").value
    const dateEnd = document.getElementById("dateEnd").value

    // console.log(storeId)
    // console.log(dateStart)
    // console.log(dateEnd)

    // Verifica che tutti i campi siano compilati
    if (!dateStart || !dateEnd || !storeId ) {
        document.getElementById("result").innerText = "Compila tutti i campi.";
        return;
    }
    
    if(!document.getElementById("countRentalsForm").checkValidity())
    {
        alert("Inserisci dati validi");
        return;
    }

    if(dateStart >  dateEnd )
    {
        document.getElementById("result").innerText = "Data di fine non può essere minore dell'inizio";
        return;
    }


   

    const token = localStorage.getItem('jwtToken');

    
              // http://localhost:8080/film/count-rentals-in-date-range-by-store/1?start=2023-01-01&end=2024-01-01
    const url = `http://localhost:8080/film/count-rentals-in-date-range-by-store/${storeId}?start=${dateStart}&end=${dateEnd}`; 

    //GET
    fetch(url, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}` 
        },
    })
    .then((response) => {
        if (!response.ok) {
            throw new Error("Errore nell'aggiornamento del film.");
        }
        return response.text(); 
    })
    .then((data) => {
        document.getElementById("result").innerText = data; 
    })
    .catch((error) => {
        document.getElementById("result").innerText = "Non sei un admin / errore del server";
    });
};
