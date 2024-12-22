import React, { useEffect, useState } from 'react';
import { Table, Button, Spinner, Alert, Card } from 'react-bootstrap';
import { Envelope, CheckCircle, XCircle } from 'react-bootstrap-icons';
import axios from 'axios';

const FactureList = () => {
    const [factures, setFactures] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // Récupère les factures lors du chargement initial
    useEffect(() => {
        const fetchFactures = async () => {
            try {
                const response = await axios.get('http://localhost:8888/FACTURE-SERVICE/factures');
                setFactures(response.data);
            } catch (err) {
                setError('Erreur lors de la récupération des factures.');
            } finally {
                setLoading(false);
            }
        };
        fetchFactures();
    }, []);

    // Envoie la facture et met à jour l'état de 'isSent'
    const onSendFacture = async (id) => {
        try {
            // Envoi de la facture au backend
            await axios.post(`http://localhost:8888/FACTURE-SERVICE/factures/send/${id}`);

            // Met à jour localement l'état de la facture (isSent)
            setFactures(prevFactures =>
                prevFactures.map(facture =>
                    facture.id === id
                        ? { ...facture, status: 'ENVOYE', dateEmission: new Date() }
                        : facture
                )
            );

        } catch (err) {
            setError('Erreur lors de l\'envoi de la facture.');
        }
    };

    if (loading) return <Spinner animation="border" variant="primary" />;
    if (error) return <Alert variant="danger">{error}</Alert>;

    return (
        <div className="container mt-5">
            <Card className="mb-4 shadow-lg">
                <Card.Body>
                    <Table className="table-custom table-striped table-hover table-bordered">
                        <thead className="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>ID Maintenance</th>
                            <th>Intervention</th>
                            <th>Montant Total</th>
                            <th>Date d'Emission</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {factures.map((facture) => (
                            <tr key={facture.id}>
                                <td>{facture.id}</td>
                                <td>{facture.id_Maintenanace}</td>
                                <td>{facture.intervention}</td>
                                <td>{facture.montantTotal} €</td>
                                <td>{facture.dateEmission ? new Date(facture.dateEmission).toLocaleDateString() : 'N/A'}</td>
                                <td>
                                    {facture.status === 'ENVOYE' ? (
                                        <span className="badge bg-success text-white d-flex align-items-center">
                                            <CheckCircle size={20} className="me-2" /> Envoyée
                                        </span>
                                    ) : (
                                        <span className="badge bg-danger text-white d-flex align-items-center">
                                            <XCircle size={20} className="me-2" /> Non envoyée
                                        </span>
                                    )}
                                </td>
                                <td>
                                    {facture.status !== 'ENVOYE' && (
                                        <Button
                                            variant="outline-success"
                                            onClick={() => onSendFacture(facture.id)}
                                            className="d-flex align-items-center"
                                        >
                                            <Envelope size={20} className="me-2" />
                                            Envoyer
                                        </Button>
                                    )}
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </Table>
                </Card.Body>
            </Card>
        </div>
    );
};

export default FactureList;
