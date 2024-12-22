import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Form, Button, Alert, Container, Card } from 'react-bootstrap';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import './ClientUpdate.css'; // Import custom CSS file for additional styling

const ClientUpdate = () => {
    const { cin } = useParams();
    const navigate = useNavigate();
    const [client, setClient] = useState(null);
    const [error, setError] = useState(null);

    useEffect(() => {
        if (cin) {
            const fetchClient = async () => {
                try {
                    const response = await axios.get(`http://localhost:8888/CLIENT-SERVICE/clients/${cin}`);
                    setClient(response.data);
                } catch (err) {
                    setError('Erreur lors de la récupération du client');
                }
            };
            fetchClient();
        }
    }, [cin]);

    const handleChange = (e) => {
        setClient({
            ...client,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.patch(`http://localhost:8888/CLIENT-SERVICE/clients/update/${cin}`, client);
            navigate('/clients');
        } catch (err) {
            setError('Erreur lors de la mise à jour du client');
        }
    };

    if (!client) return <p>Chargement...</p>;

    return (
        <Container className="d-flex justify-content-center align-items-center" style={{ minHeight: '100vh', backgroundColor: '#f0f4ff' }}>
            <Card style={{ width: '100%', maxWidth: '500px' }} className="shadow border-0">
                <Card.Body>
                <h2 className="text-center text-primary mb-3" style={{ fontWeight: 'bold', textTransform: 'uppercase', fontSize: '2rem' }}>
    Mise à Jour des Informations du Client
</h2>
<p className="text-center text-muted mb-4" style={{ fontSize: '1rem' }}>
    Veuillez remplir le formulaire ci-dessous pour mettre à jour les informations du client.
</p>
                    {error && <Alert variant="danger" className="text-center">{error}</Alert>}
                    <Form onSubmit={handleSubmit}>
                        <Form.Group controlId="formNom" className="mb-3">
                            <Form.Label>Nom</Form.Label>
                            <Form.Control
                                type="text"
                                name="nom"
                                value={client.nom || ''}
                                onChange={handleChange}
                                required
                                className="rounded-pill"
                            />
                        </Form.Group>
                        <Form.Group controlId="formPrenom" className="mb-3">
                            <Form.Label>Prénom</Form.Label>
                            <Form.Control
                                type="text"
                                name="prenom"
                                value={client.prenom || ''}
                                onChange={handleChange}
                                required
                                className="rounded-pill"
                            />
                        </Form.Group>
                        <Form.Group controlId="formAdresse" className="mb-3">
                            <Form.Label>Adresse</Form.Label>
                            <Form.Control
                                type="text"
                                name="adresse"
                                value={client.adresse || ''}
                                onChange={handleChange}
                                required
                                className="rounded-pill"
                            />
                        </Form.Group>
                        <Form.Group controlId="formTelephone" className="mb-3">
                            <Form.Label>Téléphone</Form.Label>
                            <Form.Control
                                type="text"
                                name="telephone"
                                value={client.telephone || ''}
                                onChange={handleChange}
                                required
                                className="rounded-pill"
                            />
                        </Form.Group>
                        <Form.Group controlId="formEmail" className="mb-3">
                            <Form.Label>Email</Form.Label>
                            <Form.Control
                                type="email"
                                name="email"
                                value={client.email || ''}
                                onChange={handleChange}
                                required
                                className="rounded-pill"
                            />
                        </Form.Group>
                        <div className="d-flex justify-content-center mt-4">
                            <Button variant="primary" type="submit" className="rounded-pill">
                                Enregistrer les modifications
                            </Button>
                        </div>
                    </Form>
                </Card.Body>
            </Card>
        </Container>
    );
};

export default ClientUpdate;
