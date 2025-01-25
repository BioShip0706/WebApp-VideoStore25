
if(!localStorage.getItem("jwtToken")) //se non ci sono dati salvati nello storage (permanenza anche chiudndo il browser)
{
    window.location.href = 'login.html';
}

// Recupero i dati dal localStorage
const username = localStorage.getItem('username') || 'Non sei loggato';
const authorities = JSON.parse(localStorage.getItem('authorities')) || ['Nessun ruolo'];

if (!authorities.includes("ROLE_ADMIN")) {
    const adminEndpoints = document.querySelector('.admin-endpoints');
    if (adminEndpoints) {
        adminEndpoints.style.display = 'none';
    }
}

const mappaRuoli =
{
    'ROLE_MEMBER': 'Membro',
    'ROLE_ADMIN': 'Admin'
};

const mappedAuths = authorities.map(role => mappaRuoli[role] || role);

// Mostra i dati nella sezione id "login-status"
document.getElementById('username').textContent = `Username: ${username}`;
// document.getElementById('authorities').textContent = `Authorities: ${authorities.join(', ')}`;
document.getElementById('authorities').textContent = `Authorities: ${mappedAuths.join(', ')}`;


function logout() 
{
    alert("Logout account!");
   
    localStorage.clear(); // Accedi di nuovo, dati cancellati
    window.location.href = 'login.html'; // Reindirizzamento al login
}

