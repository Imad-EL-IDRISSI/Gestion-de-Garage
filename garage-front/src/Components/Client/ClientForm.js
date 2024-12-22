import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Container, Form, Button, Alert } from 'react-bootstrap';

const ClientForm = ({ onSubmit }) => {
    const [cin, setCin] = useState('');
    const [nom, setNom] = useState('');
    const [prenom, setPrenom] = useState('');
    const [adresse, setAdresse] = useState('');
    const [telephone, setTelephone] = useState('');
    const [email, setEmail] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleSubmit = (event) => {
        event.preventDefault();
        // Basic validation
        if (!cin || !nom || !prenom || !adresse || !telephone || !email) {
            setError('Tous les champs sont requis.');
            return;
        }
        setError(''); // Clear error
        const clientData = { cin, nom, prenom, adresse, telephone, email };

        // Simulate API call
        setTimeout(() => {
            onSubmit(clientData);
            setSuccess('Client enregistré avec succès !');
            // Clear form fields
            setCin('');
            setNom('');
            setPrenom('');
            setAdresse('');
            setTelephone('');
            setEmail('');
        }, 500);
    };

    return (
        <Container className="mt-5">
            <h2 className="text-center mb-4">Ajouter un Client</h2>
            {error && <Alert variant="danger">{error}</Alert>}
            {success && <Alert variant="success">{success}</Alert>}
            <Form onSubmit={handleSubmit} className="shadow p-4 rounded">
                <Form.Group className="mb-3">
                    <Form.Label>CIN</Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="Entrez le CIN"
                        value={cin}
                        onChange={(e) => setCin(e.target.value)}
                        required
                    />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Nom</Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="Entrez le nom"
                        value={nom}
                        onChange={(e) => setNom(e.target.value)}
                        required
                    />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Prénom</Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="Entrez le prénom"
                        value={prenom}
                        onChange={(e) => setPrenom(e.target.value)}
                        required
                    />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Adresse</Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="Entrez l'adresse"
                        value={adresse}
                        onChange={(e) => setAdresse(e.target.value)}
                        required
                    />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Téléphone</Form.Label>
                    <Form.Control
                        type="tel"
                        placeholder="Entrez le téléphone"
                        value={telephone}
                        onChange={(e) => setTelephone(e.target.value)}
                        required
                    />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Email</Form.Label>
                    <Form.Control
                        type="email"
                        placeholder="Entrez l'email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </Form.Group>
                <Button variant="primary" type="submit" className="w-100">
                    Enregistrer Client
                </Button>
            </Form>
        </Container>
    );
};

export default ClientForm;
