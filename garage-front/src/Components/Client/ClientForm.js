import React, { useState } from 'react'; 
import 'bootstrap/dist/css/bootstrap.min.css';
import { Container, Form, Button, Alert } from 'react-bootstrap';
import './ClientForm.css'; // Assurez-vous de créer ce fichier CSS

const ClientForm = ({ onSubmit }) => {
    const [cin, setCin] = useState('');
    const [nom, setNom] = useState('');
    const [prenom, setPrenom] = useState('');
    const [adresse, setAdresse] = useState('');
    const [telephone, setTelephone] = useState('');
    const [email, setEmail] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleSubmit = async (event) => {
        event.preventDefault();

        // Validation des champs
        if (!cin || !nom || !prenom || !adresse || !telephone || !email) {
            setError('Tous les champs sont requis.');
            return;
        }

        setError('');
        const clientData = { cin, nom, prenom, adresse, telephone, email };

        try {
            const response = await fetch('http://localhost:8888/CLIENT-SERVICE/clients/ajouter', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(clientData),
            });

            if (!response.ok) {
                throw new Error("Erreur lors de l'ajout du client.");
            }

            setSuccess('Client enregistré avec succès !');
            // Réinitialiser les champs du formulaire
            setCin('');
            setNom('');
            setPrenom('');
            setAdresse('');
            setTelephone('');
            setEmail('');

            if (onSubmit) {
                onSubmit(clientData);
            }
        } catch (error) {
            setError(error.message);
        }
    };

    return (
        <Container className="form-container mt-5">
<h2 className="text-center text-primary mb-3" style={{ fontWeight: 'bold', textTransform: 'uppercase', fontSize: '2rem' }}>
Ajouter un Client</h2>
<p className="text-center text-muted mb-4" style={{ fontSize: '1rem' }}>
    Veuillez remplir le formulaire ci-dessous pour ajouter les informations du client.
</p>
            {error && <Alert variant="danger">{error}</Alert>}
            {success && <Alert variant="success">{success}</Alert>}
            <Form onSubmit={handleSubmit} className="shadow p-4 rounded form">
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
                <Button variant="primary" type="submit" className="w-100 submit-button">
                    Enregistrer Client
                </Button>
            </Form>
        </Container>
    );
};

export default ClientForm;
