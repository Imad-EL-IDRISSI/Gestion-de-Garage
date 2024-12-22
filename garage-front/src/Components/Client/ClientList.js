import React, { useEffect, useState } from 'react';
import { Table, Spinner, Alert, Button, Pagination } from 'react-bootstrap';
import { PencilSquare } from 'react-bootstrap-icons';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const ClientList = ({ searchTerm, onTotalClientsChange }) => {
    const [clients, setClients] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [currentPage, setCurrentPage] = useState(1);
    const clientsPerPage = 5; // Nombre de clients par page

    const navigate = useNavigate();

    useEffect(() => {
        const fetchClients = async () => {
            try {
                const response = await axios.get('http://localhost:8888/CLIENT-SERVICE/clients');
                onTotalClientsChange(response.data.length); 

                setClients(response.data);
            } catch (err) {
                console.error(err);
                setError(err.response?.data?.message || err.message || 'Erreur lors de la récupération des clients');
            } finally {
                setLoading(false);
            }
        };

        fetchClients();
    }, [onTotalClientsChange]);

    const onEditClient = (cin) => {
        navigate(`/clients/update/${cin}`);
    };

    // Filtrer les clients en fonction du terme de recherche
    const filteredClients = clients.filter(client =>
        client.nom.toLowerCase().includes(searchTerm.toLowerCase()) ||
        client.prenom.toLowerCase().includes(searchTerm.toLowerCase()) ||
        client.cin.toLowerCase().includes(searchTerm.toLowerCase())
    );

    // Pagination : Calculer les indices des clients à afficher sur la page actuelle
    const indexOfLastClient = currentPage * clientsPerPage;
    const indexOfFirstClient = indexOfLastClient - clientsPerPage;
    const currentClients = filteredClients.slice(indexOfFirstClient, indexOfLastClient);

    const totalPages = Math.ceil(filteredClients.length / clientsPerPage);

    const handlePageChange = (pageNumber) => {
        setCurrentPage(pageNumber);
    };

    if (loading) {
        return (
            <div className="text-center">
                <Spinner animation="border" role="status">
                    <span className="visually-hidden">Chargement...</span>
                </Spinner>
            </div>
        );
    }

    if (error) {
        return (
            <Alert variant="danger" className="text-center">
                {error}
            </Alert>
        );
    }

    return (
        <div>
            <Table className="table">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">CIN</th>
                        <th scope="col">Nom</th>
                        <th scope="col">Prénom</th>
                        <th scope="col">Adresse</th>
                        <th scope="col">Téléphone</th>
                        <th scope="col">Email</th>
                        <th scope="col">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {currentClients.map((client, index) => (
                        <tr key={client.cin}>
                            <th scope="row">{indexOfFirstClient + index + 1}</th>
                            <td>{client.cin}</td>
                            <td>{client.nom}</td>
                            <td>{client.prenom}</td>
                            <td>{client.adresse}</td>
                            <td>{client.telephone}</td>
                            <td>{client.email}</td>
                            <td>
                                <Button variant="outline-primary" onClick={() => onEditClient(client.cin)}>
                                    <PencilSquare />
                                </Button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </Table>

            {/* Pagination */}
            <div className="d-flex justify-content-center mt-4">
                <Pagination>
                    <Pagination.First onClick={() => handlePageChange(1)} disabled={currentPage === 1} />
                    <Pagination.Prev onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 1} />
                    {[...Array(totalPages)].map((_, index) => (
                        <Pagination.Item
                            key={index}
                            active={currentPage === index + 1}
                            onClick={() => handlePageChange(index + 1)}
                        >
                            {index + 1}
                        </Pagination.Item>
                    ))}
                    <Pagination.Next onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage === totalPages} />
                    <Pagination.Last onClick={() => handlePageChange(totalPages)} disabled={currentPage === totalPages} />
                </Pagination>
            </div>
        </div>
    );
};

export default ClientList;
