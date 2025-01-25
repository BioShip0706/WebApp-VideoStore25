
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
            
            return response.text().then(text => {
                
                throw new Error(text);
            });
        }
        
        return response.json();
    })
    .then(data => {
       
        const resultText = `Lo Store ${data.storeName} ha ${data.totalCustomers} clienti!`;
        document.getElementById("result").textContent = resultText;
        document.getElementById("result").className = "success";
    })
    .catch(error => {
        
        document.getElementById("result").innerText = "Non sei un admin / errore del server";
        document.getElementById("result").className = "error";
    });
}

