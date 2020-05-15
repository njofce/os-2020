Implement a mini voting system. In the beginning, all the candidates 
are saved at the server side, along with their respective numbers 
(ex. Petar Kostandinov - 1, Ilija Petkovski - 2 etc). The client, 
after connecting to the server, should receive the entire voting list and 
choose the desired candidate, by sending the candidate number to the server. 
Any client can, at any time, get the results for each candidate. 
The server allows maximum 100 clients connected at the same moment.

Note: Define the communication protocol - define the set of states and 
the format of the messages that are to be sent.