import React, { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Container, Form, Button, Alert } from 'react-bootstrap';
import { FaCar, FaUser, FaInfoCircle, FaCalendarAlt } from 'react-icons/fa';
import axios from 'axios';

const VehiculeForm = () => {
    const [vin, setVin] = useState('');
    const [immatriculation, setImmatriculation] = useState('');
    const [marque, setMarque] = useState('');
    const [modele, setModele] = useState('');
    const [annee, setAnnee] = useState('');
    const [couleur, setCouleur] = useState('');
    const [km, setKm] = useState('');
    const [typeCarb, setTypeCarb] = useState('');
    const [dateAchat, setDateAchat] = useState('');
    const [clientId, setClientId] = useState('');
    const [etat, setEtat] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const [clients, setClients] = useState([]);

    // Fetch clients from API
    useEffect(() => {
        const fetchClients = async () => {
            try {
                const response = await axios.get('http://localhost:8888/CLIENT-SERVICE/clients/ids');
                setClients(response.data);  // Assuming response.data contains an array of client IDs
            } catch (err) {
                setError('Erreur lors de la récupération des clients.');
            }
        };
        fetchClients();
    }, []);

    const handleSubmit = async (event) => {
        event.preventDefault();

        // Basic validation
        if (!vin || !immatriculation || !marque || !modele || !annee || !couleur || !km || !typeCarb || !dateAchat || !clientId || !etat) {
            setError('Tous les champs sont requis.');
            return;
        }
        setError('');

        const vehiculeData = { vin, immatriculation, marque, modele, annee, couleur, km: Number(km), typeCarb, dateAchat, client_Id: clientId, etat };

        try {
            const response = await fetch('http://localhost:8888/VEHICULE-SERVICE/vehicules/ajouter', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(vehiculeData),
            });

            if (!response.ok) {
                throw new Error('Une erreur est survenue lors de l\'enregistrement du véhicule.');
            }

            setSuccess('Véhicule ajouté avec succès !');
            // Clear form fields
            setVin('');
            setImmatriculation('');
            setMarque('');
            setModele('');
            setAnnee('');
            setCouleur('');
            setKm('');
            setTypeCarb('');
            setDateAchat('');
            setClientId('');
            setEtat('');
        } catch (error) {
            setError(error.message);
        }
    };

    return (
        <Container className="mt-5" style={{ maxWidth: '500px' }}>
            <h2 className="text-center text-success mb-3" style={{ fontWeight: 'bold', textTransform: 'uppercase', fontSize: '2rem' }}>
                Ajouter un Véhicule
            </h2>
            <p className="text-center text-muted mb-4" style={{ fontSize: '1rem' }}>
                Veuillez remplir le formulaire ci-dessous pour ajouter les informations du véhicule.
            </p>

            {error && <Alert variant="danger">{error}</Alert>}
            {success && <Alert variant="success">{success}</Alert>}

            <Form onSubmit={handleSubmit} className="shadow p-4 rounded" style={{ backgroundColor: '#ffffff', borderRadius: '10px' }}>
                <Form.Group className="mb-3">
                    <Form.Label>VIN <FaInfoCircle className="text-muted" /></Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="Entrez le VIN"
                        value={vin}
                        onChange={(e) => setVin(e.target.value)}
                        required
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Immatriculation <FaInfoCircle className="text-muted" /></Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="Entrez l'immatriculation"
                        value={immatriculation}
                        onChange={(e) => setImmatriculation(e.target.value)}
                        required
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Marque <FaInfoCircle className="text-muted" /></Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="Entrez la marque"
                        value={marque}
                        onChange={(e) => setMarque(e.target.value)}
                        required
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Modèle <FaInfoCircle className="text-muted" /></Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="Entrez le modèle"
                        value={modele}
                        onChange={(e) => setModele(e.target.value)}
                        required
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Année <FaCalendarAlt className="text-muted" /></Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="Entrez l'année"
                        value={annee}
                        onChange={(e) => setAnnee(e.target.value)}
                        required
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Couleur <FaInfoCircle className="text-muted" /></Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="Entrez la couleur"
                        value={couleur}
                        onChange={(e) => setCouleur(e.target.value)}
                        required
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Kilométrage <FaInfoCircle className="text-muted" /></Form.Label>
                    <Form.Control
                        type="number"
                        placeholder="Entrez le kilométrage"
                        value={km}
                        onChange={(e) => setKm(e.target.value)}
                        required
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Type de Carburant <FaInfoCircle className="text-muted" /></Form.Label>
                    <Form.Control
                        as="select"
                        value={typeCarb}
                        onChange={(e) => setTypeCarb(e.target.value)}
                        required
                    >
                        <option value="">Sélectionnez le type de carburant</option>
                        <option value="ESSENCE">ESSENCE</option>
                        <option value="DIESEL">DIESEL</option>
                        <option value="ELECTRIQUE">ELECTRIQUE</option>
                        <option value="HYBRID">HYBRID</option>
                    </Form.Control>
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Date d'Achat <FaCalendarAlt className="text-muted" /></Form.Label>
                    <Form.Control
                        type="date"
                        value={dateAchat}
                        onChange={(e) => setDateAchat(e.target.value)}
                        required
                    />
                </Form.Group>

                <Form.Group controlId="clientId">
                    <Form.Label>Propriétaire (ID Client)</Form.Label>
                    <Form.Control
                        as="select"
                        name="clientId"
                        value={clientId}
                        onChange={(e) => setClientId(e.target.value)}
                        required
                    >
                        <option value="">-- Sélectionner un client --</option>
                        {clients.map((id) => (
                            <option key={id} value={id}>
                                {id}
                            </option>
                        ))}
                    </Form.Control>
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>État <FaInfoCircle className="text-muted" /></Form.Label>
                    <Form.Control
                        as="select"
                        value={etat}
                        onChange={(e) => setEtat(e.target.value)}
                        required
                    >
                        <option value="">Sélectionnez l'état</option>
                        <option value="ENATTENTE">ENATTENTE</option>
                        <option value="ENREPARATION">ENREPARATION</option>
                        <option value="DONE">DONE</option>
                    </Form.Control>
                </Form.Group>

                <Button variant="success" type="submit" className="w-100" style={{ backgroundColor: '#28a745', borderColor: '#28a745' }}>
                    Ajouter le Véhicule
                </Button>
            </Form>
        </Container>
    );
};

export default VehiculeForm;
