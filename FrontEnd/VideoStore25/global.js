if(!localStorage.getItem("jwtToken")) //se non ci sono dati salvati nello storage (permanenza anche chiudndo il browser)
{
    window.location.href = '/VideoStore25/login.html';
}