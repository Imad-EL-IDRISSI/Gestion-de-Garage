import React, { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Container, Form, Button, Alert } from 'react-bootstrap';
import { FaCalendarAlt, FaInfoCircle, FaWrench } from 'react-icons/fa';

const MaintenanceForm = () => {
    const [debut, setDebut] = useState('');
    const [fin, setFin] = useState('');
    const [description, setDescription] = useState('');
    const [statut, setStatut] = useState('');
    const [vehiculeId, setVehiculeId] = useState('');
    const [vehicules, setVehicules] = useState([]); // Liste des véhicules
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    useEffect(() => {
        // Récupérer la liste des véhicules disponibles dans le garage
        const fetchVehicules = async () => {
            try {
                const response = await fetch('http://localhost:8888/VEHICULE-SERVICE/vehicules/fonctionnels'); // Modifier avec votre endpoint réel
                if (!response.ok) throw new Error('Erreur lors de la récupération des véhicules.');
                const data = await response.json();
                setVehicules(data); // Assurez-vous que data contient une liste de véhicules avec VINs
            } catch (err) {
                setError(err.message);
            }
        };

        fetchVehicules();
    }, []);

    const handleSubmit = async (event) => {
        event.preventDefault();

        if (!debut || !fin || !description || !statut || !vehiculeId) {
            setError('Tous les champs sont requis.');
            return;
        }
        setError('');

        const maintenanceData = { debut, fin, description, statut, vehiculeId };

        try {
            const response = await fetch('http://localhost:8888/MAINTENANCE-SERVICE/maintenanceTasks/ajouter', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(maintenanceData),
            });

            if (!response.ok) {
                throw new Error('Une erreur est survenue lors de l\'enregistrement de la maintenance.');
            }

            setSuccess('Maintenance ajoutée avec succès !');
            setDebut('');
            setFin('');
            setDescription('');
            setStatut('');
            setVehiculeId('');
        } catch (error) {
            setError(error.message);
        }
    };

    return (
        <Container className="mt-5" style={{ maxWidth: '500px' }}>
            <h2 className="text-center text-primary mb-3" style={{ fontWeight: 'bold', textTransform: 'uppercase', fontSize: '2rem' }}>
                Ajouter une Maintenance
            </h2>
            <p className="text-center text-muted mb-4" style={{ fontSize: '1rem' }}>
                Veuillez remplir le formulaire ci-dessous pour ajouter une maintenance.
            </p>

            {error && <Alert variant="danger">{error}</Alert>}
            {success && <Alert variant="success">{success}</Alert>}
            <Form onSubmit={handleSubmit} className="shadow p-4 rounded" style={{ backgroundColor: '#ffffff', borderRadius: '10px' }}>
                <Form.Group className="mb-3">
                    <Form.Label>Début <FaCalendarAlt className="text-muted" /></Form.Label>
                    <Form.Control
                        type="date"
                        value={debut}
                        onChange={(e) => setDebut(e.target.value)}
                        required
                    />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Fin <FaCalendarAlt className="text-muted" /></Form.Label>
                    <Form.Control
                        type="date"
                        value={fin}
                        onChange={(e) => setFin(e.target.value)}
                        required
                    />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Description <FaInfoCircle className="text-muted" /></Form.Label>
                    <Form.Control
                        as="textarea"
                        rows={3}
                        placeholder="Entrez la description de la maintenance"
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                        required
                    />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Statut <FaWrench className="text-muted" /></Form.Label>
                    <Form.Control
                        as="select"
                        value={statut}
                        onChange={(e) => setStatut(e.target.value)}
                        required
                    >
                        <option value="">Sélectionnez le statut</option>
                        <option value="PLANNED">PLANNED</option>
                        <option value="IN_PROGRESS">IN_PROGRESS</option>
                        <option value="COMPLETED">COMPLETED</option>
                    </Form.Control>
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Véhicule (VIN) <FaInfoCircle className="text-muted" /></Form.Label>
                    <Form.Control
                        as="select"
                        value={vehiculeId}
                        onChange={(e) => setVehiculeId(e.target.value)}
                        required
                    >
                        <option value="">Sélectionnez un véhicule</option>
                        {vehicules.map((vehicule) => (
                            <option key={vehicule.id} value={vehicule.id}>
                                {vehicule.vin} {/* Assurez-vous que chaque véhicule a un champ VIN */}
                            </option>
                        ))}
                    </Form.Control>
                </Form.Group>
                <Button variant="primary" type="submit" className="w-100">
                    Enregistrer Maintenance
                </Button>
            </Form>
        </Container>
    );
};

export default MaintenanceForm;
